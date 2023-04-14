/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.StaffPageTest;

import com.nhom1.pojo.Customer;
import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
import com.nhom1.pojo.User;
import com.nhom1.services.CustomerServices;
import com.nhom1.services.JDBCUtils;
import com.nhom1.services.TicketServices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
    @CsvFileSource(resources = "/searchTicketByIDData.csv", numLinesToSkip = 1)
    public void testSearchByID(String id, int quantity, boolean eptOutput) {
        try {
            List<Ticket> tickets = ticketServices.loadTicketByID(id);
            Assertions.assertEquals(eptOutput, quantity == tickets.size());
            for (Ticket t : tickets) {
                Assertions.assertTrue(Integer.toString(t.getId()).contains(id));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/searchTicketByInfoData.csv", numLinesToSkip = 1)
    public void testSearchByInfo(String chair, String start, String end, String startDate, String startTime, int quantity, boolean eptOutput) {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            List<Ticket> tickets = ticketServices.loadTicketByInfo(start, end, chair, LocalDate.parse(startDate, format), startTime);
            Assertions.assertEquals(eptOutput, quantity == tickets.size());
            for (Ticket t : tickets) {
                Assertions.assertTrue(t.getChair().contains(chair));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetByID() {
        try {
            Ticket tickets = TicketServices.getTicketById(1);
            Assertions.assertTrue(Integer.toString(tickets.getId()).contains(Integer.toString(1)));
        } catch (SQLException ex) {
            Logger.getLogger(TicketServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/getTicketByTripIDData.csv", numLinesToSkip = 1)
    public void testGetTicketByTripID(int tripID, int quantity, boolean expOutput) {
        try {
            List<Ticket> tickets = TicketServices.getTicketsByTripID(tripID);
            Assertions.assertEquals(expOutput, quantity == tickets.size());
            for (Ticket t : tickets) {
                Assertions.assertTrue(t.getTrip_id() == tripID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/getTicketByTripIDData.csv", numLinesToSkip = 1)
    public void testGetTicketByStringTripID(String tripID, int quantity, boolean expOutput) {
        try {
            List<Ticket> tickets = TicketServices.getTicketsByStringTripID(tripID);
            Assertions.assertEquals(expOutput, quantity == tickets.size());
            for (Ticket t : tickets) {
                Assertions.assertTrue(Integer.toString(t.getTrip_id()).contains(tripID));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testSaleSuccessful() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 246; i <= 249; i++) {
            PreparedStatement stm = conn.prepareCall("SELECT * FROM ticket WHERE id=?");
            stm.setInt(1, i);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket(rs.getInt("id"), rs.getString("chair"),
                        rs.getString("status"), rs.getString("print_date"),
                        rs.getInt("trip_id"), rs.getInt("customer_id"),
                        rs.getInt("user_id"));
                tickets.add(t);
            }
        }
        User u3 = new User(3, "u3", "1", "Employee", "Nguyen Van Hau");
        Customer cus = new Customer(3, "Smith", "0955478635");

        try {
            boolean actual = ticketServices.saleTicket(tickets, cus, u3);
            Assertions.assertTrue(actual);

            for (int i = 246; i <= 249; i++) {
                PreparedStatement stm = conn.prepareCall("SELECT * FROM ticket WHERE id=?");
                stm.setInt(1, i);

                ResultSet rs = stm.executeQuery();
                Assertions.assertNotNull(rs.next());
                Assertions.assertEquals("Sold", rs.getString("status"));
                Assertions.assertEquals(4, rs.getInt("trip_id"));
                PreparedStatement stm1 = conn.prepareCall("UPDATE ticket SET status = 'Empty',"
                        + " customer_id = null, print_date = null, user_id = null WHERE id = ?");
                stm1.setInt(1, i);

                stm1.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testSaleFail() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 250; i <= 252; i++) {
            PreparedStatement stm = conn.prepareCall("SELECT * FROM ticket WHERE id=?");
            stm.setInt(1, i);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket(rs.getInt("id"), rs.getString("chair"),
                        rs.getString("status"), rs.getString("print_date"),
                        rs.getInt("trip_id"), rs.getInt("customer_id"),
                        rs.getInt("user_id"));
                tickets.add(t);
            }
        }
        User u3 = new User(3, "u3", "1", "Employee", "Nguyen Van Hau");
//        Customer cus1 = new Customer(3, "Smith", "0955478635");
        Customer cus2 = new Customer(2, "Adam", "03877469461");

        try {
            boolean actual = ticketServices.saleTicket(tickets, cus2, u3);
            Assertions.assertFalse(actual);

            for (int i = 250; i <= 252; i++) {
                PreparedStatement stm = conn.prepareCall("SELECT * FROM ticket WHERE id=?");
                stm.setInt(1, i);

                ResultSet rs = stm.executeQuery();
                Assertions.assertNotNull(rs.next());
                Assertions.assertEquals(4, rs.getInt("trip_id"));
                PreparedStatement stm1 = conn.prepareCall("UPDATE ticket SET status = 'Empty',"
                        + " customer_id = null, print_date = null, user_id = null WHERE id = ?");
                stm1.setInt(1, i);

                stm1.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TicketServicesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //prepare data for all behind testing
    private void insertTicketBeforeTest(int ticketID, String status, String printDate) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("INSERT INTO ticket(id, chair, status, print_date, trip_id, customer_id, user_id) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?);");
        stm.setInt(1, ticketID);
        stm.setString(2, "test");
        stm.setString(3, status);
        stm.setString(4, printDate);
        stm.setInt(5, 1);
        stm.setInt(6, 1);
        stm.setInt(7, 1);
        stm.executeUpdate();
    }

    private void deleteTicketAfterTest(int ticketID) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("DELETE FROM ticket WHERE id = ?");
        stm.setInt(1, ticketID);
        stm.executeUpdate();
    }

    //Test updateTicket Method
    @Test
    public void testUpdateTicketSuccessWithExistCustomer() throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        int ticketID = TicketServices.getLastTicketId() + 1;
        insertTicketBeforeTest(ticketID, "Empty", null);
        int totalCustomer = CustomerServices.loadTrips().size();

        listTicket.add(TicketServices.getTicketById(ticketID));
        Customer customer = CustomerServices.getCustomer("Adam", "0387746946");
        Assertions.assertTrue(TicketServices.updateTicket(listTicket, customer));
        Assertions.assertEquals("Reserved", TicketServices.getTicketById(ticketID).getStatus());
        Assertions.assertEquals(totalCustomer, CustomerServices.loadTrips().size());

        deleteTicketAfterTest(ticketID);
    }

    @Test
    public void testUpdateTicketSuccessWithNotExistCustomer() throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        int ticketID = TicketServices.getLastTicketId() + 1;
        insertTicketBeforeTest(ticketID, "Empty", null);
        int totalCustomer = CustomerServices.loadTrips().size() + 1;

        listTicket.add(TicketServices.getTicketById(ticketID));
        Customer customer = new Customer(CustomerServices.getLastCustomerID() + 1, "test", "000000000");
        Assertions.assertTrue(TicketServices.updateTicket(listTicket, customer));
        Assertions.assertEquals("Reserved", TicketServices.getTicketById(ticketID).getStatus());
        Assertions.assertEquals(totalCustomer, CustomerServices.loadTrips().size());

        deleteTicketAfterTest(ticketID);
    }

    @Test
    public void testUpdateTicketFailure() throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        int ticketID = TicketServices.getLastTicketId() + 1;
        insertTicketBeforeTest(ticketID, "Recall", null);

        listTicket.add(TicketServices.getTicketById(ticketID));
        Customer customer = CustomerServices.getCustomer("Adam", "0387746946");
        Assertions.assertFalse(TicketServices.updateTicket(listTicket, customer));
        Assertions.assertEquals("Recall", TicketServices.getTicketById(ticketID).getStatus());

        deleteTicketAfterTest(ticketID);
    }

    //Test recallTicket Method
    @Test
    public void testRecallTicketSuccess() throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        int ticketID = TicketServices.getLastTicketId() + 1;
        insertTicketBeforeTest(ticketID, "Empty", null);

        listTicket.add(TicketServices.getTicketById(ticketID));
        Assertions.assertTrue(TicketServices.recallTicket(listTicket));
        Assertions.assertEquals("Recall", TicketServices.getTicketById(ticketID).getStatus());

        deleteTicketAfterTest(ticketID);
    }

    @Test
    public void testRecallTicketFailure() throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        int ticketID = TicketServices.getLastTicketId() + 1;
        insertTicketBeforeTest(ticketID, "Reserved", "2023-04-13 22:50:00");

        listTicket.add(TicketServices.getTicketById(ticketID));
        Assertions.assertFalse(TicketServices.recallTicket(listTicket));
        Assertions.assertEquals("Reserved", TicketServices.getTicketById(ticketID).getStatus());

        deleteTicketAfterTest(ticketID);
    }

    //Test resetTicket Method
    @Test
    public void testResetTicketSuccess() throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        int ticketID = TicketServices.getLastTicketId() + 1;
        insertTicketBeforeTest(ticketID, "Reserved", null);

        listTicket.add(TicketServices.getTicketById(ticketID));
        Assertions.assertTrue(TicketServices.resetTicket(listTicket));
        Assertions.assertEquals("Empty", TicketServices.getTicketById(ticketID).getStatus());

        deleteTicketAfterTest(ticketID);
    }

    @Test
    public void testResetTicketFailureNo1() throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        int ticketID = TicketServices.getLastTicketId() + 1;
        insertTicketBeforeTest(ticketID, "Reserved", "2023-04-13 22:50:00");

        listTicket.add(TicketServices.getTicketById(ticketID));
        Assertions.assertFalse(TicketServices.resetTicket(listTicket));
        Assertions.assertEquals("Reserved", TicketServices.getTicketById(ticketID).getStatus());

        deleteTicketAfterTest(ticketID);
    }

    @Test
    public void testResetTicketFailureNo2() throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        int ticketID = TicketServices.getLastTicketId() + 1;
        insertTicketBeforeTest(ticketID, "Empty", null);

        listTicket.add(TicketServices.getTicketById(ticketID));
        Assertions.assertFalse(TicketServices.resetTicket(listTicket));
        Assertions.assertEquals("Empty", TicketServices.getTicketById(ticketID).getStatus());

        deleteTicketAfterTest(ticketID);
    }

}
