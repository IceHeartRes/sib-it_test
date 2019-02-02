package com.test.exceptions;

public class NotMoveException extends Exception {
    public NotMoveException() {
        super("Движение невозможно. Крайний этаж");
    }
}
