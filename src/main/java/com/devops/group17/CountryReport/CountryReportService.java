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
 * Fetches data from the DAO, converts it to a generic map format,
 * and prints the report as a table.
 */
public class CountryReportService {

    private final CountryReportDAO countryReportDAO;

    public CountryReportService(Connection con) {
        this.countryReportDAO = new CountryReportDAO(con);
    }
    public void displayAll(){
        displayCountryReport();
    }
    /**
     * Fetches the country report data and prints it as a table.
     */
    public void displayCountryReport() {
        Optional<List<CountryReport>> reports =
                countryReportDAO.getCountryReport();

        System.out.println("Display country report");

        // Convert List<CountryReport> to List<Map<String, Object>>
        // for generic table printing.
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