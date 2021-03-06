package com.example.cosu_pra.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    EditText title, contents;
    TextView together;
    Spinner category_spinner, max_spinner;
    Button plus_btn, date_start, date_end;
    HelpPosting postHelper;
    SharedPreferences sh_Pref;
    String collection;
    LinearLayout dateContainer;

    private int REQUEST_TEST = 1;
    private int REQUEST_TEST2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);


        collection = getIntent().getStringExtra("collection");
        postHelper = new HelpPosting();
        title = findViewById(R.id.project_plus_title);
        contents = findViewById(R.id.project_plus_contents);
        category_spinner = findViewById(R.id.project_plus_category);
        max_spinner = findViewById(R.id.project_plus_max);
        plus_btn = findViewById(R.id.project_plus_btn);
        together = findViewById(R.id.together_textview);
        date_start = (Button) findViewById(R.id.date_start);
        date_end = (Button) findViewById(R.id.date_end);
        dateContainer = findViewById(R.id.plus_date_container);
        sh_Pref = getSharedPreferences("Login Credentials ", MODE_PRIVATE);

        // check collection to know add what type of post
        if (collection.equals(HelpPosting.PROJECT)) {
            together.setText("???????????? ????????????!");
            category_spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.project_category)));
        }
        if (collection.equals(HelpPosting.STUDY)) {
            together.setText("????????? ????????????!");
            category_spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.study_category)));
        }
        if (collection.equals(HelpPosting.QNA)) {
            together.setText("???????????????!");
            category_spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.qna_category)));

            max_spinner.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) max_spinner.getLayoutParams();
            params.weight = 0;
            max_spinner.setLayoutParams(params);

            dateContainer.removeAllViews();
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) dateContainer.getLayoutParams();
            params2.height = 0;
            dateContainer.setLayoutParams(params2);
        }

        // when click date start button
        // it is not visible in QnA collection
        date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalenderActivity.class);
                startActivityForResult(intent, REQUEST_TEST);
                // startActivityForResult(intent);
            }
        });

        // when click date end button
        // it is not visible in QnA collection
        date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalenderActivity.class);
                startActivityForResult(intent, REQUEST_TEST2);
                // startActivityForResult(intent);
            }
        });

        // when click add post button
        // check if post is valid or invalid
        // if post is invalid, then do not update and show toast message
        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check post valid
                if (title.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "????????? ????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (contents.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "????????? ????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (category_spinner.getSelectedItem().toString().equals("????????????")) {
                    Toast.makeText(getApplicationContext(), "??????????????? ???????????????", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!collection.equals(HelpPosting.QNA) && max_spinner.getSelectedItem().toString().equals("?????????")) {
                    Toast.makeText(getApplicationContext(), "?????? ?????? ???????????????", Toast.LENGTH_LONG).show();
                    return;
                }

                // get post information
                String ti = title.getText().toString();
                String wr = sh_Pref.getString("Email", "");
                String co = contents.getText().toString();
                String category =category_spinner.getSelectedItem().toString();

                int max;

                // update post to firebase
                if (collection.equals(HelpPosting.PROJECT)) {
                    max = Integer.parseInt(max_spinner.getSelectedItem().toString());
                    String start = (String) date_start.getText();
                    String end = (String) date_end.getText();
                    ProjectPost post = new ProjectPost(ti, wr, co, max, category, start, end);
                    postHelper.addPost(collection, post);
                }
                if (collection.equals(HelpPosting.STUDY)) {
                    max = Integer.parseInt(max_spinner.getSelectedItem().toString());
                    String start = (String) date_start.getText();
                    String end = (String) date_end.getText();
                    StudyPost post = new StudyPost(ti, wr, co, max, category, start, end);
                    postHelper.addPost(collection, post);
                }
                if (collection.equals(HelpPosting.QNA)) {
                    QnAPost post = new QnAPost(ti, wr, co, category);
                    postHelper.addPost(collection, post);
                }

                // finis activity, return to main fragment
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        date_start = findViewById(R.id.date_start);
        date_end = findViewById(R.id.date_end);
        if (requestCode == REQUEST_TEST) {
            if (resultCode == RESULT_OK) {
//                Toast.makeText(PlusActivity.this, "Result: " + data.getStringExtra("date_start"), Toast.LENGTH_SHORT).show();
                String sendText = data.getStringExtra("date_start");
                date_start.setText(sendText);
            } else {   // RESULT_CANCEL
                Toast.makeText(PlusActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
//        } else if (requestCode == REQUEST_ANOTHER) {
//            ...
        }
        if (requestCode == REQUEST_TEST2) {
            if (resultCode == RESULT_OK) {
//                Toast.makeText(PlusActivity.this, "Result: " + data.getStringExtra("date_end"), Toast.LENGTH_SHORT).show();
                String sendText = data.getStringExtra("date_end");
                date_end.setText(sendText);
            } else {   // RESULT_CANCEL
                Toast.makeText(PlusActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
//        } else if (requestCode == REQUEST_ANOTHER) {
//            ...
        }

    }
}
