package com.navi.mymoney.model.command;

public class SIP extends Command {
    public SIP(double equity, double debt, double gold) {
        super(equity, debt, gold);
    }

    @Override
    public void validate() {

    }
}
