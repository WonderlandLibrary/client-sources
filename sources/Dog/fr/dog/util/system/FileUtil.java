package fr.dog.util.system;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.dog.util.InstanceAccess;
import net.minecraft.util.ResourceLocation;

import java.io.*;

public class FileUtil implements InstanceAccess {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static JsonObject readJsonFromFile(String path) {
        try {
            return GSON.fromJson(new FileReader(path), JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonObject readJsonFromResourceLocation(ResourceLocation resourceLocation) {
        try (InputStream inputStream = mc.getResourceManager().getResource(resourceLocation).getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            return GSON.fromJson(stringBuilder.toString(), JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeJsonToFile(JsonObject json, String path) {
        try {
            FileWriter writer = new FileWriter(path);
            GSON.toJson(json, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}