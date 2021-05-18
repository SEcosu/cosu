package com.example.cosu_pra;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cosu_pra.ConnectFB.HelpPosting;
import com.example.cosu_pra.DTO.ProjectPost;
import com.example.cosu_pra.fragment.ChatFragment;
import com.example.cosu_pra.fragment.MyinfoFramgnet;
import com.example.cosu_pra.fragment.ProjectFragment;
import com.example.cosu_pra.fragment.QnAFragment;
import com.example.cosu_pra.fragment.StudyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

public class FragmentActivity extends AppCompatActivity {
    HelpPosting helpPost;
    Map<String, ProjectPost> posts;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ProjectFragment projectFragment;
    private StudyFragment studyFragment;
    private QnAFragment qaFragment;
    private ChatFragment chatFragment;
    private MyinfoFramgnet myinfoFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);


        projectFragment = new ProjectFragment();
        studyFragment = new StudyFragment();
        qaFragment = new QnAFragment();
        chatFragment = new ChatFragment();
        myinfoFragment = new MyinfoFramgnet();
       BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);
        //첫화면 -->프로젝트 모집 화면
        getSupportFragmentManager().beginTransaction().add(R.id.nav_project, new ProjectFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case (R.id.nav_project):
                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_project, new ProjectFragment()).commit();
                                break;

                            case (R.id.nav_study):
                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_study, new StudyFragment()).commit();
                                break;

                            case (R.id.nav_qna):
                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_qna, new QnAFragment()).commit();
                                break;

                            case (R.id.nav_chat):
                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_chat, new ChatFragment()).commit();
                                break;

                            case (R.id.nav_mypage):
                                getSupportFragmentManager().beginTransaction().replace(R.id.nav_mypage, new MyinfoFramgnet()).commit();
                                break;

                        }
                        return true;
                    }


                });

    }


}


