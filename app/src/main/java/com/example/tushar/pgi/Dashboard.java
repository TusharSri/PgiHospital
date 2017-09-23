package com.example.tushar.pgi;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tushar.pgi.Adapter.ExpandableListAdapter;
import com.example.tushar.pgi.Adapter.ItemRecyclerViewAdapter;
import com.example.tushar.pgi.model.ItemObjects;
import com.example.tushar.pgi.model.PatientCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

    Boolean isDoctor;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> patientName;
    List<String> buildingNumber;
    List<String> floorNumber;
    List<String> roomNumber;
    List<String> bedNumber;

    List<String> patientTime;
    List<Integer> patientImage;
    HashMap<String, List<String>> patientDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent loginIntent = getIntent();
        String qrCode = loginIntent.getStringExtra("scannedQR");
        String aadharNumber = loginIntent.getStringExtra("aadharNumber");
        if (qrCode.equals("111122223333") || aadharNumber.equals("111122223333")) {
            isDoctor = true;
        } else if (qrCode.equals("999988887777") || aadharNumber.equals("999988887777")) {
            isDoctor = false;
        }
        setLayoutOfDashboard(isDoctor);
    }

    private void setLayoutOfDashboard(boolean isDoctor) {
        if (isDoctor) {
            setContentView(R.layout.activity_dashboard_doctor);

            // get the listview
            expListView = (ExpandableListView) findViewById(R.id.lvExp);

            // preparing list data
            prepareListData();

            listAdapter = new ExpandableListAdapter(this, patientName, patientDetails, buildingNumber, floorNumber, roomNumber, bedNumber, patientTime, patientImage);

            // setting list adapter
            expListView.setAdapter(listAdapter);

            List<PatientCard> patientCardData = getPatientCardData();
           /* CardRecyclerViewAdapter recyclerViewArrayAdapter = new CardRecyclerViewAdapter(patientCardData, getApplicationContext());
            RecyclerView simpleRecyclerView = (RecyclerView) findViewById(R.id.tast_recyclerview);
            simpleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            simpleRecyclerView.setAdapter(recyclerViewArrayAdapter);*/
            ImageView doctorFloatingButton = (ImageView) findViewById(R.id.floating_button_doctor);
            doctorFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    promptSpeechInput();
                }
            });
        } else {
            setContentView(R.layout.activity_dashboard_patient);
            ImageView patiientFloatingButton = (ImageView) findViewById(R.id.floating_button_patient);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(gaggeredGridLayoutManager);
            List<ItemObjects> gaggeredList = getListItemData();
            ItemRecyclerViewAdapter rcAdapter = new ItemRecyclerViewAdapter(getApplicationContext(), gaggeredList);
            recyclerView.setAdapter(rcAdapter);

            patiientFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    promptSpeechInput();
                }
            });
        }
    }

    private List<ItemObjects> getListItemData() {
        List<ItemObjects> listViewItems = new ArrayList<>();
        listViewItems.add(new ItemObjects("Book Appointment", R.drawable.book_appointment));
        listViewItems.add(new ItemObjects("Navigate to Room", R.drawable.book_appointment));
        listViewItems.add(new ItemObjects("My Reports", R.drawable.book_appointment));
        listViewItems.add(new ItemObjects("Read Text", R.drawable.book_appointment));

        return listViewItems;
    }

    private List<PatientCard> getPatientCardData() {
        List<PatientCard> listViewItems = new ArrayList<>();
        listViewItems.add(new PatientCard("Tushar", "b4", "f1", "r1", "be1", "d1", R.mipmap.ic_launcher));
        listViewItems.add(new PatientCard("Tushar", "b4", "f1", "r1", "be1", "d1", R.mipmap.ic_launcher));
        return listViewItems;
    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (isDoctor) {
                        if (result.get(0).contains("appointment")) {
                            Toast.makeText(this, "todays Appointment shown to doctor", Toast.LENGTH_SHORT).show();
                        } else if (result.get(0).contains("leave")) {
                            Toast.makeText(this, "Leave Applied", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (result.get(0).contains("appointment")) {
                            Toast.makeText(this, "appointment booked", Toast.LENGTH_SHORT).show();
                        } else if (result.get(0).contains("navigate")) {
                            Toast.makeText(this, "Navigation completed", Toast.LENGTH_SHORT).show();
                        } else if (result.get(0).contains("report")) {
                            Toast.makeText(this, "Report shown ", Toast.LENGTH_SHORT).show();
                        } else if (result.get(0).contains("read")) {
                            Toast.makeText(this, "Reading completed ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
        }
    }

    /*
    * Preparing the list data
    */
    private void prepareListData() {
        patientName = new ArrayList<String>();
        patientTime = new ArrayList<String>();
        buildingNumber = new ArrayList<String>();
        floorNumber = new ArrayList<String>();
        roomNumber = new ArrayList<String>();
        bedNumber = new ArrayList<String>();
        patientImage = new ArrayList<Integer>();
        patientDetails = new HashMap<String, List<String>>();

        // Adding Parent data
        patientName.add("Patient 1");
        patientName.add("Patient 2");
        patientName.add("Patient 3");
        patientTime.add("10:11");
        patientTime.add("11:12");
        patientTime.add("12:13");
        buildingNumber.add("b1");
        buildingNumber.add("b2");
        buildingNumber.add("b3");
        floorNumber.add("f1");
        floorNumber.add("f2");
        floorNumber.add("f3");
        roomNumber.add("r1");
        roomNumber.add("r2");
        roomNumber.add("r3");
        bedNumber.add("be1");
        bedNumber.add("be2");
        bedNumber.add("be3");
        patientImage.add(R.mipmap.ic_launcher);
        patientImage.add(R.mipmap.ic_launcher);
        patientImage.add(R.mipmap.ic_launcher);
        // Adding child data
        List<String> desc1 = new ArrayList<String>();
        desc1.add("here we are showing the desises description r something else");

        List<String> desc2 = new ArrayList<String>();
        desc2.add("here we are showing the desises description r something else");

        List<String> desc3 = new ArrayList<String>();
        desc3.add("here we are showing the desises description r something else");

        patientDetails.put(patientName.get(0), desc1); // Header, Child data
        patientDetails.put(patientName.get(1), desc2);
        patientDetails.put(patientName.get(2), desc3);

    }
}
