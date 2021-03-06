package com.example.tushar.pgi.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.tushar.pgi.Adapter.*;
import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.Appointment;
import com.example.tushar.pgi.model.DoctorModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;


public class DoctorsListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        String type = getIntent().getStringExtra("type");

        getAllDoctorsOfaParticularType(type);

    }

    /**
     *  This method gets all the doctors of the type given
     * @param type
     */
    private void getAllDoctorsOfaParticularType(final String type) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("doctors");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<DoctorModel> doctorModelArrayList = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()){
                    DoctorModel doctor = child.getValue(DoctorModel.class);
                    if(doctor.getType().equalsIgnoreCase(type)) {
                        doctorModelArrayList.add(doctor);
                    }
                }

                setRecyclerView(doctorModelArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     *  This method sets the recycler view
     * @param doctors
     */
    private void setRecyclerView(ArrayList<DoctorModel> doctors) {
        RecyclerView categoriesRecyclerView = (RecyclerView) findViewById(R.id.doctors_recycler_view);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoriesRecyclerView.setAdapter(new DoctorsAdapter(this, doctors));
    }
}
