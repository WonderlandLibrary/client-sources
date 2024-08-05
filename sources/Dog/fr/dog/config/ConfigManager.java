package fr.dog.config;

import fr.dog.Dog;
import fr.dog.util.InstanceAccess;
import fr.dog.util.player.ChatUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class ConfigManager implements InstanceAccess {
    public static final Map<String, Config> configs = new HashMap<>();

    private static final File configFolder = new File(mc.mcDataDir, "/dog/configs");

    private Config activeConfig;

    private void refresh() {
        for (File file : Objects.requireNonNull(configFolder.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                String name = file.getName().replaceAll(".json", "");
                Config config = new Config(name);
                configs.put(config.getName(), config);
            }
        }
    }

    public void init() {
        try {
            configFolder.mkdirs();

            refresh();

            if (getConfig("default") == null) {
                Config config = new Config("default");
                config.write();
                configs.put(config.getName(), config);
            } else getConfig("default").read();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stop() {
        if (getConfig("default") == null) {
            Config config = new Config("default");
            config.write();
        } else getConfig("default").write();
    }

    public Config getConfig(String name) {
        return configs.keySet().stream().filter(key -> key.equalsIgnoreCase(name)).findFirst().map(configs::get).orElse(null);
    }

    public void saveConfig(String configName) {
        if (getConfig(configName) == null) {
            Config config = new Config(configName);
            config.write();
        } else getConfig(configName).write();
        refresh();
        ChatUtil.display(String.format("Saved config %s", configName));
    }

    public boolean delete(String configName) {
        Config existingConfig = getConfig(configName);

        if (existingConfig != null) {
            File configFile = new File(existingConfig.getDirectory().toString());

            if (configFile.exists()) {
                boolean deleted = configFile.delete();

                if (deleted) {
                    ChatUtil.display(String.format("Deleted config %s", configName));
                }

                return deleted;
            }
        }

        return true;
    }

    public void loadConfig(String configName) {
        refresh();

        if (getConfig(configName) != null) {
            getConfig(configName).read();
        }

        ChatUtil.display(String.format("Loaded config %s", configName));
    }
    @SneakyThrows
    public String uploadConfig(String name, File file){


        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("multipart/form-data");

        // Create the multipart request body
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "file.txt",
                        RequestBody.create(mediaType, file))
                .build();

        // Create the POST request
        Request request = new Request.Builder()
                .url("https://legitclient.com/uploadConfig?token=" + Dog.getInstance().getToken() + "&name=" + name)
                .post(requestBody)
                .build();

        // Execute the request
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                System.out.println("File upload failed. Response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "NoConfigUploaded";
    }
}
