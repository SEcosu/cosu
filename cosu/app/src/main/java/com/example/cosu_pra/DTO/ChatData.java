package com.example.cosu_pra.DTO;
import java.io.Serializable;

//DTO
public class ChatData{

    private String msg;
    private String name;
    private String userID;

    public ChatData(){

    }

    public ChatData(String userID, String message){
        this.userID = userID;
        this.msg = message;
    }

    public ChatData(String name, String userID, String message){
        this(userID,message);
        this.name = name;
    }

    //getter
    public String getMsg() {
        return msg;
    }

    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }

    //setter
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}

