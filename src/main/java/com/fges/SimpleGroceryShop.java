package com.fges;

import fr.anthonyquere.MyGroceryShop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

import com.fges.command.CommandHandler;

public class SimpleGroceryShop implements MyGroceryShop {
    private final List<WebGroceryItem> groceries = new ArrayList<>();

    private final CommandHandler commandHandler;

    public SimpleGroceryShop(CommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }

    @Override
    public List<WebGroceryItem> getGroceries() {
        return new ArrayList<>(groceries);
    }

    @Override
    public void addGroceryItem(String name, int quantity, String category) {
        groceries.add(new WebGroceryItem(name, quantity, category));
        try {
            String command = "add";
            commandHandler.handleCommand(command, List.of(command, name, String.valueOf(quantity)), category);
        } catch (IOException e) {
            System.err.println("Failed to persist item to file: " + e.getMessage());
            e.printStackTrace(); // Pour le debug
        }
    }

    @Override
    public void removeGroceryItem(String name) {
        groceries.removeIf(item -> item.name().equals(name));
        try {
            String command = "remove";
            commandHandler.handleCommand(command, List.of(command, name), "");
        } catch (IOException e) {
            System.err.println("Failed to persist item to file: " + e.getMessage());
            e.printStackTrace(); // Pour le debug
        }
    }

    @Override
    public Runtime getRuntime() {
        return new Runtime(
                LocalDate.now(),
                System.getProperty("java.version"),
                System.getProperty("os.name")
        );
    }
}