/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.AdminPageTest;

import com.nhom1.pojo.Trip;
import com.nhom1.services.CarServices;
import com.nhom1.services.JDBCUtils;
import com.nhom1.services.TripServices;
import java.sql.SQLException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.sql.Connection;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 *
 * @author HOA TRƯƠNG
 */
public class TripServicesTest {

    private static Connection conn;
    private static TripServices tripServices;

    @BeforeAll
    public static void BeforeALL() throws SQLException {
        conn = JDBCUtils.createConn();
        tripServices = new TripServices();
    }

    @AfterAll
    public static void AfterAll() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
    
    @Test
    public void testLoadCars() throws SQLException {
        List<Trip> trips = tripServices.loadTrips(null, 1);
        assertNotNull(trips);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/searchdata.csv", numLinesToSkip = 0)
    public void testSearch(String text, int routeID, boolean expResult) throws SQLException {
        List<Trip> listSearch = tripServices.loadTrips(text, routeID);
        boolean result = !listSearch.isEmpty();
        assertEquals(expResult, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, -100})
    public void testGetNullTripByID(int id) throws SQLException {
        Trip trip = TripServices.getTripById(id);
        assertNull(trip);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void testGetValidTripByID(int id) throws SQLException {
        Trip trip = TripServices.getTripById(id);
        assertNotNull(trip);
    }

    public static Stream<Arguments> addTripData() {
        return Stream.of(
                Arguments.arguments(new Trip("2023-04-28 01:45:00",
                        "2023-04-28 05:45:00", 150, 4, 5), 1),
                Arguments.arguments(new Trip("2023-04-28 01:45:00",
                        "2023-04-28 00:45:00", 150, 4, 5), -1),
                Arguments.arguments(new Trip("2023-04-28 01:45:00",
                        "2023-04-28 05:45:00", 150, 1, 5), -1),
                Arguments.arguments(new Trip("2023-04-28 00:30:00",
                        "2023-04-28 02:30:00", 150, 1, 5), -1),
                Arguments.arguments(new Trip("2023-04-28 01:30:00",
                        "2023-04-28 03:30:00", 150, 1, 2), -1)
        );
    }

    @ParameterizedTest
    @MethodSource("addTripData")
    public void testAddTrip(Trip trip, int expResult) throws SQLException {
        int total = 0;
        if (expResult == 1) {
            total = tripServices.loadTrips(null, 1).size() + 1;
        }
        int numChair = CarServices.getCarById(trip.getCar_id()).getSumChair();
        int result = tripServices.addTrip(trip, numChair);

        assertEquals(expResult, result);
        if (expResult == 1) {
            assertEquals(total, tripServices.loadTrips(null, 1).size());
        }
    }

    public static Stream<Arguments> editTripData() throws SQLException {
        int tripID = tripServices.getLastTripId();
        return Stream.of(
                Arguments.arguments(new Trip(tripID, "2023-04-28 00:45:00",
                        "2023-04-28 04:15:00", 150, 4, 5), 1),
                Arguments.arguments(new Trip(tripID, "2023-04-29 01:00:00",
                        "2023-04-29 04:45:00", 150, 3, 5), 1),
                Arguments.arguments(new Trip(tripID, "2023-04-29 06:45:00",
                        "2023-04-29 10:45:00", 150, 1, 5), 1),
                Arguments.arguments(new Trip(tripID, "2023-04-28 06:45:00",
                        "2023-04-28 01:45:00", 150, 1, 5), -1),
                Arguments.arguments(new Trip(tripID, "2023-04-28 06:45:00",
                        "2023-04-28 08:45:00", 150, 1, 5), -1),
                Arguments.arguments(new Trip(tripID, "2023-04-28 04:45:00",
                        "2023-04-28 08:45:00", 150, 1, 5), -1),
                Arguments.arguments(new Trip(tripID, "2023-04-28 04:45:00",
                        "2023-04-28 10:45:00", 150, 1, 5), -1),
                Arguments.arguments(new Trip(tripID, "2023-04-29 01:00:00",
                        "2023-04-29 04:45:00", 150, 5, 5), -2)
        );
    }

    @ParameterizedTest
    @MethodSource("editTripData")
    public void testEditTrip(Trip trip, int expResult) throws SQLException {
        int result = tripServices.editTrip(trip);
        assertEquals(expResult, result);
    }

    public static Stream<Arguments> deleteTripData() throws SQLException {
        int tripID = tripServices.getLastTripId();
        return Stream.of(
                Arguments.arguments(new Trip(tripID, "2023-03-30 03:45:00",
                        "2023-03-30 05:45:00", 250, 3, 5), true),
                Arguments.arguments(new Trip(100, "2023-03-30 03:45:00",
                        "2023-03-30 05:45:00", 250, 3, 5), false)
        );
    }

    @ParameterizedTest
    @MethodSource("deleteTripData")
    public void testDeleteTrip(Trip trip, boolean expResult) throws SQLException {
        int total = 0;
        if (expResult) {
            total = tripServices.loadTrips(null, 1).size() - 1;
        }
        boolean result = tripServices.deleteTrip(trip);
        assertEquals(expResult, result);
        if (expResult) {
            assertEquals(total, tripServices.loadTrips(null, 1).size());
        }
    }
}
