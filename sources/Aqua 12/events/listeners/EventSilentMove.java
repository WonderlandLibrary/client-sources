// 
// Decompiled by Procyon v0.5.36
// 

package events.listeners;

import net.minecraft.util.MathHelper;
import intent.AquaDev.aqua.utils.RotationUtil;
import net.minecraft.client.Minecraft;
import events.Event;

public class EventSilentMove extends Event
{
    private boolean silent;
    private float yaw;
    
    public EventSilentMove(final float yaw) {
        this.yaw = yaw;
    }
    
    public boolean isSilent() {
        return this.silent;
    }
    
    public void setSilent(final boolean silent) {
        this.silent = silent;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    public float[] moveSilent(final float strafe, final float forward) {
        final Minecraft mc = Minecraft.getMinecraft();
        final float diff = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - RotationUtil.yaw);
        float newForward = 0.0f;
        float newStrafe = 0.0f;
        if (diff >= 22.5 && diff < 67.5) {
            newStrafe += strafe;
            newForward += forward;
            newStrafe -= forward;
            newForward += strafe;
        }
        else if (diff >= 67.5 && diff < 112.5) {
            newStrafe -= forward;
            newForward += strafe;
        }
        else if (diff >= 112.5 && diff < 157.5) {
            newStrafe -= strafe;
            newForward -= forward;
            newStrafe -= forward;
            newForward += strafe;
        }
        else if (diff >= 157.5 || diff <= -157.5) {
            newStrafe -= strafe;
            newForward -= forward;
        }
        else if (diff > -157.5 && diff <= -112.5) {
            newStrafe -= strafe;
            newForward -= forward;
            newStrafe += forward;
            newForward -= strafe;
        }
        else if (diff > -112.5 && diff <= -67.5) {
            newStrafe += forward;
            newForward -= strafe;
        }
        else if (diff > -67.5 && diff <= -22.5) {
            newStrafe += strafe;
            newForward += forward;
            newStrafe += forward;
            newForward -= strafe;
        }
        else {
            newStrafe += strafe;
            newForward += forward;
        }
        return new float[] { newStrafe, newForward };
    }
}
