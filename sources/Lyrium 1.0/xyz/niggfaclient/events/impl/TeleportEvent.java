// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.niggfaclient.events.CancellableEvent;

public class TeleportEvent extends CancellableEvent
{
    private C03PacketPlayer.C06PacketPlayerPosLook response;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    
    public TeleportEvent(final C03PacketPlayer.C06PacketPlayerPosLook response, final double posX, final double posY, final double posZ, final float yaw, final float pitch) {
        this.response = response;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public C03PacketPlayer.C06PacketPlayerPosLook getResponse() {
        return this.response;
    }
    
    public void setResponse(final C03PacketPlayer.C06PacketPlayerPosLook response) {
        this.response = response;
    }
    
    public double getPosX() {
        return this.posX;
    }
    
    public void setPosX(final double posX) {
        this.posX = posX;
    }
    
    public double getPosY() {
        return this.posY;
    }
    
    public void setPosY(final double posY) {
        this.posY = posY;
    }
    
    public double getPosZ() {
        return this.posZ;
    }
    
    public void setPosZ(final double posZ) {
        this.posZ = posZ;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
}
