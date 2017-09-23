package com.example.tushar.pgi.Adapter;

import android.content.Context;
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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class DoctorsViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private TextView name;

        public DoctorsViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            //this.name = itemView.findViewById(R.id.)
        }
    }
}
