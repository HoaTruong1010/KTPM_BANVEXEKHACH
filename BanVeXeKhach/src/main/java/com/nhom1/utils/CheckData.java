/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.utils;

import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
import com.nhom1.services.CarServices;
import com.nhom1.services.TicketServices;
import com.nhom1.services.TripServices;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    
    public static boolean isValidName(String name) {
        return name.matches("^[aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆ\n" +
"fFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTu\n" +
"UùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ\\s]+$");
    }

    public static boolean isDouble(String text) {
        try {
            return Double.parseDouble(text) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteger(String text) {
        try {            
            return Integer.parseInt(text) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidSumChair(Trip fixedTrip, Trip dynamicTrip) throws SQLException {
        return !(fixedTrip.getId() == dynamicTrip.getId()
                && CarServices.getCarById(fixedTrip.getCar_id()).getSumChair() != CarServices.getCarById(dynamicTrip.getCar_id()).getSumChair());
    }

    public static boolean isValidCarSchedule(Trip fixedTrip, Trip dynamicTrip) {
        LocalDateTime fixedTripDeparting = LocalDateTime.parse(fixedTrip.getDeparting_at(), formatter);
        LocalDateTime fixedTripArriving = LocalDateTime.parse(fixedTrip.getArriving_at(), formatter);
        LocalDateTime dynamicTripDeparting = LocalDateTime.parse(dynamicTrip.getDeparting_at(), formatter);
        LocalDateTime dynamicTripArriving = LocalDateTime.parse(dynamicTrip.getArriving_at(), formatter);
        if (fixedTripDeparting.isBefore(fixedTripArriving)) {
            if (fixedTrip.getId() != dynamicTrip.getId()
                    && fixedTrip.getCar_id() == dynamicTrip.getCar_id()) {
                if (fixedTripArriving.isBefore(dynamicTripDeparting)) {
                    return true;
                }
                return  fixedTripDeparting.isAfter(dynamicTripArriving);
            }
            return true;
        }
        return false;
    }

    public static int isValidTrip(Trip trip) throws SQLException {
        TripServices ts = new TripServices();
        List<Trip> listTrip = ts.loadTrips(null, 0);

        for (Trip t : listTrip) {
            if (!isValidSumChair(trip, t)) {
                return -2;
            }
            if (!isValidCarSchedule(trip, t)) {
                return -1;
            }
        }
        return 1;
    }

    public static boolean isChoosing(String tripDeparting, int second) {
        LocalDateTime departing = LocalDateTime.parse(tripDeparting, Trip.formatDate);
        Date now = new Date();
        long getTime = departing.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long getDiff = getTime - now.getTime();
        return getDiff > second;
    }

    public static boolean isEmptyTicket(List<Ticket> list) throws SQLException {
        for (Ticket ticket : list) {
            Ticket ticketInDB = TicketServices.getTicketById(ticket.getId());
            if (!ticketInDB.getStatus().equalsIgnoreCase("Empty")) {
                return false;
            }
        }

        return true;
    }

    public static boolean isRecallTicket(List<Ticket> list) throws SQLException {
        for (Ticket ticket : list) {
            Ticket ticketInDB = TicketServices.getTicketById(ticket.getId());
            if (!ticketInDB.getStatus().equalsIgnoreCase("Recall")) {
                return false;
            }
        }

        return true;
    }

    public static boolean isSoldTicket(List<Ticket> list) throws SQLException {
        for (Ticket ticket : list) {
            Ticket ticketInDB = TicketServices.getTicketById(ticket.getId());
            if (!ticketInDB.getStatus().equalsIgnoreCase("Sold")) {
                return false;
            }
        }

        return true;
    }

    public static boolean isReservedTicket(List<Ticket> list) throws SQLException {
        for (Ticket ticket : list) {
            Ticket ticketInDB = TicketServices.getTicketById(ticket.getId());
            if (!ticketInDB.getStatus().equalsIgnoreCase("Reserved")) {
                return false;
            }
        }

        return true;
    }
}
