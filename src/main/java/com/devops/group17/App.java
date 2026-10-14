package com.devops.group17;

import com.devops.group17.CityReport.CityReportService;
import com.devops.group17.LanguageReport.LanguageReportService;

public class App {
    public static void main(String[] args) {

        // Get database connection
        java.sql.Connection con = Connection.getConnection();

        if (con != null) {
            try {
                // Create Service
                LanguageReportService languageReportService = new LanguageReportService(con);
                CityReportService cityReportService = new CityReportService(con);

                // Display the language report
                cityReportService.displayCityReport();
                languageReportService.displayLanguageReport();


            } finally {
                try {
                    con.close();
                } catch (Exception e) {
                    System.out.println("Error closing connection to database");
                }
            }
        } else {
            System.out.println("Failed to establish database connection after all retries.");
        }
    }
}