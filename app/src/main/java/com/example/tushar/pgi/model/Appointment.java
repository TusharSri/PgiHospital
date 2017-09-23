
package com.example.tushar.pgi.model;


public class Appointment {

    private String appointmentType;
    private String date;
    private String timeSlot;
    private String uid;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Appointment() {
    }

    /**
     * 
     * @param uid
     * @param date
     * @param appointmentType
     * @param timeSlot
     */
    public Appointment(String appointmentType, String date, String timeSlot, String uid) {
        super();
        this.appointmentType = appointmentType;
        this.date = date;
        this.timeSlot = timeSlot;
        this.uid = uid;
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

}
