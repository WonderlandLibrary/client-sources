package de.tired.tired;

import de.tired.api.extension.Extension;
import de.tired.api.guis.ConfigGui;
import de.tired.api.guis.clickgui.impl.ClickGui;
import de.tired.api.guis.clickgui.setting.SettingsManager;
import de.tired.api.guis.guipainting.GuiPainting;
import de.tired.api.performanceMode.PerformanceGui;
import de.tired.api.userinterface.UIManager;
import de.tired.api.util.misc.DiscordRPC;
import de.tired.api.util.misc.FBReconnect;
import de.tired.api.util.misc.FileUtil;
import de.tired.api.util.misc.GitHubDownloader;
import de.tired.api.util.font.FontManager;
import de.tired.config.ConfigManager;
import de.tired.interfaces.IHook;
import de.tired.module.Module;
import de.tired.module.ModuleManager;
import de.tired.shaderloader.ShaderManager;
import de.tired.shaderloader.ShaderRenderer;
import lombok.Getter;
import net.minecraft.client.settings.GameSettings;

import java.io.File;

public enum Tired {

    INSTANCE("Tired", "70", new String[]{"Felix1337", "Kroko1337", "Exeos", "0x150"});

    public static final String PROJECT_NAME = "5 Months of Tired Client! (still buggy lol) ";

    public static PerformanceGui performanceGui;

    public ShaderRenderer shaderRenderer;

    public SettingsManager settingsManager;

    public ModuleManager moduleManager;

    public UIManager uiMainMenu;

    public GuiPainting guiPainting;

    public FontManager fontManager;

    public ConfigManager configManager;

    public ClickGui clickGui;

    public DiscordRPC discordRPC;

    public ConfigGui configGui;

    public Module usingModule;

    public static String TITLE_STRING;

    public ShaderManager shaderManager;

    public void doClient() {

        Thread thread = new Thread(TiredCore.CORE::initCore);
        thread.start();

        File directory = new File(IHook.MC.mcDataDir, "Tired");

        this.uiMainMenu = new UIManager();
        this.settingsManager = new SettingsManager();
        FileUtil.FILE_UTIL.loadPerformanceMode();

        this.fontManager = new FontManager();
        this.fontManager.bootstrap();

        Extension.EXTENSION.setupExtension();
        moduleManager = new ModuleManager();
        clickGui = new ClickGui();


        GitHubDownloader.GIT_HUB_DOWNLOADER.download();
        configManager = new ConfigManager();
        shaderManager = new ShaderManager();

        guiPainting = new GuiPainting();
        FileUtil.FILE_UTIL.loadKeybinds();
        FileUtil.FILE_UTIL.loadSettings();
        FileUtil.FILE_UTIL.loadColors();
        FileUtil.FILE_UTIL.loadTime();
        FileUtil.FILE_UTIL.loadAlt();
        FBReconnect fbReconnect = new FBReconnect();
        fbReconnect.toData();
        FileUtil.FILE_UTIL.getClientData();
        FileUtil.FILE_UTIL.loadModule();

        shaderRenderer = new ShaderRenderer();

        performanceGui = new PerformanceGui();
        configGui = new ConfigGui();


        if (IHook.MC.gameSettings.ofFastRender)
            IHook.MC.gameSettings.ofFastRender = false;

        if (IHook.MC.gameSettings.fancyGraphics) {
            IHook.MC.gameSettings.fancyGraphics = false;
        }
        if (IHook.MC.gameSettings.ofConnectedTextures == 2 || IHook.MC.gameSettings.ofConnectedTextures == 1) {
            IHook.MC.gameSettings.ofConnectedTextures = 0;
            GameSettings.Options.CONNECTED_TEXTURES.setValueMax(0);
        }
    }

    @Getter
    public final String CLIENT_NAME;
    @Getter
    public final String VERSION;

    public final String[] CODER;


    Tired(String clientName, String clientVersion, String[] coder) {
        this.CLIENT_NAME = clientName;
        this.VERSION = clientVersion;
        this.CODER = coder;
    }


    public ClickGui getClickGui() {
        return clickGui;
    }
}
