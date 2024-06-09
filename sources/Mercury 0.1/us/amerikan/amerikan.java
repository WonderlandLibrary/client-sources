/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.SettingsManager;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import us.amerikan.command.CommandManager;
import us.amerikan.manager.FriendManager;
import us.amerikan.modules.ModuleManager;
import us.amerikan.utils.Logger;
import us.amerikan.utils.file.files.FileManager;

public class amerikan {
    public static amerikan instance;
    public static String Client_Name;
    public double Client_Version = 0.1;
    public String Client_Coder = "Hizzy, Garpex & SnowCode";
    public String Client_Prefix = "[\u00a74Mercury\u00a7f]";
    public String Chat_Prefix = ".";
    public Logger logger;
    public static CommandManager commandManager;
    public static ModuleManager modulemanager;
    public static SettingsManager setmgr;
    public static FileManager filemgr;
    public static FriendManager friendmgr;
    public ClickGUI clickgui;
    public boolean Packet;
    public boolean FakeFPS;
    public File directory;

    static {
        Client_Name = "Mercury";
    }

    public void startClient() throws IOException {
        setmgr = new SettingsManager();
        this.FakeFPS = false;
        this.Packet = false;
        instance = this;
        Display.setTitle(String.valueOf(Client_Name) + " " + this.Client_Version);
        System.out.println(String.valueOf(Client_Name) + this.Client_Version + " is ready");
        this.directory = new File(Minecraft.getMinecraft().mcDataDir, "SpookyMan");
        if (!this.directory.exists()) {
            this.directory.mkdir();
        }
        this.logger = new Logger();
        modulemanager = new ModuleManager();
        commandManager = new CommandManager();
        this.clickgui = new ClickGUI();
        filemgr = new FileManager();
        friendmgr = new FriendManager();
        filemgr.Initialization();
    }
}

