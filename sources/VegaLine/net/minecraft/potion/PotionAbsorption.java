/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;

public class PotionAbsorption
extends Potion {
    protected PotionAbsorption(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        entityLivingBaseIn.setAbsorptionAmount(entityLivingBaseIn.getAbsorptionAmount() - (float)(4 * (amplifier + 1)));
        super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        entityLivingBaseIn.setAbsorptionAmount(entityLivingBaseIn.getAbsorptionAmount() + (float)(4 * (amplifier + 1)));
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
    }
}

