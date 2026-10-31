package com.devops.group17;

import com.devops.group17.CityReport.CityReportService;
import com.devops.group17.CountryReport.CountryReportService;
import com.devops.group17.LanguageReport.LanguageReportService;
import com.devops.group17.Capital_City_Report.CapitalCityReportService;

public class App {
    public static void main(String[] args) {

        // Get database connection
        java.sql.Connection con = Connection.getConnection();

        if (con != null) {
            try {
                // Create Service
                LanguageReportService languageReportService = new LanguageReportService(con);
                CityReportService cityReportService = new CityReportService(con);
                CapitalCityReportService capitalCityReportService = new CapitalCityReportService(con);
                CountryReportService countryReportService = new CountryReportService(con);

                // Display the language report
                //cityReportService.displayCityReport();
                //languageReportService.displayLanguageReport();
                countryReportService.displayAll();

                // Display the language report
                cityReportService.displayCityReport();
                languageReportService.displayLanguageReport();

                capitalCityReportService.displayWorldCapitalCities();                    // UC-17
                capitalCityReportService.displayContinentCapitalCities("Asia");          // UC-18
                capitalCityReportService.displayRegionCapitalCities("Western Europe");   // UC-19
                capitalCityReportService.displayTopNWorldCapitalCities(10);                    // UC-20
                capitalCityReportService.displayTopNContinentCapitalCities("Europe", 5);       // UC-21
                capitalCityReportService.displayTopNRegionCapitalCities("Western Europe", 5);  // UC-22

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