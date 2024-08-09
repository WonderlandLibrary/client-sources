/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.trunkplacer;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class GiantTrunkPlacer
extends AbstractTrunkPlacer {
    public static final Codec<GiantTrunkPlacer> field_236898_a_ = RecordCodecBuilder.create(GiantTrunkPlacer::lambda$static$0);

    public GiantTrunkPlacer(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected TrunkPlacerType<?> func_230381_a_() {
        return TrunkPlacerType.GIANT_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, int n, BlockPos blockPos, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        BlockPos blockPos2 = blockPos.down();
        GiantTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos2);
        GiantTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos2.east());
        GiantTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos2.south());
        GiantTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos2.south().east());
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < n; ++i) {
            GiantTrunkPlacer.func_236899_a_(iWorldGenerationReader, random2, mutable, set, mutableBoundingBox, baseTreeFeatureConfig, blockPos, 0, i, 0);
            if (i >= n - 1) continue;
            GiantTrunkPlacer.func_236899_a_(iWorldGenerationReader, random2, mutable, set, mutableBoundingBox, baseTreeFeatureConfig, blockPos, 1, i, 0);
            GiantTrunkPlacer.func_236899_a_(iWorldGenerationReader, random2, mutable, set, mutableBoundingBox, baseTreeFeatureConfig, blockPos, 1, i, 1);
            GiantTrunkPlacer.func_236899_a_(iWorldGenerationReader, random2, mutable, set, mutableBoundingBox, baseTreeFeatureConfig, blockPos, 0, i, 1);
        }
        return ImmutableList.of(new FoliagePlacer.Foliage(blockPos.up(n), 0, true));
    }

    private static void func_236899_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BlockPos.Mutable mutable, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig, BlockPos blockPos, int n, int n2, int n3) {
        mutable.setAndOffset(blockPos, n, n2, n3);
        GiantTrunkPlacer.func_236910_a_(iWorldGenerationReader, random2, mutable, set, mutableBoundingBox, baseTreeFeatureConfig);
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return GiantTrunkPlacer.func_236915_a_(instance).apply(instance, GiantTrunkPlacer::new);
    }
}

