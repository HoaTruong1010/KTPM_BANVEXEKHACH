/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.utils;

import com.nhom1.pojo.Trip;
import com.nhom1.services.CarServices;
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
    
    public static boolean isValidSumChair(Trip fixedTrip, Trip dynamicTrip) throws SQLException {
        return !(fixedTrip.getId() == dynamicTrip.getId() && 
                CarServices.getCarById(fixedTrip.getCar_id()).getSumChair() != CarServices.getCarById(dynamicTrip.getCar_id()).getSumChair());
    }
    
    public static boolean isValidCarSchedule(Trip fixedTrip, Trip dynamicTrip) {
        LocalDateTime fixedTripDeparting = LocalDateTime.parse(fixedTrip.getDeparting_at(), formatter);
        LocalDateTime fixedTripArriving = LocalDateTime.parse(fixedTrip.getArriving_at(), formatter);
        LocalDateTime dynamicTripDeparting = LocalDateTime.parse(dynamicTrip.getDeparting_at(), formatter);
        LocalDateTime dynamicTripArriving = LocalDateTime.parse(dynamicTrip.getArriving_at(), formatter);
        if(fixedTrip.getId() != dynamicTrip.getId() &&
                fixedTrip.getCar_id() == dynamicTrip.getCar_id()) {
            if(fixedTripDeparting.isBefore(fixedTripArriving) && fixedTripArriving.isBefore(dynamicTripDeparting))
                return true;
            return fixedTripDeparting.isBefore(fixedTripArriving) && fixedTripDeparting.isAfter(dynamicTripArriving);
        }
        return true;
    }
    
    
    public static int isValidTrip(Trip trip) throws SQLException {
        TripServices ts = new TripServices();
        List<Trip> listTrip = ts.loadTrips(null);
        
        for (Trip t : listTrip) {
            if(!isValidSumChair(trip, t))
                return -2;
            if(!isValidCarSchedule(trip, t))
                return -1;
        }
        return 1;
    }
}