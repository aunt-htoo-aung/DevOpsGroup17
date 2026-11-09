package com.devops.group17.CountryReport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for the Country Report.
 * Executes the SQL query and returns country report data.
 */
public class CountryReportDAO {

    private final Connection con;

    public CountryReportDAO(Connection con) {
        this.con = con;
    }

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
            }

            return Optional.of(reports);

        } catch (Exception e) {
            System.out.println("Failed to execute country report query");
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}