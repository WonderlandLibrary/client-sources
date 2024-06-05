package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;
import org.lwjgl.*;

public class CallableLWJGLVersion implements Callable
{
    final Minecraft mc;
    
    public CallableLWJGLVersion(final Minecraft par1Minecraft) {
        this.mc = par1Minecraft;
    }
    
    public String getType() {
        return Sys.getVersion();
    }
    
    @Override
    public Object call() {
        return this.getType();
    }
}
