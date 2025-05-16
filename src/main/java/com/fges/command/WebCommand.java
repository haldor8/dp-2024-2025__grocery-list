package com.fges.command;

import java.io.IOException;

import fr.anthonyquere.GroceryShopServer;
import com.fges.SimpleGroceryShop;

import com.fges.command.CommandHandler;

public class WebCommand {
    public int execute(int port, CommandHandler commandHandler) {
        SimpleGroceryShop groceryShop = new SimpleGroceryShop(commandHandler);

        GroceryShopServer server = new GroceryShopServer(groceryShop);
        server.start(port);

        System.out.println("Grocery shop server started at http://localhost:" + port);

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return 1;
    }
}
