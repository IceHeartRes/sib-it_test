package com.test;

import com.test.exceptions.FloorNotFoundException;
import com.test.exceptions.NotMoveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * класс внешней панели управления
 */

public class ExternalControlPanel {
    private static final Logger log = LoggerFactory.getLogger(ExternalControlPanel.class);

    private final MotionProcessor motionProcessor;
    private final int floor;

    public ExternalControlPanel(MotionProcessor motionProcessor, int floor) {
        this.motionProcessor = motionProcessor;
        this.floor = floor;
    }

    /**
     * вызов лифта
     *
     * @param elevatorState направление в котором далее поедет пассажир
     * @throws FloorNotFoundException исключение если пытаемся вызвать лифт на несуществующий этаж
     * @throws NotMoveException       исключение если пытаемся заказать движения с первого этажа вниз или с последнего вверх
     */
    public void call(ElevatorState elevatorState) throws FloorNotFoundException, NotMoveException {
        if ((floor == motionProcessor.getFloorCount() && elevatorState == ElevatorState.UP_MOVE)
                || (floor == Constants.FIRST_FLOOR && elevatorState == ElevatorState.DOWN_MOVE)) {
            throw new NotMoveException();
        }
        log.debug("Лифт вызван на {}. Направление движения: {}", floor, elevatorState.name());
        motionProcessor.callElevator(floor, elevatorState);
    }
}
