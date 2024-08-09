/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HangingEntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFrameItem
extends HangingEntityItem {
    public ItemFrameItem(Item.Properties properties) {
        super(EntityType.ITEM_FRAME, properties);
    }

    @Override
    protected boolean canPlace(PlayerEntity playerEntity, Direction direction, ItemStack itemStack, BlockPos blockPos) {
        return !World.isOutsideBuildHeight(blockPos) && playerEntity.canPlayerEdit(blockPos, direction, itemStack);
    }
}

