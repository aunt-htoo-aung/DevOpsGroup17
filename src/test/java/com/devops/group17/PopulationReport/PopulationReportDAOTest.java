package com.devops.group17.PopulationReport;

import com.devops.group17.Population_Report.PopulationReport;
import com.devops.group17.Population_Report.PopulationReportDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("PopulationReportDAO Unit Tests")
class PopulationReportDAOTest {

    private final Connection mockConnection = mock(Connection.class);
    private final Statement mockStatement = mock(Statement.class);
    private final ResultSet mockResultSet = mock(ResultSet.class);

    private PopulationReportDAO createDao() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        return new PopulationReportDAO(mockConnection);
    }

    private void stubResultSetForRows(Object[][] rows) throws Exception {
        // Configure ResultSet to iterate over provided rows
        // rows is array of {Name, TotalPopulation, CityPopulation}
        when(mockResultSet.next()).thenAnswer(new org.mockito.stubbing.Answer<Boolean>() {
            private int index = -1;
            @Override
            public Boolean answer(org.mockito.invocation.InvocationOnMock invocation) {
                index++;
                return index < rows.length;
            }
        });
        for (int i = 0; i < rows.length; i++) {
            final int rowIdx = i;
            when(mockResultSet.getString("Name")).thenReturn((String) rows[rowIdx][0]);
            when(mockResultSet.getLong("TotalPopulation")).thenReturn((Long) rows[rowIdx][1]);
            when(mockResultSet.getLong("CityPopulation")).thenReturn((Long) rows[rowIdx][2]);
        }
    }

    /**
 * Tests the happy‑path for continent population report retrieval.
 * Ensures the DAO correctly maps result‑set rows to PopulationReport DTOs.
 */
@Test
    void testGetContinentPopulationReport_HappyPath() throws Exception {
        PopulationReportDAO dao = createDao();
        Object[][] data = {
                {"Asia", 4600000000L, 1500000000L},
                {"Europe", 750000000L, 300000000L}
        };
        stubResultSetForRows(data);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        Optional<List<PopulationReport>> result = dao.getContinentPopulationReport();
        assertTrue(result.isPresent());
        List<PopulationReport> reports = result.get();
        assertEquals(2, reports.size());
        PopulationReport asia = reports.get(0);
        assertEquals("Asia", asia.getName());
        assertEquals(4600000000L, asia.getTotalPopulation());
        assertEquals(1500000000L, asia.getCityPopulation());
        assertEquals(3100000000L, asia.getNonCityPopulation());
    }

    /**
 * Tests the happy‑path for region population report retrieval.
 * Verifies correct mapping of result‑set rows to DTOs.
 */
@Test
    void testGetRegionPopulationReport_HappyPath() throws Exception {
        PopulationReportDAO dao = createDao();
        Object[][] data = {{"Caribbean", 44_000_000L, 20_000_000L}};
        stubResultSetForRows(data);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        Optional<List<PopulationReport>> result = dao.getRegionPopulationReport();
        assertTrue(result.isPresent());
        PopulationReport report = result.get().get(0);
        assertEquals("Caribbean", report.getName());
        assertEquals(44_000_000L, report.getTotalPopulation());
        assertEquals(20_000_000L, report.getCityPopulation());
        assertEquals(24_000_000L, report.getNonCityPopulation());
    }

    /**
 * Tests the happy‑path for country population report retrieval.
 * Ensures correct mapping of result‑set rows to DTOs.
 */
@Test
    void testGetCountryPopulationReport_HappyPath() throws Exception {
        PopulationReportDAO dao = createDao();
        Object[][] data = {{"Japan", 126_000_000L, 80_000_000L}};
        stubResultSetForRows(data);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        Optional<List<PopulationReport>> result = dao.getCountryPopulationReport();
        assertTrue(result.isPresent());
        PopulationReport report = result.get().get(0);
        assertEquals("Japan", report.getName());
        assertEquals(126_000_000L, report.getTotalPopulation());
        assertEquals(80_000_000L, report.getCityPopulation());
        assertEquals(46_000_000L, report.getNonCityPopulation());
    }

    /**
 * Tests that DAO returns Optional.empty() when an exception occurs during query execution.
 */
@Test
    void testDaoReturnsEmptyOnException() throws Exception {
        PopulationReportDAO dao = createDao();
        when(mockStatement.executeQuery(anyString())).thenThrow(new RuntimeException("SQL error"));
        Optional<List<PopulationReport>> result = dao.getCountryPopulationReport();
        assertTrue(result.isEmpty());
    }
}
