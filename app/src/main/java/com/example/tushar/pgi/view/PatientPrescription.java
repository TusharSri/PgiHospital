package com.example.tushar.pgi.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.Appointment;

import java.util.List;

public class PatientPrescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_prescription);

        Appointment appointments = (Appointment) getIntent().getSerializableExtra("patient_data");
        TextView patientName = (TextView) findViewById(R.id.text_patient_name);
        TextView patientAge = (TextView) findViewById(R.id.text_age);
        TextView timing = (TextView) findViewById(R.id.text_time);
        TextView patientBed = (TextView) findViewById(R.id.text_bed);
        TextView patientDetails = (TextView) findViewById(R.id.text_detail);
        patientName.setText(appointments.getName());
        //patientAge.setText(appointments.get());
        timing.setText(appointments.getTimeSlot());
        patientBed.setText(appointments.getBedNumber());
        patientDetails.setText(appointments.getDescription());
    }
}
