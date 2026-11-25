package com.devops.group17.CityReport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CityReport DTO Unit Tests")
class CityReportTest {

    @Test
    @DisplayName("Default constructor initializes fields to default values")
    void testDefaultConstructor() {
        CityReport report = new CityReport();

        assertNull(report.getName());
        assertNull(report.getCountry());
        assertNull(report.getDistrict());
        assertEquals(0L, report.getPopulation());
    }

    @Test
    @DisplayName("Setters and getters set and return all fields correctly")
    void testSettersAndGetters() {
        CityReport report = new CityReport();
        report.setName("Bangkok");
        report.setCountry("Thailand");
        report.setDistrict("Bangkok");
        report.setPopulation(6320174L);

        assertEquals("Bangkok", report.getName());
        assertEquals("Thailand", report.getCountry());
        assertEquals("Bangkok", report.getDistrict());
        assertEquals(6320174L, report.getPopulation());
    }
}