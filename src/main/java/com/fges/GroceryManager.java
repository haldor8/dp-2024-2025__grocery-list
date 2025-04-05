package com.fges;

// DAO
import com.fges.dao.*;

// Structures de données
import com.fges.data_structures.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GroceryManager {
    private final GroceryDAO dao;
    private GroceryList groceryList;

    public GroceryManager(String fileName, String outputFormat) throws IOException {
        this.dao = createDAO(fileName, outputFormat);
        this.groceryList = dao.load();
    }

    private GroceryDAO createDAO(String fileName, String format) {
        return format.equalsIgnoreCase("csv")
                ? new CsvGroceryDAO(fileName)
                : new JsonGroceryDAO(fileName);
    }

    public int handleCommand(String command, List<String> positionalArgs) throws IOException {
        switch (command) {
            case "add":
                if (positionalArgs.size() < 4) {
                    System.err.println("Missing arguments for add (need name, quantity, category)");
                    return 1;
                }
                return addItem(positionalArgs.get(1), positionalArgs.get(2), positionalArgs.get(3));

            case "remove":
                if (positionalArgs.size() < 2) {
                    System.err.println("Missing arguments for remove (need name)");
                    return 1;
                }
                return removeItem(positionalArgs.get(1));

            case "list":
                return listItems();

            default:
                System.err.println("Unknown command: " + command);
                return 1;
        }
    }

    private int addItem(String name, String quantityStr, String category) throws IOException {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            System.err.println("Invalid quantity format");
            return 1;
        }
        if (category.equalsIgnoreCase("inconnu")) {
            category = "default";
        }
        groceryList.addItem(new Item(name, quantity, category));
        dao.save(groceryList);
        return 0;
    }

    private int removeItem(String name) throws IOException {
        boolean removed = groceryList.removeItemByName(name);
        if (removed) {
            dao.save(groceryList);
            return 0;
        } else {
            System.err.println("Item not found: " + name);
            return 1;
        }
    }

    private int listItems() {
        List<Item> items = groceryList.getListeObjets();
        List<String> categories = new ArrayList<>();

        // Extraire toutes les catégories uniques
        for (Item item : items) {
            String category = item.getCategory();
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }

        // Pour chaque catégorie, afficher tous les éléments correspondants (potentiellement ajouter un algo de tri)
        for (String category : categories) {
            System.out.println("# " + category + " :");

            for (Item item : items) {
                if (item.getCategory().equals(category)) {
                    System.out.println(item.getName());
                }
            }
        }
        return 0;
    }

}