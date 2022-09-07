package com.navi.mymoney.repository;
import com.navi.mymoney.constant.FundType;
import com.navi.mymoney.constant.Month;
import com.navi.mymoney.constant.OperationType;
import com.navi.mymoney.exception.FundNotFoundException;
import com.navi.mymoney.model.Fund;
import com.navi.mymoney.model.command.*;
import com.navi.mymoney.utility.AppUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PortfolioRepository {
    private final HashMap<String, List<Fund>> portfolio = new HashMap<>();
    private List<Fund> sip = new ArrayList<>();
    private final HashMap<FundType, Double> initialInvestmentShare = new HashMap<>();
    private Month currentMonth = null;

    /**
     * All functional method required for application
     */

    public void allocate(Command command) {
        Allocation allocation = (Allocation) command;
        List<Fund> funds = new ArrayList<>(this.getFunds(allocation));
        this.portfolio.put(getOperationName(Month.JANUARY, OperationType.ALLOCATION), funds);
        this.portfolio.put(getOperationName(Month.JANUARY, OperationType.EXISTING), funds);
        this.portfolio.put(getOperationName(Month.JANUARY, OperationType.MARKET_CHANGE), funds);
        this.updateNextMonthExistingBalance(Month.JANUARY, funds);
        this.setInitialInvestmentShare(allocation);
    }

    public void sip(Command command) {
        SIP sip = (SIP) command;
        this.sip = this.getFunds(sip);
    }

    public void change(Command command) {

        try {
            Change change = (Change) command;
            String operation = getOperationName(change.getMonth(), OperationType.EXISTING);

            if (!this.portfolio.containsKey(operation)) {
                throw new FundNotFoundException("Fund is not available for this month");
            }

            // Apply SIP (if any)
            if (!change.getMonth().equals(Month.JANUARY) && this.sip.size() == FundType.values().length) {
                this.addSIPFund(change.getMonth());
                operation = getOperationName(change.getMonth(), OperationType.SIP);
            }

            // Apply market change
            List<Fund> balances = new ArrayList<>();
            for (Fund balance : this.portfolio.get(operation)) {
                Fund fund = new Fund();
                if (balance.getType().equals(FundType.EQUITY)) {
                    fund.setType(FundType.EQUITY);
                    fund.setAmount(this.getChangedAmount(balance.getAmount(), change.getAmount(FundType.EQUITY)));
                } else if (balance.getType().equals(FundType.DEBT)) {
                    fund.setType(FundType.DEBT);
                    fund.setAmount(this.getChangedAmount(balance.getAmount(), change.getAmount(FundType.DEBT)));
                } else if (balance.getType().equals(FundType.GOLD)) {
                    fund.setType(FundType.GOLD);
                    fund.setAmount(this.getChangedAmount(balance.getAmount(), change.getAmount(FundType.GOLD)));
                }
                balances.add(fund);
            }
            this.portfolio.put(getOperationName(change.getMonth(), OperationType.MARKET_CHANGE), balances);
            this.updateNextMonthExistingBalance(change.getMonth(), balances);
        } catch (Exception e) {
            System.out.println("Exception => " + e.getMessage());
        }
    }

    public void balance(Command command) {
        Balance balance = (Balance) command;
        String operation = getOperationName(balance.getMonth(), OperationType.MARKET_CHANGE);
        if (this.portfolio.containsKey(operation)) {
            for (Fund fund : this.portfolio.get(operation)) {
                System.out.print(fund.getAmount() + " ");
            }
            System.out.println("");
        } else {
            System.out.println("No balance found");
        }
    }

    public void rebalance() {
        String ERROR = "CANNOT REBALANCE";
        if (this.currentMonth == null) {
            System.out.println("");
        }

        if (this.currentMonth != null && this.currentMonth.equals(Month.JUNE)) {
            this.rebalanceByMonth(Month.JUNE);
        } else if (this.currentMonth != null && this.currentMonth.equals(Month.DECEMBER)) {
            this.rebalanceByMonth(Month.DECEMBER);
        } else {
            System.out.println(ERROR);
            return;
        }
    }

    /**
     * All private methods related to repository
     */

    private List<Fund> getFunds(Command command) {
        List<Fund> funds = new ArrayList<>();
        for (FundType type : FundType.values()) {
            Fund fund = new Fund();
            fund.setType(type);
            fund.setAmount(command.getAmount(type));
            funds.add(fund);
        }
        return funds;
    }

    private void setInitialInvestmentShare(Allocation balance) {
        double total = 0;

        for (FundType type : FundType.values()) {
            total += balance.getAmount(type);
        }

        for (FundType type : FundType.values()) {
            this.initialInvestmentShare.put(type, (balance.getAmount(type) / total) * 100);
        }
    }

    private void addSIPFund(Month current) {
        List<Fund> existingFund = new ArrayList<>();
        for (Fund existing : this.portfolio.get(this.getOperationName(current, OperationType.EXISTING))) {
            Fund fund = new Fund();
            fund.setType(existing.getType());
            fund.setAmount(existing.getAmount());
            for (Fund sip : this.sip) {
                if (sip.getType().equals(existing.getType())) {
                    fund.setAmount(existing.getAmount() + sip.getAmount());
                }
            }
            existingFund.add(fund);
        }
        this.portfolio.put(getOperationName(current, OperationType.SIP), existingFund);
    }

    private void updateNextMonthExistingBalance(Month currentMonth, List<Fund> funds) {
        Month nextMonth = nextMonth(currentMonth);
        if (nextMonth != null) {
            this.portfolio.put(getOperationName(nextMonth, OperationType.EXISTING), funds);
        }
    }

    private void rebalanceByMonth(Month month) {
        String error = "CANNOT_REBALANCE";
        String operation = getOperationName(month, OperationType.MARKET_CHANGE);
        if (!this.portfolio.containsKey(operation)) {
            System.out.println("Error");
            System.out.println(error);
            return;
        }

        double total = 0;
        for (Fund fund : this.portfolio.get(operation)) {
            total += fund.getAmount();
        }

        List<Fund> rebalance = new ArrayList<>();
        for (Fund fund : this.portfolio.get(operation)) {
            Fund rebalanceFund = new Fund();
            rebalanceFund.setType(fund.getType());
            double rebalanceAmount = (total * (this.initialInvestmentShare.get(fund.getType()))) / 100;
            rebalanceFund.setAmount(rebalanceAmount);
            rebalance.add(rebalanceFund);
        }
        this.portfolio.put(getOperationName(month, OperationType.REBALANCE), rebalance);
        for (Fund fund : rebalance) {
            System.out.print(fund.getAmount() + " ");
        }
        System.out.println("");
    }

    private String getOperationName(Month month, OperationType type) {
        return AppUtil.getOperationName(month.name(), type.label);
    }

    private double getChangedAmount(double amount, double percentage) {
        return amount + (amount * percentage) / 100;
    }

    private Month nextMonth(Month current) {
        boolean monthMatch = false;
        this.currentMonth = current;
        for (Month month : Month.values()) {
            if (monthMatch) {
                return month;
            }

            if (month.equals(current) && !month.equals(Month.DECEMBER)) {
                monthMatch = true;
            }
        }
        return null;
    }

}
