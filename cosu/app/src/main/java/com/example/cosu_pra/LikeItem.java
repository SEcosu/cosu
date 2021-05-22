package com.example.cosu_pra;

public class LikeItem{
    private String content;
    private String writer;

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
    LikeItem(String content, String writer){
        this.content = content;
        this.writer = writer;
    }
}
