package club.strifeclient.config;

import club.strifeclient.Client;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class Config {

    private final String author, description;
    private final Path path;
    private final double version;
    private String name;

    public Config(final String name, final String author, final String description, final double version) {
        this.path = Client.INSTANCE.getConfigManager().getPathForConfig(name);
        this.name = name;
        this.author = author;
        this.description = description;
        this.version = version;
    }

    public Config(String name, String author) {
        this(name, author, "", 1);
    }

    public boolean create() {
        try {
            Client.INSTANCE.getConfigManager().add(this);
            Client.INSTANCE.getConfigManager().writeConfigToFile(this);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean load() {
        try {
            Client.INSTANCE.getConfigManager().readConfigFromFile(path.toFile().getPath());
            Client.INSTANCE.getConfigManager().setLoadedConfig(this);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean save() {
        try {
            Client.INSTANCE.getConfigManager().writeConfigToFile(this);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete() {
        try {
            Client.INSTANCE.getConfigManager().getFileConfigs().remove(this);
            Files.delete(Client.INSTANCE.getConfigManager().getPathForConfig(name));
            Client.INSTANCE.getConfigManager().setLoadedConfig(null);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
