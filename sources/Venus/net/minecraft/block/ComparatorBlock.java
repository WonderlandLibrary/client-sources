/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ComparatorMode;
import net.minecraft.tileentity.ComparatorTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ComparatorBlock
extends RedstoneDiodeBlock
implements ITileEntityProvider {
    public static final EnumProperty<ComparatorMode> MODE = BlockStateProperties.COMPARATOR_MODE;

    public ComparatorBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH)).with(POWERED, false)).with(MODE, ComparatorMode.COMPARE));
    }

    @Override
    protected int getDelay(BlockState blockState) {
        return 1;
    }

    @Override
    protected int getActiveSignal(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        TileEntity tileEntity = iBlockReader.getTileEntity(blockPos);
        return tileEntity instanceof ComparatorTileEntity ? ((ComparatorTileEntity)tileEntity).getOutputSignal() : 0;
    }

    private int calculateOutput(World world, BlockPos blockPos, BlockState blockState) {
        return blockState.get(MODE) == ComparatorMode.SUBTRACT ? Math.max(this.calculateInputStrength(world, blockPos, blockState) - this.getPowerOnSides(world, blockPos, blockState), 0) : this.calculateInputStrength(world, blockPos, blockState);
    }

    @Override
    protected boolean shouldBePowered(World world, BlockPos blockPos, BlockState blockState) {
        int n = this.calculateInputStrength(world, blockPos, blockState);
        if (n == 0) {
            return true;
        }
        int n2 = this.getPowerOnSides(world, blockPos, blockState);
        if (n > n2) {
            return false;
        }
        return n == n2 && blockState.get(MODE) == ComparatorMode.COMPARE;
    }

    @Override
    protected int calculateInputStrength(World world, BlockPos blockPos, BlockState blockState) {
        int n = super.calculateInputStrength(world, blockPos, blockState);
        Direction direction = blockState.get(HORIZONTAL_FACING);
        BlockPos blockPos2 = blockPos.offset(direction);
        BlockState blockState2 = world.getBlockState(blockPos2);
        if (blockState2.hasComparatorInputOverride()) {
            n = blockState2.getComparatorInputOverride(world, blockPos2);
        } else if (n < 15 && blockState2.isNormalCube(world, blockPos2)) {
            blockPos2 = blockPos2.offset(direction);
            blockState2 = world.getBlockState(blockPos2);
            ItemFrameEntity itemFrameEntity = this.findItemFrame(world, direction, blockPos2);
            int n2 = Math.max(itemFrameEntity == null ? Integer.MIN_VALUE : itemFrameEntity.getAnalogOutput(), blockState2.hasComparatorInputOverride() ? blockState2.getComparatorInputOverride(world, blockPos2) : Integer.MIN_VALUE);
            if (n2 != Integer.MIN_VALUE) {
                n = n2;
            }
        }
        return n;
    }

    @Nullable
    private ItemFrameEntity findItemFrame(World world, Direction direction, BlockPos blockPos) {
        List<ItemFrameEntity> list = world.getEntitiesWithinAABB(ItemFrameEntity.class, new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1), arg_0 -> ComparatorBlock.lambda$findItemFrame$0(direction, arg_0));
        return list.size() == 1 ? list.get(0) : null;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!playerEntity.abilities.allowEdit) {
            return ActionResultType.PASS;
        }
        float f = (blockState = (BlockState)blockState.func_235896_a_(MODE)).get(MODE) == ComparatorMode.SUBTRACT ? 0.55f : 0.5f;
        world.playSound(playerEntity, blockPos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3f, f);
        world.setBlockState(blockPos, blockState, 1);
        this.onStateChange(world, blockPos, blockState);
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    protected void updateState(World world, BlockPos blockPos, BlockState blockState) {
        if (!world.getPendingBlockTicks().isTickPending(blockPos, this)) {
            int n;
            int n2 = this.calculateOutput(world, blockPos, blockState);
            TileEntity tileEntity = world.getTileEntity(blockPos);
            int n3 = n = tileEntity instanceof ComparatorTileEntity ? ((ComparatorTileEntity)tileEntity).getOutputSignal() : 0;
            if (n2 != n || blockState.get(POWERED).booleanValue() != this.shouldBePowered(world, blockPos, blockState)) {
                TickPriority tickPriority = this.isFacingTowardsRepeater(world, blockPos, blockState) ? TickPriority.HIGH : TickPriority.NORMAL;
                world.getPendingBlockTicks().scheduleTick(blockPos, this, 2, tickPriority);
            }
        }
    }

    private void onStateChange(World world, BlockPos blockPos, BlockState blockState) {
        int n = this.calculateOutput(world, blockPos, blockState);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        int n2 = 0;
        if (tileEntity instanceof ComparatorTileEntity) {
            ComparatorTileEntity comparatorTileEntity = (ComparatorTileEntity)tileEntity;
            n2 = comparatorTileEntity.getOutputSignal();
            comparatorTileEntity.setOutputSignal(n);
        }
        if (n2 != n || blockState.get(MODE) == ComparatorMode.COMPARE) {
            boolean bl = this.shouldBePowered(world, blockPos, blockState);
            boolean bl2 = blockState.get(POWERED);
            if (bl2 && !bl) {
                world.setBlockState(blockPos, (BlockState)blockState.with(POWERED, false), 1);
            } else if (!bl2 && bl) {
                world.setBlockState(blockPos, (BlockState)blockState.with(POWERED, true), 1);
            }
            this.notifyNeighbors(world, blockPos, blockState);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        this.onStateChange(serverWorld, blockPos, blockState);
    }

    @Override
    public boolean eventReceived(BlockState blockState, World world, BlockPos blockPos, int n, int n2) {
        super.eventReceived(blockState, world, blockPos, n, n2);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity != null && tileEntity.receiveClientEvent(n, n2);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new ComparatorTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, MODE, POWERED);
    }

    private static boolean lambda$findItemFrame$0(Direction direction, ItemFrameEntity itemFrameEntity) {
        return itemFrameEntity != null && itemFrameEntity.getHorizontalFacing() == direction;
    }
}

