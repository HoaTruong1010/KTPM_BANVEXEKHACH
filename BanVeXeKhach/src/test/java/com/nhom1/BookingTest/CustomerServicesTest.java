/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.BookingTest;

import com.nhom1.pojo.Customer;
import com.nhom1.pojo.Trip;
import com.nhom1.services.CustomerServices;
import com.nhom1.services.JDBCUtils;
import com.nhom1.services.TripServices;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author HOA TRƯƠNG
 */
public class CustomerServicesTest {
    private static Connection conn;
    
    @BeforeAll
    public static void BeforeALL() throws SQLException {
        conn = JDBCUtils.createConn();
    }
    
    @AfterAll
    public static void AfterAll() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
    
//    @Test
//    public  void testGetLastCustomerID() throws SQLException {
//        int cus = CustomerServices.getLastCustomerID();
//        assertEquals(5, cus);
//    }
        
    @Test
    public  void testExistCustomer() throws SQLException {
        boolean result = CustomerServices.isExistCustomer(new Customer("Jonny", "0359874164"));
        assertTrue(result);
    }
    
    @Test
    public  void testNotExistCustomer() throws SQLException {
        boolean result = CustomerServices.isExistCustomer(new Customer("dfsf", "0217849658"));
        assertFalse(result);
    }
}
