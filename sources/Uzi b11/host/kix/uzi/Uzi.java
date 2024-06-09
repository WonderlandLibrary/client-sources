package host.kix.uzi;

import host.kix.uzi.admin.AdminManager;
import host.kix.uzi.command.CommandManager;
import host.kix.uzi.file.FileManager;
import host.kix.uzi.friends.FriendManager;
import host.kix.uzi.module.ModuleManager;
import host.kix.uzi.module.addons.theme.management.ThemeManager;
import host.kix.uzi.ui.alt.AltManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;

/**
 * Created by Kix on 2/3/2017.
 */
public final class Uzi {

    public static final String LABEL = "Uzi";
    public static final int BUILD = 11;
    private static final Uzi INSTANCE = new Uzi();
    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private FriendManager friendManager;
    private AdminManager adminManager;
    private FileManager fileManager;
    private AltManager altManager = new AltManager();
    private ThemeManager themeManager;
    private File directory;

    public static Uzi getInstance() {
        return INSTANCE;
    }

    public void init(String gameDirectory) throws IOException {
        Display.setTitle(String.format("%s b%s for Minecraft 1.8", LABEL, BUILD));
        this.directory = new File(gameDirectory + File.separator + "uzi" + File.separator);
        if (!directory.exists())
            if (!directory.mkdirs())
                System.exit(0);
        moduleManager = new ModuleManager();
        themeManager = new ThemeManager();
        commandManager = new CommandManager();
        fileManager = new FileManager();
        friendManager = new FriendManager();
        adminManager = new AdminManager();
        moduleManager.load();
        fileManager.load();
        commandManager.load();
    }

    public void shutdown() throws IOException {
        fileManager.save();
    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }

    public AltManager getAltManager() {
        return altManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public File getDirectory() {
        return directory;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public AdminManager getAdminManager() {
        return adminManager;
    }
}
