package com.example.youbike.feature.list;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StationListController {

    private final RecyclerView recyclerView;

    public StationListController(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void bind(List<Station> stations, StationAdapter adapter) {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    public void toggleVisibility() {
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }
}
