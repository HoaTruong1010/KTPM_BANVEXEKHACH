/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.pojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author HOA TRƯƠNG
 */
public class Trip {
    private int id;
    private LocalDateTime departing_at;
    private LocalDateTime arriving_at;
    private double price;
    private int car_id;
    private int route_id;
    private static int count = 0;
    public static DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    {
        this.id = count++;
    }

    public Trip() {
    }

    public Trip(String departing_at, String arriving_at, double price, int car_id, int route_id) {
        this.departing_at = LocalDateTime.parse(departing_at, formatDate);
        this.arriving_at = LocalDateTime.parse(arriving_at, formatDate);
        this.price = price;
        this.car_id = car_id;
        this.route_id = route_id;
    }

    public Trip(int id, String departing_at, String arriving_at, double price, int car_id, int route_id) {
        this.id = id;
        this.departing_at = LocalDateTime.parse(departing_at, formatDate);
        this.arriving_at = LocalDateTime.parse(arriving_at, formatDate);
        this.price = price;
        this.car_id = car_id;
        this.route_id = route_id;
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
     * @return the departing_at
     */
    public String getDeparting_at() {
        return this.departing_at.format(formatDate);
    }

    /**
     * @param tripDate the departing_at to set
     */
    public void setDeparting_at(String tripDate) {
        this.departing_at = LocalDateTime.parse(tripDate, formatDate);
    }
    
    /**
     * @return the arriving_at
     */
    public String getArriving_at() {
        return this.arriving_at.format(formatDate);
    }

    /**
     * @param arriving_at the arriving_at to set
     */
    public void setArriving_at(String arriving_at) {
        this.arriving_at = LocalDateTime.parse(arriving_at, formatDate);
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the car_id
     */
    public int getCar_id() {
        return car_id;
    }

    /**
     * @param car_id the car_id to set
     */
    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    /**
     * @return the route_id
     */
    public int getRoute_id() {
        return route_id;
    }

    /**
     * @param route_id the route_id to set
     */
    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    @Override
    public String toString() {
        return String.format("%s", this.departing_at); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
}
