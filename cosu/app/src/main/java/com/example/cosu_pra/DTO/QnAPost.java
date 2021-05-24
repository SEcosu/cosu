package com.example.cosu_pra.DTO;

import java.util.List;

public class QnAPost extends Post{

    public QnAPost(){super();}

    public QnAPost(String title, String writer_id, String content){
        super(title, writer_id,content);
    }

    public QnAPost(String title, String writer_id, String content, String category){
        super(title, writer_id,content,category);
    }


}
