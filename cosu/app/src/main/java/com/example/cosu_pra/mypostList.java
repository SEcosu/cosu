package com.example.cosu_pra;

public class mypostList{
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
    mypostList(String content, String category){
        this.content = content;
        this.category = category;
    }
}
