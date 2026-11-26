
package com.devops.group17.CountryReport;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CountryReportDAO Unit Tests")
class CountryReportDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private void prepareMockResultSet() throws SQLException {

        when(mockConnection.prepareStatement(anyString()))
                .thenReturn(mockStatement);

        when(mockStatement.executeQuery())
                .thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);

        when(mockResultSet.getString("Code")).thenReturn("CHN");
        when(mockResultSet.getString("Name")).thenReturn("China");
        when(mockResultSet.getString("Continent")).thenReturn("Asia");
        when(mockResultSet.getString("Region")).thenReturn("Eastern Asia");
        when(mockResultSet.getLong("Population")).thenReturn(1411778724L);
        when(mockResultSet.getString("Capital")).thenReturn("Beijing");
    }

    private void assertCountryResult(Optional<List<CountryReport>> result) {

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());

        CountryReport report = result.get().get(0);

        assertEquals("CHN", report.getCode());
        assertEquals("China", report.getName());
        assertEquals("Asia", report.getContinent());
        assertEquals("Eastern Asia", report.getRegion());
        assertEquals(1411778724L, report.getPopulation());
        assertEquals("Beijing", report.getCapital());
    }

    @Test
    @DisplayName("UC-1: Successfully retrieves all countries worldwide")
    void testGetCountryReport_Success() throws SQLException {

        prepareMockResultSet();

        CountryReportDAO dao = new CountryReportDAO(mockConnection);

        Optional<List<CountryReport>> result = dao.getCountryReport();

        assertCountryResult(result);

        verify(mockConnection, times(1))
                .prepareStatement(anyString());

        verify(mockStatement, times(1)).executeQuery();
        verify(mockResultSet, times(2)).next();
    }

    @Test
    @DisplayName("UC-2: Successfully retrieves countries by continent")
    void testGetCountriesByContinent_Success() throws SQLException {

        prepareMockResultSet();

        CountryReportDAO dao = new CountryReportDAO(mockConnection);

        Optional<List<CountryReport>> result =
                dao.getCountriesByContinent("Asia");

        assertCountryResult(result);

        verify(mockStatement, times(1)).setObject(1, "Asia");
        verify(mockStatement, times(1)).executeQuery();
    }

    @Test
    @DisplayName("UC-3: Successfully retrieves countries by region")
    void testGetCountriesByRegion_Success() throws SQLException {

        prepareMockResultSet();

        CountryReportDAO dao = new CountryReportDAO(mockConnection);

        Optional<List<CountryReport>> result =
                dao.getCountriesByRegion("Eastern Asia");

        assertCountryResult(result);

        verify(mockStatement, times(1))
                .setObject(1, "Eastern Asia");

        verify(mockStatement, times(1)).executeQuery();
    }

    @Test
    @DisplayName("UC-4: Successfully retrieves Top N countries worldwide")
    void testGetTopNWorldCountries_Success() throws SQLException {

        prepareMockResultSet();

        CountryReportDAO dao = new CountryReportDAO(mockConnection);

        Optional<List<CountryReport>> result =
                dao.getTopNWorldCountries(10);

        assertCountryResult(result);

        verify(mockStatement, times(1)).setObject(1, 10);
        verify(mockStatement, times(1)).executeQuery();
    }

    @Test
    @DisplayName("UC-5: Successfully retrieves Top N countries in a continent")
    void testGetTopNContinentCountries_Success() throws SQLException {

        prepareMockResultSet();

        CountryReportDAO dao = new CountryReportDAO(mockConnection);

        Optional<List<CountryReport>> result =
                dao.getTopNContinentCountries("Asia", 5);

        assertCountryResult(result);

        verify(mockStatement, times(1)).setObject(1, "Asia");
        verify(mockStatement, times(1)).setObject(2, 5);
        verify(mockStatement, times(1)).executeQuery();
    }
}
