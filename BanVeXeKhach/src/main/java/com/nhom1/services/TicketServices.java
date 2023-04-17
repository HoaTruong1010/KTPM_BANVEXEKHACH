/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.services;

import com.nhom1.pojo.Customer;
import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.User;
import com.nhom1.utils.CheckData;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fptshop.com.vn
 */
public class TicketServices {

    public List<Ticket> loadTicketByID(String ticket_id) throws SQLException {
        List<Ticket> list = new ArrayList<>();
        try (Connection conn = JDBCUtils.createConn()) {
            String sql = "SELECT ticket.* FROM ticket, trip WHERE ticket.trip_id = trip.id And"
                    + " trip.departing_at > now()";
            if (ticket_id != null && !ticket_id.isEmpty()) {
                sql += "AND ticket.id like concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (ticket_id != null && !ticket_id.isEmpty()) {
                stm.setString(1, ticket_id);
            }
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

    public List<Ticket> loadTicketByInfo(String start, String end, String chair, LocalDate startDate, String startTime) throws SQLException {
        List<Ticket> list = new ArrayList<>();
        try (Connection conn = JDBCUtils.createConn()) {
            String sql = "SELECT ticket.*"
                    + " FROM ticket, trip, route"
                    + " WHERE ticket.trip_id = trip.id"
                    + " and trip.route_id = route.id"
                    + " and trip.departing_at > now()"
                    + " and route.start like concat('%', ?, '%')"
                    + " and route.end like concat('%', ?, '%')"
                    + " and ticket.chair like concat('%', ?, '%')"
                    + " and DATE(trip.departing_at) like concat('%', ?, '%')"
                    + " and DATE_FORMAT(trip.departing_at, '%H:%i') like concat('%', ?, '%')";
            PreparedStatement stm = conn.prepareCall(sql);
            if (start != null && !start.isEmpty()) {
                stm.setString(1, start);
            } else {
                stm.setString(1, "");
            }

            if (end != null && !end.isEmpty()) {
                stm.setString(2, end);
            } else {
                stm.setString(2, "");
            }

            if (chair != null && !chair.isEmpty()) {
                stm.setString(3, chair);
            } else {
                stm.setString(3, "");
            }

            if (startDate != null) {
                Date stDate = Date.valueOf(startDate);
                stm.setDate(4, stDate);
            } else {
                stm.setString(4, "");
            }

            if (startTime != null && !startTime.isEmpty()) {
                stm.setString(5, startTime);
            } else {
                stm.setString(5, "");
            }

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

    public static Ticket getTicketById(int id) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM ticket WHERE id = ?;");
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return new Ticket(rs.getInt("id"), rs.getString("chair"),
                        rs.getString("status"), rs.getString("print_date"),
                        rs.getInt("trip_id"), rs.getInt("customer_id"),
                        rs.getInt("user_id"));
            }
            return null;
        }
    }

    public static int getLastTicketId() throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT id FROM ticket ORDER BY id DESC LIMIT 1;");

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
            return 0;
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

    public static List<Ticket> getTicketsByStringTripID(String tripID) throws SQLException {
        List<Ticket> listTicket = new ArrayList<>();
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM ticket WHERE trip_id = ?;");
            stm.setString(1, tripID);

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

    public static boolean updateTicket(List<Ticket> listTicket, Customer customer) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            conn.setAutoCommit(false);
            boolean isExist = false;
            String sql;
            PreparedStatement stm;
            if (!CustomerServices.isExistCustomer(customer)) {
                sql = "INSERT INTO customer(id, name, phone) "
                        + "VALUES(?, ?, ?);";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, customer.getId());
                stm.setString(2, customer.getName());
                stm.setString(3, customer.getPhone());
                stm.executeUpdate();
            } else {
                customer = CustomerServices.getCustomer(customer.getName(), customer.getPhone());
                isExist = true;
            }

            if (CheckData.isEmptyTicket(listTicket)) {
                for (Ticket ticket : listTicket) {
                    sql = "UPDATE ticket SET status = ?, customer_id = ? WHERE id = ?;";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, "Reserved");
                    stm.setInt(2, customer.getId());
                    stm.setInt(3, ticket.getId());
                    stm.executeUpdate();
                }
                conn.commit();
                return true;
            } 
            
            if(!isExist) {
                sql = "DELETE FROM customer WHERE id = ? ";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, customer.getId());
            }
            
            return false;
        }
    }

    public boolean saleTicket(List<Ticket> listTicket, Customer customer, User user) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            conn.setAutoCommit(false);
            String sql;
            PreparedStatement stm;
            int result = 1;

            if (!CustomerServices.isExistCustomer(customer)) {
                sql = "INSERT INTO customer(id, name, phone) "
                        + "VALUES(?, ?, ?);";
                stm = conn.prepareStatement(sql);
                stm.setInt(1, customer.getId());
                stm.setString(2, customer.getName());
                stm.setString(3, customer.getPhone());
                result = stm.executeUpdate();

            }

            if (result > 0 && !CheckData.isSoldTicket(listTicket)) {
                for (Ticket ticket : listTicket) {
                    if ("Sold".equals(ticket.getStatus())) {
                        return false;
                    }
                    if ("Reserved".equals(ticket.getStatus())) {
                        if (customer.getId() != ticket.getCustomer_id()) {
                            return false;
                        }
                    }
                    sql = "UPDATE ticket SET status = ?, customer_id = ?, print_date = NOW(), user_id = ? WHERE id = ?;";
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, "Sold");
                    stm.setInt(2, customer.getId());
                    stm.setInt(3, user.getId());
                    stm.setInt(4, ticket.getId());
                    stm.executeUpdate();
                }
                conn.commit();
                return true;
            }

            return false;
        }
    }

    public boolean changeTicket(String idtk1, Ticket tk2) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;
            Ticket tk1 = null;
            if (idtk1 != null && !idtk1.isEmpty()) {
                PreparedStatement stm = conn.prepareStatement("SELECT * FROM ticket WHERE id = ?;");
                stm.setString(1, idtk1);
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    tk1 = new Ticket(rs.getInt("id"), rs.getString("chair"),
                            rs.getString("status"), rs.getString("print_date"),
                            rs.getInt("trip_id"), rs.getInt("customer_id"),
                            rs.getInt("user_id"));
                }
                if (!"Reserved".equals(tk1.getStatus()) || !"Empty".equals(tk2.getStatus())) {
                    return flag3;
                }
                flag1 = true;
            }

            conn.setAutoCommit(false);
            if ("Empty".equals(tk2.getStatus())) {
                String sql = "UPDATE ticket SET status = ?, customer_id = ? WHERE id = ?";
                PreparedStatement stm = conn.prepareCall(sql);
                stm.setString(1, tk1.getStatus());
                stm.setInt(2, tk1.getCustomer_id());
                stm.setInt(3, tk2.getId());
                stm.executeUpdate();
                flag2 = true;
            }
            if ("Reserved".equals(tk1.getStatus())) {
                String sql = "UPDATE ticket SET status = 'Empty', customer_id = NULL WHERE id = ?";
                PreparedStatement stm = conn.prepareCall(sql);
                stm.setInt(1, tk1.getId());
                stm.executeUpdate();
                if (flag1 && flag2) {
                    flag3 = true;
                }
            }
            conn.commit();
            return flag3;
        }
    }

    public boolean cancelTicket(int id) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            if (Integer.toString(id) != null && !Integer.toString(id).isEmpty()) {
                Ticket t = TicketServices.getTicketById(id);
                if ("Reserved".equals(t.getStatus())) {
                    String sql = "UPDATE ticket SET status = 'Empty', customer_id = null WHERE id = ? AND status = 'Reserved'";
                    PreparedStatement stm = conn.prepareCall(sql);
                    stm.setInt(1, id);
                    stm.executeUpdate();
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean recallTicket(List<Ticket> listTicket) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            conn.setAutoCommit(false);
            String sql;
            PreparedStatement stm;
            int result = -1;

            for (Ticket ticket : listTicket) {
                sql = "UPDATE ticket SET status = ? WHERE id = ? and status = ?;";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "Recall");
                stm.setInt(2, ticket.getId());
                stm.setString(3, "Empty");
                result = stm.executeUpdate();
            }
            conn.commit();

            return result > 0;
        }
    }

    public static boolean resetTicket(List<Ticket> listTicket) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            conn.setAutoCommit(false);
            String sql;
            PreparedStatement stm;
            int result = -1;

            for (Ticket ticket : listTicket) {
                sql = "UPDATE ticket SET status = ?, customer_id = ? WHERE id = ? and print_date IS NULL and status = ?;";
                stm = conn.prepareStatement(sql);
                stm.setString(1, "Empty");
                stm.setString(2, null);
                stm.setInt(3, ticket.getId());
                stm.setString(4, "Reserved");
                result = stm.executeUpdate();
            }
            conn.commit();

            return result > 0;
        }
    }
}
