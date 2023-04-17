/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.nhom1.services;

import com.nhom1.pojo.User;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author HOA TRƯƠNG
 */
public class UserServicesTest {
    private static Connection conn;
    
    
    @BeforeAll
    public static void BeforeALL() throws SQLException {
        conn = JDBCUtils.createConn();
    }
    
    @AfterAll
    public static void AfterAll() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Test of getUserByUsername method, of class UserServices.
     * @throws SQLException
     */
    @Test
    public void testGetUserByUsernameSuccess() throws SQLException {
        System.out.println("getUserByUsername");
        String username = "u1";
        String password = "1";
        User expResult = new User(1, "u1", "1", "Admin", "Truong Thi Kim Hoa");
        User result = UserServices.getUserByUsername(username, password);
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getUsername(), result.getUsername());
        assertEquals(expResult.getPassword(), result.getPassword());
        assertEquals(expResult.getUserRole(), result.getUserRole());
        assertEquals(expResult.getUsername(), result.getUsername());
    }
    
    @Test
    public void testGetUserByUsernameFailure() throws SQLException {
        System.out.println("getNullUserByUsername");
        String username = "u10";
        String password = "1";
        User result = UserServices.getUserByUsername(username, password);
        assertNull(result);
    }
}
