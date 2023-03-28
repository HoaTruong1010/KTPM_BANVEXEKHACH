/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.utils;

import com.nhom1.pojo.Trip;
import com.nhom1.services.TripServices;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author HOA TRƯƠNG
 */
public class CheckData {  
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static boolean isDateTimeFormat(String text) {
        try {
            LocalDateTime.parse(text, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean isDouble(String text) {
        try {
            Double.valueOf(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isInteger(String text) {
        try {
            Integer.valueOf(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    
    public static boolean isValidTrip(Trip t) throws SQLException {
        TripServices ts = new TripServices();
        List<Trip> listTrip = ts.loadTrips(null);
        LocalDateTime tDeparting = LocalDateTime.parse(t.getDeparting_at(), formatter);
        LocalDateTime tripDeparting;
        LocalDateTime tripArriving;
        
        for (Trip trip : listTrip) {
            tripDeparting = LocalDateTime.parse(trip.getDeparting_at(), formatter);
            tripArriving = LocalDateTime.parse(trip.getArriving_at(), formatter);
            if(t.getCar_id() == trip.getCar_id())
                if(tripDeparting.compareTo(tDeparting) <= 0 && tripArriving.compareTo(tDeparting) >= 0) {
                    return false;
                }
        }
        return true;
    }
}
