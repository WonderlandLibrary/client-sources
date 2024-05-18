package me.darkmagician6.morbid.util;

import me.darkmagician6.morbid.*;

public class RotationManager
{
    private float pitch;
    private float yaw;
    private float oldPitch;
    private float oldYaw;
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public float getOldYaw() {
        return this.oldYaw;
    }
    
    public float getOldPitch() {
        return this.oldPitch;
    }
    
    public void setPitch(final float paramFloat) {
        this.pitch = paramFloat;
    }
    
    public void setYaw(final float paramFloat) {
        this.yaw = paramFloat;
    }
    
    public void setOldPitch(final float paramFloat) {
        this.oldPitch = paramFloat;
    }
    
    public void setOldYaw(final float paramFloat) {
        this.oldYaw = paramFloat;
    }
    
    public void preUpdate() {
        this.yaw = MorbidWrapper.getPlayer().A;
        this.pitch = MorbidWrapper.getPlayer().B;
    }
    
    public void postUpdate() {
        this.oldPitch = this.pitch;
        this.oldYaw = this.yaw;
    }
    
    public static Object[] updateRotation(final float current, final float target, final int maxIncrease) {
        float angle = kx.g(target - current);
        boolean aiming = true;
        if (angle > maxIncrease) {
            angle = maxIncrease;
            aiming = false;
        }
        if (angle < -maxIncrease) {
            angle = -maxIncrease;
            aiming = false;
        }
        return new Object[] { current + angle, aiming };
    }
}
