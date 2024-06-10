package xyz.gucciclient;

import xyz.gucciclient.gui.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import xyz.gucciclient.utils.*;
import org.lwjgl.input.*;
import xyz.gucciclient.modules.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mod(modid = "TcpNoDelayMod-2.0", acceptedMinecraftVersions = "[1.8.9]")
public class Gucci
{
    private static ClickGUI clickGUI;
    public static Gucci INSTANCE;
    
    @Mod.EventHandler
    public void fmlInitialization(final FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)this);
        FMLCommonHandler.instance().bus().register((Object)this);
        Gucci.clickGUI = new ClickGUI();
    }
    
    public static Gucci getGucci() {
        return Gucci.INSTANCE;
    }
    
    @SubscribeEvent
    public void keyInput(final InputEvent.KeyInputEvent event) {
        if (Wrapper.getPlayer() != null) {
            if (!Keyboard.getEventKeyState()) {
                return;
            }
            for (final Module mod : ModuleManager.getModules()) {
                if (mod.getKey() == Keyboard.getEventKey()) {
                    mod.setState(!mod.getState());
                }
            }
        }
    }
    
    public static ClickGUI getClickGUI() {
        return Gucci.clickGUI;
    }
}
