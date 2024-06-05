/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.Display
 */
package drunkclient.beta;

import drunkclient.beta.API.EventBus;
import drunkclient.beta.API.GUI.notifications.NotificationManager;
import drunkclient.beta.IMPL.Configs.Configs;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.font.CFontRenderer;
import drunkclient.beta.IMPL.font.FontLoaders;
import drunkclient.beta.IMPL.managers.CommandManager;
import drunkclient.beta.IMPL.managers.FileManager;
import drunkclient.beta.IMPL.managers.ModuleManager;
import drunkclient.beta.IMPL.set.Value;
import java.io.File;
import java.util.Iterator;
import org.lwjgl.opengl.Display;
import viamcp.ViaMCP;

public class Client {
    public static boolean load;
    public String name = "Drunk Client";
    public final String version = "0002";
    public String edition = "BETA";
    public static boolean wasLogged;
    public static String ClientDesc;
    public File dir;
    public static Client instance;
    private CommandManager commandmanager;
    private ModuleManager MODULEmanager;
    private Configs ConfigManager;
    private NotificationManager notificationManager;
    CFontRenderer fe = FontLoaders.Comfortaa18;
    CFontRenderer fs = FontLoaders.Comfortaa20;
    CFontRenderer test2 = FontLoaders.Comfortaa22;
    CFontRenderer fss = FontLoaders.Comfortaa28;
    CFontRenderer test3 = FontLoaders.GoogleSans26;
    CFontRenderer fsmallbold = FontLoaders.GoogleSans28;
    CFontRenderer header = FontLoaders.NovICON38;
    CFontRenderer subHeader = FontLoaders.NovICON42;
    CFontRenderer verdana16 = FontLoaders.Comfortaa80;
    CFontRenderer test1 = FontLoaders.NovICON40;
    CFontRenderer verdana10 = FontLoaders.NovICON34;
    CFontRenderer nametagsFont = FontLoaders.NovICON38;

    public void startup() {
        load = false;
        this.commandmanager = new CommandManager();
        this.commandmanager.init();
        this.MODULEmanager = new ModuleManager();
        this.MODULEmanager.init();
        try {
            ViaMCP.getInstance().start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        FileManager.init();
        EventBus.getInstance().register(this);
        this.dir = new File(FileManager.dir + "/configs");
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
        load = true;
        Display.setTitle((String)(this.name + " | Release " + "0002"));
        FileManager.save("SpotifyRefreshToken.txt", "", false);
        FileManager.save("SpotifyAuthToken.txt", "", false);
        wasLogged = false;
    }

    public Client getInstance() {
        return instance;
    }

    public void shutDown() {
        String values = "";
        instance.getModuleManager();
        for (Module m : instance.getModuleManager().getModules()) {
            for (Value v : m.getValues()) {
                values = String.valueOf(values) + String.format("%s:%s:%s%s", m.getName(), v.getName(), v.getValue(), System.lineSeparator());
            }
        }
        FileManager.save("Values.txt", values, false);
        String enabld = "";
        instance.getModuleManager();
        Iterator<Module> iterator = instance.getModuleManager().getModules().iterator();
        while (true) {
            if (!iterator.hasNext()) {
                FileManager.save("Enabled.txt", enabld, false);
                return;
            }
            Module m = iterator.next();
            if (!m.isEnabled()) continue;
            enabld = String.valueOf(enabld) + String.format("%s%s", m.getName(), System.lineSeparator());
        }
    }

    public CommandManager getCommandManager() {
        return this.commandmanager;
    }

    public ModuleManager getModuleManager() {
        return this.MODULEmanager;
    }

    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }

    public Configs getConfigManager() {
        return this.ConfigManager;
    }

    static {
        wasLogged = false;
        ClientDesc = "";
        instance = new Client();
    }
}

