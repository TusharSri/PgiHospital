
package com.example.tushar.pgi.model;


import java.io.Serializable;
public class Appointment implements Serializable{

    private String name;
    private String appointmentType;
    private String date;
    private String timeSlot;
    private String uid;
    private String buildingNumber;
    private String floorNumber;
    private String roomNumber;
    private String bedNumber;
    private String description;

    /**
     * No args constructor for use in serialization
     *
     */
    public Appointment() {
    }

    private Appointment(String name, String appointmentType, String date, String timeSlot, String uid, String buildingNumber, String floorNumber, String roomNumber, String bedNumber, String description) {
        this.name = name;
        this.appointmentType = appointmentType;
        this.date = date;
        this.timeSlot = timeSlot;
        this.uid = uid;
        this.buildingNumber = buildingNumber;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

}