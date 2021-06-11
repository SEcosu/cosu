package com.example.cosu_pra;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.Adapter.MyCommentItemAdapter;
import com.example.cosu_pra.Adapter.MypostItemAdapter;
import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.Comment;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.Main.Comment_sub;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyCommentActivity extends AppCompatActivity {
    ListView lv;
    MyCommentItemAdapter adapter;
    SharedPreferences sh_Pref;

    //Firebase
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;

    private String _userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);

        lv = findViewById(R.id.mycommentlv);
        adapter = new MyCommentItemAdapter();
        adapter.addItem(new CommentItem("스터디원 구해요", "김가천"));

        firebaseAuth = FirebaseAuth.getInstance();
        _userID = firebaseAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        sh_Pref = getSharedPreferences("Login Credentials ", MODE_PRIVATE);
        _userID = sh_Pref.getString("Email", "");
        db = FirebaseFirestore.getInstance();
        Log.d("test","My id: "+_userID);

        // Retrieves user-written comments in Project
        db.collection(HelpPosting.COMMENTS).whereEqualTo("writer", _userID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Comment_sub comment = document.toObject(Comment_sub.class);
                                CommentItem item = new CommentItem(comment.getComment(), comment.getCommentWriter());
                                item.collection = HelpPosting.COMMENTS;
                                item.postID = document.getId();
                                adapter.addItem(item);
                            }

                            lv.setAdapter(adapter);
                        } else {
                            Log.d("MyCommentActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}