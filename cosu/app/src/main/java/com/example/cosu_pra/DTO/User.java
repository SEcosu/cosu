package com.example.cosu_pra.DTO;

public class User {
//user data
    private String email;
    private String pwd;
    private String realName;
    private String nickName;

    public User() {
    }

    public User(String email, String realName, String nickName) {
        this.email = email;

        this.realName = realName;
        this.nickName = nickName;
    }

    // getter
    public String getEmail() {
        return email;
    }


    public String getRealName() {
        return realName;
    }

    public String getNickName() {
        return nickName;
    }

    //setter
    public void setEmail(String email) {
        this.email = email;
    }


    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}