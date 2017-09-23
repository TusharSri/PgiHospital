package com.example.tushar.pgi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tushar.pgi.R;
import com.example.tushar.pgi.view.DoctorsListActivity;

import java.util.ArrayList;


/**
 * Created by saumy on 9/21/2017.
 */

public class DoctorCategoriesAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ArrayList<String> categories;

    public DoctorCategoriesAdapter(Context context, ArrayList<String> pCategories) {
        this.context = context;
        this.categories = pCategories;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new DoctorCategoriesViewHolder(inflater.inflate(R.layout.categories_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String hindiList;
        if (categories.get(position).equals("Orthopedic")) {
            hindiList = "हड्डी रोग विशेषज्ञ";
        } else {
            hindiList = "हृदय रोग विशेषज्ञ";
        }
        ((DoctorCategoriesViewHolder) holder).categoryName.setText(hindiList);
        ((DoctorCategoriesViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Intent doctorsIntent = new Intent(context, DoctorsListActivity.class);
                doctorsIntent.putExtra("type", categories.get(position));
                context.startActivity(doctorsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private class DoctorCategoriesViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView categoryName;

        public DoctorCategoriesViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            categoryName = (TextView) itemView.findViewById(R.id.text_view_category);

        }
    }
}