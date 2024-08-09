/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class AttackDamageEffect
extends Effect {
    protected final double bonusPerLevel;

    protected AttackDamageEffect(EffectType effectType, int n, double d) {
        super(effectType, n);
        this.bonusPerLevel = d;
    }

    @Override
    public double getAttributeModifierAmount(int n, AttributeModifier attributeModifier) {
        return this.bonusPerLevel * (double)(n + 1);
    }
}

