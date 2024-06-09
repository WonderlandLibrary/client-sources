// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient;

import java.util.Date;
import java.text.SimpleDateFormat;
import xyz.niggfaclient.draggable.Dragging;
import xyz.niggfaclient.module.Module;
import xyz.niggfaclient.draggable.DragManager;
import org.lwjgl.opengl.Display;
import xyz.niggfaclient.keybind.BindSystem;
import xyz.niggfaclient.gui.click.ClickGui;
import net.minecraft.client.Minecraft;
import viamcp.ViaMCP;
import java.io.File;
import xyz.niggfaclient.events.Event;
import xyz.niggfaclient.eventbus.bus.impl.EventBus;
import xyz.niggfaclient.discord.DiscordRP;
import xyz.niggfaclient.config.ConfigManager;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.friend.FriendManager;
import xyz.niggfaclient.notifications.NotificationManager;

public class Client
{
    private static final Client INSTANCE;
    public String clientName;
    public String watermarkName;
    public String discordUsername;
    public String version;
    public boolean authorized;
    private NotificationManager notificationManager;
    private FriendManager friendManager;
    private ModuleManager moduleManager;
    private ConfigManager configManager;
    private DiscordRP discordRP;
    private EventBus<Event> eventBus;
    public File folder;
    
    public Client() {
        this.clientName = "Lyrium";
        this.watermarkName = "Lyrium";
        this.discordUsername = "";
        this.version = "1.0";
    }
    
    public static Client getInstance() {
        return Client.INSTANCE;
    }
    
    public FriendManager getFriendManager() {
        return this.friendManager;
    }
    
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
    
    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    
    public void start() {
        try {
            (this.discordRP = new DiscordRP()).start();
        }
        catch (Exception e) {
            System.out.print("Cannot initialize DiscordRP");
        }
        try {
            ViaMCP.getInstance().start();
            ViaMCP.getInstance().initAsyncSlider();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.folder = new File(Minecraft.getMinecraft().mcDataDir, this.clientName);
        if (!this.folder.exists()) {
            this.folder.mkdir();
        }
        this.eventBus = new EventBus<Event>();
        this.configManager = new ConfigManager();
        this.notificationManager = new NotificationManager();
        this.friendManager = new FriendManager(this.folder);
        this.moduleManager = new ModuleManager();
        this.eventBus.subscribe(new ClickGui());
        this.eventBus.subscribe(new BindSystem(this.moduleManager.getModules()));
        this.friendManager.getFriendSaving().loadFile();
        this.moduleManager.postInit();
        Display.setTitle(this.clientName + " " + this.version + " | Private Build");
        DragManager.loadDragData();
    }
    
    public void shutdown() {
        this.friendManager.getFriendSaving().saveFile();
        this.eventBus.unsubscribe(this);
        DragManager.saveDragData();
    }
    
    public Dragging createDraggable(final Module module, final String name, final float x, final float y) {
        DragManager.draggables.put(name, new Dragging(module, name, x, y));
        return DragManager.draggables.get(name);
    }
    
    public EventBus<Event> getEventBus() {
        return this.eventBus;
    }
    
    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }
    
    public DiscordRP getDiscordRP() {
        return this.discordRP;
    }
    
    public String getBuildDate() {
        return new SimpleDateFormat("MMddyy").format(new Date());
    }
    
    public void setAuthorized(final boolean authorized) {
        this.authorized = authorized;
    }
    
    static {
        INSTANCE = new Client();
    }
}
