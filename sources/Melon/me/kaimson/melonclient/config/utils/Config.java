package me.kaimson.melonclient.config.utils;

import me.kaimson.melonclient.*;
import java.util.function.*;
import com.google.gson.*;
import java.io.*;
import java.util.*;

public abstract class Config implements IConfig
{
    public final String name;
    protected final File configFile;
    protected final double version;
    
    public Config(final String name, final String type, final double version) {
        this.name = name;
        this.version = version;
        this.configFile = new File(Client.dir, name + "." + type);
    }
    
    protected void createStructure() {
        if (!this.configFile.exists() || !Client.dir.exists()) {
            Client.dir.mkdirs();
            try {
                this.configFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    protected void getNonNull(final JsonObject jsonObject, final String key, final Consumer<JsonElement> consumer) {
        if (jsonObject != null && jsonObject.get(key) != null) {
            consumer.accept(jsonObject.get(key));
        }
    }
    
    public JsonObject loadJsonFile(final File file) {
        if (!file.exists()) {
            return null;
        }
        final JsonElement fileElement = new JsonParser().parse(this.getFileContents(file));
        if (fileElement == null || fileElement.isJsonNull()) {
            throw new JsonParseException("File \"" + file.getName() + "\" is null!");
        }
        return fileElement.getAsJsonObject();
    }
    
    public String getFileContents(final File file) {
        if (file.exists()) {
            try {
                final BufferedReader reader = new BufferedReader(new FileReader(file));
                try {
                    final StringBuilder builder = new StringBuilder();
                    String nextLine;
                    while ((nextLine = reader.readLine()) != null) {
                        builder.append(nextLine);
                    }
                    return builder.toString().equals("") ? "{}" : builder.toString();
                }
                finally {
                    if (Collections.singletonList(reader).get(0) != null) {
                        reader.close();
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "{}";
    }
}
