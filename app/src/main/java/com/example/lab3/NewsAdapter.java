package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3.Entities.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<News> lNews;

    public NewsAdapter(List<News> lNews) {
        this.lNews = lNews;
    }

    public void setNewsList(List<News> lNews) {
        this.lNews = lNews;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View newsView = inflater.inflate(R.layout.item_news, parent, false);
        ViewHolder wh = new ViewHolder(newsView);
        return wh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        News news = lNews.get(position);

        TextView newsTitle = holder.newsTitle;
        newsTitle.setText(news.title);
    }

    @Override
    public int getItemCount() {
        return lNews.size();
    }

    public News getItemByPosition(int position) {
        return this.lNews.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView newsTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitle = (TextView) itemView.findViewById(R.id.news_title);
        }
    }
}
