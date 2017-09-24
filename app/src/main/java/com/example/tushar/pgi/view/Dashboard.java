package com.example.tushar.pgi.view;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tushar.pgi.Adapter.ExpandableListAdapter;
import com.example.tushar.pgi.Adapter.ItemRecyclerViewAdapter;
import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.Appointment;
import com.example.tushar.pgi.model.DoctorModel;
import com.example.tushar.pgi.model.ItemObjects;
import com.example.tushar.pgi.model.Patient;
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
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

    Boolean isDoctor;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    private String uid;
    private TextToSpeech tts;
    private List<Appointment> todaysAppointments;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final String PREFS = "prefs";
    private static final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent loginIntent = getIntent();
        uid = loginIntent.getStringExtra("uid");
        String type = loginIntent.getStringExtra("type");

        if (type.equalsIgnoreCase("doctor")) {
            isDoctor = true;
            getDoctorData();
        } else {
            savePatientInformationInSp(uid);
            isDoctor = false;
            setLayoutOfDashboard(false, null);
        }
    }

    /**
     * This method saves the patient information in the db
     *
     * @param uid
     */
    private void savePatientInformationInSp(final String uid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("patient");

        Query queryRef = myRef.orderByChild("uid").equalTo(uid);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Patient patient = child.getValue(Patient.class);
                    String MyPREFERENCES = "abcd";
                    SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("patientuid", uid);
                    editor.putString("name", patient.getName());
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     * This method gets the data of the particular doctor
     */
    private void getDoctorData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("doctors");
        Query queryRef = myRef.orderByChild("uid").equalTo(uid);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    DoctorModel model = child.getValue(DoctorModel.class);
                    showAppointments(model);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * This method brings out all the appointments and shows them to the user
     */
    private void showAppointments(DoctorModel model) {
        todaysAppointments = new ArrayList<>();
        List<Appointment> listOfAppointments = model.getAppointments();

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        String currentDate = dateFormat.format(date);

        for (Appointment appointment : listOfAppointments) {
            if (appointment.getDate().equalsIgnoreCase(currentDate)) {
                todaysAppointments.add(appointment);
            }
        }
        setLayoutOfDashboard(true, todaysAppointments);
    }

    private void setLayoutOfDashboard(boolean isDoctor, List<Appointment> appointments) {
        if (isDoctor) {
            setContentView(R.layout.activity_dashboard_doctor);
            ImageView doctorFloatingButton = (ImageView) findViewById(R.id.floating_button_doctor);
            doctorFloatingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    promptSpeechInput();
                }
            });

            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        int result = tts.setLanguage(Locale.US);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        }
                        speak("");
                    } else {
                        Log.e("TTS", "Initilization Failed!");
                    }
                }
            });
        } else {
            setContentView(R.layout.activity_dashboard_patient);
            showAlertDAilouge();
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

    private void showAlertDAilouge() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("भाषा को हिंदी में परिवर्तित करें")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("language", "hindi");
                        editor.commit();
                    }
                })
                .setNegativeButton("नहीं", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("language", "english");
                        editor.commit();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void speak(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
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
                        preferences = getSharedPreferences(PREFS, 0);
                        editor = preferences.edit();
                        recognition(result.get(0));
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

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private void recognition(String text) {

        String[] speech = text.split(" ");
        if (text.contains("emergency")) {
            speak("We are looking into it");
        } else if (text.contains("hello")) {
            speak("Hello, what is your name?");
        } else if (text.contains("my name is")) {
            String name = speech[speech.length - 1];
            Log.e("THIS", "" + name);
            editor.putString(NAME, name).apply();
            speak("what can i do for you " + preferences.getString(NAME, null));
        } else if (text.contains("what") && text.contains("time")) {
            SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");//dd/MM/yyyy
            Date now = new Date();
            String[] strDate = sdfDate.format(now).split(":");
            if (strDate[1].contains("00"))
                strDate[1] = "o'clock";
            speak("The time is " + sdfDate.format(now));
        } else if (text.contains("thank you") || text.contains("thanks")) {
            speak("Your Welcome " + preferences.getString(NAME, null));
        } else if (text.contains("leave")) {
            speak("For which date you want to apply leave " + preferences.getString(NAME, null));
        } else if (text.contains("what") && text.contains("my name")) {
            speak("Your name is " + preferences.getString(NAME, null));
        } else if (text.contains("read") || text.contains("repeat")) {
            if (text.contains("first")) {
                speak("First Appointment is of " + todaysAppointments.get(0).getName() +
                        "in building number" + todaysAppointments.get(0).getBuildingNumber() +
                        " The patient is suffering from " + todaysAppointments.get(0).getDescription());
            }
            if (text.contains("second")) {
                speak("second Appointment is of " + todaysAppointments.get(1).getName() +
                        "in building number" + todaysAppointments.get(1).getBuildingNumber() +
                        " The patient is suffering from " + todaysAppointments.get(1).getDescription());
            }
            if (text.contains("third")) {
                speak("third Appointment is of " + todaysAppointments.get(1).getName() +
                        "in building number" + todaysAppointments.get(1).getBuildingNumber() +
                        " The patient is suffering from " + todaysAppointments.get(1).getDescription());
            }
        } else if (text.contains("open") || text.contains("details")) {
            if (text.contains("first")) {
                Intent patientdetail = new Intent(this, PatientPrescription.class);
                patientdetail.putExtra("patient_data", todaysAppointments.get(0));
                startActivity(patientdetail);
            }
            if (text.contains("second")) {
                Intent patientdetail = new Intent(this, PatientPrescription.class);
                patientdetail.putExtra("patient_data", todaysAppointments.get(1));
                startActivity(patientdetail);
            }
            if (text.contains("third")) {
                Intent patientdetail = new Intent(this, PatientPrescription.class);
                patientdetail.putExtra("patient_data", todaysAppointments.get(2));
                startActivity(patientdetail);
            }
        } else if (text.contains("appointment") && text.contains("today")) {
            speak("These are your Today's Appointment" + preferences.getString(NAME, null));
            expListView = (ExpandableListView) findViewById(R.id.lvExp);
            listAdapter = new ExpandableListAdapter(this, todaysAppointments);
            expListView.setAdapter(listAdapter);
        } else if (text.contains("yesterday")) {
            speak("sorry we are unable to fetch appointments for yesterday");
        } else if (text.contains("tomorrow")) {
            speak("sorry we are unable to fetch appointments for tommorow");
        } else {
            speak("I am sorry i didn't get you, can u please come again");
        }
    }
}
