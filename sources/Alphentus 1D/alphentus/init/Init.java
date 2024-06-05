package alphentus.init;

import alphentus.config.ConfigManager;
import alphentus.file.FileSystem;
import alphentus.gui.altmanager.AltManager;
import alphentus.gui.clickgui.ClickGUI;
import alphentus.gui.customhud.CustomHUD;
import alphentus.gui.customhud.dragging.DraggingHUD;
import alphentus.gui.customhud.dragging.DraggingUtil;
import alphentus.gui.customhud.settings.settings.SetValues;
import alphentus.gui.customhud.settings.settings.ValueManager;
import alphentus.mod.ModManager;
import alphentus.settings.SettingManager;
import alphentus.utils.BlurUtil;
import alphentus.utils.FriendSystem;
import alphentus.utils.TabGUIBlur;
import alphentus.utils.fontrenderer.FontManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;


import java.awt.*;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class Init {

    private static Init instance = new Init();

    public final String CLIENT_NAME = "Alphentus";
    public final String[] CLIENT_AUTHORS = {"lmao", "avox"};
    public final Double CLIENT_VERSION = 1D;
    public final Color CLIENT_COLOR = new Color(41, 166, 255, 255);

    public ModManager modManager;
    public SettingManager settingManager;

    public ValueManager valueManager;
    public SetValues setValues;
    public CustomHUD customHUD;
    public DraggingHUD draggingHUD;
    public DraggingUtil draggingUtil;

    public ClickGUI clickGUI;
    public alphentus.gui.clickguipanel.ClickGUI panelClickGUI;
    public alphentus.gui.clickguisigma.ClickGUI clickGUISigma;

    public FileSystem fileSystem;

    public BlurUtil blurUtil;
    public TabGUIBlur tabGUIBlur;

    public FontManager fontManager;

    public AltManager altManager;

    public ConfigManager configManager;

    public FriendSystem friendSystem;

    public void initClient() {
        Display.setTitle(CLIENT_NAME + " by " + CLIENT_AUTHORS[0] + " | " + CLIENT_AUTHORS[1]);
        Minecraft.getMinecraft().gameSettings.guiScale = 2;
        Minecraft.getMinecraft().gameSettings.ofFastRender = false;

        this.friendSystem = new FriendSystem();

        this.fontManager = new FontManager();
        this.settingManager = new SettingManager();
        this.modManager = new ModManager();

        this.valueManager = new ValueManager();
        this.setValues = new SetValues();
        this.customHUD = new CustomHUD();

        this.blurUtil = new BlurUtil();
        this.tabGUIBlur = new TabGUIBlur();

        this.clickGUI = new ClickGUI();
        this.panelClickGUI = new alphentus.gui.clickguipanel.ClickGUI();
        this.clickGUISigma = new alphentus.gui.clickguisigma.ClickGUI();

        this.altManager = new AltManager();

        this.draggingHUD = new DraggingHUD();
        this.draggingUtil = new DraggingUtil();

        this.configManager = new ConfigManager();

        this.fileSystem = new FileSystem();


    }

    public static Init getInstance() {
        return instance;
    }
}
