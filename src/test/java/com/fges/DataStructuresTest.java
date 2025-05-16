package com.fges.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the data structure classes
 */
public class DataStructuresTest {

    private GroceryList groceryList;

    @BeforeEach
    void setUp() {
        groceryList = new GroceryList();
        groceryList.addItem(new Item("Milk", 2, "dairy"));
        groceryList.addItem(new Item("Bread", 1, "bakery"));
        groceryList.addItem(new Item("Cheese", 3, "dairy"));
    }

    @Test
    void testGroceryListAddItem() {
        // Act
        groceryList.addItem(new Item("Eggs", 12, "dairy"));

        // Assert
        assertEquals(4, groceryList.getListeObjets().size());
        assertEquals("Eggs", groceryList.getListeObjets().get(3).getName());
        assertEquals(12, groceryList.getListeObjets().get(3).getQuantity());
        assertEquals("dairy", groceryList.getListeObjets().get(3).getCategory());
    }

    @Test
    void testGroceryListRemoveItemByName() {
        // Act
        boolean result = groceryList.removeItemByName("Bread");

        // Assert
        assertTrue(result);
        assertEquals(2, groceryList.getListeObjets().size());
        assertEquals("Milk", groceryList.getListeObjets().get(0).getName());
        assertEquals("Cheese", groceryList.getListeObjets().get(1).getName());
    }

    @Test
    void testGroceryListRemoveNonExistingItem() {
        // Act
        boolean result = groceryList.removeItemByName("NonExisting");

        // Assert
        assertFalse(result);
        assertEquals(3, groceryList.getListeObjets().size());
    }

    @Test
    void testGroceryListFindElementIndex() {
        // Act & Assert
        assertEquals(0, groceryList.findElementIndex("Milk", "dairy"));
        assertEquals(1, groceryList.findElementIndex("Bread", "bakery"));
        assertEquals(2, groceryList.findElementIndex("Cheese", "dairy"));
        assertEquals(-1, groceryList.findElementIndex("NonExisting", "category"));
        assertEquals(-1, groceryList.findElementIndex("Milk", "wrong-category"));
    }

    @Test
    void testItemAddQuantity() {
        // Arrange
        Item item = new Item("Test", 5, "test-category");

        // Act
        item.addQuantity(3);

        // Assert
        assertEquals(8, item.getQuantity());
    }

    @Test
    void testItemSetters() {
        // Arrange
        Item item = new Item("Test", 5, "test-category");

        // Act
        item.setName("NewName");
        item.setQuantity(10);
        item.setCategory("new-category");

        // Assert
        assertEquals("NewName", item.getName());
        assertEquals(10, item.getQuantity());
        assertEquals("new-category", item.getCategory());
    }
}