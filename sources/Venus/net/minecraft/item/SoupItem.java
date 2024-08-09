/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class SoupItem
extends Item {
    public SoupItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity livingEntity) {
        ItemStack itemStack2 = super.onItemUseFinish(itemStack, world, livingEntity);
        return livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.isCreativeMode ? itemStack2 : new ItemStack(Items.BOWL);
    }
}

