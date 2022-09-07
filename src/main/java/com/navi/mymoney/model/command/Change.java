package com.navi.mymoney.model.command;

import com.navi.mymoney.constant.Month;

public class Change extends Command {

    private Month month;
    public Change(double equity, double debt, double gold, Month month) {
        super(equity, debt, gold);
        this.month = month;
    }

    @Override
    public void validate() {

    }

    public Month getMonth() {
        return this.month;
    }
}
