package com.fges.util;

public class FileNameFormatter {
    /**
     * Garantit le bon format de fichier même si l'utilisateur rentre des extensions factices ou aucune
     */
    public static String reformatFileName(String fileName, String format) {
        String baseName = fileName.replaceAll("\\.[^.]+$", "");
        return baseName + "." + format;
    }
}