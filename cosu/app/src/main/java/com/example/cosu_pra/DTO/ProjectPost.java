package com.example.cosu_pra.DTO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ProjectPost extends Post {
    private int max;
    private List<String> users;

    public ProjectPost() {
        super();
    }

    public ProjectPost(String title, String writer, String contents, int max_users) {
        super(title, writer, contents);
        max = max_users;
        users = new ArrayList<String>();
        users.add(writer);
    }

    public ProjectPost(String title, String writer, String contents,
                       int max_users, List<String> category) {
        super(title, writer, contents,category);
        max = max_users;
        users = new ArrayList<String>();
        users.add(writer);
    }

    public ProjectPost(String title, String writer, String contents,
                       int max_users, List<String> category, String startDate, String endDate) {
        super(title, writer, contents,category,startDate,endDate);
        max = max_users;
        users = new ArrayList<String>();
        users.add(writer);
    }

    // getter
    public int getMax() {
        return max;
    }

    public List<String> getUsers() {
        return users;
    }


    // setter
    public void setUsers(List<String> users) {
        this.users = users;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
