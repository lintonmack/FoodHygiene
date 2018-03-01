package com.example.linton.foodhygiene;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by linton on 21/02/2018.
 */

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants){
        super(context, 0, restaurants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Restaurant restaurant = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);
        }
        TextView tvBName = (TextView) convertView.findViewById(R.id.businessName);
        TextView tvAdd1 = (TextView) convertView.findViewById(R.id.addressLine1);
        TextView tvAdd2 = (TextView) convertView.findViewById(R.id.addressLine2);
        TextView tvAdd3 = (TextView) convertView.findViewById(R.id.addressLine3);
        TextView tvpCode = (TextView) convertView.findViewById(R.id.postCode);
        ImageView hygRating = (ImageView) convertView.findViewById(R.id.hygieneRating);
        TextView distnceKMResult = (TextView) convertView.findViewById(R.id.distanceKm);
        TextView distanceKMLabel = (TextView) convertView.findViewById(R.id.distanceKmLabel);


        tvBName.setText(restaurant.businessName);
        tvAdd1.setText(restaurant.addressLine1);
        tvAdd2.setText(restaurant.addressLine2);
        tvAdd3.setText(restaurant.addressLine3);
        tvpCode.setText(restaurant.postCode);
        distnceKMResult.setText(restaurant.distanceKM);
        hygRating.setImageResource(restaurant.getHygRatingImageDrawable());



        return convertView;
    }



}
