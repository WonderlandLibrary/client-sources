package arsenic.utils.interfaces;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.file.Files;

public interface IConfig<T extends ISerializable & IContainable> extends IContainer<T> {

    File getDirectory();

    default void loadConfig() {
        JsonObject data = new JsonObject();
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(getDirectory())) {
            JsonElement obj = jsonParser.parse(reader);
            data = obj.getAsJsonObject();
        } catch (JsonSyntaxException | ClassCastException | IOException | IllegalStateException e) {

        }
        loadContentsFromJson(data);
    }

    default void saveConfig() {
        JsonObject data = getJson(new JsonObject());
        try (PrintWriter out = new PrintWriter(new FileWriter(getDirectory()))) {
            out.write(data.toString());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    default void deleteConfig() {
        try {
            Files.delete(getDirectory().toPath());
        } catch (IOException e) {
            //ignored
        }
    }

    default String getName() { return getDirectory().getName().replace(".json", ""); }

    default void loadContentsFromJson(JsonObject obj) {
        getContents().forEach(content -> {
            JsonElement jsonElement = obj.get(content.getJsonKey());
            try {
                content.loadFromJson(jsonElement != null ? jsonElement.getAsJsonObject() : new JsonObject());
            } catch (NullPointerException e){
                //thrown when the values that it wants doesnt exist
            }
        });
    }

    default JsonObject getJson(JsonObject data) {
        getContents().forEach(content -> content.addToJson(data));
        return data;
    }

    default T getContentByJsonKey(String name) {
        for(T serializable : getContents()) {
            if(serializable == null)
                continue;
            if(serializable.getJsonKey().equalsIgnoreCase(name)) {
                return serializable;
            }
        }
        return null;
    }

}
