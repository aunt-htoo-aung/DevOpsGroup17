package com.devops.group17.CityReport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CityReportDAO Unit Tests")
class CityReportDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Test
    @DisplayName("Successfully retrieves and maps all world city report records [Req 7]")
    void testGetCityReport_Success() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("City")).thenReturn("Mumbai (Bombay)", "Seoul");
        when(mockResultSet.getString("Country")).thenReturn("India", "South Korea");
        when(mockResultSet.getString("District")).thenReturn("Maharashtra", "Seoul");
        when(mockResultSet.getLong("Population")).thenReturn(10500000L, 9981619L);

        CityReportDAO dao = new CityReportDAO(mockConnection);
        Optional<List<CityReport>> result = dao.getCityReport();

        assertTrue(result.isPresent());
        List<CityReport> reports = result.get();
        assertEquals(2, reports.size());

        assertEquals("Mumbai (Bombay)", reports.get(0).getName());
        assertEquals("India", reports.get(0).getCountry());
        assertEquals("Maharashtra", reports.get(0).getDistrict());
        assertEquals(10500000L, reports.get(0).getPopulation());

        assertEquals("Seoul", reports.get(1).getName());
        assertEquals("South Korea", reports.get(1).getCountry());
        assertEquals("Seoul", reports.get(1).getDistrict());
        assertEquals(9981619L, reports.get(1).getPopulation());

        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(3)).next();
    }

    @Test
    @DisplayName("Retrieves cities by continent with parameter binding [Req 8]")
    void testGetCityReportByContinent_Success() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("City")).thenReturn("Jakarta");
        when(mockResultSet.getString("Country")).thenReturn("Indonesia");
        when(mockResultSet.getString("District")).thenReturn("Jakarta Raya");
        when(mockResultSet.getLong("Population")).thenReturn(8761400L);

        CityReportDAO dao = new CityReportDAO(mockConnection);
        Optional<List<CityReport>> result = dao.getCityReportByContinent("Asia");

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals("Jakarta", result.get().get(0).getName());

        verify(mockPreparedStatement).setObject(1, "Asia");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    @DisplayName("Retrieves top N cities in the world with limit parameter [Req 12]")
    void testGetTopNWorldCities_Success() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("City")).thenReturn("Mumbai (Bombay)");
        when(mockResultSet.getString("Country")).thenReturn("India");
        when(mockResultSet.getString("District")).thenReturn("Maharashtra");
        when(mockResultSet.getLong("Population")).thenReturn(10500000L);

        CityReportDAO dao = new CityReportDAO(mockConnection);
        Optional<List<CityReport>> result = dao.getTopNWorldCities(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());

        verify(mockPreparedStatement).setObject(1, 1);
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    @DisplayName("Returns empty Optional when SQL exception occurs")
    void testGetCityReport_SQLException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        CityReportDAO dao = new CityReportDAO(mockConnection);
        Optional<List<CityReport>> result = dao.getCityReport();

        assertTrue(result.isEmpty());
    }
}