package com.fges;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws IOException {
        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();

        cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("o", "output", true, "Output format (json or csv)");

        CommandLine cmd;
        try {
            cmd = parser.parse(cliOptions, args);
        } catch (ParseException ex) {
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        }

        String fileName = cmd.getOptionValue("s");
        String outputFormat = cmd.getOptionValue("o", "json"); // Par défaut JSON si non précisé

        List<String> positionalArgs = cmd.getArgList();
        if (positionalArgs.isEmpty()) {
            System.err.println("Missing Command");
            return 1;
        }

        String command = positionalArgs.get(0);
        GroceryManager manager = new GroceryManager(fileName, outputFormat);

        // Tous les managers devront utiliser la même méthode, potentiellement ajouter de l'héritage vers un Manager général
        return manager.handleCommand(command, positionalArgs);
    }
}
