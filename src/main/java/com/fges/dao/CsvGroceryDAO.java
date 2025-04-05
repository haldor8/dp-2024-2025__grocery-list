package com.fges.dao;

import com.fges.data_structures.GroceryList;
import com.fges.data_structures.Item;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CsvGroceryDAO implements GroceryDAO {
    private final String filePath;

    public CsvGroceryDAO(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public GroceryList load() throws IOException {
        GroceryList groceryList = new GroceryList();

        if (!Files.exists(Paths.get(filePath))) {
            return groceryList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String name = parts[0].trim();
                    int quantity;
                    try {
                        quantity = Integer.parseInt(parts[1].trim());
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    String category = parts[2].trim();
                    if (category.equalsIgnoreCase("inconnu")) {
                        category = "default";
                    }

                    groceryList.addItem(new Item(name, quantity, category));
                }
            }
        }

        return groceryList;
    }

    @Override
    public void save(GroceryList list) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("name,quantity,category");
            writer.newLine();
            for (Item item : list.getListeObjets()) {
                writer.write(item.getName() + "," + item.getQuantity() + "," + item.getCategory());
                writer.newLine();
            }
        }
    }
}
