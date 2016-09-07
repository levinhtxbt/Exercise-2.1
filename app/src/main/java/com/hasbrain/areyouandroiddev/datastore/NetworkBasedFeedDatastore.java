package com.hasbrain.areyouandroiddev.datastore;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hasbrain.areyouandroiddev.utils.AsyncResponse;
import com.hasbrain.areyouandroiddev.utils.DownloadTask;
import com.hasbrain.areyouandroiddev.model.RedditPost;
import com.hasbrain.areyouandroiddev.model.RedditPostConverter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by levinhtxbt@gmail.com on 30/08/2016.
 */
public class NetworkBasedFeedDatastore implements FeedDataStore {

    private static final String TAG = NetworkBasedFeedDatastore.class.getSimpleName();
    private String baseUrl;
    private Gson gson;

    public NetworkBasedFeedDatastore(String baseUrl) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RedditPost.class, new RedditPostConverter());
        gson = gsonBuilder.create();
        this.baseUrl = baseUrl;
    }

    @Override
    public void getPostList(final OnRedditPostsRetrievedListener onRedditPostsRetrievedListener) {
        if (onRedditPostsRetrievedListener != null) {
            DownloadTask downloadTask = (DownloadTask) new DownloadTask(new AsyncResponse() {
                @Override
                public void processFinish(String json) {
                    if(TextUtils.isEmpty(json)){
                        onRedditPostsRetrievedListener.onRedditPostsRetrieved(null,null);
                    }

                    JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
                    if (jsonObject != null && jsonObject.isJsonObject()) {
                        JsonElement elementData = jsonObject.get("data");
                        if (elementData != null && elementData.isJsonObject()) {
                            JsonObject jsonChildren = elementData.getAsJsonObject();
                            JsonElement elementChild = jsonChildren.get("children");
                            if (elementChild != null && elementChild.isJsonArray()) {
                                String strJsonChild = elementChild.toString();
                                Log.d(TAG, "processFinish: " + strJsonChild);
                                Type type = new TypeToken<List<RedditPost>>() {}.getType();
                                InputStream is = new ByteArrayInputStream(strJsonChild.getBytes());
                                List<RedditPost> posts = gson.fromJson(new InputStreamReader(is), type);
                                onRedditPostsRetrievedListener.onRedditPostsRetrieved(posts, null);
                            }
                        }
                    }
                }
            }).execute(baseUrl);
        }
    }
}
