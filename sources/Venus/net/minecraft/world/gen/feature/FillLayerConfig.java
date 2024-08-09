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

public class FillLayerConfig
implements IFeatureConfig {
    public static final Codec<FillLayerConfig> field_236537_a_ = RecordCodecBuilder.create(FillLayerConfig::lambda$static$2);
    public final int height;
    public final BlockState state;

    public FillLayerConfig(int n, BlockState blockState) {
        this.height = n;
        this.state = blockState;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.intRange(0, 255).fieldOf("height")).forGetter(FillLayerConfig::lambda$static$0), ((MapCodec)BlockState.CODEC.fieldOf("state")).forGetter(FillLayerConfig::lambda$static$1)).apply(instance, FillLayerConfig::new);
    }

    private static BlockState lambda$static$1(FillLayerConfig fillLayerConfig) {
        return fillLayerConfig.state;
    }

    private static Integer lambda$static$0(FillLayerConfig fillLayerConfig) {
        return fillLayerConfig.height;
    }
}

