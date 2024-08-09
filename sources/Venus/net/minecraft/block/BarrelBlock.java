/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BarrelBlock
extends ContainerBlock {
    public static final DirectionProperty PROPERTY_FACING = BlockStateProperties.FACING;
    public static final BooleanProperty PROPERTY_OPEN = BlockStateProperties.OPEN;

    public BarrelBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(PROPERTY_FACING, Direction.NORTH)).with(PROPERTY_OPEN, false));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof BarrelTileEntity) {
            playerEntity.openContainer((BarrelTileEntity)tileEntity);
            playerEntity.addStat(Stats.OPEN_BARREL);
            PiglinTasks.func_234478_a_(playerEntity, true);
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isIn(blockState2.getBlock())) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof IInventory) {
                InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)((Object)tileEntity));
                world.updateComparatorOutputLevel(blockPos, this);
            }
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        TileEntity tileEntity = serverWorld.getTileEntity(blockPos);
        if (tileEntity instanceof BarrelTileEntity) {
            ((BarrelTileEntity)tileEntity).barrelTick();
        }
    }

    @Override
    @Nullable
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new BarrelTileEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        TileEntity tileEntity;
        if (itemStack.hasDisplayName() && (tileEntity = world.getTileEntity(blockPos)) instanceof BarrelTileEntity) {
            ((BarrelTileEntity)tileEntity).setCustomName(itemStack.getDisplayName());
        }
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        return Container.calcRedstone(world.getTileEntity(blockPos));
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return (BlockState)blockState.with(PROPERTY_FACING, rotation.rotate(blockState.get(PROPERTY_FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.toRotation(blockState.get(PROPERTY_FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PROPERTY_FACING, PROPERTY_OPEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(PROPERTY_FACING, blockItemUseContext.getNearestLookingDirection().getOpposite());
    }
}

