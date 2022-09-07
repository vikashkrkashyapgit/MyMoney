package com.navi.mymoney.factory;

import com.navi.mymoney.repository.PortfolioRepository;
import com.navi.mymoney.service.PortfolioService;

public class ServiceFactory {

    private static PortfolioService factory;
    private ServiceFactory() {

    }

    public static PortfolioService getInstance() {
        if (factory == null) {
            PortfolioRepository repository = new PortfolioRepository();
            factory = new PortfolioService(repository);
        }
         return factory;

    }
}
