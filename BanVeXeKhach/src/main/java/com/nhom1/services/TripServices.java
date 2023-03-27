/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.services;

import com.nhom1.pojo.Trip;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HOA TRƯƠNG
 */
public class TripServices {
    public List<Trip> loadTrips(String kw) throws SQLException {
        List<Trip> list = new ArrayList<>();
        
        try(Connection conn = JDBCUtils.createConn()) {
            String sql = "SELECT * FROM trip;";
            if(kw != null && !kw.isEmpty())
                sql += " WHERE ";
            
            PreparedStatement stm = conn.prepareStatement(sql);
            if(kw != null && !kw.isEmpty())
                stm.setString(1, kw);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {                
                Trip t = new Trip(rs.getInt("id"), rs.getString("trip_date"), 
                        rs.getDouble("price"), 
                        rs.getInt("car_id"), rs.getInt("route_id"));
                list.add(t);
            }
        }
        
        return list;        
    }
}
