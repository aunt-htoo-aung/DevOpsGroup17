package com.devops.group17.PopulationLookupReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for the Population Lookup Report.
 * Executes queries to look up population data including city, country, district,
 * continent, and region, returning results as a list of DTOs.
 */
public class PopulationLookupReportDAO {

    private final Connection con;

    public PopulationLookupReportDAO(Connection con) {
        this.con = con;
    }

    public Optional<List<PopulationLookupReport>> lookupPopulationByName(String searchTerm) {
        List<PopulationLookupReport> reports = new ArrayList<>();

        String query = "SELECT ci.Name AS CityName, co.Name AS CountryName, ci.District, " +
                "ci.CountryCode, ci.Population, co.Continent, co.Region " +
                "FROM city ci " +
                "JOIN country co ON ci.CountryCode = co.Code " +
                "WHERE ci.Name LIKE ? OR co.Name LIKE ? OR co.Continent LIKE ? OR co.Region LIKE ? " +
                "ORDER BY ci.Population DESC";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            String wildcardSearch = "%" + searchTerm + "%";

            // Wild Card Searches
            for (int i = 1; i <= 4; i++) {
                pstmt.setString(i, wildcardSearch);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PopulationLookupReport report = new PopulationLookupReport();

                    // DIO Settlers
                    report.setName(rs.getString("CityName"));
                    report.setCity(rs.getString("CityName"));
                    report.setCountry(rs.getString("CountryName"));
                    report.setDistrict(rs.getString("District"));
                    report.setCountryCode(rs.getString("CountryCode"));
                    report.setPopulation(rs.getLong("Population"));
                    report.setContinent(rs.getString("Continent"));
                    report.setRegion(rs.getString("Region"));

                    reports.add(report);
                }
            }
            return Optional.of(reports);

            // ERROR HANDLING
        } catch (Exception e) {
            System.out.println("Failed to execute population lookup query");
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
