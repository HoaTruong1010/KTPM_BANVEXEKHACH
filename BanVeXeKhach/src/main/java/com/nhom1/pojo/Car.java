/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom1.pojo;

/**
 *
 * @author HOA TRƯƠNG
 */
public class Car {
    private int id;
    private String licensePlate;
    private String name;
    private int sumChair;
    private static int count = 0;
    
    {
        this.id = count++;
    }

    public Car() {
    }

    public Car(String licensePlate, String name, int sumChair) {
        this.licensePlate = licensePlate;
        this.name = name;
        this.sumChair = sumChair;
    }

    public Car(int id, String licensePlate, String name, int sumChair) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.name = name;
        this.sumChair = sumChair;
    }

    @Override
    public String toString() {
        return this.name;
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
     * @return the licensePlate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * @param licensePlate the licensePlate to set
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
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

    /**
     * @return the sumChair
     */
    public int getSumChair() {
        return sumChair;
    }

    /**
     * @param sumChair the sumChair to set
     */
    public void setSumChair(int sumChair) {
        this.sumChair = sumChair;
    }
}
