/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class AbsorptionEffect
extends Effect {
    protected AbsorptionEffect(EffectType effectType, int n) {
        super(effectType, n);
    }

    @Override
    public void removeAttributesModifiersFromEntity(LivingEntity livingEntity, AttributeModifierManager attributeModifierManager, int n) {
        livingEntity.setAbsorptionAmount(livingEntity.getAbsorptionAmount() - (float)(4 * (n + 1)));
        super.removeAttributesModifiersFromEntity(livingEntity, attributeModifierManager, n);
    }

    @Override
    public void applyAttributesModifiersToEntity(LivingEntity livingEntity, AttributeModifierManager attributeModifierManager, int n) {
        livingEntity.setAbsorptionAmount(livingEntity.getAbsorptionAmount() + (float)(4 * (n + 1)));
        super.applyAttributesModifiersToEntity(livingEntity, attributeModifierManager, n);
    }
}

