/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.foliageplacer;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

public class FancyFoliagePlacer
extends BlobFoliagePlacer {
    public static final Codec<FancyFoliagePlacer> field_236747_c_ = RecordCodecBuilder.create(FancyFoliagePlacer::lambda$static$0);

    public FancyFoliagePlacer(FeatureSpread featureSpread, FeatureSpread featureSpread2, int n) {
        super(featureSpread, featureSpread2, n);
    }

    @Override
    protected FoliagePlacerType<?> func_230371_a_() {
        return FoliagePlacerType.field_236767_f_;
    }

    @Override
    protected void func_230372_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BaseTreeFeatureConfig baseTreeFeatureConfig, int n, FoliagePlacer.Foliage foliage, int n2, int n3, Set<BlockPos> set, int n4, MutableBoundingBox mutableBoundingBox) {
        for (int i = n4; i >= n4 - n2; --i) {
            int n5 = n3 + (i != n4 && i != n4 - n2 ? 1 : 0);
            this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, foliage.func_236763_a_(), n5, set, i, foliage.func_236765_c_(), mutableBoundingBox);
        }
    }

    @Override
    protected boolean func_230373_a_(Random random2, int n, int n2, int n3, int n4, boolean bl) {
        return MathHelper.squareFloat((float)n + 0.5f) + MathHelper.squareFloat((float)n3 + 0.5f) > (float)(n4 * n4);
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return FancyFoliagePlacer.func_236740_a_(instance).apply(instance, FancyFoliagePlacer::new);
    }
}

