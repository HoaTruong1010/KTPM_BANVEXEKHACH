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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    @CsvFileSource(resources = "/SearchTicketByIDData.csv", numLinesToSkip = 1)
    public void testSearchByID(String id, int quantity, boolean eptOutput) {
        try {
            List<Ticket> tickets = ticketServices.loadTicketByID(id);
            Assertions.assertEquals(eptOutput, quantity == tickets.size());
            for (Ticket t: tickets) {
                Assertions.assertTrue(Integer.toString(t.getId()).contains(id));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "/SearchTicketByInfoData.csv", numLinesToSkip = 1)
    public void testSearchByInfo(String chair, String start, String end, String startDate, String startTime, int quantity, boolean eptOutput) {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            List<Ticket> tickets = ticketServices.loadTicketByInfo(start, end, chair, LocalDate.parse(startDate, format), startTime);
            Assertions.assertEquals(eptOutput, quantity == tickets.size());
            for (Ticket t: tickets) {
                Assertions.assertTrue(t.getChair().contains(chair));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void testChangeTicket() {
        
    }
}
