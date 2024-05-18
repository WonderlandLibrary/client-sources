package us.dev.direkt;

import java.io.File;

import org.lwjgl.opengl.Display;

import us.dev.direkt.command.handler.CommandManager;
import us.dev.direkt.event.Event;
import us.dev.direkt.event.internal.events.system.EventOnStartup;
import us.dev.direkt.file.handler.FileManager;
import us.dev.direkt.gui.accounts.handler.AccountManager;
import us.dev.direkt.keybind.handler.KeybindManager;
import us.dev.direkt.module.handler.ModuleManager;
import us.dev.direkt.module.internal.core.friends.handler.FriendManager;
import us.dev.direkt.module.internal.render.waypoints.handler.WaypointManager;
import us.dev.dvent.DventBus;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

/**
 * @author Foundry
 */
public enum Direkt {
    INSTANCE;

    private static final String CLIENT_NAME = "Direkt";
    private static final String CLIENT_VERSION = "v1.2";
    private static final String DISPLAY_TITLE = String.format("Minecraft 1.10.2 | %s %s", Direkt.CLIENT_NAME, Direkt.CLIENT_VERSION);
    private final DventBus<? super Event> eventManager = new DventBus<>(CLIENT_NAME + " Event Manager");
    private KeybindManager keybindManager;
    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private FileManager fileManager;
    private AccountManager accountManager = new AccountManager();
    private FriendManager friendManager = new FriendManager();
    private WaypointManager waypointManager = new WaypointManager();
    private File clientDirectory;

    Direkt() {
        this.clientDirectory = new File(Wrapper.getMinecraft().mcDataDir, Direkt.CLIENT_NAME);
        this.eventManager.register(this);
    }

    public static Direkt getInstance() {
        return INSTANCE;
    }

    public String getClientName() {
        return CLIENT_NAME;
    }

    public String getClientVersion() {
        return CLIENT_VERSION;
    }

    public String getDisplayTitle() {
        return DISPLAY_TITLE;
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public FriendManager getFriendManager() {
        return this.friendManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public DventBus<? super Event> getEventManager() {
        return this.eventManager;
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public File getClientDirectory() {
        return this.clientDirectory;
    }

    public KeybindManager getKeybindManager() {
        return this.keybindManager;
    }

    public WaypointManager getWaypointManager() {
        return this.waypointManager;
    }

    @Listener
    protected Link<EventOnStartup> onStartupLink = new Link<>(event -> {
        this.fileManager = new FileManager();
        this.keybindManager = new KeybindManager();
        (this.commandManager = new CommandManager()).makeLoaded();
        this.moduleManager = new ModuleManager();

        fileManager.setup();
        fileManager.loadAllFiles();
        Display.setTitle(Direkt.getInstance().getDisplayTitle());
    });
}
