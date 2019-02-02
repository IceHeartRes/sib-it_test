package com.test.exceptions;

public class FloorNotFoundException extends Exception {
    public FloorNotFoundException() {
        super("Невозможно переместить лифт на заданный этаж");
    }
}
