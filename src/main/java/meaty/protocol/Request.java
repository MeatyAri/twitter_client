package meaty.protocol;

import com.google.gson.JsonObject;

public class Request {
    private long id;
    private RequestType type;
    private JsonObject data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }
}
