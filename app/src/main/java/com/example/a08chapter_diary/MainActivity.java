package com.example.a08chapter_diary;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    MenuItem menuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Diary");

        bottomNavigationView = findViewById(R.id.bottomNavi);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new Fragment1()).commit();

        //처음화면
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.item_fragment1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment1()).commit();
                }
                else if (menuItem.getItemId() == R.id.item_fragment2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment2()).commit();
                }
                else if (menuItem.getItemId() == R.id.item_fragment3) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Fragment3()).commit();
                }
                return true;
            }
        });







    }


}
