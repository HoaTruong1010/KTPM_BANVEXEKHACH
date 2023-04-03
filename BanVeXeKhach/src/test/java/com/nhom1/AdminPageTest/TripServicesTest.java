/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.AdminPageTest;

import com.nhom1.pojo.Trip;
import com.nhom1.services.CarServices;
import com.nhom1.services.JDBCUtils;
import com.nhom1.services.TripServices;
import com.nhom1.utils.CheckData;
import java.sql.SQLException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.sql.Connection;
//import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 *
 * @author HOA TRƯƠNG
 */
public class TripServicesTest {
    private static Connection conn;
    private static TripServices tripServies;
    
    @BeforeAll
    public static void BeforeALL() throws SQLException {
        conn = JDBCUtils.createConn();
        tripServies = new TripServices();
    }
    
    @AfterAll
    public static void AfterAll() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
        
    @ParameterizedTest
    @CsvFileSource(resources = "/searchdata.csv", numLinesToSkip = 1)
    public void testSearch(String text, int routeID, boolean expResult) throws SQLException {
        List<Trip> listSearch = tripServies.loadTrips(text, routeID);
        boolean result = !listSearch.isEmpty();
        assertEquals(expResult, result);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, -100})
    public  void testGetNullTripByID(int id) throws SQLException {
        Trip trip = TripServices.getTripById(id);
        assertNull(trip);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public  void testGetValidTripByID(int id) throws SQLException {
        Trip trip = TripServices.getTripById(id);
        assertNotNull(trip);
    }
    
    public static Stream<Arguments> addTripData() {
        return Stream.of (
                Arguments.arguments(new Trip("2023-03-30 03:45:00", 
                        "2023-03-30 05:45:00", 250, 5, 5), 1),
                Arguments.arguments(new Trip("2023-03-30 03:45:00", 
                        "2023-03-30 02:45:00", 150, 4, 4), -1),
                Arguments.arguments(new Trip("2023-04-12 13:00:00", 
                        "2023-04-12 18:30:00", 450, 1, 2), -1),
                Arguments.arguments(new Trip("2023-04-12 14:30:00", 
                        "2023-04-13 02:30:00", 450, 1, 2), -1),
                Arguments.arguments(new Trip("2023-04-12 14:00:00", 
                        "2023-04-12 18:30:00", 150, 1, 2), -1),
                Arguments.arguments(new Trip("2023-04-13 13:00:00", 
                        "2023-04-13 18:15:00", 200, 1, 1), 1)
        );
    }
    
    @ParameterizedTest
    @MethodSource("addTripData")
    public void testAddTrip(Trip trip, int expResult) throws SQLException {
        int numChair = CarServices.getCarById(trip.getCar_id()).getSumChair();
        int result = tripServies.addTrip(trip, numChair);
        
        assertEquals(expResult, result);
    }
        
//    public static Stream<Arguments> editTripData() {
//        return Stream.of (
//                Arguments.arguments(new Trip(1, "2022-11-15 09:45:00", 
//                        "2022-11-15 10:45:00", 250, 1, 4), 1),
//                Arguments.arguments(new Trip(2, "2023-04-12 13:15:00", 
//                        "2023-04-11 18:15:00", 200, 1, 1), -1),
//                Arguments.arguments(new Trip(3, "2022-11-15 07:00:00", 
//                        "2022-11-15 10:00:00", 250, 1, 5), -1),
//                Arguments.arguments(new Trip(4, "2022-11-17 00:00:00", 
//                        "2022-11-17 01:00:00", 200, 3, 1), -2)
//        );
//    }
//    
//    @ParameterizedTest
//    @MethodSource("editTripData")
//    public  void testEditTrip(Trip trip, int expResult) throws SQLException {       
//        int result = tripServies.editTrip(trip);
//        assertEquals(expResult, result);
//    }
        
//    public static Stream<Arguments> deleteTripData() {
//        return Stream.of (
//                Arguments.arguments(new Trip(6, "2023-03-30 03:45:00", 
//                        "2023-03-30 05:45:00", 250, 3, 5), true),
//                Arguments.arguments(new Trip(7, "2023-04-13 13:00:00", 
//                        "2023-04-13 18:15:00", 200, 1, 1), true),
//                Arguments.arguments(new Trip(6, "2023-03-30 03:45:00", 
//                        "2023-03-30 05:45:00", 250, 3, 5), false),
//                Arguments.arguments(new Trip(7, "2023-04-13 13:00:00", 
//                        "2023-04-13 18:15:00", 200, 1, 1), false)
//        );
//    }
//    
//    @ParameterizedTest
//    @MethodSource("deleteTripData")
//    public  void testDeleteTrip(Trip trip, boolean expResult) throws SQLException {
//        boolean result = tripServies.deleteTrip(trip);
//        assertEquals(expResult, result);
//    }
}
