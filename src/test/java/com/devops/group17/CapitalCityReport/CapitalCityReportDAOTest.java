package com.devops.group17.CapitalCityReport;

import com.devops.group17.Capital_City_Report.CapitalCityReport;
import com.devops.group17.Capital_City_Report.CapitalCityReportDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CapitalCityReportDAO Unit Tests")
class CapitalCityReportDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    /** Prepares the mocks so that the DAO receives two rows: Seoul and Tokyo. */
    private void stubTwoRows() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("Name")).thenReturn("Seoul", "Tokyo");
        when(mockResultSet.getString("Country")).thenReturn("South Korea", "Japan");
        when(mockResultSet.getLong("Population")).thenReturn(9981619L, 7980230L);
    }

    private String capturedSql() throws SQLException {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockConnection).prepareStatement(captor.capture());
        return captor.getValue();
    }

    private void assertTwoRows(Optional<List<CapitalCityReport>> result) {
        assertTrue(result.isPresent());
        List<CapitalCityReport> reports = result.get();
        assertEquals(2, reports.size());

        assertEquals("Seoul", reports.get(0).getName());
        assertEquals("South Korea", reports.get(0).getCountry());
        assertEquals(9981619L, reports.get(0).getPopulation());

        assertEquals("Tokyo", reports.get(1).getName());
        assertEquals("Japan", reports.get(1).getCountry());
        assertEquals(7980230L, reports.get(1).getPopulation());
    }

    // ---------- UC-17 ----------

    @Test
    @DisplayName("UC-17: retrieves and maps all world capital cities")
    void testGetWorldCapitalCities_Success() throws SQLException {
        stubTwoRows();

        CapitalCityReportDAO dao = new CapitalCityReportDAO(mockConnection);
        assertTwoRows(dao.getWorldCapitalCities());

        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockStatement, times(1)).executeQuery();
        verify(mockResultSet, times(3)).next();
        verify(mockStatement, never()).setString(anyInt(), anyString());
        verify(mockStatement, never()).setInt(anyInt(), anyInt());
    }

    @Test
    @DisplayName("UC-17: query joins capital to city and sorts by population descending without filters")
    void testGetWorldCapitalCities_Sql() throws SQLException {
        stubTwoRows();

        new CapitalCityReportDAO(mockConnection).getWorldCapitalCities();

        String sql = capturedSql();
        assertTrue(sql.contains("country.Capital = city.ID"));
        assertTrue(sql.contains("ORDER BY city.Population DESC"));
        assertFalse(sql.contains("WHERE"));
        assertFalse(sql.contains("LIMIT"));
    }

    // ---------- UC-18 ----------

    @Test
    @DisplayName("UC-18: retrieves capital cities for a continent and binds the continent")
    void testGetContinentCapitalCities_Success() throws SQLException {
        stubTwoRows();

        CapitalCityReportDAO dao = new CapitalCityReportDAO(mockConnection);
        assertTwoRows(dao.getContinentCapitalCities("Asia"));

        assertTrue(capturedSql().contains("WHERE country.Continent = ?"));
        verify(mockStatement).setString(1, "Asia");
        verify(mockStatement, never()).setInt(anyInt(), anyInt());
    }

    // ---------- UC-19 ----------

    @Test
    @DisplayName("UC-19: retrieves capital cities for a region and binds the region")
    void testGetRegionCapitalCities_Success() throws SQLException {
        stubTwoRows();

        CapitalCityReportDAO dao = new CapitalCityReportDAO(mockConnection);
        assertTwoRows(dao.getRegionCapitalCities("Eastern Asia"));

        assertTrue(capturedSql().contains("WHERE country.Region = ?"));
        verify(mockStatement).setString(1, "Eastern Asia");
        verify(mockStatement, never()).setInt(anyInt(), anyInt());
    }

    // ---------- UC-20 ----------

    @Test
    @DisplayName("UC-20: retrieves the top N world capital cities and binds the limit")
    void testGetTopNWorldCapitalCities_Success() throws SQLException {
        stubTwoRows();

        CapitalCityReportDAO dao = new CapitalCityReportDAO(mockConnection);
        assertTwoRows(dao.getTopNWorldCapitalCities(2));

        String sql = capturedSql();
        assertTrue(sql.contains("ORDER BY city.Population DESC"));
        assertTrue(sql.contains("LIMIT ?"));
        assertFalse(sql.contains("WHERE"));
        verify(mockStatement).setInt(1, 2);
        verify(mockStatement, never()).setString(anyInt(), anyString());
    }

    // ---------- UC-21 ----------

    @Test
    @DisplayName("UC-21: retrieves the top N capital cities of a continent and binds continent then limit")
    void testGetTopNContinentCapitalCities_Success() throws SQLException {
        stubTwoRows();

        CapitalCityReportDAO dao = new CapitalCityReportDAO(mockConnection);
        assertTwoRows(dao.getTopNContinentCapitalCities("Europe", 2));

        String sql = capturedSql();
        assertTrue(sql.contains("WHERE country.Continent = ?"));
        assertTrue(sql.contains("LIMIT ?"));
        verify(mockStatement).setString(1, "Europe");
        verify(mockStatement).setInt(2, 2);
    }

    // ---------- UC-22 ----------

    @Test
    @DisplayName("UC-22: retrieves the top N capital cities of a region and binds region then limit")
    void testGetTopNRegionCapitalCities_Success() throws SQLException {
        stubTwoRows();

        CapitalCityReportDAO dao = new CapitalCityReportDAO(mockConnection);
        assertTwoRows(dao.getTopNRegionCapitalCities("Caribbean", 2));

        String sql = capturedSql();
        assertTrue(sql.contains("WHERE country.Region = ?"));
        assertTrue(sql.contains("LIMIT ?"));
        verify(mockStatement).setString(1, "Caribbean");
        verify(mockStatement).setInt(2, 2);
    }

    // ---------- Edge cases ----------

    @Test
    @DisplayName("Returns a present but empty list when no rows match")
    void testEmptyResultSet() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Optional<List<CapitalCityReport>> result =
                new CapitalCityReportDAO(mockConnection).getContinentCapitalCities("Nowhere");

        assertTrue(result.isPresent());
        assertTrue(result.get().isEmpty());
    }

    @Test
    @DisplayName("All six queries return Optional.empty when the statement cannot be prepared")
    void testAllQueriesReturnEmptyOnPrepareFailure() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Connection lost"));

        CapitalCityReportDAO dao = new CapitalCityReportDAO(mockConnection);

        assertTrue(dao.getWorldCapitalCities().isEmpty());
        assertTrue(dao.getContinentCapitalCities("Asia").isEmpty());
        assertTrue(dao.getRegionCapitalCities("Caribbean").isEmpty());
        assertTrue(dao.getTopNWorldCapitalCities(5).isEmpty());
        assertTrue(dao.getTopNContinentCapitalCities("Asia", 5).isEmpty());
        assertTrue(dao.getTopNRegionCapitalCities("Caribbean", 5).isEmpty());

        verify(mockConnection, times(6)).prepareStatement(anyString());
    }

    @Test
    @DisplayName("Returns Optional.empty when the query execution fails")
    void testReturnsEmptyOnExecuteFailure() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenThrow(new SQLException("Query failed"));

        Optional<List<CapitalCityReport>> result =
                new CapitalCityReportDAO(mockConnection).getWorldCapitalCities();

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Returns Optional.empty when reading a row fails")
    void testReturnsEmptyOnReadFailure() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("Name")).thenThrow(new SQLException("Bad column"));

        Optional<List<CapitalCityReport>> result =
                new CapitalCityReportDAO(mockConnection).getWorldCapitalCities();

        assertTrue(result.isEmpty());
    }
}
