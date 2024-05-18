package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;

public class CallableUpdatingScreenName implements Callable
{
    final Minecraft theMinecraft;
    
    public CallableUpdatingScreenName(final Minecraft par1Minecraft) {
        this.theMinecraft = par1Minecraft;
    }
    
    public String callUpdatingScreenName() {
        return Minecraft.currentScreen.getClass().getCanonicalName();
    }
    
    @Override
    public Object call() {
        return this.callUpdatingScreenName();
    }
}
