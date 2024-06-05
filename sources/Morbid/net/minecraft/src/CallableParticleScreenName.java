package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;

public class CallableParticleScreenName implements Callable
{
    final Minecraft theMinecraft;
    
    public CallableParticleScreenName(final Minecraft par1Minecraft) {
        this.theMinecraft = par1Minecraft;
    }
    
    public String callParticleScreenName() {
        return Minecraft.currentScreen.getClass().getCanonicalName();
    }
    
    @Override
    public Object call() {
        return this.callParticleScreenName();
    }
}
