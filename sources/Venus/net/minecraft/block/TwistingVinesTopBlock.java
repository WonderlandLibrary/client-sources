/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlockHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;

public class TwistingVinesTopBlock
extends AbstractTopPlantBlock {
    public static final VoxelShape SHAPE = Block.makeCuboidShape(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);

    public TwistingVinesTopBlock(AbstractBlock.Properties properties) {
        super(properties, Direction.UP, SHAPE, false, 0.1);
    }

    @Override
    protected int getGrowthAmount(Random random2) {
        return PlantBlockHelper.getGrowthAmount(random2);
    }

    @Override
    protected Block getBodyPlantBlock() {
        return Blocks.TWISTING_VINES_PLANT;
    }

    @Override
    protected boolean canGrowIn(BlockState blockState) {
        return PlantBlockHelper.isAir(blockState);
    }
}

