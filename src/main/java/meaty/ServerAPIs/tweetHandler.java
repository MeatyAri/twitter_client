package meaty.ServerAPIs;

import java.io.IOException;

import com.google.gson.JsonObject;

import meaty.ServerAPIs.protocol.*;

public class tweetHandler {

    public static long sendTweetRequest(String content) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("content", content);
        data.addProperty("token", ConnectionAPI.getToken());
        return ConnectionAPI.sendMessage(RequestType.CREATE_TWEET, data);
    }

    public static long getTweetsRequest() throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("token", ConnectionAPI.getToken());
        return ConnectionAPI.sendMessage(RequestType.GET_TWEETS, data);
    }

    public static long getTweetsRequest(String username) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("token", ConnectionAPI.getToken());
        data.addProperty("username", username);
        return ConnectionAPI.sendMessage(RequestType.GET_TWEETS, data);
    }

    public static long getBookmarkedTweetsRequest() throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("token", ConnectionAPI.getToken());
        data.addProperty("bookmarks", true);
        return ConnectionAPI.sendMessage(RequestType.GET_TWEETS, data);
    }

    public static long LikeUnlikeTweetRequest(Long tweetId) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("tweetId", tweetId);
        data.addProperty("token", ConnectionAPI.getToken());
        return ConnectionAPI.sendMessage(RequestType.LIKE_UNLIKE_TWEET, data);
    }

    public static long SaveUnsaveTweetRequest(Long tweetId) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("tweetId", tweetId);
        data.addProperty("token", ConnectionAPI.getToken());
        return ConnectionAPI.sendMessage(RequestType.SAVE_UNSAVE_TWEET, data);
    }
}
