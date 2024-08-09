/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

public class ProtectionEnchantment
extends Enchantment {
    public final Type protectionType;

    public ProtectionEnchantment(Enchantment.Rarity rarity, Type type, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, type == Type.FALL ? EnchantmentType.ARMOR_FEET : EnchantmentType.ARMOR, equipmentSlotTypeArray);
        this.protectionType = type;
    }

    @Override
    public int getMinEnchantability(int n) {
        return this.protectionType.getMinimalEnchantability() + (n - 1) * this.protectionType.getEnchantIncreasePerLevel();
    }

    @Override
    public int getMaxEnchantability(int n) {
        return this.getMinEnchantability(n) + this.protectionType.getEnchantIncreasePerLevel();
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int calcModifierDamage(int n, DamageSource damageSource) {
        if (damageSource.canHarmInCreative()) {
            return 1;
        }
        if (this.protectionType == Type.ALL) {
            return n;
        }
        if (this.protectionType == Type.FIRE && damageSource.isFireDamage()) {
            return n * 2;
        }
        if (this.protectionType == Type.FALL && damageSource == DamageSource.FALL) {
            return n * 3;
        }
        if (this.protectionType == Type.EXPLOSION && damageSource.isExplosion()) {
            return n * 2;
        }
        return this.protectionType == Type.PROJECTILE && damageSource.isProjectile() ? n * 2 : 0;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        if (enchantment instanceof ProtectionEnchantment) {
            ProtectionEnchantment protectionEnchantment = (ProtectionEnchantment)enchantment;
            if (this.protectionType == protectionEnchantment.protectionType) {
                return true;
            }
            return this.protectionType == Type.FALL || protectionEnchantment.protectionType == Type.FALL;
        }
        return super.canApplyTogether(enchantment);
    }

    public static int getFireTimeForEntity(LivingEntity livingEntity, int n) {
        int n2 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FIRE_PROTECTION, livingEntity);
        if (n2 > 0) {
            n -= MathHelper.floor((float)n * (float)n2 * 0.15f);
        }
        return n;
    }

    public static double getBlastDamageReduction(LivingEntity livingEntity, double d) {
        int n = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.BLAST_PROTECTION, livingEntity);
        if (n > 0) {
            d -= (double)MathHelper.floor(d * (double)((float)n * 0.15f));
        }
        return d;
    }

    public static enum Type {
        ALL("all", 1, 11),
        FIRE("fire", 10, 8),
        FALL("fall", 5, 6),
        EXPLOSION("explosion", 5, 8),
        PROJECTILE("projectile", 3, 6);

        private final String typeName;
        private final int minEnchantability;
        private final int levelCost;

        private Type(String string2, int n2, int n3) {
            this.typeName = string2;
            this.minEnchantability = n2;
            this.levelCost = n3;
        }

        public int getMinimalEnchantability() {
            return this.minEnchantability;
        }

        public int getEnchantIncreasePerLevel() {
            return this.levelCost;
        }
    }
}

