package com.example.cosu_pra.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import com.example.cosu_pra.DTO.ChatData;
import com.example.cosu_pra.DTO.Comment;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.DTO.StudyPost;
import com.example.cosu_pra.DTO.User;
import com.example.cosu_pra.MessageItem;
import com.example.cosu_pra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailActivity extends AppCompatActivity {
    Button comment_bt;
    EditText input_comment;
    ImageView image;
    TextView title_text, maxpeople, date_text, good_text, contents_text, writerTextView, commentWriter, nowpeple;
    String postID, collection, title, people, date, good, contents, writer, userEmail;
    HelpPosting postHelper;
    SharedPreferences sh_Pref;
    CommentAdapter adapter;
    RecyclerView recyclerView;


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
        maxpeople = findViewById(R.id.people_text_goal);
        date_text = findViewById(R.id.date_text);
        good_text = findViewById(R.id.good_text);
        contents_text = findViewById(R.id.detail_content);
        sh_Pref = getSharedPreferences("Login Credentials ", MODE_PRIVATE);
        writerTextView = findViewById(R.id.detail_writer);
        recyclerView = findViewById(R.id.recyclerview_comment);
        input_comment = findViewById(R.id.input_comment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        commentWriter = findViewById(R.id.comment_writer);
        nowpeple = findViewById(R.id.people_text);
        adapter = new CommentAdapter();

        userEmail = sh_Pref.getString("Email", "");
        String userNickName = sh_Pref.getString("Nickname", "");
        commentWriter.setText(userNickName);

        // get post
        postHelper.getPost(collection, postID)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ProjectPost post = documentSnapshot.toObject(ProjectPost.class); // post는 원하는 post 객체를 사용하세요
                        title = post.getTitle();
                        if (post.getUsers() != null) people = post.getUsers().size() + "";
                        date = post.getEndDate();
                        good = post.getLikes().size() + "";
                        contents = post.getContent();
                        writer = post.getWriter();

                        title_text.setText(title);
                        maxpeople.setText(post.getMax() + "");
                        date_text.setText(date);
                        good_text.setText(good);
                        contents_text.setText(contents);
                        nowpeple.setText(people);

                        postHelper.getUserNickname(writer).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("test", document.toString());
                                        User user = document.toObject(User.class);
                                        String nickName = user.getNickName();
                                        String name = user.getEmail();
                                        writerTextView.setText(nickName);
                                    }

                                }
                            }
                        });
                    }
                });
        // get post's comments
        //loadComments();


        // 댓글올리기
        comment_bt = findViewById(R.id.button);
        comment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = input_comment.getText().toString();
                String wr = sh_Pref.getString("Email", "");

                Comment com = new Comment(wr, comment);
                postHelper.addComment(collection, postID, com);

            }
        });

        // 신고하기
        Button report = findViewById(R.id.report_btn);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postHelper.reportPost(collection, postID);

            }
        });

        // 좋아요 버튼
        ImageButton likeBtn = findViewById(R.id.like_btn);
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wr = sh_Pref.getString("Email", "");
                postHelper.addLike(collection, postID, wr);

            }
        });

        // 참여하기 버튼
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
                                    chatting.makeChatRoom(userList, title);
                                }
                                postHelper.addUser(collection, postID, wr);
                            }
                        });

            }
        });


        postHelper.checkChangeComment(collection, postID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value,
                                @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("test", "Listen failed.", error);
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Comment comment = dc.getDocument().toObject(Comment.class);
                            postHelper.getUserNickname(comment.getWriter()).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            adapter.addItem(new Comment_sub(document.toObject(User.class).getNickName(), comment.getContent()));
                                        }
                                    }
                                    recyclerView.setAdapter(adapter);
                                }
                            });
                            break;

                    }
                }
            }
        });

        postHelper.checkChange(collection, postID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                         @Override
                                         public void onEvent(@Nullable @org.jetbrains.annotations.Nullable DocumentSnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                                             ProjectPost post = value.toObject(ProjectPost.class);
                                             title = post.getTitle();
                                             if (post.getUsers() != null) people = post.getUsers().size() + "";
                                             date = post.getEndDate();
                                             good = post.getLikes().size() + "";
                                             contents = post.getContent();
                                             writer = post.getWriter();

                                             title_text.setText(title);
                                             maxpeople.setText(post.getMax() + "");
                                             date_text.setText(date);
                                             good_text.setText(good);
                                             contents_text.setText(contents);
                                             nowpeple.setText(people);
                                         }
                                     }
                );


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

    private void loadComments() {
        // get post's comments
        postHelper.getComments(collection, postID)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Comment comment = document.toObject(Comment.class);
                                postHelper.getUserNickname(comment.getWriter()).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                adapter.addItem(new Comment_sub(document.toObject(User.class).getNickName(), comment.getContent()));
                                            }
                                        }
                                        recyclerView.setAdapter(adapter);
                                    }
                                });
                            }

                        }
                    }
                });
    }
}