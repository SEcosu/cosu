package com.example.cosu_pra.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosu_pra.Adapter.ChatroomlistAdapter;
import com.example.cosu_pra.DTO.Chatroom;
import com.example.cosu_pra.R;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private ChatroomlistAdapter adapter;
    //chating room list 데이터
    private ArrayList<Chatroom>chatroomlist = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new ChatroomlistAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return inflater.inflate(R.layout.fragment_chat, container, false);
        //recyclerView = (RecyclerView)rootView.findViewById(R.id.chatroomList);


    }



}