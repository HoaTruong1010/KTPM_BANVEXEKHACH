/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.BookingTest;

import com.nhom1.pojo.Customer;
import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
import com.nhom1.services.CustomerServices;
import com.nhom1.services.JDBCUtils;
import com.nhom1.services.TicketServices;
import com.nhom1.services.TripServices;
import com.nhom1.utils.CheckData;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
//import java.sql.Statement;
import java.util.List;
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
public class TicketServicesTest {
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
    
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, -100})
    public  void testGetNullTicketByID(int id) throws SQLException {
        Ticket ticket = TicketServices.getTicketById(id);
        assertNull(ticket);
    }
    
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public  void testGetValidTicketByID(int id) throws SQLException {
        Ticket ticket = TicketServices.getTicketById(id);
        assertNotNull(ticket);
    }
        
    @Test
    public void testUpdateTicketSuccess() throws SQLException {
        List<Ticket> list = new ArrayList<>();
        
        Ticket t1 = TicketServices.getTicketById(17);
        Ticket t2 = TicketServices.getTicketById(18);
        Ticket t3 = TicketServices.getTicketById(19);
        Ticket t4 = TicketServices.getTicketById(20);
        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        assertTrue(TicketServices.updateTicket(list, 
                new Customer(CustomerServices.getLastCustomerID()+1,"nam", "0217849658"))); 
    }
    
    @Test
    public void testUpdateTicketFail() throws SQLException {
        List<Ticket> list = new ArrayList<>();
        
        Ticket t1 = TicketServices.getTicketById(29);
        Ticket t2 = TicketServices.getTicketById(30);
        list.add(t1);
        list.add(t2);
        assertFalse(TicketServices.updateTicket(list, 
                new Customer(CustomerServices.getLastCustomerID()+1,"nam", "0217849658"))); 
    }
}
