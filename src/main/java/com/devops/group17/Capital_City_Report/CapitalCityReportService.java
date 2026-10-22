package com.devops.group17.Capital_City_Report;

import com.devops.group17.utility.Utils;

import java.sql.Connection;
import java.util.*;

/**
 * Service layer for the Capital City Reports (UC-17, UC-18, UC-19).
 * Validates input, fetches data from the DAO, converts it to map format,
 * and prints it via Utils.
 */
public class CapitalCityReportService {

    private final CapitalCityReportDAO capitalCityReportDAO;

    public CapitalCityReportService(Connection con) {
        this.capitalCityReportDAO = new CapitalCityReportDAO(con);
    }

    /**
     * UC-17: Fetches all capital cities in the world and prints them as a table.
     */
    public void displayWorldCapitalCities() {
        System.out.println("Display capital cities in the world");
        print(capitalCityReportDAO.getWorldCapitalCities(), null);
    }

    /**
     * UC-18: Fetches all capital cities in a continent and prints them as a table.
     *
     * @param continent the continent name (e.g. Asia)
     */
    public void displayContinentCapitalCities(String continent) {
        System.out.println("Display capital cities in continent: " + continent);
        if (isBlank(continent)) {
            System.out.println("Invalid input. Please enter a valid continent.");
            return;
        }
        print(capitalCityReportDAO.getContinentCapitalCities(continent.trim()), "continent");
    }

    /**
     * UC-19: Fetches all capital cities in a region and prints them as a table.
     *
     * @param region the region name (e.g. Western Europe)
     */
    public void displayRegionCapitalCities(String region) {
        System.out.println("Display capital cities in region: " + region);
        if (isBlank(region)) {
            System.out.println("Invalid input. Please enter a valid region.");
            return;
        }
        print(capitalCityReportDAO.getRegionCapitalCities(region.trim()), "region");
    }

    /**
     * Converts the DTOs to maps and prints them as a table.
     * Prints the use-case error messages when the query failed or returned no rows.
     *
     * @param reports result from the DAO
     * @param scope   "continent", "region", or null for the world report
     */
    private void print(Optional<List<CapitalCityReport>> reports, String scope) {
        if (reports.isEmpty()) {
            System.out.println("Database connection lost. Please check configuration.");
            return;
        }
        if (reports.get().isEmpty()) {
            if (scope != null) {
                System.out.println("No capital cities found for the specified " + scope
                        + ". Please enter a valid " + scope + ".");
            } else {
                System.out.println("No data available to display.");
            }
            return;
        }

        // Convert List<CapitalCityReport> to List<Map<String, Object>> for generic printing
        Optional<List<Map<String, Object>>> mapData = reports.map(list -> {
            List<Map<String, Object>> rows = new ArrayList<>();
            for (CapitalCityReport r : list) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("Name", r.getName());
                row.put("Country", r.getCountry());
                row.put("Population", r.getPopulation());
                rows.add(row);
            }
            return rows;
        });

        Utils.printTable(mapData);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}