package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;

public class CallableModded implements Callable
{
    final Minecraft mc;
    
    public CallableModded(final Minecraft par1Minecraft) {
        this.mc = par1Minecraft;
    }
    
    public String getClientProfilerEnabled() {
        final String var1 = ClientBrandRetriever.getClientModName();
        return var1.equals("vanilla") ? ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.") : ("Definitely; Client brand changed to '" + var1 + "'");
    }
    
    @Override
    public Object call() {
        return this.getClientProfilerEnabled();
    }
}
