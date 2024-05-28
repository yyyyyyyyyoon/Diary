package com.example.a08chapter_diary;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Fragment2 extends Fragment {

    private int[] data = {
            R.drawable.flower1,
            R.drawable.flower2,
            R.drawable.flower3,
            R.drawable.flower4
    };
    private Adapter adapter =null;
    private GridView gridView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        gridView = view.findViewById(R.id.gridView1);
        adapter = new Adapter(getActivity(), data);
        gridView.setAdapter(adapter);

        return view;
    }

    public class Adapter extends BaseAdapter {
        Context context;
        public Adapter(Context c, int[] data) {
            context = c;
        }
        public int getCount() { return data.length; } //그리드뷰에 보일 이미지 개수 반환
        public Object getItem(int arg0) { return null; }
        public long getItemId(int arg0) { return 0; }
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
            imageView.setImageResource(data[position]);

            final int pos = position;
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    View dialogView = (View) View.inflate(getActivity(), R.layout.dialog_f2, null);
                    //getActivity()는 프래그먼트의 Activity 반환 (Context를 얻기 위함)
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                    ImageView flowerInfo = (ImageView) dialogView.findViewById(R.id.flowerInfo);
                    flowerInfo.setImageResource(data[pos]);
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });

            return imageView;

    }
}
}