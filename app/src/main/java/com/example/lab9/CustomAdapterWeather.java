package com.example.lab9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab9.Weather;

import java.util.ArrayList;

public class CustomAdapterWeather extends ArrayAdapter<Weather> {

    private Context mContext;
    private int mResource;

    public CustomAdapterWeather(Context context, int resource, ArrayList<Weather> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }

        Weather currentWeather = getItem(position);

        TextView cityNameTextView = convertView.findViewById(R.id.cityNameTextView);
        TextView temperatureTextView = convertView.findViewById(R.id.temperatureTextView);
        TextView countryTextView = convertView.findViewById(R.id.countryTextView);
        TextView pressureTextView = convertView.findViewById(R.id.pressureTextView);
        TextView humidityTextView = convertView.findViewById(R.id.humidityTextView);
        TextView windTextView = convertView.findViewById(R.id.windTextView);
        TextView weatherMainTextView = convertView.findViewById(R.id.weatherMainTextView);
        TextView weatherDescriptionTextView = convertView.findViewById(R.id.weatherDescriptionTextView);
        ImageView weatherIconImageView = convertView.findViewById(R.id.imgViewIconWeather);

        if (currentWeather != null) {
            cityNameTextView.setText("City: " + currentWeather.getCityName());
            temperatureTextView.setText(Html.fromHtml("<b>Temperature:</b> " + currentWeather.getTemperature()));
            countryTextView.setText(Html.fromHtml("<b>Country:</b> " + currentWeather.getCountry()));
            pressureTextView.setText(Html.fromHtml("<b>Pressure:</b> " + currentWeather.getPressure()));
            humidityTextView.setText(Html.fromHtml("<b>RH:</b> " + currentWeather.getHumidity()));
            windTextView.setText(Html.fromHtml("<b>WS:</b> " + currentWeather.getWindSpeed()));
            weatherMainTextView.setText(Html.fromHtml("<b>Weather Main:</b> " + currentWeather.getWeatherMain()));
            weatherDescriptionTextView.setText(Html.fromHtml("<b>Weather Description:</b> " + currentWeather.getWeatherDescription()));
            String weatherIcon = currentWeather.getWeatherIcon();
            int iconResource = getIconResourceFromCode(weatherIcon);
            if (iconResource != -1) {
                weatherIconImageView.setImageResource(iconResource);
            } else {
                // Hình ảnh mặc định khi không có icon thời tiết
                weatherIconImageView.setImageResource(R.drawable.non);
            }
        }

        return convertView;
    }
    private int getIconResourceFromCode(String weatherIconCode) {
        // Xác định ứng với mã icon thời tiết, đặt hình ảnh tương ứng và trả về ID của hình ảnh
        switch (weatherIconCode) {
            case "01d":
                return R.drawable.one_d;
            case "02d":
                return R.drawable.two_d;
            case "010d":
                return R.drawable.ten_d;
            case "03d":
                return R.drawable.three_d;
            case "09d":
                return R.drawable.nine_d;
            case "04d":
                return R.drawable.four_d;
            default:
                return -1; // Trả về -1 nếu không tìm thấy icon
        }
    }
}
