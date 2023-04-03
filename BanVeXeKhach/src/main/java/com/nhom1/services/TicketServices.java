/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.services;

import com.nhom1.pojo.Ticket;
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
        try(Connection conn = JDBCUtils.createConn()) {
            String sql = "SELECT * FROM ticket";
            if (ticket_id != null && !ticket_id.isEmpty()) {
                sql += " WHERE id like concat('%', ?, '%')";
            }            
            PreparedStatement stm = conn.prepareCall(sql);
            if (ticket_id != null && !ticket_id.isEmpty())
                stm.setString(1, ticket_id);
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
        try(Connection conn = JDBCUtils.createConn()) {
            String sql = "SELECT ticket.* " +
                    "FROM ticket, trip, "
                    + "route, customer" +
                    " WHERE ticket.customer_id = customer.id and ticket.trip_id = trip.id "
                    + "and trip.route_id = route.id"
                    + " and route.start like concat('%', ?, '%') "
                    + "and route.end like concat('%', ?, '%')"
                    + " and ticket.chair like concat('%', ?, '%')"
                    + " and DATE(trip.departing_at) like concat('%', ?, '%')"
                    + "and DATE_FORMAT(trip.departing_at, '%H:%i') like concat('%', ?, '%')";
            PreparedStatement stm = conn.prepareCall(sql);
            if (start != null && !start.isEmpty())
                stm.setString(1, start);
            else
                stm.setString(1, "");
            
            if (end != null && !end.isEmpty())
                stm.setString(2, end);
            else
                stm.setString(2, "");
            
            if (chair != null && !chair.isEmpty())
                stm.setString(3, chair);
            else
                stm.setString(3, "");
            
            if (startDate != null)
            {
                Date stDate = Date.valueOf(startDate);
                stm.setDate(4, stDate);
            }
            else
                stm.setString(4, "");
            
            if (startTime != null && !startTime.isEmpty())
                stm.setString(5, startTime);
            else
                stm.setString(5, "");
            
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
}
