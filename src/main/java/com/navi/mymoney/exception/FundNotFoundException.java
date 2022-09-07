package com.navi.mymoney.exception;

public class FundNotFoundException extends Exception {
    public FundNotFoundException() {
    }

    public FundNotFoundException(String message) {
        super(message);
    }
}
