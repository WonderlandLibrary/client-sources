/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.DamageSource;

public abstract class SittingPhase
extends Phase {
    public SittingPhase(EnderDragonEntity enderDragonEntity) {
        super(enderDragonEntity);
    }

    @Override
    public boolean getIsStationary() {
        return false;
    }

    @Override
    public float func_221113_a(DamageSource damageSource, float f) {
        if (damageSource.getImmediateSource() instanceof AbstractArrowEntity) {
            damageSource.getImmediateSource().setFire(1);
            return 0.0f;
        }
        return super.func_221113_a(damageSource, f);
    }
}

