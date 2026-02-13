package com.airtribe.meditrack.util;

import java.io.*;
import java.util.List;

/**
 * CSVUtil utility class for reading and writing CSV files.
 * Handles serialization and deserialization of entities to/from CSV format.
 */
public class CSVUtil {
    
    private static final String CSV_DELIMITER = ",";
    private static final String QUOTE = "\"";
    private static final String NEWLINE = "\n";
    
    /**
     * Write data to CSV file.
     *
     * @param filePath the file path
     * @param content  the content to write
     * @throws IOException if I/O error occurs
     */
    public static void writeToCsv(String filePath, String content) throws IOException {
        createFileIfNotExists(filePath);
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(content).append(NEWLINE);
        }
    }
    
    /**
     * Read all content from a CSV file.
     *
     * @param filePath the file path
     * @return the file content as string
     * @throws IOException if I/O error occurs
     */
    public static String readFromCsv(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);
        
        if (!file.exists()) {
            return content.toString();
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(NEWLINE);
            }
        }
        return content.toString();
    }
    
    /**
     * Create CSV file if it doesn't exist.
     *
     * @param filePath the file path
     * @throws IOException if I/O error occurs
     */
    public static void createFileIfNotExists(String filePath) throws IOException {
        File file = new File(filePath);
        File dir = file.getParentFile();
        
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        
        if (!file.exists()) {
            file.createNewFile();
        }
    }
    
    /**
     * Clear CSV file content.
     *
     * @param filePath the file path
     * @throws IOException if I/O error occurs
     */
    public static void clearCsv(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("");
        }
    }
    
    /**
     * Escape special characters in CSV values.
     *
     * @param value the value to escape
     * @return the escaped value
     */
    public static String escapeCSVValue(String value) {
        if (value == null) {
            return "";
        }
        
        if (value.contains(CSV_DELIMITER) || value.contains(QUOTE) || value.contains("\n")) {
            return QUOTE + value.replace(QUOTE, QUOTE + QUOTE) + QUOTE;
        }
        return value;
    }
    
    /**
     * Parse CSV line into array of values.
     *
     * @param line the CSV line to parse
     * @return array of values
     */
    public static String[] parseCsvLine(String line) {
        List<String> values = new java.util.ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (c == ',' && !insideQuotes) {
                values.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        
        values.add(current.toString().trim());
        return values.toArray(new String[0]);
    }
    
    // Private constructor to prevent instantiation
    private CSVUtil() {
        throw new AssertionError("CSVUtil class cannot be instantiated");
    }
}
