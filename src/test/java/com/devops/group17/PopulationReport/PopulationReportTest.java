package com.devops.group17.PopulationReport;

import com.devops.group17.Population_Report.PopulationReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PopulationReport} verifying field accessors and
 * population percentage calculations.
 */
@DisplayName("PopulationReport DTO Tests")
class PopulationReportTest {

    /**
     * Verifies that getters and setters store and return name and population
     * values accurately on {@link PopulationReport}.
     */
    @Test
    void gettersAndSettersWork() {
        PopulationReport report = new PopulationReport();
        report.setName("TestLand");
        report.setTotalPopulation(1_000_000L);
        report.setCityPopulation(400_000L);
        report.setNonCityPopulation(600_000L);

        assertEquals("TestLand", report.getName());
        assertEquals(1_000_000L, report.getTotalPopulation());
        assertEquals(400_000L, report.getCityPopulation());
        assertEquals(600_000L, report.getNonCityPopulation());
    }

    /**
     * Verifies that city and non-city population percentages are computed
     * accurately relative to total population.
     */
    @Test
    void percentageCalculations() {
        PopulationReport report = new PopulationReport();
        report.setTotalPopulation(200L);
        report.setCityPopulation(50L);
        report.setNonCityPopulation(150L);
        assertEquals(25.0, report.getCityPercentage(), 0.0001);
        assertEquals(75.0, report.getNonCityPercentage(), 0.0001);
    }

    /**
     * Verifies that city and non-city percentage calculations return {@code 0.0}
     * without throwing a division-by-zero error when total population is zero.
     */
    @Test
    void zeroTotalPopulationHandledGracefully() {
        PopulationReport report = new PopulationReport();
        report.setTotalPopulation(0L);
        report.setCityPopulation(0L);
        report.setNonCityPopulation(0L);
        assertEquals(0.0, report.getCityPercentage());
        assertEquals(0.0, report.getNonCityPercentage());
    }
}