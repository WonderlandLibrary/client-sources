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
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

public class AcaciaFoliagePlacer
extends FoliagePlacer {
    public static final Codec<AcaciaFoliagePlacer> field_236736_a_ = RecordCodecBuilder.create(AcaciaFoliagePlacer::lambda$static$0);

    public AcaciaFoliagePlacer(FeatureSpread featureSpread, FeatureSpread featureSpread2) {
        super(featureSpread, featureSpread2);
    }

    @Override
    protected FoliagePlacerType<?> func_230371_a_() {
        return FoliagePlacerType.ACACIA;
    }

    @Override
    protected void func_230372_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BaseTreeFeatureConfig baseTreeFeatureConfig, int n, FoliagePlacer.Foliage foliage, int n2, int n3, Set<BlockPos> set, int n4, MutableBoundingBox mutableBoundingBox) {
        boolean bl = foliage.func_236765_c_();
        BlockPos blockPos = foliage.func_236763_a_().up(n4);
        this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, blockPos, n3 + foliage.func_236764_b_(), set, -1 - n2, bl, mutableBoundingBox);
        this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, blockPos, n3 - 1, set, -n2, bl, mutableBoundingBox);
        this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, blockPos, n3 + foliage.func_236764_b_() - 1, set, 0, bl, mutableBoundingBox);
    }

    @Override
    public int func_230374_a_(Random random2, int n, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return 1;
    }

    @Override
    protected boolean func_230373_a_(Random random2, int n, int n2, int n3, int n4, boolean bl) {
        if (n2 == 0) {
            return (n > 1 || n3 > 1) && n != 0 && n3 != 0;
        }
        return n == n4 && n3 == n4 && n4 > 0;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return AcaciaFoliagePlacer.func_242830_b(instance).apply(instance, AcaciaFoliagePlacer::new);
    }
}

