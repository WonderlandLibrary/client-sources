/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class HealthBoostEffect
extends Effect {
    public HealthBoostEffect(EffectType effectType, int n) {
        super(effectType, n);
    }

    @Override
    public void removeAttributesModifiersFromEntity(LivingEntity livingEntity, AttributeModifierManager attributeModifierManager, int n) {
        super.removeAttributesModifiersFromEntity(livingEntity, attributeModifierManager, n);
        if (livingEntity.getHealth() > livingEntity.getMaxHealth()) {
            livingEntity.setHealth(livingEntity.getMaxHealth());
        }
    }
}

