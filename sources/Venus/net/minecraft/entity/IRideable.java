/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import net.minecraft.entity.BoostHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public interface IRideable {
    public boolean boost();

    public void travelTowards(Vector3d var1);

    public float getMountedSpeed();

    default public boolean ride(MobEntity mobEntity, BoostHelper boostHelper, Vector3d vector3d) {
        Entity entity2;
        if (!mobEntity.isAlive()) {
            return true;
        }
        Entity entity3 = entity2 = mobEntity.getPassengers().isEmpty() ? null : mobEntity.getPassengers().get(0);
        if (mobEntity.isBeingRidden() && mobEntity.canBeSteered() && entity2 instanceof PlayerEntity) {
            mobEntity.prevRotationYaw = mobEntity.rotationYaw = entity2.rotationYaw;
            mobEntity.rotationPitch = entity2.rotationPitch * 0.5f;
            mobEntity.setRotation(mobEntity.rotationYaw, mobEntity.rotationPitch);
            mobEntity.renderYawOffset = mobEntity.rotationYaw;
            mobEntity.rotationYawHead = mobEntity.rotationYaw;
            mobEntity.stepHeight = 1.0f;
            mobEntity.jumpMovementFactor = mobEntity.getAIMoveSpeed() * 0.1f;
            if (boostHelper.saddledRaw && boostHelper.field_233611_b_++ > boostHelper.boostTimeRaw) {
                boostHelper.saddledRaw = false;
            }
            if (mobEntity.canPassengerSteer()) {
                float f = this.getMountedSpeed();
                if (boostHelper.saddledRaw) {
                    f += f * 1.15f * MathHelper.sin((float)boostHelper.field_233611_b_ / (float)boostHelper.boostTimeRaw * (float)Math.PI);
                }
                mobEntity.setAIMoveSpeed(f);
                this.travelTowards(new Vector3d(0.0, 0.0, 1.0));
                mobEntity.newPosRotationIncrements = 0;
            } else {
                mobEntity.func_233629_a_(mobEntity, true);
                mobEntity.setMotion(Vector3d.ZERO);
            }
            return false;
        }
        mobEntity.stepHeight = 0.5f;
        mobEntity.jumpMovementFactor = 0.02f;
        this.travelTowards(vector3d);
        return true;
    }
}

