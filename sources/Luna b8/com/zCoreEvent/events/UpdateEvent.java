// 
// Decompiled by Procyon v0.5.30
// 

package com.zCoreEvent.events;

import com.zCoreEvent.Event;

import lunadevs.luna.events.Event.State;
import lunadevs.luna.events.Location;
import net.minecraft.network.Packet;

public class UpdateEvent extends Event
{
    private State state;
    private float yaw;
    private float pitch;
    public static boolean sendcanel;
	  public static boolean recievecancel;
    private double y;
    private boolean onground;
    private Location location;
    private boolean alwaysSend;
    
    public UpdateEvent() {
        this.state = State.POST;
    }
    
    public UpdateEvent(final double y, final float yaw, final float pitch, final boolean ground) {
        this.state = State.PRE;
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.onground = ground;
    }
    public Packet getReceivedPacket()
	  {
	    return this.packet;
	  }
    public State getState() {
        return this.state;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public double getY() {
        return this.y;
    }
    
    public boolean isOnground() {
        return this.onground;
    }
    
    public boolean shouldAlwaysSend() {
        return this.alwaysSend;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public void setGround(final boolean ground) {
        this.onground = ground;
    }
    
    public void setAlwaysSend(final boolean alwaysSend) {
        this.alwaysSend = alwaysSend;
    }

	public Location getLocation()
  {
    return this.location;
  }
}
