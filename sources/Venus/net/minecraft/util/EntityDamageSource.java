/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EntityDamageSource
extends DamageSource {
    @Nullable
    protected final Entity damageSourceEntity;
    private boolean isThornsDamage;

    public EntityDamageSource(String string, @Nullable Entity entity2) {
        super(string);
        this.damageSourceEntity = entity2;
    }

    public EntityDamageSource setIsThornsDamage() {
        this.isThornsDamage = true;
        return this;
    }

    public boolean getIsThornsDamage() {
        return this.isThornsDamage;
    }

    @Override
    @Nullable
    public Entity getTrueSource() {
        return this.damageSourceEntity;
    }

    @Override
    public ITextComponent getDeathMessage(LivingEntity livingEntity) {
        ItemStack itemStack = this.damageSourceEntity instanceof LivingEntity ? ((LivingEntity)this.damageSourceEntity).getHeldItemMainhand() : ItemStack.EMPTY;
        String string = "death.attack." + this.damageType;
        return !itemStack.isEmpty() && itemStack.hasDisplayName() ? new TranslationTextComponent(string + ".item", livingEntity.getDisplayName(), this.damageSourceEntity.getDisplayName(), itemStack.getTextComponent()) : new TranslationTextComponent(string, livingEntity.getDisplayName(), this.damageSourceEntity.getDisplayName());
    }

    @Override
    public boolean isDifficultyScaled() {
        return this.damageSourceEntity != null && this.damageSourceEntity instanceof LivingEntity && !(this.damageSourceEntity instanceof PlayerEntity);
    }

    @Override
    @Nullable
    public Vector3d getDamageLocation() {
        return this.damageSourceEntity != null ? this.damageSourceEntity.getPositionVec() : null;
    }

    @Override
    public String toString() {
        return "EntityDamageSource (" + this.damageSourceEntity + ")";
    }
}

