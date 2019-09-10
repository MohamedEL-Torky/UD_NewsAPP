package com.example.android.ud_the_guardian_news_app;

import android.text.TextUtils;
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

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String TIME_SEPARATOR = "T";

    private static final int SUCCESS_CODE = 200;

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<News> fetchNewsData(String stringURL) {
        URL url = createURL(stringURL);
        String jsonResponse = makeHttpRequest(url);
        return extractNews(jsonResponse);
    }

    private static URL createURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) {
        HttpURLConnection httpURLConnection = null;
        String jsonResponse = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == SUCCESS_CODE) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in opening a connection ", e);
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String line = bufferedReader.readLine();
                while (line != null) {
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error reading from buffer ", e);
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<News> extractNews(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<News> news = new ArrayList<>();
        try {
            JSONObject rootJson = new JSONObject(jsonResponse);
            JSONObject responseObject = rootJson.getJSONObject("response");
            JSONArray resultsArray = responseObject.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject resultsObject = resultsArray.getJSONObject(i);
                String tittle = resultsObject.getString("webTitle");
                String section = resultsObject.getString("sectionName");
                String url = resultsObject.getString("webUrl");

                String date = null;
                String time = null;
                /*Checking if the Json response has "webPublicationDate" before processing it.
                As stated in the project rubric, the response might come without time and date
                */
                if (resultsObject.has("webPublicationDate")) {
                    String dateTime = resultsObject.getString("webPublicationDate");
                    String[] words = dateTime.split(TIME_SEPARATOR);
                    date = words[0];
                    time = words[1].substring(0, words[1].length() - 1);
                }
                String authorName = null;
                /*Checking if the Json response has "tags" before processing it.
                As stated in the project rubric, the response might come without tags which contains
                the author name.
                */
                if (resultsObject.has("tags")) {
                    JSONArray tagsArray = resultsObject.getJSONArray("tags");
                    if (tagsArray.length() != 0) {
                        JSONObject contributorTag = (JSONObject) tagsArray.get(0);
                        authorName = contributorTag.getString("webTitle");
                    }
                }
                //Covering all the cases if something goes missing
                if (authorName != null && (date != null && time != null)) {
                    news.add(new News(tittle, section, authorName, url, date, time));
                } else if (authorName == null && (date == null && time == null)) {
                    news.add(new News(tittle, section, url));
                } else if (authorName == null) {
                    news.add(new News(tittle, section, url, date, time));
                } else if (date == null && time == null) {
                    news.add(new News(tittle, section, authorName, url));
                }
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return news;
    }
}
