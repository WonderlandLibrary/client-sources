/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.DaylightDetectorTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class DaylightDetectorBlock
extends ContainerBlock {
    public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;
    public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);

    public DaylightDetectorBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(POWER, 0)).with(INVERTED, false));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    public boolean isTransparent(BlockState blockState) {
        return false;
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.get(POWER);
    }

    public static void updatePower(BlockState blockState, World world, BlockPos blockPos) {
        if (world.getDimensionType().hasSkyLight()) {
            int n = world.getLightFor(LightType.SKY, blockPos) - world.getSkylightSubtracted();
            float f = world.getCelestialAngleRadians(1.0f);
            boolean bl = blockState.get(INVERTED);
            if (bl) {
                n = 15 - n;
            } else if (n > 0) {
                float f2 = f < (float)Math.PI ? 0.0f : (float)Math.PI * 2;
                f += (f2 - f) * 0.2f;
                n = Math.round((float)n * MathHelper.cos(f));
            }
            n = MathHelper.clamp(n, 0, 15);
            if (blockState.get(POWER) != n) {
                world.setBlockState(blockPos, (BlockState)blockState.with(POWER, n), 0);
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (playerEntity.isAllowEdit()) {
            if (world.isRemote) {
                return ActionResultType.SUCCESS;
            }
            BlockState blockState2 = (BlockState)blockState.func_235896_a_(INVERTED);
            world.setBlockState(blockPos, blockState2, 1);
            DaylightDetectorBlock.updatePower(blockState2, world, blockPos);
            return ActionResultType.CONSUME;
        }
        return super.onBlockActivated(blockState, world, blockPos, playerEntity, hand, blockRayTraceResult);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new DaylightDetectorTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWER, INVERTED);
    }
}

