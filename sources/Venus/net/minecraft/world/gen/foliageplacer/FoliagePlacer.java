/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.foliageplacer;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

public abstract class FoliagePlacer {
    public static final Codec<FoliagePlacer> field_236749_d_ = Registry.FOLIAGE_PLACER_TYPE.dispatch(FoliagePlacer::func_230371_a_, FoliagePlacerType::func_236772_a_);
    protected final FeatureSpread field_227381_a_;
    protected final FeatureSpread field_236750_g_;

    protected static <P extends FoliagePlacer> Products.P2<RecordCodecBuilder.Mu<P>, FeatureSpread, FeatureSpread> func_242830_b(RecordCodecBuilder.Instance<P> instance) {
        return instance.group(((MapCodec)FeatureSpread.func_242254_a(0, 8, 8).fieldOf("radius")).forGetter(FoliagePlacer::lambda$func_242830_b$0), ((MapCodec)FeatureSpread.func_242254_a(0, 8, 8).fieldOf("offset")).forGetter(FoliagePlacer::lambda$func_242830_b$1));
    }

    public FoliagePlacer(FeatureSpread featureSpread, FeatureSpread featureSpread2) {
        this.field_227381_a_ = featureSpread;
        this.field_236750_g_ = featureSpread2;
    }

    protected abstract FoliagePlacerType<?> func_230371_a_();

    public void func_236752_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BaseTreeFeatureConfig baseTreeFeatureConfig, int n, Foliage foliage, int n2, int n3, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox) {
        this.func_230372_a_(iWorldGenerationReader, random2, baseTreeFeatureConfig, n, foliage, n2, n3, set, this.func_236755_a_(random2), mutableBoundingBox);
    }

    protected abstract void func_230372_a_(IWorldGenerationReader var1, Random var2, BaseTreeFeatureConfig var3, int var4, Foliage var5, int var6, int var7, Set<BlockPos> var8, int var9, MutableBoundingBox var10);

    public abstract int func_230374_a_(Random var1, int var2, BaseTreeFeatureConfig var3);

    public int func_230376_a_(Random random2, int n) {
        return this.field_227381_a_.func_242259_a(random2);
    }

    private int func_236755_a_(Random random2) {
        return this.field_236750_g_.func_242259_a(random2);
    }

    protected abstract boolean func_230373_a_(Random var1, int var2, int var3, int var4, int var5, boolean var6);

    protected boolean func_230375_b_(Random random2, int n, int n2, int n3, int n4, boolean bl) {
        int n5;
        int n6;
        if (bl) {
            n6 = Math.min(Math.abs(n), Math.abs(n - 1));
            n5 = Math.min(Math.abs(n3), Math.abs(n3 - 1));
        } else {
            n6 = Math.abs(n);
            n5 = Math.abs(n3);
        }
        return this.func_230373_a_(random2, n6, n2, n5, n4, bl);
    }

    protected void func_236753_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BaseTreeFeatureConfig baseTreeFeatureConfig, BlockPos blockPos, int n, Set<BlockPos> set, int n2, boolean bl, MutableBoundingBox mutableBoundingBox) {
        int n3 = bl ? 1 : 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = -n; i <= n + n3; ++i) {
            for (int j = -n; j <= n + n3; ++j) {
                if (this.func_230375_b_(random2, i, n2, j, n, bl)) continue;
                mutable.setAndOffset(blockPos, i, n2, j);
                if (!TreeFeature.isReplaceableAt(iWorldGenerationReader, mutable)) continue;
                iWorldGenerationReader.setBlockState(mutable, baseTreeFeatureConfig.leavesProvider.getBlockState(random2, mutable), 19);
                mutableBoundingBox.expandTo(new MutableBoundingBox(mutable, mutable));
                set.add(mutable.toImmutable());
            }
        }
    }

    private static FeatureSpread lambda$func_242830_b$1(FoliagePlacer foliagePlacer) {
        return foliagePlacer.field_236750_g_;
    }

    private static FeatureSpread lambda$func_242830_b$0(FoliagePlacer foliagePlacer) {
        return foliagePlacer.field_227381_a_;
    }

    public static final class Foliage {
        private final BlockPos field_236760_a_;
        private final int field_236761_b_;
        private final boolean field_236762_c_;

        public Foliage(BlockPos blockPos, int n, boolean bl) {
            this.field_236760_a_ = blockPos;
            this.field_236761_b_ = n;
            this.field_236762_c_ = bl;
        }

        public BlockPos func_236763_a_() {
            return this.field_236760_a_;
        }

        public int func_236764_b_() {
            return this.field_236761_b_;
        }

        public boolean func_236765_c_() {
            return this.field_236762_c_;
        }
    }
}

