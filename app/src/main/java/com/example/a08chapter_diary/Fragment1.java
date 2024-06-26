package com.example.a08chapter_diary;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;



public class Fragment1 extends Fragment {

    CalendarView cv;
    EditText edtDiary;
    Button btnWrite;
    String fileName;
    SharedPreferences prefs;
    int saveCount;
    private FlowerViewModel flowerViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flowerViewModel = new ViewModelProvider(requireActivity()).get(FlowerViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        prefs = getActivity().getSharedPreferences("DiaryPrefs", Context.MODE_PRIVATE);
        saveCount = prefs.getInt("saveCount", 0);

        cv = view.findViewById(R.id.calendarView);
        edtDiary = view.findViewById(R.id.edtDiary);
        btnWrite = view.findViewById(R.id.btnWrite);

        edtDiary.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // 키보드 내리기
                    edtDiary.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(edtDiary.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fileName = year + "_" + (month + 1) + "_" + dayOfMonth + ".txt";
                String str = readDiary(fileName);
                edtDiary.setText(str);
                btnWrite.setEnabled(true);
            }
        });

        btnWrite.setOnClickListener(v -> {
            try {
                FileOutputStream outFs = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
                String str = edtDiary.getText().toString();
                outFs.write(str.getBytes());
                outFs.close();

                //저장 횟수 증가 및 저장
                saveCount++;
                flowerViewModel.setSaveCount(saveCount);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("saveCount", saveCount);
                editor.apply();

                //다이얼로그 표시
                showCustomDialog(saveCount);
                Log.d("Fragment1", "Save Count: " + saveCount);

                //저장 횟수 초기화
                if (saveCount == 4) {
                    saveCount = 0;
                    editor.putInt("saveCount", saveCount);
                    editor.apply();
                    Log.d("Fragment1", "Save Count reset to 0");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return view;
    }

    String readDiary(String fName) {
        String diaryStr = null;

        try {
            FileInputStream inFs = getActivity().openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();

            diaryStr = (new String(txt)).trim();
            btnWrite.setText("수정하기");
        } catch (IOException e) {
            edtDiary.setHint("오늘의 일기를 작성하세요");
            btnWrite.setText("일기 저장");
        }
        return diaryStr;
    }

    // 커스텀 다이얼로그 표시 메소드
    void showCustomDialog(int count) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_f1);

        ImageView imageView = dialog.findViewById(R.id.image);
        TextView textView = dialog.findViewById(R.id.text);

        int[] flowerImages = {R.drawable.flower1, R.drawable.flower2, R.drawable.flower3, R.drawable.flower4, R.drawable.flower5, R.drawable.flower6, R.drawable.flower7, R.drawable.flower8};
        String[] flowerNames = {"해바라기", "튤립", "라벤더", "금계국", "장미", "네잎클로버", "무궁화", "데이지"};
        String[] flowerMessages = {"영원한 사랑", "사랑의 고백", "침묵과 기대", "상쾌한 기분", "정열적인 사랑", "행운, 평화, 약속", "일편단심", "겸손함"};

        Random random = new Random();
        int randomNumber = random.nextInt(8);

        // 랜덤으로 선택된 꽃 정보 설정
        String flowerName = flowerNames[randomNumber];
        String flowerMessage = flowerMessages[randomNumber];

        switch (count) {
            case 1:
                imageView.setImageResource(R.drawable.seeds);
                textView.setText("씨앗을 얻었습니다! (1/4)");
                break;
            case 2:
                imageView.setImageResource(R.drawable.small_sprout);
                textView.setText("작은 새싹이 자랐습니다! (2/4)");
                break;
            case 3:
                imageView.setImageResource(R.drawable.big_sprout);
                textView.setText("큰 새싹이 자랐습니다! (3/4)");
                break;
            case 4:
                imageView.setImageResource(flowerImages[randomNumber]);
                textView.setText(flowerName + "을/를 얻었습니다. 꽃말은 '" + flowerMessage + "'입니다!");

                // FlowerViewModel을 사용하여 선택된 꽃 정보 설정
                if (flowerViewModel != null) {
                    flowerViewModel.selectFlower(flowerImages[randomNumber], flowerNames[randomNumber], flowerMessages[randomNumber]);
                } else {
                    Log.e("Fragment1", "flowerViewModel is null");
                }
                break;
        }

        dialog.show();

        // 3초 후 다이얼로그 닫기
        new Handler().postDelayed(dialog::dismiss, 3000);
    }
}


