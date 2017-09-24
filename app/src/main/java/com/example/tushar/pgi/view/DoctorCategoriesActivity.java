package com.example.tushar.pgi.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.tushar.pgi.Adapter.*;

import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.DoctorModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DoctorCategoriesActivity extends AppCompatActivity {

    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        ((TextView)findViewById(R.id.text_doctor_cat)).setText("Please choose a Category");

        TextView cat = (TextView) findViewById(R.id.text_doctor_cat);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        language = prefs.getString("language","");
        if(language.equals("hindi")){
            cat.setText(R.string.hin_please_choose_a_category);
        } else {
            cat.setText(R.string.eng_please_choose_a_category);
        }
        getAndSetData();

    }

    /**
     * This method sets the recycler view
     *
     * @param pCategories
     */
    private void setRecyclerView(ArrayList<String> pCategories) {
        RecyclerView categoriesRecyclerView = (RecyclerView) findViewById(R.id.doctors_recycler_view);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesRecyclerView.setAdapter(new DoctorCategoriesAdapter(this, pCategories));
    }

    /**
     * This method gets the types of doctors from the database
     */
    private void getAndSetData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("doctors");


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> categories = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    DoctorModel doctorModel = child.getValue(DoctorModel.class);
                    if (!categories.contains(doctorModel.getType())) {
                        categories.add(doctorModel.getType());
                    }
                }
                setRecyclerView(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}
