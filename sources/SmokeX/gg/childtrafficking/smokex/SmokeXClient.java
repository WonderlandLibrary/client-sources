// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex;

import net.minecraft.client.Minecraft;
import wtf.eviate.protection.impl.UserCache;
import net.minecraft.src.Config;
import java.awt.Font;
import java.util.Objects;
import java.io.InputStream;
import java.io.IOException;
import gg.childtrafficking.smokex.module.modules.misc.KillInsultModule;
import gg.childtrafficking.smokex.gui.font.CFontRenderer;
import gg.childtrafficking.smokex.gui.ClickGUI;
import gg.childtrafficking.smokex.bind.BindListener;
import gg.childtrafficking.smokex.event.DefaultListener;
import gg.childtrafficking.smokex.event.Event;
import gg.childtrafficking.smokex.event.EventDispatcher;
import gg.childtrafficking.smokex.config.ConfigManager;
import gg.childtrafficking.smokex.bind.BindManager;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.person.PlayerManager;
import gg.childtrafficking.smokex.command.CommandManager;
import java.io.File;

public final class SmokeXClient
{
    private static volatile SmokeXClient INSTANCE;
    private static final File clientDirectory;
    private static final File fontDirectory;
    private final CommandManager commandManager;
    private final PlayerManager playerManager;
    private final ModuleManager moduleManager;
    private final BindManager bindManager;
    private final ConfigManager configManager;
    private final EventDispatcher<Event> eventDispatcher;
    private final DefaultListener defaultListener;
    private final BindListener bindListener;
    private final ClickGUI clickGUI;
    public CFontRenderer fontRenderer;
    public String font;
    
    private SmokeXClient() {
        this.commandManager = new CommandManager();
        this.playerManager = new PlayerManager();
        this.moduleManager = new ModuleManager();
        this.bindManager = new BindManager();
        this.configManager = new ConfigManager();
        this.eventDispatcher = new EventDispatcher<Event>();
        this.defaultListener = new DefaultListener();
        this.bindListener = new BindListener();
        this.clickGUI = ClickGUI.getInstance();
        this.font = "default";
        if (SmokeXClient.INSTANCE != null) {
            throw new RuntimeException("Another instance of this class currently exists!");
        }
    }
    
    public static SmokeXClient getInstance() {
        if (SmokeXClient.INSTANCE == null) {
            synchronized (SmokeXClient.class) {
                if (SmokeXClient.INSTANCE == null) {
                    SmokeXClient.INSTANCE = new SmokeXClient();
                }
            }
        }
        return SmokeXClient.INSTANCE;
    }
    
    public void init() {
        if (!SmokeXClient.clientDirectory.isDirectory()) {
            SmokeXClient.clientDirectory.mkdirs();
        }
        if (!SmokeXClient.fontDirectory.isDirectory()) {
            SmokeXClient.fontDirectory.mkdirs();
        }
        this.configManager.init();
        try {
            if (!KillInsultModule.KILLINSULTS_TXT.exists()) {
                KillInsultModule.KILLINSULTS_TXT.createNewFile();
            }
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        this.eventDispatcher.register(this.defaultListener);
        this.eventDispatcher.register(this.bindListener);
        this.eventDispatcher.register(this.clickGUI);
        try {
            getInstance().fontRenderer = new CFontRenderer(Font.createFont(0, Objects.requireNonNull(this.getClass().getResourceAsStream("/default.ttf"))).deriveFont(20.0f));
        }
        catch (final Exception e2) {
            Config.log(e2.toString());
        }
        this.moduleManager.init();
        if (!this.configManager.load("default")) {
            this.configManager.save("default");
        }
        UserCache.init();
    }
    
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
    
    public BindManager getBindManager() {
        return this.bindManager;
    }
    
    public CommandManager getCommandManager() {
        return this.commandManager;
    }
    
    public EventDispatcher<Event> getEventDispatcher() {
        return this.eventDispatcher;
    }
    
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }
    
    public File getClientDirectory() {
        return SmokeXClient.clientDirectory;
    }
    
    public File getFontDirectory() {
        return SmokeXClient.fontDirectory;
    }
    
    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    
    static {
        clientDirectory = new File(Minecraft.getMinecraft().mcDataDir, "smoke");
        fontDirectory = new File(SmokeXClient.clientDirectory, "fonts");
    }
}
