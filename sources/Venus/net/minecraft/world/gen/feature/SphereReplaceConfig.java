/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class SphereReplaceConfig
implements IFeatureConfig {
    public static final Codec<SphereReplaceConfig> field_236516_a_ = RecordCodecBuilder.create(SphereReplaceConfig::lambda$static$4);
    public final BlockState state;
    public final FeatureSpread radius;
    public final int field_242809_d;
    public final List<BlockState> targets;

    public SphereReplaceConfig(BlockState blockState, FeatureSpread featureSpread, int n, List<BlockState> list) {
        this.state = blockState;
        this.radius = featureSpread;
        this.field_242809_d = n;
        this.targets = list;
    }

    private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockState.CODEC.fieldOf("state")).forGetter(SphereReplaceConfig::lambda$static$0), ((MapCodec)FeatureSpread.func_242254_a(0, 4, 4).fieldOf("radius")).forGetter(SphereReplaceConfig::lambda$static$1), ((MapCodec)Codec.intRange(0, 4).fieldOf("half_height")).forGetter(SphereReplaceConfig::lambda$static$2), ((MapCodec)BlockState.CODEC.listOf().fieldOf("targets")).forGetter(SphereReplaceConfig::lambda$static$3)).apply(instance, SphereReplaceConfig::new);
    }

    private static List lambda$static$3(SphereReplaceConfig sphereReplaceConfig) {
        return sphereReplaceConfig.targets;
    }

    private static Integer lambda$static$2(SphereReplaceConfig sphereReplaceConfig) {
        return sphereReplaceConfig.field_242809_d;
    }

    private static FeatureSpread lambda$static$1(SphereReplaceConfig sphereReplaceConfig) {
        return sphereReplaceConfig.radius;
    }

    private static BlockState lambda$static$0(SphereReplaceConfig sphereReplaceConfig) {
        return sphereReplaceConfig.state;
    }
}

