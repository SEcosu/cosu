package com.example.cosu_pra.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cosu_pra.Adapter.ChatRoomAdatper;
import com.example.cosu_pra.ChattingActivity;
import com.example.cosu_pra.Adapter.ChatRoomItem;
import com.example.cosu_pra.ConnectFB.HelpChatting;
import com.example.cosu_pra.DTO.Chatroom;
import com.example.cosu_pra.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
//참고 : https://itqna.net/questions/87594/how-create-custom-listview-inside-fragment

public class Fragment4 extends Fragment {
    HelpChatting chatHelper;
    SharedPreferences sh_Pref;
    ChatRoomAdatper adatper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment4, container, false);
        ListView chatRoomView = (ListView) v.findViewById(R.id.mainlist);
        List<ChatRoomItem> listViewItemList = new ArrayList<>();
        adatper = new ChatRoomAdatper(getActivity(), listViewItemList);

        sh_Pref = getActivity().getSharedPreferences("Login Credentials ", Context.MODE_PRIVATE);
        String userID = sh_Pref.getString("Email", "");

        chatHelper = new HelpChatting();
        chatHelper.getChatRooms(userID).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Chatroom room = document.toObject(Chatroom.class);
                        ChatRoomItem item = new ChatRoomItem();

                        item.setLastMSG(room.getLastMSG());
                        item.setRoomID(document.getId());
                        item.setLastTime(room.getLastTime());
                        item.setRoomName(room.getRoomName());
                        item.setNewMSG(3); //TODO: 이거 어케하지?

                        adatper.addItem(item);
                    }
                    chatRoomView.setAdapter(adatper);
                }
            }
        });

        return v;
    }

    //TODO 단톡방 클릭 이벤트
    public void onListItemClick(ListView l, View v, int position, long id) {
        String strText = (String) l.getItemAtPosition(position);
        Log.d("listview: ", position + ": " + strText);
        Intent intent = new Intent(getActivity(), ChattingActivity.class);
        intent.putExtra("roomID", adatper.getItem(position).getRoomID());
        startActivity(intent);
    }
    //리스트뷰 클릭이벤트



}


