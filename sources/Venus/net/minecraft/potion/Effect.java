/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class Effect {
    private final Map<Attribute, AttributeModifier> attributeModifierMap = Maps.newHashMap();
    private final EffectType type;
    private final int liquidColor;
    @Nullable
    private String name;

    @Nullable
    public static Effect get(int n) {
        return (Effect)Registry.EFFECTS.getByValue(n);
    }

    public static int getId(Effect effect) {
        return Registry.EFFECTS.getId(effect);
    }

    protected Effect(EffectType effectType, int n) {
        this.type = effectType;
        this.liquidColor = n;
    }

    public void performEffect(LivingEntity livingEntity, int n) {
        if (this == Effects.REGENERATION) {
            if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
                livingEntity.heal(1.0f);
            }
        } else if (this == Effects.POISON) {
            if (livingEntity.getHealth() > 1.0f) {
                livingEntity.attackEntityFrom(DamageSource.MAGIC, 1.0f);
            }
        } else if (this == Effects.WITHER) {
            livingEntity.attackEntityFrom(DamageSource.WITHER, 1.0f);
        } else if (this == Effects.HUNGER && livingEntity instanceof PlayerEntity) {
            ((PlayerEntity)livingEntity).addExhaustion(0.005f * (float)(n + 1));
        } else if (this == Effects.SATURATION && livingEntity instanceof PlayerEntity) {
            if (!livingEntity.world.isRemote) {
                ((PlayerEntity)livingEntity).getFoodStats().addStats(n + 1, 1.0f);
            }
        } else if (!(this == Effects.INSTANT_HEALTH && !livingEntity.isEntityUndead() || this == Effects.INSTANT_DAMAGE && livingEntity.isEntityUndead())) {
            if (this == Effects.INSTANT_DAMAGE && !livingEntity.isEntityUndead() || this == Effects.INSTANT_HEALTH && livingEntity.isEntityUndead()) {
                livingEntity.attackEntityFrom(DamageSource.MAGIC, 6 << n);
            }
        } else {
            livingEntity.heal(Math.max(4 << n, 0));
        }
    }

    public void affectEntity(@Nullable Entity entity2, @Nullable Entity entity3, LivingEntity livingEntity, int n, double d) {
        if (!(this == Effects.INSTANT_HEALTH && !livingEntity.isEntityUndead() || this == Effects.INSTANT_DAMAGE && livingEntity.isEntityUndead())) {
            if (this == Effects.INSTANT_DAMAGE && !livingEntity.isEntityUndead() || this == Effects.INSTANT_HEALTH && livingEntity.isEntityUndead()) {
                int n2 = (int)(d * (double)(6 << n) + 0.5);
                if (entity2 == null) {
                    livingEntity.attackEntityFrom(DamageSource.MAGIC, n2);
                } else {
                    livingEntity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(entity2, entity3), n2);
                }
            } else {
                this.performEffect(livingEntity, n);
            }
        } else {
            int n3 = (int)(d * (double)(4 << n) + 0.5);
            livingEntity.heal(n3);
        }
    }

    public boolean isReady(int n, int n2) {
        if (this == Effects.REGENERATION) {
            int n3 = 50 >> n2;
            if (n3 > 0) {
                return n % n3 == 0;
            }
            return false;
        }
        if (this == Effects.POISON) {
            int n4 = 25 >> n2;
            if (n4 > 0) {
                return n % n4 == 0;
            }
            return false;
        }
        if (this == Effects.WITHER) {
            int n5 = 40 >> n2;
            if (n5 > 0) {
                return n % n5 == 0;
            }
            return false;
        }
        return this == Effects.HUNGER;
    }

    public boolean isInstant() {
        return true;
    }

    protected String getOrCreateDescriptionId() {
        if (this.name == null) {
            this.name = Util.makeTranslationKey("effect", Registry.EFFECTS.getKey(this));
        }
        return this.name;
    }

    public String getName() {
        return this.getOrCreateDescriptionId();
    }

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.getName());
    }

    public EffectType getEffectType() {
        return this.type;
    }

    public int getLiquidColor() {
        return this.liquidColor;
    }

    public Effect addAttributesModifier(Attribute attribute, String string, double d, AttributeModifier.Operation operation) {
        AttributeModifier attributeModifier = new AttributeModifier(UUID.fromString(string), this::getName, d, operation);
        this.attributeModifierMap.put(attribute, attributeModifier);
        return this;
    }

    public Map<Attribute, AttributeModifier> getAttributeModifierMap() {
        return this.attributeModifierMap;
    }

    public void removeAttributesModifiersFromEntity(LivingEntity livingEntity, AttributeModifierManager attributeModifierManager, int n) {
        for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
            ModifiableAttributeInstance modifiableAttributeInstance = attributeModifierManager.createInstanceIfAbsent(entry.getKey());
            if (modifiableAttributeInstance == null) continue;
            modifiableAttributeInstance.removeModifier(entry.getValue());
        }
    }

    public void applyAttributesModifiersToEntity(LivingEntity livingEntity, AttributeModifierManager attributeModifierManager, int n) {
        for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
            ModifiableAttributeInstance modifiableAttributeInstance = attributeModifierManager.createInstanceIfAbsent(entry.getKey());
            if (modifiableAttributeInstance == null) continue;
            AttributeModifier attributeModifier = entry.getValue();
            modifiableAttributeInstance.removeModifier(attributeModifier);
            modifiableAttributeInstance.applyPersistentModifier(new AttributeModifier(attributeModifier.getID(), this.getName() + " " + n, this.getAttributeModifierAmount(n, attributeModifier), attributeModifier.getOperation()));
        }
    }

    public double getAttributeModifierAmount(int n, AttributeModifier attributeModifier) {
        return attributeModifier.getAmount() * (double)(n + 1);
    }

    public boolean isBeneficial() {
        return this.type == EffectType.BENEFICIAL;
    }
}

