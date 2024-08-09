/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;

public class TallBlockItem
extends BlockItem {
    public TallBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext blockItemUseContext, BlockState blockState) {
        blockItemUseContext.getWorld().setBlockState(blockItemUseContext.getPos().up(), Blocks.AIR.getDefaultState(), 0);
        return super.placeBlock(blockItemUseContext, blockState);
    }
}

