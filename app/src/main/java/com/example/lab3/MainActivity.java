package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lab3.Entities.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ParseFeedCallback {
    public RecyclerView rvNews;
    public NewsAdapter newsAdapter;
    public Button editRssButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNews = (RecyclerView) findViewById(R.id.rvNews);

        rvNews.addOnItemTouchListener(
            new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsAdapter.getItemByPosition(position).link));
                    startActivity(browserIntent);
                }
            })
        );

        newsAdapter = new NewsAdapter(new ArrayList<News>());

        rvNews.setAdapter(newsAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(this));

        ParseFeed pf = new ParseFeed(this, getApplicationContext());
        pf.execute(this);

        editRssButton = (Button) findViewById(R.id.edit_rss);
        editRssButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editRssIntent = new Intent(MainActivity.this, RssSettingsActivity.class);
                startActivity(editRssIntent);
            }
        });
    }

    @Override
    public void onSuccess(List<News> lNews) {
        newsAdapter.setNewsList(lNews);
        newsAdapter.notifyDataSetChanged();
    }
}