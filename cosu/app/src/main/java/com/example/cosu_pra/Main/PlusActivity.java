package com.example.cosu_pra.Main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.ConnectFB.HelpChatting;
import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.ProjectPost;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        postHelper = new HelpPosting();
        title = findViewById(R.id.project_plus_title);
        contents = findViewById(R.id.project_plus_contents);
        category_spinner = findViewById(R.id.project_plus_category);
        max_spinner = findViewById(R.id.project_plus_max);
        plus_btn = findViewById(R.id.project_plus_btn);
        sh_Pref = getSharedPreferences ("Login Credentials ", MODE_PRIVATE);


        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ti = title.getText().toString();
                String wr = sh_Pref.getString("Email","");
                String co = contents.getText().toString();
                List<String> list = new ArrayList<String>();
                list.add(category_spinner.getSelectedItem().toString());
                int max = Integer.parseInt(max_spinner.getSelectedItem().toString());
                ProjectPost post = new ProjectPost(ti,wr,co,max,list);
                postHelper.addPost(HelpPosting.PROJECT,post);

                finish();
            }
        });
    }
}