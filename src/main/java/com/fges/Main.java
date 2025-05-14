package com.fges;

import org.apache.commons.cli.*;
import com.fges.cli.CLIParser;
import com.fges.cli.CLIParameters;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.exit(exec(args));
    }

    public static int exec(String[] args) throws IOException {
        // Check if "info" is present anywhere in the arguments
        if (containsInfoCommand(args)) {
            return new com.fges.command.InfoCommand().execute();
        }

        // Parse la commande pour l'exécuter
        CLIParser cliParser = new CLIParser();

        try {
            CLIParameters parameters = cliParser.parse(args);

            // For all other commands, we need a grocery manager
            GroceryManager manager = new GroceryManager(parameters.getFileName(), parameters.getOutputFormat());
            return manager.handleCommand(parameters.getCommand(), parameters.getPositionalArgs(), parameters.getCategory());
        } catch (ParseException ex) {
            System.err.println("Fail to parse arguments: " + ex.getMessage());
            return 1;
        }
    }

    /**
     * Check if any argument is "info"
     */
    private static boolean containsInfoCommand(String[] args) {
        for (String arg : args) {
            if (arg.equals("info")) {
                return true;
            }
        }
        return false;
    }
}