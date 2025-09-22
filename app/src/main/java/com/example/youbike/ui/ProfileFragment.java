package com.example.youbike.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.youbike.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Retrieve the user details from the arguments
        Bundle args = getArguments();
        if (args != null) {
            String userName = args.getString("USER_NAME");
            String userEmail = args.getString("USER_EMAIL");
            double userBalance = args.getDouble("USER_BALANCE");
            String easyCardNumber = args.getString("EASYCARD_NUMBER");

            binding.textViewName.setText(userName);
            binding.textViewEmail.setText(userEmail);
            binding.textViewBalance.setText(String.valueOf(userBalance));
            binding.textViewEasyCard.setText(easyCardNumber);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
