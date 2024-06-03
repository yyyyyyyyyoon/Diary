package com.example.a08chapter_diary;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class Fragment2 extends Fragment {
    private FlowerViewModel flowerViewModel;
    private ImageView imageView;
    private TextView textView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flowerViewModel = new ViewModelProvider(requireActivity()).get(FlowerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);

        flowerViewModel.getSaveCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                if (count != null) {
                    switch (count) {
                        case 1:
                            imageView.setImageResource(R.drawable.seeds);
                            textView.setText("씨앗을 심은 상태입니다!\n 작은 새싹을 위해 일기를 작성해주세요.");
                            break;
                        case 2:
                            imageView.setImageResource(R.drawable.small_sprout);
                            textView.setText("작은 새싹이 자란 상태입니다!\n 큰 새싹을 위해 일기를 작성해주세요.");
                            break;
                        case 3:
                            imageView.setImageResource(R.drawable.big_sprout);
                            textView.setText("큰 새싹이 자란 상태입니다!\n 새로운 꽃을 위해 일기를 작성해주세요.");
                            break;
                        default:
                            imageView.setImageResource(0); // 기본 이미지 또는 빈 이미지 설정
                            textView.setText("아무것도 심어지지 않은 상태입니다!\n 일기를 작성하여 씨앗을 심어주세요.");
                            break;
                    }
                }
            }
        });
        return view;
    }}