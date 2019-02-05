package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * класс лифта
 * считаем что лифт включает в себя кабину и двигатель
 */

public class Elevator extends Thread {
    private static final Logger log = LoggerFactory.getLogger(Elevator.class);
    /**
     * пауза в цикле что бы не перегружать процесстор пустой работой
     */
    private static final int LOOP_DELAY = 50;
    /**
     * задержка имитирующая движение лифта
     */
    private static final int FLOOR_MOTION_DELAY = 1000;

    /**
     * этаж на котором находится лифт
     */
    private int floor = Constants.FIRST_FLOOR;

    /**
     * обратная связь
     * возвращает значение этажа мимо которого проезжает лифт
     */
    protected FloorListener floorListener;

    /**
     * эмитация направления движения
     */
    private ElevatorState elevatorState = ElevatorState.STAY_MOVE;

    boolean isElevatorRun = false;

    public void setState(ElevatorState elevatorState) {
        this.elevatorState = elevatorState;
        if (!isElevatorRun) {
            start();
        }
    }

    public void setFloorListener(FloorListener floorListener) {
        this.floorListener = floorListener;
    }

    @Override
    public void run() {
        isElevatorRun = true;
        while (isElevatorRun) {
            switch (elevatorState) {
                case UP_MOVE:
                case DOWN_MOVE:
                    gotoNextFloor();
                    break;
                case STAY_MOVE:
                    delay(LOOP_DELAY);
                    break;
                case SHUTDOWN:
                    isElevatorRun = false;
                    log.debug("Лифт отключен");
            }
        }
    }

    private void gotoNextFloor() {
        delay(FLOOR_MOTION_DELAY);
        floor += elevatorState.getIncrease();
        if (floorListener != null) {
            floorListener.onFloor(floor);
        }
    }

    private void delay(int mils) {
        try {
            Thread.sleep(mils);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

