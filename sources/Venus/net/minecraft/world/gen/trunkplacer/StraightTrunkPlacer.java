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

public class StraightTrunkPlacer
extends AbstractTrunkPlacer {
    public static final Codec<StraightTrunkPlacer> field_236903_a_ = RecordCodecBuilder.create(StraightTrunkPlacer::lambda$static$0);

    public StraightTrunkPlacer(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected TrunkPlacerType<?> func_230381_a_() {
        return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, int n, BlockPos blockPos, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        StraightTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos.down());
        for (int i = 0; i < n; ++i) {
            StraightTrunkPlacer.func_236911_a_(iWorldGenerationReader, random2, blockPos.up(i), set, mutableBoundingBox, baseTreeFeatureConfig);
        }
        return ImmutableList.of(new FoliagePlacer.Foliage(blockPos.up(n), 0, false));
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return StraightTrunkPlacer.func_236915_a_(instance).apply(instance, StraightTrunkPlacer::new);
    }
}

