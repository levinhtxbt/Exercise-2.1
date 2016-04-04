package com.hasbrain.areyouandroiddev.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/8/15.
 */
public class RedditPostConverter extends EasyDeserializer<RedditPost> {

    @Override
    public RedditPost deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        RedditPost redditPost = null;
        if (json != null && json.isJsonObject()) {
            JsonObject postJsonObject = json.getAsJsonObject();
            JsonElement dataElement = postJsonObject.get("data");
            if (dataElement != null && dataElement.isJsonObject()) {
                JsonObject dataJsonObject = dataElement.getAsJsonObject();
                redditPost = new RedditPost();
                redditPost.setId(getStringValue(dataJsonObject.get("id"), null));
                redditPost.setTitle(getStringValue(dataJsonObject.get("title"), null));
                redditPost.setAuthor(getStringValue(dataJsonObject.get("author"), null));
                redditPost.setCommentCount(getIntValue(dataJsonObject.get("num_comments"), 0));
                redditPost.setScore(getIntValue(dataJsonObject.get("score"), 0));
                redditPost.setIsStickyPost(getBooleanValue(dataJsonObject.get("stickied"), false));
                redditPost.setUrl(getStringValue(dataJsonObject.get("url"), null));
                redditPost.setCreatedUTC(getLongValue(dataJsonObject.get("created_utc"), 0));
                redditPost.setSubreddit(getStringValue(dataJsonObject.get("subreddit"), null));
                redditPost.setDomain(getStringValue(dataJsonObject.get("domain"), null));
            }
        }
        return redditPost;
    }
}
