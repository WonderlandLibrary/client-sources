/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;

public class OperatorOnlyItem
extends BlockItem {
    public OperatorOnlyItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    @Nullable
    protected BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        PlayerEntity playerEntity = blockItemUseContext.getPlayer();
        return playerEntity != null && !playerEntity.canUseCommandBlock() ? null : super.getStateForPlacement(blockItemUseContext);
    }
}

