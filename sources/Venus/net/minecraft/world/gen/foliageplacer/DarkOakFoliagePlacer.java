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

public class DarkOakFoliagePlacer
extends FoliagePlacer {
    public static final Codec<DarkOakFoliagePlacer> field_236745_a_ = RecordCodecBuilder.create(DarkOakFoliagePlacer::lambda$static$0);

    public DarkOakFoliagePlacer(FeatureSpread featureSpread, FeatureSpread featureSpread2) {
        super(featureSpread, featureSpread2);
    }

    @Override
    protected FoliagePlacerType<?> func_230371_a_() {
        return FoliagePlacerType.field_236770_i_;
    }

    @Override
    protected void func_230372_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BaseTreeFeatureConfig baseTreeFeatureConfig, int n, FoliagePlacer.Foliage foliage, int n2, int n3, Set<BlockPos> set, int n4, MutableBoundingBox mutableBoundingBox) {
        BlockPos blockPos = foliage.func_236763_a_().up(n4);
        boolean bl = foliage.func_236765_c_();
        if (bl) {
            this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, blockPos, n3 + 2, set, -1, bl, mutableBoundingBox);
            this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, blockPos, n3 + 3, set, 0, bl, mutableBoundingBox);
            this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, blockPos, n3 + 2, set, 1, bl, mutableBoundingBox);
            if (random2.nextBoolean()) {
                this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, blockPos, n3, set, 2, bl, mutableBoundingBox);
            }
        } else {
            this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, blockPos, n3 + 2, set, -1, bl, mutableBoundingBox);
            this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, blockPos, n3 + 1, set, 0, bl, mutableBoundingBox);
        }
    }

    @Override
    public int func_230374_a_(Random random2, int n, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return 1;
    }

    @Override
    protected boolean func_230375_b_(Random random2, int n, int n2, int n3, int n4, boolean bl) {
        return n2 != 0 || !bl || n != -n4 && n < n4 || n3 != -n4 && n3 < n4 ? super.func_230375_b_(random2, n, n2, n3, n4, bl) : true;
    }

    @Override
    protected boolean func_230373_a_(Random random2, int n, int n2, int n3, int n4, boolean bl) {
        if (n2 == -1 && !bl) {
            return n == n4 && n3 == n4;
        }
        if (n2 == 1) {
            return n + n3 > n4 * 2 - 2;
        }
        return true;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return DarkOakFoliagePlacer.func_242830_b(instance).apply(instance, DarkOakFoliagePlacer::new);
    }
}

