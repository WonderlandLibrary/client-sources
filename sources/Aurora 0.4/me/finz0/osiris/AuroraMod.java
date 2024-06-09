package me.finz0.osiris;

import de.Hero.settings.SettingsManager;
import me.finz0.osiris.command.CommandManager;
import me.finz0.osiris.enemy.Enemies;
import me.finz0.osiris.event.EventProcessor;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.gui.HudManager;
import me.finz0.osiris.macro.MacroManager;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.util.CapeUtils;
import me.finz0.osiris.util.ConfigUtils;
import me.finz0.osiris.util.TpsUtils;
import me.finz0.osiris.util.font.CFontRenderer;
import me.finz0.osiris.waypoint.WaypointManager;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;

@Mod(modid = AuroraMod.MODID, name = AuroraMod.FORGENAME, version = AuroraMod.MODVER, clientSideOnly = true)
public class AuroraMod {
    public static final String MODID = "aurora";
    public static String MODNAME = "Aurora";
    public static final String MODVER = "0.4";
    public static final String FORGENAME = "Aurora";
//4
    public static final Logger log = LogManager.getLogger(MODNAME);
    public SettingsManager settingsManager;
    public Friends friends;
    public ModuleManager moduleManager;
    public ConfigUtils configUtils;
    public CapeUtils capeUtils;
    public MacroManager macroManager;
    EventProcessor eventProcessor;
    public WaypointManager waypointManager;
    public static CFontRenderer fontRenderer;
    public static Enemies enemies;
    public HudManager hudManager;

    public static final EventBus EVENT_BUS = new EventManager();

    @Mod.Instance
    private static AuroraMod INSTANCE;

    public AuroraMod(){
        INSTANCE = this;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        //log.info("PreInitialization complete!\n");

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        eventProcessor = new EventProcessor();
        eventProcessor.init();
        fontRenderer = new CFontRenderer(new Font("Arial", Font.PLAIN, 18), true, false);
        TpsUtils tpsUtils = new TpsUtils();

        settingsManager = new SettingsManager();
        log.info("Settings initialized!");

        friends = new Friends();
        enemies = new Enemies();
        log.info("Friends and enemies initialized!");

        moduleManager = new ModuleManager();
        log.info("Modules initialized!");

        hudManager = new HudManager();
        //HudComponentManager hudComponentManager = new HudComponentManager(0, 0, clickGui);
        log.info("ClickGUI initialized!");

        macroManager = new MacroManager();
        log.info("Macros initialized!");

        configUtils = new ConfigUtils();
        Runtime.getRuntime().addShutdownHook(new ShutDownHookerino());
        log.info("Config loaded!");

        CommandManager.initCommands();
        log.info("Commands initialized!");

        waypointManager = new WaypointManager();
        log.info("Waypoints initialized!");

        log.info("Initialization complete!\n");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        Display.setTitle(MODNAME + " " + MODVER);

        capeUtils = new CapeUtils();
        log.info("Capes initialised!");

        //WelcomeWindow ww = new WelcomeWindow();
        //ww.setVisible(false);
        log.info("PostInitialization complete!\n");
    }

    public static AuroraMod getInstance(){
        return INSTANCE;
    }

}
