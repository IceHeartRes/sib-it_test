package com.test;

import com.test.exceptions.FloorNotFoundException;
import com.test.exceptions.NotMoveException;

public class Main {

    private static final int FLOOR_COUNT = 12;

    public static void main(String[] args) throws FloorNotFoundException, InterruptedException, NotMoveException {
        final Elevator elevator = new Elevator();
        final MotionProcessor motionProcessor = new MotionProcessor(elevator, FLOOR_COUNT);
        final ExternalControlPanel externalControlPanel2 = new ExternalControlPanel(motionProcessor, 2);
        final ExternalControlPanel externalControlPanel9 = new ExternalControlPanel(motionProcessor, 9);
        final ExternalControlPanel externalControlPanel8 = new ExternalControlPanel(motionProcessor, 8);

        externalControlPanel9.call(ElevatorState.DOWN_MOVE);

        Thread.sleep(2000);

        externalControlPanel8.call(ElevatorState.DOWN_MOVE);

        Thread.sleep(2000);

        externalControlPanel2.call(ElevatorState.DOWN_MOVE);
    }
}
