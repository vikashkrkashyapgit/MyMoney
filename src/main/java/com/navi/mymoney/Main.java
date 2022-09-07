package com.navi.mymoney;

import com.navi.mymoney.factory.CommandFactory;
import com.navi.mymoney.factory.ServiceFactory;
import com.navi.mymoney.handler.InputHandler;
import com.navi.mymoney.model.command.Command;
import com.navi.mymoney.service.PortfolioService;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        InputHandler inputHandler = new InputHandler();
        List<String> commands = inputHandler.getCommands();
        PortfolioService portfolio = ServiceFactory.getInstance();

        for (String command: commands) {
            Command commandInput = CommandFactory.getInstance(command);
            portfolio.execute(commandInput);
        }
    }
}
