/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ReplaceBlockConfig
implements IFeatureConfig {
    public static final Codec<ReplaceBlockConfig> field_236604_a_ = RecordCodecBuilder.create(ReplaceBlockConfig::lambda$static$2);
    public final BlockState target;
    public final BlockState state;

    public ReplaceBlockConfig(BlockState blockState, BlockState blockState2) {
        this.target = blockState;
        this.state = blockState2;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockState.CODEC.fieldOf("target")).forGetter(ReplaceBlockConfig::lambda$static$0), ((MapCodec)BlockState.CODEC.fieldOf("state")).forGetter(ReplaceBlockConfig::lambda$static$1)).apply(instance, ReplaceBlockConfig::new);
    }

    private static BlockState lambda$static$1(ReplaceBlockConfig replaceBlockConfig) {
        return replaceBlockConfig.state;
    }

    private static BlockState lambda$static$0(ReplaceBlockConfig replaceBlockConfig) {
        return replaceBlockConfig.target;
    }
}

