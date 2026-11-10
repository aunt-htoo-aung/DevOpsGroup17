package com.devops.group17.LanguageReport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LanguageReportDAO Unit Tests")
class LanguageReportDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @Test
    @DisplayName("Successfully retrieves and maps language report records")
    void testGetLanguageReport_Success() throws SQLException {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        // Simulate 3 rows in result set, then finish
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("Language")).thenReturn("Chinese", "English");
        when(mockResultSet.getLong("TotalSpeakers")).thenReturn(1191843539L, 347077867L);
        when(mockResultSet.getDouble("WorldPercentage")).thenReturn(19.61, 5.71);

        LanguageReportDAO dao = new LanguageReportDAO(mockConnection);
        Optional<List<LanguageReport>> result = dao.getLanguageReport();

        assertTrue(result.isPresent());
        List<LanguageReport> reports = result.get();
        assertEquals(2, reports.size());

        LanguageReport r1 = reports.get(0);
        assertEquals("Chinese", r1.getLanguage());
        assertEquals(1191843539L, r1.getTotalSpeakers());
        assertEquals(19.61, r1.getWorldPercentage(), 0.001);

        LanguageReport r2 = reports.get(1);
        assertEquals("English", r2.getLanguage());
        assertEquals(347077867L, r2.getTotalSpeakers());
        assertEquals(5.71, r2.getWorldPercentage(), 0.001);


        verify(mockConnection, times(1)).createStatement();
        verify(mockStatement, times(1)).executeQuery(anyString());
        verify(mockResultSet, times(3)).next();
    }

}
