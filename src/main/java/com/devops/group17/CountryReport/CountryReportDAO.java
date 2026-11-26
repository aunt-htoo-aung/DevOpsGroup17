<<<<<<< HEAD
package com.devops.group17.CountryReport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
=======

package com.devops.group17.CountryReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
>>>>>>> develop
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for the Country Report.
<<<<<<< HEAD
 * Executes the SQL query and returns country report data.
=======
 * Executes SQL queries and returns country report data.
>>>>>>> develop
 */
public class CountryReportDAO {

    private final Connection con;

    public CountryReportDAO(Connection con) {
        this.con = con;
    }

<<<<<<< HEAD
    /**
     * Retrieves all countries with their country code, name, continent,
     * region, population and capital city.
     *
     * Countries are ordered by population from largest to smallest.
     *
     * @return Optional containing the list of CountryReport DTOs,
     *         or empty if the query fails.
     */
    public Optional<List<CountryReport>> getCountryReport() {
        List<CountryReport> reports = new ArrayList<>();

        String query =
                "SELECT country.Code, "
                        + "country.Name, "
                        + "country.Continent, "
                        + "country.Region, "
                        + "country.Population, "
                        + "COALESCE(city.Name, 'N/A') AS Capital "
                        + "FROM country "
                        + "LEFT JOIN city ON country.Capital = city.ID "
                        + "ORDER BY country.Population DESC";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                CountryReport report = new CountryReport();

                report.setCode(rs.getString("Code"));
                report.setName(rs.getString("Name"));
                report.setContinent(rs.getString("Continent"));
                report.setRegion(rs.getString("Region"));
                report.setPopulation(rs.getLong("Population"));
                report.setCapital(rs.getString("Capital"));

                reports.add(report);
=======
    // UC-1: Display all countries worldwide
    public Optional<List<CountryReport>> getCountryReport() {

        String query =
                "SELECT country.Code, country.Name, country.Continent, " +
                        "country.Region, country.Population, " +
                        "COALESCE(city.Name, 'N/A') AS Capital " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Capital = city.ID " +
                        "ORDER BY country.Population DESC";

        return executeQuery(query);
    }

    // UC-2: Display all countries in a continent
    public Optional<List<CountryReport>> getCountriesByContinent(String continent) {

        String query =
                "SELECT country.Code, country.Name, country.Continent, " +
                        "country.Region, country.Population, " +
                        "COALESCE(city.Name, 'N/A') AS Capital " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Capital = city.ID " +
                        "WHERE country.Continent = ? " +
                        "ORDER BY country.Population DESC";

        return executeQuery(query, continent);
    }

    // UC-3: Display all countries in a region
    public Optional<List<CountryReport>> getCountriesByRegion(String region) {

        String query =
                "SELECT country.Code, country.Name, country.Continent, " +
                        "country.Region, country.Population, " +
                        "COALESCE(city.Name, 'N/A') AS Capital " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Capital = city.ID " +
                        "WHERE country.Region = ? " +
                        "ORDER BY country.Population DESC";

        return executeQuery(query, region);
    }

    // UC-4: Display Top N populated countries worldwide
    public Optional<List<CountryReport>> getTopNWorldCountries(int limit) {

        String query =
                "SELECT country.Code, country.Name, country.Continent, " +
                        "country.Region, country.Population, " +
                        "COALESCE(city.Name, 'N/A') AS Capital " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Capital = city.ID " +
                        "ORDER BY country.Population DESC " +
                        "LIMIT ?";

        return executeQuery(query, limit);
    }

    // UC-5: Display Top N populated countries in a continent
    public Optional<List<CountryReport>> getTopNContinentCountries(
            String continent, int limit) {

        String query =
                "SELECT country.Code, country.Name, country.Continent, " +
                        "country.Region, country.Population, " +
                        "COALESCE(city.Name, 'N/A') AS Capital " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Capital = city.ID " +
                        "WHERE country.Continent = ? " +
                        "ORDER BY country.Population DESC " +
                        "LIMIT ?";

        return executeQuery(query, continent, limit);
    }

    // UC-6: Display Top N populated countries in a region
    public Optional<List<CountryReport>> getTopNRegionCountries(
            String region, int limit) {

        String query =
                "SELECT country.Code, country.Name, country.Continent, " +
                        "country.Region, country.Population, " +
                        "COALESCE(city.Name, 'N/A') AS Capital " +
                        "FROM country " +
                        "LEFT JOIN city ON country.Capital = city.ID " +
                        "WHERE country.Region = ? " +
                        "ORDER BY country.Population DESC " +
                        "LIMIT ?";

        return executeQuery(query, region, limit);
    }

    // Common method to execute SQL queries
    private Optional<List<CountryReport>> executeQuery(
            String query, Object... parameters) {

        List<CountryReport> reports = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(query)) {

            for (int i = 0; i < parameters.length; i++) {
                stmt.setObject(i + 1, parameters[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    CountryReport report = new CountryReport();

                    report.setCode(rs.getString("Code"));
                    report.setName(rs.getString("Name"));
                    report.setContinent(rs.getString("Continent"));
                    report.setRegion(rs.getString("Region"));
                    report.setPopulation(rs.getLong("Population"));
                    report.setCapital(rs.getString("Capital"));

                    reports.add(report);
                }
>>>>>>> develop
            }

            return Optional.of(reports);

        } catch (Exception e) {
            System.out.println("Failed to execute country report query");
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> develop
