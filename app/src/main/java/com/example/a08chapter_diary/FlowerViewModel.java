package com.example.a08chapter_diary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlowerViewModel extends ViewModel {
    private FlowerViewModel flowerViewModel;

    private final MutableLiveData<Integer> saveCount = new MutableLiveData<>();
    private MutableLiveData<List<Integer>> flowerImages = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<String>> flowerNames = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<String>> flowerMessages = new MutableLiveData<>(new ArrayList<>());

    //count 횟수
    public void setSaveCount(int count) {
        saveCount.setValue(count);
    }

    public LiveData<Integer> getSaveCount() {
        return saveCount;
    }

    //Flower 이미지, 이름, 꽃말
    public LiveData<List<Integer>> getFlowerImages() {
        return flowerImages;
    }

    public LiveData<List<String>> getFlowerNames() {
        return flowerNames;
    }

    public LiveData<List<String>> getFlowerMessages() {
        return flowerMessages;
    }

    public void selectFlower(int imageResId, String name, String message) {
        List<Integer> currentImages = flowerImages.getValue();
        List<String> currentNames = flowerNames.getValue();
        List<String> currentMessages = flowerMessages.getValue();

        if (currentImages != null && currentNames != null && currentMessages != null) {
            currentImages.add(imageResId);
            currentNames.add(name);
            currentMessages.add(message);

            flowerImages.setValue(currentImages);
            flowerNames.setValue(currentNames);
            flowerMessages.setValue(currentMessages);

        }
    }


    public void setFlowerImages(List<Integer> flowerImages) {
        this.flowerImages.setValue(flowerImages);
    }

    public void setFlowerNames(List<String> flowerNames) {
        this.flowerNames.setValue(flowerNames);
    }

    public void setFlowerMessages(List<String> flowerMessages) {
        this.flowerMessages.setValue(flowerMessages);
    }
}

