package koks;

import koks.command.CommandManager;
import koks.event.EventManager;
import koks.files.FileManager;
import koks.gui.clickgui.ClickGUI;
import koks.gui.clickgui.commonvalue.CommonValueManager;
import koks.gui.configs.ConfigScreen;
import koks.gui.configsnew.DrawConfigManager;
import koks.gui.customhud.CustomHUD;
import koks.gui.customhud.valuehudsystem.ValueHUDManager;
import koks.gui.panelgui.PanelGUI;
import koks.hud.tabgui.TabGUI;
import koks.manager.ConfigManager;
import koks.modules.ModuleManager;
import koks.theme.Theme;
import koks.theme.ThemeManager;
import koks.utilities.value.ValueManager;
import net.minecraft.client.audio.*;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:07
 */
public class Koks {

    private static final Koks KOKS;

    static {
        KOKS = new Koks();
    }

    public static Koks getKoks() {
        return KOKS;
    }

    public final String CLIENT_NAME = "Koks";
    public final String[] CLIENT_DEVELOPER = new String[] {"avox", "lmao", "Kroko"};
    public final String CLIENT_VERSION = "1.0.0";
    public final String PREFIX = "§c" + CLIENT_NAME + " §7>> §f";
    public boolean playingSound = false;

    public Color client_color = Color.PINK;
    private Theme.ThemeCategory themeCategory = Theme.ThemeCategory.JELLO;

    public ISound koksSound;

    public ModuleManager moduleManager;
    public ValueHUDManager valueHUDManager;
    public ValueManager valueManager;
    public CommonValueManager commonValueManager;
    public ThemeManager themeManager;
    public ClickGUI clickGUI;
    public PanelGUI panelGUI;
    public EventManager eventManager;
    public CommandManager commandManager;
    public FileManager fileManager;
    public ConfigScreen configScreen;
    public ConfigManager configManager;
    public CustomHUD customHUD;
    public TabGUI tabGUI;
    public DrawConfigManager drawConfigManager;
    public koks.gui.configsnew.ConfigManager configManagerFromScreen;

    public void initClient() {
        koksSound = PositionedSoundRecord.create(new ResourceLocation("koks.sound"));
        Display.setTitle(CLIENT_NAME + " v" + CLIENT_VERSION + " by " + CLIENT_DEVELOPER[0] + " | " + CLIENT_DEVELOPER[1] + " | " + CLIENT_DEVELOPER[2]);
        valueManager = new ValueManager();
        valueHUDManager = new ValueHUDManager();
        commonValueManager = new CommonValueManager();
        moduleManager = new ModuleManager();
        themeManager = new ThemeManager();
        clickGUI = new ClickGUI();
        panelGUI = new PanelGUI();
        commandManager = new CommandManager();
        eventManager = new EventManager();
        fileManager = new FileManager();
        configManager = new ConfigManager();
        if(!configManager.DIR.exists())configManager.DIR.mkdirs();
        configScreen = new ConfigScreen();
        customHUD = new CustomHUD();
        tabGUI = new TabGUI();
        drawConfigManager = new DrawConfigManager();
        configManagerFromScreen = new koks.gui.configsnew.ConfigManager();
        fileManager.createFiles();

    }

    public void shutdownClient() {
        fileManager.writeToAllFiles();
    }

    public void setThemeCategory(Theme.ThemeCategory themeCategory) {
        this.themeCategory = themeCategory;
    }

    public Theme.ThemeCategory getThemeCategory() {
        return themeCategory;
    }

}