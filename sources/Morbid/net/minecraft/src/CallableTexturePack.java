package net.minecraft.src;

import java.util.concurrent.*;
import net.minecraft.client.*;

public class CallableTexturePack implements Callable
{
    final Minecraft theMinecraft;
    
    public CallableTexturePack(final Minecraft par1Minecraft) {
        this.theMinecraft = par1Minecraft;
    }
    
    public String callTexturePack() {
        return this.theMinecraft.gameSettings.skin;
    }
    
    @Override
    public Object call() {
        return this.callTexturePack();
    }
}
