package com.example.cosu_pra;

public class MyPostItem {
    public String collection;
    public String postID;
    private String content;
    private String category;

    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public MyPostItem(String content, String category){
        this.content = content;
        this.category = category;
    }
}
