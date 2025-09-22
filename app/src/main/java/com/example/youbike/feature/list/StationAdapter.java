package com.example.youbike.feature.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.youbike.R;
import com.example.youbike.StationDetailsActivity;

import java.util.List;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {
    private List<Station> stationList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stationNameTextView;
        public TextView stationUIDTextView;
        public TextView stationCapacityTextView;

        public ViewHolder(View view) {
            super(view);
            stationNameTextView = view.findViewById(R.id.station_name);
            stationUIDTextView = view.findViewById(R.id.station_UID);
            stationCapacityTextView = view.findViewById(R.id.station_capacity);
        }
    }

    public StationAdapter(Context context, List<Station> stationList) {
        this.stationList = stationList;
        this.context = context;
    }

    @Override
    public StationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Station station = stationList.get(position);
        holder.stationNameTextView.setText(station.getStationName());
        holder.stationUIDTextView.setText(station.getStationUID());
        holder.stationCapacityTextView.setText("場站總車格：" + station.getBikesCapacity());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StationDetailsActivity.class);
            intent.putExtra("stationName", station.getStationName());
            intent.putExtra("stationUID", station.getStationUID());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }
}
