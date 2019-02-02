package com.test;

import com.mycila.junit.concurrent.Concurrency;
import com.mycila.junit.concurrent.ConcurrentJunitRunner;
import com.test.exceptions.FloorNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Deque;

@RunWith(ConcurrentJunitRunner.class)
@Concurrency(6)
public class MotionProcessorTest {

    private static final int FLOOR_COUNT = 12;
    private MotionProcessor motionProcessor;

    @Before
    public void setUp() throws Exception {
        final Elevator elevator = Mockito.mock(Elevator.class);
        motionProcessor = new MotionProcessor(elevator, FLOOR_COUNT);
    }

    @Test
    public void getFloorCountTest() throws Exception {
        final int floorCount = motionProcessor.getFloorCount();
        Assert.assertEquals(FLOOR_COUNT, floorCount);
    }

    @Test(expected = FloorNotFoundException.class)
    public void callElevator_floorMoreLimitExceptionTest() throws Exception {
        motionProcessor.callElevator(FLOOR_COUNT + 1, ElevatorState.DOWN_MOVE);
    }

    @Test(expected = FloorNotFoundException.class)
    public void callElevator_floorLessLimitExceptionTest() throws Exception {
        motionProcessor.callElevator(Constants.FIRST_FLOOR - 1, ElevatorState.DOWN_MOVE);
    }

    @Test()
    public void callsElevatorTest() throws Exception {
        final int firstCalledFloor = 3;
        final int secondCalledFloor = 4;
        final int thirdCalledFloor = 2;

        motionProcessor.callElevator(firstCalledFloor, ElevatorState.DOWN_MOVE);
        motionProcessor.callElevator(secondCalledFloor, ElevatorState.DOWN_MOVE);
        motionProcessor.callElevator(thirdCalledFloor, ElevatorState.DOWN_MOVE);

        final Deque<Call> calls = motionProcessor.getCalls();
        Assert.assertEquals(3, calls.size());

        Assert.assertEquals(thirdCalledFloor, calls.poll().getFloor());
        Assert.assertEquals(firstCalledFloor, calls.poll().getFloor());
        Assert.assertEquals(secondCalledFloor, calls.poll().getFloor());
    }

    @Test()
    public void integrationElevatorTest() throws Exception {
        final Elevator elevator = new Elevator();
        final MotionProcessor motionProcessor = new MotionProcessor(elevator, FLOOR_COUNT);
        motionProcessor.callElevator(3, ElevatorState.DOWN_MOVE);
        motionProcessor.callElevator(4, ElevatorState.DOWN_MOVE);
        motionProcessor.callElevator(2, ElevatorState.DOWN_MOVE);
        elevator.join(4000);

        Assert.assertEquals(false, elevator.isAlive());
    }
}