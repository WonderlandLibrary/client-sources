/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

public class WallOrFloorItem
extends BlockItem {
    protected final Block wallBlock;

    public WallOrFloorItem(Block block, Block block2, Item.Properties properties) {
        super(block, properties);
        this.wallBlock = block2;
    }

    @Override
    @Nullable
    protected BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = this.wallBlock.getStateForPlacement(blockItemUseContext);
        BlockState blockState2 = null;
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        for (Direction direction : blockItemUseContext.getNearestLookingDirections()) {
            BlockState blockState3;
            if (direction == Direction.UP) continue;
            BlockState blockState4 = blockState3 = direction == Direction.DOWN ? this.getBlock().getStateForPlacement(blockItemUseContext) : blockState;
            if (blockState3 == null || !blockState3.isValidPosition(world, blockPos)) continue;
            blockState2 = blockState3;
            break;
        }
        return blockState2 != null && world.placedBlockCollides(blockState2, blockPos, ISelectionContext.dummy()) ? blockState2 : null;
    }

    @Override
    public void addToBlockToItemMap(Map<Block, Item> map, Item item) {
        super.addToBlockToItemMap(map, item);
        map.put(this.wallBlock, item);
    }
}

