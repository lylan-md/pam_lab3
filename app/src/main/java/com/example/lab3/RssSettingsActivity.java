package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssSettingsActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private ArrayList<String> listRssLinks;
    private ListView viewRssLinks;
    private ArrayAdapter viewRssLinksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_settings);

        this.sp = getApplicationContext().getSharedPreferences("com.example.lab3", AppCompatActivity.MODE_PRIVATE);
        String jsonRssList = sp.getString("rss_list", null);

        if (jsonRssList != null) {
            Gson gson = new Gson();
            listRssLinks = gson.fromJson(jsonRssList, new TypeToken<List<String>>(){}.getType());
        } else {
            listRssLinks = new ArrayList<String>();
        }

        this.viewRssLinks = findViewById(R.id.rssLinksView);
        this.viewRssLinksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listRssLinks);
        this.viewRssLinks.setAdapter(this.viewRssLinksAdapter);

        Button addRssButton = (Button) findViewById(R.id.addRssLink);
        addRssButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = (EditText) findViewById(R.id.editRssLink);
                String rssLinkToAdd = et.getText().toString();

                try {
                    new URL(rssLinkToAdd);
                } catch (MalformedURLException e) {
                    return;
                }

                listRssLinks.add(rssLinkToAdd);
                viewRssLinksAdapter.notifyDataSetChanged();
            }
        });

        Button buttonClearRssList = (Button) findViewById(R.id.buttonClearRssList);
        buttonClearRssList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listRssLinks.clear();
                viewRssLinksAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.sp.edit().putString("rss_list", new Gson().toJson(this.listRssLinks)).apply();
        startActivity(new Intent(RssSettingsActivity.this, MainActivity.class));
    }
}