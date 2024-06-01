package com.polarware;

import by.radioegor146.nativeobfuscator.Native;
import com.polarware.anticheat.CheatDetector;
import com.polarware.component.impl.target.BotComponent;
import com.polarware.command.Command;
import com.polarware.command.CommandManager;
import com.polarware.component.Component;
import com.polarware.component.ComponentManager;
import com.polarware.module.Module;
import com.polarware.module.api.manager.ModuleManager;
import com.polarware.event.bus.impl.EventBus;
import com.polarware.component.impl.target.TargetComponent;
import com.polarware.security.SecurityFeatureManager;
import com.polarware.ui.click.RiseClickGUI;
import com.polarware.ui.menu.impl.alt.AltManagerMenu;
import com.polarware.ui.theme.ThemeManager;
import com.polarware.util.ReflectionUtil;
import com.polarware.util.file.FileManager;
import com.polarware.util.file.FileType;
import com.polarware.util.file.alt.AltManager;
import com.polarware.util.file.config.ConfigFile;
import com.polarware.util.file.config.ConfigManager;
import com.polarware.util.file.data.DataManager;
import com.polarware.util.file.insult.InsultFile;
import com.polarware.util.file.insult.InsultManager;
import com.polarware.util.localization.Locale;
import com.polarware.util.math.MathConst;
import com.polarware.util.value.ConstantManager;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.viamcp.viamcp.ViaMCP;
import org.lwjgl.opengl.Display;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Native
public enum Client {

    /**
     * Simple enum instance for our client as enum instances
     * are immutable and are very easy to create and use.
     */
    INSTANCE;

    public static String NAME = "Polarware";
    public static String VERSION = "1.0";
    public static final String VERSION_FULL = "1.0";
    public static final String VERSION_DATE = "October 15, 2023";

    public static boolean DEVELOPMENT_SWITCH = true;
    public static final boolean BETA_SWITCH = false;
    public static final boolean FIRST_LAUNCH = true;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Setter
    private Locale locale = Locale.EN_US; // The language of the client

    private EventBus eventBus;
    private ModuleManager moduleManager;
    private ComponentManager componentManager;
    private CommandManager commandManager;
    private SecurityFeatureManager securityManager;
    private BotComponent botComponent;
    private ThemeManager themeManager;
    private DataManager dataManager;
    private CheatDetector cheatDetector;

    private FileManager fileManager;

    private ConfigManager configManager;
    private AltManager altManager;
    private InsultManager insultManager;
    private TargetComponent targetComponent;
    private ConstantManager constantManager;

    private ConfigFile configFile;

    private RiseClickGUI standardClickGUI;
    private AltManagerMenu altManagerMenu;

    @Setter
    private boolean validated;

    /**
     * The main method when the Minecraft#startGame method is about
     * finish executing our client gets called and that's where we
     * can start loading our own classes and modules.
     */
    public void initRise() {
        
        // Init
        Minecraft mc = Minecraft.getMinecraft();
        MathConst.calculate();

        // Compatibility
        mc.gameSettings.guiScale = 2;
        mc.gameSettings.ofFastRender = false;
        mc.gameSettings.ofShowGlErrors = DEVELOPMENT_SWITCH;

        // Performance
        mc.gameSettings.ofSmartAnimations = true;
        mc.gameSettings.ofSmoothFps = false;
        mc.gameSettings.ofFastMath = false;

        this.moduleManager = new ModuleManager();
        this.componentManager = new ComponentManager();
        this.commandManager = new CommandManager();
        this.fileManager = new FileManager();
        this.configManager = new ConfigManager();
        this.altManager = new AltManager();
        this.insultManager = new InsultManager();
        this.dataManager = new DataManager();
        this.securityManager = new SecurityFeatureManager();
        this.botComponent = new BotComponent();
        this.themeManager = new ThemeManager();
        this.targetComponent = new TargetComponent();
        this.cheatDetector = new CheatDetector();
        this.constantManager = new ConstantManager();
        this.eventBus = new EventBus();

        GuiConnecting.a = true;
        Container.a = false;
        EntityPlayer.a = 3;
        GuiIngame.a = 33L;

        // Register
        String[] paths = {
                "com.polarware",
                "hackclient"
        };

        for (String path : paths) {
            if (!ReflectionUtil.exist(path)) {
                continue;
            }

            Class<?>[] classes = ReflectionUtil.getClassesInPackage(path);

            for (Class<?> clazz : classes) {
                try {
                    if (Component.class.isAssignableFrom(clazz) && clazz != Component.class) {
                        this.componentManager.add((Component) clazz.getConstructor().newInstance());
                    } else if (Module.class.isAssignableFrom(clazz) && clazz != Module.class) {
                        this.moduleManager.add((Module) clazz.getConstructor().newInstance());
                    } else if (Command.class.isAssignableFrom(clazz) && clazz != Command.class) {
                        this.commandManager.add((Command) clazz.getConstructor().newInstance());
                    }
                } catch (Exception ignored) {
                    // nothing here my black ass nigger
                }
            }
        }

        // Init Managers
        this.targetComponent.init();
        this.dataManager.init();
        this.moduleManager.init();
        this.securityManager.init();
        this.botComponent.init();
        this.componentManager.init();
        this.commandManager.init();
        this.fileManager.init();
        this.configManager.init();
        this.altManager.init();
        this.insultManager.init();

        final File file = new File(ConfigManager.CONFIG_DIRECTORY, "latest.json");
        this.configFile = new ConfigFile(file, FileType.CONFIG);
        this.configFile.allowKeyCodeLoading();
        this.configFile.read();

        this.insultManager.update();
        this.insultManager.forEach(InsultFile::read);

        this.standardClickGUI = new RiseClickGUI();
        this.altManagerMenu = new AltManagerMenu();

        ViaMCP.create();

        Display.setTitle(NAME + " " + VERSION_FULL);
    }

    /**
     * The terminate method is called when the Minecraft client is shutting
     * down, so we can cleanup our stuff and ready ourselves for the client quitting.
     */
    public void terminate() {
        this.configFile.write();
    }
}

