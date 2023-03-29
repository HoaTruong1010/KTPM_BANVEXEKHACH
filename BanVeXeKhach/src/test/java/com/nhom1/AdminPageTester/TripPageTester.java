/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.AdminPageTester;

import com.nhom1.pojo.Trip;
import com.nhom1.services.JDBCUtils;
import com.nhom1.services.TripServices;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author HOA TRƯƠNG
 */
public class TripPageTester {
    private static Connection conn;
    private static TripServices tripServies;
    
    @BeforeAll
    public static void BeforeALL() throws SQLException {
        conn = JDBCUtils.createConn();
        tripServies = new TripServices();
    }
    @Test
    public void testSearch() {
        
        Assertions.assertTrue(0<1);
    }
    @AfterAll
    public static void AfterAll() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
        
//    public  void testValidCarSchedule() throws SQLException {
//        List<Trip> listTrip = tripServies.loadTrips(null);
//        
//        
//    }
}
