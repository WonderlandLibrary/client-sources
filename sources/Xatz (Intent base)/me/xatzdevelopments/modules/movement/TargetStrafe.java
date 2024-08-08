package me.xatzdevelopments.modules.movement;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventAttack;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class TargetStrafe extends Module
{
    Entity t;
    
    public TargetStrafe() {
        super("TargetStrafe", 0, Category.MOVEMENT, null);
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventAttack) {
            this.t = ((EventAttack)e).entity;
        }
        if (e instanceof EventMotion && this.t == null) {
            return;
        }
    }
    
    public float[] getRotations(final Entity target) {
        final double var4 = target.posX - this.mc.thePlayer.posX;
        final double var5 = target.posZ - this.mc.thePlayer.posZ;
        final double var6 = target.posY + 0.3 + target.getEyeHeight() / 1.3 - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
        final double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float yaw = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793));
        final float interpolatedAngle = MathHelper.cos((float)(var4 - 1.0 + (var5 - (var6 - 1.0)) * this.mc.timer.renderPartialTicks));
        return new float[] { this.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - this.mc.thePlayer.rotationYaw + interpolatedAngle), this.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - this.mc.thePlayer.rotationPitch) };
    }
}
