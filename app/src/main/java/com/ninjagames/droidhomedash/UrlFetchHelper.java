package com.ninjagames.droidhomedash;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Allows to fetch string data from a server, given the url and a callable.
 * The callable is an object of a class implementing the FetchDataCallbackInterface
 * which defines the callback method fetchDataCallback
 */
public class UrlFetchHelper extends AsyncTask<String, Integer, String> {
    HttpURLConnection urlConnection;
    Callback callback;

    public interface Callback {
        void onComplete(String result);
    }
    /**
     * Constructor
     * @param url
     */
    public UrlFetchHelper(Callback callback) {
        this.callback = callback;
    }
    @Override
    protected String doInBackground(String... args) {
        StringBuilder result = new StringBuilder();
        if (args.length == 0) {
            return null;
        }
        try {
            URL url = new URL(args[0]);
            Log.i("UrlFetcherHelper", url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return result.toString();
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        callback.onComplete(result);
    }
}