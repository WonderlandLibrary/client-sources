package me.satisfactory.base.module;

import net.minecraft.client.*;

public class Mode<T>
{
    public Minecraft mc;
    protected T parent;
    private String name;
    
    public Mode(final T parent, final String name) {
        this.mc = Minecraft.getMinecraft();
        this.parent = parent;
        this.name = name;
    }
    
    public void onEnable() {
        if (this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
    }
    
    public void onDisable() {
        if (this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
    }
    
    public T getParent() {
        return this.parent;
    }
    
    public String getName() {
        return this.name;
    }
}
