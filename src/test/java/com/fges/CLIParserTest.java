package com.fges.cli;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Command Line Interface parser
 */
public class CLIParserTest {

    private CLIParser cliParser;

    @BeforeEach
    void setUp() {
        cliParser = new CLIParser();
    }

    @Test
    void testParseWithSourceAndFormat() throws ParseException {
        // Arrange
        String[] args = {"-s", "test.json", "-f", "json", "add", "Milk", "2"};

        // Act
        CLIParameters parameters = cliParser.parse(args);

        // Assert
        assertEquals("test.json", parameters.getFileName());
        assertEquals("json", parameters.getOutputFormat());
        assertEquals("default", parameters.getCategory());
        assertEquals("add", parameters.getCommand());
        assertEquals(3, parameters.getPositionalArgs().size());
        assertEquals("add", parameters.getPositionalArgs().get(0));
        assertEquals("Milk", parameters.getPositionalArgs().get(1));
        assertEquals("2", parameters.getPositionalArgs().get(2));
    }

    @Test
    void testParseWithCategory() throws ParseException {
        // Arrange
        String[] args = {"-s", "test.json", "-c", "dairy", "add", "Milk", "2"};

        // Act
        CLIParameters parameters = cliParser.parse(args);

        // Assert
        assertEquals("test.json", parameters.getFileName());
        assertEquals("json", parameters.getOutputFormat()); // Default value
        assertEquals("dairy", parameters.getCategory());
        assertEquals("add", parameters.getCommand());
    }

    @Test
    void testParseWithCsvFormat() throws ParseException {
        // Arrange
        String[] args = {"-s", "test.csv", "-f", "csv", "list"};

        // Act
        CLIParameters parameters = cliParser.parse(args);

        // Assert
        assertEquals("test.csv", parameters.getFileName());
        assertEquals("csv", parameters.getOutputFormat());
        assertEquals("list", parameters.getCommand());
    }

    @Test
    void testParseInfoCommand() throws ParseException {
        // Arrange
        String[] args = {"info"};

        // Act
        CLIParameters parameters = cliParser.parse(args);

        // Assert
        assertEquals("", parameters.getFileName()); // Empty since source is not required for info
        assertEquals("json", parameters.getOutputFormat()); // Default value
        assertEquals("info", parameters.getCommand());
    }

    @Test
    void testParseInfoCommandWithSource() throws ParseException {
        // Arrange
        String[] args = {"-s", "test.json", "info"};

        // Act
        CLIParameters parameters = cliParser.parse(args);

        // Assert
        assertEquals("test.json", parameters.getFileName());
        assertEquals("json", parameters.getOutputFormat());
        assertEquals("info", parameters.getCommand());
    }

    @Test
    void testMissingSourceForNonInfoCommand() {
        // Arrange
        String[] args = {"add", "Milk", "2"};

        // Act & Assert
        assertThrows(ParseException.class, () -> cliParser.parse(args));
    }

    @Test
    void testMissingCommand() {
        // Arrange
        String[] args = {"-s", "test.json"};

        // Act & Assert
        assertThrows(ParseException.class, () -> cliParser.parse(args));
    }
}