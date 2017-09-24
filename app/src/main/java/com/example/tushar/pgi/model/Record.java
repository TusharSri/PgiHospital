
package com.example.tushar.pgi.model;


public class Record {

    private String description;
    private String medicine;
    private String aditional;
    private String nextAppointment;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Record() {
    }

    /**
     * 
     * @param aditional
     * @param description
     * @param nextAppointment
     * @param medicine
     */
    public Record(String description, String medicine, String aditional, String nextAppointment) {
        super();
        this.description = description;
        this.medicine = medicine;
        this.aditional = aditional;
        this.nextAppointment = nextAppointment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getAditional() {
        return aditional;
    }

    public void setAditional(String aditional) {
        this.aditional = aditional;
    }

    public String getNextAppointment() {
        return nextAppointment;
    }

    public void setNextAppointment(String nextAppointment) {
        this.nextAppointment = nextAppointment;
    }

}
