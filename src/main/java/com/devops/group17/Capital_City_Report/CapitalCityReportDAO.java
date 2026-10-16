package com.devops.group17.Capital_City_Report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for the Capital City Report (UC-17).
 * Executes the query and returns data as a list of DTOs.
 * A capital city is the city referenced by country.Capital.
 */
public class CapitalCityReportDAO {

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
        List<CapitalCityReport> reports = new ArrayList<>();

        String query =
                "SELECT city.Name AS Name, "
                        + "country.Name AS Country, "
                        + "city.Population AS Population "
                        + "FROM country "
                        + "JOIN city ON country.Capital = city.ID "
                        + "ORDER BY city.Population DESC, city.Name ASC";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                CapitalCityReport report = new CapitalCityReport();
                report.setName(rs.getString("Name"));
                report.setCountry(rs.getString("Country"));
                report.setPopulation(rs.getLong("Population"));
                reports.add(report);
            }
            return Optional.of(reports);

        } catch (SQLException e) {
            System.out.println("Failed to execute capital city report query");
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
