package xyz.mathax.mathaxclient.utils.json;

import xyz.mathax.mathaxclient.utils.files.StreamUtils;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JSONUtils {
    public static void saveJSON(JSONObject json, File file) {
        if (file == null) {
            return;
        }

        file.getParentFile().mkdirs();

        try {
            File tempFile = File.createTempFile("Temp", file.getName());
            FileUtils.write(tempFile, json.toString(4));

            StreamUtils.copy(tempFile, file);
            tempFile.delete();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static JSONObject loadJSON(File file) {
        if (file == null) {
            return null;
        }


        if (file.exists()) {
            try {
                String jsonString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                if (jsonString != null && isValidJSON(jsonString)) {
                    return new JSONObject(jsonString);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public static boolean isValidJSON(String stringJson) {
        try {
            new JSONObject(stringJson);
        } catch (JSONException exception) {
            return false;
        }

        return true;
    }

    public static boolean isValidJSONArray(String stringJsonArray) {
        try {
            new JSONArray(stringJsonArray);
        } catch (JSONException exception) {
            return false;
        }

        return true;
    }

    public static boolean isValidJSONArray(JSONObject json, String key) {
        try {
            new JSONArray(json.getJSONArray(key));
        } catch (JSONException exception) {
            return false;
        }

        return true;
    }
}
