package com.example.cosu_pra.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.ConnectFB.HelpChatting;
import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.DTO.QnAPost;
import com.example.cosu_pra.DTO.StudyPost;
import com.example.cosu_pra.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;



public class PlusActivity extends AppCompatActivity {
    EditText title;
    EditText contents;
    Spinner category_spinner;
    Spinner max_spinner;
    Button plus_btn;
    HelpPosting postHelper;
    SharedPreferences sh_Pref;
    String collection;
    TextView together;
    Button date_start;
    Button date_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        //TODO 모집기간 설정
        Button date_start = (Button)findViewById(R.id.date_start);
        Button date_end = (Button)findViewById(R.id.date_end);

        date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalenderActivity.class);
                startActivity(intent);
            }
        });

        date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalenderActivity.class);
                startActivity(intent);
            }
        });

        collection = getIntent().getStringExtra("collection");

        postHelper = new HelpPosting();
        title = findViewById(R.id.project_plus_title);
        contents = findViewById(R.id.project_plus_contents);
        category_spinner = findViewById(R.id.project_plus_category);
        max_spinner = findViewById(R.id.project_plus_max);
        plus_btn = findViewById(R.id.project_plus_btn);
        together = findViewById(R.id.together_textview);
        sh_Pref = getSharedPreferences("Login Credentials ", MODE_PRIVATE);
//        Intent intent = new Intent(this, CalenderActivity.class);
//        startActivity(intent);
        if (collection.equals(HelpPosting.PROJECT)) {
            category_spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.project_category)));
            together.setText("프로젝트 같이해요!");
        }
        if (collection.equals(HelpPosting.STUDY)) {
            category_spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.study_category)));
            together.setText("스터디 같이해요!");
        }
        if (collection.equals(HelpPosting.QNA)) {
            category_spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.qna_category)));
            together.setText("질문있어요!");
            max_spinner.setVisibility(View.INVISIBLE);
            category_spinner.setMinimumWidth(category_spinner.getWidth());
        }


        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "제목을 써주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (contents.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "내용을 써주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (category_spinner.getSelectedItem().toString().equals("카테고리")) {
                    Toast.makeText(getApplicationContext(), "카테고리를 골라주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!collection.equals(HelpPosting.QNA) && max_spinner.getSelectedItem().toString().equals("인원수")) {
                    Toast.makeText(getApplicationContext(), "인원 수를 골라주세요", Toast.LENGTH_LONG).show();
                    return;
                }

                String ti = title.getText().toString();
                String wr = sh_Pref.getString("Email", "");
                String co = contents.getText().toString();
                List<String> list = new ArrayList<String>();
                list.add(category_spinner.getSelectedItem().toString());
                int max; 

                if(collection.equals(HelpPosting.PROJECT)) {
                    max = Integer.parseInt(max_spinner.getSelectedItem().toString());
                    ProjectPost post = new ProjectPost(ti, wr, co, max, list);
                    postHelper.addPost(collection, post);
                }
                if(collection.equals(HelpPosting.STUDY)) {
                    max = Integer.parseInt(max_spinner.getSelectedItem().toString());
                    StudyPost post = new StudyPost(ti, wr, co, max, list);
                    postHelper.addPost(collection, post);
                }
                if(collection.equals(HelpPosting.QNA)) {
                    QnAPost post = new QnAPost(ti, wr, co, list);
                    postHelper.addPost(collection, post);
                }
                finish();
            }
        });
    }
}