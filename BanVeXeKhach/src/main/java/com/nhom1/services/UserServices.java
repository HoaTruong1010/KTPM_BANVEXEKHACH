/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.services;

import com.nhom1.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author HOA TRƯƠNG
 */
public class UserServices {

    public static User getUserByUsername(String username, String password) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            String sql = "SELECT * FROM user WHERE username = ? and password = ?;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, DigestUtils.md2Hex(password));
            
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), 
                        rs.getString("password"), rs.getString("user_role"), 
                        rs.getString("name"));
            }
            return null;
        }
    }
}
