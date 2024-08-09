/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CryingObsidianBlock
extends Block {
    public CryingObsidianBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        Direction direction;
        if (random2.nextInt(5) == 0 && (direction = Direction.getRandomDirection(random2)) != Direction.UP) {
            BlockPos blockPos2 = blockPos.offset(direction);
            BlockState blockState2 = world.getBlockState(blockPos2);
            if (!blockState.isSolid() || !blockState2.isSolidSide(world, blockPos2, direction.getOpposite())) {
                double d = direction.getXOffset() == 0 ? random2.nextDouble() : 0.5 + (double)direction.getXOffset() * 0.6;
                double d2 = direction.getYOffset() == 0 ? random2.nextDouble() : 0.5 + (double)direction.getYOffset() * 0.6;
                double d3 = direction.getZOffset() == 0 ? random2.nextDouble() : 0.5 + (double)direction.getZOffset() * 0.6;
                world.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, (double)blockPos.getX() + d, (double)blockPos.getY() + d2, (double)blockPos.getZ() + d3, 0.0, 0.0, 0.0);
            }
        }
    }
}

