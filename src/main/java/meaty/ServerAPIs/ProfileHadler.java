package meaty.ServerAPIs;

import java.io.IOException;

import com.google.gson.JsonObject;

import meaty.ServerAPIs.protocol.RequestType;

public class ProfileHadler {

    public static long sendGetSelfProfileRequest(String token) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("token", token);
        data.addProperty("self", true);
        return ConnectionAPI.sendMessage(RequestType.GET_PROFILE, data);
    }

    public static long sendGetProfileRequest(String token, String username) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("token", token);
        data.addProperty("username", username);
        return ConnectionAPI.sendMessage(RequestType.GET_PROFILE, data);
    }

    public static long sendFollowRequest(String token, String username) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("token", token);
        data.addProperty("username", username);
        return ConnectionAPI.sendMessage(RequestType.FOLLOW, data);
    }

    public static long sendUnfollowRequest(String token, String username) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("token", token);
        data.addProperty("username", username);
        return ConnectionAPI.sendMessage(RequestType.UNFOLLOW, data);
    }
}
