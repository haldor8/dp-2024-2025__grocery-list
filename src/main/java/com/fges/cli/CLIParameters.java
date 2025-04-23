package com.fges.cli;

import java.util.List;

public class CLIParameters {
    private final String fileName;
    private final String outputFormat;
    private final String category;
    private final String command;
    private final List<String> positionalArgs;

    public CLIParameters(String fileName, String outputFormat, String category,
                         String command, List<String> positionalArgs) {
        this.fileName = fileName;
        this.outputFormat = outputFormat;
        this.category = category;
        this.command = command;
        this.positionalArgs = positionalArgs;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public String getCategory() {
        return category;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getPositionalArgs() {
        return positionalArgs;
    }
}