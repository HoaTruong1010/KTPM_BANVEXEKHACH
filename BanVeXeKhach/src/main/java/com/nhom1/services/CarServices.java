/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.services;

import com.nhom1.pojo.Car;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HOA TRƯƠNG
 */
public class CarServices {
    public List<Car> loadCars() throws SQLException {
        List<Car> listCar = new ArrayList<>();
        
        try(Connection conn = JDBCUtils.createConn()) {
            Statement stm = conn.createStatement();            
            ResultSet rs = stm.executeQuery("SELECT * FROM car");
            
            while (rs.next()) {
                Car c = new Car(rs.getInt("id"), rs.getString("lisense_plate"), 
                        rs.getString("name"), rs.getInt("sum_chair"));
                listCar.add(c);
            }
        }
        
        return listCar;
    }

    public static Car getCarById(int id) throws SQLException {   
        Car c = null;
        try(Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM car WHERE id = ?");            
            stm.setInt(1, id);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                c = new Car(rs.getInt("id"), rs.getString("lisense_plate"), 
                        rs.getString("name"), rs.getInt("sum_chair"));
                break;
            }
        }
        
        return c;
    }
}
