package com.example.aleksandr.testtaskn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aleksandr on 30.04.18.
 */

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Place> places;

    DataAdapter(Context context, List<Place> places) {
        this.places = places;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Place place = places.get(position);
        holder.lonView.setText(place.getLon());
        holder.latView.setText(place.getLat());
        holder.addressView.setText(place.getAddress());
        holder.dateView.setText(place.getDate());
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView lonView, latView, addressView,dateView;
        ViewHolder(View view){
            super(view);
            lonView = (TextView) view.findViewById(R.id.lon);
            latView= (TextView) view.findViewById(R.id.lat);
            addressView= (TextView) view.findViewById(R.id.address);
            dateView= (TextView) view.findViewById(R.id.date);
        }
    }
}