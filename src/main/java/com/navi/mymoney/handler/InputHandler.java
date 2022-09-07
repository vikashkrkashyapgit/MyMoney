package com.navi.mymoney.handler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    public List<String> getCommands() throws IOException {
        String filePath = "./src/main/resources/input.txt";
        List<String> commands = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                commands.add(line);
                line = bufferedReader.readLine();
            }

        } catch (IOException fileNotFound) {
            System.out.println(fileNotFound.getMessage());
        }
        return commands;
    }
}
