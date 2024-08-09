/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MyceliumBlock
extends SpreadableSnowyDirtBlock {
    public MyceliumBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        super.animateTick(blockState, world, blockPos, random2);
        if (random2.nextInt(10) == 0) {
            world.addParticle(ParticleTypes.MYCELIUM, (double)blockPos.getX() + random2.nextDouble(), (double)blockPos.getY() + 1.1, (double)blockPos.getZ() + random2.nextDouble(), 0.0, 0.0, 0.0);
        }
    }
}

