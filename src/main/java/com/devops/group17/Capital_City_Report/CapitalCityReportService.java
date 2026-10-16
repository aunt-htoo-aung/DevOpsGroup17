package com.devops.group17.Capital_City_Report;

import com.devops.group17.utility.Utils;

import java.sql.Connection;
import java.util.*;

/**
 * Service layer for the Capital City Report (UC-17).
 * Fetches data from the DAO, converts it to map format, and prints it via Utils.
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
        Optional<List<CapitalCityReport>> reports = capitalCityReportDAO.getWorldCapitalCities();
        System.out.println("Display capital cities in the world");

        if (reports.isEmpty()) {
            System.out.println("Database connection lost. Please check configuration.");
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
}
