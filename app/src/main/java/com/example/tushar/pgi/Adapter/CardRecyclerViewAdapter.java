package com.example.tushar.pgi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.PatientCard;

import java.util.List;

/**
 * Created by Tushar on 9/21/2017.
 */

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<PatientCardViewHolder>{

    private List<PatientCard> itemList;
    private Context context;

    public CardRecyclerViewAdapter(List<PatientCard> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }


    @Override
    public PatientCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_details_row, null);
        PatientCardViewHolder rcv = new PatientCardViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(PatientCardViewHolder holder, int position) {
        holder.patientName.setText(itemList.get(position).getPatientName());
        holder.buildingNumber.setText(itemList.get(position).getBuildingNumber());
        holder.floorNumber.setText(itemList.get(position).getFloorNumber());
        holder.bedNumber.setText(itemList.get(position).getBedNumber());
        holder.roomNumber.setText(itemList.get(position).getRoomNumber());
        holder.desieseDescription.setText(itemList.get(position).getDesieseDescription());
        holder.patientImage.setImageResource(itemList.get(position).getPatientImage());
/*        holder.patientImage.setVisibility(View.GONE);
        holder.desieseDescription.setVisibility(View.GONE);*/
        holder.patientName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "animate card here", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
