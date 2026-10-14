package com.devops.group17.CityReport;
import com.devops.group17.utility.Utils;

import java.sql.Connection;
import java.util.*;

public class CityReportService {
    private final CityReportDAO cityReportDAO;

    public CityReportService(Connection con) {
        this.cityReportDAO = new CityReportDAO(con);
    }

    /**
     * Fetches the City report data and prints it as a table.
     */
    public void displayCityReport() {
        Optional<List<CityReport>> reports = cityReportDAO.getCityReport();
        System.out.println("Display City report");
        // Convert List<CityReport> to List<Map<String, Object>> for generic printing
        Optional<List<Map<String, Object>>> mapData = reports.map(list -> {
            List<Map<String, Object>> rows = new ArrayList<>();
            for (CityReport r : list) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("City Name", r.getName());
                row.put("Country", r.getCountry());
                row.put("District", r.getDistrict());
                row.put("Population", r.getPopulation());
                rows.add(row);
            }
            return rows;
        });

        Utils.printTable(mapData);
    }
}
