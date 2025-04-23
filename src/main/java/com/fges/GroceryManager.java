package com.fges;

import com.fges.command.CommandHandler;
import com.fges.dao.GroceryDAO;
import com.fges.factory.DAOFactory;

import java.io.IOException;
import java.util.List;

public class GroceryManager {
    private final CommandHandler commandHandler;

    public GroceryManager(String fileName, String outputFormat) throws IOException {
        GroceryDAO dao = DAOFactory.createDAO(fileName, outputFormat);
        this.commandHandler = new CommandHandler(dao);
    }

    public int handleCommand(String command, List<String> positionalArgs, String category) throws IOException {
        return commandHandler.handleCommand(command, positionalArgs, category);
    }
}