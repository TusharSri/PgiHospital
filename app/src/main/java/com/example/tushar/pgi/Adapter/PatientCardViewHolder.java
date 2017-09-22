package com.example.tushar.pgi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tushar.pgi.R;

/**
 * Created by Tushar on 9/21/2017.
 */

public class PatientCardViewHolder extends RecyclerView.ViewHolder{

    public TextView patientName;
    public TextView buildingNumber;
    public TextView floorNumber;
    public TextView bedNumber;
    public TextView roomNumber;
    public TextView desieseDescription;
    public ImageView patientImage;

    public PatientCardViewHolder(View itemView) {
        super(itemView);
        patientName = (TextView) itemView.findViewById(R.id.text_patient_name);
        buildingNumber = (TextView) itemView.findViewById(R.id.text_buiding_number);
        floorNumber = (TextView) itemView.findViewById(R.id.text_floor_number);
        roomNumber = (TextView) itemView.findViewById(R.id.text_room_number);
        bedNumber = (TextView) itemView.findViewById(R.id.text_bed_number);
        desieseDescription = (TextView) itemView.findViewById(R.id.text_patiend_description);
        patientImage = (ImageView) itemView.findViewById(R.id.imageview_patient);
    }
}
