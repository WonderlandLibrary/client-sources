/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AbstractMapItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MapItem
extends AbstractMapItem {
    public MapItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = FilledMapItem.setupNewMap(world, MathHelper.floor(playerEntity.getPosX()), MathHelper.floor(playerEntity.getPosZ()), (byte)0, true, false);
        ItemStack itemStack2 = playerEntity.getHeldItem(hand);
        if (!playerEntity.abilities.isCreativeMode) {
            itemStack2.shrink(1);
        }
        playerEntity.addStat(Stats.ITEM_USED.get(this));
        playerEntity.playSound(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1.0f, 1.0f);
        if (itemStack2.isEmpty()) {
            return ActionResult.func_233538_a_(itemStack, world.isRemote());
        }
        if (!playerEntity.inventory.addItemStackToInventory(itemStack.copy())) {
            playerEntity.dropItem(itemStack, true);
        }
        return ActionResult.func_233538_a_(itemStack2, world.isRemote());
    }
}

