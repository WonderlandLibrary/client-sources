package net.silentclient.client.emotes.config;

import net.minecraft.client.Minecraft;
import net.silentclient.client.Client;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;

public class EmotesConfig {
    public static File configFile;
    public static EmotesConfigType config;
    public static HashMap<Integer, EmotesConfigType.Bind> binds;

    public static void init() throws IOException {
        configFile = new File(Minecraft.getMinecraft().mcDataDir, "slc-emotes.json");
        if(!configFile.exists()) {
            configFile.createNewFile();
            config = EmotesConfigType.getDefault();
            save();
        } else {
            try {
                InputStream in = Files.newInputStream(configFile.toPath());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    content.append(inputLine);
                }
                config = Client.getInstance().getGson().fromJson(content.toString(), EmotesConfigType.class);
                in.close();
            } catch (Exception err) {
                Client.logger.catching(err);
                config = EmotesConfigType.getDefault();
                save();
            }
        }
        updateHashMap();
    }

    public static void save() {
        try {
            FileOutputStream outputStream = new FileOutputStream(configFile);
            byte[] strToBytes = Client.getInstance().getGson().toJson(config).toString().getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
        } catch (Exception err) {
            Client.logger.catching(err);
        }
    }

    public static void updateHashMap() {
        HashMap<Integer, EmotesConfigType.Bind> map = new HashMap<>();
        for (EmotesConfigType.Bind bind : config.getBinds()) {
            map.put(bind.keyId, bind);
        }
        binds = map;
    }

    public static HashMap<Integer, EmotesConfigType.Bind> getBinds() {
        return binds;
    }

    public static EmotesConfigType getConfig() {
        return config;
    }
}
