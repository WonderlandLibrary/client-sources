package in.momin5.cookieclient;

import in.momin5.cookieclient.api.command.BindCommand;
import in.momin5.cookieclient.api.command.HelpCommand;
import in.momin5.cookieclient.api.command.PrefixCommand;
import in.momin5.cookieclient.api.command.ToggleCommand;
import in.momin5.cookieclient.api.event.EventProcessor;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.api.setting.SettingManager;
import in.momin5.cookieclient.api.util.utils.font.CustomFontRenderer;
import in.momin5.cookieclient.client.config.ClickGUILoad;
import in.momin5.cookieclient.client.config.ClickGUISave;
import in.momin5.cookieclient.client.config.ConfigSave;
import in.momin5.cookieclient.client.config.ConfigStopper;
import in.momin5.cookieclient.client.gui.clickgui.ClickGUI;
import me.yagel15637.venture.manager.CommandManager;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = CookieClient.MOD_ID, name = CookieClient.MOD_NAME, version = CookieClient.MOD_VERSION)
public class CookieClient {

    public static final String MOD_ID = "cookie";
    public static final String MOD_NAME = "Biscuit Client";
    public static final String MOD_VERSION = "1.1.0";

    public static final Logger LOGGER = LogManager.getLogger(CookieClient.MOD_NAME);
    public static final EventBus EVENT_BUS = new EventManager();

    @Mod.Instance
    public static CookieClient INSTANCE;

    public CookieClient() { INSTANCE = this; }
    public static CookieClient getInstance() { return INSTANCE; }


    public static ModuleManager moduleManager;
    public static EventProcessor eventProcessor;
    public static ClickGUI clickGUI;
    public static CustomFontRenderer customFontRenderer;
    public static SettingManager settingManager;
    public static ConfigSave configSave;
    public static ClickGUILoad clickGUILoad;
    public static ClickGUISave clickGUISave;
    public static String commandPrefix = ":";

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(INSTANCE);
        LOGGER.info("Starting to load " + CookieClient.MOD_NAME);

        moduleManager = new ModuleManager();
        MinecraftForge.EVENT_BUS.register(moduleManager);
        LOGGER.info("Registering Modules");

        eventProcessor = new EventProcessor();
        eventProcessor.init();
        LOGGER.info("Registering Events");

        settingManager = new SettingManager();
        LOGGER.info("Registering Settings");

        CommandManager.addCommands(
                new BindCommand(),
                new ToggleCommand(),
                new PrefixCommand(),
                new HelpCommand()
        );
        LOGGER.info("Registering Commands");

        clickGUI = new ClickGUI();
        LOGGER.info("Loading GUI");

        configSave = new ConfigSave();
        LOGGER.info("Loading Configs");

        clickGUISave = new ClickGUISave();
        clickGUILoad = new ClickGUILoad();
        Runtime.getRuntime().addShutdownHook(new ConfigStopper());

        LOGGER.info("All done " + CookieClient.MOD_NAME + " working");

    }
    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
    }

}