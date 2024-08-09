/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class EndSpikeFeatureConfig
implements IFeatureConfig {
    public static final Codec<EndSpikeFeatureConfig> field_236644_a_ = RecordCodecBuilder.create(EndSpikeFeatureConfig::lambda$static$3);
    private final boolean crystalInvulnerable;
    private final List<EndSpikeFeature.EndSpike> spikes;
    @Nullable
    private final BlockPos crystalBeamTarget;

    public EndSpikeFeatureConfig(boolean bl, List<EndSpikeFeature.EndSpike> list, @Nullable BlockPos blockPos) {
        this(bl, list, Optional.ofNullable(blockPos));
    }

    private EndSpikeFeatureConfig(boolean bl, List<EndSpikeFeature.EndSpike> list, Optional<BlockPos> optional) {
        this.crystalInvulnerable = bl;
        this.spikes = list;
        this.crystalBeamTarget = optional.orElse(null);
    }

    public boolean isCrystalInvulnerable() {
        return this.crystalInvulnerable;
    }

    public List<EndSpikeFeature.EndSpike> getSpikes() {
        return this.spikes;
    }

    @Nullable
    public BlockPos getCrystalBeamTarget() {
        return this.crystalBeamTarget;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.BOOL.fieldOf("crystal_invulnerable")).orElse(false).forGetter(EndSpikeFeatureConfig::lambda$static$0), ((MapCodec)EndSpikeFeature.EndSpike.field_236357_a_.listOf().fieldOf("spikes")).forGetter(EndSpikeFeatureConfig::lambda$static$1), BlockPos.CODEC.optionalFieldOf("crystal_beam_target").forGetter(EndSpikeFeatureConfig::lambda$static$2)).apply(instance, EndSpikeFeatureConfig::new);
    }

    private static Optional lambda$static$2(EndSpikeFeatureConfig endSpikeFeatureConfig) {
        return Optional.ofNullable(endSpikeFeatureConfig.crystalBeamTarget);
    }

    private static List lambda$static$1(EndSpikeFeatureConfig endSpikeFeatureConfig) {
        return endSpikeFeatureConfig.spikes;
    }

    private static Boolean lambda$static$0(EndSpikeFeatureConfig endSpikeFeatureConfig) {
        return endSpikeFeatureConfig.crystalInvulnerable;
    }
}

