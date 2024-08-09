/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class IndirectEntityDamageSource
extends EntityDamageSource {
    private final Entity indirectEntity;

    public IndirectEntityDamageSource(String string, Entity entity2, @Nullable Entity entity3) {
        super(string, entity2);
        this.indirectEntity = entity3;
    }

    @Override
    @Nullable
    public Entity getImmediateSource() {
        return this.damageSourceEntity;
    }

    @Override
    @Nullable
    public Entity getTrueSource() {
        return this.indirectEntity;
    }

    @Override
    public ITextComponent getDeathMessage(LivingEntity livingEntity) {
        ITextComponent iTextComponent = this.indirectEntity == null ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
        ItemStack itemStack = this.indirectEntity instanceof LivingEntity ? ((LivingEntity)this.indirectEntity).getHeldItemMainhand() : ItemStack.EMPTY;
        String string = "death.attack." + this.damageType;
        String string2 = string + ".item";
        return !itemStack.isEmpty() && itemStack.hasDisplayName() ? new TranslationTextComponent(string2, livingEntity.getDisplayName(), iTextComponent, itemStack.getTextComponent()) : new TranslationTextComponent(string, livingEntity.getDisplayName(), iTextComponent);
    }
}

