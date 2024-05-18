package pw.latematt.xiv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pw.latematt.timer.Timer;
import pw.latematt.timer.convert.TimeConverter;
import pw.latematt.xiv.management.managers.*;
import pw.latematt.xiv.ui.clickgui.GuiClick;
import pw.latematt.xiv.ui.notifications.NotificationsHandler;
import pw.latematt.xiv.ui.tabgui.GuiTabHandler;

/**
 * @author Matthew
 */
public class XIV {
    /* static instance of main class */
    private static final XIV instance = new XIV();

    public static XIV getInstance() {
        return instance;
    }

    /* Management */
    private final FileManager fileManager = new FileManager();
    private final ModManager modManager = new ModManager();
    private final AdminManager adminManager = new AdminManager();
    private final AltManager altManager = new AltManager();
    private final CommandManager commandManager = new CommandManager();
    private final ListenerManager listenerManager = new ListenerManager();
    private final FriendManager friendManager = new FriendManager();
    private final ValueManager valueManager = new ValueManager();
    private final ConfigManager configManager = new ConfigManager();
    private final MacroManager macroManager = new MacroManager();
    private final GuiClick guiClick = new GuiClick();
    private final GuiTabHandler tabHandler = new GuiTabHandler();
    private final NotificationsHandler notificationsHandler = new NotificationsHandler();

    public FileManager getFileManager() {
        return fileManager;
    }

    public ModManager getModManager() {
        return modManager;
    }

    public AdminManager getAdminManager() {
        return adminManager;
    }

    public AltManager getAltManager() {
        return altManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public ValueManager getValueManager() {
        return valueManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MacroManager getMacroManager() {
        return macroManager;
    }

    public GuiClick getGuiClick() {
        return guiClick;
    }

    public GuiTabHandler getTabHandler() {
        return tabHandler;
    }

    public NotificationsHandler getNotificationsHandler() {
        return notificationsHandler;
    }

    /* logger */
    private final Logger logger = LogManager.getLogger();

    public Logger getLogger() {
        return logger;
    }

    /* sets up the client base (called in Minecraft#run() at line 402) */
    public void setup() {
        Timer timer = new Timer();
        logger.info("-> Begin XIV setup");
        /* call setup on all managers */
        /* the order that these are called in is important, do not change! */
        /* setup is not required with all managers */

        fileManager.setup();
        commandManager.setup();
        listenerManager.setup();
        modManager.setup();
        tabHandler.setup();
        friendManager.setup();
        adminManager.setup();
        altManager.setup();
        configManager.setup();
        macroManager.setup();

        /* file stuffs on startup */
        fileManager.loadAllFiles();
        fileManager.saveAllFiles();

        /* file stuffs on shutdown */
        Runtime.getRuntime().addShutdownHook(new Thread("XIV Shutdown Thread") {
            public void run() {
                fileManager.saveAllFiles();
            }
        });

        logger.info(String.format("-> End XIV setup (Took %s seconds)", new TimeConverter().fromMilliseconds(timer.getDifference(), TimeConverter.SECOND)));
    }
}