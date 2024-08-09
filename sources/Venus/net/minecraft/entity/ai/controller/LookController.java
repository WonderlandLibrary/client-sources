/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.controller;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class LookController {
    protected final MobEntity mob;
    protected float deltaLookYaw;
    protected float deltaLookPitch;
    protected boolean isLooking;
    protected double posX;
    protected double posY;
    protected double posZ;

    public LookController(MobEntity mobEntity) {
        this.mob = mobEntity;
    }

    public void setLookPosition(Vector3d vector3d) {
        this.setLookPosition(vector3d.x, vector3d.y, vector3d.z);
    }

    public void setLookPositionWithEntity(Entity entity2, float f, float f2) {
        this.setLookPosition(entity2.getPosX(), LookController.getEyePosition(entity2), entity2.getPosZ(), f, f2);
    }

    public void setLookPosition(double d, double d2, double d3) {
        this.setLookPosition(d, d2, d3, this.mob.getFaceRotSpeed(), this.mob.getVerticalFaceSpeed());
    }

    public void setLookPosition(double d, double d2, double d3, float f, float f2) {
        this.posX = d;
        this.posY = d2;
        this.posZ = d3;
        this.deltaLookYaw = f;
        this.deltaLookPitch = f2;
        this.isLooking = true;
    }

    public void tick() {
        if (this.shouldResetPitch()) {
            this.mob.rotationPitch = 0.0f;
        }
        if (this.isLooking) {
            this.isLooking = false;
            this.mob.rotationYawHead = this.clampedRotate(this.mob.rotationYawHead, this.getTargetYaw(), this.deltaLookYaw);
            this.mob.rotationPitch = this.clampedRotate(this.mob.rotationPitch, this.getTargetPitch(), this.deltaLookPitch);
        } else {
            this.mob.rotationYawHead = this.clampedRotate(this.mob.rotationYawHead, this.mob.renderYawOffset, 10.0f);
        }
        if (!this.mob.getNavigator().noPath()) {
            this.mob.rotationYawHead = MathHelper.func_219800_b(this.mob.rotationYawHead, this.mob.renderYawOffset, this.mob.getHorizontalFaceSpeed());
        }
    }

    protected boolean shouldResetPitch() {
        return false;
    }

    public boolean getIsLooking() {
        return this.isLooking;
    }

    public double getLookPosX() {
        return this.posX;
    }

    public double getLookPosY() {
        return this.posY;
    }

    public double getLookPosZ() {
        return this.posZ;
    }

    protected float getTargetPitch() {
        double d = this.posX - this.mob.getPosX();
        double d2 = this.posY - this.mob.getPosYEye();
        double d3 = this.posZ - this.mob.getPosZ();
        double d4 = MathHelper.sqrt(d * d + d3 * d3);
        return (float)(-(MathHelper.atan2(d2, d4) * 57.2957763671875));
    }

    protected float getTargetYaw() {
        double d = this.posX - this.mob.getPosX();
        double d2 = this.posZ - this.mob.getPosZ();
        return (float)(MathHelper.atan2(d2, d) * 57.2957763671875) - 90.0f;
    }

    protected float clampedRotate(float f, float f2, float f3) {
        float f4 = MathHelper.wrapSubtractDegrees(f, f2);
        float f5 = MathHelper.clamp(f4, -f3, f3);
        return f + f5;
    }

    private static double getEyePosition(Entity entity2) {
        return entity2 instanceof LivingEntity ? entity2.getPosYEye() : (entity2.getBoundingBox().minY + entity2.getBoundingBox().maxY) / 2.0;
    }
}

