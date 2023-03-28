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
    private LocalDateTime tripDate;
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

    public Trip(LocalDateTime tripDate, double price, int car_id, int route_id) {
        this.tripDate = tripDate;
        this.price = price;
        this.car_id = car_id;
        this.route_id = route_id;
    }

    public Trip(int id, LocalDateTime tripDate, double price, int car_id, int route_id) {
        this.id = id;
        this.tripDate = tripDate;
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
     * @return the tripDate
     */
    public LocalDateTime getTripDate() {
        return tripDate;
    }

    /**
     * @param tripDate the tripDate to set
     */
    public void setTripDate(LocalDateTime tripDate) {
        this.tripDate = tripDate;
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
    
}
