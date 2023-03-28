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
            String sql = "SELECT * FROM trip";
            if(kw != null && !kw.isEmpty())
                sql += " WHERE departing_at between ? and Date_add(?, INTERVAL 1 DAY);";
            
            PreparedStatement stm = conn.prepareStatement(sql);
            if(kw != null && !kw.isEmpty()) {
                stm.setString(1, kw);
                stm.setString(2, kw);
            }
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {                
                Trip t = new Trip(rs.getInt("id"), rs.getString("departing_at"), 
                        rs.getString("arriving_at"), rs.getDouble("price"), 
                        rs.getInt("car_id"), rs.getInt("route_id"));
                list.add(t);
            }
        }
        
        return list;        
    }
    
    
    public void addTrip(Trip t) throws SQLException {        
        try(Connection conn = JDBCUtils.createConn()) {     
            conn.setAutoCommit(false);
            String sql = "INSERT INTO trip(departing_at, arriving_at, price, car_id, route_id) "
                    + "VALUES(STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), ?, ?, ?);";
            
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, t.getDeparting_at());
            stm.setString(2, t.getArriving_at());
            stm.setDouble(3, t.getPrice());
            stm.setInt(4, t.getCar_id());
            stm.setInt(5, t.getRoute_id());
            
            stm.executeUpdate();
            
            conn.commit();
        }
    }
    
    
    public void editTrip(Trip t) throws SQLException {        
        try(Connection conn = JDBCUtils.createConn()) {     
            conn.setAutoCommit(false);
            String sql = "UPDATE trip SET departing_at = ?, arriving_at = ?, price = ?, car_id = ?, route_id = ? WHERE id = ?;";
            
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, t.getDeparting_at());
            stm.setString(2, t.getArriving_at());
            stm.setDouble(3, t.getPrice());
            stm.setInt(4, t.getCar_id());
            stm.setInt(5, t.getRoute_id());
            stm.setInt(6, t.getId());
            
            stm.executeUpdate();
            
            conn.commit();
        }
    }
    
    
    public void deleteTrip(int tripId) throws SQLException {        
        try(Connection conn = JDBCUtils.createConn()) {        
            conn.setAutoCommit(false);    
            String sql = "DELETE FROM trip WHERE id = ?";
            
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, tripId);
            
            stm.executeUpdate();
            
            conn.commit();
        }
    }
}
