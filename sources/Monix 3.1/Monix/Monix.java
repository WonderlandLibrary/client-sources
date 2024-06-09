/*
 * Decompiled with CFR 0_122.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 */
package Monix;

import Monix.Alts.AltManager;
import Monix.Commands.CommandManager;
import Monix.Event.EventManager;
import Monix.Files.FileManager;
import Monix.Friends.FriendManager;
import Monix.Mod.Mod;
import Monix.Mod.ModManager;
import Monix.Mod.mods.Hud;
import Monix.UI.Font.FontManager;
import Monix.UI.tab.TabGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.Display;

public class Monix {
    public static final Monix instance = new Monix();
    public static Monix Plux = new Monix();
    public static ModManager modManager = new ModManager();
    public static FontManager fontManager = new FontManager();
    public static Hud HUD = new Hud();
    public static final String Name = "Monix";
    public static final String Version = "3.1";
    public static String prefix = "-";
    public static TabGui tabGui;
    public static AltManager altManager;
    public static FileManager.CustomFile customFile;
    public static FileManager fileManager;
    public static FriendManager friendManager;
    public static CommandManager cmdManager;
    public static final String tag = "\u00a77[\u00a75Monix\u00a77]\u00a77 ";

    public void start() {
        Display.setTitle("Monix");
        EventManager.register(this);
        fontManager.loadFonts();
        cmdManager = new CommandManager();
        fileManager = new FileManager();
        friendManager = new FriendManager();
        fileManager.loadFiles();
        tabGui = new TabGui();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.end();
        }
        ));
    }

    public void end() {
    }

    public static Monix getInstance() {
        return Plux;
    }

    public static void addChatMessage(String s) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a75Monix\u00a77]\u00a77 " + s));
    }

    public static boolean onSendChatMessage(String s) {
        if (s.startsWith(prefix)) {
            cmdManager.callCommand(s.substring(1));
            return false;
        }
        for (Mod m : ModManager.getMods()) {
            if (!m.isToggled()) continue;
            return m.onSendChatMessage(s);
        }
        return true;
    }

    public static boolean onReciveChatMessage(S02PacketChat packet) {
        for (Mod m : ModManager.getMods()) {
            if (!m.isToggled()) continue;
            return m.onReciveChatMessage(packet);
        }
        return true;
    }
}

