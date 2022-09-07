package com.navi.mymoney.model;

import com.navi.mymoney.constant.FundType;

public class Fund {

    private double amount;
    private FundType type;

    public void validate() {

    }

    public void setAmount(double amount) {
        this.amount = Math.floor(amount);
    }

    public double getAmount() {
        return Math.floor(amount);
    }

    public void setType(FundType type) {
        this.type = type;
    }

    public FundType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "amount=" + amount +
                ", type=" + type +
                '}';
    }
}
