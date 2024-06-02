package com.example.a08chapter_diary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FlowerViewModel extends ViewModel {
    private MutableLiveData<List<Integer>> flowerImages = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Integer>> getFlowerImages() {
        return flowerImages;
    }
    public void selectFlower(int flowerId) {
        List<Integer> currentFlowers = flowerImages.getValue();
        if (currentFlowers != null) {
            currentFlowers.add(flowerId);
            flowerImages.setValue(currentFlowers); // 데이터를 업데이트하여 관찰자에게 알림
        }
    }

}