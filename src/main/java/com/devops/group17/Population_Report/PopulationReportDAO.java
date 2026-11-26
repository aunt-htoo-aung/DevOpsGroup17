package com.devops.group17.Population_Report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PopulationReportDAO {
    private final Connection con;

    public PopulationReportDAO(Connection con) {
        this.con = con;
    }

    /**
     * R23: population of people, people living in cities and people not living in cities
     * for each continent.
     *
     * @return Optional containing the list of PopulationReport DTOs, or empty if query fails
     */
    public Optional<List<PopulationReport>> getContinentPopulationReport() {
        String query =
                "SELECT country.Continent AS Name, "
                        + "SUM(country.Population) AS TotalPopulation, "
                        + "SUM(IFNULL(cityTotals.CityPopulation, 0)) AS CityPopulation "
                        + "FROM country "
                        + "LEFT JOIN (SELECT CountryCode, SUM(Population) AS CityPopulation "
                        + "FROM city GROUP BY CountryCode) AS cityTotals "
                        + "ON country.Code = cityTotals.CountryCode "
                        + "GROUP BY country.Continent "
                        + "ORDER BY TotalPopulation DESC;";
        return runQuery(query, "continent");
    }

    /**
     * R24: population of people, people living in cities and people not living in cities
     * for each region.
     *
     * @return Optional containing the list of PopulationReport DTOs, or empty if query fails
     */
    public Optional<List<PopulationReport>> getRegionPopulationReport() {
        String query =
                "SELECT country.Region AS Name, "
                        + "SUM(country.Population) AS TotalPopulation, "
                        + "SUM(IFNULL(cityTotals.CityPopulation, 0)) AS CityPopulation "
                        + "FROM country "
                        + "LEFT JOIN (SELECT CountryCode, SUM(Population) AS CityPopulation "
                        + "FROM city GROUP BY CountryCode) AS cityTotals "
                        + "ON country.Code = cityTotals.CountryCode "
                        + "GROUP BY country.Region "
                        + "ORDER BY TotalPopulation DESC;";
        return runQuery(query, "region");
    }

    /**
     * R25: population of people, people living in cities and people not living in cities
     * for each country.
     *
     * @return Optional containing the list of PopulationReport DTOs, or empty if query fails
     */
    public Optional<List<PopulationReport>> getCountryPopulationReport() {
        String query =
                "SELECT country.Name AS Name, "
                        + "country.Population AS TotalPopulation, "
                        + "IFNULL(cityTotals.CityPopulation, 0) AS CityPopulation "
                        + "FROM country "
                        + "LEFT JOIN (SELECT CountryCode, SUM(Population) AS CityPopulation "
                        + "FROM city GROUP BY CountryCode) AS cityTotals "
                        + "ON country.Code = cityTotals.CountryCode "
                        + "ORDER BY country.Population DESC;";
        return runQuery(query, "country");
    }

    /**
     * Runs one of the population report queries and maps the rows to DTOs.
     * Each query must return the columns Name, TotalPopulation and CityPopulation.
     */
    private Optional<List<PopulationReport>> runQuery(String query, String level) {
        List<PopulationReport> reports = new ArrayList<>();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                PopulationReport report = new PopulationReport();
                long total = rs.getLong("TotalPopulation");
                long city = rs.getLong("CityPopulation");
                report.setName(rs.getString("Name"));
                report.setTotalPopulation(total);
                report.setCityPopulation(city);
                report.setNonCityPopulation(total - city);
                reports.add(report);
            }

            return Optional.of(reports);

        } catch (Exception e) {
            System.out.println("Failed to execute " + level + " population report query");
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}