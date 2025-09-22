package com.example.youbike.ui.value;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ValueViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ValueViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Value fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}