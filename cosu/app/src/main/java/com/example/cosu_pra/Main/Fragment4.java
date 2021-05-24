package com.example.cosu_pra.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    ListView chatRoomView;
    ArrayList<ChatRoomItem> chatRoomList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment4, container, false);
        chatRoomList = new ArrayList<ChatRoomItem>();

        chatRoomView = (ListView) v.findViewById(R.id.mainlist);
        adatper = new ChatRoomAdatper();
        chatRoomView.setAdapter(adatper);

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
                        //item.setNewMSG(3); //TODO: 이거 어케하지?

                        adatper.addItem(item);
                    }
                    chatRoomView.setAdapter(adatper);
                }
            }
        });

        chatRoomView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChattingActivity.class);
                intent.putExtra("roomID", adatper.getItem(position).getRoomID());
                startActivity(intent);
            }
        });

        return v;
    }


}







