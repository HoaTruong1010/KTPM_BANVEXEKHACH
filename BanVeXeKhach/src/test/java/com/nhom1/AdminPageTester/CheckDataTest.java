/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.nhom1.AdminPageTester;



import com.nhom1.pojo.Trip;
import com.nhom1.utils.CheckData;
import java.sql.SQLException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author HOA TRƯƠNG
 */
public class CheckDataTest {

    /**
     * Test of isDateTimeFormat method, of class CheckData.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/datetimedata.csv",numLinesToSkip = 1)
    public void testIsDateTimeFormat(String text,boolean expResult) {
        boolean result = CheckData.isDateTimeFormat(text);
        assertEquals(expResult, result);
    }

    /**
     * Test of isDouble method, of class CheckData.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/doubledata.csv",numLinesToSkip = 1)
    public void testIsDouble(String text,boolean expResult) {
        boolean result = CheckData.isDouble(text);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInteger method, of class CheckData.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/integerdata.csv",numLinesToSkip = 1)
    public void testIsInteger(String text,boolean expResult) {
        boolean result = CheckData.isInteger(text);
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidTrip method, of class CheckData.
     */
    @Test
    public void testIsValidTripNo1() throws Exception {
        Trip trip = new Trip(6, "2023-03-30 03:45:00", "2023-03-30 05:45:00", 150, 4, 4);
        int expResult = 0;
        int result = CheckData.isValidTrip(trip);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsValidTripNo2() throws Exception {
        Trip trip = new Trip(6, "2023-03-30 03:45:00", "2023-03-30 02:45:00", 150, 4, 4);
        int expResult = -1;
        int result = CheckData.isValidTrip(trip);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsValidTripNo3() throws Exception {
        Trip trip = new Trip(6, "2023-04-12 13:00:00", "2023-04-12 13:12:00", 150, 1, 4);
        int expResult = -1;
        int result = CheckData.isValidTrip(trip);
        assertEquals(expResult, result);
    }

//    @ParameterizedTest
//    @MethodSource(value = "tripData")
//    public void testIsValidTrip(Trip trip, int expResult) throws SQLException { 
//        int result = CheckData.isValidTrip(trip);
//        assertEquals(expResult, result);
//    }
//    
//    static Stream<Arguments> tripData() {
//        return Stream.of (
//                Arguments.arguments(new Trip(6, "2023-03-30 03:45:00", "2023-03-30 05:45:00", 150, 4, 4), 0),
//                Arguments.arguments(new Trip(7, "2023-03-30 03:45:00", "2023-03-30 02:45:00", 150, 4, 4), -3)
//        );
//    }
}
