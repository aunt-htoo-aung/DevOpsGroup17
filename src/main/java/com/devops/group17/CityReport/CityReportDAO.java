package com.devops.group17.CityReport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityReportDAO {
    private final Connection con;

    public CityReportDAO(Connection con) {
        this.con = con;
    }

    /**

     *
     * @return Optional containing the list of CityReport DTOs, or empty if query fails
     */
    public Optional<List<CityReport>> getCityReport() {
        List<CityReport> reports = new ArrayList<>();

        String query =
               "SELECT city.Name AS City,"
                       + " country.Name AS Country, "
                       + "city.District, "
                       + "city.Population FROM city" +
                         " JOIN country ON city.CountryCode = country.Code " +
                           "ORDER BY city.Population DESC;";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                CityReport report = new CityReport();
                report.setName(rs.getString("City"));
                report.setCountry(rs.getString("Country"));
                report.setDistrict(rs.getString("District"));
                report.setPopulation(rs.getLong("Population"));
                reports.add(report);
            }

            return Optional.of(reports);

        } catch (Exception e) {
            System.out.println("Failed to execute language report query");
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
