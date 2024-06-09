// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;
import xyz.niggfaclient.events.CancellableEvent;

public class MoveEvent extends CancellableEvent
{
    public double x;
    public double y;
    public double z;
    
    public MoveEvent(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        thePlayer.motionX *= x;
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        Minecraft.getMinecraft().thePlayer.motionY = y;
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        final EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        thePlayer.motionZ *= z;
        this.z = z;
    }
}
