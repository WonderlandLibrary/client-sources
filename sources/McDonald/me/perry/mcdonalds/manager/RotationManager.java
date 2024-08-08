// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.manager;

import me.perry.mcdonalds.util.RotationUtil;
import net.minecraft.entity.Entity;
import me.perry.mcdonalds.util.MathUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import me.perry.mcdonalds.features.Feature;

public class RotationManager extends Feature
{
    private float yaw;
    private float pitch;
    
    public void updateRotations() {
        this.yaw = RotationManager.mc.player.rotationYaw;
        this.pitch = RotationManager.mc.player.rotationPitch;
    }
    
    public void restoreRotations() {
        RotationManager.mc.player.rotationYaw = this.yaw;
        RotationManager.mc.player.rotationYawHead = this.yaw;
        RotationManager.mc.player.rotationPitch = this.pitch;
    }
    
    public void setPlayerRotations(final float yaw, final float pitch) {
        RotationManager.mc.player.rotationYaw = yaw;
        RotationManager.mc.player.rotationYawHead = yaw;
        RotationManager.mc.player.rotationPitch = pitch;
    }
    
    public void setPlayerYaw(final float yaw) {
        RotationManager.mc.player.rotationYaw = yaw;
        RotationManager.mc.player.rotationYawHead = yaw;
    }
    
    public void lookAtPos(final BlockPos pos) {
        final float[] angle = MathUtil.calcAngle(RotationManager.mc.player.getPositionEyes(RotationManager.mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() + 0.5f), (double)(pos.getZ() + 0.5f)));
        this.setPlayerRotations(angle[0], angle[1]);
    }
    
    public void lookAtVec3d(final Vec3d vec3d) {
        final float[] angle = MathUtil.calcAngle(RotationManager.mc.player.getPositionEyes(RotationManager.mc.getRenderPartialTicks()), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
        this.setPlayerRotations(angle[0], angle[1]);
    }
    
    public void lookAtVec3d(final double x, final double y, final double z) {
        final Vec3d vec3d = new Vec3d(x, y, z);
        this.lookAtVec3d(vec3d);
    }
    
    public void lookAtEntity(final Entity entity) {
        final float[] angle = MathUtil.calcAngle(RotationManager.mc.player.getPositionEyes(RotationManager.mc.getRenderPartialTicks()), entity.getPositionEyes(RotationManager.mc.getRenderPartialTicks()));
        this.setPlayerRotations(angle[0], angle[1]);
    }
    
    public void setPlayerPitch(final float pitch) {
        RotationManager.mc.player.rotationPitch = pitch;
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
    
    public int getDirection4D() {
        return RotationUtil.getDirection4D();
    }
    
    public String getDirection4D(final boolean northRed) {
        return RotationUtil.getDirection4D(northRed);
    }
}
