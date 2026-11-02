package com.devops.group17.Capital_City_Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for the Capital City Reports (UC-17 to UC-22).
 * Executes the queries and returns data as a list of DTOs.
 * A capital city is the city referenced by country.Capital.
 */
public class CapitalCityReportDAO {

    private static final String BASE_QUERY =
            "SELECT city.Name AS Name, "
                    + "country.Name AS Country, "
                    + "city.Population AS Population "
                    + "FROM country "
                    + "JOIN city ON country.Capital = city.ID ";

    private static final String ORDER_BY = "ORDER BY city.Population DESC, city.Name ASC ";

    private final Connection con;

    public CapitalCityReportDAO(Connection con) {
        this.con = con;
    }

    /**
     * UC-17: Retrieves all the capital cities in the world,
     * organised by largest population to smallest.
     *
     * @return Optional containing the list of CapitalCityReport DTOs, or empty if query fails
     */
    public Optional<List<CapitalCityReport>> getWorldCapitalCities() {
        return execute(BASE_QUERY + ORDER_BY, null, null);
    }

    /**
     * UC-18: Retrieves all the capital cities in a continent,
     * organised by largest population to smallest.
     *
     * @param continent the continent name (e.g. Asia)
     * @return Optional containing the list of CapitalCityReport DTOs, or empty if query fails
     */
    public Optional<List<CapitalCityReport>> getContinentCapitalCities(String continent) {
        return execute(BASE_QUERY + "WHERE country.Continent = ? " + ORDER_BY, continent, null);
    }

    /**
     * UC-19: Retrieves all the capital cities in a region,
     * organised by largest population to smallest.
     *
     * @param region the region name (e.g. Western Europe)
     * @return Optional containing the list of CapitalCityReport DTOs, or empty if query fails
     */
    public Optional<List<CapitalCityReport>> getRegionCapitalCities(String region) {
        return execute(BASE_QUERY + "WHERE country.Region = ? " + ORDER_BY, region, null);
    }

    /**
     * UC-20: Retrieves the top N populated capital cities in the world.
     *
     * @param n the number of capital cities to return
     * @return Optional containing the list of CapitalCityReport DTOs, or empty if query fails
     */
    public Optional<List<CapitalCityReport>> getTopNWorldCapitalCities(int n) {
        return execute(BASE_QUERY + ORDER_BY + "LIMIT ?", null, n);
    }

    /**
     * UC-21: Retrieves the top N populated capital cities in a continent.
     *
     * @param continent the continent name (e.g. Asia)
     * @param n         the number of capital cities to return
     * @return Optional containing the list of CapitalCityReport DTOs, or empty if query fails
     */
    public Optional<List<CapitalCityReport>> getTopNContinentCapitalCities(String continent, int n) {
        return execute(BASE_QUERY + "WHERE country.Continent = ? " + ORDER_BY + "LIMIT ?", continent, n);
    }

    /**
     * UC-22: Retrieves the top N populated capital cities in a region.
     *
     * @param region the region name (e.g. Western Europe)
     * @param n      the number of capital cities to return
     * @return Optional containing the list of CapitalCityReport DTOs, or empty if query fails
     */
    public Optional<List<CapitalCityReport>> getTopNRegionCapitalCities(String region, int n) {
        return execute(BASE_QUERY + "WHERE country.Region = ? " + ORDER_BY + "LIMIT ?", region, n);
    }

    /**
     * Runs a prepared query with an optional filter value and an optional row limit.
     *
     * @param query  the SQL to run
     * @param filter value for the continent/region placeholder, or null if the query has none
     * @param limit  value for the LIMIT placeholder, or null if the query has none
     * @return Optional containing the list of DTOs, or empty if the query fails
     */
    private Optional<List<CapitalCityReport>> execute(String query, String filter, Integer limit) {
        List<CapitalCityReport> reports = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            int index = 1;
            if (filter != null) {
                stmt.setString(index++, filter);
            }
            if (limit != null) {
                stmt.setInt(index, limit);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CapitalCityReport report = new CapitalCityReport();
                    report.setName(rs.getString("Name"));
                    report.setCountry(rs.getString("Country"));
                    report.setPopulation(rs.getLong("Population"));
                    reports.add(report);
                }
            }
            return Optional.of(reports);

        } catch (SQLException e) {
            System.out.println("Failed to execute capital city report query");
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}