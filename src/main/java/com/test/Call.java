package com.test;

/**
 * модель вызова лифта
 */

public class Call {
    private final int floor;
    private final ElevatorState elevatorState;

    public Call(int floor, ElevatorState elevatorState) {
        this.floor = floor;
        this.elevatorState = elevatorState;
    }

    public int getFloor() {
        return floor;
    }

    public ElevatorState getElevatorState() {
        return elevatorState;
    }
}
