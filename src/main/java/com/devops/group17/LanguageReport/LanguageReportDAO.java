package com.devops.group17.LanguageReport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object for the Language Report.
 * Executes the query and returns data as a list of DTOs.
 */
public class LanguageReportDAO {

    private final Connection con;

    public LanguageReportDAO(Connection con) {
        this.con = con;
    }

    /**
     * Retrieves the number of speakers and world percentage for
     * Chinese, English, Hindi, Spanish, and Arabic.
     *
     * @return Optional containing the list of LanguageReport DTOs, or empty if query fails
     */
    public Optional<List<LanguageReport>> getLanguageReport() {
        List<LanguageReport> reports = new ArrayList<>();

        String query =
                "SELECT cl.Language, "
                + "ROUND(SUM(c.Population * (cl.Percentage / 100))) AS TotalSpeakers, "
                + "ROUND((SUM(c.Population * (cl.Percentage / 100)) "
                + "/ (SELECT SUM(Population) FROM country)) * 100, 2) AS WorldPercentage "
                + "FROM countrylanguage cl "
                + "JOIN country c ON cl.CountryCode = c.Code "
                + "WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') "
                + "GROUP BY cl.Language "
                + "ORDER BY TotalSpeakers DESC";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                LanguageReport report = new LanguageReport();
                report.setLanguage(rs.getString("Language"));
                report.setTotalSpeakers(rs.getLong("TotalSpeakers"));
                report.setWorldPercentage(rs.getDouble("WorldPercentage"));
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
