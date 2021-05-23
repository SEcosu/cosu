package com.example.cosu_pra;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.Adapter.MyCommentItemAdapter;

public class MylikeActivity extends AppCompatActivity {
    ListView lv;
    MyCommentItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylike);
        lv= findViewById(R.id.mylikelv);
        adapter = new MyCommentItemAdapter();
        adapter.addItem(new CommentItem("스터디원 구해요", "홍길동"));
        lv.setAdapter(adapter);

    }

}