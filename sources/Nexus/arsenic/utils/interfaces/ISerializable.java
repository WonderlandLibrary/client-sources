package arsenic.utils.interfaces;

import com.google.gson.JsonObject;

public interface ISerializable extends IContainable{

    void loadFromJson(JsonObject obj);
    JsonObject saveInfoToJson(JsonObject obj);
    default JsonObject addToJson(JsonObject obj) {
        final JsonObject config = new JsonObject();
        saveInfoToJson(config);
        obj.add(getJsonKey(), config);
        return obj;
    }
    String getJsonKey();

}
