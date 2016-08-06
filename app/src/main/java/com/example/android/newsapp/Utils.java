package com.example.android.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakshmivineeth on 8/6/16.
 */
class Utils {

    private static final String LOG_TAG = NewsAsyncLoader.class.getSimpleName();

    public static List<News> fetchNews(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<News> newsList = extractNewsFromJson(jsonResponse);

        return newsList;
    }

    private static List<News> extractNewsFromJson(String jsonResponse) {
        if (jsonResponse.length() < 1) {
            return null;
        }

        List<News> news = new ArrayList<>();
        try {
            JSONObject responseJson = new JSONObject(jsonResponse).getJSONObject("response");
            JSONArray resultsArray = responseJson.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                try {
                    JSONObject newsJson = resultsArray.getJSONObject(i);
                    String sectionName = newsJson.getString("sectionName");
                    String date = newsJson.getString("webPublicationDate");
                    String title = newsJson.getString("webTitle");
                    String url = newsJson.getString("webUrl");
                    String contributor = newsJson.optString("contributor");

                    News earthquake = new News(sectionName, date, title, url, contributor);
                    news.add(earthquake);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Exception raised for News item at: " + i);
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results", e);
        }
        return news;

    }

    private static String makeHttpRequest(URL url) throws IOException {
        if (url == null) {
            return null;
        }

        HttpURLConnection urlConnection;
        String jsonResponse = "";
        InputStream inputStream;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the News JSON results" + e);
        }

        return jsonResponse;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        InputStreamReader inputReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputReader);
        String line = reader.readLine();
        while (line != null) {
            output.append(line);
            line = reader.readLine();
        }
        return output.toString();
    }

    private static URL createUrl(String param) {
        URL url = null;
        try {
            url = new URL(param);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
