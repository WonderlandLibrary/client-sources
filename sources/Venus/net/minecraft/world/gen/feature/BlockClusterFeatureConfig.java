/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BlockClusterFeatureConfig
implements IFeatureConfig {
    public static final Codec<BlockClusterFeatureConfig> field_236587_a_ = RecordCodecBuilder.create(BlockClusterFeatureConfig::lambda$static$11);
    public final BlockStateProvider stateProvider;
    public final BlockPlacer blockPlacer;
    public final Set<Block> whitelist;
    public final Set<BlockState> blacklist;
    public final int tryCount;
    public final int xSpread;
    public final int ySpread;
    public final int zSpread;
    public final boolean isReplaceable;
    public final boolean field_227298_k_;
    public final boolean requiresWater;

    private BlockClusterFeatureConfig(BlockStateProvider blockStateProvider, BlockPlacer blockPlacer, List<BlockState> list, List<BlockState> list2, int n, int n2, int n3, int n4, boolean bl, boolean bl2, boolean bl3) {
        this(blockStateProvider, blockPlacer, list.stream().map(AbstractBlock.AbstractBlockState::getBlock).collect(Collectors.toSet()), ImmutableSet.copyOf(list2), n, n2, n3, n4, bl, bl2, bl3);
    }

    private BlockClusterFeatureConfig(BlockStateProvider blockStateProvider, BlockPlacer blockPlacer, Set<Block> set, Set<BlockState> set2, int n, int n2, int n3, int n4, boolean bl, boolean bl2, boolean bl3) {
        this.stateProvider = blockStateProvider;
        this.blockPlacer = blockPlacer;
        this.whitelist = set;
        this.blacklist = set2;
        this.tryCount = n;
        this.xSpread = n2;
        this.ySpread = n3;
        this.zSpread = n4;
        this.isReplaceable = bl;
        this.field_227298_k_ = bl2;
        this.requiresWater = bl3;
    }

    private static App lambda$static$11(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockStateProvider.CODEC.fieldOf("state_provider")).forGetter(BlockClusterFeatureConfig::lambda$static$0), ((MapCodec)BlockPlacer.CODEC.fieldOf("block_placer")).forGetter(BlockClusterFeatureConfig::lambda$static$1), ((MapCodec)BlockState.CODEC.listOf().fieldOf("whitelist")).forGetter(BlockClusterFeatureConfig::lambda$static$2), ((MapCodec)BlockState.CODEC.listOf().fieldOf("blacklist")).forGetter(BlockClusterFeatureConfig::lambda$static$3), ((MapCodec)Codec.INT.fieldOf("tries")).orElse(128).forGetter(BlockClusterFeatureConfig::lambda$static$4), ((MapCodec)Codec.INT.fieldOf("xspread")).orElse(7).forGetter(BlockClusterFeatureConfig::lambda$static$5), ((MapCodec)Codec.INT.fieldOf("yspread")).orElse(3).forGetter(BlockClusterFeatureConfig::lambda$static$6), ((MapCodec)Codec.INT.fieldOf("zspread")).orElse(7).forGetter(BlockClusterFeatureConfig::lambda$static$7), ((MapCodec)Codec.BOOL.fieldOf("can_replace")).orElse(false).forGetter(BlockClusterFeatureConfig::lambda$static$8), ((MapCodec)Codec.BOOL.fieldOf("project")).orElse(true).forGetter(BlockClusterFeatureConfig::lambda$static$9), ((MapCodec)Codec.BOOL.fieldOf("need_water")).orElse(false).forGetter(BlockClusterFeatureConfig::lambda$static$10)).apply(instance, BlockClusterFeatureConfig::new);
    }

    private static Boolean lambda$static$10(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.requiresWater;
    }

    private static Boolean lambda$static$9(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.field_227298_k_;
    }

    private static Boolean lambda$static$8(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.isReplaceable;
    }

    private static Integer lambda$static$7(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.zSpread;
    }

    private static Integer lambda$static$6(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.ySpread;
    }

    private static Integer lambda$static$5(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.xSpread;
    }

    private static Integer lambda$static$4(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.tryCount;
    }

    private static List lambda$static$3(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return ImmutableList.copyOf(blockClusterFeatureConfig.blacklist);
    }

    private static List lambda$static$2(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.whitelist.stream().map(Block::getDefaultState).collect(Collectors.toList());
    }

    private static BlockPlacer lambda$static$1(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.blockPlacer;
    }

    private static BlockStateProvider lambda$static$0(BlockClusterFeatureConfig blockClusterFeatureConfig) {
        return blockClusterFeatureConfig.stateProvider;
    }

    public static class Builder {
        private final BlockStateProvider stateProvider;
        private final BlockPlacer blockPlacer;
        private Set<Block> whitelist = ImmutableSet.of();
        private Set<BlockState> blacklist = ImmutableSet.of();
        private int tryCount = 64;
        private int xSpread = 7;
        private int ySpread = 3;
        private int zSpread = 7;
        private boolean isReplaceable;
        private boolean field_227312_j_ = true;
        private boolean requiresWater = false;

        public Builder(BlockStateProvider blockStateProvider, BlockPlacer blockPlacer) {
            this.stateProvider = blockStateProvider;
            this.blockPlacer = blockPlacer;
        }

        public Builder whitelist(Set<Block> set) {
            this.whitelist = set;
            return this;
        }

        public Builder blacklist(Set<BlockState> set) {
            this.blacklist = set;
            return this;
        }

        public Builder tries(int n) {
            this.tryCount = n;
            return this;
        }

        public Builder xSpread(int n) {
            this.xSpread = n;
            return this;
        }

        public Builder ySpread(int n) {
            this.ySpread = n;
            return this;
        }

        public Builder zSpread(int n) {
            this.zSpread = n;
            return this;
        }

        public Builder replaceable() {
            this.isReplaceable = true;
            return this;
        }

        public Builder func_227317_b_() {
            this.field_227312_j_ = false;
            return this;
        }

        public Builder requiresWater() {
            this.requiresWater = true;
            return this;
        }

        public BlockClusterFeatureConfig build() {
            return new BlockClusterFeatureConfig(this.stateProvider, this.blockPlacer, this.whitelist, this.blacklist, this.tryCount, this.xSpread, this.ySpread, this.zSpread, this.isReplaceable, this.field_227312_j_, this.requiresWater);
        }
    }
}

