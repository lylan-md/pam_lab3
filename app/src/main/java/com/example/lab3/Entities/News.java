package com.example.lab3.Entities;

public class News implements Comparable<News> {
    public String title;
    public String link;
    public String updated;
    public String author;
    public String id;
    public String summary;
    public String name;

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", updated='" + updated + '\'' +
                ", author='" + author + '\'' +
                ", id='" + id + '\'' +
                ", summary='" + summary + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(News news) {
        return title.compareTo(news.title);
    }
}