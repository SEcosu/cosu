package com.example.cosu_pra;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.Adapter.MyCommentItemAdapter;

public class MyCommentActivity extends AppCompatActivity {
    ListView lv;
    MyCommentItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        lv = findViewById(R.id.mycommentlv);
        adapter = new MyCommentItemAdapter();
        adapter.addItem(new MycommentItem("참여하고 싶습니다.", "홍길동"));
        lv.setAdapter(adapter);
    }
}