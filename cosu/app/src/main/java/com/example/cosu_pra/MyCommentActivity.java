package com.example.cosu_pra;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.Adapter.MyCommentItemAdapter;
import com.example.cosu_pra.Adapter.MypostItemAdapter;
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
        lv.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        _userID = firebaseAuth.getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        //project에서 comment정보를 가져오는 방법
        db.collection("Projects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<CommentItem> commentItemList = new ArrayList<>();

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getId().equals(_userID)) continue;
                        CommentItem commentItem = document.toObject(CommentItem.class);
                        commentItemList.add(commentItem);
                        break;
                    }
                    adapter = new MyCommentItemAdapter();
                    lv.setAdapter(adapter);
                } else {
                    Log.d("MyCommentActivity", "Error getting documents: ", task.getException());
                }
            }
        });

    }
}