/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;

public class BedItem
extends BlockItem {
    public BedItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext blockItemUseContext, BlockState blockState) {
        return blockItemUseContext.getWorld().setBlockState(blockItemUseContext.getPos(), blockState, 1);
    }
}

