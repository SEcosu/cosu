package com.example.cosu_pra.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosu_pra.DTO.Chatroom;
import com.example.cosu_pra.R;

import java.util.ArrayList;


public class Fragment4 extends Fragment {
    private RecyclerView recyclerView;
    private ChatroomlistAdapter adapter;
    //chating room list 데이터
    private ArrayList<Chatroom> chatroomlist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v =  inflater.inflate(R.layout.fragment4, container, false);
        adapter = new ChatroomlistAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView = (RecyclerView)v.findViewById(R.id.chatroomList);
        return v;
    }

}
