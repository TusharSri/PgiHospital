package com.example.tushar.pgi.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.Appointment;
import com.example.tushar.pgi.model.DoctorModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookAppointmentActivity extends AppCompatActivity {

    private String selectedDateText = "";
    private String[] leaveArray;
    private String selectedTimeSlot;
    private String[] timeSlotsArray;
    private DoctorModel doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        doctor = (DoctorModel) getIntent().getSerializableExtra("doctor");

        String timeSlots = "1000-1030,1030-1100,1100-1130,1130-1200,1500-1530,1530-1600,1600-1630";
        timeSlotsArray =timeSlots.split(",");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSlotsArray);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_time_slot);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTimeSlot = timeSlotsArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String leaves = doctor.getUpcomingLeaves();
        leaveArray = leaves.split(",");

        final DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
        Button selectDateButton = (Button) findViewById(R.id.button_select_Date);
        Button bookAppointmentButton = (Button) findViewById(R.id.button_book_appointment);
        final TextView selectedDate = (TextView) findViewById(R.id.text_selected_date);
        final TextView upcomingLeaves = (TextView) findViewById(R.id.text_upcoming_leaves);

        upcomingLeaves.setText("Upcoming leaves : " +doctor.getUpcomingLeaves());
        int a = datePicker.getMonth();
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDateText =  datePicker.getDayOfMonth()+"/"+
                        (datePicker.getMonth()+1)+"/"+datePicker.getYear();
                selectedDate.setText("Date of Appointment : "+ selectedDateText);
            }
        });

        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDoctorOnLeave() || selectedDateText.equalsIgnoreCase("") || selectedTimeSlot.equalsIgnoreCase("")){
                    Toast.makeText(BookAppointmentActivity.this, "Not able to book appointment", Toast.LENGTH_SHORT).show();
                }else{
                    insertAppointmentIntoDB();
                }
            }
        });
    }

    /**
     * Checks if the selected date is less than current date
     * @param selectedDateText
     * @return
     */
    private boolean dateLessThanCurrentDate(String selectedDateText) {
        Date d = new Date();
        DateFormat dateFormat = new SimpleDateFormat("DD/MM/YY");
        String[] y = dateFormat.format(d).split("/");
        String[] x = selectedDateText.split("/");
        if(Integer.parseInt(x[1]) > Integer.parseInt(y[1])){
            return false;
        }
        else if(Integer.parseInt(x[1]) < Integer.parseInt(y[1])){
            return true;
        }
        else if(Integer.parseInt(x[1]) == Integer.parseInt(y[1])){
            if(Integer.parseInt(x[0]) > Integer.parseInt(y[0])){
                return false;
            }
            else{
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks weather the date of appointment selected by the user is not a leave day
     * @return
     */
    private boolean isDoctorOnLeave() {
        for (String str : leaveArray){
            if(str.equalsIgnoreCase(selectedDateText)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method inserts an appointment into the dp
     */
    private void insertAppointmentIntoDB() {

        final Appointment appointment = new Appointment();
        appointment.setTimeSlot(selectedTimeSlot);
        appointment.setDate(selectedDateText);
        SharedPreferences sharedpreferences = getSharedPreferences("abcd", Context.MODE_PRIVATE);
        //TODO - GET NAME HERE
        appointment.setName(sharedpreferences.getString("name",""));
        appointment.setDescription("awehfuiawhfiuwahefiuwheafu");
        appointment.setUid(sharedpreferences.getString("patientuid",""));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("doctors");

        final String uid = doctor.getUid();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i =0;
                int index=-1;
                DoctorModel doctorModel = null;
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    if(child.getValue(DoctorModel.class).getUid().equalsIgnoreCase(uid)){
                        index =i;
                        doctorModel = child.getValue(DoctorModel.class);
                        break;
                    }else{
                        i++;
                    }
                }

                if(index!=-1 && doctorModel!=null) {
                    List<Appointment> a =doctorModel.getAppointments();
                    a.add(appointment);
                    doctorModel.setAppointments(a);
                    myRef.child(String.valueOf(index)).setValue(doctorModel.toMap());
                    Toast.makeText(BookAppointmentActivity.this, "Appointment Added", Toast.LENGTH_SHORT).show();
                    BookAppointmentActivity.this.finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
