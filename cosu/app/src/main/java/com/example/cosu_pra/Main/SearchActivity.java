package com.example.cosu_pra.Main;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SearchActivity extends AppCompatActivity {

    HelpPosting postHelper;
    ListView listView;
    StudyAdapter adapter;
    StudyItem studyItem;
    ArrayList<String> postIDs = new ArrayList<>();
    ArrayList<ProjectPost> posts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        postHelper = new HelpPosting();
        listView = (ListView) findViewById(R.id.listview);

        adapter = new StudyAdapter();
        listView.setAdapter(adapter);

        postHelper.getAllPosts(HelpPosting.PROJECT).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        postIDs.add(document.getId());
                        posts.add(document.toObject(ProjectPost.class));
                    }
                    for (ProjectPost pp : posts) {
                        adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.android),
                                pp.getTitle(), pp.getMax()+"", "~2021/5/31", "3", "5");
                    }
                    listView.setAdapter(adapter);
                }
            }
        });


//        adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.android),
//                "안드로이드", "5", "~2021/5/31", "3", "5");
//        adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.java),
//                "자바", "6", "~2021/5/31", "4", "6");
//        adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.kotlin),
//                "코틀린", "3", "~2021/5/31", "1", "0");
//        adapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.python),
//                "파이썬", "7", "~2021/5/31", "2", "2");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
