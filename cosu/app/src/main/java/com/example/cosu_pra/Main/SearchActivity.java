package com.example.cosu_pra.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.DTO.QnAPost;
import com.example.cosu_pra.DTO.StudyPost;
import com.example.cosu_pra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class SearchActivity extends AppCompatActivity {

    HelpPosting postHelper;
    ListView listView;
    PostListAdapter adapter;
    PostListItem studyItem;
    Map<String, ProjectPost> projectPosts = new HashMap<>();
    String collection, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        collection = getIntent().getStringExtra("collection");
        category = getIntent().getStringExtra("category");
        String[] cateList = new String[]{category};

        postHelper = new HelpPosting();
        listView = (ListView) findViewById(R.id.search_listview);

        adapter = new PostListAdapter(collection);
        listView.setAdapter(adapter);

        if (collection.equals(HelpPosting.PROJECT)) { // Project
            if (category == null) { // 전체보기인 경우
                postHelper.getAllPosts(HelpPosting.PROJECT).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProjectPost pp = document.toObject(ProjectPost.class);

                                PostListItem item = new PostListItem();
                                item.setComment(pp.getComment() + "");
                                item.setDate(pp.getDate());
                                item.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.android)); //TODO: 수정필
                                item.setGood(pp.getLikes().size() + "");
                                item.setPeople(pp.getUsers().size() + ""); // TODO: max 인지 현재인지 확인필
                                item.setPostID(document.getId());
                                item.setTitle(pp.getTitle());

                                adapter.addItem(item);
                            }
                            listView.setAdapter(adapter);
                        }
                    }
                });
            } else { // 카테고리가 있는 경우
                postHelper.searchPostByCategory(collection, cateList).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProjectPost pp = document.toObject(ProjectPost.class);

                                PostListItem item = new PostListItem();
                                item.setComment(pp.getComment() + "");
                                item.setDate(pp.getDate());
                                item.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.android)); //TODO: 수정필
                                item.setGood(pp.getLikes().size() + "");
                                item.setPeople(pp.getUsers().size() + ""); // TODO: max 인지 현재인지 확인필
                                item.setPostID(document.getId());
                                item.setTitle(pp.getTitle());

                                adapter.addItem(item);
                            }
                            listView.setAdapter(adapter);
                        }
                    }
                });
            }
        } else if (collection.equals(HelpPosting.STUDY)) { // Study
            if (category == null) { // 전체보기인 경우
                postHelper.getAllPosts(collection).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                StudyPost pp = document.toObject(StudyPost.class);

                                PostListItem item = new PostListItem();
                                item.setComment(pp.getComment() + "");
                                item.setDate(pp.getDate());
                                item.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.android)); //TODO: 수정필
                                item.setGood(pp.getLikes().size() + "");
                                item.setPeople(pp.getUsers().size() + ""); // TODO: max 인지 현재인지 확인필
                                item.setPostID(document.getId());
                                item.setTitle(pp.getTitle());

                                adapter.addItem(item);
                            }
                            listView.setAdapter(adapter);
                        }
                    }
                });
            } else { // 카테고리가 있는 경우
                postHelper.searchPostByCategory(collection, cateList).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                StudyPost pp = document.toObject(StudyPost.class);

                                PostListItem item = new PostListItem();
                                item.setComment(pp.getComment() + "");
                                item.setDate(pp.getDate());
                                item.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.android)); //TODO: 수정필
                                item.setGood(pp.getLikes().size() + "");
                                item.setPeople(pp.getUsers().size() + ""); // TODO: max 인지 현재인지 확인필
                                item.setPostID(document.getId());
                                item.setTitle(pp.getTitle());

                                adapter.addItem(item);
                            }
                            listView.setAdapter(adapter);
                        }
                    }
                });
            }
        } else if (collection.equals(HelpPosting.QNA)) { // QnA
            if (category == null) { // 전체보기인 경우
                postHelper.getAllPosts(collection).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                QnAPost pp = document.toObject(QnAPost.class);

                                PostListItem item = new PostListItem();
                                item.setComment(pp.getComment() + "");
                                item.setDate(pp.getDate());
                                item.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.android)); //TODO: 수정필
                                item.setGood(pp.getLikes().size() + "");
                                item.setPostID(document.getId());
                                item.setTitle(pp.getTitle());

                                adapter.addItem(item);
                            }
                            listView.setAdapter(adapter);
                        }
                    }
                });
            } else { // 카테고리가 있는 경우
                postHelper.searchPostByCategory(collection, cateList).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                QnAPost pp = document.toObject(QnAPost.class);

                                PostListItem item = new PostListItem();
                                item.setComment(pp.getComment() + "");
                                item.setDate(pp.getDate());
                                item.setImage(ContextCompat.getDrawable(getApplicationContext(), R.drawable.android)); //TODO: 수정필
                                item.setGood(pp.getLikes().size() + "");
                                item.setPostID(document.getId());
                                item.setTitle(pp.getTitle());

                                adapter.addItem(item);
                            }
                            listView.setAdapter(adapter);
                        }
                    }
                });
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("postID", adapter.getItem(position).getPostID());
                intent.putExtra("collection", collection);
                startActivity(intent);
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
