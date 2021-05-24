package com.example.cosu_pra;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ImageButton btn = (ImageButton) findViewById(R.id.amypage_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupmenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.popup_admin, popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //TODO 벨 버튼 클릭 후 이벤트
                        if (item.getItemId() == R.id.aaction_menu3) {
                            Intent intent = new Intent(AdminActivity.this, ReportActivity.class);
                            startActivity(intent);
                        }

                        return false;
                    }
                });
            }
        });
    }
}
