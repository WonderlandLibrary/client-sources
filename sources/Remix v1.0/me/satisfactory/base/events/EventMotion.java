package me.satisfactory.base.events;

import me.satisfactory.base.events.event.callables.*;

public class EventMotion extends EventCancellable
{
    public float yaw;
    public float pitch;
    public float lastReportedYaw;
    public float lastReportedPitch;
    private byte type;
    public double x;
    public double y;
    public double z;
    public boolean onGround;
    
    public EventMotion(final float yaw, final float pitch, final float lastReportedYaw, final float lastReportedPitch, final double x, final double y, final double z, final boolean onGround) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.lastReportedYaw = lastReportedYaw;
        this.lastReportedPitch = lastReportedPitch;
        this.type = 0;
        this.y = y;
        this.x = x;
        this.z = z;
        this.onGround = onGround;
    }
    
    public EventMotion() {
        this.type = 2;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(final float newyaw) {
        this.yaw = newyaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(final float newpitch) {
        this.pitch = newpitch;
    }
    
    public byte getType() {
        return this.type;
    }
    
    public boolean isRotating() {
        final double yaw = this.yaw - this.yaw;
        final double pitch = this.pitch - this.pitch;
        return Math.abs(yaw) != 0.0 || Math.abs(pitch) != 0.0;
    }
}
