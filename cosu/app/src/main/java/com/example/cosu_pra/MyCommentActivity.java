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
        adapter.addItem(new CommentItem("스터디원 구해요", "김가천"));
        lv.setAdapter(adapter);
    }
}