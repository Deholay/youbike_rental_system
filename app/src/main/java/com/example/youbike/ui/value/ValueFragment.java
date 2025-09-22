// In ValueFragment.java
package com.example.youbike.ui.value;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.youbike.DatabaseHelper;
import com.example.youbike.R;

public class ValueFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    private TextView balanceTextView;
    private EditText amountEditText;
    private Button addButton;
    private String userEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_value, container, false);

        TextView balanceTextView = view.findViewById(R.id.textViewBalance);
        EditText addAmountEditText = view.findViewById(R.id.editTextAddAmount);
        Button addButton = view.findViewById(R.id.buttonAdd);

        // Retrieve the email from the arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            String userEmail = bundle.getString("USER_EMAIL");
            loadUserBalance(userEmail, balanceTextView);

            addButton.setOnClickListener(v -> {
                String addAmountStr = addAmountEditText.getText().toString();
                if (!addAmountStr.isEmpty()) {
                    double addAmount = Double.parseDouble(addAmountStr);
                    addBalance(userEmail, addAmount, balanceTextView);
                } else {
                    Toast.makeText(getActivity(), "Please enter an amount", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "User email not found", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void loadUserBalance(String email, TextView balanceTextView) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        double balance = databaseHelper.getBalanceByEmail(email);
        balanceTextView.setText(String.valueOf(balance));
    }

    private void addBalance(String email, double amount, TextView balanceTextView) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        double currentBalance = databaseHelper.getBalanceByEmail(email);
        double newBalance = currentBalance + amount;

        if (databaseHelper.updateBalance(email, newBalance)) {
            balanceTextView.setText(String.valueOf(newBalance));
            Toast.makeText(getContext(), "Balance updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Failed to update balance", Toast.LENGTH_SHORT).show();
        }
    }

}
