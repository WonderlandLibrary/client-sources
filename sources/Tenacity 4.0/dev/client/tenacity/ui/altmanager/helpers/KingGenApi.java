package dev.client.tenacity.ui.altmanager.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.client.tenacity.Tenacity;
import dev.client.tenacity.utils.misc.NetworkingUtils;

import java.io.*;

public class KingGenApi {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().setPrettyPrinting().create();
    private final File kingAltData = new File(Tenacity.DIRECTORY, "KingGen.json");
    public String generated = "0";
    public String generatedToday = "0";
    public String username = "";
    private String key = "";

    public void setKey(String key) {
        JsonObject keyObject = new JsonObject();
        keyObject.addProperty("key", key);
        try {
            Writer writer = new BufferedWriter(new FileWriter(kingAltData));
            gson.toJson(keyObject, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.key = key;
    }

    public void refreshKey() {

    }

    public boolean checkKey() {
       return true;
    }

    public boolean hasKeyInFile() {

        return true;
    }

    public final String[] genAlt() {
        String[] errorResponse = {"error", "error"};
        return errorResponse;
    }
}