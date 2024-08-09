/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class DamageEnchantment
extends Enchantment {
    private static final String[] DAMAGE_NAMES = new String[]{"all", "undead", "arthropods"};
    private static final int[] MIN_COST = new int[]{1, 5, 5};
    private static final int[] LEVEL_COST = new int[]{11, 8, 8};
    private static final int[] LEVEL_COST_SPAN = new int[]{20, 20, 20};
    public final int damageType;

    public DamageEnchantment(Enchantment.Rarity rarity, int n, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.WEAPON, equipmentSlotTypeArray);
        this.damageType = n;
    }

    @Override
    public int getMinEnchantability(int n) {
        return MIN_COST[this.damageType] + (n - 1) * LEVEL_COST[this.damageType];
    }

    @Override
    public int getMaxEnchantability(int n) {
        return this.getMinEnchantability(n) + LEVEL_COST_SPAN[this.damageType];
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public float calcDamageByCreature(int n, CreatureAttribute creatureAttribute) {
        if (this.damageType == 0) {
            return 1.0f + (float)Math.max(0, n - 1) * 0.5f;
        }
        if (this.damageType == 1 && creatureAttribute == CreatureAttribute.UNDEAD) {
            return (float)n * 2.5f;
        }
        return this.damageType == 2 && creatureAttribute == CreatureAttribute.ARTHROPOD ? (float)n * 2.5f : 0.0f;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return !(enchantment instanceof DamageEnchantment);
    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return itemStack.getItem() instanceof AxeItem ? true : super.canApply(itemStack);
    }

    @Override
    public void onEntityDamaged(LivingEntity livingEntity, Entity entity2, int n) {
        if (entity2 instanceof LivingEntity) {
            LivingEntity livingEntity2 = (LivingEntity)entity2;
            if (this.damageType == 2 && livingEntity2.getCreatureAttribute() == CreatureAttribute.ARTHROPOD) {
                int n2 = 20 + livingEntity.getRNG().nextInt(10 * n);
                livingEntity2.addPotionEffect(new EffectInstance(Effects.SLOWNESS, n2, 3));
            }
        }
    }
}

