package com.example.cosu_pra;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cosu_pra.Adapter.MypostItemAdapter;
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

        db.collection("Projects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<MyPostItem> myPostItemList = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getId().equals(_userID)) continue;
                        MyPostItem myPostItem = document.toObject(MyPostItem.class);
                        myPostItemList.add(myPostItem);
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
