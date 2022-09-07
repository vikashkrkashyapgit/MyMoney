package com.navi.mymoney.model.command;

import com.navi.mymoney.constant.FundType;

public abstract class Command {
    protected double equity;
    protected double debt;
    protected double gold;

    public Command(double equity, double debt, double gold) {
        this.equity = equity;
        this.debt = debt;
        this.gold = gold;
    }

    protected Command() {
    }

    public double getAmount (FundType type) {
        double amount = 0;
        switch (type) {
            case EQUITY:
                amount = this.equity;
                break;
            case DEBT:
                amount = this.debt;
                break;
            case GOLD:
                amount = this.gold;
                break;
        }
        return amount;
    }

    public abstract void validate();
}
