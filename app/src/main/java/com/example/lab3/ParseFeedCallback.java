package com.example.lab3;

import com.example.lab3.Entities.News;

import java.util.List;

public interface ParseFeedCallback {
    void onSuccess(List<News> lNews);
}
