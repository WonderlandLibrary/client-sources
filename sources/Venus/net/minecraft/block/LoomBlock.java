/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.LoomContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class LoomBlock
extends HorizontalBlock {
    private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.loom");

    protected LoomBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        playerEntity.openContainer(blockState.getContainer(world, blockPos));
        playerEntity.addStat(Stats.INTERACT_WITH_LOOM);
        return ActionResultType.CONSUME;
    }

    @Override
    public INamedContainerProvider getContainer(BlockState blockState, World world, BlockPos blockPos) {
        return new SimpleNamedContainerProvider((arg_0, arg_1, arg_2) -> LoomBlock.lambda$getContainer$0(world, blockPos, arg_0, arg_1, arg_2), CONTAINER_NAME);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(HORIZONTAL_FACING, blockItemUseContext.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    private static Container lambda$getContainer$0(World world, BlockPos blockPos, int n, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new LoomContainer(n, playerInventory, IWorldPosCallable.of(world, blockPos));
    }
}

