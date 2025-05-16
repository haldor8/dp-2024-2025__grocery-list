package com.fges.factory;

import com.fges.dao.CsvGroceryDAO;
import com.fges.dao.GroceryDAO;
import com.fges.dao.JsonGroceryDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the DAO Factory class following the Factory pattern
 */
public class DAOFactoryTest {

    @Test
    void testCreateJsonDAO() {
        // Act
        GroceryDAO dao = DAOFactory.createDAO("test", "json");

        // Assert
        assertTrue(dao instanceof JsonGroceryDAO);
    }

    @Test
    void testCreateCsvDAO() {
        // Act
        GroceryDAO dao = DAOFactory.createDAO("test", "csv");

        // Assert
        assertTrue(dao instanceof CsvGroceryDAO);
    }

    @Test
    void testCreateDAOWithCaseInsensitiveFormat() {
        // Act
        GroceryDAO dao = DAOFactory.createDAO("test", "CSV");

        // Assert
        assertTrue(dao instanceof CsvGroceryDAO);
    }

    @Test
    void testFileNameFormatting() {
        // Act
        GroceryDAO jsonDao = DAOFactory.createDAO("test.txt", "json");
        GroceryDAO csvDao = DAOFactory.createDAO("test.json", "csv");

        // Assert
        // We can't directly test the file path as it's private, but we can check the class type
        assertTrue(jsonDao instanceof JsonGroceryDAO);
        assertTrue(csvDao instanceof CsvGroceryDAO);
    }
}