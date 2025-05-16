package com.fges.command;

import com.fges.dao.GroceryDAO;
import com.fges.data_structures.GroceryList;
import java.io.IOException;
import java.util.List;

public class CommandHandler {
    private final GroceryDAO dao;
    private GroceryList groceryList;

    public CommandHandler(GroceryDAO dao) throws IOException {
        this.dao = dao;
        this.groceryList = dao.load();
    }

    /**
     * Gère la commande passée en CLI
     */
    public int handleCommand(String command, List<String> positionalArgs, String category) throws IOException {
        System.out.println(positionalArgs);
        switch (command) {
            case "add":
                if (positionalArgs.size() < 3) {
                    System.err.println("Missing arguments for add (need name, quantity, category)");
                    return 1;
                }
                return new AddItemCommand(dao, groceryList).execute(
                        positionalArgs.get(1), positionalArgs.get(2), category);

            case "remove":
                if (positionalArgs.size() < 2) {
                    System.err.println("Missing arguments for remove (need name)");
                    return 1;
                }
                return new RemoveItemCommand(dao, groceryList).execute(positionalArgs.get(1));

            case "list":
                return new ListItemsCommand(groceryList).execute();

            case "info":
                return new InfoCommand().execute();

            case "web":
                int port = 3000; // Port par défaut
                if (positionalArgs.size() > 1) {
                    try {
                        port = Integer.parseInt(positionalArgs.get(1));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid port number provided. Using default port 8080.");
                    }
                }
                return new WebCommand().execute(port, this);

            default:
                System.err.println("Unknown command: " + command);
                return 1;
        }
    }
}