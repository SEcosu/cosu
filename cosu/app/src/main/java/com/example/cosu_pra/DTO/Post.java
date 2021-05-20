package com.example.cosu_pra.DTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Post {
    private String title;
    private String writer;
    private String date;
    private String startDate;
    private String endDate;
    private String content;
    private List<String> category;
    private List<String> likes;
    private int comment;
    private int report;

    public Post() {
        date = new SimpleDateFormat("yyyy/ MM / dd / HH:mm:ss").
                format(Calendar.getInstance().getTime());
        category = new ArrayList<String>();
        likes = new ArrayList<String>();
        report = 0;
        comment = 0;
    }

    public Post(String writer, String content) {
        this();
        this.writer = writer;
        this.content = content;
    }

    public Post(String title, String writer, String content) {
        this(writer, content);
        this.title = title;
    }

    public Post(String title, String writer, String content,List<String> category) {
        this(writer, content);
        this.title = title;
        this.category = category;
    }

    public Post(String title, String writer, String content,List<String> category, String startDate, String endDate) {
        this(writer, content);
        this.title = title;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }



    // getter
    public String getWriter() {
        return writer;
    }

    public String getDate() {
        return date;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getCategory() {
        return category;
    }

    public List<String> getLikes() {
        return likes;
    }

    public int getReport(){ return report;}

    public int getComment() {
        return comment;
    }



    // setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public void setCategory(List<String> category){
        this.category = category;
    }

    public void setReport(int report){
        this.report = report;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
