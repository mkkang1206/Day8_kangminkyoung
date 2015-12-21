package com.example.user.day8_kangminkyoung;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by 20150092 on 2015-12-21.
 */
public class WeatherAdapter extends ArrayAdapter<WeatherForecast>{
    ArrayList<WeatherForecast> weatherList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public WeatherAdapter(Context context, int resource, ArrayList<WeatherForecast> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        weatherList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.textView = (TextView) v.findViewById(R.id.weather);
            holder.imageview = (ImageView) v.findViewById(R.id.icon);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.textView.setText(weatherList.get(position).getWeather());
        new DownloadImageTask(holder.imageview).execute(weatherList.get(position).getIcon());
        return v;

    }

    static class ViewHolder {
        public TextView textView;
        public ImageView imageview;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL("http://openweathermap.org/img/w/"+urldisplay+".png").openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }
}
