package com.devops.group17.CapitalCityReport;

import com.devops.group17.Capital_City_Report.CapitalCityReport;
import com.devops.group17.Capital_City_Report.CapitalCityReportDAO;
import com.devops.group17.Capital_City_Report.CapitalCityReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CapitalCityReportService Unit Tests")
class CapitalCityReportServiceTest {

    @Mock
    private CapitalCityReportDAO mockCapitalCityReportDAO;

    @Mock
    private Connection mockConnection;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    private List<CapitalCityReport> sampleReports() {
        return Arrays.asList(
                new CapitalCityReport("Seoul", "South Korea", 9981619L),
                new CapitalCityReport("Jakarta", "Indonesia", 9604900L),
                new CapitalCityReport("Tokyo", "Japan", 7980230L)
        );
    }

    private void assertSampleTable(String output) {
        assertTrue(output.contains("Name"));
        assertTrue(output.contains("Country"));
        assertTrue(output.contains("Population"));
        assertTrue(output.contains("Seoul"));
        assertTrue(output.contains("South Korea"));
        assertTrue(output.contains("9981619"));
        assertTrue(output.contains("Jakarta"));
        assertTrue(output.contains("Tokyo"));
        // Order from the DAO (largest to smallest) is preserved
        assertTrue(output.indexOf("Seoul") < output.indexOf("Jakarta"));
        assertTrue(output.indexOf("Jakarta") < output.indexOf("Tokyo"));
    }

    @Test
    @DisplayName("Constructor with Connection initializes service correctly")
    void testConstructorWithConnection() {
        CapitalCityReportService service = new CapitalCityReportService(mockConnection);
        assertNotNull(service);
    }

    // ---------- UC-17 ----------

    @Test
    @DisplayName("UC-17: prints the world capital city table when data is available")
    void testDisplayWorldCapitalCities_Success() {
        when(mockCapitalCityReportDAO.getWorldCapitalCities()).thenReturn(Optional.of(sampleReports()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayWorldCapitalCities();

        String output = outContent.toString();
        assertTrue(output.contains("Display capital cities in the world"));
        assertSampleTable(output);
        verify(mockCapitalCityReportDAO, times(1)).getWorldCapitalCities();
    }

    @Test
    @DisplayName("UC-17: prints the connection error when the DAO returns Optional.empty")
    void testDisplayWorldCapitalCities_DatabaseFailure() {
        when(mockCapitalCityReportDAO.getWorldCapitalCities()).thenReturn(Optional.empty());

        new CapitalCityReportService(mockCapitalCityReportDAO).displayWorldCapitalCities();

        String output = outContent.toString();
        assertTrue(output.contains("Database connection lost. Please check configuration."));
        assertFalse(output.contains("Country"));
    }

    @Test
    @DisplayName("UC-17: prints a no data message when the list is empty")
    void testDisplayWorldCapitalCities_EmptyList() {
        when(mockCapitalCityReportDAO.getWorldCapitalCities())
                .thenReturn(Optional.of(Collections.emptyList()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayWorldCapitalCities();

        assertTrue(outContent.toString().contains("No data available to display."));
    }

    // ---------- UC-18 ----------

    @Test
    @DisplayName("UC-18: prints the continent capital city table when data is available")
    void testDisplayContinentCapitalCities_Success() {
        when(mockCapitalCityReportDAO.getContinentCapitalCities("Asia")).thenReturn(Optional.of(sampleReports()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayContinentCapitalCities("Asia");

        String output = outContent.toString();
        assertTrue(output.contains("Display capital cities in continent: Asia"));
        assertSampleTable(output);
        verify(mockCapitalCityReportDAO, times(1)).getContinentCapitalCities("Asia");
    }

    @Test
    @DisplayName("UC-18: trims the continent before querying")
    void testDisplayContinentCapitalCities_TrimsInput() {
        when(mockCapitalCityReportDAO.getContinentCapitalCities("Asia")).thenReturn(Optional.of(sampleReports()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayContinentCapitalCities("  Asia  ");

        verify(mockCapitalCityReportDAO).getContinentCapitalCities("Asia");
    }

    @Test
    @DisplayName("UC-18: rejects blank or null continent without querying")
    void testDisplayContinentCapitalCities_BlankInput() {
        CapitalCityReportService service = new CapitalCityReportService(mockCapitalCityReportDAO);

        service.displayContinentCapitalCities("   ");
        service.displayContinentCapitalCities(null);

        String output = outContent.toString();
        assertTrue(output.contains("Invalid input. Please enter a valid continent."));
        verifyNoInteractions(mockCapitalCityReportDAO);
    }

    @Test
    @DisplayName("UC-18: tells the user when the continent has no capital cities")
    void testDisplayContinentCapitalCities_NoResults() {
        when(mockCapitalCityReportDAO.getContinentCapitalCities("Atlantis"))
                .thenReturn(Optional.of(Collections.emptyList()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayContinentCapitalCities("Atlantis");

        assertTrue(outContent.toString()
                .contains("No capital cities found for the specified continent. Please enter a valid continent."));
    }

    @Test
    @DisplayName("UC-18: prints the connection error when the DAO fails")
    void testDisplayContinentCapitalCities_DatabaseFailure() {
        when(mockCapitalCityReportDAO.getContinentCapitalCities("Asia")).thenReturn(Optional.empty());

        new CapitalCityReportService(mockCapitalCityReportDAO).displayContinentCapitalCities("Asia");

        assertTrue(outContent.toString().contains("Database connection lost. Please check configuration."));
    }

    // ---------- UC-19 ----------

    @Test
    @DisplayName("UC-19: prints the region capital city table when data is available")
    void testDisplayRegionCapitalCities_Success() {
        when(mockCapitalCityReportDAO.getRegionCapitalCities("Eastern Asia")).thenReturn(Optional.of(sampleReports()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayRegionCapitalCities("Eastern Asia");

        String output = outContent.toString();
        assertTrue(output.contains("Display capital cities in region: Eastern Asia"));
        assertSampleTable(output);
        verify(mockCapitalCityReportDAO, times(1)).getRegionCapitalCities("Eastern Asia");
    }

    @Test
    @DisplayName("UC-19: rejects blank or null region without querying")
    void testDisplayRegionCapitalCities_BlankInput() {
        CapitalCityReportService service = new CapitalCityReportService(mockCapitalCityReportDAO);

        service.displayRegionCapitalCities("");
        service.displayRegionCapitalCities(null);

        assertTrue(outContent.toString().contains("Invalid input. Please enter a valid region."));
        verifyNoInteractions(mockCapitalCityReportDAO);
    }

    @Test
    @DisplayName("UC-19: tells the user when the region has no capital cities")
    void testDisplayRegionCapitalCities_NoResults() {
        when(mockCapitalCityReportDAO.getRegionCapitalCities("Mordor"))
                .thenReturn(Optional.of(Collections.emptyList()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayRegionCapitalCities("Mordor");

        assertTrue(outContent.toString()
                .contains("No capital cities found for the specified region. Please enter a valid region."));
    }

    @Test
    @DisplayName("UC-19: prints the connection error when the DAO fails")
    void testDisplayRegionCapitalCities_DatabaseFailure() {
        when(mockCapitalCityReportDAO.getRegionCapitalCities("Caribbean")).thenReturn(Optional.empty());

        new CapitalCityReportService(mockCapitalCityReportDAO).displayRegionCapitalCities("Caribbean");

        assertTrue(outContent.toString().contains("Database connection lost. Please check configuration."));
    }

    // ---------- UC-20 ----------

    @Test
    @DisplayName("UC-20: prints the top N world capital city table when data is available")
    void testDisplayTopNWorldCapitalCities_Success() {
        when(mockCapitalCityReportDAO.getTopNWorldCapitalCities(3)).thenReturn(Optional.of(sampleReports()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNWorldCapitalCities(3);

        String output = outContent.toString();
        assertTrue(output.contains("Display top 3 capital cities in the world"));
        assertSampleTable(output);
        verify(mockCapitalCityReportDAO, times(1)).getTopNWorldCapitalCities(3);
    }

    @Test
    @DisplayName("UC-20: rejects zero and negative N without querying")
    void testDisplayTopNWorldCapitalCities_InvalidN() {
        CapitalCityReportService service = new CapitalCityReportService(mockCapitalCityReportDAO);

        service.displayTopNWorldCapitalCities(0);
        service.displayTopNWorldCapitalCities(-5);

        String output = outContent.toString();
        assertTrue(output.contains("Invalid input. N must be a positive whole number."));
        verifyNoInteractions(mockCapitalCityReportDAO);
    }

    @Test
    @DisplayName("UC-20: prints the connection error when the DAO fails")
    void testDisplayTopNWorldCapitalCities_DatabaseFailure() {
        when(mockCapitalCityReportDAO.getTopNWorldCapitalCities(10)).thenReturn(Optional.empty());

        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNWorldCapitalCities(10);

        assertTrue(outContent.toString().contains("Database connection lost. Please check configuration."));
    }

    // ---------- UC-21 ----------

    @Test
    @DisplayName("UC-21: prints the top N continent capital city table when data is available")
    void testDisplayTopNContinentCapitalCities_Success() {
        when(mockCapitalCityReportDAO.getTopNContinentCapitalCities("Asia", 3)).thenReturn(Optional.of(sampleReports()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNContinentCapitalCities("Asia", 3);

        String output = outContent.toString();
        assertTrue(output.contains("Display top 3 capital cities in continent: Asia"));
        assertSampleTable(output);
        verify(mockCapitalCityReportDAO, times(1)).getTopNContinentCapitalCities("Asia", 3);
    }

    @Test
    @DisplayName("UC-21: rejects invalid N without querying")
    void testDisplayTopNContinentCapitalCities_InvalidN() {
        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNContinentCapitalCities("Asia", 0);

        assertTrue(outContent.toString().contains("Invalid input. N must be a positive whole number."));
        verifyNoInteractions(mockCapitalCityReportDAO);
    }

    @Test
    @DisplayName("UC-21: rejects a blank continent without querying")
    void testDisplayTopNContinentCapitalCities_BlankContinent() {
        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNContinentCapitalCities(" ", 5);

        assertTrue(outContent.toString().contains("Invalid input. Please enter a valid continent."));
        verifyNoInteractions(mockCapitalCityReportDAO);
    }

    @Test
    @DisplayName("UC-21: validates N before the continent")
    void testDisplayTopNContinentCapitalCities_InvalidNTakesPriority() {
        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNContinentCapitalCities("", -1);

        String output = outContent.toString();
        assertTrue(output.contains("Invalid input. N must be a positive whole number."));
        assertFalse(output.contains("Please enter a valid continent."));
        verifyNoInteractions(mockCapitalCityReportDAO);
    }

    @Test
    @DisplayName("UC-21: tells the user when the continent has no capital cities")
    void testDisplayTopNContinentCapitalCities_NoResults() {
        when(mockCapitalCityReportDAO.getTopNContinentCapitalCities("Atlantis", 5))
                .thenReturn(Optional.of(Collections.emptyList()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNContinentCapitalCities("Atlantis", 5);

        assertTrue(outContent.toString()
                .contains("No capital cities found for the specified continent. Please enter a valid continent."));
    }

    // ---------- UC-22 ----------

    @Test
    @DisplayName("UC-22: prints the top N region capital city table when data is available")
    void testDisplayTopNRegionCapitalCities_Success() {
        when(mockCapitalCityReportDAO.getTopNRegionCapitalCities("Eastern Asia", 3)).thenReturn(Optional.of(sampleReports()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNRegionCapitalCities("Eastern Asia", 3);

        String output = outContent.toString();
        assertTrue(output.contains("Display top 3 capital cities in region: Eastern Asia"));
        assertSampleTable(output);
        verify(mockCapitalCityReportDAO, times(1)).getTopNRegionCapitalCities("Eastern Asia", 3);
    }

    @Test
    @DisplayName("UC-22: rejects invalid N without querying")
    void testDisplayTopNRegionCapitalCities_InvalidN() {
        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNRegionCapitalCities("Caribbean", -2);

        assertTrue(outContent.toString().contains("Invalid input. N must be a positive whole number."));
        verifyNoInteractions(mockCapitalCityReportDAO);
    }

    @Test
    @DisplayName("UC-22: rejects a blank region without querying")
    void testDisplayTopNRegionCapitalCities_BlankRegion() {
        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNRegionCapitalCities(null, 5);

        assertTrue(outContent.toString().contains("Invalid input. Please enter a valid region."));
        verifyNoInteractions(mockCapitalCityReportDAO);
    }

    @Test
    @DisplayName("UC-22: tells the user when the region has no capital cities")
    void testDisplayTopNRegionCapitalCities_NoResults() {
        when(mockCapitalCityReportDAO.getTopNRegionCapitalCities("Mordor", 5))
                .thenReturn(Optional.of(Collections.emptyList()));

        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNRegionCapitalCities("Mordor", 5);

        assertTrue(outContent.toString()
                .contains("No capital cities found for the specified region. Please enter a valid region."));
    }

    @Test
    @DisplayName("UC-22: prints the connection error when the DAO fails")
    void testDisplayTopNRegionCapitalCities_DatabaseFailure() {
        when(mockCapitalCityReportDAO.getTopNRegionCapitalCities("Caribbean", 5)).thenReturn(Optional.empty());

        new CapitalCityReportService(mockCapitalCityReportDAO).displayTopNRegionCapitalCities("Caribbean", 5);

        assertTrue(outContent.toString().contains("Database connection lost. Please check configuration."));
    }
}
