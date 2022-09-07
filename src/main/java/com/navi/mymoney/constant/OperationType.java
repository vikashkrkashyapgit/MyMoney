package com.navi.mymoney.constant;

public enum OperationType {
    ALLOCATION("Allocation"),
    EXISTING("Existing"),
    SIP("After SIP"),
    MARKET_CHANGE("After Market Change"),
    REBALANCE("Rebalance");

    public final String label;

    private OperationType(String label) {
        this.label = label;
    }

}
