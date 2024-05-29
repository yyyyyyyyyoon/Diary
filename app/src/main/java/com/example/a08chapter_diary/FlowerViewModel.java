package com.example.a08chapter_diary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FlowerViewModel extends ViewModel {
    private MutableLiveData<Integer> selectedFlower = new MutableLiveData<>();
    public LiveData<Integer> getSelectedFlower() {
        return selectedFlower;
    }
    public void selectFlower(int flowerId) {
        selectedFlower.setValue(flowerId);
    }
}
