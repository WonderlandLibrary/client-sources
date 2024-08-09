/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.trunkplacer;

import com.google.common.collect.Lists;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class MegaJungleTrunkPlacer
extends GiantTrunkPlacer {
    public static final Codec<MegaJungleTrunkPlacer> field_236901_b_ = RecordCodecBuilder.create(MegaJungleTrunkPlacer::lambda$static$0);

    public MegaJungleTrunkPlacer(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected TrunkPlacerType<?> func_230381_a_() {
        return TrunkPlacerType.MEGA_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, int n, BlockPos blockPos, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        ArrayList<FoliagePlacer.Foliage> arrayList = Lists.newArrayList();
        arrayList.addAll(super.func_230382_a_(iWorldGenerationReader, random2, n, blockPos, set, mutableBoundingBox, baseTreeFeatureConfig));
        for (int i = n - 2 - random2.nextInt(4); i > n / 2; i -= 2 + random2.nextInt(4)) {
            float f = random2.nextFloat() * ((float)Math.PI * 2);
            int n2 = 0;
            int n3 = 0;
            for (int j = 0; j < 5; ++j) {
                n2 = (int)(1.5f + MathHelper.cos(f) * (float)j);
                n3 = (int)(1.5f + MathHelper.sin(f) * (float)j);
                BlockPos blockPos2 = blockPos.add(n2, i - 3 + j / 2, n3);
                MegaJungleTrunkPlacer.func_236911_a_(iWorldGenerationReader, random2, blockPos2, set, mutableBoundingBox, baseTreeFeatureConfig);
            }
            arrayList.add(new FoliagePlacer.Foliage(blockPos.add(n2, i, n3), -2, false));
        }
        return arrayList;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return MegaJungleTrunkPlacer.func_236915_a_(instance).apply(instance, MegaJungleTrunkPlacer::new);
    }
}

