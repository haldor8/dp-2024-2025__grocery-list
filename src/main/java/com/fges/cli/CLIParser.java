package com.fges.cli;

import org.apache.commons.cli.*;
import java.util.Arrays;
import java.util.List;

public class CLIParser {
    /**
     * Parse les arguments de la ligne de commande
     */
    public CLIParameters parse(String[] args) throws ParseException {
        // First, check if the command is "info" to determine if source is required
        boolean isInfoCommand = isInfoCommand(args);

        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();

        // Set source as required only if it's not an info command
        if (!isInfoCommand) {
            cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");
        } else {
            cliOptions.addOption("s", "source", true, "File containing the grocery list");
        }

        cliOptions.addOption("f", "format", true, "File format (json or csv)");
        cliOptions.addOption("c", "category", true, "Specify the category of the item");

        CommandLine cmd = parser.parse(cliOptions, args);

        String fileName = cmd.getOptionValue("s", ""); // Empty string as default if not provided
        String outputFormat = cmd.getOptionValue("f", "json"); // JSON by default if not given
        String category = cmd.getOptionValue("c", "default"); // "default" if not specified

        List<String> positionalArgs = cmd.getArgList();
        if (positionalArgs.isEmpty()) {
            throw new ParseException("Missing Command");
        }

        String command = positionalArgs.get(0);

        return new CLIParameters(fileName, outputFormat, category, command, positionalArgs);
    }

    /**
     * Determine if the command is "info" based on positional arguments
     * Returns true if "info" is found anywhere in the arguments
     */
    private boolean isInfoCommand(String[] args) {
        for (String arg : args) {
            if (arg.equals("info")) {
                return true;
            }
        }
        return false;
    }
}