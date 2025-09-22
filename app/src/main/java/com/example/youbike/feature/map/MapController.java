package com.example.youbike.feature.map;

import android.content.Context;
import android.content.Intent;

import com.example.youbike.StationDetailsActivity;
import com.example.youbike.feature.list.Station;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapController {

    private final Context context;
    private final String userEmail;
    private final Map<Marker, Station> markerToStation = new HashMap<>();
    private final Map<Marker, Boolean> markerClickState = new HashMap<>();

    public MapController(Context context, String userEmail) {
        this.context = context;
        this.userEmail = userEmail;
    }

    public void renderStations(GoogleMap map, List<Station> stations) {
        for (Station station : stations) {
            LatLng position = new LatLng(station.getPositionLat(), station.getPositionLon());
            Marker marker = map.addMarker(new MarkerOptions().position(position).title(station.getStationName()));
            if (marker != null) {
                marker.setTag(station);
                markerToStation.put(marker, station);
                markerClickState.put(marker, false);
            }
        }
        if (!stations.isEmpty()) {
            Station first = stations.get(0);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(first.getPositionLat(), first.getPositionLon()), 15f));
        }
        map.setOnMarkerClickListener(this::onMarkerClick);
        map.setOnInfoWindowClickListener(this::onInfoWindowClick);
    }

    private boolean onMarkerClick(Marker marker) {
        Station station = markerToStation.get(marker);
        if (station == null) return false;
        Boolean clicked = markerClickState.get(marker);
        if (clicked == null || !clicked) {
            marker.showInfoWindow();
            markerClickState.put(marker, true);
        }
        return true;
    }

    private void onInfoWindowClick(Marker marker) {
        Station station = markerToStation.get(marker);
        if (station == null) return;
        Intent intent = new Intent(context, StationDetailsActivity.class);
        intent.putExtra("stationUID", station.getStationUID());
        intent.putExtra("stationName", station.getStationName());
        intent.putExtra("USER_EMAIL", userEmail);
        context.startActivity(intent);
        markerClickState.put(marker, false);
    }
}
