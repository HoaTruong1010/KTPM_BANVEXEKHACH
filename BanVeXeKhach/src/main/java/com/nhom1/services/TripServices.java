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

    public static Trip getTripById(int id) throws SQLException {   
        Trip t = null;
        try(Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM trip WHERE id = ?");            
            stm.setInt(1, id);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                t = new Trip(rs.getInt("id"), rs.getString("departing_at"), 
                        rs.getString("arriving_at"), rs.getDouble("price"), 
                        rs.getInt("car_id"), rs.getInt("route_id"));
                break;
            }
        }
        
        return t;
    }
    
    
    public void addTrip(Trip trip, int numChair) throws SQLException {        
        try(Connection conn = JDBCUtils.createConn()) {  
            conn.setAutoCommit(false);
            String sql = "INSERT INTO trip(departing_at, arriving_at, price, car_id, route_id) "
                    + "VALUES(STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), ?, ?, ?);";
            
            PreparedStatement stm1 = conn.prepareStatement(sql);
            stm1.setString(1, trip.getDeparting_at());
            stm1.setString(2, trip.getArriving_at());
            stm1.setDouble(3, trip.getPrice());
            stm1.setInt(4, trip.getCar_id());
            stm1.setInt(5, trip.getRoute_id());
            
            stm1.executeUpdate();
            
            for (int i = 1; i <= numChair; i++) {
                sql = "INSERT INTO ticket(chair, status, print_date, trip_id, customer_id, user_id) "
                        + "VALUES(?, ?, STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), ?, ?, ?);";
                PreparedStatement stm2 = conn.prepareStatement(sql);
                stm2.setString(1, String.format("%d", i));
                stm2.setString(2, "Empty");
                stm2.setString(3, null);
                stm2.setInt(4, trip.getId());
                stm2.setString(5, null);
                stm2.setString(6, null);
                
                stm2.executeUpdate();
            }
            
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
    
    
    public void deleteTrip(int tripId, int numChair) throws SQLException {        
        try(Connection conn = JDBCUtils.createConn()) {        
            conn.setAutoCommit(false);    
            String sql = "DELETE FROM trip WHERE id = ?";
            
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, tripId);
            
            stm.executeUpdate();
            
            for (int i = 1; i <= numChair; i++) {
                sql = "DELETE FROM ticket WHERE trip_id = ?";
                PreparedStatement stm2 = conn.prepareStatement(sql);
                stm2.setInt(1, tripId);
                
                stm2.executeUpdate();
            }
            
            conn.commit();
        }
    }
}
