package com.example.cosu_pra.Main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cosu_pra.Adapter.PostListAdapter;
import com.example.cosu_pra.Adapter.PostListItem;
import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.DTO.QnAPost;
import com.example.cosu_pra.DTO.StudyPost;
import com.example.cosu_pra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class SearchActivity extends AppCompatActivity {

    HelpPosting postHelper;
    ListView listView;
    PostListAdapter adapter;
    String collection, category, searchWord;
    Spinner category_spinner, sort_spinner;
    String[] cateList, categoryTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        collection = getIntent().getStringExtra("collection");
        category = getIntent().getStringExtra("category");
        searchWord = getIntent().getStringExtra("search");

        postHelper = new HelpPosting();
        listView = (ListView) findViewById(R.id.search_listview);
        category_spinner = findViewById(R.id.search_cate_spinner);


        // Spinner 지정
        if (collection.equals(HelpPosting.PROJECT)) {
            categoryTypeList = getResources().getStringArray(R.array.project_category);
        } else if (collection.equals(HelpPosting.STUDY)) {
            categoryTypeList = getResources().getStringArray(R.array.study_category);
        } else {
            categoryTypeList = getResources().getStringArray(R.array.qna_category);
        }
        categoryTypeList[0] = "전체보기";
        category_spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, categoryTypeList));

        // 카테고리를 지정한 경우 스피너를 카테고리에 맞게 선택
        int i = 0;
        if (category != null) {
            for (String str : categoryTypeList) {
                if (str.equals(category)) break;
                i++;
            }
        }

        // category 스피너 리스너,
        category_spinner.setSelection(i);
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    category = null;
                    if (searchWord != null) {
                        searchList(searchWord);
                        return;
                    }
                } else {
                    category = categoryTypeList[position];
                    cateList = new String[]{category};
                }
                showList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // item select
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("postID", adapter.getItem(position).getPostID());
                intent.putExtra("collection", collection);
                startActivity(intent);
            }
        });


    }

    /**
     * showList
     * checking collection and category and show list of valid posts
     */
    private void showList() {
        adapter = new PostListAdapter(collection);
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
                postHelper.searchPostByCategory(collection, category).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProjectPost pp = document.toObject(ProjectPost.class);
                                Log.d("test",document.getData().toString());

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
                postHelper.searchPostByCategory(collection, category).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                postHelper.searchPostByCategory(collection, category).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
    }


    /**
     * searchList
     * checking collection and search word and show list of valid posts
     *
     * @param searchWord: String that user's search
     */
    private void searchList(String searchWord) {
        adapter = new PostListAdapter(collection);
        if (collection.equals(HelpPosting.PROJECT)) { // Project
            if (category == null) { // 전체보기인 경우
                postHelper.getAllPosts(HelpPosting.PROJECT).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProjectPost pp = document.toObject(ProjectPost.class);

                                if (pp.getContent().contains(searchWord)) {
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
                            }


                        }
                        listView.setAdapter(adapter);
                    }

                });
            }
        }
        if (collection.equals(HelpPosting.STUDY)) { // Project
            if (category == null) { // 전체보기인 경우
                postHelper.getAllPosts(HelpPosting.STUDY).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProjectPost pp = document.toObject(ProjectPost.class);

                                if (pp.getContent().contains(searchWord) || pp.getTitle().contains(searchWord)) {
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
                            }


                        }
                        listView.setAdapter(adapter);
                    }

                });
            }
        }
        if (collection.equals(HelpPosting.QNA)) { // Project
            if (category == null) { // 전체보기인 경우
                postHelper.getAllPosts(HelpPosting.QNA).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProjectPost pp = document.toObject(ProjectPost.class);

                                if (pp.getContent().contains(searchWord)) {
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
                            }


                        }
                        listView.setAdapter(adapter);
                    }

                });
            }
        }
    }

}
