package com.devops.group17.PopulationReport;

import com.devops.group17.Population_Report.PopulationReport;
import com.devops.group17.Population_Report.PopulationReportDAO;
import com.devops.group17.Population_Report.PopulationReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PopulationReportService Unit Tests")
class PopulationReportServiceTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PopulationReportDAO mockDao;

    private PopulationReportService service;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        // Service constructor expects a Connection, which creates its own DAO.
        // We'll replace the internal DAO via reflection for testing.
        service = new PopulationReportService(mockConnection);
        // Inject mocked DAO
        try {
            java.lang.reflect.Field field = PopulationReportService.class.getDeclaredField("populationReportDAO");
            field.setAccessible(true);
            field.set(service, mockDao);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifies that {@link PopulationReportService#displayContinentPopulationReport()}
     * prints the continent report header and sample continent data when the DAO returns reports.
     */
    @Test
    void displayContinentPopulationReport_HappyPath() {
        List<PopulationReport> sample = sampleReports();
        when(mockDao.getContinentPopulationReport()).thenReturn(Optional.of(sample));

        service.displayContinentPopulationReport();
        String output = outContent.toString();
        assertTrue(output.contains("Display Continent Population report"));
        assertTrue(output.contains("Asia"));
        verify(mockDao, times(1)).getContinentPopulationReport();
    }

    /**
     * Verifies that {@link PopulationReportService#displayRegionPopulationReport()}
     * prints the header without error and queries the DAO once when no region data is returned.
     */
    @Test
    void displayRegionPopulationReport_EmptyResult() {
        when(mockDao.getRegionPopulationReport()).thenReturn(Optional.empty());
        service.displayRegionPopulationReport();
        String output = outContent.toString();
        assertTrue(output.contains("Display Region Population report"));
        // Utils.printTable prints nothing for empty optional; just ensure no exception and DAO called.
        verify(mockDao, times(1)).getRegionPopulationReport();
    }

    /**
     * Verifies that {@link PopulationReportService#displayCountryPopulationReport()}
     * prints the country report header and expected country data when the DAO returns reports.
     */
    @Test
    void displayCountryPopulationReport_HappyPath() {
        List<PopulationReport> sample = sampleReports();
        when(mockDao.getCountryPopulationReport()).thenReturn(Optional.of(sample));
        service.displayCountryPopulationReport();
        String output = outContent.toString();
        assertTrue(output.contains("Display Country Population report"));
        assertTrue(output.contains("Japan"));
        verify(mockDao, times(1)).getCountryPopulationReport();
    }
    private List<PopulationReport> sampleReports() {
        List<PopulationReport> list = new ArrayList<>();
        PopulationReport r1 = new PopulationReport();
        r1.setName("Asia");
        r1.setTotalPopulation(4600000000L);
        r1.setCityPopulation(1500000000L);
        r1.setNonCityPopulation(3100000000L);
        list.add(r1);

        PopulationReport r2 = new PopulationReport();
        r2.setName("Europe");
        r2.setTotalPopulation(750000000L);
        r2.setCityPopulation(300000000L);
        r2.setNonCityPopulation(450000000L);
        list.add(r2);
        return list;
    }
}
