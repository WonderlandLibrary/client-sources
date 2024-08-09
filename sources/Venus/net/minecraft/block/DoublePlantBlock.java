/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class DoublePlantBlock
extends BushBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public DoublePlantBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        DoubleBlockHalf doubleBlockHalf = blockState.get(HALF);
        if (direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || blockState2.isIn(this) && blockState2.get(HALF) != doubleBlockHalf) {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockPos blockPos = blockItemUseContext.getPos();
        return blockPos.getY() < 255 && blockItemUseContext.getWorld().getBlockState(blockPos.up()).isReplaceable(blockItemUseContext) ? super.getStateForPlacement(blockItemUseContext) : null;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        world.setBlockState(blockPos.up(), (BlockState)this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), 0);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        if (blockState.get(HALF) != DoubleBlockHalf.UPPER) {
            return super.isValidPosition(blockState, iWorldReader, blockPos);
        }
        BlockState blockState2 = iWorldReader.getBlockState(blockPos.down());
        return blockState2.isIn(this) && blockState2.get(HALF) == DoubleBlockHalf.LOWER;
    }

    public void placeAt(IWorld iWorld, BlockPos blockPos, int n) {
        iWorld.setBlockState(blockPos, (BlockState)this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER), n);
        iWorld.setBlockState(blockPos.up(), (BlockState)this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), n);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        if (!world.isRemote) {
            if (playerEntity.isCreative()) {
                DoublePlantBlock.removeBottomHalf(world, blockPos, blockState, playerEntity);
            } else {
                DoublePlantBlock.spawnDrops(blockState, world, blockPos, null, playerEntity, playerEntity.getHeldItemMainhand());
            }
        }
        super.onBlockHarvested(world, blockPos, blockState, playerEntity);
    }

    @Override
    public void harvestBlock(World world, PlayerEntity playerEntity, BlockPos blockPos, BlockState blockState, @Nullable TileEntity tileEntity, ItemStack itemStack) {
        super.harvestBlock(world, playerEntity, blockPos, Blocks.AIR.getDefaultState(), tileEntity, itemStack);
    }

    protected static void removeBottomHalf(World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {
        BlockPos blockPos2;
        BlockState blockState2;
        DoubleBlockHalf doubleBlockHalf = blockState.get(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER && (blockState2 = world.getBlockState(blockPos2 = blockPos.down())).getBlock() == blockState.getBlock() && blockState2.get(HALF) == DoubleBlockHalf.LOWER) {
            world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 0);
            world.playEvent(playerEntity, 2001, blockPos2, Block.getStateId(blockState2));
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    @Override
    public long getPositionRandom(BlockState blockState, BlockPos blockPos) {
        return MathHelper.getCoordinateRandom(blockPos.getX(), blockPos.down(blockState.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), blockPos.getZ());
    }
}

