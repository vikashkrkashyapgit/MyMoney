package com.navi.mymoney.factory;

import com.navi.mymoney.constant.InputType;
import com.navi.mymoney.constant.Month;
import com.navi.mymoney.model.command.*;
import com.navi.mymoney.utility.AppUtil;

public class CommandFactory {

    public static Command getInstance(String command) {
        String[] arguments = command.split(" ");
        Command commandInput = null;

        switch (InputType.valueOf(arguments[0])) {
            case ALLOCATE:
                commandInput = new Allocation(AppUtil.stringToDouble(arguments[1]), AppUtil.stringToDouble(arguments[2]), AppUtil.stringToDouble(arguments[3]));
                break;
            case SIP:
                commandInput = new SIP(AppUtil.stringToDouble(arguments[1]), AppUtil.stringToDouble(arguments[2]), AppUtil.stringToDouble(arguments[3]));
                break;
            case CHANGE:
                commandInput = new Change(AppUtil.percentageToDouble(arguments[1]), AppUtil.percentageToDouble(arguments[2]), AppUtil.percentageToDouble(arguments[3]), Month.valueOf(arguments[4]));
                break;
            case BALANCE:
                commandInput = new Balance(arguments[1]);
                break;
            case REBALANCE:
                commandInput = new Rebalance();
                break;
        }

        return commandInput;
    }

}
