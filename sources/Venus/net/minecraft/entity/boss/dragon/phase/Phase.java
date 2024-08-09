/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.IPhase;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public abstract class Phase
implements IPhase {
    protected final EnderDragonEntity dragon;

    public Phase(EnderDragonEntity enderDragonEntity) {
        this.dragon = enderDragonEntity;
    }

    @Override
    public boolean getIsStationary() {
        return true;
    }

    @Override
    public void clientTick() {
    }

    @Override
    public void serverTick() {
    }

    @Override
    public void onCrystalDestroyed(EnderCrystalEntity enderCrystalEntity, BlockPos blockPos, DamageSource damageSource, @Nullable PlayerEntity playerEntity) {
    }

    @Override
    public void initPhase() {
    }

    @Override
    public void removeAreaEffect() {
    }

    @Override
    public float getMaxRiseOrFall() {
        return 0.6f;
    }

    @Override
    @Nullable
    public Vector3d getTargetLocation() {
        return null;
    }

    @Override
    public float func_221113_a(DamageSource damageSource, float f) {
        return f;
    }

    @Override
    public float getYawFactor() {
        float f = MathHelper.sqrt(Entity.horizontalMag(this.dragon.getMotion())) + 1.0f;
        float f2 = Math.min(f, 40.0f);
        return 0.7f / f2 / f;
    }
}

