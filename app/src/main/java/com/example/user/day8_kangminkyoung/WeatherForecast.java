package com.example.user.day8_kangminkyoung;

/**
 * Created by 20150092 on 2015-12-21.
 */
public class WeatherForecast {
    private String weather;
    private String icon;

    public WeatherForecast() {
        // TODO Auto-generated constructor stub
    }

    public WeatherForecast(String weather, String icon) {
        super();
        this.weather = weather;
        this.icon = icon;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
