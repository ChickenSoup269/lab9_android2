package com.example.lab9;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Bai3 extends AppCompatActivity {
    FloatingActionButton fab3;
     ListView lvBai3;
     ArrayList<Weather> weatherList;
     CustomAdapterWeather adapter;
    SearchView svBai3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);
        addControls();
        addEvents();
    }
    public void addControls(){
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        lvBai3 = (ListView) findViewById(R.id.lvBai3);
        svBai3 = (SearchView) findViewById(R.id.svBai3);
        weatherList = new ArrayList<>();
        adapter = new CustomAdapterWeather(this, R.layout.layout_item_weather, weatherList);
        lvBai3.setAdapter(adapter);
    }
    public void addEvents(){
        fab3.setOnClickListener(v -> onBackPressed());
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("weather_16.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonData = new String(buffer, StandardCharsets.UTF_8);

            JSONArray cityArray = new JSONArray(jsonData);
            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject cityObject = cityArray.getJSONObject(i);
                JSONObject city = cityObject.getJSONObject("city");
                String cityName = city.getString("name");
                String country = city.getString("country");

                JSONObject main = cityObject.getJSONObject("main");
                double temperature = main.getDouble("temp");
                int pressure = main.getInt("pressure");
                int humidity = main.getInt("humidity");

                JSONObject wind = cityObject.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                int windDegree = wind.getInt("deg");

                JSONArray weatherArray = cityObject.getJSONArray("weather");
                JSONObject weatherObj = weatherArray.getJSONObject(0);
                String weatherMain = weatherObj.getString("main");
                String weatherDescription = weatherObj.getString("description");
                String weatherIcon = weatherObj.getString("icon");

                Weather weather = new Weather(cityName, country, temperature, pressure, humidity, windSpeed, windDegree, weatherMain, weatherDescription, weatherIcon);
                weatherList.add(weather);
            }

            adapter.notifyDataSetChanged();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        lvBai3.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;
            boolean areButtonsVisible = true;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mLastFirstVisibleItem < firstVisibleItem) {
                    if (areButtonsVisible) {
                        fab3.hide();
                        svBai3.setVisibility(View.GONE);
                        areButtonsVisible = false;
                    }
                } else if (mLastFirstVisibleItem > firstVisibleItem) {
                    if (!areButtonsVisible) {
                        fab3.show();
                        svBai3.setVisibility(View.VISIBLE);
                        areButtonsVisible = true;
                    }
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });
        svBai3.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

}
