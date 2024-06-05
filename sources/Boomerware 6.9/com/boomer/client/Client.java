package com.boomer.client;

import com.boomer.client.command.CommandManager;
import com.boomer.client.config.ConfigManager;
import com.boomer.client.event.bus.EventBus;
import com.boomer.client.friend.FriendManager;
import com.boomer.client.gui.account.system.AccountManager;
import com.boomer.client.gui.notification.NotificationManager;
import com.boomer.client.macro.manager.MacroManager;
import com.boomer.client.module.ModuleManager;
import com.boomer.client.module.modules.visuals.hudcomps.HudCompManager;
import com.boomer.client.utils.autocheat.AutoCheatListener;
import com.boomer.client.utils.thealtening.AltService;
import com.boomer.client.utils.thealtening.utilities.SSLVerification;
import com.boomer.client.waypoint.WaypointManager;
import com.boomer.client.gui.AntiStrike;
import net.minecraft.client.Minecraft;

import java.io.File;

/**
 * made by oHare for Client
 *
 * @since 5/25/2019
 **/
public enum Client {
    INSTANCE;
    private File directory;
    private HudCompManager hudCompManager = new HudCompManager();
    private ModuleManager moduleManager = new ModuleManager();
    private CommandManager commandManager = new CommandManager();
    private SSLVerification sslVerification = new SSLVerification();
    private NotificationManager notificationManager = new NotificationManager();
    private AltService altService = new AltService();
    private FriendManager friendManager;
    private AccountManager accountManager;
    private MacroManager macroManager;
    private WaypointManager waypointManager;
    private ConfigManager configManager;
    private AntiStrike antistrike;
    private EventBus bus = new EventBus();
    public double version = 6.9;
    private boolean autoCheat;
    public void start(){
        directory = new File(Minecraft.getMinecraft().mcDataDir,"Boomer Client");
        friendManager = new FriendManager(directory);
        friendManager.getFriendSaving().loadFile();
        hudCompManager.setDirectory(new File(directory, "hudcomponents"));
        hudCompManager.initialize();
        hudCompManager.loadComps();
        moduleManager.setDirectory(new File(directory, "modules"));
        moduleManager.initialize();
        moduleManager.loadModules();
        commandManager.initialize();
        configManager = new ConfigManager(new File(directory, "configs"));
        configManager.load();
        sslVerification.verify();
        accountManager = new AccountManager(directory);

        antistrike = new AntiStrike(directory);
        waypointManager = new WaypointManager(directory);
        macroManager = new MacroManager(directory);
        macroManager.init();
        AutoCheatListener autoCheatListener = new AutoCheatListener();
        System.out.println("client main class loaded.");
    }


    public void end(){
        if (!directory.exists())
            directory.mkdir();
        moduleManager.saveModules();
        hudCompManager.saveComps();
        friendManager.getFriendSaving().saveFile();
        accountManager.save();

        antistrike.saveFile();
        macroManager.save();
    }

    public void switchToMojang() {
        try {
            altService.switchService(AltService.EnumAltService.MOJANG);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldnt switch to modank altservice");
        }
    }
    public void switchToTheAltening() {
        try {
            altService.switchService(AltService.EnumAltService.THEALTENING);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Couldnt switch to altening altservice");
        }
    }

    public boolean isAutoCheat() {
        return autoCheat;
    }

    public void setAutoCheat(boolean autoCheat) {
        this.autoCheat = autoCheat;
    }

    public File getDirectory() {
        return directory;
    }
    public NotificationManager getNotificationManager() {
        return notificationManager;
    }
    public AccountManager getAccountManager() {
        return accountManager;
    }
    public ModuleManager getModuleManager() {
        return moduleManager;
    }
    public CommandManager getCommandManager() {
        return this.commandManager;
    }
    public FriendManager getFriendManager() {
        return this.friendManager;
    }
    public HudCompManager getHudCompManager() {
        return hudCompManager;
    }
    public AltService getAltService() {
        return altService;
    }
    public SSLVerification getSSLVerification() {
        return sslVerification;
    }
    public AntiStrike getAntiStrike() {
        return antistrike;
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public WaypointManager getWaypointManager() {
        return this.waypointManager;
    }
    public MacroManager getMacroManager() {
        return macroManager;
    }
    public EventBus getBus() {
        return bus;
    }
}