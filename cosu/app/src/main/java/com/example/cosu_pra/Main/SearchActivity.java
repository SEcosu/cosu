package com.example.cosu_pra.Main;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cosu_pra.R;


public class SearchActivity extends AppCompatActivity {


    ListView listView;
    StudyAdapter adapter ;
    StudyItem studyItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView =(ListView) findViewById(R.id.listview);

        adapter = new StudyAdapter() ;
        listView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.android),
                "안드로이드" , "5", "~2021/5/31","3","5") ;
        adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.java),
                "자바" , "6", "~2021/5/31","4","6") ;
        adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.kotlin),
                "코틀린" , "3", "~2021/5/31","1","0") ;
        adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.python),
                "파이썬" , "7", "~2021/5/31","2","2") ;

    }
}
