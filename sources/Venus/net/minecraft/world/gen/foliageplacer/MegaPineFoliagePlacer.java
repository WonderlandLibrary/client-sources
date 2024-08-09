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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

public class MegaPineFoliagePlacer
extends FoliagePlacer {
    public static final Codec<MegaPineFoliagePlacer> field_236778_a_ = RecordCodecBuilder.create(MegaPineFoliagePlacer::lambda$static$1);
    private final FeatureSpread field_236780_c_;

    public MegaPineFoliagePlacer(FeatureSpread featureSpread, FeatureSpread featureSpread2, FeatureSpread featureSpread3) {
        super(featureSpread, featureSpread2);
        this.field_236780_c_ = featureSpread3;
    }

    @Override
    protected FoliagePlacerType<?> func_230371_a_() {
        return FoliagePlacerType.field_236769_h_;
    }

    @Override
    protected void func_230372_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BaseTreeFeatureConfig baseTreeFeatureConfig, int n, FoliagePlacer.Foliage foliage, int n2, int n3, Set<BlockPos> set, int n4, MutableBoundingBox mutableBoundingBox) {
        BlockPos blockPos = foliage.func_236763_a_();
        int n5 = 0;
        for (int i = blockPos.getY() - n2 + n4; i <= blockPos.getY() + n4; ++i) {
            int n6 = blockPos.getY() - i;
            int n7 = n3 + foliage.func_236764_b_() + MathHelper.floor((float)n6 / (float)n2 * 3.5f);
            int n8 = n6 > 0 && n7 == n5 && (i & 1) == 0 ? n7 + 1 : n7;
            this.func_236753_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, new BlockPos(blockPos.getX(), i, blockPos.getZ()), n8, set, 0, foliage.func_236765_c_(), mutableBoundingBox);
            n5 = n7;
        }
    }

    @Override
    public int func_230374_a_(Random random2, int n, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return this.field_236780_c_.func_242259_a(random2);
    }

    @Override
    protected boolean func_230373_a_(Random random2, int n, int n2, int n3, int n4, boolean bl) {
        if (n + n3 >= 7) {
            return false;
        }
        return n * n + n3 * n3 > n4 * n4;
    }

    private static App lambda$static$1(RecordCodecBuilder.Instance instance) {
        return MegaPineFoliagePlacer.func_242830_b(instance).and(((MapCodec)FeatureSpread.func_242254_a(0, 16, 8).fieldOf("crown_height")).forGetter(MegaPineFoliagePlacer::lambda$static$0)).apply(instance, MegaPineFoliagePlacer::new);
    }

    private static FeatureSpread lambda$static$0(MegaPineFoliagePlacer megaPineFoliagePlacer) {
        return megaPineFoliagePlacer.field_236780_c_;
    }
}

