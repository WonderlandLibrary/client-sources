package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class CallableGLInfo implements Callable
{
    final Minecraft mc;
    
    public CallableGLInfo(final Minecraft par1Minecraft) {
        this.mc = par1Minecraft;
    }
    
    public String getTexturePack() {
        return String.valueOf(GL11.glGetString(7937)) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936);
    }
    
    @Override
    public Object call() {
        return this.getTexturePack();
    }
}
