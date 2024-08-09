/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.blockplacer;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

public class SimpleBlockPlacer
extends BlockPlacer {
    public static final Codec<SimpleBlockPlacer> CODEC;
    public static final SimpleBlockPlacer PLACER;

    @Override
    protected BlockPlacerType<?> getBlockPlacerType() {
        return BlockPlacerType.SIMPLE_BLOCK;
    }

    @Override
    public void place(IWorld iWorld, BlockPos blockPos, BlockState blockState, Random random2) {
        iWorld.setBlockState(blockPos, blockState, 2);
    }

    private static SimpleBlockPlacer lambda$static$0() {
        return PLACER;
    }

    static {
        PLACER = new SimpleBlockPlacer();
        CODEC = Codec.unit(SimpleBlockPlacer::lambda$static$0);
    }
}

