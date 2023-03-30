/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.nhom1.AdminPageTest;

import com.nhom1.pojo.Car;
import com.nhom1.services.CarServices;
import com.nhom1.services.JDBCUtils;
import com.nhom1.services.TripServices;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author HOA TRƯƠNG
 */
public class CarServicesTest {
    private static Connection conn;
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
        conn = JDBCUtils.createConn();
    }
    
    @AfterAll
    public static void tearDownClass() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Test of getCarById method, of class CarServices.
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, -100})
    public  void testGetNullCarByID(int id) throws SQLException {
        Car car = CarServices.getCarById(id);
        assertNull(car);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public  void testGetValidCarByID(int id) throws SQLException {
        Car car = CarServices.getCarById(id);
        assertNotNull(car);
    }
    
}
