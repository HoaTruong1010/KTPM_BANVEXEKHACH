/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.services;

import com.nhom1.pojo.Route;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HOA TRƯƠNG
 */
public class RouteServices {

    public List<Route> loadRoutes() throws SQLException {
        List<Route> listRoute = new ArrayList<>();

        try (Connection conn = JDBCUtils.createConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM route");

            while (rs.next()) {
                Route r = new Route(rs.getInt("id"), rs.getString("start"),
                        rs.getString("end"));
                listRoute.add(r);
            }
        }

        return listRoute;
    }

    public static Route getRouteById(int routeID) throws SQLException {
        try (Connection conn = JDBCUtils.createConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM route WHERE id = ?");
            stm.setInt(1, routeID);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Route r = new Route(rs.getInt("id"), rs.getString("start"),
                        rs.getString("end"));

                return r;
            }
        }
        return null;
    }
}
