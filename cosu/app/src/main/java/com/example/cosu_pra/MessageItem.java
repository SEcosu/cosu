package com.example.cosu_pra;

public class MessageItem {
    private String id;
    private String content;
    private String time;

    public String getContent() {
        return content;
    }

    public String getName() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MessageItem(String id, String content, String time){
        this.time = time;
        this.content = content;
        this.id = id;
    }
}
