package com.fges.command;

import com.fges.dao.GroceryDAO;
import com.fges.data_structures.GroceryList;
import java.io.IOException;

public class RemoveItemCommand {
    private final GroceryDAO dao;
    private final GroceryList groceryList;

    public RemoveItemCommand(GroceryDAO dao, GroceryList groceryList) {
        this.dao = dao;
        this.groceryList = groceryList;
    }

    /**
     * Supprime un élément de la liste
     */
    public int execute(String name) throws IOException {
        boolean removed = groceryList.removeItemByName(name);
        if (removed) {
            dao.save(groceryList);
            return 0;
        } else {
            System.err.println("Item not found: " + name);
            return 1;
        }
    }
}