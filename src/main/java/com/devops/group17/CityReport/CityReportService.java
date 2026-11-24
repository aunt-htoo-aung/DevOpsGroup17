package com.devops.group17.CityReport;

import com.devops.group17.utility.Utils;

import java.sql.Connection;
import java.util.*;

public class CityReportService {
    private final CityReportDAO cityReportDAO;

    public CityReportService(Connection con) {
        this.cityReportDAO = new CityReportDAO(con);
    }

    private void printReport(Optional<List<CityReport>> reports, String reportTitle) {
        System.out.println("--- " + reportTitle + " ---");

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

    public void displayAll() {
        displayCityReport();
    }

    // [Req 7] All the cities in the world
    public void displayCityReport() {
        printReport(cityReportDAO.getCityReport(), "All Cities in the World");
    }

    // [Req 8] All the cities in a continent
    public void displayCityReportByContinent(String continent) {
        printReport(cityReportDAO.getCityReportByContinent(continent), "Cities in Continent: " + continent);
    }

    // [Req 9] All the cities in a region
    public void displayCityReportByRegion(String region) {
        printReport(cityReportDAO.getCityReportByRegion(region), "Cities in Region: " + region);
    }

    // [Req 10] All the cities in a country
    public void displayCityReportByCountry(String country) {
        printReport(cityReportDAO.getCityReportByCountry(country), "Cities in Country: " + country);
    }

    // [Req 11] All the cities in a district
    public void displayCityReportByDistrict(String district) {
        printReport(cityReportDAO.getCityReportByDistrict(district), "Cities in District: " + district);
    }

    // [Req 12] Top N populated cities in the world
    public void displayTopNWorldCities(int n) {
        printReport(cityReportDAO.getTopNWorldCities(n), "Top " + n + " Cities in the World");
    }

    // [Req 13] Top N populated cities in a continent
    public void displayTopNContinentCities(String continent, int n) {
        printReport(cityReportDAO.getTopNContinentCities(continent, n), "Top " + n + " Cities in Continent: " + continent);
    }

    // [Req 14] Top N populated cities in a region
    public void displayTopNRegionCities(String region, int n) {
        printReport(cityReportDAO.getTopNRegionCities(region, n), "Top " + n + " Cities in Region: " + region);
    }

    // [Req 15] Top N populated cities in a country
    public void displayTopNCountryCities(String country, int n) {
        printReport(cityReportDAO.getTopNCountryCities(country, n), "Top " + n + " Cities in Country: " + country);
    }

    // [Req 16] Top N populated cities in a district
    public void displayTopNDistrictCities(String district, int n) {
        printReport(cityReportDAO.getTopNDistrictCities(district, n), "Top " + n + " Cities in District: " + district);
    }
}