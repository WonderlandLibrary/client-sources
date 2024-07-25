package club.bluezenith.core.data.config;

import club.bluezenith.core.data.ClientResourceRepository;
import club.bluezenith.core.data.preferences.DataHandler;
import club.bluezenith.module.Module;
import club.bluezenith.util.player.TargetHelper;
import com.google.gson.*;
import org.lwjgl.input.Keyboard;

import java.io.File;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.module.ModuleCategory.RENDER;
import static club.bluezenith.util.player.TargetHelper.getTargetsAsObject;
import static java.lang.String.format;
import static java.nio.file.Files.readAllBytes;
import static java.util.Arrays.stream;
import static org.apache.commons.io.FilenameUtils.removeExtension;

public class ConfigManager implements DataHandler {
    private final ClientResourceRepository repository;

    private static final String folderName = "config", extension = ".json", targetsName = "targets", keybindsName = ("binds" + extension);

    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ConfigManager(ClientResourceRepository repository) {
        this.repository = repository;
    }

    public String[] getConfigs() {
        return stream(repository.getAllFilesInFolder(folderName))
                .filter(fileName -> fileName.endsWith(extension))
                .toArray(String[]::new);
    }

    public void clearConfigs() {
        repository.createFolderInDirectory(folderName, true);
    }

    private void loadConfigFromJson(String json, String configName, boolean setModuleKeybindings, boolean ignoreRenderModules, boolean sendNotifications) {
        try {
            final JsonElement configElement = new JsonParser().parse(json); assert configElement != null;

            final JsonObject configObject = configElement.getAsJsonObject();

            configObject.entrySet().forEach(entry -> {
                if (entry.getKey().equals(targetsName)) {
                    TargetHelper.fromObject(entry);
                    return;
                }

                final Module module = getBlueZenith().getModuleManager().getModule(entry.getKey());

                if (module == null || module.getCategory() == RENDER && ignoreRenderModules) return;

                module.deserializeModuleData(entry.getValue().getAsJsonObject(), setModuleKeybindings);
            });
            if (sendNotifications) {
                String notification = "Loaded config " + removeExtension(configName);

                if (ignoreRenderModules) notification += ", ignored render modules";
                if (setModuleKeybindings) notification += " and set keybinds";
                notification += ".";

                getBlueZenith().getNotificationPublisher().postSuccess("Config Manager", notification, 2000);
            }
        } catch (Exception exception) {
            getBlueZenith().getNotificationPublisher().postError(
                    "Config Manager",
                    format("Couldn't load config '%s' \n Error: %s",
                            removeExtension(configName),
                            exception.getClass().getName()),
                    3500
            );
            exception.printStackTrace();
        }
    }

    public void loadConfigFromName(String name, boolean setModuleKeybindings, boolean ignoreRenderModules, boolean sendNotifications) {
        assert name != null;
        if(!name.endsWith(extension)) name += extension;

        if(!repository.fileExists(folderName, name)) {
            getBlueZenith().getNotificationPublisher().postError(
                    "Config Manager",
                    format("Config '%s' doesn't exist.", removeExtension(name)),
                    3000
            );
            return;
        }

        loadConfigFromJson(repository.readFromFile(folderName, name), name, setModuleKeybindings, ignoreRenderModules, sendNotifications);
    }

    public void loadConfigFromFile(File configFile, String configName, boolean setModuleKeybindings, boolean ignoreRenderModules, boolean sendNotifications) {
        if(configFile == null || !configFile.exists()) {
            getBlueZenith().getNotificationPublisher().postError(
                    "Config Manager",
                    format("Config '%s' doesn't exist.", removeExtension(configName)),
                    3000
            );
            return;
        }

        try {
            loadConfigFromJson(new String(readAllBytes(configFile.toPath())), configName, setModuleKeybindings, ignoreRenderModules, sendNotifications);
        } catch (Exception exception) {
            getBlueZenith().getNotificationPublisher().postError(
                    "Config Manager",
                    format("Couldn't load config '%s' \n Error: %s",
                            removeExtension(configName),
                            exception.getClass().getName()),
                    3500
            );
            exception.printStackTrace();
        }
    }

    public void saveConfig(String name, boolean notify) {
        if(!name.endsWith(extension)) name += extension;

        final JsonObject configObject = new JsonObject();
        getBlueZenith().getModuleManager().getModules().forEach(module -> configObject.add(module.getName(), module.serializeModuleData()));

        configObject.add(targetsName, getTargetsAsObject());
        repository.writeToFile(folderName, name, gson.toJson(configObject));

        if(notify)
            getBlueZenith().getNotificationPublisher().postSuccess(
                    "Config Manager",
                    format("Saved config \"%s\"", removeExtension(name)),
                    2000);
    }

    @Override
    public void serialize() {
        final JsonObject binds = new JsonObject();
        getBlueZenith().getModuleManager().getModules().forEach(module ->
                binds.add(module.getName(), new JsonPrimitive(Keyboard.getKeyName(module.keyBind)))
        );
        repository.writeToFile(keybindsName, gson.toJson(binds));
        saveConfig("default", false);
    }

    @Override
    public void deserialize() {
        loadConfigFromName("default", false, false, false);
        if(!repository.fileExists(keybindsName)) return;
        new JsonParser().parse(repository.readFromFile(keybindsName)).getAsJsonObject().entrySet().forEach(entry -> {
            final Module module = getBlueZenith().getModuleManager().getModule(entry.getKey());

            if(module == null) return;
            module.keyBind = Keyboard.getKeyIndex(entry.getValue().getAsString());
        });
    }
}
