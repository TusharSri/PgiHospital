package com.example.tushar.pgi.view;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

        upcomingLeaves.setText("Upcoming leaves : " + doctor.getUpcomingLeaves());

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                selectedDateText = condate(datePicker.getDayOfMonth()) + "/" +
                        condate(datePicker.getMonth()+1) + "/" + String.valueOf(datePicker.getYear()).substring(2);
                selectedDate.setText("Date of Appointment : " + selectedDateText);
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
                paytmGateway();
            }
        });
    }

    private String condate(int i){
        if(i<10){
            return "0"+i;
        }else{
            return String.valueOf(i);
        }
    }

    private void paytmGateway() {
        PaytmPGService Service = PaytmPGService.getStagingService();


        //Kindly create complete Map and checksum on your server side and then put it here in paramMap.

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", "DIY12386817555501617");
        paramMap.put("ORDER_ID", "TestMerchant000111007");
        paramMap.put("CUST_ID", "mohit.aggarwal@paytm.com");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", "1");
        paramMap.put("WEBSITE", "worldpressplg");
        paramMap.put("CALLBACK_URL", "https://pguat.paytm.com/PayTMSecured/app/auth/login");
        paramMap.put("CHECKSUMHASH", "5I2MB1m+U7J0MxWm6jNXj8az21ZXoma9VT5Y0qOrTmxlYkpEhSgjWXOfFx9kdDN7EB0y1BO\\/RNAvMZsSmSWKP0dMs3bzOcwi1JHR53DOJsM=");
        PaytmOrder Order = new PaytmOrder(paramMap);


        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {

                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction : " + inResponse);
                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() {
                        // If network is not
                        // available, then this
                        // method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                });
    }

    /**
     * This method checks weather the date of appointment selected by the user is not a leave day
     *
     * @return
     */
    private boolean isDoctorOnLeave() {
        for (String str : leaveArray) {
            if (str.equalsIgnoreCase(selectedDateText)) {
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

        ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();

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
