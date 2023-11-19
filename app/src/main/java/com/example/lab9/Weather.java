package com.example.lab9;

public class Weather {
    public Weather(){}
    String cityName, country, weatherMain, weatherDescription, weatherIcon;
    double temperature,windSpeed;
    int pressure,humidity, windDegree;

    public Weather(String cityName, String country, double temperature, int pressure, int humidity, double windSpeed, int windDegree,
                    String weatherMain, String weatherDescription, String weatherIcon) {
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindDegree() {
        return windDegree;
    }
}
