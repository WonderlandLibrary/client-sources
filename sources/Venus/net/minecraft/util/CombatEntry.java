/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;

public class CombatEntry {
    private final DamageSource damageSrc;
    private final int time;
    private final float damage;
    private final float health;
    private final String fallSuffix;
    private final float fallDistance;

    public CombatEntry(DamageSource damageSource, int n, float f, float f2, String string, float f3) {
        this.damageSrc = damageSource;
        this.time = n;
        this.damage = f2;
        this.health = f;
        this.fallSuffix = string;
        this.fallDistance = f3;
    }

    public DamageSource getDamageSrc() {
        return this.damageSrc;
    }

    public float getDamage() {
        return this.damage;
    }

    public boolean isLivingDamageSrc() {
        return this.damageSrc.getTrueSource() instanceof LivingEntity;
    }

    @Nullable
    public String getFallSuffix() {
        return this.fallSuffix;
    }

    @Nullable
    public ITextComponent getDamageSrcDisplayName() {
        return this.getDamageSrc().getTrueSource() == null ? null : this.getDamageSrc().getTrueSource().getDisplayName();
    }

    public float getDamageAmount() {
        return this.damageSrc == DamageSource.OUT_OF_WORLD ? Float.MAX_VALUE : this.fallDistance;
    }
}

