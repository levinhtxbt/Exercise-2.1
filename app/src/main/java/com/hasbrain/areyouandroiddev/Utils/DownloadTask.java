package com.hasbrain.areyouandroiddev.utils;

import android.os.AsyncTask;

import com.hasbrain.areyouandroiddev.datastore.FeedDataStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by levinhtxbt@gmail.com on 19/08/2016.
 */
public class DownloadTask extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate = null;
    private FeedDataStore feedDataStore;

    public DownloadTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... params) {
        if (!params[0].equals(""))
            try {
                return downloadFromURL(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    protected void onPostExecute(String para) {
        super.onPostExecute(para);
        delegate.processFinish(para);
    }

    private String downloadFromURL(String myUrl) throws IOException {

        InputStream inputStream = null;
        URL url = new URL(myUrl);
//        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        int response = connection.getResponseCode();
        inputStream = connection.getInputStream();
        return  readIt(inputStream);
    }
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = stream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }

}
