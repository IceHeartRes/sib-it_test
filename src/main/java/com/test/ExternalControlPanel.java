package com.test;

import com.test.exceptions.FloorNotFoundException;
import com.test.exceptions.NotMoveException;

/**
 * класс внешней панели управления
 */

public class ExternalControlPanel {

    private final MotionProcessor motionProcessor;
    private final int floor;

    public ExternalControlPanel(MotionProcessor motionProcessor, int floor) {
        this.motionProcessor = motionProcessor;
        this.floor = floor;
    }

    /**
     * вызов лифта
     * @param elevatorState направление в котором далее поедет пассажир
     * @throws FloorNotFoundException исключение если пытаемся вызвать лифт на несуществующий этаж
     * @throws NotMoveException исключение если пытаемся заказать движения с первого этажа вниз или с последнего вверх
     */
    public void call(ElevatorState elevatorState) throws FloorNotFoundException, NotMoveException {
        if ((floor == motionProcessor.getFloorCount() && elevatorState == ElevatorState.UP_MOVE)
                || (floor == Constants.FIRST_FLOOR && elevatorState == ElevatorState.DOWN_MOVE)) {
            throw new NotMoveException();
        }
        System.out.println("Лифт вызван на " + floor + ". Направление движения: " + elevatorState.name());
        motionProcessor.callElevator(floor, elevatorState);
    }
}
