package com.example.cosu_pra.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.R;

public class CalenderActivity extends AppCompatActivity {
    int selectedYear, selectedMonth, selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        final CalendarView cPicker = findViewById(R.id.cPicker);
        Button btnPick = findViewById(R.id.btnPick);

        // CalendarView 날짜 변경 시 동작하는 이벤트
        cPicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // OnClick 이벤트에서 사용하기 위해 저장
                selectedYear = year;
                selectedMonth = month + 1; // 0~11월을 사용하므로 +1 필요
                selectedDay = dayOfMonth;
            }
        });

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedYear == 0) {
                    Toast.makeText(CalenderActivity.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(CalenderActivity.this, selectedYear + "년 " + selectedMonth + "월 " + selectedDay + "일", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
