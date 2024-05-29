package com.example.a08chapter_diary;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import kotlinx.coroutines.flow.Flow;

public class Fragment2 extends Fragment {
    private FlowerViewModel flowerViewModel;
    private Adapter adapter =null;
    private GridView gridView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flowerViewModel = new ViewModelProvider(requireActivity()).get(FlowerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        gridView = view.findViewById(R.id.gridView1);
        adapter = new Adapter(getActivity());
        gridView.setAdapter(adapter);

        // LiveData를 관찰하여 데이터가 변경될 때마다 그리드뷰 갱신
        flowerViewModel.getSelectedFlower().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer flowerId) {
                if (flowerId != null) {
                    adapter.addFlowerImage(flowerId);
                }
            }
        });


        return view;
    }

    public class Adapter extends BaseAdapter {
        Context context;
        private ArrayList<Integer> flowerImages;

        public Adapter(Context c) {
            context = c;
            flowerImages = new ArrayList<>();

        }
        public void addFlowerImage(int flowerImage) {
            flowerImages.add(flowerImage);
            notifyDataSetChanged();
        }
        public int getCount() { return flowerImages.size(); } //그리드뷰에 보일 이미지 개수 반환
        public Object getItem(int position) {
            return flowerImages.get(position);
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView==null){
                imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(300,300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5,5,5,5);
            }else{
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(flowerImages.get(position));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View dialogView = View.inflate(context, R.layout.dialog_f2, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                    ImageView flowerInfo = dialogView.findViewById(R.id.flowerInfo);
                    flowerInfo.setImageResource(flowerImages.get(position));
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });

            return imageView;

        }
    }
}