package com.example.tushar.pgi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.DoctorModel;

import java.util.ArrayList;


/**
 * Created by saumy on 9/21/2017.
 */

public class DoctorsAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ArrayList<DoctorModel> mDoctors;

    public DoctorsAdapter(Context context, ArrayList<DoctorModel> doctors) {
        this.context = context;
        this.mDoctors = doctors;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        return new DoctorsViewHolder(inflater.inflate(R.layout.doctor_item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DoctorModel doctor = mDoctors.get(position);
        ((DoctorsViewHolder)holder).name.setText(doctor.getName());;
        ((DoctorsViewHolder)holder).age.setText("Age = "+doctor.getAge());
        ((DoctorsViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookAppointmentIntent = new Intent(context, com.example.tushar.pgi.Adapter.BookAppointmentActivity.class);
                bookAppointmentIntent.putExtra("doctor",doctor);
                context.startActivity(bookAppointmentIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    private class DoctorsViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView name;
        private TextView age;

        public DoctorsViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.name = (TextView) itemView.findViewById(R.id.text_name);
            this.age = (TextView) itemView.findViewById(R.id.text_age);
        }
    }
}
