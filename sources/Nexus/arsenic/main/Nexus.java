package arsenic.main;

import arsenic.command.CommandManager;
import arsenic.config.ConfigManager;
import arsenic.event.EventManager;
import arsenic.event.ForgeEvents;
import arsenic.gui.click.ClickGuiScreen;
import arsenic.gui.themes.ThemeManager;
import arsenic.module.ModuleManager;
import arsenic.utils.font.Fonts;
import arsenic.utils.rotations.SilentRotationManager;
import de.florianmichael.viaforge.ViaForge;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Mod(name = "Nexus Client", modid = "nexus", clientSideOnly = true, version = "1.0")
public class Nexus {

    private final String clientName = "Nexus";
    private final long clientVersion = 221020L;
    private final Logger logger = LogManager.getLogger(clientName);
    private final EventManager eventManager = new EventManager();
    private final ModuleManager moduleManager = new ModuleManager();
    private final Fonts fonts = new Fonts();
    private final ConfigManager configManager = new ConfigManager();
    private final CommandManager commandManager = new CommandManager();
    private final ClickGuiScreen clickGuiScreen = new ClickGuiScreen();
    private final ThemeManager themeManager = new ThemeManager();
    private final SilentRotationManager silentRotationManager = new SilentRotationManager();

    private final Executor executor = Executors.newSingleThreadExecutor();
    private Minecraft mc = Minecraft.getMinecraft();
    @Mod.EventHandler
    public final void init(FMLInitializationEvent event) {
        executor.execute(() -> {
            logger.info("This is to get a guide on how many people are using the client it records zero data");
        });

        logger.info("Loading {}, version {}...", clientName, getClientVersionString());

        MinecraftForge.EVENT_BUS.register(new ForgeEvents());
        logger.info("Hooked forge events");

        getEventManager().subscribe(silentRotationManager);

        logger.info("Subscribed silent rotation manager");

        logger.info("Loaded {} modules...", String.valueOf(moduleManager.initialize()));

        logger.info("Loaded {} themes...", String.valueOf(themeManager.initialize()));

        logger.info("Loaded {} configs...", String.valueOf(configManager.initialize()));

        logger.info("Loaded {} commands...", String.valueOf(commandManager.initialize()));

        fonts.initTextures();
        logger.info("Loaded fonts.");

        logger.info("Loaded {}.", clientName);
      //  ViaForge.start();
    }

    public String getName() {
        return clientName;
    }

    @Mod.Instance
    private static Nexus instance;

    public static Nexus getInstance() {
        return instance;
    }

    public static Nexus getNexus() {
        return instance;
    }

    public final String getClientName() {
        return clientName;
    }

    public final long getClientVersion() {
        return clientVersion;
    }

    @Contract(pure = true)
    public final @NotNull String getClientVersionString() {
        return String.valueOf(clientVersion);
    }

    public final Logger getLogger() {
        return logger;
    }

    public final EventManager getEventManager() {
        return eventManager;
    }

    public final ModuleManager getModuleManager() {
        return moduleManager;
    }

    public final Fonts getFonts() {
        return fonts;
    }

    public final ConfigManager getConfigManager() {
        return configManager;
    }

    public final CommandManager getCommandManager() {
        return commandManager;
    }

    public final ClickGuiScreen getClickGuiScreen() {
        return clickGuiScreen;
    }

    public final SilentRotationManager getSilentRotationManager() {
        return silentRotationManager;
    }

    public final ThemeManager getThemeManager() {
        return themeManager;
    }
}