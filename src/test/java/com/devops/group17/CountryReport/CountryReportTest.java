
package com.devops.group17.CountryReport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CountryReport DTO Unit Tests")
class CountryReportTest {

    @Test
    @DisplayName("Default constructor initializes fields to default values")
    void testDefaultConstructor() {

        CountryReport report = new CountryReport();

        assertNull(report.getCode());
        assertNull(report.getName());
        assertNull(report.getContinent());
        assertNull(report.getRegion());
        assertEquals(0L, report.getPopulation());
        assertNull(report.getCapital());
    }

    @Test
    @DisplayName("Setters and getters work correctly for all fields")
    void testGettersAndSetters() {

        CountryReport report = new CountryReport();

        report.setCode("CHN");
        report.setName("China");
        report.setContinent("Asia");
        report.setRegion("Eastern Asia");
        report.setPopulation(1411778724L);
        report.setCapital("Beijing");

        assertEquals("CHN", report.getCode());
        assertEquals("China", report.getName());
        assertEquals("Asia", report.getContinent());
        assertEquals("Eastern Asia", report.getRegion());
        assertEquals(1411778724L, report.getPopulation());
        assertEquals("Beijing", report.getCapital());
    }
}
