package com.krazzzzymonkey.catalyst;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import net.minecraftforge.fml.common.FMLCommonHandler;
import com.krazzzzymonkey.catalyst.utils.system.Nan0EventRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.lwjgl.opengl.Display;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import com.krazzzzymonkey.catalyst.managers.HackManager;
import com.krazzzzymonkey.catalyst.managers.FileManager;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "catalyst", name = "Catalyst", version = "0.1.0", acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.12.2]")
public class Main
{
    public static int initCount;
    public static FileManager fileManager;
    public static HackManager hackManager;
    public static EventsHandler eventsHandler;
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        Display.setTitle("Catalyst 0.1.0");
    }
    
    public Main() {
        this.init(null);
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Main.hackManager = new HackManager();
        Main.fileManager = new FileManager();
        Main.eventsHandler = new EventsHandler();
        Nan0EventRegister.register(MinecraftForge.EVENT_BUS, Main.eventsHandler);
        Nan0EventRegister.register(FMLCommonHandler.instance().bus(), Main.eventsHandler);
        Main.initCount += 1;
    }
    
    static {
        MCVERSION = "1.12.2";
        NAME = "Catalyst";
        MODID = "catalyst";
        VERSION = "0.1.0";
        Main.initCount = 0;
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Display.setTitle("Initializing Catalyst 0.1.0 By Krazzzzymonkey");
    }
}
