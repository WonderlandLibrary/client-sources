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
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public class FancyTrunkPlacer
extends AbstractTrunkPlacer {
    public static final Codec<FancyTrunkPlacer> field_236884_a_ = RecordCodecBuilder.create(FancyTrunkPlacer::lambda$static$0);

    public FancyTrunkPlacer(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected TrunkPlacerType<?> func_230381_a_() {
        return TrunkPlacerType.FANCY_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, int n, BlockPos blockPos, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        int n2;
        int n3 = 5;
        int n4 = n + 2;
        int n5 = MathHelper.floor((double)n4 * 0.618);
        if (!baseTreeFeatureConfig.forcePlacement) {
            FancyTrunkPlacer.func_236909_a_(iWorldGenerationReader, blockPos.down());
        }
        double d = 1.0;
        int n6 = Math.min(1, MathHelper.floor(1.382 + Math.pow(1.0 * (double)n4 / 13.0, 2.0)));
        int n7 = blockPos.getY() + n5;
        ArrayList<Foliage> arrayList = Lists.newArrayList();
        arrayList.add(new Foliage(blockPos.up(n2), n7));
        for (n2 = n4 - 5; n2 >= 0; --n2) {
            float f = this.func_236890_b_(n4, n2);
            if (f < 0.0f) continue;
            for (int i = 0; i < n6; ++i) {
                BlockPos blockPos2;
                double d2;
                double d3;
                double d4 = 1.0;
                double d5 = 1.0 * (double)f * ((double)random2.nextFloat() + 0.328);
                double d6 = d5 * Math.sin(d3 = (double)(random2.nextFloat() * 2.0f) * Math.PI) + 0.5;
                BlockPos blockPos3 = blockPos.add(d6, (double)(n2 - 1), d2 = d5 * Math.cos(d3) + 0.5);
                if (!this.func_236887_a_(iWorldGenerationReader, random2, blockPos3, blockPos2 = blockPos3.up(5), false, set, mutableBoundingBox, baseTreeFeatureConfig)) continue;
                int n8 = blockPos.getX() - blockPos3.getX();
                int n9 = blockPos.getZ() - blockPos3.getZ();
                double d7 = (double)blockPos3.getY() - Math.sqrt(n8 * n8 + n9 * n9) * 0.381;
                int n10 = d7 > (double)n7 ? n7 : (int)d7;
                BlockPos blockPos4 = new BlockPos(blockPos.getX(), n10, blockPos.getZ());
                if (!this.func_236887_a_(iWorldGenerationReader, random2, blockPos4, blockPos3, false, set, mutableBoundingBox, baseTreeFeatureConfig)) continue;
                arrayList.add(new Foliage(blockPos3, blockPos4.getY()));
            }
        }
        this.func_236887_a_(iWorldGenerationReader, random2, blockPos, blockPos.up(n5), true, set, mutableBoundingBox, baseTreeFeatureConfig);
        this.func_236886_a_(iWorldGenerationReader, random2, n4, blockPos, arrayList, set, mutableBoundingBox, baseTreeFeatureConfig);
        ArrayList<FoliagePlacer.Foliage> arrayList2 = Lists.newArrayList();
        for (Foliage foliage : arrayList) {
            if (!this.func_236885_a_(n4, foliage.func_236894_a_() - blockPos.getY())) continue;
            arrayList2.add(foliage.field_236892_a_);
        }
        return arrayList2;
    }

    private boolean func_236887_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BlockPos blockPos, BlockPos blockPos2, boolean bl, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        if (!bl && Objects.equals(blockPos, blockPos2)) {
            return false;
        }
        BlockPos blockPos3 = blockPos2.add(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        int n = this.func_236888_a_(blockPos3);
        float f = (float)blockPos3.getX() / (float)n;
        float f2 = (float)blockPos3.getY() / (float)n;
        float f3 = (float)blockPos3.getZ() / (float)n;
        for (int i = 0; i <= n; ++i) {
            BlockPos blockPos4 = blockPos.add(0.5f + (float)i * f, 0.5f + (float)i * f2, 0.5f + (float)i * f3);
            if (bl) {
                FancyTrunkPlacer.func_236913_a_(iWorldGenerationReader, blockPos4, (BlockState)baseTreeFeatureConfig.trunkProvider.getBlockState(random2, blockPos4).with(RotatedPillarBlock.AXIS, this.func_236889_a_(blockPos, blockPos4)), mutableBoundingBox);
                set.add(blockPos4.toImmutable());
                continue;
            }
            if (TreeFeature.func_236410_c_(iWorldGenerationReader, blockPos4)) continue;
            return true;
        }
        return false;
    }

    private int func_236888_a_(BlockPos blockPos) {
        int n = MathHelper.abs(blockPos.getX());
        int n2 = MathHelper.abs(blockPos.getY());
        int n3 = MathHelper.abs(blockPos.getZ());
        return Math.max(n, Math.max(n2, n3));
    }

    private Direction.Axis func_236889_a_(BlockPos blockPos, BlockPos blockPos2) {
        int n;
        Direction.Axis axis = Direction.Axis.Y;
        int n2 = Math.abs(blockPos2.getX() - blockPos.getX());
        int n3 = Math.max(n2, n = Math.abs(blockPos2.getZ() - blockPos.getZ()));
        if (n3 > 0) {
            axis = n2 == n3 ? Direction.Axis.X : Direction.Axis.Z;
        }
        return axis;
    }

    private boolean func_236885_a_(int n, int n2) {
        return (double)n2 >= (double)n * 0.2;
    }

    private void func_236886_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, int n, BlockPos blockPos, List<Foliage> list, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        for (Foliage foliage : list) {
            int n2 = foliage.func_236894_a_();
            BlockPos blockPos2 = new BlockPos(blockPos.getX(), n2, blockPos.getZ());
            if (blockPos2.equals(foliage.field_236892_a_.func_236763_a_()) || !this.func_236885_a_(n, n2 - blockPos.getY())) continue;
            this.func_236887_a_(iWorldGenerationReader, random2, blockPos2, foliage.field_236892_a_.func_236763_a_(), true, set, mutableBoundingBox, baseTreeFeatureConfig);
        }
    }

    private float func_236890_b_(int n, int n2) {
        if ((float)n2 < (float)n * 0.3f) {
            return -1.0f;
        }
        float f = (float)n / 2.0f;
        float f2 = f - (float)n2;
        float f3 = MathHelper.sqrt(f * f - f2 * f2);
        if (f2 == 0.0f) {
            f3 = f;
        } else if (Math.abs(f2) >= f) {
            return 0.0f;
        }
        return f3 * 0.5f;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return FancyTrunkPlacer.func_236915_a_(instance).apply(instance, FancyTrunkPlacer::new);
    }

    static class Foliage {
        private final FoliagePlacer.Foliage field_236892_a_;
        private final int field_236893_b_;

        public Foliage(BlockPos blockPos, int n) {
            this.field_236892_a_ = new FoliagePlacer.Foliage(blockPos, 0, false);
            this.field_236893_b_ = n;
        }

        public int func_236894_a_() {
            return this.field_236893_b_;
        }
    }
}

