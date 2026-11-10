package com.devops.group17.LanguageReport;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("LanguageReportService Unit Tests")
class LanguageReportServiceTest {

    @Mock
    private LanguageReportDAO mockLanguageReportDAO;

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

    @Test
    @DisplayName("Constructor with Connection initializes service correctly")
    void testConstructorWithConnection() {
        LanguageReportService service = new LanguageReportService(mockConnection);
        assertNotNull(service);
    }

    @Test
    @DisplayName("displayLanguageReport prints table with correct headers and values when data is available")
    void testDisplayLanguageReport_Success() {
        List<LanguageReport> mockList = Arrays.asList(
                new LanguageReport("Chinese", 1191843539L, 19.61),
                new LanguageReport("English", 347077867L, 5.71),
                new LanguageReport("Hindi", 405110543L, 6.66),
                new LanguageReport("Spanish", 355029462L, 5.84),
                new LanguageReport("Arabic", 233839238L, 3.85)
        );

        when(mockLanguageReportDAO.getLanguageReport()).thenReturn(Optional.of(mockList));

        LanguageReportService service = new LanguageReportService(mockLanguageReportDAO);
        service.displayLanguageReport();

        String output = outContent.toString();

        assertTrue(output.contains("Display language report"));
        assertTrue(output.contains("Language"));
        assertTrue(output.contains("Total Speakers"));
        assertTrue(output.contains("World Percentage (%)"));
        assertTrue(output.contains("Chinese"));
        assertTrue(output.contains("English"));
        assertTrue(output.contains("Hindi"));
        assertTrue(output.contains("Spanish"));
        assertTrue(output.contains("Arabic"));
        assertTrue(output.contains("1191843539"));
        assertTrue(output.contains("19.61"));

        verify(mockLanguageReportDAO, times(1)).getLanguageReport();
    }

}
