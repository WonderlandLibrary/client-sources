package dev.client.tenacity;

import dev.client.tenacity.config.ConfigManager;
import dev.client.tenacity.config.DragManager;
import dev.client.tenacity.ui.altmanager.GuiAltManager;
import dev.client.tenacity.ui.altmanager.AltPanels;
import dev.client.tenacity.ui.altmanager.helpers.KingGenApi;
import dev.client.tenacity.utils.misc.DiscordRP;
import dev.client.tenacity.utils.objects.Dragging;
import dev.event.Event;
import dev.event.EventProtocol;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.ModuleCollection;
import dev.client.tenacity.ui.notifications.NotificationManager;
import dev.client.tenacity.ui.sidegui.SideGui;
import dev.utils.Utils;
import dev.client.tenacity.utils.client.ReleaseType;
import net.minecraft.command.CommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum Tenacity implements Utils {

    INSTANCE;

    public static final String NAME = "Tenaskid";
    public static final String VERSION = "4.0";
    public static final ReleaseType RELEASE = ReleaseType.BETA;
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final File DIRECTORY = new File(mc.mcDataDir, "Tenacity");


    private final EventProtocol<Event> eventProtocol = new EventProtocol<>();
    private final NotificationManager notificationManager = new NotificationManager();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final SideGui sideGui = new SideGui();
    private ModuleCollection moduleCollection;
    private ConfigManager configManager;
    private GuiAltManager altManager;
    private CommandHandler commandHandler;
    private DiscordRP discordRP;
    public final AltPanels altPanels = new AltPanels();
    public KingGenApi kingGenApi;

    public void init() {
        this.moduleCollection = new ModuleCollection();
        this.configManager = new ConfigManager();
        this.altManager = new GuiAltManager();

    }

    public String getVersion() {
        return VERSION + (RELEASE != ReleaseType.PUBLIC ? " (" + RELEASE.getName() + ")" : "");
    }

    public final Color getClientColor() {
        return new Color(236, 133, 209);
    }

    public final Color getAlternateClientColor() {
        return new Color(28, 167, 222);
    }

    public SideGui getSideGui() {
        return sideGui;
    }

    public EventProtocol<Event> getEventProtocol() {
        return eventProtocol;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public CommandHandler getCommandHandler() { return commandHandler; }

    public GuiAltManager getAltManager() { return altManager; }

    public ModuleCollection getModuleCollection() {
        return moduleCollection;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ExecutorService getExecutorService() { return executorService; }

    public void setModuleCollection(ModuleCollection moduleCollection) { this.moduleCollection = moduleCollection; }

    public void setConfigManager(ConfigManager configManager) { this.configManager = configManager; }

    public void setAltManager(GuiAltManager altManager) { this.altManager = altManager; }

    public void setCommandHandler(CommandHandler commandHandler) { this.commandHandler = commandHandler; }

    public void setDiscordRP(DiscordRP discordRP) { this.discordRP = discordRP; }


    public boolean isToggled(Class<? extends Module> c) {
        Module m = INSTANCE.moduleCollection.get(c);
        return m != null && m.isToggled();
    }

    public Dragging createDrag(Module module, String name, float x, float y) {
        DragManager.draggables.put(name, new Dragging(module, name, x, y));
        return DragManager.draggables.get(name);
    }

}
