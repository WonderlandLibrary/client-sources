package com.alan.clients;

import com.alan.clients.bindable.BindableManager;
import com.alan.clients.bots.BotManager;
import com.alan.clients.command.Command;
import com.alan.clients.command.CommandManager;
import com.alan.clients.component.Component;
import com.alan.clients.component.ComponentManager;
import com.alan.clients.creative.RiseTab;
import com.alan.clients.event.Event;
import com.alan.clients.event.bus.impl.EventBus;
import com.alan.clients.layer.LayerManager;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.manager.ModuleManager;
import com.alan.clients.packetlog.Check;
import com.alan.clients.packetlog.api.manager.PacketLogManager;
import com.alan.clients.protection.api.McqBFVbnWB;
import com.alan.clients.script.ScriptManager;
import com.alan.clients.security.SecurityFeatureManager;
import com.alan.clients.ui.click.standard.RiseClickGUI;
import com.alan.clients.ui.theme.ThemeManager;
import com.alan.clients.util.ReflectionUtil;
import com.alan.clients.util.constants.ConstantsManager;
import com.alan.clients.util.file.FileManager;
import com.alan.clients.util.file.alt.AltManager;
import com.alan.clients.util.file.config.ConfigManager;
import com.alan.clients.util.file.data.DataManager;
import com.alan.clients.util.file.insult.InsultFile;
import com.alan.clients.util.file.insult.InsultManager;
import com.alan.clients.util.localization.Locale;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.viamcp.ViaMCP;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The main class where the client is loaded up.
 * Anything related to the client will start from here and managers etc. instances will be stored in this class.
 */
@Getter
public enum Client {

    /**
     * Simple enum instance for our client as enum instances
     * are immutable and are very easy to create and use.
     */
    INSTANCE;

    public static String NAME = "Rise";
    public static final String VERSION = "6";
    public static final String VERSION_FULL = "6.2.4";
    public static final String EDITION = "Christmas Edition";
    public static final String COPYRIGHT = "© Rise Client 2024. All Rights Reserved";
    public static final String CREDITS = "Made with <3 by Alan, Rotel and The_Bi11iona1re";

    public static boolean DEVELOPMENT_SWITCH = true;
    public static boolean BETA_SWITCH = true;
    public static boolean FIRST_LAUNCH = true;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Setter
    private Locale locale = Locale.EN_US; // The language of the client

    private EventBus<Event> eventBus;
    private McqBFVbnWB McqAFVeaWB;
    @Setter
    private ModuleManager moduleManager;
    @Setter
    private ComponentManager componentManager;
    @Setter
    private CommandManager commandManager;
    @Setter
    private SecurityFeatureManager securityManager;
    @Getter
    private BotManager botManager;
    private ThemeManager themeManager;
    @Setter
    private ScriptManager scriptManager;
    private DataManager dataManager;

    private FileManager fileManager;

    private ConfigManager configManager;
    private AltManager altManager;
    private InsultManager insultManager;
    private PacketLogManager packetLogManager;
    private BindableManager bindableManager;

    private LayerManager layerManager;

    @Setter
    private RiseClickGUI clickGUI;

    private RiseTab creativeTab;

    @Getter
    private Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * The main method when the Minecraft#startGame method is about
     * finish executing our client gets called and that's where we
     * can start loading our own classes and modules.
     */
    public void init() {
        // Init
        Minecraft mc = Minecraft.getMinecraft();

        // Compatibility
        mc.gameSettings.guiScale = 2;
        mc.gameSettings.ofFastRender = false;
        mc.gameSettings.ofShowGlErrors = DEVELOPMENT_SWITCH;

        // Performance
        mc.gameSettings.ofSmartAnimations = true;
        mc.gameSettings.ofSmoothFps = false;

        this.McqAFVeaWB = new McqBFVbnWB();
        this.moduleManager = new ModuleManager();
        this.componentManager = new ComponentManager();
        this.commandManager = new CommandManager();
        this.fileManager = new FileManager();
        this.configManager = new ConfigManager();
        this.altManager = new AltManager();
        this.insultManager = new InsultManager();
        this.dataManager = new DataManager();
        this.securityManager = new SecurityFeatureManager();
        this.botManager = new BotManager();
        this.themeManager = new ThemeManager();
        this.scriptManager = new ScriptManager();
        this.eventBus = new EventBus<>();
        this.packetLogManager = new PacketLogManager();
        this.bindableManager = new BindableManager();
        this.layerManager = new LayerManager();
        new ConstantsManager();

        this.fileManager.init();

        DEVELOPMENT_SWITCH = !ReflectionUtil.dirExist("hackclient.") && ReflectionUtil.dirExist("com.alan.clients");

        // Init Managers
        this.dataManager.init();
        this.McqAFVeaWB.init();
        this.moduleManager.init();
        this.securityManager.init();
        this.botManager.init();
        this.componentManager.init();
        this.commandManager.init();
        this.altManager.init();
        this.insultManager.init();
        this.scriptManager.init();
        this.packetLogManager.init();

        this.clickGUI = new RiseClickGUI();
        this.clickGUI.initGui();

        this.insultManager.update();
        this.insultManager.forEach(InsultFile::read);

        this.creativeTab = new RiseTab();

        new Thread(() -> {
            ViaMCP.create();
            ViaMCP.INSTANCE.initAsyncSlider();

            ViaMCP.INSTANCE.getAsyncVersionSlider().setVersion(ViaMCP.NATIVE_VERSION);
        }).start();

        this.configManager.init();
        this.bindableManager.init();

        Display.setTitle(NAME + " " + VERSION_FULL.replace(".0", ""));
    }

    public void register() {
        // Register
        String[] paths = {
                /**
                 * You have to do this in reverse order,
                 * since some things in com.alan.clients are exempted when obfuscated,
                 * the path exists, but that's not where rise is stored.
                */

                "hackclient.",
                "com.alan.clients."
        };

        for (String path : paths) {
            if (!ReflectionUtil.dirExist(path)) {
                continue;
            }

            if (path.equals("hackclient.")) {
                DEVELOPMENT_SWITCH = false;
            }

            Class<?>[] classes = ReflectionUtil.getClassesInPackage(path);

            for (Class<?> clazz : classes) {
                try {
                    if (Modifier.isAbstract(clazz.getModifiers()))
                        continue;

                    if (Component.class.isAssignableFrom(clazz)) {
                        this.componentManager.add((Component) clazz.getConstructor().newInstance());
                    } else if (Module.class.isAssignableFrom(clazz)) {
                        this.moduleManager.put(clazz, (Module) clazz.getConstructor().newInstance());
                    } else if (Command.class.isAssignableFrom(clazz)) {
                        this.commandManager.getCommandList().add((Command) clazz.getConstructor().newInstance());
                    } else if (Check.class.isAssignableFrom(clazz)) {
                        this.packetLogManager.add((Check) clazz.getConstructor().newInstance());
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException exception) {
                    exception.printStackTrace();
                }
            }

            break;
        }
    }

    /**
     * The terminate method is called when the Minecraft client is shutting
     * down, so we can cleanup our stuff and ready ourselves for the client quitting.
     */
    public void terminate() {
        if (this.getConfigManager() != null && this.getConfigManager().getLatestConfig() != null) {
            this.getConfigManager().getLatestConfig().write();
        }
    }

    public void reload() {
        terminate();
        init();
        Client.INSTANCE.getConfigManager().setupLatestConfig();
    }
}