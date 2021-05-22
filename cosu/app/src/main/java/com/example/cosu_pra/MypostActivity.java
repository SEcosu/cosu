package com.example.cosu_pra;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.Adapter.MypostItemAdapter;

public class MypostActivity extends AppCompatActivity {
    ListView lv;
    MypostItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);
        lv = findViewById(R.id.mypostlv);
        adapter = new MypostItemAdapter();
        adapter.addItem(new mypostList("스터디원 구해요", "JAVA"));
        lv.setAdapter(adapter);
    }
}
