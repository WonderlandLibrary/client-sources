package me.enrythebest.reborn.cracked.util;

import me.enrythebest.reborn.cracked.*;
import net.minecraft.src.*;

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
    
    public void setPitch(final float var1) {
        this.pitch = var1;
    }
    
    public void setYaw(final float var1) {
        this.yaw = var1;
    }
    
    public void setOldPitch(final float var1) {
        this.oldPitch = var1;
    }
    
    public void setOldYaw(final float var1) {
        this.oldYaw = var1;
    }
    
    public void preUpdate() {
        this.yaw = MorbidWrapper.getPlayer().rotationYaw;
        this.pitch = MorbidWrapper.getPlayer().rotationPitch;
    }
    
    public void postUpdate() {
        this.oldPitch = this.pitch;
        this.oldYaw = this.yaw;
    }
    
    public static Object[] updateRotation(final float var0, final float var1, final int var2) {
        float var3 = MathHelper.wrapAngleTo180_float(var1 - var0);
        boolean var4 = true;
        if (var3 > var2) {
            var3 = var2;
            var4 = false;
        }
        if (var3 < -var2) {
            var3 = -var2;
            var4 = false;
        }
        return new Object[] { var0 + var3, var4 };
    }
}
