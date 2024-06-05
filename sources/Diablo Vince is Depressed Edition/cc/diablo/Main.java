/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.EventBus
 *  com.google.common.eventbus.Subscribe
 *  org.lwjgl.opengl.Display
 *  store.intent.intentguard.annotation.Bootstrap
 *  store.intent.intentguard.annotation.Native
 */
package cc.diablo;

import cc.diablo.clickgui.ClickGUI;
import cc.diablo.command.CommandManager;
import cc.diablo.event.impl.KeycodeEvent;
import cc.diablo.font.FontManager;
import cc.diablo.font.TTFFontRenderer;
import cc.diablo.helpers.DiscordRPCHelper;
import cc.diablo.manager.file.FileManager;
import cc.diablo.manager.friend.FriendManager;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Module;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import org.lwjgl.opengl.Display;
import store.intent.intentguard.annotation.Bootstrap;
import store.intent.intentguard.annotation.Native;

public class Main {
    public static long timestamp = System.currentTimeMillis() / 1000L;
    private static final Main Instance = new Main();
    public static String name;
    public static String customName;
    public static String authors;
    public static String version;
    public static String serverVersion;
    public static String buildType;
    public static Color clientColor;
    public static String username;
    public static String uid;
    public static File fileDir;
    private EventBus eventBus = new EventBus("Diablo");
    private FileManager fileManager;
    private ModuleManager moduleManager;
    private FontManager fontManager;
    private ClickGUI clickGUI;
    private CommandManager commandManager;
    private FriendManager friendManager;

    @Native
    @Bootstrap
    public static void StartClient() throws IOException {
        Main.initClientVisuals();
        Main.initClientVars();
        Main.sendDiabloPrint("Launched Diablo, Running On Build " + version);
    }

    @Native
    public void setupEvents() {
        this.friendManager = new FriendManager();
        clientColor = new Color(80, 24, 116);
        this.eventBus = new EventBus(name);
        this.moduleManager = new ModuleManager();
        this.fontManager = new FontManager();
        this.clickGUI = new ClickGUI();
        fileDir = new File(Minecraft.getMinecraft().mcDataDir + name);
        this.fileManager = new FileManager();
        this.commandManager = new CommandManager();
        this.fileManager.loadFiles();
        this.eventBus.register((Object)this);
    }

    @Native
    public static void initClientVars() {
        name = "Diablo";
        customName = "Diablo";
        authors = "Vince, muffin, reset and matt2";
        version = "Vince is Depressed Edition";
        buildType = "Beta";
    }

    @Native
    public static void initClientVisuals() {
        try {
            DiscordRPCHelper.updateRPC();
            InputStream inputStream = Config.getResourceStream(new ResourceLocation("Client/images/logo16.png"));
            InputStream inputStream1 = Config.getResourceStream(new ResourceLocation("Client/images/logo32.png"));
            Display.setIcon((ByteBuffer[])new ByteBuffer[]{Config.readIconImage(inputStream), Config.readIconImage(inputStream1)});
            if (version == null) {
                Display.setTitle((String)"Loading Diablo...");
            } else {
                Display.setTitle((String)("Diablo " + version));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String readUrl(String urlString) throws Exception {
        try (BufferedReader reader = null;){
            int read;
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            String string = buffer.toString();
            return string;
        }
    }

    public static Main getInstance() {
        return Instance;
    }

    public static void sendDiabloPrint(String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm:ss");
        System.out.println("[" + dtf.format(LocalDateTime.now()) + "] [Diablo] " + message);
    }

    @Subscribe
    public void onKeyCodePressed(KeycodeEvent e) {
        ModuleManager.getModules().stream().filter(module -> module.getKey() == e.getKey()).forEach(Module::toggle);
    }

    public TTFFontRenderer getSFUI(int size) {
        return this.fontManager.getFont("sfui " + size);
    }

    public TTFFontRenderer getSFPRO(int size) {
        return this.fontManager.getFont("sfpro " + size);
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public FontManager getFontManager() {
        return this.fontManager;
    }

    public ClickGUI getClickGUI() {
        return this.clickGUI;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public FriendManager getFriendManager() {
        return this.friendManager;
    }
}

