package com.devops.group17.CapitalCityReport;

import com.devops.group17.Capital_City_Report.CapitalCityReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CapitalCityReport DTO Unit Tests")
class CapitalCityReportTest {

    @Test
    @DisplayName("Default constructor initializes fields to default values")
    void testDefaultConstructor() {
        CapitalCityReport report = new CapitalCityReport();

        assertNull(report.getName());
        assertNull(report.getCountry());
        assertEquals(0L, report.getPopulation());
    }

    @Test
    @DisplayName("Parameterized constructor sets all fields correctly")
    void testParameterizedConstructor() {
        CapitalCityReport report = new CapitalCityReport("Seoul", "South Korea", 9981619L);

        assertEquals("Seoul", report.getName());
        assertEquals("South Korea", report.getCountry());
        assertEquals(9981619L, report.getPopulation());
    }

    @Test
    @DisplayName("Setters update the fields")
    void testSetters() {
        CapitalCityReport report = new CapitalCityReport();
        report.setName("Tokyo");
        report.setCountry("Japan");
        report.setPopulation(7980230L);

        assertEquals("Tokyo", report.getName());
        assertEquals("Japan", report.getCountry());
        assertEquals(7980230L, report.getPopulation());
    }
}
