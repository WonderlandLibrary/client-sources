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
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class LiquidsConfig
implements IFeatureConfig {
    public static final Codec<LiquidsConfig> field_236649_a_ = RecordCodecBuilder.create(LiquidsConfig::lambda$static$5);
    public final FluidState state;
    public final boolean needsBlockBelow;
    public final int rockAmount;
    public final int holeAmount;
    public final Set<Block> acceptedBlocks;

    public LiquidsConfig(FluidState fluidState, boolean bl, int n, int n2, Set<Block> set) {
        this.state = fluidState;
        this.needsBlockBelow = bl;
        this.rockAmount = n;
        this.holeAmount = n2;
        this.acceptedBlocks = set;
    }

    private static App lambda$static$5(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)FluidState.field_237213_a_.fieldOf("state")).forGetter(LiquidsConfig::lambda$static$0), ((MapCodec)Codec.BOOL.fieldOf("requires_block_below")).orElse(true).forGetter(LiquidsConfig::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("rock_count")).orElse(4).forGetter(LiquidsConfig::lambda$static$2), ((MapCodec)Codec.INT.fieldOf("hole_count")).orElse(1).forGetter(LiquidsConfig::lambda$static$3), ((MapCodec)Registry.BLOCK.listOf().fieldOf("valid_blocks")).xmap(ImmutableSet::copyOf, ImmutableList::copyOf).forGetter(LiquidsConfig::lambda$static$4)).apply(instance, LiquidsConfig::new);
    }

    private static Set lambda$static$4(LiquidsConfig liquidsConfig) {
        return liquidsConfig.acceptedBlocks;
    }

    private static Integer lambda$static$3(LiquidsConfig liquidsConfig) {
        return liquidsConfig.holeAmount;
    }

    private static Integer lambda$static$2(LiquidsConfig liquidsConfig) {
        return liquidsConfig.rockAmount;
    }

    private static Boolean lambda$static$1(LiquidsConfig liquidsConfig) {
        return liquidsConfig.needsBlockBelow;
    }

    private static FluidState lambda$static$0(LiquidsConfig liquidsConfig) {
        return liquidsConfig.state;
    }
}

