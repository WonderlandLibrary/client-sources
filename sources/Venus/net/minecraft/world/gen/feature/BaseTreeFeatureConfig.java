/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.AbstractFeatureSizeType;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;

public class BaseTreeFeatureConfig
implements IFeatureConfig {
    public static final Codec<BaseTreeFeatureConfig> CODEC = RecordCodecBuilder.create(BaseTreeFeatureConfig::lambda$static$9);
    public final BlockStateProvider trunkProvider;
    public final BlockStateProvider leavesProvider;
    public final List<TreeDecorator> decorators;
    public transient boolean forcePlacement;
    public final FoliagePlacer field_236677_f_;
    public final AbstractTrunkPlacer field_236678_g_;
    public final AbstractFeatureSizeType field_236679_h_;
    public final int field_236680_i_;
    public final boolean field_236681_j_;
    public final Heightmap.Type field_236682_l_;

    protected BaseTreeFeatureConfig(BlockStateProvider blockStateProvider, BlockStateProvider blockStateProvider2, FoliagePlacer foliagePlacer, AbstractTrunkPlacer abstractTrunkPlacer, AbstractFeatureSizeType abstractFeatureSizeType, List<TreeDecorator> list, int n, boolean bl, Heightmap.Type type) {
        this.trunkProvider = blockStateProvider;
        this.leavesProvider = blockStateProvider2;
        this.decorators = list;
        this.field_236677_f_ = foliagePlacer;
        this.field_236679_h_ = abstractFeatureSizeType;
        this.field_236678_g_ = abstractTrunkPlacer;
        this.field_236680_i_ = n;
        this.field_236681_j_ = bl;
        this.field_236682_l_ = type;
    }

    public void forcePlacement() {
        this.forcePlacement = true;
    }

    public BaseTreeFeatureConfig func_236685_a_(List<TreeDecorator> list) {
        return new BaseTreeFeatureConfig(this.trunkProvider, this.leavesProvider, this.field_236677_f_, this.field_236678_g_, this.field_236679_h_, list, this.field_236680_i_, this.field_236681_j_, this.field_236682_l_);
    }

    private static App lambda$static$9(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockStateProvider.CODEC.fieldOf("trunk_provider")).forGetter(BaseTreeFeatureConfig::lambda$static$0), ((MapCodec)BlockStateProvider.CODEC.fieldOf("leaves_provider")).forGetter(BaseTreeFeatureConfig::lambda$static$1), ((MapCodec)FoliagePlacer.field_236749_d_.fieldOf("foliage_placer")).forGetter(BaseTreeFeatureConfig::lambda$static$2), ((MapCodec)AbstractTrunkPlacer.field_236905_c_.fieldOf("trunk_placer")).forGetter(BaseTreeFeatureConfig::lambda$static$3), ((MapCodec)AbstractFeatureSizeType.field_236704_a_.fieldOf("minimum_size")).forGetter(BaseTreeFeatureConfig::lambda$static$4), ((MapCodec)TreeDecorator.field_236874_c_.listOf().fieldOf("decorators")).forGetter(BaseTreeFeatureConfig::lambda$static$5), ((MapCodec)Codec.INT.fieldOf("max_water_depth")).orElse(0).forGetter(BaseTreeFeatureConfig::lambda$static$6), ((MapCodec)Codec.BOOL.fieldOf("ignore_vines")).orElse(false).forGetter(BaseTreeFeatureConfig::lambda$static$7), ((MapCodec)Heightmap.Type.field_236078_g_.fieldOf("heightmap")).forGetter(BaseTreeFeatureConfig::lambda$static$8)).apply(instance, BaseTreeFeatureConfig::new);
    }

    private static Heightmap.Type lambda$static$8(BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return baseTreeFeatureConfig.field_236682_l_;
    }

    private static Boolean lambda$static$7(BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return baseTreeFeatureConfig.field_236681_j_;
    }

    private static Integer lambda$static$6(BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return baseTreeFeatureConfig.field_236680_i_;
    }

    private static List lambda$static$5(BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return baseTreeFeatureConfig.decorators;
    }

    private static AbstractFeatureSizeType lambda$static$4(BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return baseTreeFeatureConfig.field_236679_h_;
    }

    private static AbstractTrunkPlacer lambda$static$3(BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return baseTreeFeatureConfig.field_236678_g_;
    }

    private static FoliagePlacer lambda$static$2(BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return baseTreeFeatureConfig.field_236677_f_;
    }

    private static BlockStateProvider lambda$static$1(BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return baseTreeFeatureConfig.leavesProvider;
    }

    private static BlockStateProvider lambda$static$0(BaseTreeFeatureConfig baseTreeFeatureConfig) {
        return baseTreeFeatureConfig.trunkProvider;
    }

    public static class Builder {
        public final BlockStateProvider trunkProvider;
        public final BlockStateProvider leavesProvider;
        private final FoliagePlacer field_236694_c_;
        private final AbstractTrunkPlacer field_236695_d_;
        private final AbstractFeatureSizeType field_236696_e_;
        private List<TreeDecorator> decorators = ImmutableList.of();
        private int field_236697_g_;
        private boolean field_236698_h_;
        private Heightmap.Type field_236699_i_ = Heightmap.Type.OCEAN_FLOOR;

        public Builder(BlockStateProvider blockStateProvider, BlockStateProvider blockStateProvider2, FoliagePlacer foliagePlacer, AbstractTrunkPlacer abstractTrunkPlacer, AbstractFeatureSizeType abstractFeatureSizeType) {
            this.trunkProvider = blockStateProvider;
            this.leavesProvider = blockStateProvider2;
            this.field_236694_c_ = foliagePlacer;
            this.field_236695_d_ = abstractTrunkPlacer;
            this.field_236696_e_ = abstractFeatureSizeType;
        }

        public Builder func_236703_a_(List<TreeDecorator> list) {
            this.decorators = list;
            return this;
        }

        public Builder func_236701_a_(int n) {
            this.field_236697_g_ = n;
            return this;
        }

        public Builder setIgnoreVines() {
            this.field_236698_h_ = true;
            return this;
        }

        public Builder func_236702_a_(Heightmap.Type type) {
            this.field_236699_i_ = type;
            return this;
        }

        public BaseTreeFeatureConfig build() {
            return new BaseTreeFeatureConfig(this.trunkProvider, this.leavesProvider, this.field_236694_c_, this.field_236695_d_, this.field_236696_e_, this.decorators, this.field_236697_g_, this.field_236698_h_, this.field_236699_i_);
        }
    }
}

