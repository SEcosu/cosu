package com.example.cosu_pra.ConnectFB;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.cosu_pra.DTO.ChatData;
import com.example.cosu_pra.DTO.Chatroom;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

    public void makeCharRoom(List<String> userList) {

        Chatroom chm = new Chatroom(userList);

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

    public void waitMSG() {
        db.collection(CHAT).document("Ij2L74mfcmWTIjDRwsBL")
                .collection(MSG).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value,
                                @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("test", "Listen failed.", error);
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d("test", "New : " + dc.getDocument().getData());
                            break;
                        case MODIFIED:
                            Log.d("test", "Modified : " + dc.getDocument().getData());
                            break;
                        case REMOVED:
                            Log.d("test", "Removed : " + dc.getDocument().getData());
                            break;
                    }
                }
                for (QueryDocumentSnapshot doc : value) {

                    Log.d("test", doc.get("msg").toString());

                }
            }
        });

    }
}
