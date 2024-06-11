package meaty.ServerAPIs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;

// import com.google.gson.Gson;
import com.google.gson.JsonObject;

import meaty.protocol.*;

public class Auth {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String pass2hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static long sendLoginRequest(String username, String password) throws IOException {
        JsonObject data = new JsonObject();
        data.addProperty("username", username);
        data.addProperty("password", password);
        return ConnectionAPI.sendMessage(RequestType.LOGIN, data);
    }

    public static long sendSignUpRequest(String username, String password, String email, String phone, Date birthDate, String bio) throws IOException {
        JsonObject data = new JsonObject();

        // replace the password with its hash
        password = pass2hash(password);

        String birthDateStr = new SimpleDateFormat(DATE_FORMAT).format(birthDate);

        String[] properties = {"username", "password", "email", "phone", "birthDate", "bio"};
        String[] values = {username, password, email, phone, birthDateStr, bio};
        for (int i = 0; i < properties.length; i++) {
            String property = properties[i];
            String value = values[i];
            if (value != null && !value.isEmpty()) {
                data.addProperty(property, value);
            }
        }
        return ConnectionAPI.sendMessage(RequestType.SIGNUP, data);
    }
}
