package com.example.cosu_pra.DTO;

import java.util.ArrayList;
import java.util.List;

public class Chatroom {
    String roomName;
    String lastTime;
    String lastMSG;
    List<String> userList;

    Chatroom() {
        userList = new ArrayList<>();
    }


    public Chatroom(List<String> userList) {
        this.userList = userList;
    }

    // getter
    public String getRoomName() {
        return roomName;
    }

    public List<String> getUserList() {
        return userList;
    }

    public String getLastMSG() {
        return lastMSG;
    }

    public String getLastTime() {
        return lastTime;
    }

    // setter
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public void setLastMSG(String lastMSG) {
        this.lastMSG = lastMSG;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
