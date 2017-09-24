package com.example.tushar.pgi.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.Appointment;

import java.util.ArrayList;
import java.util.Locale;

public class PatientPrescription extends AppCompatActivity implements View.OnClickListener {

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_prescription);

        Appointment appointments = (Appointment) getIntent().getSerializableExtra("patient_data");
        TextView patientName = (TextView) findViewById(R.id.text_patient_name);
        //TextView patientAge = (TextView) findViewById(R.id.text_age);
        TextView timing = (TextView) findViewById(R.id.text_time);
        TextView patientBed = (TextView) findViewById(R.id.text_bed);
        TextView patientDetails = (TextView) findViewById(R.id.text_detail);
        Button addPrescription = (Button) findViewById(R.id.button_add_prescription);
        Button medicalHistory = (Button) findViewById(R.id.button_medical_history);
        Button cancelAppointment = (Button) findViewById(R.id.button_cancel_appointment);
        Button markAsDone = (Button) findViewById(R.id.button_mark_done);
        addPrescription.setOnClickListener(this);
        medicalHistory.setOnClickListener(this);
        cancelAppointment.setOnClickListener(this);
        markAsDone.setOnClickListener(this);

        ImageView doctorFloatingButton = (ImageView) findViewById(R.id.floating_button_doctor);
        doctorFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listen();
            }
        });
        patientName.setText(appointments.getName());
        //patientAge.setText(appointments.get());
        timing.setText(appointments.getTimeSlot());
        patientBed.setText(appointments.getBedNumber());
        patientDetails.setText(appointments.getDescription());

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
                    Log.e("TTS", "Initialization Failed!");
                }
            }
        });
        tts.setSpeechRate(1);
    }

    private void speak(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    private void listen() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");

        try {
            startActivityForResult(i, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, "Your device doesn't support Speech Recognition", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String inSpeech = res.get(0);
                recognition(inSpeech);
            }
        }
    }

    private void recognition(String text) {
        if (text.contains("add") && text.contains("prescription")) {
            speak("Prescription feature is a module which is yet to be added");
        } else if (text.contains("medical") && text.contains("history")) {
            speak("medical history module is yet to be added");
        } else if (text.contains("cancel") && text.contains("appointment")) {
            speak("cancel appointment");
            finish();
        } else if (text.contains("done") || text.contains("complete")) {
            speak("The Appointment is been completed by you ");
            finish();
        } else {
            speak("I am sorry i didn't get you, can u please come again");
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_add_prescription:
                Toast.makeText(this, "Prescription added", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_medical_history:
                Toast.makeText(this, "medical history", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_cancel_appointment:
                Toast.makeText(this, "cancel appointment", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.button_mark_done:
                Toast.makeText(this, "complete ", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
