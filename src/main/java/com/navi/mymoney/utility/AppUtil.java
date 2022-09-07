package com.navi.mymoney.utility;

public class AppUtil {

    public static double stringToDouble(String number) {
        return Double.parseDouble(number);
    }

    public static double percentageToDouble(String percentage) {
        return Double.parseDouble(percentage.replace("%", ""));
    }

    public static String getOperationName(String month, String operation) {
        return month + operation;
    }
}
