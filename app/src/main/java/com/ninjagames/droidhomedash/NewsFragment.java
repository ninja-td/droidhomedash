package com.ninjagames.droidhomedash;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


public class NewsFragment extends Fragment {
    private static final String NEWS_URL = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=d8ab8daeecf046df9a6e26938ed1f748";

    TextView newsText;

    private JSONArray newsList;
    private int currentNewsIndex = 0;


    UrlFetchHelper.Callback newsUrlCallback = new UrlFetchHelper.Callback() {

        @Override
        public void onComplete(String result) {
            updateWeather(result);

        }
    };
    Runnable newsUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            new UrlFetchHelper(newsUrlCallback).execute(NEWS_URL);
            newsUpdateHandler.postDelayed(newsUpdateRunnable, 60000 * 60 * 3);
        }
    };
    Handler newsUpdateHandler = new Handler(Looper.getMainLooper());

    Runnable newsTextCycleRunnable = new Runnable() {
        @Override
        public void run() {
            cycleNews();
            newsUpdateHandler.postDelayed(newsTextCycleRunnable, 10000);
        }
    };
    Handler newsCycleHandler = new Handler(Looper.getMainLooper());


    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        newsText = rootView.findViewById(R.id.news_text);
        newsUpdateRunnable.run();
        return rootView;
    }


    private void updateWeather(String newsJsonText) {
        try {
            JSONObject newsObj = new JSONObject(newsJsonText);
            newsList = newsObj.getJSONArray("articles");
            /*
            String mainText = weatherObj.getJSONArray("weather").getJSONObject(0).getString("main");
            int mainTemp = mainStats.getInt("temp");
            int maxTemp = mainStats.getInt("temp_max");
            int minTemp = mainStats.getInt("temp_min");
            */
            newsTextCycleRunnable.run();
        } catch (Exception e) {
            Log.e("NewsFragment", e.toString());
        }

    }

    private void cycleNews() {
        try {
            JSONObject currentNews = newsList.getJSONObject(currentNewsIndex);
            newsText.setText(currentNews.getString("title"));

            currentNewsIndex++;
            if (currentNewsIndex >= newsList.length()) {
                currentNewsIndex = 0;
            }
        } catch (Exception e) {
            Log.e("NewsFragment", e.toString());
        }


    }
}
