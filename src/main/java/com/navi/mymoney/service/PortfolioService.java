package com.navi.mymoney.service;

import com.navi.mymoney.model.command.*;
import com.navi.mymoney.repository.PortfolioRepository;

public class PortfolioService {

    PortfolioRepository repository;
    public PortfolioService(PortfolioRepository repository) {
        this.repository = repository;
    }

    public void execute(Command command) {
        if (command instanceof Allocation) {
            this.repository.allocate(command);
        }
        else if (command instanceof Balance) {
            this.repository.balance(command);
        }
        else if(command instanceof SIP) {
            this.repository.sip(command);
        }
        else if(command instanceof Change) {
            this.repository.change(command);
        }
        else if(command instanceof Rebalance) {
            this.repository.rebalance();
        }
        else {
            // do nothing
        }
    }

}
