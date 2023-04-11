/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.nhom1.AdminPageTest;

import com.nhom1.pojo.Car;
import com.nhom1.services.CarServices;
import com.nhom1.services.JDBCUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 *
 * @author HOA TRƯƠNG
 */
public class CarServicesTest {

    private static Connection conn;
    private static CarServices carServices;

    @BeforeAll
    public static void setUpClass() throws SQLException {
        conn = JDBCUtils.createConn();
        carServices = new CarServices();
    }

    @AfterAll
    public static void tearDownClass() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Test of loadCars method, of class CarServices.
     * @throws java.sql.SQLException
     */
    @Test
    public void testDuplicateName() throws SQLException {
        List<Car> cars = carServices.loadCars();
        
        List<String> names = cars.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
        Set<String> testNames = new HashSet<>(names);

        assertEquals(names.size(), testNames.size());
    }   
    
    @Test
    public void testLoadCars() throws SQLException {
        List<Car> cars = carServices.loadCars();
        assertNotNull(cars);
    }

    /**
     * Test of getCarById method, of class CarServices.
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, -100})
    public void testGetNullCarByID(int id) throws SQLException {
        Car car = CarServices.getCarById(id);
        assertNull(car);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void testGetValidCarByID(int id) throws SQLException {
        Car car = CarServices.getCarById(id);
        assertNotNull(car);
    }

}
