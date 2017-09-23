package com.example.tushar.pgi.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.Appointment;
import com.example.tushar.pgi.model.DoctorModel;

public class BookAppointmentActivity extends AppCompatActivity {

    private String selectedDateText = "";
    private String[] leaveArray;
    private String[] timeSlotsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        DoctorModel doctor = (DoctorModel) getIntent().getSerializableExtra("doctor");

        //String timeSlots = ";
        //timeSlotsArray =timeSlots.split(",");

        String leaves = doctor.getUpcomingLeaves();
        leaveArray = leaves.split(",");

        final DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
        Button selectDateButton = (Button) findViewById(R.id.button_select_Date);
        Button bookAppointmentButton = (Button) findViewById(R.id.button_book_appointment);
        final TextView selectedDate = (TextView) findViewById(R.id.text_selected_date);
        final TextView upcomingLeaves = (TextView) findViewById(R.id.text_upcoming_leaves);

        upcomingLeaves.setText("Upcoming leaves : " +doctor.getUpcomingLeaves());

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDateText =  datePicker.getDayOfMonth()+"/"+
                        datePicker.getMonth()+"/"+datePicker.getYear();
                selectedDate.setText("Date of Appointment : "+ selectedDateText);
            }
        });

        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDoctorOnLeave()){
                    Toast.makeText(BookAppointmentActivity.this, "Doctor is on leave on this day please select some other day", Toast.LENGTH_SHORT).show();
                }else{
                    insertAppointmentIntoDB();
                }
            }
        });
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
        Appointment appointment = new Appointment();
    }
}
