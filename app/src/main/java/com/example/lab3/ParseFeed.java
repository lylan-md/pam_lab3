package com.example.lab3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lab3.Entities.Feed;
import com.example.lab3.Entities.News;
import com.example.lab3.Entities.NewsLink;
import com.example.lab3.Entities.Rss;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParseFeed extends AsyncTask {
    private List<News> lNews = new ArrayList<News>();

    private final WeakReference<ParseFeedCallback> listener;
    private final Context context;
    public ParseFeed(ParseFeedCallback callback, Context context) {
        this.listener = new WeakReference<>(callback);
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        SharedPreferences sp = this.context.getSharedPreferences("com.example.lab3", AppCompatActivity.MODE_PRIVATE);
        String jsonRssList = sp.getString("rss_list", null);

        ArrayList<String> rssList = null;

        if (jsonRssList != null)
        {
            Gson gson = new Gson();
            rssList = gson.fromJson(jsonRssList, new TypeToken<List<String>>(){}.getType());
        }
        else
        {
            rssList = new ArrayList<>();
        }

        for (String rssLink: rssList) {
            URL url;

            try {
                url = new URL(rssLink);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            XStream xs = new XStream();
            xs.alias("rss", Rss.class);
            xs.alias("item", News.class);
            xs.addImplicitCollection(Feed.class, "news", News.class);

            xs.ignoreUnknownElements();

            try {
                Rss rss = (Rss) xs.fromXML(url);
                lNews.addAll(rss.channel.news);
            } catch (Exception e) { }
        }

        Collections.sort(lNews);

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        listener.get().onSuccess(this.lNews);
    }
}
