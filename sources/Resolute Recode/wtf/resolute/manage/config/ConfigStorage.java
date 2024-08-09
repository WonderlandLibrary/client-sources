package wtf.resolute.manage.config;

import com.google.gson.*;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ConfigStorage {
    public final Logger logger = Logger.getLogger(ConfigStorage.class.getName());

    public final File CONFIG_DIR = new File(Minecraft.getInstance().gameDir, "\\resolute\\configs");
    private final File autoCfgDir = new File(Minecraft.getInstance().gameDir, "\\resolute\\configs\\autocfg.cfg");

    public final JsonParser jsonParser = new JsonParser();

    public void init() throws Exception {
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        } else if (autoCfgDir.exists()) {
            loadConfiguration("autocfg");
        } else {
            System.out.println("Автоматический конфиг не найден! Создаю пустой файл конфига...");
            autoCfgDir.createNewFile();
        }
    }

    public boolean isEmpty() {
        return getConfigs().isEmpty();
    }

    public List<Config> getConfigs() {
        List<Config> configs = new ArrayList<>();
        File[] configFiles = CONFIG_DIR.listFiles();

        if (configFiles != null) {
            for (File configFile : configFiles) {
                if (configFile.isFile() && configFile.getName().endsWith(".cfg")) {
                    String configName = configFile.getName().replace(".cfg", "");
                    Config config = findConfig(configName);
                    if (config != null) {
                        configs.add(config);
                    }
                }
            }
        }

        return configs;
    }


    public void loadConfiguration(String configuration) {
        Config config = findConfig(configuration);
        if (config == null) {
            System.out.println("Конфиг " + configuration + " не был найден!");
            return;
        }

        try {
            JsonElement element = compressAndWrite(config.getFile());
            if (element != null) {
                JsonObject object = element.getAsJsonObject();

                config.loadConfig(object, configuration);
            } else {
                saveConfiguration(configuration);
            }
        } catch (JsonParseException e) {
            System.out.println("Ошибка разбора JSON-строки.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Фатальная ошибка конфига.");
            System.out.println("Конфиг " + configuration + " устарел!");
        } catch (ClassCastException classCastException) {
            System.out.println("Фатальная ошибка конфига.");
            System.out.println("Конфиг " + configuration + " пустой!");

        }
    }
    private static final JsonParser jsonParsers = new JsonParser();
    public static JsonElement compressAndWrite(File file) {
        try {
            byte[] byArray = java.nio.file.Files.readAllBytes(file.toPath());
            byte[] byArray2 = compressBytes(byArray);
            String string = new String(byArray2, 0, byArray2.length, StandardCharsets.UTF_8);
            return jsonParsers.parse(string);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    private static byte[] compressBytes(byte[] byArray) throws DataFormatException, IOException {
        if (byArray.length == 0) {
            return byArray;
        }
        Inflater inflater = new Inflater();
        inflater.setInput(byArray);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(byArray.length);
        byte[] byArray2 = new byte[1024];
        while (true) {
            if (inflater.finished()) {
                byteArrayOutputStream.close();
                inflater.end();
                return byteArrayOutputStream.toByteArray();
            }
            int n = inflater.inflate(byArray2);
            byteArrayOutputStream.write(byArray2, 0, n);
        }
    }

    public void saveConfiguration(String configuration) {
        Config config = findConfig(configuration);
        if (config == null) {
            config = new Config(configuration);
        }
        decompressAndWrite(config.getFile(), config.saveConfig());
    }

    public static void decompressAndWrite(File file, JsonElement jsonElement) {
        Thread thread = new Thread(() -> {
            try {
                String string = new GsonBuilder().setPrettyPrinting().create().toJson(jsonElement);
                byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
                byte[] byArray2 = decompress(byArray);
                java.nio.file.Files.write(file.toPath(), byArray2, new OpenOption[0]);
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        });
        thread.start();
    }
    private static byte[] decompress(byte[] byArray) throws IOException {
        if (byArray.length == 0) {
            return byArray;
        }
        Deflater deflater = new Deflater();
        deflater.setLevel(9);
        deflater.setInput(byArray);
        deflater.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(byArray.length);
        byte[] byArray2 = new byte[1024];
        while (true) {
            if (deflater.finished()) {
                byteArrayOutputStream.close();
                deflater.end();
                return byteArrayOutputStream.toByteArray();
            }
            int n = deflater.deflate(byArray2);
            byteArrayOutputStream.write(byArray2, 0, n);
        }
    }

    public Config findConfig(String configName) {
        if (configName == null) return null;
        if (new File(CONFIG_DIR, configName + ".cfg").exists())
            return new Config(configName);
        return null;
    }
}
