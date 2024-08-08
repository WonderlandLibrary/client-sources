// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds;

import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.Display;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import me.perry.mcdonalds.manager.TextManager;
import me.perry.mcdonalds.manager.EventManager;
import me.perry.mcdonalds.manager.ServerManager;
import me.perry.mcdonalds.manager.ConfigManager;
import me.perry.mcdonalds.manager.FileManager;
import me.perry.mcdonalds.manager.ReloadManager;
import me.perry.mcdonalds.manager.SpeedManager;
import me.perry.mcdonalds.manager.PositionManager;
import me.perry.mcdonalds.manager.RotationManager;
import me.perry.mcdonalds.manager.PotionManager;
import me.perry.mcdonalds.manager.InventoryManager;
import me.perry.mcdonalds.manager.HoleManager;
import me.perry.mcdonalds.manager.ColorManager;
import me.perry.mcdonalds.manager.PacketManager;
import me.perry.mcdonalds.manager.ModuleManager;
import me.perry.mcdonalds.manager.FriendManager;
import me.perry.mcdonalds.manager.CommandManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "mcdonalds client", name = "McDonalds Client", version = "1.1.7")
public class McDonalds
{
    public static final String MODID = "mcdonalds client";
    public static final String MODNAME = "McDonalds Client";
    public static final String MODVER = "1.1.7";
    public static final Logger LOGGER;
    public static CommandManager commandManager;
    public static FriendManager friendManager;
    public static ModuleManager moduleManager;
    public static PacketManager packetManager;
    public static ColorManager colorManager;
    public static HoleManager holeManager;
    public static InventoryManager inventoryManager;
    public static PotionManager potionManager;
    public static RotationManager rotationManager;
    public static PositionManager positionManager;
    public static SpeedManager speedManager;
    public static ReloadManager reloadManager;
    public static FileManager fileManager;
    public static ConfigManager configManager;
    public static ServerManager serverManager;
    public static EventManager eventManager;
    public static TextManager textManager;
    @Mod.Instance
    public static McDonalds INSTANCE;
    private static boolean unloaded;
    
    public static void load() {
        McDonalds.LOGGER.info("\n\nLoading McDonalds Client by zPrestige_ & ad6q");
        McDonalds.unloaded = false;
        if (McDonalds.reloadManager != null) {
            McDonalds.reloadManager.unload();
            McDonalds.reloadManager = null;
        }
        McDonalds.textManager = new TextManager();
        McDonalds.commandManager = new CommandManager();
        McDonalds.friendManager = new FriendManager();
        McDonalds.moduleManager = new ModuleManager();
        McDonalds.rotationManager = new RotationManager();
        McDonalds.packetManager = new PacketManager();
        McDonalds.eventManager = new EventManager();
        McDonalds.speedManager = new SpeedManager();
        McDonalds.potionManager = new PotionManager();
        McDonalds.inventoryManager = new InventoryManager();
        McDonalds.serverManager = new ServerManager();
        McDonalds.fileManager = new FileManager();
        McDonalds.colorManager = new ColorManager();
        McDonalds.positionManager = new PositionManager();
        McDonalds.configManager = new ConfigManager();
        McDonalds.holeManager = new HoleManager();
        McDonalds.LOGGER.info("Managers loaded.");
        McDonalds.moduleManager.init();
        McDonalds.LOGGER.info("Modules loaded.");
        McDonalds.configManager.init();
        McDonalds.eventManager.init();
        McDonalds.LOGGER.info("EventManager loaded.");
        McDonalds.textManager.init(true);
        McDonalds.moduleManager.onLoad();
        McDonalds.LOGGER.info("McDonalds Client successfully loaded!\n");
    }
    
    public static void unload(final boolean unload) {
        McDonalds.LOGGER.info("\n\nUnloading McDonalds Client by zPrestige_ & ad6q");
        if (unload) {
            (McDonalds.reloadManager = new ReloadManager()).init((McDonalds.commandManager != null) ? McDonalds.commandManager.getPrefix() : ".");
        }
        onUnload();
        McDonalds.eventManager = null;
        McDonalds.friendManager = null;
        McDonalds.speedManager = null;
        McDonalds.holeManager = null;
        McDonalds.positionManager = null;
        McDonalds.rotationManager = null;
        McDonalds.configManager = null;
        McDonalds.commandManager = null;
        McDonalds.colorManager = null;
        McDonalds.serverManager = null;
        McDonalds.fileManager = null;
        McDonalds.potionManager = null;
        McDonalds.inventoryManager = null;
        McDonalds.moduleManager = null;
        McDonalds.textManager = null;
        McDonalds.LOGGER.info("McDonalds Client unloaded!\n");
    }
    
    public static void reload() {
        unload(false);
        load();
    }
    
    public static void onUnload() {
        if (!McDonalds.unloaded) {
            McDonalds.eventManager.onUnload();
            McDonalds.moduleManager.onUnload();
            McDonalds.configManager.saveConfig(McDonalds.configManager.config.replaceFirst("McDonalds/", ""));
            McDonalds.moduleManager.onUnloadPost();
            McDonalds.unloaded = true;
        }
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        McDonalds.LOGGER.info("McDonalds Client");
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Display.setTitle("McDonalds Client v1.1.7");
        load();
    }
    
    static {
        LOGGER = LogManager.getLogger("McDonalds Client");
        McDonalds.unloaded = false;
    }
}
