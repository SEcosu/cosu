package com.example.cosu_pra.Main;

public class Comment_sub {
    String Comment;
    String CommentWriter;

    public Comment_sub(String comment_name, String comment) {
        Comment = comment;
        CommentWriter = comment_name;
    }
    public String getCommentWriter() {
        return CommentWriter;
    }

    public void setCommentWriter(String comment_name) {
        CommentWriter = comment_name;
    }
    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }


}