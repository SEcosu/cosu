package com.example.cosu_pra.DTO;

import android.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyPost extends Post {
    private int max;
    private List<String> users;
    private Map<String, Double> location;

    public StudyPost() {
    }

    public StudyPost(String title, String writer_id, String contents, int max_users) {
        super(title, writer_id, contents);

        max = max_users;
        users = new ArrayList<String>();
        users.add(writer_id);
    }

    public StudyPost(String title, String writer_id, String contents, int max_users, List<String> category) {
        super(title, writer_id, contents, category);

        max = max_users;
        users = new ArrayList<String>();
        users.add(writer_id);
    }

    public StudyPost(String title, String writer_id, String contents,
                     int max_users, Location location) {
        this(title, writer_id, contents, max_users);

        this.location = new HashMap<>();
        this.location.put("altitude", location.getAltitude());
        this.location.put("latitude", location.getLatitude());
    }

    public StudyPost(String title, String writer, String contents,
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

    public Map<String, Double> getLocation() {
        return location;
    }

    // setter
    public void setUsers(List<String> users) {
        this.users = users;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setLocation(Map<String, Double> location) {
        this.location = location;
    }
}
