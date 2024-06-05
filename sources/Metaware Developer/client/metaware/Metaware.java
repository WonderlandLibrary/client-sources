package client.metaware;

import client.metaware.api.account.AccountManager;
import client.metaware.api.config.Config;
import client.metaware.api.config.settings.SettingsLoader;
import client.metaware.api.event.painfulniggerrapist.bus.impl.EventBus;
import client.metaware.api.gui.notis.NotificationManager;
import client.metaware.api.shader.implementations.BlurShader;
import client.metaware.api.utils.MinecraftUtil;
import client.metaware.client.ClientInfo;
import client.metaware.impl.event.Event;
import client.metaware.impl.managers.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.Display;
import viamcp.ViaMCP;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum Metaware implements MinecraftUtil {
    INSTANCE;

    private final Path DIRECTORY = Paths.get(Minecraft.getMinecraft().mcDataDir.getAbsolutePath(), "MeTaWaRe");
    private final ClientInfo clientInfo = new ClientInfo("Metaware", "Developer", "Jinthium & RussianE", ".", EnumChatFormatting.GRAY + "[" + EnumChatFormatting.BLUE   + "Metaware" + EnumChatFormatting.GRAY + "]" + EnumChatFormatting.WHITE + " ");
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final NotificationManager notificationManager = new NotificationManager();
    private final SettingsLoader settingsLoader = new SettingsLoader();
    private final AccountManager accountManager = new AccountManager();
    private final CommandManager commandManager = new CommandManager();
    private final ConfigManager configManager = new ConfigManager();
    private final ModuleManager moduleManager = new ModuleManager();
    private final EventBus<Event> eventBus = new EventBus<>();
    private final BindManager bindManager = new BindManager();
    private final FontManager fontManager = new FontManager();
    private BlurShader blurShader;
    private String uid = "";
    private String user = "";
    private Config lastConfig;

    public void startClient(){
        Display.setTitle(clientInfo.getClientTitle());
        eventBus.subscribe(this);
        try{
            ViaMCP.getInstance().start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        accountManager.init();
        configManager.init();
        commandManager.init();
        settingsLoader.load();
        bindManager.init();
        fontManager.init();
        moduleManager.init();
        if(lastConfig != null){
            configManager.load(lastConfig);
        }
    }

    public void shutdownClient(){
        eventBus.unsubscribe(this);
        settingsLoader.save();
        if(lastConfig == null && configManager.currentConfig() != null)
            lastConfig = configManager.currentConfig();
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }
    public CommandManager getCommandManager() {
        return commandManager;
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public Path getDirectory() {
        return DIRECTORY;
    }
    public AccountManager getAccountManager() {
        return accountManager;
    }
    public ExecutorService getExecutorService() {
        return executorService;
    }
    public ModuleManager getModuleManager() {
        return moduleManager;
    }
    public FontManager getFontManager() {
        return fontManager;
    }
    public ClientInfo getClientInfo() {
        return clientInfo;
    }
    public BindManager getBindManager() {
        return bindManager;
    }
    public EventBus<Event> getEventBus() {
        return eventBus;
    }
    public void setUser(String user){
        this.user = user;
    }
    public void setUID(String uid) {
        this.uid = uid;
    }
    public String getUID() {
        return uid;
    }
    public String getUser() {
        return user;
    }
    public BlurShader getBlurShader(int radius){
        if(blurShader == null){
            blurShader = new BlurShader(radius);
        }
        if(blurShader.radius() != radius){
            blurShader.radius(radius);
        }
        return blurShader;
    }
}
