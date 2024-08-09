/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class FlowerPotBlock
extends Block {
    private static final Map<Block, Block> POTTED_CONTENT = Maps.newHashMap();
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
    private final Block flower;

    public FlowerPotBlock(Block block, AbstractBlock.Properties properties) {
        super(properties);
        this.flower = block;
        POTTED_CONTENT.put(block, this);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        boolean bl;
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        Item item = itemStack.getItem();
        Block block = item instanceof BlockItem ? POTTED_CONTENT.getOrDefault(((BlockItem)item).getBlock(), Blocks.AIR) : Blocks.AIR;
        boolean bl2 = block == Blocks.AIR;
        boolean bl3 = bl = this.flower == Blocks.AIR;
        if (bl2 != bl) {
            if (bl) {
                world.setBlockState(blockPos, block.getDefaultState(), 0);
                playerEntity.addStat(Stats.POT_FLOWER);
                if (!playerEntity.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
            } else {
                ItemStack itemStack2 = new ItemStack(this.flower);
                if (itemStack.isEmpty()) {
                    playerEntity.setHeldItem(hand, itemStack2);
                } else if (!playerEntity.addItemStackToInventory(itemStack2)) {
                    playerEntity.dropItem(itemStack2, true);
                }
                world.setBlockState(blockPos, Blocks.FLOWER_POT.getDefaultState(), 0);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return this.flower == Blocks.AIR ? super.getItem(iBlockReader, blockPos, blockState) : new ItemStack(this.flower);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction == Direction.DOWN && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    public Block getFlower() {
        return this.flower;
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

