package com.test;

/**
 * перечисление состояний лифта
 */

public enum ElevatorState {
    /**
     * вверх
     */
    UP_MOVE(1),
    /**
     * вниз
     */
    DOWN_MOVE(-1),
    /**
     * остановить
     */
    STAY_MOVE(0),
    /**
     * отключен
     */
    SHUTDOWN;

    /**
     * значение на которое будет изменяться этаж
     */
    private int increase;

    ElevatorState(int increase) {
        this.increase = increase;
    }

    ElevatorState() {

    }

    public int getIncrease() {
        return increase;
    }
}
