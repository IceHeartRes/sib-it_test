package com.test;

import com.test.exceptions.FloorNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * класс управления лифтом
 */

public class MotionProcessor {
    private static final Logger log = LoggerFactory.getLogger(MotionProcessor.class);
    /**
     * инстанс лифта
     */
    private Elevator elevator = null;
    /**
     * количество этажей в доме
     */
    private final int floorCount;
    /**
     * этаж который в текущий момент проезжает лифт
     */
    private int currentFloor = Constants.FIRST_FLOOR;
    /**
     * в какую стророну в текущий момент едит лифт
     */
    private ElevatorState currentDirection = ElevatorState.STAY_MOVE;
    /**
     * очередь вызовов лифта
     */
    private Deque<Call> calls = new LinkedBlockingDeque<>();
    /**
     * лисенер обратной связи позиции лифта
     */
    private final FloorListener floorListener = floorNumber -> {
        log.debug("Этаж номер {}", floorNumber);
        currentFloor = floorNumber;
        final Call call = calls.peek();
        if (currentFloor == call.getFloor() && elevator != null) {
            stopElevator();
            log.debug("Лифт остановлен");
            nextCall();
        }
    };

    public MotionProcessor(Elevator elevator, int floorCount) {
        this.elevator = elevator;
        elevator.setFloorListener(floorListener);
        this.floorCount = floorCount;
    }

    public int getFloorCount() {
        return floorCount;
    }

    public void callElevator(int callFloor, ElevatorState callElevatorState) throws FloorNotFoundException {
        if (callFloor > floorCount || callFloor < Constants.FIRST_FLOOR) {
            throw new FloorNotFoundException();
        }

        addToDeque(callFloor, callElevatorState);
        move(callFloor);
    }

    public Deque<Call> getCalls() {
        return calls;
    }

    private void nextCall() {
        final Call peek = calls.peek();
        if (peek != null) {
            move(peek.getFloor());
        } else {
            elevator.setState(ElevatorState.SHUTDOWN);
        }
    }

    private void stopElevator() {
        calls.poll();
        elevator.setState(ElevatorState.STAY_MOVE);
        currentDirection = ElevatorState.STAY_MOVE;
    }

    private void move(int callFloor) {
        final ElevatorState elevatorState = calcDirection(callFloor);
        if (currentDirection == ElevatorState.STAY_MOVE) {
            elevator.setState(elevatorState);
            currentDirection = elevatorState;
            log.debug("Лифт поехал: {}", elevatorState.name());
        }
    }

    private void addToDeque(int callFloor, ElevatorState callElevatorState) {
        final Call newCall = new Call(callFloor, callElevatorState);
        final Call call = calls.peek();
        if (call != null) {
            final int floor = call.getFloor();
            final ElevatorState elevatorState1 = call.getElevatorState();

            if ((floor > callFloor && callFloor > currentFloor)
                    && elevatorState1 == callElevatorState
                    && calcDirection(floor) == currentDirection) {
                calls.addFirst(newCall);
            } else {
                calls.add(newCall);
            }

        } else {
            calls.add(newCall);
        }
    }

    private ElevatorState calcDirection(int callFloor) {
        if (callFloor > currentFloor) {
            return ElevatorState.UP_MOVE;
        } else if (callFloor < currentFloor) {
            return ElevatorState.DOWN_MOVE;
        } else {
            return ElevatorState.STAY_MOVE;
        }
    }
}
