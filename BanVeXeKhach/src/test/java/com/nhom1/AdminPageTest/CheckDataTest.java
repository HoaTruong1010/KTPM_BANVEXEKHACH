/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.nhom1.AdminPageTest;

import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
import com.nhom1.utils.CheckData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    @CsvFileSource(resources = "/datetimedata.csv", numLinesToSkip = 0)
    public void testIsDateTimeFormat(String text, boolean expResult) {
        boolean result = CheckData.isDateTimeFormat(text);
        assertEquals(expResult, result);
    }

    @ParameterizedTest
    @CsvSource({"@Hoa,false", "4Hoa,false", "Kim Hoa,true", "78888,false", "Trương Thị Kim Hoa,true"})
    public void testIsValidName(String name, boolean expected) {
        assertEquals(expected, CheckData.isValidName(name));
    }

    /**
     * Test of isDouble method, of class CheckData.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/doubledata.csv", numLinesToSkip = 0)
    public void testIsDouble(String text, boolean expResult) {
        boolean result = CheckData.isDouble(text);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInteger method, of class CheckData.
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/integerdata.csv", numLinesToSkip = 0)
    public void testIsInteger(String text, boolean expResult) {
        boolean result = CheckData.isInteger(text);
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidTrip method, of class CheckData.
     *
     * @return
     */
    public static Stream<Arguments> tripData() {
        return Stream.of(
                Arguments.arguments(new Trip(15, "2023-03-30 03:45:00",
                        "2023-03-30 05:45:00", 150, 4, 4), 1),
                Arguments.arguments(new Trip(15, "2023-03-30 03:45:00",
                        "2023-03-30 02:45:00", 150, 4, 4), -1),
                Arguments.arguments(new Trip(15, "2023-04-28 00:00:00",
                        "2023-04-28 01:15:00", 150, 1, 3), -1),
                Arguments.arguments(new Trip(15, "2023-04-28 02:00:00",
                        "2023-04-28 05:15:00", 150, 1, 3), -1),
                Arguments.arguments(new Trip(15, "2023-04-28 02:00:00",
                        "2023-04-28 03:15:00", 150, 1, 3), -1),
                Arguments.arguments(new Trip(2, "2023-04-28 05:45:00",
                        "2023-04-28 09:15:00", 150, 5, 2), -2),
                Arguments.arguments(new Trip(2, "2023-04-28 05:45:00",
                        "2023-04-28 09:15:00", 150, 4, 2), 1),
                Arguments.arguments(new Trip(1, "2023-04-28 00:30:00",
                        "2023-04-28 04:00:00", 150, 1, 4), 1),
                Arguments.arguments(new Trip(1, "2023-04-28 04:00:00",
                        "2023-04-28 00:15:00", 150, 1, 4), -1),
                Arguments.arguments(new Trip(1, "2023-04-28 03:00:00",
                        "2023-04-28 09:00:00", 150, 1, 4), -1),
                Arguments.arguments(new Trip(1, "2023-04-28 06:00:00",
                        "2023-04-28 09:00:00", 150, 1, 4), -1),
                Arguments.arguments(new Trip(1, "2023-04-28 06:00:00",
                        "2023-04-28 10:00:00", 150, 1, 4), -1)
        );
    }

    @ParameterizedTest
    @MethodSource("tripData")
    public void testIsValidTrip(Trip trip, int expResult) throws SQLException {
        int result = CheckData.isValidTrip(trip);
        assertEquals(expResult, result);
    }

    @Test
    public void testEmptyTicket() throws SQLException {
        List<Ticket> list = new ArrayList<>();
        Ticket t1 = new Ticket(10, "11", "Empty",
                null, 1, 1, 1);
        Ticket t2 = new Ticket(11, "11", "Empty",
                null, 1, 1, 1);
        list.add(t1);
        list.add(t2);
        assertTrue(CheckData.isEmptyTicket(list));
    }

    @Test
    public void testNotEmptyTicket() throws SQLException {
        List<Ticket> list = new ArrayList<>();
        Ticket t1 = new Ticket(10, "11", "Empty",
                null, 1, 1, 1);
        Ticket t2 = new Ticket(1, "1", "Reserved",
                null, 1, 1, 1);
        list.add(t1);
        list.add(t2);
        assertFalse(CheckData.isEmptyTicket(list));
    }

//    @ParameterizedTest
//    @CsvFileSource(resources = "/isChoosingData.csv", numLinesToSkip = 0)
//    public void isChoosing(String tripDeparting, int second, boolean expResult) {
//        assertEquals(expResult, CheckData.isChoosing(tripDeparting, second));
//    }
}
