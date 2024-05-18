// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft;

import java.awt.Color;
import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.net.URL;
import org.darkstorm.minecraft.gui.GuiManager;
import org.darkstorm.minecraft.gui.theme.Theme;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.BoolOption;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;

import net.minecraft.client.network.badlion.UI.GuiKeybindMgr;
import net.minecraft.client.network.badlion.Utils.FileManager;
import net.minecraft.client.network.badlion.UI.ClickGui;
import net.minecraft.client.network.badlion.Utils.CmdMgr;
import net.minecraft.client.network.badlion.Utils.FriendUtils;
import net.minecraft.client.network.badlion.Mod.Mods;

public class Badlion
{
    private static Badlion theClient;
    public static boolean allowed;
    public Mods theMods;
    public FriendUtils friendUtils;
    public CmdMgr theCmds;
    private ClickGui mememan;
    public FileManager fileManager;
    private GuiKeybindMgr guiKeybindMgr;
    private GuiManagerDisplayScreen guiKeybindDisplay;
    private GuiManagerDisplayScreen guiDisplay;
    public BoolOption bhop;
    public BoolOption legit;
    private EventManager events;
    private static float hue;
    
    static {
        Badlion.allowed = true;
        Badlion.theClient = new Badlion();
        Badlion.hue = 0.0f;
    }
    
    public static Badlion getWinter() {
        return Badlion.theClient;
    }
    
    public ClickGui getGuiMgr() {
        if (this.mememan == null) {
            (this.mememan = new ClickGui()).setTheme(new SimpleTheme());
            this.getGuiMgr().setup();
        }
        return this.mememan;
    }
    
    public GuiManagerDisplayScreen getGui() {
        if (this.guiDisplay == null) {
            this.guiDisplay = new GuiManagerDisplayScreen(this.getGuiMgr());
        }
        return this.guiDisplay;
    }
    
    public GuiKeybindMgr getKeybindGuiMgr() {
        if (this.guiKeybindMgr == null) {
            (this.guiKeybindMgr = new GuiKeybindMgr()).setTheme(new SimpleTheme());
            this.guiKeybindMgr.setup();
        }
        return this.guiKeybindMgr;
    }
    
    public GuiManagerDisplayScreen getKeybindGui() {
        if (this.guiKeybindDisplay == null) {
            this.guiKeybindDisplay = new GuiManagerDisplayScreen(this.getKeybindGuiMgr());
        }
        return this.guiKeybindDisplay;
    }
    
    public void start() {
        this.legit = new BoolOption("Hud");
        this.bhop = new BoolOption("Bhop");
        this.theMods = new Mods();
        this.friendUtils = new FriendUtils();
        this.fileManager = new FileManager();
        this.theCmds = new CmdMgr();
        this.fileManager.loadFiles();
    }
    
    public static float getHue() {
        return Badlion.hue;
    }
    
    public static int getRainbow() {
        return Color.HSBtoRGB(0.39215687f, 1.0f, 1.0f);
    }
    
    public EventManager getEventManager() {
        return this.events;
    }
}
