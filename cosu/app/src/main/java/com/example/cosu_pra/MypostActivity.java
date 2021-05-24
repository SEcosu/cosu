package com.example.cosu_pra;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.Adapter.MypostItemAdapter;
import com.example.cosu_pra.ConnectFB.HelpChatting;
import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.DTO.QnAPost;
import com.example.cosu_pra.DTO.StudyPost;
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

    //Firebase
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;

    private String _userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);
        lv = findViewById(R.id.mypostlv);
        adapter = new MypostItemAdapter();
        adapter.addItem(new MyPostItem("스터디원 구해요", "JAVA"));
        //  lv.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        _userID = firebaseAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        db.collection(HelpPosting.PROJECT).whereEqualTo("writer", _userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<MyPostItem> myPostItemList = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProjectPost post = document.toObject(ProjectPost.class);
                                MyPostItem item = new MyPostItem(post.getContent(), post.getCategory());
                                item.collection = HelpPosting.PROJECT;
                                item.postID = document.getId();

                                myPostItemList.add(item);
                                break;
                            }
                            adapter = new MypostItemAdapter();
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

                                myPostItemList.add(item);
                                break;
                            }
                            adapter = new MypostItemAdapter();
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
                                item.collection = HelpPosting.PROJECT;
                                item.postID = document.getId();

                                myPostItemList.add(item);
                                break;
                            }
                            adapter = new MypostItemAdapter();
                            lv.setAdapter(adapter);
                        } else {
                            Log.d("MypostActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


}
