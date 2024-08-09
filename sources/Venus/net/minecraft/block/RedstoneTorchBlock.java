/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RedstoneTorchBlock
extends TorchBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final Map<IBlockReader, List<Toggle>> BURNED_TORCHES = new WeakHashMap<IBlockReader, List<Toggle>>();

    protected RedstoneTorchBlock(AbstractBlock.Properties properties) {
        super(properties, RedstoneParticleData.REDSTONE_DUST);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(LIT, true));
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        for (Direction direction : Direction.values()) {
            world.notifyNeighborsOfStateChange(blockPos.offset(direction), this);
        }
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!bl) {
            for (Direction direction : Direction.values()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(direction), this);
            }
        }
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.get(LIT) != false && Direction.UP != direction ? 15 : 0;
    }

    protected boolean shouldBeOff(World world, BlockPos blockPos, BlockState blockState) {
        return world.isSidePowered(blockPos.down(), Direction.DOWN);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        boolean bl = this.shouldBeOff(serverWorld, blockPos, blockState);
        List<Toggle> list = BURNED_TORCHES.get(serverWorld);
        while (list != null && !list.isEmpty() && serverWorld.getGameTime() - list.get((int)0).time > 60L) {
            list.remove(0);
        }
        if (blockState.get(LIT).booleanValue()) {
            if (bl) {
                serverWorld.setBlockState(blockPos, (BlockState)blockState.with(LIT, false), 0);
                if (RedstoneTorchBlock.isBurnedOut(serverWorld, blockPos, true)) {
                    serverWorld.playEvent(1502, blockPos, 0);
                    serverWorld.getPendingBlockTicks().scheduleTick(blockPos, serverWorld.getBlockState(blockPos).getBlock(), 160);
                }
            }
        } else if (!bl && !RedstoneTorchBlock.isBurnedOut(serverWorld, blockPos, false)) {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.with(LIT, true), 0);
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (blockState.get(LIT).booleanValue() == this.shouldBeOff(world, blockPos, blockState) && !world.getPendingBlockTicks().isTickPending(blockPos, this)) {
            world.getPendingBlockTicks().scheduleTick(blockPos, this, 2);
        }
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return direction == Direction.DOWN ? blockState.getWeakPower(iBlockReader, blockPos, direction) : 0;
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        if (blockState.get(LIT).booleanValue()) {
            double d = (double)blockPos.getX() + 0.5 + (random2.nextDouble() - 0.5) * 0.2;
            double d2 = (double)blockPos.getY() + 0.7 + (random2.nextDouble() - 0.5) * 0.2;
            double d3 = (double)blockPos.getZ() + 0.5 + (random2.nextDouble() - 0.5) * 0.2;
            world.addParticle(this.particleData, d, d2, d3, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    private static boolean isBurnedOut(World world, BlockPos blockPos, boolean bl) {
        List list = BURNED_TORCHES.computeIfAbsent(world, RedstoneTorchBlock::lambda$isBurnedOut$0);
        if (bl) {
            list.add(new Toggle(blockPos.toImmutable(), world.getGameTime()));
        }
        int n = 0;
        for (int i = 0; i < list.size(); ++i) {
            Toggle toggle = (Toggle)list.get(i);
            if (!toggle.pos.equals(blockPos) || ++n < 8) continue;
            return false;
        }
        return true;
    }

    private static List lambda$isBurnedOut$0(IBlockReader iBlockReader) {
        return Lists.newArrayList();
    }

    public static class Toggle {
        private final BlockPos pos;
        private final long time;

        public Toggle(BlockPos blockPos, long l) {
            this.pos = blockPos;
            this.time = l;
        }
    }
}

