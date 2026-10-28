package com.devops.group17.PopulationLookupReport;

import com.devops.group17.utility.Utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service layer for the Population Lookup Report.
 * Fetches data from DAO, converts to map format, and prints via Utils.
 */
public class PopulationLookupReportService {

    private final PopulationLookupReportDAO populationLookupReportDAO;

    public PopulationLookupReportService(Connection con) {
        this.populationLookupReportDAO = new PopulationLookupReportDAO(con);
    }

    /**
     * Executes a population lookup report by keyword and displays results
     *
     * @param searchTerm The keyword (city, country, continent, or region) to look up.
     */
    public void displayPopulationLookupReport(String searchTerm) {
        System.out.println("Displaying population lookup report for keyword: '" + searchTerm + "'");

        Optional<List<PopulationLookupReport>> reports = populationLookupReportDAO.lookupPopulationByName(searchTerm);

        Optional<List<Map<String, Object>>> mapData = reports.map(list -> {
            List<Map<String, Object>> rows = new ArrayList<>();
            for (PopulationLookupReport r : list) {
                Map<String, Object> row = new LinkedHashMap<>();

                // DIO Fields
                row.put("Name", r.getName());
                row.put("City", r.getCity());
                row.put("District", r.getDistrict());
                row.put("Country", r.getCountry());
                row.put("Country Code", r.getCountryCode());
                row.put("Continent", r.getContinent());
                row.put("Region", r.getRegion());
                row.put("Population", r.getPopulation());

                rows.add(row);
            }
            return rows;
        });

        Utils.printTable(mapData);
    }
}
