package com.devops.group17.LanguageReport;

import com.devops.group17.utility.Utils;

import java.sql.Connection;
import java.util.*;

/**
 * Service layer for the Language Report.
 * Fetches data from DAO, converts to map format, and prints via Utils.
 */
public class LanguageReportService {

    private final LanguageReportDAO languageReportDAO;

    public LanguageReportService(Connection con) {
        this.languageReportDAO = new LanguageReportDAO(con);
    }


    /**
     * Fetches the language report data and prints it as a table.
     */
    public void displayLanguageReport() {
        Optional<List<LanguageReport>> reports = languageReportDAO.getLanguageReport();
        System.out.println("Display language report");
        // Convert List<LanguageReport> to List<Map<String, Object>> for generic printing
        Optional<List<Map<String, Object>>> mapData = reports.map(list -> {
            List<Map<String, Object>> rows = new ArrayList<>();
            for (LanguageReport r : list) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("Language", r.getLanguage());
                row.put("Total Speakers", r.getTotalSpeakers());
                row.put("World Percentage (%)", r.getWorldPercentage());
                rows.add(row);
            }
            return rows;
        });

        Utils.printTable(mapData);
    }
}
