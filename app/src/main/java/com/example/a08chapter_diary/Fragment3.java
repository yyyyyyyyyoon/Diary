
package com.example.a08chapter_diary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Fragment3 extends Fragment {
    private FlowerViewModel flowerViewModel;
    private Fragment3.Adapter adapter =null;
    private GridView gridView = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flowerViewModel = new ViewModelProvider(requireActivity()).get(FlowerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        gridView = view.findViewById(R.id.gridView1);
        adapter = new Fragment3.Adapter(getActivity());
        gridView.setAdapter(adapter);

        // LiveData를 관찰하여 데이터가 변경될 때마다 그리드뷰 갱신
        flowerViewModel.getFlowerImages().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> Images) {
                if (Images != null) {
                    adapter.setFlowerImages(Images);
                }
            }
        });

        return view;
    }


    public class Adapter extends BaseAdapter {
        Context context;
        private List<Integer> flowerImages;

        public Adapter(Context c) {
            context = c;
            flowerImages = new ArrayList<>();

        }
        public void setFlowerImages(List<Integer> flowerImages) {
            this.flowerImages = flowerImages;
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
            int flowerImage = flowerImages.get(position);
            imageView.setImageResource(flowerImage);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View dialogView = View.inflate(context, R.layout.dialog_f2, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                    ImageView flowerInfo = dialogView.findViewById(R.id.flowerInfo);
                    TextView flowerName = dialogView.findViewById(R.id.flowerName);
                    TextView flowerMessage = dialogView.findViewById(R.id.flowerMessage);

                    List<String> names = flowerViewModel.getFlowerNames().getValue();
                    List<String> messages = flowerViewModel.getFlowerMessages().getValue();

                    if (names != null && messages != null) {
                        int flowerPosition = flowerImages.indexOf(flowerImage);
                        flowerInfo.setImageResource(flowerImage);
                        flowerName.setText(names.get(flowerPosition));
                        flowerMessage.setText(messages.get(flowerPosition));
                    }

                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });

            return imageView;

        }
    }
}
