package com.fges;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws IOException {
        // Parse la commande pour l'exécuter

        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();

        cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("f", "format", true, "File format (json or csv)");
        cliOptions.addOption("c", "category", true, "Specify the category of the item");

        CommandLine cmd;
        try {
            cmd = parser.parse(cliOptions, args);
        } catch (ParseException ex) {
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        }

        String fileName = cmd.getOptionValue("s");
        String outputFormat = cmd.getOptionValue("f", "json"); // Par défaut JSON si non précisé
        String category = cmd.getOptionValue("c", "default"); // "default" si non spécifié

        List<String> positionalArgs = cmd.getArgList();
        if (positionalArgs.isEmpty()) {
            System.err.println("Missing Command");
            return 1;
        }

        String command = positionalArgs.get(0);
        GroceryManager manager = new GroceryManager(fileName, outputFormat);

        return manager.handleCommand(command, positionalArgs, category);
    }

}
