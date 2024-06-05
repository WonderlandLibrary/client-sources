package io.github.nevalackin.radium;

import com.thealtening.auth.service.AlteningServiceType;
import io.github.nevalackin.radium.alt.AltManager;
import io.github.nevalackin.radium.command.CommandManager;
import io.github.nevalackin.radium.config.ConfigManager;
import io.github.nevalackin.radium.event.Event;
import io.github.nevalackin.radium.gui.click.ClickGui;
import io.github.nevalackin.radium.keybind.BindSystem;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.notification.NotificationManager;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.bus.Bus;
import me.zane.basicbus.api.bus.BusImpl;

public final class RadiumClient {
    public static final String NAME = "Radium";
    public static final String VERSION = "v0.4";
    private static final RadiumClient INSTANCE = new RadiumClient();
    private Bus<Event> eventBus;
    private ModuleManager moduleManager;
    private ConfigManager configHandler;
    private NotificationManager notificationManager;
    private CommandManager commandManager;
    private AltManager altManager;

    public static RadiumClient getInstance() {
        return INSTANCE;
    }

    public AltManager getAltManager() {
        return altManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public ConfigManager getConfigHandler() {
        return configHandler;
    }

    public CommandManager getCommandHandler() {
        return commandManager;
    }

    public void onPostInit() {
        Wrapper.getFontRenderer().generateTextures();
        Wrapper.getNameTagFontRenderer().generateTextures();
        configHandler = new ConfigManager();
        altManager = new AltManager();
        eventBus = new BusImpl<>();
        notificationManager = new NotificationManager();
        commandManager = new CommandManager();
        moduleManager = new ModuleManager();
        eventBus.subscribe(new ClickGui());
        eventBus.subscribe(new BindSystem(moduleManager.getModules()));
        moduleManager.postInit();
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                altManager.getAlteningAuth().updateService(AlteningServiceType.MOJANG)));
    }

    public Bus<Event> getEventBus() {
        return eventBus;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

}
