/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.lua.classes;

import mpp.venusfr.scripts.lua.classes.VectorClass;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityClass {
    private final Entity entity;

    public EntityClass(Entity entity2) {
        this.entity = entity2;
    }

    public boolean isOnGround() {
        return this.entity.isOnGround();
    }

    public VectorClass getPosition() {
        return new VectorClass(this.entity.getPositionVec());
    }

    public int getHurtTime() {
        Entity entity2 = this.entity;
        if (entity2 instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity2;
            return livingEntity.hurtTime;
        }
        return 1;
    }

    public double getHealth() {
        Entity entity2 = this.entity;
        if (entity2 instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity2;
            return livingEntity.getHealth();
        }
        return 0.0;
    }

    public double getMaxHealth() {
        Entity entity2 = this.entity;
        if (entity2 instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity2;
            return livingEntity.getMaxHealth();
        }
        return 0.0;
    }

    public int getEntityID() {
        return this.entity.getEntityId();
    }

    public void jump() {
        Entity entity2 = this.entity;
        if (entity2 instanceof ClientPlayerEntity) {
            ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)entity2;
            clientPlayerEntity.jump();
        }
    }

    public boolean isInWater() {
        return this.entity.isInWater();
    }

    public boolean isAlive() {
        return this.entity.isAlive();
    }

    public double getDistance(EntityClass entityClass) {
        return this.entity.getDistance(entityClass.entity);
    }

    public double getYaw() {
        return this.entity.rotationYaw;
    }

    public double getPitch() {
        return this.entity.rotationPitch;
    }

    public boolean isSneaking() {
        Entity entity2 = this.entity;
        if (entity2 instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)entity2;
            return playerEntity.isCrouching();
        }
        return true;
    }
}

