/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.services;

import com.nhom1.pojo.Ticket;
import com.nhom1.pojo.Trip;
import com.nhom1.utils.CheckData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author HOA TRƯƠNG
 */
public class TripServices {

    public List<Trip> loadTrips(String kw, int routeID) throws SQLException {
        List<Trip> list = new ArrayList<>();
        boolean isSearch = kw != null && !kw.isEmpty();

        try (Connection conn = JDBCUtils.createConn()) {
            String sql = "SELECT * FROM trip";
            if (isSearch) {
                sql += " WHERE departing_at between ? and Date_add(?, INTERVAL 1 DAY);";
            }

            PreparedStatement stm = conn.prepareStatement(sql);
            if (isSearch) {
                stm.setString(1, kw);
                stm.setString(2, kw);
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Trip t = new Trip(rs.getInt("id"), rs.getString("departing_at"),
                        rs.getString("arriving_at"), rs.getDouble("price"),
                        rs.getInt("car_id"), rs.getInt("route_id"));
                list.add(t);
            }

            if (isSearch && !list.isEmpty()) {
                list = list.stream().filter(x -> x.getRoute_id() == routeID).collect(Collectors.toList());
            }
        }

        return list;
    }

    public static Trip getTripById(int id) throws SQLException {
        Trip t = null;
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM trip WHERE id = ?");
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                t = new Trip(rs.getInt("id"), rs.getString("departing_at"),
                        rs.getString("arriving_at"), rs.getDouble("price"),
                        rs.getInt("car_id"), rs.getInt("route_id"));
                break;
            }
        }

        return t;
    }

    public int getLastTripId() throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT id FROM trip ORDER BY id DESC LIMIT 1;");

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
            return 0;
        }
    }

    public static List<Trip> getTripsByDeparting(int second) throws SQLException {
        TripServices t = new TripServices();
        List<Trip> list = t.loadTrips(null, 0);

        list = list.stream().filter((Trip x) -> {
            LocalDateTime departing = LocalDateTime.parse(x.getDeparting_at(), Trip.formatDate);
            Date now = new Date();
            long getTime = departing.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long getDiff = getTime - now.getTime();
            return getDiff <= second;
        }).collect(Collectors.toList());

        return list;
    }

    public int addTrip(Trip trip, int numChair) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            conn.setAutoCommit(false);

            trip.setId(this.getLastTripId() + 1);

            int resultCheck = CheckData.isValidTrip(trip);
            if (resultCheck == 1) {
                String sql = "INSERT INTO trip(id, departing_at, arriving_at, price, car_id, route_id) "
                        + "VALUES(?, STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), ?, ?, ?);";

                PreparedStatement stm1 = conn.prepareStatement(sql);
                stm1.setInt(1, trip.getId());
                stm1.setString(2, trip.getDeparting_at());
                stm1.setString(3, trip.getArriving_at());
                stm1.setDouble(4, trip.getPrice());
                stm1.setInt(5, trip.getCar_id());
                stm1.setInt(6, trip.getRoute_id());

                stm1.executeUpdate();
                int ticketID = TicketServices.getLastTicketId();
                for (int i = 1; i <= numChair; i++) {
                    sql = "INSERT INTO ticket(id, chair, status, print_date, trip_id, customer_id, user_id) "
                            + "VALUES(?, ?, ?, ?, ?, ?, ?);";
                    PreparedStatement stm2 = conn.prepareStatement(sql);
                    stm2.setInt(1, ticketID + i);
                    stm2.setString(2, String.format("%d", i));
                    stm2.setString(3, "Empty");
                    stm2.setString(4, null);
                    stm2.setInt(5, trip.getId());
                    stm2.setString(6, null);
                    stm2.setString(7, null);

                    stm2.executeUpdate();
                }

                conn.commit();
            }
            return resultCheck;
        }
    }

    public int editTrip(Trip trip) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            conn.setAutoCommit(false);
            int resultCheck = CheckData.isValidTrip(trip);

            if (resultCheck == 1) {
                String sql = "UPDATE trip SET departing_at = ?, arriving_at = ?, price = ?, car_id = ?, route_id = ? WHERE id = ?;";

                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, trip.getDeparting_at());
                stm.setString(2, trip.getArriving_at());
                stm.setDouble(3, trip.getPrice());
                stm.setInt(4, trip.getCar_id());
                stm.setInt(5, trip.getRoute_id());
                stm.setInt(6, trip.getId());

                stm.executeUpdate();
                
                List<Ticket> listTicket = TicketServices.getTicketsByTripID(trip.getId());
                for (Ticket ticket: listTicket) {
                    sql = "UPDATE ticket SET status = ? WHERE id = ? and status = ?;";
                    PreparedStatement stm2 = conn.prepareStatement(sql);
                    stm2.setString(1, "Empty");
                    stm2.setInt(2, ticket.getId());
                    stm2.setString(3, "Recall");

                    stm2.executeUpdate();
                }

                conn.commit();
            }

            return resultCheck;
        }
    }

    public boolean deleteTrip(Trip trip) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            int tripResult;
            int numChair;
            conn.setAutoCommit(false);

            String sql = "DELETE FROM trip WHERE id = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, trip.getId());

            tripResult = stm.executeUpdate();

            if (tripResult > 0) {
                numChair = CarServices.getCarById(trip.getCar_id()).getSumChair();
                for (int i = 1; i <= numChair; i++) {
                    sql = "DELETE FROM ticket WHERE trip_id = ?";
                    PreparedStatement stm2 = conn.prepareStatement(sql);
                    stm2.setInt(1, trip.getId());

                    stm2.executeUpdate();
                }

                conn.commit();
                return true;
            }

            return false;
        }
    }
}
