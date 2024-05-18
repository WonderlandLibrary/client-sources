package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;

public class CallableTickingScreenName implements Callable
{
    final Minecraft mc;
    
    public CallableTickingScreenName(final Minecraft par1Minecraft) {
        this.mc = par1Minecraft;
    }
    
    public String getLWJGLVersion() {
        return Minecraft.currentScreen.getClass().getCanonicalName();
    }
    
    @Override
    public Object call() {
        return this.getLWJGLVersion();
    }
}
