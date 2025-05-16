package com.fges.util;

import com.fges.data_structures.Item;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for utility classes
 */
public class UtilsTest {

    @Test
    void testFileNameFormatter() {
        // Act & Assert
        assertEquals("test.json", FileNameFormatter.reformatFileName("test", "json"));
        assertEquals("test.csv", FileNameFormatter.reformatFileName("test.txt", "csv"));
        assertEquals("test.json", FileNameFormatter.reformatFileName("test.csv", "json"));
        assertEquals("path/to/file.json", FileNameFormatter.reformatFileName("path/to/file.txt", "json"));
    }

    @Test
    void testCategoryExtractorWithEmptyList() {
        // Arrange
        List<Item> items = new ArrayList<>();
        CategoryExtractor extractor = new CategoryExtractor(items);

        // Act
        List<String> categories = extractor.extractCategories();

        // Assert
        assertNotNull(categories);
        assertEquals(0, categories.size());
    }

    @Test
    void testCategoryExtractorWithItems() {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(new Item("Milk", 2, "dairy"));
        items.add(new Item("Cheese", 1, "dairy"));
        items.add(new Item("Bread", 1, "bakery"));
        items.add(new Item("Yogurt", 3, "dairy"));
        items.add(new Item("Apple", 5, "fruits"));

        CategoryExtractor extractor = new CategoryExtractor(items);

        // Act
        List<String> categories = extractor.extractCategories();

        // Assert
        assertEquals(3, categories.size());
        assertTrue(categories.contains("dairy"));
        assertTrue(categories.contains("bakery"));
        assertTrue(categories.contains("fruits"));
    }
}