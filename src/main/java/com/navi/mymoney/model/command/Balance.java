package com.navi.mymoney.model.command;

import com.navi.mymoney.constant.Month;

public class Balance extends Command {

    private Month month;

    public Balance(String month) {
        this.month = Month.valueOf(month);
    }

    public Month getMonth() {
        return month;
    }

    @Override
    public void validate() {

    }
}
