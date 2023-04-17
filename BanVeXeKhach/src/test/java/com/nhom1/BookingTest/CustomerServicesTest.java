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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 *
 * @author HOA TRƯƠNG
 */
public class CustomerServicesTest {
    private static Connection conn;
    CustomerServices cs = new CustomerServices();
    
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
    public  void testGetLastCustomerIDFail() throws SQLException {
        int cus = CustomerServices.getLastCustomerID();
        assertFalse(cus == 100);
    }
        
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
    
    @ParameterizedTest
    @CsvFileSource(resources = "/testCustomerData.csv", numLinesToSkip = 0)
    public void testGetCustomer(int id, String name, String phone, boolean eptOutput) {
        try {
            Customer cus = CustomerServices.getCustomer(name, phone);
            assertEquals(eptOutput, cus != null && cus.getId() == id);
            if (cus != null) {
                assertTrue(cus.getName().equals(name));
                assertTrue(cus.getPhone().equals(phone));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "/testCustomerData.csv", numLinesToSkip = 0)
    public void testGetCustomerByID(int id, String name, String phone, boolean eptOutput) {
        try {
            Customer cus = cs.getCustomerByID(id);
            assertEquals(eptOutput, cus != null && cus.getId() == id); 
            if (cus != null) {
                assertTrue(cus.getName().equals(name));
                assertTrue(cus.getPhone().equals(phone));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
