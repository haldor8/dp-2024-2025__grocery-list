package com.fges;

// DAO
import com.fges.dao.*;

// Structures de données
import com.fges.data_structures.*;

import java.io.IOException;
import java.util.*;

public class GroceryManager {
    private final GroceryDAO dao;
    private GroceryList groceryList;

    public GroceryManager(String fileName, String outputFormat) throws IOException {
        this.dao = createDAO(fileName, outputFormat);
        this.groceryList = dao.load();
    }

    private GroceryDAO createDAO(String fileName, String format) {
        String correctFileName = reformatFileName(fileName, format);

        return format.equalsIgnoreCase("csv")
                ? new CsvGroceryDAO(correctFileName)
                : new JsonGroceryDAO(correctFileName);
    }

    public int handleCommand(String command, List<String> positionalArgs, String category) throws IOException {
        // Switch case pour gérer la commande passée en CLI
        switch (command) {
            case "add":
                if (positionalArgs.size() < 3) {
                    System.err.println("Missing arguments for add (need name, quantity, category)");
                    return 1;
                }
                return addItem(positionalArgs.get(1), positionalArgs.get(2), category);

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

        // Vérifier si l'élément existe réellement ou non
        int itemIndex = groceryList.findElementIndex(name, category);
        if(itemIndex != -1){
            groceryList.getListeObjets().get(itemIndex).addQuantity(quantity);
        }else{
            groceryList.addItem(new Item(name, quantity, category));
        }

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
                    System.out.println(item.getName() + ": " + item.getQuantity());
                }
            }
        }
        return 0;
    }

    private String reformatFileName(String fileName, String format){
        // Garanti le bon format de fichier même si l'utilisateur rentre des extensions factices ou aucune

        String baseName = fileName.replaceAll("\\.[^.]+$", "");
        return baseName + "." + format;
    }

}