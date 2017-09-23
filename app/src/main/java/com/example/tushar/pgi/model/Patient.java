
package com.example.tushar.pgi.model;

import java.util.List;

public class Patient {

    private String name;
    private String uid;
    private String timeSlot;
    private String date;
    private String age;
    private String problem;
    private String status;
    private List<Record> records = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Patient() {
    }

    /**
     * 
     * @param uid
     * @param status
     * @param age
     * @param name
     * @param problem
     * @param records
     * @param date
     * @param timeSlot
     */
    public Patient(String name, String uid, String timeSlot, String date, String age, String problem, String status, List<Record> records) {
        super();
        this.name = name;
        this.uid = uid;
        this.timeSlot = timeSlot;
        this.date = date;
        this.age = age;
        this.problem = problem;
        this.status = status;
        this.records = records;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

}
