package com.example.cosu_pra.Adapter;

public class ReportListItem {
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

    public ReportListItem(String content, String writer){
        this.content = content;
        this.writer= writer;
    }
}
