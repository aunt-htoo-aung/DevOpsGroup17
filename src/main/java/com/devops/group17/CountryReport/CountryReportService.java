
package com.devops.group17.CountryReport;

import com.devops.group17.utility.Utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service layer for the Country Report.
 * Fetches data from the DAO and displays the report.
 */
public class CountryReportService {

    private final CountryReportDAO countryReportDAO;

    public CountryReportService(Connection con) {
        this.countryReportDAO = new CountryReportDAO(con);
    }

    // UC-1: All countries worldwide
    public void displayAll() {
        // Display all Country Reports (UC-1 to UC-6)

            // UC-1: All countries worldwide
            displayCountryReport();

            // UC-2: All countries in Asia
            displayCountriesByContinent("Asia");

            // UC-3: All countries in Western Europe
            displayCountriesByRegion("Western Europe");

            // UC-4: Top 10 populated countries worldwide
            displayTopNWorldCountries(10);

            // UC-5: Top 5 populated countries in Europe
            displayTopNContinentCountries("Europe", 5);

            // UC-6: Top 5 populated countries in Western Europe
            displayTopNRegionCountries("Western Europe", 5);
        }


    public void displayCountryReport() {
        displayReport("All Countries Worldwide",
                countryReportDAO.getCountryReport());
    }

    // UC-2: All countries in a continent
    public void displayCountriesByContinent(String continent) {
        displayReport("Countries in " + continent,
                countryReportDAO.getCountriesByContinent(continent));
    }

    // UC-3: All countries in a region
    public void displayCountriesByRegion(String region) {
        displayReport("Countries in " + region,
                countryReportDAO.getCountriesByRegion(region));
    }

    // UC-4: Top N countries worldwide
    public void displayTopNWorldCountries(int limit) {
        displayReport("Top " + limit + " Populated Countries Worldwide",
                countryReportDAO.getTopNWorldCountries(limit));
    }

    // UC-5: Top N countries in a continent
    public void displayTopNContinentCountries(String continent, int limit) {
        displayReport("Top " + limit + " Countries in " + continent,
                countryReportDAO.getTopNContinentCountries(continent, limit));
    }

    // UC-6: Top N countries in a region
    public void displayTopNRegionCountries(String region, int limit) {
        displayReport("Top " + limit + " Countries in " + region,
                countryReportDAO.getTopNRegionCountries(region, limit));
    }

    // Common method to display country reports
    private void displayReport(
            String title, Optional<List<CountryReport>> reports) {

        System.out.println("\n" + title);

        Optional<List<Map<String, Object>>> mapData = reports.map(list -> {

            List<Map<String, Object>> rows = new ArrayList<>();

            for (CountryReport r : list) {

                Map<String, Object> row = new LinkedHashMap<>();

                row.put("Code", r.getCode());
                row.put("Name", r.getName());
                row.put("Continent", r.getContinent());
                row.put("Region", r.getRegion());
                row.put("Population", r.getPopulation());
                row.put("Capital", r.getCapital());

                rows.add(row);
            }

            return rows;
        });

        Utils.printTable(mapData);
    }
}
