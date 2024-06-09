package ez.cloudclient;

import ez.cloudclient.command.CommandManager;
import ez.cloudclient.gui.HUD;
import ez.cloudclient.module.ModuleManager;
import ez.cloudclient.setting.SettingsManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid = CloudClient.MODID, name = CloudClient.NAME, version = CloudClient.VERSION)

public class CloudClient {

    public static final String MODID = "cloudclient";
    public static final String NAME = "Cloud Client";
    public static final String VERSION = "1.1"; //putting a v before this looks weird in the mod menu
    public static final String APP_ID = "731393813104558122";
    public static final String FULLNAME = NAME + " " + VERSION;
    public static final String CLOUDCLIENT_CONFIGFILE = "CloudClientConfig.json";
    public static final Logger log = LogManager.getLogger("Cloud Client");
    public static final CommandManager COMMAND_MANAGER = new CommandManager();
    public static final ModuleManager MODULE_MANAGER = new ModuleManager();
    public static final SettingsManager SETTINGS_MANAGER = new SettingsManager();
    public static String PREFIX = ".";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Display.setTitle(FULLNAME);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // new AuthUtil().auth();
        ModuleManager.init();
        CommandManager.init();
        MinecraftForge.EVENT_BUS.register(new HUD());
        MinecraftForge.EVENT_BUS.register(MODULE_MANAGER);
        MinecraftForge.EVENT_BUS.register(COMMAND_MANAGER);
        // ASCII.printFancyConsoleMSG();
        System.out.println(FULLNAME + " loaded.");
    }
}
