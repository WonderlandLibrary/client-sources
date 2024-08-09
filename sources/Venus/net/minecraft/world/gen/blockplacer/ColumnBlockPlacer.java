/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.blockplacer;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;

public class ColumnBlockPlacer
extends BlockPlacer {
    public static final Codec<ColumnBlockPlacer> CODEC = RecordCodecBuilder.create(ColumnBlockPlacer::lambda$static$2);
    private final int minSize;
    private final int extraSize;

    public ColumnBlockPlacer(int n, int n2) {
        this.minSize = n;
        this.extraSize = n2;
    }

    @Override
    protected BlockPlacerType<?> getBlockPlacerType() {
        return BlockPlacerType.COLUMN;
    }

    @Override
    public void place(IWorld iWorld, BlockPos blockPos, BlockState blockState, Random random2) {
        BlockPos.Mutable mutable = blockPos.toMutable();
        int n = this.minSize + random2.nextInt(random2.nextInt(this.extraSize + 1) + 1);
        for (int i = 0; i < n; ++i) {
            iWorld.setBlockState(mutable, blockState, 2);
            mutable.move(Direction.UP);
        }
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.INT.fieldOf("min_size")).forGetter(ColumnBlockPlacer::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("extra_size")).forGetter(ColumnBlockPlacer::lambda$static$1)).apply(instance, ColumnBlockPlacer::new);
    }

    private static Integer lambda$static$1(ColumnBlockPlacer columnBlockPlacer) {
        return columnBlockPlacer.extraSize;
    }

    private static Integer lambda$static$0(ColumnBlockPlacer columnBlockPlacer) {
        return columnBlockPlacer.minSize;
    }
}

