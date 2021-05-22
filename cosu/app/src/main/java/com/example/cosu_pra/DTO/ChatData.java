package com.example.cosu_pra.DTO;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//DTO
public class ChatData{

    private String msg;
    private String name;
    private String userID;
    private String time;

    public ChatData(){
        time = new SimpleDateFormat("yyyy/ MM / dd / HH:mm:ss").
                format(Calendar.getInstance().getTime());
    }

    public ChatData(String userID, String message){
        this();
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

    public String getTime(){
        return time;
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

    public void setTime(String time) {
        this.time = time;
    }
}

