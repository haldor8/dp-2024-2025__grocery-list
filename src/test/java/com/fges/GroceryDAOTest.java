package com.fges.dao;

import com.fges.data_structures.GroceryList;
import com.fges.data_structures.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the DAO implementation classes following the Data Access Object pattern
 */
public class GroceryDAOTest {

    @TempDir
    Path tempDir;

    private String jsonFilePath;
    private String csvFilePath;
    private GroceryDAO jsonDAO;
    private GroceryDAO csvDAO;

    @BeforeEach
    void setUp() {
        jsonFilePath = tempDir.resolve("test.json").toString();
        csvFilePath = tempDir.resolve("test.csv").toString();
        jsonDAO = new JsonGroceryDAO(jsonFilePath);
        csvDAO = new CsvGroceryDAO(csvFilePath);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(jsonFilePath));
        Files.deleteIfExists(Path.of(csvFilePath));
    }

    @Test
    void testJsonSaveAndLoad() throws IOException {
        // Arrange
        GroceryList originalList = new GroceryList();
        originalList.addItem(new Item("Milk", 2, "dairy"));
        originalList.addItem(new Item("Bread", 1, "bakery"));

        // Act
        jsonDAO.save(originalList);
        GroceryList loadedList = jsonDAO.load();

        // Assert
        assertEquals(2, loadedList.getListeObjets().size());
        assertEquals("Milk", loadedList.getListeObjets().get(0).getName());
        assertEquals(2, loadedList.getListeObjets().get(0).getQuantity());
        assertEquals("dairy", loadedList.getListeObjets().get(0).getCategory());

        assertEquals("Bread", loadedList.getListeObjets().get(1).getName());
        assertEquals(1, loadedList.getListeObjets().get(1).getQuantity());
        assertEquals("bakery", loadedList.getListeObjets().get(1).getCategory());
    }

    @Test
    void testCsvSaveAndLoad() throws IOException {
        // Arrange
        GroceryList originalList = new GroceryList();
        originalList.addItem(new Item("Milk", 2, "dairy"));
        originalList.addItem(new Item("Bread", 1, "bakery"));

        // Act
        csvDAO.save(originalList);
        GroceryList loadedList = csvDAO.load();

        // Assert
        assertEquals(2, loadedList.getListeObjets().size());
        assertEquals("Milk", loadedList.getListeObjets().get(0).getName());
        assertEquals(2, loadedList.getListeObjets().get(0).getQuantity());
        assertEquals("dairy", loadedList.getListeObjets().get(0).getCategory());

        assertEquals("Bread", loadedList.getListeObjets().get(1).getName());
        assertEquals(1, loadedList.getListeObjets().get(1).getQuantity());
        assertEquals("bakery", loadedList.getListeObjets().get(1).getCategory());
    }

    @Test
    void testLoadFromNonExistentFile() throws IOException {
        // Act
        GroceryList list = jsonDAO.load();

        // Assert
        assertNotNull(list);
        assertEquals(0, list.getListeObjets().size());
    }
}