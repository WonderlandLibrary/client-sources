/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class DrinkHelper {
    public static ActionResult<ItemStack> startDrinking(World world, PlayerEntity playerEntity, Hand hand) {
        playerEntity.setActiveHand(hand);
        return ActionResult.resultConsume(playerEntity.getHeldItem(hand));
    }

    public static ItemStack fill(ItemStack itemStack, PlayerEntity playerEntity, ItemStack itemStack2, boolean bl) {
        boolean bl2 = playerEntity.abilities.isCreativeMode;
        if (bl && bl2) {
            if (!playerEntity.inventory.hasItemStack(itemStack2)) {
                playerEntity.inventory.addItemStackToInventory(itemStack2);
            }
            return itemStack;
        }
        if (!bl2) {
            itemStack.shrink(1);
        }
        if (itemStack.isEmpty()) {
            return itemStack2;
        }
        if (!playerEntity.inventory.addItemStackToInventory(itemStack2)) {
            playerEntity.dropItem(itemStack2, true);
        }
        return itemStack;
    }

    public static ItemStack fill(ItemStack itemStack, PlayerEntity playerEntity, ItemStack itemStack2) {
        return DrinkHelper.fill(itemStack, playerEntity, itemStack2, true);
    }
}

