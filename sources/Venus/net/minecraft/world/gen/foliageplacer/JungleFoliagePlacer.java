/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.foliageplacer;

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

public class JungleFoliagePlacer
extends FoliagePlacer {
    public static final Codec<JungleFoliagePlacer> field_236774_a_ = RecordCodecBuilder.create(JungleFoliagePlacer::lambda$static$1);
    protected final int field_236775_b_;

    public JungleFoliagePlacer(FeatureSpread featureSpread, FeatureSpread featureSpread2, int n) {
        super(featureSpread, featureSpread2);
        this.field_236775_b_ = n;
    }

    @Override
    protected FoliagePlacerType<?> func_230371_a_() {
        return FoliagePlacerType.field_236768_g_;
    }

    @Override
    protected void func_230372_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BaseTreeFeatureConfig baseTreeFeatureConfig, int n, FoliagePlacer.Foliage foliage, int n2, int n3, Set<BlockPos> set, int n4, MutableBoundingBox mutableBoundingBox) {
        int n5 = foliage.func_236765_c_() ? n2 : 1 + random2.nextInt(2);
        for (int i = n4; i >= n4 - n5; --i) {
            int n6 = n3 + foliage.func_236764_b_() + 1 - i;
            this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, foliage.func_236763_a_(), n6, set, i, foliage.func_236765_c_(), mutableBoundingBox);
        }
    }

    @Override
    public int func_230374_a_(Random random2, int n, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return this.field_236775_b_;
    }

    @Override
    protected boolean func_230373_a_(Random random2, int n, int n2, int n3, int n4, boolean bl) {
        if (n + n3 >= 7) {
            return false;
        }
        return n * n + n3 * n3 > n4 * n4;
    }

    private static App lambda$static$1(RecordCodecBuilder.Instance instance) {
        return JungleFoliagePlacer.func_242830_b(instance).and(((MapCodec)Codec.intRange(0, 16).fieldOf("height")).forGetter(JungleFoliagePlacer::lambda$static$0)).apply(instance, JungleFoliagePlacer::new);
    }

    private static Integer lambda$static$0(JungleFoliagePlacer jungleFoliagePlacer) {
        return jungleFoliagePlacer.field_236775_b_;
    }
}

