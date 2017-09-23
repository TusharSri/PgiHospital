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
import com.example.tushar.pgi.model.Appointment;
import com.example.tushar.pgi.model.DoctorModel;
import com.example.tushar.pgi.model.ItemObjects;
import com.example.tushar.pgi.model.PatientCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

    Boolean isDoctor;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent loginIntent = getIntent();
        uid = loginIntent.getStringExtra("uid");
        String type = loginIntent.getStringExtra("type");


        if (type.equalsIgnoreCase("doctor")) {
            getDoctorData();
            isDoctor = true;
        } else{
            isDoctor = false;
        }
        setLayoutOfDashboard(isDoctor);
    }



    /**
     *  This method gets the data of the particular doctor
     */
    private void getDoctorData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("doctors");

        Query queryRef = myRef.orderByChild("uid").equalTo(uid);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    DoctorModel model = child.getValue(DoctorModel.class);
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                    String a = dateFormat.format(date);
                    Toast.makeText(Dashboard.this, a, Toast.LENGTH_SHORT).show();

                    Query aa =child.getRef().orderByChild("appointment").getRef().orderByChild("date").equalTo(a);
                    aa.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for ()
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setLayoutOfDashboard(boolean isDoctor) {
        if (isDoctor) {
            setContentView(R.layout.activity_dashboard_doctor);

            // get the listview
            expListView = (ExpandableListView) findViewById(R.id.lvExp);

            // preparing list data
            prepareListData();

            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

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
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding Parent data
        listDataHeader.add("Patient 1");
        listDataHeader.add("Patient 2");
        listDataHeader.add("Patient 3");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
}
