package com.example.cosu_pra.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cosu_pra.ConnectFB.HelpChatting;
import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.Comment;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.DTO.StudyPost;
import com.example.cosu_pra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailActivity extends AppCompatActivity {
    Button comment_bt;
    EditText input_comment;
    ImageView image;
    TextView title_text, people_text, date_text, good_text, contents_text;
    String postID, collection, title, people, date, good, contents, comments;
    HelpPosting postHelper;
    SharedPreferences sh_Pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        postID = intent.getStringExtra("postID");
        collection = intent.getStringExtra("collection");
        postHelper = new HelpPosting();

        image = findViewById(R.id.image);
        title_text = findViewById(R.id.title_text);
        people_text = findViewById(R.id.people_text);
        date_text = findViewById(R.id.date_text);
        good_text = findViewById(R.id.good_text);
        contents_text = findViewById(R.id.detail_content);
        sh_Pref = getSharedPreferences("Login Credentials ", MODE_PRIVATE);
        // get post
        postHelper.getPost(collection, postID)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ProjectPost post = documentSnapshot.toObject(ProjectPost.class); // post는 원하는 post 객체를 사용하세요
                        title = post.getTitle();
                        people = post.getUsers().size() + "";
                        date = post.getDate();
                        good = post.getLikes().size() + "";
                        contents = post.getContent();

                        title_text.setText(title);
                        people_text.setText(people);
                        date_text.setText(date);
                        good_text.setText(good);
                        contents_text.setText(contents);
                    }
                });

        RecyclerView recyclerView = findViewById(R.id.recyclerview_comment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CommentAdapter adapter = new CommentAdapter();
        // get post's comments
        postHelper.getComments(collection, postID)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Comment comment = document.toObject(Comment.class);
                                adapter.addItem(new Comment_sub(comment.getContent()));

                            }
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });


        input_comment = findViewById(R.id.input_comment);

        comment_bt = findViewById(R.id.button);
        comment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = input_comment.getText().toString();
                adapter.addItem(new Comment_sub(comment));
                adapter.notifyDataSetChanged();
                String wr = sh_Pref.getString("Email", "");

                Comment com = new Comment(wr, comment);
                postHelper.addComment(collection, postID, com);

            }
        });

        Button report = findViewById(R.id.report_btn);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postHelper.reportPost(collection, postID);
            }
        });

        ImageButton likeBtn = findViewById(R.id.like_btn);
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wr = sh_Pref.getString("Email", "");
                postHelper.addLike(collection, postID, wr);
            }
        });

        Button parButton = findViewById(R.id.particpate_btn);
        parButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wr = sh_Pref.getString("Email", "");
                postHelper.getPost(collection, postID)
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                int user, max;
                                List<String> userList;
                                if (collection.equals(HelpPosting.PROJECT)) {
                                    ProjectPost post = documentSnapshot.toObject(ProjectPost.class); // post는 원하는 post 객체를 사용하세요
                                    user = post.getUsers().size();
                                    max = post.getMax();
                                    userList = post.getUsers();
                                } else {
                                    StudyPost post = documentSnapshot.toObject(StudyPost.class);
                                    user = post.getUsers().size();
                                    max = post.getMax();
                                    userList = post.getUsers();
                                }

                                if (user >= max) {
                                   return;
                                } else if (user + 1 == max) {
                                    userList.add(wr);
                                    HelpChatting chatting = new HelpChatting();
                                    chatting.makeCharRoom(userList);
                                } 
                                postHelper.addUser(collection, postID, wr);
                            }
                        });

            }
        });


//        if (title.equals("안드로이드")){
//            image.setImageDrawable(getResources().getDrawable(R.drawable.android));
//        } else if (title.equals("자바")){
//            image.setImageDrawable(getResources().getDrawable(R.drawable.java));
//        } else if (title.equals("코틀린")){
//            image.setImageDrawable(getResources().getDrawable(R.drawable.kotlin));
//        } else if (title.equals("파이썬")){
//            image.setImageDrawable(getResources().getDrawable(R.drawable.python));
//        }
    }
}