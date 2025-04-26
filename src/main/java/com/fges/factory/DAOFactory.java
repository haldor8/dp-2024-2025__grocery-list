package com.fges.factory;

import com.fges.dao.*;
import com.fges.util.FileNameFormatter;

public class DAOFactory {
    /**
     * Crée le DAO approprié selon le format spécifié
     */
    public static GroceryDAO createDAO(String fileName, String format) {
        String correctFileName = FileNameFormatter.reformatFileName(fileName, format);

        return format.equalsIgnoreCase("csv")
                ? new CsvGroceryDAO(correctFileName)
                : new JsonGroceryDAO(correctFileName);
    }
}