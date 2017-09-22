package com.example.tushar.pgi.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tushar.pgi.R;

/**
 * Created by Tushar on 9/21/2017.
 */

public class ItemViewHolders extends RecyclerView.ViewHolder {

    public TextView countryName;
    public ImageView countryPhoto;

    public ItemViewHolders(View itemView) {
        super(itemView);
        countryName = (TextView) itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
    }
}
