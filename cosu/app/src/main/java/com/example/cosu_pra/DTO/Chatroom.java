package com.example.cosu_pra.DTO;

import java.util.ArrayList;
import java.util.List;

public class Chatroom {
    List<String> userList;

    Chatroom() {
        userList = new ArrayList<>();
    }

    public Chatroom(List<String> userList) {
        this.userList = userList;
    }


    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }
}
