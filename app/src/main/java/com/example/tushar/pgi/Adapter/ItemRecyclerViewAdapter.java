package com.example.tushar.pgi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.ItemObjects;
import com.example.tushar.pgi.view.DoctorCategoriesActivity;

import java.util.List;

/**
 * Created by Tushar on 9/21/2017.
 */

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolders> {

    private List<ItemObjects> itemList;
    private Context context;

    public ItemRecyclerViewAdapter(Context context, List<ItemObjects> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public ItemViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
        ItemViewHolders rcv = new ItemViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolders holder, final int position) {
        final Animation zoomin = AnimationUtils.loadAnimation(context, R.anim.zoomin);
        final Animation zoomout = AnimationUtils.loadAnimation(context, R.anim.zoomout);
        holder.countryPhoto.setAnimation(zoomin);
        holder.countryPhoto.setAnimation(zoomout);
        holder.countryName.setText(itemList.get(position).getName());
        holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
        holder.countryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemList.get(position).getName().equalsIgnoreCase("Book Appointment")){
                    Intent intent = new Intent(context, DoctorCategoriesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "categry is "+itemList.get(position).getName(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}
