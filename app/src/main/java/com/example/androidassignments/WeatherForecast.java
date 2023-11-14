package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ProgressBar progressBar = findViewById(R.id.newProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        Spinner citySpinner = findViewById(R.id.newCitySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.canadian_cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCity = adapterView.getItemAtPosition(i).toString();
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + selectedCity + ",CA&APPID=b0705ad7e3eb1bc89f7f34a9737c361e&mode=xml&units=metric";

                new ForecastQuery().execute(url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String minTemperature;
        private String maxTemperature;
        private String currentTemperature;
        private Bitmap weatherBitmap;
        @Override
        protected String doInBackground(String... strings) {
            String weatherData = "";
            String urlString = strings[0];

            try {
                Log.d("WeatherApp", "Connecting to URL: " + urlString);
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    int responseCode = urlConnection.getResponseCode();
                    String responseMessage = urlConnection.getResponseMessage();
                    Log.d("WeatherApp", "Response Code: " + responseCode + ", Response Message: " + responseMessage);
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        weatherData = parseXml(in);
                    } else {
                        Log.e("WeatherApp", "Non-OK response code: " + responseCode);
                    }
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherData;
        }

        private String parseXml(InputStream in) {
            String result = "";
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);

                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name = parser.getName();
                    if (eventType == XmlPullParser.START_TAG) {
                        if (name != null && name.equals("temperature")) {
                            currentTemperature = parser.getAttributeValue(null, "value");
                            minTemperature = parser.getAttributeValue(null, "min");
                            maxTemperature = parser.getAttributeValue(null, "max");
                            publishProgress(25, 50, 75);
                        } else if (name != null && name.equals("weather")) {
                            weatherBitmap = getBitmapFromURL(parser.getAttributeValue(null, "icon"));
                        }
                    }

                    eventType = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        private Bitmap getBitmapFromURL(String iconName) {
            try {
                String imageURL = "https://openweathermap.org/img/w/" + iconName + ".png";
                if (fileExistance(iconName + ".png")) {
                    Log.i("WeatherApp", "Local file found for: " + iconName);
                    FileInputStream fis = openFileInput(iconName + ".png");
                    return BitmapFactory.decodeStream(fis);
                } else {
                    Log.i("WeatherApp", "Downloading image for: " + iconName);
                    Bitmap image = HTTPUtilsPack.getImage(imageURL);
                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    publishProgress(100);
                    return image;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            ProgressBar progressBar = findViewById(R.id.newProgressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            TextView currentTemperatureTextView = findViewById(R.id.newCurrentTemperatureTextView);
            TextView minTemperatureTextView = findViewById(R.id.newMinTemperatureTextView);
            TextView maxTemperatureTextView = findViewById(R.id.newMaxTemperatureTextView);
            ImageView weatherImageView = findViewById(R.id.newWeatherImageView);
            ProgressBar progressBar = findViewById(R.id.newProgressBar);
            String ct;
            String mint;
            String maxt;

            if(currentTemperature.substring(0,currentTemperature.indexOf('.')).equals("-0")){
                ct="0";
            }else{
                ct=currentTemperature.substring(0,currentTemperature.indexOf('.'));
            }

            if(minTemperature.substring(0,minTemperature.indexOf('.')).equals("-0")){
                mint="0";
            }else{
                mint=minTemperature.substring(0,minTemperature.indexOf('.'));
            }

            if(maxTemperature.substring(0,maxTemperature.indexOf('.')).equals("-0")){
                maxt="0";
            }else{
                maxt=maxTemperature.substring(0,maxTemperature.indexOf('.'));
            }

            currentTemperatureTextView.setText("Current Temperature: " +ct+ " °C");
            minTemperatureTextView.setText("Min Temperature: " + mint+ " °C");
            maxTemperatureTextView.setText("Max Temperature: " + maxt+ " °C");
            weatherImageView.setImageBitmap(weatherBitmap);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
