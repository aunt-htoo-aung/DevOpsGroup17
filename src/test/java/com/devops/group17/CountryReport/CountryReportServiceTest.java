
package com.devops.group17.CountryReport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CountryReportService Unit Tests")
class CountryReportServiceTest {

    @Mock
    private CountryReportDAO mockCountryReportDAO;

    private final ByteArrayOutputStream outContent =
            new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    private CountryReport createMockCountry() {

        CountryReport report = new CountryReport();

        report.setCode("CHN");
        report.setName("China");
        report.setContinent("Asia");
        report.setRegion("Eastern Asia");
        report.setPopulation(1411778724L);
        report.setCapital("Beijing");

        return report;
    }

    @Test
    @DisplayName("Constructor initializes service correctly")
    void testConstructor() {

        CountryReportService service =
                new CountryReportService(mockCountryReportDAO);

        assertNotNull(service);
    }

    @Test
    @DisplayName("UC-1: Displays all countries worldwide")
    void testDisplayCountryReport_Success() {

        List<CountryReport> mockList =
                Collections.singletonList(createMockCountry());

        when(mockCountryReportDAO.getCountryReport())
                .thenReturn(Optional.of(mockList));

        CountryReportService service =
                new CountryReportService(mockCountryReportDAO);

        service.displayCountryReport();

        String output = outContent.toString();

        assertTrue(output.contains("All Countries Worldwide"));
        assertTrue(output.contains("Code"));
        assertTrue(output.contains("Name"));
        assertTrue(output.contains("Continent"));
        assertTrue(output.contains("Region"));
        assertTrue(output.contains("Population"));
        assertTrue(output.contains("Capital"));
        assertTrue(output.contains("China"));
        assertTrue(output.contains("Beijing"));

        verify(mockCountryReportDAO, times(1)).getCountryReport();
    }

    @Test
    @DisplayName("UC-2: Displays countries in a continent")
    void testDisplayCountriesByContinent_Success() {

        List<CountryReport> mockList =
                Collections.singletonList(createMockCountry());

        when(mockCountryReportDAO.getCountriesByContinent("Asia"))
                .thenReturn(Optional.of(mockList));

        CountryReportService service =
                new CountryReportService(mockCountryReportDAO);

        service.displayCountriesByContinent("Asia");

        String output = outContent.toString();

        assertTrue(output.contains("Countries in Asia"));
        assertTrue(output.contains("China"));
        assertTrue(output.contains("Eastern Asia"));

        verify(mockCountryReportDAO, times(1))
                .getCountriesByContinent("Asia");
    }

    @Test
    @DisplayName("UC-3: Displays countries in a region")
    void testDisplayCountriesByRegion_Success() {

        List<CountryReport> mockList =
                Collections.singletonList(createMockCountry());

        when(mockCountryReportDAO.getCountriesByRegion("Eastern Asia"))
                .thenReturn(Optional.of(mockList));

        CountryReportService service =
                new CountryReportService(mockCountryReportDAO);

        service.displayCountriesByRegion("Eastern Asia");

        String output = outContent.toString();

        assertTrue(output.contains("Countries in Eastern Asia"));
        assertTrue(output.contains("China"));

        verify(mockCountryReportDAO, times(1))
                .getCountriesByRegion("Eastern Asia");
    }

    @Test
    @DisplayName("UC-4: Displays Top N populated countries worldwide")
    void testDisplayTopNWorldCountries_Success() {

        List<CountryReport> mockList =
                Collections.singletonList(createMockCountry());

        when(mockCountryReportDAO.getTopNWorldCountries(10))
                .thenReturn(Optional.of(mockList));

        CountryReportService service =
                new CountryReportService(mockCountryReportDAO);

        service.displayTopNWorldCountries(10);

        String output = outContent.toString();

        assertTrue(output.contains("Top 10"));
        assertTrue(output.contains("Worldwide"));
        assertTrue(output.contains("China"));

        verify(mockCountryReportDAO, times(1))
                .getTopNWorldCountries(10);
    }

    @Test
    @DisplayName("UC-5: Displays Top N populated countries in a continent")
    void testDisplayTopNContinentCountries_Success() {

        List<CountryReport> mockList =
                Collections.singletonList(createMockCountry());

        when(mockCountryReportDAO.getTopNContinentCountries("Asia", 5))
                .thenReturn(Optional.of(mockList));

        CountryReportService service =
                new CountryReportService(mockCountryReportDAO);

        service.displayTopNContinentCountries("Asia", 5);

        String output = outContent.toString();

        assertTrue(output.contains("Top 5"));
        assertTrue(output.contains("Asia"));
        assertTrue(output.contains("China"));

        verify(mockCountryReportDAO, times(1))
                .getTopNContinentCountries("Asia", 5);
    }

}
