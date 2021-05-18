package com.example.cosu_pra.Main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.R;


public class DetailActivity extends AppCompatActivity {

    ImageView image;
    TextView title_text,people_text,date_text,good_text;
    String title,people,date,good;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        people = intent.getStringExtra("people");
        date = intent.getStringExtra("date");
        good = intent.getStringExtra("good");

        image = findViewById(R.id.image);
        title_text = findViewById(R.id.title_text);
        people_text = findViewById(R.id.people_text);
        date_text = findViewById(R.id.date_text);
        good_text = findViewById(R.id.good_text);

        title_text.setText(title);
        people_text.setText(people);
        date_text.setText(date);
        good_text.setText(good);

        if (title.equals("안드로이드")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.android));
        } else if (title.equals("자바")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.java));
        } else if (title.equals("코틀린")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.kotlin));
        } else if (title.equals("파이썬")){
            image.setImageDrawable(getResources().getDrawable(R.drawable.python));
        }
    }
}