/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.services;

import com.nhom1.pojo.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HOA TRƯƠNG
 */
public class CustomerServices {   

    public static int getLastCustomerID() throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT id FROM customer ORDER BY id DESC LIMIT 1;");

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
            return -1;
        }
    }
    
    public static boolean isExistCustomer(Customer customer) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT id FROM customer WHERE name = ? && phone = ?;");
            stm.setString(1, customer.getName());
            stm.setString(2, customer.getPhone());

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        }
    }
    
    public static boolean isExistCustomer(String name, String phone) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT id FROM customer WHERE name = ? && phone = ?;");
            stm.setString(1, name);
            stm.setString(2, phone);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        }
    }
    
    public static Customer getCustomer(String name, String phone) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            Customer cus;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM customer WHERE name = ? && phone = ?;");
            stm.setString(1, name);
            stm.setString(2, phone);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return cus = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"));
            }
            return null;
        }
    }
}
