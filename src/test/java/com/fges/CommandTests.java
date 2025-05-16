package com.fges.command;

import com.fges.dao.GroceryDAO;
import com.fges.dao.JsonGroceryDAO;
import com.fges.data_structures.GroceryList;
import com.fges.data_structures.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests pour les classes de commande
 */
public class CommandTests {

    private GroceryList groceryList;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @TempDir
    Path tempDir;
    private GroceryDAO dao;

    @BeforeEach
    void setUp() throws IOException {
        // Créer une liste avec quelques éléments pour les tests
        groceryList = new GroceryList();
        groceryList.addItem(new Item("Milk", 2, "dairy"));
        groceryList.addItem(new Item("Bread", 1, "bakery"));

        // Configuration pour capturer la sortie standard
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Créer un DAO concret pour les tests
        String jsonFilePath = tempDir.resolve("test.json").toString();
        dao = new JsonGroceryDAO(jsonFilePath);
    }

    @AfterEach
    void tearDown() {
        // Restaurer le flux de sortie standard original
        System.setOut(originalOut);
    }

    // Tests pour AddItemCommand

    @Test
    void testAddItemCommandAddNewItem() throws IOException {
        // Arrange
        AddItemCommand command = new AddItemCommand(dao, groceryList);

        // Act
        int result = command.execute("Eggs", "12", "dairy");

        // Assert
        assertEquals(0, result);
        int itemIndex = groceryList.findElementIndex("Eggs", "dairy");
        assertNotEquals(-1, itemIndex);
        assertEquals(12, groceryList.getListeObjets().get(itemIndex).getQuantity());
    }

    @Test
    void testAddItemCommandInvalidQuantity() throws IOException {
        // Arrange
        AddItemCommand command = new AddItemCommand(dao, groceryList);

        // Act
        int result = command.execute("Orange", "not-a-number", "fruits");

        // Assert
        assertEquals(1, result);
        assertEquals(-1, groceryList.findElementIndex("Orange", "fruits"));
    }

    @Test
    void testAddItemCommandIncreaseQuantity() throws IOException {
        // Arrange
        AddItemCommand command = new AddItemCommand(dao, groceryList);
        int initialQuantity = groceryList.getListeObjets().get(0).getQuantity();

        // Act
        int result = command.execute("Milk", "3", "dairy");

        // Assert
        assertEquals(0, result);
        assertEquals(initialQuantity + 3, groceryList.getListeObjets().get(0).getQuantity());
    }

    // Tests pour RemoveItemCommand

    @Test
    void testRemoveItemCommandSuccess() throws IOException {
        // Arrange
        RemoveItemCommand command = new RemoveItemCommand(dao, groceryList);
        int initialSize = groceryList.getListeObjets().size();

        // Act
        int result = command.execute("Bread");

        // Assert
        assertEquals(0, result);
        assertEquals(initialSize - 1, groceryList.getListeObjets().size());
        assertEquals(-1, groceryList.findElementIndex("Bread", "bakery"));
    }

    @Test
    void testRemoveItemCommandNotFound() throws IOException {
        // Arrange
        RemoveItemCommand command = new RemoveItemCommand(dao, groceryList);
        int initialSize = groceryList.getListeObjets().size();

        // Act
        int result = command.execute("NonExistingItem");

        // Assert
        assertEquals(1, result);
        assertEquals(initialSize, groceryList.getListeObjets().size());
    }

    // Tests pour ListItemsCommand

    @Test
    void testListItemsCommand() {
        // Reset output stream to ensure clean test
        outputStream.reset();

        // Arrange
        ListItemsCommand command = new ListItemsCommand(groceryList);

        // Act
        int result = command.execute();

        // Assert
        assertEquals(0, result);
        String output = outputStream.toString();
        // Vérifier que la sortie n'est pas vide
        assertFalse(output.isEmpty());
    }

    // Tests pour InfoCommand

    @Test
    void testInfoCommand() {
        // Reset output stream to ensure clean test
        outputStream.reset();

        // Arrange
        InfoCommand command = new InfoCommand();

        // Act
        int result = command.execute();

        // Assert
        assertEquals(0, result);
        String output = outputStream.toString();
        // Vérifier que la sortie n'est pas vide
        assertFalse(output.isEmpty());
        assertTrue(output.contains("Java Version:"));
    }

    // Tests pour CommandHandler

    @Test
    void testCommandHandlerAddCommand() throws IOException {
        // Arrange
        CommandHandler handler = new CommandHandler(dao);
        List<String> args = Arrays.asList("add", "Eggs", "12");

        // Act
        int result = handler.handleCommand("add", args, "dairy");

        // Assert
        assertEquals(0, result);
    }

    @Test
    void testCommandHandlerAddCommandMissingArgs() throws IOException {
        // Arrange
        CommandHandler handler = new CommandHandler(dao);
        List<String> args = Arrays.asList("add");

        // Act
        int result = handler.handleCommand("add", args, "dairy");

        // Assert
        assertEquals(1, result);
    }

    @Test
    void testCommandHandlerRemoveCommand() throws IOException {
        // Arrange
        CommandHandler handler = new CommandHandler(dao);

        // Créer et sauvegarder une nouvelle liste
        GroceryList list = new GroceryList();
        list.addItem(new Item("Milk", 2, "dairy"));
        dao.save(list);

        // Recréer le handler pour qu'il charge la liste sauvegardée
        handler = new CommandHandler(dao);

        List<String> args = Arrays.asList("remove", "Milk");

        // Act
        int result = handler.handleCommand("remove", args, "dairy");

        // Assert
        assertEquals(0, result);
    }

    @Test
    void testCommandHandlerListCommand() throws IOException {
        // Reset output stream to ensure clean test
        outputStream.reset();

        // Arrange
        CommandHandler handler = new CommandHandler(dao);
        List<String> args = Arrays.asList("list");

        // Save a grocery list first with items to display
        dao.save(groceryList);

        // Act
        int result = handler.handleCommand("list", args, "default");

        // Assert
        assertEquals(0, result);
    }

    @Test
    void testCommandHandlerInfoCommand() throws IOException {
        // Reset output stream to ensure clean test
        outputStream.reset();

        // Arrange
        CommandHandler handler = new CommandHandler(dao);
        List<String> args = Arrays.asList("info");

        // Act
        int result = handler.handleCommand("info", args, "default");

        // Assert
        assertEquals(0, result);
        // Nous vérifions seulement que la commande a réussi
    }

    @Test
    void testCommandHandlerUnknownCommand() throws IOException {
        // Arrange
        CommandHandler handler = new CommandHandler(dao);
        List<String> args = Arrays.asList("unknown");

        // Act
        int result = handler.handleCommand("unknown", args, "default");

        // Assert
        assertEquals(1, result);
    }

    // Test pour WebCommand (limité sans serveur réel)
    @Test
    void testWebCommandConstruction() {
        // Arrange
        WebCommand command = new WebCommand();

        // Assert
        assertNotNull(command);
        // Note: Testing execute() would start a server, which is not ideal for unit tests
    }
}