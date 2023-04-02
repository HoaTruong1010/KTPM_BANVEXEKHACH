/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.services;

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
        try(Connection conn = JDBCUtils.createConn()) {
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
}
