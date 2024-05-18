package com.klintos.twelve;

import java.io.IOException;
import java.net.MalformedURLException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import com.klintos.twelve.gui.TwelveChat;
import com.klintos.twelve.gui.click.GuiClick;
import com.klintos.twelve.gui.newclick.ClickGui;

import java.util.Iterator;
import com.klintos.twelve.mod.Mod;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.Minecraft;
import com.klintos.twelve.utils.FileUtils;
import org.lwjgl.opengl.Display;
import java.awt.Font;
import com.klintos.twelve.utils.NahrFont;
import jaco.mp3.player.MP3Player;
import com.klintos.twelve.gui.TabGui;
import com.klintos.twelve.handlers.friend.FriendHandler;
import com.klintos.twelve.handlers.notifications.NotificationHandler;
import com.klintos.twelve.handlers.KeybindsHandler;
import com.klintos.twelve.handlers.CmdHandler;
import com.klintos.twelve.handlers.ModHandler;

public class Twelve
{
    public static Twelve clientInstance;
    private ModHandler modHandler;
    private CmdHandler cmdHandler;
    private KeybindsHandler keybindHandler;
    private static NotificationHandler notificationHandler;
    private static FriendHandler friendHandler;
    private TabGui tabGui;
    private static GuiClick gui2;
    private static ClickGui gui;
    public boolean ghost;
    public MP3Player player;
    private int build;
    public boolean outdated;
    public NahrFont guiFont;
    private String prefix;
    
    public Twelve() {
        this.build = 1;
        this.guiFont = new NahrFont(new Font("Ubuntu", 1, 16), true, false);
        this.prefix = "";
        Display.setTitle("ONE2");
        Twelve.clientInstance = this;
        this.outdated = this.isOutdated();
        this.cmdHandler = new CmdHandler();
        this.modHandler = new ModHandler();
        this.keybindHandler = new KeybindsHandler();
        Twelve.notificationHandler = new NotificationHandler();
        Twelve.friendHandler = new FriendHandler();
        this.tabGui = new TabGui();
        this.player = new MP3Player();
        Twelve.gui2 = new GuiClick();
        Twelve.gui = new ClickGui();
        FileUtils.init();
    }
    
    public static Twelve getInstance() {
        if (Twelve.clientInstance == null) {
            Twelve.clientInstance = new Twelve();
        }
        return Twelve.clientInstance;
    }
    
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
    
    public ModHandler getModHandler() {
        if (this.modHandler == null) {
            this.modHandler = new ModHandler();
        }
        return this.modHandler;
    }
    
    public CmdHandler getCmdHandler() {
        if (this.cmdHandler == null) {
            this.cmdHandler = new CmdHandler();
        }
        return this.cmdHandler;
    }
    
    public KeybindsHandler getKeybindsHandler() {
        if (this.keybindHandler == null) {
            this.keybindHandler = new KeybindsHandler();
        }
        return this.keybindHandler;
    }
    
    public NotificationHandler getNotificationHandler() {
        if (Twelve.notificationHandler == null) {
            Twelve.notificationHandler = new NotificationHandler();
        }
        return Twelve.notificationHandler;
    }
    
    public FriendHandler getFriendHandler() {
        if (Twelve.friendHandler == null) {
            Twelve.friendHandler = new FriendHandler();
        }
        return Twelve.friendHandler;
    }
    
    public TabGui getTabGui() {
        if (this.tabGui == null) {
            this.tabGui = new TabGui();
        }
        return this.tabGui;
    }
    
    public GuiClick getClickGui2() {
        if (Twelve.gui2 == null) {
            Twelve.gui2 = new GuiClick();
        }
        return Twelve.gui2;
    }
    
    public ClickGui getClickGui() {
        if (Twelve.gui == null) {
            Twelve.gui = new ClickGui();
        }
        return Twelve.gui;
    }
    
    public MP3Player getMP3Player() {
        if (this.player == null) {
            this.player = new MP3Player();
        }
        return this.player;
    }
    
    public void ghostClient() {
        this.ghost = true;
        Display.setTitle("Minecraft 1.8");
        this.prefix = this.getCmdHandler().getPrefix();
        this.getCmdHandler().setPrefix("§");
        getMinecraft().ingameGUI.persistantChatGUI.clearChatMessages();
        getMinecraft().ingameGUI.persistantChatGUI = new GuiNewChat(getMinecraft());
        for (final Mod mod : this.getModHandler().getMods()) {
            if (mod.getEnabled()) {
                mod.onToggle();
            }
        }
    }
    
    public void unGhostClient() {
        this.ghost = false;
        Display.setTitle("ONE2");
        this.getCmdHandler().setPrefix(this.prefix);
        getMinecraft().ingameGUI.persistantChatGUI = new TwelveChat(getMinecraft());
    }
    
    public boolean isOutdated() {
        try {
            final URL url = new URL("https://www.dropbox.com/s/7e5lyllg0ntvqmt/ONE2%20Build.txt?raw=1");
            final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = br.readLine()) != null) {
                if (Integer.parseInt(line) > this.build) {
                    return true;
                }
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        catch (Exception e3) {
            e3.printStackTrace();
        }
        return false;
    }
}
