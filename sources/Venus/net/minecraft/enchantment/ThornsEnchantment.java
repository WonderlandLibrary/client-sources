/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import java.util.Map;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ThornsEnchantment
extends Enchantment {
    public ThornsEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.ARMOR_CHEST, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 10 + 20 * (n - 1);
    }

    @Override
    public int getMaxEnchantability(int n) {
        return super.getMinEnchantability(n) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return itemStack.getItem() instanceof ArmorItem ? true : super.canApply(itemStack);
    }

    @Override
    public void onUserHurt(LivingEntity livingEntity, Entity entity2, int n) {
        Random random2 = livingEntity.getRNG();
        Map.Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomItemWithEnchantment(Enchantments.THORNS, livingEntity);
        if (ThornsEnchantment.shouldHit(n, random2)) {
            if (entity2 != null) {
                entity2.attackEntityFrom(DamageSource.causeThornsDamage(livingEntity), ThornsEnchantment.getDamage(n, random2));
            }
            if (entry != null) {
                entry.getValue().damageItem(2, livingEntity, arg_0 -> ThornsEnchantment.lambda$onUserHurt$0(entry, arg_0));
            }
        }
    }

    public static boolean shouldHit(int n, Random random2) {
        if (n <= 0) {
            return true;
        }
        return random2.nextFloat() < 0.15f * (float)n;
    }

    public static int getDamage(int n, Random random2) {
        return n > 10 ? n - 10 : 1 + random2.nextInt(4);
    }

    private static void lambda$onUserHurt$0(Map.Entry entry, LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation((EquipmentSlotType)((Object)entry.getKey()));
    }
}

