package wtf.diablo.client.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import wtf.diablo.client.manager.AbstractManager;
import wtf.diablo.client.util.Constants;

import java.io.*;
import java.util.Objects;

public final class ConfigManager extends AbstractManager<Config> {
    private final File directory;

    public ConfigManager(final File directory) {
        super();
        this.directory = directory;
    }

    @Override
    public void initialize() {
        final File file = new File(this.directory, "default.json");
        if (!file.exists()) {
            final Config config = new Config("default");

            this.register(config);
            this.saveConfig(config);
        } else {
            this.loadConfig("default");
        }
    }

    @Override
    public void shutdown() {
        final Config config = new Config("default");

        this.saveConfig(config);
    }

    public void loadConfig(final String name) {
        try {
            final File file = new File(this.directory, name + ".json");
            try {
                final JsonObject json = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
                final Config config = new Config(name);
                config.parseJSON(json);

                this.register(config);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (final Exception e) {
            System.err.println("Failed to load config: " + name);
        }
    }

    public void saveConfig(final Config config) {
        try {
            final FileWriter writer = new FileWriter(new File(this.directory, config.getName() + ".json"));
            writer.write(Constants.GSON.toJson(config.constructJSON()));
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to save config: " + config.getName());
        }
    }

    public void registerAllInDirectory() {
        this.getEntries().clear();

        for (final File file : Objects.requireNonNull(this.directory.listFiles())) {
            if (file.getName().endsWith(".json")) {
                final Config config = new Config(file.getName().replace(".json", ""));

                this.register(config);
            }
        }
    }
}