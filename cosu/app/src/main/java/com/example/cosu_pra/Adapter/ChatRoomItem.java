package com.example.cosu_pra.Adapter;

//TODO 채팅방리스트 임의적으로 만들었습니다.
public class ChatRoomItem {
    private int newMSG;
    private String roomName;
    private String roomID;
    private String lastTime;
    private String lastMSG;


    public String getLastMSG() {
        return lastMSG;
    }

    public String getLastTime(){
        return lastTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getNewMSG() {
        return newMSG;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setLastMSG(String lastMSG) {
        this.lastMSG = lastMSG;
    }

    public void setNewMSG(int newMSG) {
        this.newMSG = newMSG;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
