package com.fges.command;

import com.fges.data_structures.GroceryList;
import com.fges.data_structures.Item;
import com.fges.util.CategoryExtractor;
import java.util.List;

public class ListItemsCommand {
    private final GroceryList groceryList;

    public ListItemsCommand(GroceryList groceryList) {
        this.groceryList = groceryList;
    }

    /**
     * Liste tous les éléments par catégorie
     */
    public int execute() {
        List<Item> items = groceryList.getListeObjets();
        List<String> categories = new CategoryExtractor(items).extractCategories();

        // Pour chaque catégorie, afficher tous les éléments correspondants
        for (String category : categories) {
            System.out.println("# " + category + " :");

            for (Item item : items) {
                if (item.getCategory().equals(category)) {
                    System.out.println(item.getName() + ": " + item.getQuantity());
                }
            }
        }
        return 0;
    }
}