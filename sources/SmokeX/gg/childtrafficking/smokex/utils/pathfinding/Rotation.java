// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.pathfinding;

public class Rotation
{
    private float yaw;
    private float pitch;
    
    public Rotation(final float yaw, final float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
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
