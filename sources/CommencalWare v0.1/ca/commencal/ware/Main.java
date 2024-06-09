package ca.commencal.ware;

import ca.commencal.ware.managers.FileManager;
import ca.commencal.ware.managers.ModuleManager;
import ca.commencal.ware.module.Module;
import ca.commencal.ware.utils.system.EventRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.opengl.Display;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION, acceptableRemoteVersions = "*")
public class Main {
	
	public static final String MODID = "commencal.ware";
	public static final String NAME = "Commencal Ware";
	public static final String VERSION = "v0.1";
	public static final String MCVERSION = "1.12.2";
	public static int initCount = 0;
	public static ModuleManager moduleManager;
	public static FileManager fileManager;
	public static EventsHandler eventsHandler;

	public Main() { init(null); }
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent E) {
		Display.setTitle(NAME + " " + VERSION + " - " + MCVERSION);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent E) {
		if(initCount > 0) { return; } 
		moduleManager = new ModuleManager();
		fileManager = new FileManager();
		eventsHandler = new EventsHandler();
		EventRegister.register(MinecraftForge.EVENT_BUS, eventsHandler);
		EventRegister.register(FMLCommonHandler.instance().bus(), eventsHandler);
		initCount++;
		for(Module module : ModuleManager.getToggleModule()) {
			module.onUpdate();
		}
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent E) {}
}
