package com.test;

import com.test.exceptions.NotMoveException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ExternalControlPanelTest {
    private static final int FLOOR_COUNT = 12;
    private MotionProcessor motionProcessor;

    @Before
    public void setUp() throws Exception {
        motionProcessor = Mockito.mock(MotionProcessor.class);
        Mockito.when(motionProcessor.getFloorCount()).thenReturn(FLOOR_COUNT);
    }

    @Test(expected = NotMoveException.class)
    public void callUpExceptionTest() throws Exception {
        new ExternalControlPanel(motionProcessor, FLOOR_COUNT).call(ElevatorState.UP_MOVE);
    }

    @Test(expected = NotMoveException.class)
    public void callDownExceptionTest() throws Exception {
        new ExternalControlPanel(motionProcessor, Constants.FIRST_FLOOR).call(ElevatorState.DOWN_MOVE);
    }

}