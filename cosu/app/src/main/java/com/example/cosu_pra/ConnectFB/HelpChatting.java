package com.example.cosu_pra.ConnectFB;

import com.example.cosu_pra.DTO.ChatData;
import com.example.cosu_pra.DTO.Chatroom;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;

/**
 * HelpChatting
 * this class consist of chat function
 */
public class HelpChatting {
    private FirebaseFirestore db;

    public final static String CHAT = "ChatRoom";
    public final static String MSG = "Messages";

    public HelpChatting() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * makeChatRoom
     * make chat room with user list and room name
     * this method call when post's members are fulled
     *
     * @param userList: post's members
     * @param roomName: post's room name
     */
    public void makeChatRoom(List<String> userList, String roomName) {


        Chatroom chm = new Chatroom(userList, roomName);

        db.collection(CHAT).add(chm);
    }

    /**
     * addChat
     * send chat message
     * @param roomID: chat room id
     * @param userID: user id
     * @param msg : chatting message
     */
    public void addChat(String roomID, String userID, String msg) {
        ChatData chat = new ChatData(userID, msg);

        db.collection(CHAT).document(roomID)
                .collection(MSG).add(chat);
        db.collection(CHAT).document(roomID).update("lastMSG",chat.getMsg());
        db.collection(CHAT).document(roomID).update("lastTime",chat.getTime());
    }

    /**
     * addChat
     * send chat message by ChatData object
     * @param roomID: chat room id
     * @param chat: ChatData
     */
    public void addChat(String roomID, ChatData chat) {
        db.collection(CHAT).document(roomID)
                .collection(MSG).add(chat);
        db.collection(CHAT).document(roomID).update("lastMSG",chat.getMsg());
        db.collection(CHAT).document(roomID).update("lastTime",chat.getTime());
    }

    /**
     * waitMSG
     * when new message is updated, event listener is called
     * @param roomID : chat room id
     * @return : {@link CollectionReference}
     */
    public CollectionReference waitMSG(String roomID) {
        return db.collection(CHAT).document(roomID)
                .collection(MSG);


    }

    /**
     * getChatRooms
     * get user's chat rooms list
     * @param userID: user id
     * @return: Query
     */
    public Query getChatRooms(String userID) {
        return db.collection(CHAT).whereArrayContains("userList", userID);
    }

    /**
     * getMessages
     * get all messages in chat room
     * @param roomID: chat room id
     * @return: Task<QuerySnapshot>
     */
    public Task<QuerySnapshot> getMessages(String roomID) {
        return db.collection(CHAT).document(roomID)
                .collection(MSG).orderBy("time", Query.Direction.DESCENDING).get();
    }

}
