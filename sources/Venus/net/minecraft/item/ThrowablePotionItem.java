/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ThrowablePotionItem
extends PotionItem {
    public ThrowablePotionItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (!world.isRemote) {
            PotionEntity potionEntity = new PotionEntity(world, playerEntity);
            potionEntity.setItem(itemStack);
            potionEntity.func_234612_a_(playerEntity, playerEntity.rotationPitch, playerEntity.rotationYaw, -20.0f, 0.5f, 1.0f);
            world.addEntity(potionEntity);
        }
        playerEntity.addStat(Stats.ITEM_USED.get(this));
        if (!playerEntity.abilities.isCreativeMode) {
            itemStack.shrink(1);
        }
        return ActionResult.func_233538_a_(itemStack, world.isRemote());
    }
}

