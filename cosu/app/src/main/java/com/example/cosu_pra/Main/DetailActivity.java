package com.example.cosu_pra.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.Comment;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class DetailActivity extends AppCompatActivity {

    ImageView image;
    TextView title_text,people_text,date_text,good_text,contents_text;
    String postID, collection,title,people,date,good,contents;
    HelpPosting postHelper;
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

        // get post
        postHelper.getPost(collection, postID)
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ProjectPost post = documentSnapshot.toObject(ProjectPost.class); // post는 원하는 post 객체를 사용하세요
                        title = post.getTitle();
                        people = post.getUsers().size()+"";
                        date = post.getDate();
                        good = post.getLikes().size()+"";
                        contents = post.getContent();

                        title_text.setText(title);
                        people_text.setText(people);
                        date_text.setText(date);
                        good_text.setText(good);
                        contents_text.setText(contents);
                    }
                });

        // get post's comments
        postHelper.getComments(collection, postID)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Map<String, Comment> comments = new HashMap<String, Comment>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                comments.put(document.getId(), document.toObject(Comment.class)); // 맵으로 넣는 방법
                            }
                            for (Comment cmt : comments.values()) {
                                Log.d("test", cmt.getContent());
                                Log.d("test", cmt.getWriter());
                            }
                        }
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