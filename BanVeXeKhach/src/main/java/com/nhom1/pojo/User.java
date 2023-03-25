/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.pojo;

/**
 *
 * @author HOA TRƯƠNG
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String userRole;
    private String name;
    private static int count = 0;
    
    {
        this.id = count++;
    }

    public User() {
    }

    public User(String username, String password, String userRole, String name) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.name = name;
    }

    public User(int id, String username, String password, String userRole, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.name = name;
    }
    
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
