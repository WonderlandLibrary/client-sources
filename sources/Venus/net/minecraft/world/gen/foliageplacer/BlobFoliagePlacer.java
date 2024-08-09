/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.foliageplacer;

import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

public class BlobFoliagePlacer
extends FoliagePlacer {
    public static final Codec<BlobFoliagePlacer> field_236738_a_ = RecordCodecBuilder.create(BlobFoliagePlacer::lambda$static$0);
    protected final int field_236739_b_;

    protected static <P extends BlobFoliagePlacer> Products.P3<RecordCodecBuilder.Mu<P>, FeatureSpread, FeatureSpread, Integer> func_236740_a_(RecordCodecBuilder.Instance<P> instance) {
        return BlobFoliagePlacer.func_242830_b(instance).and(((MapCodec)Codec.intRange(0, 16).fieldOf("height")).forGetter(BlobFoliagePlacer::lambda$func_236740_a_$1));
    }

    public BlobFoliagePlacer(FeatureSpread featureSpread, FeatureSpread featureSpread2, int n) {
        super(featureSpread, featureSpread2);
        this.field_236739_b_ = n;
    }

    @Override
    protected FoliagePlacerType<?> func_230371_a_() {
        return FoliagePlacerType.BLOB;
    }

    @Override
    protected void func_230372_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BaseTreeFeatureConfig baseTreeFeatureConfig, int n, FoliagePlacer.Foliage foliage, int n2, int n3, Set<BlockPos> set, int n4, MutableBoundingBox mutableBoundingBox) {
        for (int i = n4; i >= n4 - n2; --i) {
            int n5 = Math.max(n3 + foliage.func_236764_b_() - 1 - i / 2, 0);
            this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, foliage.func_236763_a_(), n5, set, i, foliage.func_236765_c_(), mutableBoundingBox);
        }
    }

    @Override
    public int func_230374_a_(Random random2, int n, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return this.field_236739_b_;
    }

    @Override
    protected boolean func_230373_a_(Random random2, int n, int n2, int n3, int n4, boolean bl) {
        return n == n4 && n3 == n4 && (random2.nextInt(2) == 0 || n2 == 0);
    }

    private static Integer lambda$func_236740_a_$1(BlobFoliagePlacer blobFoliagePlacer) {
        return blobFoliagePlacer.field_236739_b_;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return BlobFoliagePlacer.func_236740_a_(instance).apply(instance, BlobFoliagePlacer::new);
    }
}

