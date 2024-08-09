/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class SuspiciousStewItem
extends Item {
    public SuspiciousStewItem(Item.Properties properties) {
        super(properties);
    }

    public static void addEffect(ItemStack itemStack, Effect effect, int n) {
        CompoundNBT compoundNBT = itemStack.getOrCreateTag();
        ListNBT listNBT = compoundNBT.getList("Effects", 9);
        CompoundNBT compoundNBT2 = new CompoundNBT();
        compoundNBT2.putByte("EffectId", (byte)Effect.getId(effect));
        compoundNBT2.putInt("EffectDuration", n);
        listNBT.add(compoundNBT2);
        compoundNBT.put("Effects", listNBT);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity livingEntity) {
        ItemStack itemStack2 = super.onItemUseFinish(itemStack, world, livingEntity);
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null && compoundNBT.contains("Effects", 0)) {
            ListNBT listNBT = compoundNBT.getList("Effects", 10);
            for (int i = 0; i < listNBT.size(); ++i) {
                Effect effect;
                int n = 160;
                CompoundNBT compoundNBT2 = listNBT.getCompound(i);
                if (compoundNBT2.contains("EffectDuration", 0)) {
                    n = compoundNBT2.getInt("EffectDuration");
                }
                if ((effect = Effect.get(compoundNBT2.getByte("EffectId"))) == null) continue;
                livingEntity.addPotionEffect(new EffectInstance(effect, n));
            }
        }
        return livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.isCreativeMode ? itemStack2 : new ItemStack(Items.BOWL);
    }
}

