/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WetSpongeBlock
extends Block {
    protected WetSpongeBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (world.getDimensionType().isUltrawarm()) {
            world.setBlockState(blockPos, Blocks.SPONGE.getDefaultState(), 0);
            world.playEvent(2009, blockPos, 0);
            world.playSound(null, blockPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, (1.0f + world.getRandom().nextFloat() * 0.2f) * 0.7f);
        }
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        Direction direction = Direction.getRandomDirection(random2);
        if (direction != Direction.UP) {
            BlockPos blockPos2 = blockPos.offset(direction);
            BlockState blockState2 = world.getBlockState(blockPos2);
            if (!blockState.isSolid() || !blockState2.isSolidSide(world, blockPos2, direction.getOpposite())) {
                double d = blockPos.getX();
                double d2 = blockPos.getY();
                double d3 = blockPos.getZ();
                if (direction == Direction.DOWN) {
                    d2 -= 0.05;
                    d += random2.nextDouble();
                    d3 += random2.nextDouble();
                } else {
                    d2 += random2.nextDouble() * 0.8;
                    if (direction.getAxis() == Direction.Axis.X) {
                        d3 += random2.nextDouble();
                        d = direction == Direction.EAST ? (d += 1.0) : (d += 0.05);
                    } else {
                        d += random2.nextDouble();
                        d3 = direction == Direction.SOUTH ? (d3 += 1.0) : (d3 += 0.05);
                    }
                }
                world.addParticle(ParticleTypes.DRIPPING_WATER, d, d2, d3, 0.0, 0.0, 0.0);
            }
        }
    }
}

