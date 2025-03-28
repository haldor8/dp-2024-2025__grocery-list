package com.fges;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GroceryManagerTest {

    @TempDir
    Path tempDir;

    /**
     * Crée un fichier temporaire avec un contenu donné
     * @param content Contenu initial du fichier
     * @param extension Extension du fichier
     * @return Chemin du fichier temporaire créé
     * @throws IOException Si une erreur d'écriture survient
     */
    private Path createTempFile(String content, String extension) throws IOException {
        Path tempFile = tempDir.resolve("groceries." + extension);
        Files.writeString(tempFile, content);
        return tempFile;
    }

    @Test
    void testAjouterNouveauProduit() throws IOException {
        // Préparer un fichier JSON temporaire vide
        Path tempFile = createTempFile("{}", "json");

        // Créer un GroceryManager
        GroceryManager manager = new GroceryManager(tempFile.toString(), "json");

        // Ajouter un élément
        int result = manager.handleCommand("add", Arrays.asList("", "Milk", "10"));

        // Vérifier le résultat et le contenu du fichier
        assertEquals(0, result);

        // Lire le fichier pour vérification
        String fileContent = Files.readString(tempFile);
        assertTrue(fileContent.contains("\"Milk\":10"));
    }

    @Test
    void testAjouterProduitExistant() throws IOException {
        // Préparer un fichier JSON temporaire avec du contenu initial
        Path tempFile = createTempFile("{\"Milk\":5}", "json");

        // Créer un GroceryManager
        GroceryManager manager = new GroceryManager(tempFile.toString(), "json");

        // Ajouter un élément existant
        int result = manager.handleCommand("add", Arrays.asList("", "Milk", "10"));

        // Vérifier le résultat et le contenu du fichier
        assertEquals(0, result);

        // Lire le fichier pour vérification
        String fileContent = Files.readString(tempFile);
        assertTrue(fileContent.contains("\"Milk\":15"));
    }

    @Test
    void testSupprimerProduit() throws IOException {
        // Préparer un fichier JSON temporaire avec du contenu initial
        Path tempFile = createTempFile("{\"Milk\":10}", "json");

        // Créer un GroceryManager
        GroceryManager manager = new GroceryManager(tempFile.toString(), "json");

        // Supprimer un élément
        int result = manager.handleCommand("remove", Arrays.asList("", "Milk"));

        // Vérifier le résultat et le contenu du fichier
        assertEquals(0, result);

        // Lire le fichier pour vérification
        String fileContent = Files.readString(tempFile);
        assertEquals("{}", fileContent);
    }

    @Test
    void testSupprimerProduitInexistant() throws IOException {
        // Préparer un fichier JSON temporaire avec du contenu initial
        Path tempFile = createTempFile("{\"Eggs\":5}", "json");

        // Créer un GroceryManager
        GroceryManager manager = new GroceryManager(tempFile.toString(), "json");

        // Essayer de supprimer un produit inexistant
        int result = manager.handleCommand("remove", Arrays.asList("", "Milk"));

        // Vérifier le résultat (échec attendu)
        assertEquals(1, result);
    }

    @Test
    void testListerProduits() throws IOException {
        // Rediriger System.out pour capturer la sortie
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Préparer un fichier JSON temporaire avec du contenu
        Path tempFile = createTempFile("{\"Milk\":10,\"Eggs\":5}", "json");

        // Créer un GroceryManager
        GroceryManager manager = new GroceryManager(tempFile.toString(), "json");

        // Lister les produits
        int result = manager.handleCommand("list", Arrays.asList(""));

        // Vérifier le résultat et la sortie
        assertEquals(0, result);
        assertTrue(outContent.toString().contains("Milk: 10"));
        assertTrue(outContent.toString().contains("Eggs: 5"));

        // Réinitialiser System.out
        System.setOut(System.out);
    }

    @Test
    void testListerProduitsVide() throws IOException {
        // Rediriger System.out pour capturer la sortie
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Préparer un fichier JSON temporaire vide
        Path tempFile = createTempFile("{}", "json");

        // Créer un GroceryManager
        GroceryManager manager = new GroceryManager(tempFile.toString(), "json");

        // Lister les produits
        int result = manager.handleCommand("list", Arrays.asList(""));

        // Vérifier le résultat et la sortie
        assertEquals(0, result);

        // Récupérer la sortie et enlever les caractères de fin de ligne
        String output = outContent.toString().trim();

        // Vérifier le message exact
        assertEquals("The grocery list is empty.", output);

        // Réinitialiser System.out
        System.setOut(System.out);
    }

    @Test
    void testConversionJSONversCSV() throws IOException {
        // Préparer un fichier JSON temporaire avec du contenu
        Path tempJsonFile = createTempFile("{\"Milk\":10,\"Eggs\":5}", "json");

        // Créer un GroceryManager avec sortie CSV
        GroceryManager manager = new GroceryManager(tempJsonFile.toString(), "csv");

        // Ajouter un élément pour forcer la sauvegarde
        int result = manager.handleCommand("add", Arrays.asList("", "Bread", "3"));

        // Vérifier le résultat
        assertEquals(0, result);

        // Vérifier l'existence du fichier CSV
        Path csvFile = tempDir.resolve("groceries.csv");
        assertTrue(Files.exists(csvFile));

        // Vérifier le contenu du fichier CSV
        String csvContent = Files.readString(csvFile);
        assertTrue(csvContent.contains("name,quantity"));
        assertTrue(csvContent.contains("Milk,10"));
        assertTrue(csvContent.contains("Eggs,5"));
        assertTrue(csvContent.contains("Bread,3"));
    }

    @Test
    void testAjouterQuantiteInvalide() throws IOException {
        // Préparer un fichier JSON temporaire
        Path tempFile = createTempFile("{}", "json");

        // Créer un GroceryManager
        GroceryManager manager = new GroceryManager(tempFile.toString(), "json");

        // Essayer d'ajouter avec une quantité invalide
        int result = manager.handleCommand("add", Arrays.asList("", "Milk", "invalid"));

        // Vérifier le résultat (échec attendu)
        assertEquals(1, result);
    }
}