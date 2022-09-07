package com.navi.mymoney.model.command;

public class Allocation extends Command {
    public Allocation(double equity, double debt, double gold) {
        super(equity, debt, gold);
    }

    @Override
    public void validate() {

    }
}
