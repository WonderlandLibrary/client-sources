/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.trunkplacer;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

public abstract class AbstractTrunkPlacer {
    public static final Codec<AbstractTrunkPlacer> field_236905_c_ = Registry.TRUNK_REPLACER.dispatch(AbstractTrunkPlacer::func_230381_a_, TrunkPlacerType::func_236927_a_);
    protected final int field_236906_d_;
    protected final int field_236907_e_;
    protected final int field_236908_f_;

    protected static <P extends AbstractTrunkPlacer> Products.P3<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer> func_236915_a_(RecordCodecBuilder.Instance<P> instance) {
        return instance.group(((MapCodec)Codec.intRange(0, 32).fieldOf("base_height")).forGetter(AbstractTrunkPlacer::lambda$func_236915_a_$0), ((MapCodec)Codec.intRange(0, 24).fieldOf("height_rand_a")).forGetter(AbstractTrunkPlacer::lambda$func_236915_a_$1), ((MapCodec)Codec.intRange(0, 24).fieldOf("height_rand_b")).forGetter(AbstractTrunkPlacer::lambda$func_236915_a_$2));
    }

    public AbstractTrunkPlacer(int n, int n2, int n3) {
        this.field_236906_d_ = n;
        this.field_236907_e_ = n2;
        this.field_236908_f_ = n3;
    }

    protected abstract TrunkPlacerType<?> func_230381_a_();

    public abstract List<FoliagePlacer.Foliage> func_230382_a_(IWorldGenerationReader var1, Random var2, int var3, BlockPos var4, Set<BlockPos> var5, MutableBoundingBox var6, BaseTreeFeatureConfig var7);

    public int func_236917_a_(Random random2) {
        return this.field_236906_d_ + random2.nextInt(this.field_236907_e_ + 1) + random2.nextInt(this.field_236908_f_ + 1);
    }

    protected static void func_236913_a_(IWorldWriter iWorldWriter, BlockPos blockPos, BlockState blockState, MutableBoundingBox mutableBoundingBox) {
        TreeFeature.func_236408_b_(iWorldWriter, blockPos, blockState);
        mutableBoundingBox.expandTo(new MutableBoundingBox(blockPos, blockPos));
    }

    private static boolean func_236912_a_(IWorldGenerationBaseReader iWorldGenerationBaseReader, BlockPos blockPos) {
        return iWorldGenerationBaseReader.hasBlockState(blockPos, AbstractTrunkPlacer::lambda$func_236912_a_$3);
    }

    protected static void func_236909_a_(IWorldGenerationReader iWorldGenerationReader, BlockPos blockPos) {
        if (!AbstractTrunkPlacer.func_236912_a_(iWorldGenerationReader, blockPos)) {
            TreeFeature.func_236408_b_(iWorldGenerationReader, blockPos, Blocks.DIRT.getDefaultState());
        }
    }

    protected static boolean func_236911_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BlockPos blockPos, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        if (TreeFeature.isReplaceableAt(iWorldGenerationReader, blockPos)) {
            AbstractTrunkPlacer.func_236913_a_(iWorldGenerationReader, blockPos, baseTreeFeatureConfig.trunkProvider.getBlockState(random2, blockPos), mutableBoundingBox);
            set.add(blockPos.toImmutable());
            return false;
        }
        return true;
    }

    protected static void func_236910_a_(IWorldGenerationReader iWorldGenerationReader, Random random2, BlockPos.Mutable mutable, Set<BlockPos> set, MutableBoundingBox mutableBoundingBox, BaseTreeFeatureConfig baseTreeFeatureConfig) {
        if (TreeFeature.func_236410_c_(iWorldGenerationReader, mutable)) {
            AbstractTrunkPlacer.func_236911_a_(iWorldGenerationReader, random2, mutable, set, mutableBoundingBox, baseTreeFeatureConfig);
        }
    }

    private static boolean lambda$func_236912_a_$3(BlockState blockState) {
        Block block = blockState.getBlock();
        return Feature.isDirt(block) && !blockState.isIn(Blocks.GRASS_BLOCK) && !blockState.isIn(Blocks.MYCELIUM);
    }

    private static Integer lambda$func_236915_a_$2(AbstractTrunkPlacer abstractTrunkPlacer) {
        return abstractTrunkPlacer.field_236908_f_;
    }

    private static Integer lambda$func_236915_a_$1(AbstractTrunkPlacer abstractTrunkPlacer) {
        return abstractTrunkPlacer.field_236907_e_;
    }

    private static Integer lambda$func_236915_a_$0(AbstractTrunkPlacer abstractTrunkPlacer) {
        return abstractTrunkPlacer.field_236906_d_;
    }
}

