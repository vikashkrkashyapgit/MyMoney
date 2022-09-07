package com.navi.mymoney.exception;

public class InvalidCommandException extends IllegalArgumentException {

    public InvalidCommandException() {
    }

    public InvalidCommandException(String message) {
        super(message);
    }
}
