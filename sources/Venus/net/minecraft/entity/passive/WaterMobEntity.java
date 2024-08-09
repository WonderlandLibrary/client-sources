/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class WaterMobEntity
extends CreatureEntity {
    protected WaterMobEntity(EntityType<? extends WaterMobEntity> entityType, World world) {
        super((EntityType<? extends CreatureEntity>)entityType, world);
        this.setPathPriority(PathNodeType.WATER, 0.0f);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return false;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.WATER;
    }

    @Override
    public boolean isNotColliding(IWorldReader iWorldReader) {
        return iWorldReader.checkNoEntityCollision(this);
    }

    @Override
    public int getTalkInterval() {
        return 1;
    }

    @Override
    protected int getExperiencePoints(PlayerEntity playerEntity) {
        return 1 + this.world.rand.nextInt(3);
    }

    protected void updateAir(int n) {
        if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
            this.setAir(n - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 2.0f);
            }
        } else {
            this.setAir(300);
        }
    }

    @Override
    public void baseTick() {
        int n = this.getAir();
        super.baseTick();
        this.updateAir(n);
    }

    @Override
    public boolean isPushedByWater() {
        return true;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity playerEntity) {
        return true;
    }
}

