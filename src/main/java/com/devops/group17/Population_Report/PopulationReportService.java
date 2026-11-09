package com.devops.group17.Population_Report;
import com.devops.group17.utility.Utils;

import java.sql.Connection;
import java.util.*;

public class PopulationReportService {
    private final PopulationReportDAO populationReportDAO;

    public PopulationReportService(Connection con) {
        this.populationReportDAO = new PopulationReportDAO(con);
    }

    /**
     * R23: Fetches the continent population report data and prints it as a table.
     */
    public void displayContinentPopulationReport() {
        System.out.println("Display Continent Population report");
        printReport("Continent", populationReportDAO.getContinentPopulationReport());
    }

    /**
     * R24: Fetches the region population report data and prints it as a table.
     */
    public void displayRegionPopulationReport() {
        System.out.println("Display Region Population report");
        printReport("Region", populationReportDAO.getRegionPopulationReport());
    }

    /**
     * R25: Fetches the country population report data and prints it as a table.
     */
    public void displayCountryPopulationReport() {
        System.out.println("Display Country Population report");
        printReport("Country", populationReportDAO.getCountryPopulationReport());
    }

    /**
     * Converts List<PopulationReport> to List<Map<String, Object>> for generic printing.
     *
     * @param nameHeader column header for the name column (Continent, Region or Country)
     */
    private void printReport(String nameHeader, Optional<List<PopulationReport>> reports) {
        Optional<List<Map<String, Object>>> mapData = reports.map(list -> {
            List<Map<String, Object>> rows = new ArrayList<>();
            for (PopulationReport r : list) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put(nameHeader, r.getName());
                row.put("Total Population", r.getTotalPopulation());
                row.put("Population in Cities", r.getCityPopulation());
                row.put("Cities (%)", String.format("%.2f%%", r.getCityPercentage()));
                row.put("Population not in Cities", r.getNonCityPopulation());
                row.put("Not in Cities (%)", String.format("%.2f%%", r.getNonCityPercentage()));
                rows.add(row);
            }
            return rows;
        });

        Utils.printTable(mapData);
    }
}