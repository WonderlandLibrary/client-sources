/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public interface IPhase {
    public boolean getIsStationary();

    public void clientTick();

    public void serverTick();

    public void onCrystalDestroyed(EnderCrystalEntity var1, BlockPos var2, DamageSource var3, @Nullable PlayerEntity var4);

    public void initPhase();

    public void removeAreaEffect();

    public float getMaxRiseOrFall();

    public float getYawFactor();

    public PhaseType<? extends IPhase> getType();

    @Nullable
    public Vector3d getTargetLocation();

    public float func_221113_a(DamageSource var1, float var2);
}

