
package com.example.tushar.pgi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorModel implements Serializable{

    private String uid;
    private String name;
    private String type;
    private String email;
    private String phone;
    private String age;
    private String upcomingLeaves;
    private List<Appointment> appointments = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DoctorModel() {
    }

    /**
     * 
     * @param uid
     * @param phone
     * @param appointments
     * @param email
     * @param age
     * @param name
     * @param type
     * @param upcomingLeaves
     */
    public DoctorModel(String uid, String name, String type, String email, String phone, String age, String upcomingLeaves, List<Appointment> appointments) {
        super();
        this.uid = uid;
        this.name = name;
        this.type = type;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.upcomingLeaves = upcomingLeaves;
        this.appointments = appointments;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUpcomingLeaves() {
        return upcomingLeaves;
    }

    public void setUpcomingLeaves(String upcomingLeaves) {
        this.upcomingLeaves = upcomingLeaves;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid",getUid());
        map.put("name",getName());
        map.put("type",getType());
        map.put("email",getEmail());
        map.put("phone",getPhone());
        map.put("age",getAge());
        map.put("upcomingLeaves",getUpcomingLeaves());
        map.put("appointments",getAppointments());

        return map;
    }

}
