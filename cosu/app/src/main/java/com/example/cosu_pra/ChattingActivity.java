package com.example.cosu_pra;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity {
    ArrayList<String> chatoutList;
    ArrayAdapter<String> chatoutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ImageButton End = findViewById(R.id.project_end);
        ImageButton Exit = findViewById(R.id.exit);


        //TODO 채팅방나가기
        End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //TODO 3 DOT 버튼
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmenu = new PopupMenu(ChattingActivity.this, Exit);
                popupmenu.getMenuInflater().inflate(R.menu.popup1,popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //TODO 벨 버튼 클릭 후 이벤트
                        switch(item.getItemId()){
                            case R.id.menu1:
                                Toast.makeText(getApplicationContext(), "푸시 알림 설정", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu2:
                                Toast.makeText(getApplicationContext(), "진동", Toast.LENGTH_SHORT).show();

                                break;

                        }
                        return false;
                    }
                });
                popupmenu.show();
            }
        });



    }

}