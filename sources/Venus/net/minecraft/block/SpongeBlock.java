/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpongeBlock
extends Block {
    protected SpongeBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.isIn(blockState.getBlock())) {
            this.tryAbsorb(world, blockPos);
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        this.tryAbsorb(world, blockPos);
        super.neighborChanged(blockState, world, blockPos, block, blockPos2, bl);
    }

    protected void tryAbsorb(World world, BlockPos blockPos) {
        if (this.absorb(world, blockPos)) {
            world.setBlockState(blockPos, Blocks.WET_SPONGE.getDefaultState(), 1);
            world.playEvent(2001, blockPos, Block.getStateId(Blocks.WATER.getDefaultState()));
        }
    }

    private boolean absorb(World world, BlockPos blockPos) {
        LinkedList<Tuple<BlockPos, Integer>> linkedList = Lists.newLinkedList();
        linkedList.add(new Tuple<BlockPos, Integer>(blockPos, 0));
        int n = 0;
        while (!linkedList.isEmpty()) {
            Tuple tuple = (Tuple)linkedList.poll();
            BlockPos blockPos2 = (BlockPos)tuple.getA();
            int n2 = (Integer)tuple.getB();
            for (Direction direction : Direction.values()) {
                BlockPos blockPos3 = blockPos2.offset(direction);
                BlockState blockState = world.getBlockState(blockPos3);
                FluidState fluidState = world.getFluidState(blockPos3);
                Material material = blockState.getMaterial();
                if (!fluidState.isTagged(FluidTags.WATER)) continue;
                if (blockState.getBlock() instanceof IBucketPickupHandler && ((IBucketPickupHandler)((Object)blockState.getBlock())).pickupFluid(world, blockPos3, blockState) != Fluids.EMPTY) {
                    ++n;
                    if (n2 >= 6) continue;
                    linkedList.add(new Tuple<BlockPos, Integer>(blockPos3, n2 + 1));
                    continue;
                }
                if (blockState.getBlock() instanceof FlowingFluidBlock) {
                    world.setBlockState(blockPos3, Blocks.AIR.getDefaultState(), 0);
                    ++n;
                    if (n2 >= 6) continue;
                    linkedList.add(new Tuple<BlockPos, Integer>(blockPos3, n2 + 1));
                    continue;
                }
                if (material != Material.OCEAN_PLANT && material != Material.SEA_GRASS) continue;
                TileEntity tileEntity = blockState.getBlock().isTileEntityProvider() ? world.getTileEntity(blockPos3) : null;
                SpongeBlock.spawnDrops(blockState, world, blockPos3, tileEntity);
                world.setBlockState(blockPos3, Blocks.AIR.getDefaultState(), 0);
                ++n;
                if (n2 >= 6) continue;
                linkedList.add(new Tuple<BlockPos, Integer>(blockPos3, n2 + 1));
            }
            if (n <= 64) continue;
            break;
        }
        return n > 0;
    }
}

