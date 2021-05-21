package com.example.cosu_pra.DTO;

//TODO 채팅방리스트 임의적으로 만들었습니다.
public class ListViewItem {
    private String chatName;
    private String chatContent;
    private String chatTime;

    public void setName(String chatname) {
        chatName = chatname;
    }

    public void setContent(String chatcontent) {
        chatContent = chatcontent;
    }

    public void setTime(String chattime) {
        chatTime = chattime;
    }

    public String getName() {
        return this.chatName;
    }

    public String getContent() {
        return this.chatContent;
    }

    public String getTime() {
        return this.chatTime;
    }

}
