package com.devops.group17.CityReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityReportDAO {
    private final Connection con;

    public CityReportDAO(Connection con) {
        this.con = con;
    }

    // Helper method to execute queries with parameters safely using PreparedStatement
    private Optional<List<CityReport>> executeQuery(String query, Object... params) {
        List<CityReport> reports = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CityReport report = new CityReport();
                    report.setName(rs.getString("City"));
                    report.setCountry(rs.getString("Country"));
                    report.setDistrict(rs.getString("District"));
                    report.setPopulation(rs.getLong("Population"));
                    reports.add(report);
                }
            }
            return Optional.of(reports);
        } catch (Exception e) {
            System.out.println("Failed to execute city report query");
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    // [Req 7] All the cities in the world organised by largest population to smallest.
    public Optional<List<CityReport>> getCityReport() {
        String query = "SELECT city.Name AS City, country.Name AS Country, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code ORDER BY city.Population DESC;";
        return executeQuery(query);
    }

    // [Req 8] All the cities in a continent organised by largest population to smallest.
    public Optional<List<CityReport>> getCityReportByContinent(String continent) {
        String query = "SELECT city.Name AS City, country.Name AS Country, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = ? ORDER BY city.Population DESC;";
        return executeQuery(query, continent);
    }

    // [Req 9] All the cities in a region organised by largest population to smallest.
    public Optional<List<CityReport>> getCityReportByRegion(String region) {
        String query = "SELECT city.Name AS City, country.Name AS Country, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = ? ORDER BY city.Population DESC;";
        return executeQuery(query, region);
    }

    // [Req 10] All the cities in a country organised by largest population to smallest.
    public Optional<List<CityReport>> getCityReportByCountry(String country) {
        String query = "SELECT city.Name AS City, country.Name AS Country, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = ? ORDER BY city.Population DESC;";
        return executeQuery(query, country);
    }

    // [Req 11] All the cities in a district organised by largest population to smallest.
    public Optional<List<CityReport>> getCityReportByDistrict(String district) {
        String query = "SELECT city.Name AS City, country.Name AS Country, city.District, city.Population " +
                "FROM city JOIN country ON city.CountryCode = country.Code " +
                "WHERE city.District = ? ORDER BY city.Population DESC;";
        return executeQuery(query, district);
    }
}