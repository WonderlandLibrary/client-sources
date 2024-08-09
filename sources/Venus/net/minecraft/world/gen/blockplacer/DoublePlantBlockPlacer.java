/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

public class DoublePlantBlockPlacer
extends BlockPlacer {
    public static final Codec<DoublePlantBlockPlacer> CODEC;
    public static final DoublePlantBlockPlacer PLACER;

    @Override
    protected BlockPlacerType<?> getBlockPlacerType() {
        return BlockPlacerType.DOUBLE_PLANT;
    }

    @Override
    public void place(IWorld iWorld, BlockPos blockPos, BlockState blockState, Random random2) {
        ((DoublePlantBlock)blockState.getBlock()).placeAt(iWorld, blockPos, 2);
    }

    private static DoublePlantBlockPlacer lambda$static$0() {
        return PLACER;
    }

    static {
        PLACER = new DoublePlantBlockPlacer();
        CODEC = Codec.unit(DoublePlantBlockPlacer::lambda$static$0);
    }
}

