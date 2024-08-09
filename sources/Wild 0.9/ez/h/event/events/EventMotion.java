package ez.h.event.events;

import ez.h.event.*;

public class EventMotion extends Event
{
    public double y;
    public double z;
    public float pitch;
    public boolean onGround;
    public float yaw;
    public double x;
    
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public EventMotion(final double x, final double y, final double z, final float yaw, final float n, final boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = rk.a(n, -90.0f, 90.0f);
        this.onGround = onGround;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
}
