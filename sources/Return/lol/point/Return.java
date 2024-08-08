package lol.point;

import lol.point.returnclient.configs.LoadConfig;
import lol.point.returnclient.configs.SaveConfig;
import lol.point.returnclient.managers.*;
import lol.point.returnclient.ui.LoginMenu;
import lol.point.returnclient.util.system.FileUtil;
import lol.point.returnclient.util.system.NetworkUtil;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import me.zero.alpine.listener.Subscriber;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static lol.point.returnclient.util.MinecraftInstance.mc;

public enum Return implements Subscriber {
    INSTANCE;

    public static final Logger LOGGER = LogManager.getLogger("Return");

    public static final Minecraft MC = Minecraft.getMinecraft();

    public static final EventBus BUS = EventManager.builder()
            .setName("root/Return")
            .setSuperListeners()
            .build();

    public String
            name = "Return",
            version = "v0.01",
            commandPrefix = ".",
            clientPrefix = "[Return]",
            authors = "Authenthication, noarc, Endpoint, trqpc, Nat, GreenToad";

    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public SettingsManager settingManager;
    public FontManager fontManager;
    public ThemeManager themeManager;
    public ConfigManager configManager;

    public final void preInit() {
        FileUtil.createFolder("return");
        FileUtil.createFolder("return/fonts");
        FileUtil.createFolder("return/configs");
        FileUtil.createFolder("return/online-configs");
        try {
            final Path runningDirectoryPath = Paths.get(Minecraft.getMinecraft().mcDataDir.getPath(), "return");

            if (!Files.exists(runningDirectoryPath))
                Files.createDirectories(runningDirectoryPath);

            final Path fontVersionPath = Paths.get(runningDirectoryPath.toString(), "font-version");

            String versionFromWeb = NetworkUtil.raw("https://raw.githubusercontent.com/Return-Client/cloud/main/font-version");

            // Check if the font-version file exists
            if (Files.exists(fontVersionPath)) {
                String versionInFile = Files.readString(fontVersionPath);
                Path fontsDirectory = Paths.get(runningDirectoryPath.toString(), "fonts");

                // Check if the versions are different or the fonts directory doesn't exist
                if (!Objects.equals(versionFromWeb, versionInFile) || !Files.exists(fontsDirectory)) {
                    String fontsZipURL = "https://github.com/Return-Client/cloud/raw/main/fonts.zip";
                    NetworkUtil.download_zip(fontsZipURL, Paths.get(Minecraft.getMinecraft().mcDataDir.getPath(), "return").toString());

                    Files.writeString(fontVersionPath, versionFromWeb);
                } else {
                    LOGGER.info("Fonts are up to date. Skipping download.");
                }
            } else {
                Files.createFile(fontVersionPath);
                Files.writeString(fontVersionPath, versionFromWeb);

                String fontsZipURL = "https://github.com/Return-Client/cloud/raw/main/fonts.zip";
                NetworkUtil.download_zip(fontsZipURL, Paths.get(Minecraft.getMinecraft().mcDataDir.getPath(), "return").toString());
            }
        } catch (IOException | URISyntaxException e) {
            LOGGER.fatal("Error verifying font version!", e);
            throw new RuntimeException(e);
        }
    }

    public final void postInit() {
        BUS.subscribe(this);
        Display.setTitle(name + " " + version);
        themeManager = new ThemeManager();
        moduleManager = new ModuleManager();
        mc.displayGuiScreen(new LoginMenu());
        configManager = new ConfigManager();
        commandManager = new CommandManager();
        settingManager = new SettingsManager();

        try {
            LoadConfig loadConfig = new LoadConfig();
            loadConfig.loadKeybinds();
            loadConfig.loadDefault();

        } catch (IOException e) {
            LOGGER.error("Failed to load the default config: {}", e.getMessage());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    public final void shutdown() {
        LOGGER.info("Shutting down Return!");
        try {
            SaveConfig saveConfig = new SaveConfig();
            saveConfig.saveDefault();
            saveConfig.saveKeybinds();
        } catch (IOException e) {
            LOGGER.error("Failed to save the default config: {}", e.getMessage());
        }

        BUS.unsubscribe(this);
    }

}
