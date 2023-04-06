/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.pojo;

import java.time.LocalDateTime;

/**
 *
 * @author HOA TRƯƠNG
 */
public class Ticket {

    private int id;
    private String chair;
    private String status;
    private LocalDateTime print_date;
    private int trip_id;
    private int customer_id;
    private int user_id;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }
    private static int count = 0;

    {
        this.id = count++;
    }

    public Ticket() {
    }

    public Ticket(String chair, String status, String print_date, int trip_id, int customer_id, int user_id) {
        this.chair = chair;
        this.status = status;
        if (print_date != null) {
            this.print_date = LocalDateTime.parse(print_date, Trip.formatDate);
        } else {
            this.print_date = null;
        }
        this.trip_id = trip_id;
        this.customer_id = customer_id;
        this.user_id = user_id;
    }

    public Ticket(int id, String chair, String status, String print_date, int trip_id, int customer_id, int user_id) {
        this.id = id;
        this.chair = chair;
        this.status = status;
        if (print_date != null) {
            this.print_date = LocalDateTime.parse(print_date, Trip.formatDate);
        } else {
            this.print_date = null;
        }
        this.trip_id = trip_id;
        this.customer_id = customer_id;
        this.user_id = user_id;
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
     * @return the chair
     */
    public String getChair() {
        return chair;
    }

    /**
     * @param chair the chair to set
     */
    public void setChair(String chair) {
        this.chair = chair;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the print_date
     */
    public LocalDateTime getPrint_date() {
        return print_date;
    }

    /**
     * @param print_date the start to set
     */
    public void setPrint_date(LocalDateTime print_date) {
        this.print_date = print_date;
    }

    /**
     * @return the trip_id
     */
    public int getTrip_id() {
        return trip_id;
    }

    /**
     * @param trip_id the trip_id to set
     */
    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    /**
     * @return the customer_id
     */
    public int getCustomer_id() {
        return customer_id;
    }

    /**
     * @param customer_id the customer_id to set
     */
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return Integer.toString(this.id); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    

}
