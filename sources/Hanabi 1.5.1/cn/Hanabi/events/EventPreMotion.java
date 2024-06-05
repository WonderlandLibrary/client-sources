package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.callables.*;

public class EventPreMotion extends EventCancellable
{
    public double y;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public boolean cancel;
    
    public EventPreMotion(final double y, final float yaw, final float pitch, final boolean onGround) {
        this.y = y;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
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
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
    
    public boolean isCancel() {
        return this.cancel;
    }
    
    public void setCancel(final boolean cancel) {
        this.cancel = cancel;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean state) {
        this.cancel = state;
    }
}
