package com.devops.group17.Capital_City_Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for the Capital City Reports (UC-17, UC-18, UC-19).
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

    private static final String ORDER_BY = "ORDER BY city.Population DESC, city.Name ASC";

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
        return execute(BASE_QUERY + ORDER_BY, null);
    }

    /**
     * UC-18: Retrieves all the capital cities in a continent,
     * organised by largest population to smallest.
     *
     * @param continent the continent name (e.g. Asia)
     * @return Optional containing the list of CapitalCityReport DTOs, or empty if query fails
     */
    public Optional<List<CapitalCityReport>> getContinentCapitalCities(String continent) {
        return execute(BASE_QUERY + "WHERE country.Continent = ? " + ORDER_BY, continent);
    }

    /**
     * UC-19: Retrieves all the capital cities in a region,
     * organised by largest population to smallest.
     *
     * @param region the region name (e.g. Western Europe)
     * @return Optional containing the list of CapitalCityReport DTOs, or empty if query fails
     */
    public Optional<List<CapitalCityReport>> getRegionCapitalCities(String region) {
        return execute(BASE_QUERY + "WHERE country.Region = ? " + ORDER_BY, region);
    }

    /**
     * Runs a prepared query with an optional filter value.
     *
     * @param query  the SQL to run
     * @param filter value for the single placeholder, or null if the query has none
     * @return Optional containing the list of DTOs, or empty if the query fails
     */
    private Optional<List<CapitalCityReport>> execute(String query, String filter) {
        List<CapitalCityReport> reports = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            if (filter != null) {
                stmt.setString(1, filter);
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