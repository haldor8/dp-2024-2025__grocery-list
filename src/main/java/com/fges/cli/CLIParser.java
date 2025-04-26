package com.fges.cli;

import org.apache.commons.cli.*;
import java.util.List;

public class CLIParser {
    /**
     * Parse les arguments de la ligne de commande
     */
    public CLIParameters parse(String[] args) throws ParseException {
        Options cliOptions = new Options();
        CommandLineParser parser = new DefaultParser();

        cliOptions.addRequiredOption("s", "source", true, "File containing the grocery list");
        cliOptions.addOption("f", "format", true, "File format (json or csv)");
        cliOptions.addOption("c", "category", true, "Specify the category of the item");

        CommandLine cmd = parser.parse(cliOptions, args);

        String fileName = cmd.getOptionValue("s");
        String outputFormat = cmd.getOptionValue("f", "json"); // Par défaut JSON si non précisé
        String category = cmd.getOptionValue("c", "default"); // "default" si non spécifié

        List<String> positionalArgs = cmd.getArgList();
        if (positionalArgs.isEmpty()) {
            throw new ParseException("Missing Command");
        }

        String command = positionalArgs.get(0);

        return new CLIParameters(fileName, outputFormat, category, command, positionalArgs);
    }
}