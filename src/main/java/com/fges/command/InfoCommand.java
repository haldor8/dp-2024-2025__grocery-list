package com.fges.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InfoCommand {

    /**
     * Exécute la commande info qui affiche des informations système
     */
    public int execute() {
        // Récupérer les informations système
        String osName = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");

        // Afficher la date du jour
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);
        System.out.println("Date: " + formattedDate);

        // Afficher le nom du système d'exploitation
        System.out.println("Operating System: " + osName);

        // Afficher la version de Java
        System.out.println("Java Version: " + javaVersion);

        return 0;
    }

    // Pour usage indépendant si nécessaire
    public static void main(String[] args) {
        String osName = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");

        System.out.println("Operating System: " + osName);
        System.out.println("Java Version: " + javaVersion);
    }
}