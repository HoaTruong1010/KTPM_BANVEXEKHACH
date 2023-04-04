/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.services;

import com.nhom1.pojo.Customer;
import com.nhom1.pojo.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fptshop.com.vn
 */
public class TicketServices {

    public List<Ticket> loadTicket() throws SQLException {
        List<Ticket> list = new ArrayList<>();
        try (Connection conn = JDBCUtils.createConn()) {
            String sql = "SELECT * FROM ticket";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket(rs.getInt("id"), rs.getString("chair"),
                        rs.getString("status"), rs.getString("print_date"),
                        rs.getInt("trip_id"), rs.getInt("customer_id"),
                        rs.getInt("user_id"));
                list.add(t);
            }
        }
        return list;
    }

    public static int getLastTicketId() throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT id FROM ticket ORDER BY id DESC LIMIT 1;");

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        }
    }

    public static List<Ticket> getTicketsByTripID(int tripID) throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM ticket WHERE trip_id = ?;");
            stm.setInt(1, tripID);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket(rs.getInt("id"), rs.getString("chair"),
                        rs.getString("status"), rs.getString("print_date"),
                        rs.getInt("trip_id"), rs.getInt("customer_id"),
                        rs.getInt("user_id"));
                listTicket.add(t);
            }
            return listTicket;
        }
    }

    public static boolean updateTicket(int ticketId, Customer customer) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            conn.setAutoCommit(false);
            String sql;
            PreparedStatement stm;
            int result = 1;
            if (!CustomerServices.isFindCustomer(customer)) {
                sql = "INSERT INTO customer(id, name, phone) "
                        + "VALUES(?, ?, ?);";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, customer.getId());
                stm.setString(2, customer.getName());
                stm.setString(3, customer.getPhone());
                result = stm.executeUpdate();
            }

            if (result > 0) {
                sql = "UPDATE ticket SET status = ?, customer_id = ? WHERE id = ?;";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "Reserved");
                stm.setInt(2, customer.getId());
                stm.setInt(3, ticketId);
                stm.executeUpdate();
                conn.commit();
                return true;
            }

            return false;
        }
    }
}
