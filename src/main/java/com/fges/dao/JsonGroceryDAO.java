package com.fges.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fges.data_structures.GroceryList;

import java.io.File;
import java.io.IOException;

public class JsonGroceryDAO implements GroceryDAO {
    private final File file;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public JsonGroceryDAO(String filePath) {
        this.file = new File(filePath);
    }

    @Override
    public GroceryList load() throws IOException {
        // Implémente le chargement de fichier pour le format json
        if (!file.exists()) {
            return new GroceryList();
        }
        return OBJECT_MAPPER.readValue(file, GroceryList.class);
    }

    @Override
    public void save(GroceryList list) throws IOException {
        // Sauvegarde sous format json
        OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(file, list);
    }
}