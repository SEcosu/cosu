package com.example.cosu_pra.ConnectFB;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.cosu_pra.DTO.ChatData;
import com.example.cosu_pra.DTO.Chatroom;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;

public class HelpChatting {
    private FirebaseFirestore db;

    public final static String CHAT = "ChatRoom";
    public final static String MSG = "Messages";

    public HelpChatting() {
        db = FirebaseFirestore.getInstance();
    }

    public void makeChatRoom(List<String> userList, String roomName) {


        Chatroom chm = new Chatroom(userList, roomName);

        db.collection(CHAT).add(chm);
    }

    public void addChat(String roomID, String userID, String msg) {
        ChatData chat = new ChatData(userID, msg);

        db.collection(CHAT).document(roomID)
                .collection(MSG).add(chat);
    }

    public void addChat(String roomID, ChatData chat) {
        db.collection(CHAT).document(roomID)
                .collection(MSG).add(chat);
    }

    public CollectionReference waitMSG(String roomID) {
        return db.collection(CHAT).document(roomID)
                .collection(MSG);


    }

    public Task<QuerySnapshot> getChatRooms(String userID) {
        return db.collection(CHAT).whereArrayContains("userList", userID).get();
    }

    public Task<QuerySnapshot> getMessages(String roomID) {
        return db.collection(CHAT).document(roomID)
                .collection(MSG).orderBy("data", Query.Direction.DESCENDING).get();
    }

}
