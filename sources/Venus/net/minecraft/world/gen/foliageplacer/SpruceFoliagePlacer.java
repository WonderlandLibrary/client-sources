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

public class SpruceFoliagePlacer
extends FoliagePlacer {
    public static final Codec<SpruceFoliagePlacer> field_236790_a_ = RecordCodecBuilder.create(SpruceFoliagePlacer::lambda$static$1);
    private final FeatureSpread field_236791_b_;

    public SpruceFoliagePlacer(FeatureSpread featureSpread, FeatureSpread featureSpread2, FeatureSpread featureSpread3) {
        super(featureSpread, featureSpread2);
        this.field_236791_b_ = featureSpread3;
    }

    @Override
    protected FoliagePlacerType<?> func_230371_a_() {
        return FoliagePlacerType.SPRUCE;
    }

    @Override
    protected void func_230372_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BaseTreeFeatureConfig baseTreeFeatureConfig, int n, FoliagePlacer.Foliage foliage, int n2, int n3, Set<BlockPos> set, int n4, MutableBoundingBox mutableBoundingBox) {
        BlockPos blockPos = foliage.func_236763_a_();
        int n5 = random2.nextInt(2);
        int n6 = 1;
        int n7 = 0;
        for (int i = n4; i >= -n2; --i) {
            this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, blockPos, n5, set, i, foliage.func_236765_c_(), mutableBoundingBox);
            if (n5 >= n6) {
                n5 = n7;
                n7 = 1;
                n6 = Math.min(n6 + 1, n3 + foliage.func_236764_b_());
                continue;
            }
            ++n5;
        }
    }

    @Override
    public int func_230374_a_(Random random2, int n, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return Math.max(4, n - this.field_236791_b_.func_242259_a(random2));
    }

    @Override
    protected boolean func_230373_a_(Random random2, int n, int n2, int n3, int n4, boolean bl) {
        return n == n4 && n3 == n4 && n4 > 0;
    }

    private static App lambda$static$1(RecordCodecBuilder.Instance instance) {
        return SpruceFoliagePlacer.func_242830_b(instance).and(((MapCodec)FeatureSpread.func_242254_a(0, 16, 8).fieldOf("trunk_height")).forGetter(SpruceFoliagePlacer::lambda$static$0)).apply(instance, SpruceFoliagePlacer::new);
    }

    private static FeatureSpread lambda$static$0(SpruceFoliagePlacer spruceFoliagePlacer) {
        return spruceFoliagePlacer.field_236791_b_;
    }
}

