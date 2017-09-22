package com.example.tushar.pgi.model;

import android.widget.ImageView;

/**
 * Created by Tushar on 9/21/2017.
 */

public class PatientCard {

    private String patientName;
    private String buildingNumber;
    private String floorNumber;
    private String roomNumber;

    public PatientCard(String patientName, String buildingNumber, String floorNumber, String roomNumber,
                       String bedNumber, String desieseDescription, int patientImage) {
        this.patientName = patientName;
        this.buildingNumber = buildingNumber;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
        this.desieseDescription = desieseDescription;
        this.patientImage = patientImage;
    }

    private String bedNumber;
    private String desieseDescription;
    private int patientImage;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getDesieseDescription() {
        return desieseDescription;
    }

    public void setDesieseDescription(String desieseDescription) {
        this.desieseDescription = desieseDescription;
    }

    public int getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(int patientImage) {
        this.patientImage = patientImage;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
