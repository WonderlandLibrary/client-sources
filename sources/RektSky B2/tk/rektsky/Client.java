package tk.rektsky;

import net.minecraft.client.*;
import tk.rektsky.ui.*;
import java.util.concurrent.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.*;
import tk.rektsky.Module.Render.*;
import tk.rektsky.Guis.*;
import tk.rektsky.Module.*;
import tk.rektsky.Commands.*;
import tk.rektsky.Files.*;
import tk.rektsky.Main.*;

public class Client
{
    private Minecraft mc;
    public static HUD hud;
    public CopyOnWriteArrayList<Module> modules;
    public static boolean finishedSetup;
    public static final Boolean DEBUG;
    public static final String CLIENT_NAME = "RektSky Client";
    public static final String VERSION = "B2";
    public static final String AUTHORS = "hackage & fan87";
    public static final String CHANNEL = "Public Beta";
    public static final String NAME = "RektSky Client B2  |  Public Beta";
    public static String userName;
    public static String role;
    
    public Client() {
        this.mc = Minecraft.getMinecraft();
        this.modules = new CopyOnWriteArrayList<Module>();
    }
    
    public static void addClientChat(final IChatComponent msg) {
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) {
            return;
        }
        Minecraft.getMinecraft().thePlayer.sendMessage(new ChatComponentText(ChatFormatting.GREEN + "[" + "RektSky Client" + "] ").appendSibling(msg));
    }
    
    public static void addClientChat(final String msg) {
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) {
            return;
        }
        Minecraft.getMinecraft().thePlayer.sendMessage(new ChatComponentText(ChatFormatting.GREEN + "[" + "RektSky Client" + "] ").appendSibling(new ChatComponentText(msg)));
    }
    
    public static void notify(final Notification.PopupMessage message) {
        Notification.displayNotification(message);
    }
    
    public static void notifyWithClassName(final Notification.PopupMessage message) {
        notify(message);
    }
    
    public static UnicodeFontRenderer getFont() {
        return new UnicodeFontRenderer("Comfortaa", 0, 18.0f, 0.0f, 1.0f);
    }
    
    public static UnicodeFontRenderer getFontBig() {
        return new UnicodeFontRenderer("Comfortaa", 0, 24.0f, 0.0f, 1.0f);
    }
    
    public static UnicodeFontRenderer getHDFont() {
        return new UnicodeFontRenderer("Comfortaa", 0, 18.0f, 0.0f, 4.0f);
    }
    
    public static UnicodeFontRenderer getFontWithSize(final int fontSize) {
        return new UnicodeFontRenderer("Comfortaa", 0, (float)fontSize, 0.0f, 1.0f);
    }
    
    public void init() {
        System.out.println("Welcome to RektSky Client B2  |  Public Beta by hackage & fan87.");
        Client.hud = new HUD();
        ModulesManager.inti();
        CommandsManager.init();
        this.mc.gameSettings.showInventoryAchievementHint = false;
        System.out.println("RektSky Client B2  |  Public Beta Started!");
        FileManager.init();
        Client.finishedSetup = true;
    }
    
    static {
        Client.finishedSetup = false;
        DEBUG = false;
        Client.userName = Auth.userName;
        Client.role = Auth.role;
    }
}
