package net.minecraft.realms;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import java.lang.reflect.*;
import org.apache.logging.log4j.*;

public class RealmsBridge extends RealmsScreen
{
    private static final Logger LOGGER;
    private GuiScreen previousScreen;
    
    public void switchToRealms(final GuiScreen p_switchToRealms_1_) {
        this.previousScreen = p_switchToRealms_1_;
        try {
            final Class var2 = Class.forName("com.mojang.realmsclient.RealmsMainScreen");
            final Constructor var3 = var2.getDeclaredConstructor(RealmsScreen.class);
            var3.setAccessible(true);
            final Object var4 = var3.newInstance(this);
            Minecraft.getMinecraft().displayGuiScreen(((RealmsScreen)var4).getProxy());
        }
        catch (Exception var5) {
            RealmsBridge.LOGGER.error("Realms module missing", (Throwable)var5);
        }
    }
    
    @Override
    public void init() {
        Minecraft.getMinecraft().displayGuiScreen(this.previousScreen);
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
