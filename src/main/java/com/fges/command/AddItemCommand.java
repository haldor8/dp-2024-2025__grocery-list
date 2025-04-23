package com.fges.command;

import com.fges.dao.GroceryDAO;
import com.fges.data_structures.GroceryList;
import com.fges.data_structures.Item;
import java.io.IOException;

public class AddItemCommand {
    private final GroceryDAO dao;
    private final GroceryList groceryList;

    public AddItemCommand(GroceryDAO dao, GroceryList groceryList) {
        this.dao = dao;
        this.groceryList = groceryList;
    }

    /**
     * Ajoute un élément à la liste
     */
    public int execute(String name, String quantityStr, String category) throws IOException {
        int quantity;

        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            System.err.println("Invalid quantity format");
            return 1;
        }

        // Vérifier si l'élément existe réellement ou non
        int itemIndex = groceryList.findElementIndex(name, category);
        if(itemIndex != -1){
            groceryList.getListeObjets().get(itemIndex).addQuantity(quantity);
        } else {
            groceryList.addItem(new Item(name, quantity, category));
        }

        dao.save(groceryList);
        return 0;
    }
}