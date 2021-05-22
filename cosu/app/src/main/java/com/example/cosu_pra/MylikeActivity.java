package com.example.cosu_pra;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.Adapter.MylikeItemAdapter;

public class MylikeActivity extends AppCompatActivity {
    ListView lv;
    MylikeItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylike);
        lv = findViewById(R.id.mypostlv);
        adapter = new MylikeItemAdapter();
        adapter.addItem(new MyLikeItem("스터디원 구해요", "허서윤"));
        lv.setAdapter(adapter);
    }

}