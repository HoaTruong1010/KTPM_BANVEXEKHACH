/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.StaffPageTest;

import com.nhom1.pojo.Ticket;
import com.nhom1.services.JDBCUtils;
import com.nhom1.services.TicketServices;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;

/**
 *
 * @author fptshop.com.vn
 */
public class TicketServicesTest {
    private static Connection conn;
    private static TicketServices ticketServices;
    
    @BeforeAll
    public static void BeforeALL() throws SQLException {
        conn = JDBCUtils.createConn();
        ticketServices = new TicketServices();
    }
    
    @AfterAll
    public static void AfterAll() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
    
    @ParameterizedTest
    public void testSearchByID() {
        try {
            List<Ticket> tickets = ticketServices.loadTicketByID("11");
            Assertions.assertEquals(1, tickets.size());
            for (Ticket t: tickets) {
                Assertions.assertTrue(Integer.toString(t.getId()).contains("11"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
