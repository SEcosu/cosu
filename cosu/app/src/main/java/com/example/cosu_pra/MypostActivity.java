package com.example.cosu_pra;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.Adapter.MypostItemAdapter;
import com.example.cosu_pra.ConnectFB.HelpChatting;
import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.DTO.QnAPost;
import com.example.cosu_pra.DTO.StudyPost;
import com.example.cosu_pra.Main.DetailActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MypostActivity extends AppCompatActivity {
    ListView lv;
    MypostItemAdapter adapter;
    SharedPreferences sh_Pref;
    //Firebase
    private FirebaseFirestore db;


    private String _userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);
        lv = findViewById(R.id.mypostlv);
        adapter = new MypostItemAdapter();
        //  lv.setAdapter(adapter);
        sh_Pref = getSharedPreferences("Login Credentials ", MODE_PRIVATE);

        _userID = sh_Pref.getString("Email", "");
        db = FirebaseFirestore.getInstance();
        Log.d("test","My id: "+_userID);

        db.collection(HelpPosting.PROJECT).whereEqualTo("writer", _userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProjectPost post = document.toObject(ProjectPost.class);
                                MyPostItem item = new MyPostItem(post.getContent(), post.getCategory());
                                item.collection = HelpPosting.PROJECT;
                                item.postID = document.getId();
                                adapter.addItem(item);
                            }

                            lv.setAdapter(adapter);
                        } else {
                            Log.d("MypostActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });
        db.collection(HelpPosting.STUDY).whereEqualTo("writer", _userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<MyPostItem> myPostItemList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                StudyPost post = document.toObject(StudyPost.class);
                                MyPostItem item = new MyPostItem(post.getContent(), post.getCategory());
                                item.collection = HelpPosting.STUDY;
                                item.postID = document.getId();
                                adapter.addItem(item);
                            }

                            lv.setAdapter(adapter);
                        } else {
                            Log.d("MypostActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });
        db.collection(HelpPosting.QNA).whereEqualTo("writer", _userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<MyPostItem> myPostItemList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                QnAPost post = document.toObject(QnAPost.class);
                                MyPostItem item = new MyPostItem(post.getContent(), post.getCategory());
                                item.collection = HelpPosting.QNA;
                                item.postID = document.getId();
                                adapter.addItem(item);
                            }
                            lv.setAdapter(adapter);
                        } else {
                            Log.d("MypostActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });

        // item select
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("postID", adapter.getItem(position).postID);
                intent.putExtra("collection", adapter.getItem(position).collection);
                startActivity(intent);
            }
        });

    }


}
