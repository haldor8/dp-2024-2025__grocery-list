package com.fges;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class GroceryManager {
    private final String inputFileName;
    private final String outputFileName;
    private final String inputFormat;
    private final String outputFormat;
    private Map<String, Integer> groceryList;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public GroceryManager(String fileName, String outputFormat) throws IOException {
        this.inputFileName = fileName;
        this.inputFormat = getFileExtension(fileName);
        this.outputFormat = outputFormat.toLowerCase(); // "json" ou "csv"
        this.outputFileName = getOutputFileName(fileName, this.outputFormat);
        this.groceryList = new HashMap<>();
        loadGroceryList();
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1).toLowerCase();
    }

    private String getOutputFileName(String fileName, String format) {
        return fileName.replaceAll("\\.[^.]+$", "") + "." + format;
    }

    private void loadGroceryList() throws IOException {
        Path filePath = Paths.get(inputFileName);
        if (Files.exists(filePath)) {
            if (inputFormat.equals("json")) {
                readFromJSON();
            } else if (inputFormat.equals("csv")) {
                readFromCSV();
            } else {
                System.err.println("Unsupported file format: " + inputFormat);
            }
        }
    }

    private void readFromJSON() throws IOException {
        String fileContent = Files.readString(Paths.get(inputFileName));
        groceryList = OBJECT_MAPPER.readValue(fileContent, new TypeReference<Map<String, Integer>>() {});
    }

    private void readFromCSV() throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFileName))) {
            groceryList.clear();
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        groceryList.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format in CSV: " + line);
                    }
                }
            }
        }
    }

    public int handleCommand(String command, List<String> positionalArgs) throws IOException {
        switch (command) {
            case "add" -> {
                if (positionalArgs.size() < 3) {
                    System.err.println("Missing arguments");
                    return 1;
                }
                return addItem(positionalArgs.get(1), positionalArgs.get(2));
            }
            case "list" -> {
                return listItems();
            }
            case "remove" -> {
                if (positionalArgs.size() < 2) {
                    System.err.println("Missing arguments");
                    return 1;
                }
                return removeItem(positionalArgs.get(1));
            }
            default -> {
                System.err.println("Unknown command: " + command);
                return 1;
            }
        }
    }

    private int addItem(String itemName, String quantityStr) throws IOException {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            System.err.println("Invalid quantity format");
            return 1;
        }

        groceryList.put(itemName, groceryList.getOrDefault(itemName, 0) + quantity);
        saveGroceryList();
        return 0;
    }

    private int removeItem(String itemName) throws IOException {
        if (groceryList.containsKey(itemName)) {
            groceryList.remove(itemName);
            saveGroceryList();
            return 0;
        } else {
            System.err.println("Item not found: " + itemName);
            return 1;
        }
    }

    private int listItems() {
        if (groceryList.isEmpty()) {
            System.out.println("The grocery list is empty.");
            return 0;
        }

        groceryList.forEach((key, value) -> System.out.println(key + ": " + value));
        return 0;
    }

    private void saveGroceryList() throws IOException {
        if (outputFormat.equals("json")) {
            OBJECT_MAPPER.writeValue(new File(outputFileName), groceryList);
        } else if (outputFormat.equals("csv")) {
            writeToCSV();
        } else {
            System.err.println("Unsupported output format: " + outputFormat);
        }
    }

    private void writeToCSV() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFileName))) {
            writer.write("name,quantity");
            writer.newLine();
            for (var entry : groceryList.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        }
    }
}
