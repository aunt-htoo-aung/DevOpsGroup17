package com.devops.group17.LanguageReport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LanguageReport DTO Unit Tests")
class LanguageReportTest {

    @Test
    @DisplayName("Default constructor initializes fields to default values")
    void testDefaultConstructor() {
        LanguageReport report = new LanguageReport();

        assertNull(report.getLanguage());
        assertEquals(0L, report.getTotalSpeakers());
        assertEquals(0.0, report.getWorldPercentage(), 0.001);
    }

    @Test
    @DisplayName("Parameterized constructor sets all fields correctly")
    void testParameterizedConstructor() {
        LanguageReport report = new LanguageReport("Chinese", 1191843539L, 19.61);

        assertEquals("Chinese", report.getLanguage());
        assertEquals(1191843539L, report.getTotalSpeakers());
        assertEquals(19.61, report.getWorldPercentage(), 0.001);
    }


}
