package com.example.youbike;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.youbike.data.LoginDataSource;
import java.util.List;
import java.util.Objects;

public class DockAdapter extends RecyclerView.Adapter<DockAdapter.ViewHolder> {
    private List<Dock> dockList;
    private StationDetailsActivity activity;
    private LoginDataSource loginDataSource;
    private DatabaseHelper dbHelper;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dockIDTextView;
        public TextView bikeIDTextView;
        public Button rentButton;
        public Button returnButton;

        public ViewHolder(View view) {
            super(view);
            dockIDTextView = view.findViewById(R.id.dock_id);
            bikeIDTextView = view.findViewById(R.id.bike_id);
            rentButton = view.findViewById(R.id.rent_button);
            returnButton = view.findViewById(R.id.return_button);
        }
    }

    public DockAdapter(StationDetailsActivity activity, List<Dock> dockList, DatabaseHelper dbHelper) {
        this.activity = activity;
        this.dockList = dockList;
        this.dbHelper = dbHelper;
        this.loginDataSource = new LoginDataSource(dbHelper.getReadableDatabase());
    }

    @Override
    public DockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_dock, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dock dock = dockList.get(position);
        holder.dockIDTextView.setText(dock.getDockID());
        holder.bikeIDTextView.setText(dock.getBikeID());

        holder.rentButton.setOnClickListener(v -> {
            String email = activity.getCurrentUserEmail();// Get email from the activity
            if (email != null){
                double balance = dbHelper.getBalanceByEmail(email);// Get the balance just before renting
                if (balance >= 10.0){
                    if (!Objects.equals(dock.getBikeID(), "")) {
                        activity.rentBike(dock.getDockUID(), dock.getBikeID());
                    } else {
                        Toast.makeText(activity, "沒有可供租借的Youbike！", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(activity, "您的餘額不足！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.returnButton.setOnClickListener(v -> {
            if (Objects.equals(dock.getBikeID(), "")) {
                activity.returnBike(dock.getDockUID(), dock.getBikeID());
                activity.deductBalance(activity.getCurrentUserEmail(), 10.0);
            } else {
                Toast.makeText(activity, "站位上已經有一台Youbike了！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dockList.size();
    }

}
