package net.minecraft.src;

import java.awt.*;
import net.minecraft.client.*;

public class CanvasMinecraftApplet extends Canvas
{
    final MinecraftApplet mcApplet;
    
    public CanvasMinecraftApplet(final MinecraftApplet par1MinecraftApplet) {
        this.mcApplet = par1MinecraftApplet;
    }
    
    @Override
    public synchronized void addNotify() {
        super.addNotify();
        this.mcApplet.startMainThread();
    }
    
    @Override
    public synchronized void removeNotify() {
        this.mcApplet.shutdown();
        super.removeNotify();
    }
}
