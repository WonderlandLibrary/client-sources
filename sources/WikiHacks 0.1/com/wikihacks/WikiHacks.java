package com.wikihacks;

import com.wikihacks.drm.Drm;
import com.wikihacks.module.ModuleManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.opengl.Display;

import java.util.HashMap;
import java.util.Map;

@Mod(modid = WikiHacks.MODID, name = WikiHacks.NAME, version = WikiHacks.VERSION)
public class WikiHacks {
	public static final Map<String, String> validUsers =new HashMap<String, String>() {{
		put("cookiedragon234", "penis");
	}};
    public static final String MODID = "wikihacks";
    public static final String NAME = "WikiHacks";
    public static final String VERSION = "0.1";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
       // preinit
        //Display.setTitle(WikiHacks.NAME + " " + WikiHacks.VERSION);
        Display.setTitle("nigga ballsack");
		Drm.checkUserLicense();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModuleManager.init();
    }
}
