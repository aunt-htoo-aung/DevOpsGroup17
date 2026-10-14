package com.devops.group17.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Generic utility class with static helper methods.
 * Prints any Optional<List<Map<String, Object>>> data as a formatted console table.
 * Keys become headers, values become rows.
 */
public class Utils {

    private Utils() {
        // Prevent instantiation — utility class
    }

    /**
     * Prints the given data as a formatted table in the console.
     * Keys of the map are used as column headers.
     * Each map in the list represents one row.
     *
     * @param data Optional containing a list of maps to display
     */
    public static void printTable(Optional<List<Map<String, Object>>> data) {
        if (data == null || data.isEmpty() || data.get().isEmpty()) {
            System.out.println("No data available to display.");
            return;
        }

        List<Map<String, Object>> rows = data.get();

        // Get headers from the first row's keys
        List<String> headers = new ArrayList<>(rows.get(0).keySet());

        // Calculate column widths (max of header length vs value length)
        int[] widths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            widths[i] = headers.get(i).length();
        }
        for (Map<String, Object> row : rows) {
            int i = 0;
            for (String header : headers) {
                String val = String.valueOf(row.getOrDefault(header, ""));
                widths[i] = Math.max(widths[i], val.length());
                i++;
            }
        }

        // Build format string
        StringBuilder formatBuilder = new StringBuilder();
        for (int w : widths) {
            formatBuilder.append("%-").append(w + 2).append("s ");
        }
        String format = formatBuilder.toString();

        // Build separator
        int totalWidth = 0;
        for (int w : widths) {
            totalWidth += w + 3;
        }
        String separator = "-".repeat(totalWidth);

        // Print table
        System.out.println(separator);
        System.out.printf((format) + "%n", headers.toArray());
        System.out.println(separator);

        for (Map<String, Object> row : rows) {
            Object[] values = new Object[headers.size()];
            for (int i = 0; i < headers.size(); i++) {
                values[i] = row.getOrDefault(headers.get(i), "");
            }
            System.out.printf((format) + "%n", values);
        }

        System.out.println(separator);
    }
}
