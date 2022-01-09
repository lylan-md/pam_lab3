package com.example.lab3.Entities;

import java.util.ArrayList;

public class Feed {
    public String title;
    public String link;
    public String id;
    public String updated;
    public ArrayList<News> news;

    @Override
    public String toString() {
        return "Feed{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", id='" + id + '\'' +
                ", updated='" + updated + '\'' +
                ", news=" + news +
                '}';
    }
}
