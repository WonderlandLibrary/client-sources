/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class EndGatewayConfig
implements IFeatureConfig {
    public static final Codec<EndGatewayConfig> field_236522_a_ = RecordCodecBuilder.create(EndGatewayConfig::lambda$static$2);
    private final Optional<BlockPos> exit;
    private final boolean exact;

    private EndGatewayConfig(Optional<BlockPos> optional, boolean bl) {
        this.exit = optional;
        this.exact = bl;
    }

    public static EndGatewayConfig func_214702_a(BlockPos blockPos, boolean bl) {
        return new EndGatewayConfig(Optional.of(blockPos), bl);
    }

    public static EndGatewayConfig func_214698_a() {
        return new EndGatewayConfig(Optional.empty(), false);
    }

    public Optional<BlockPos> func_214700_b() {
        return this.exit;
    }

    public boolean func_214701_c() {
        return this.exact;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(BlockPos.CODEC.optionalFieldOf("exit").forGetter(EndGatewayConfig::lambda$static$0), ((MapCodec)Codec.BOOL.fieldOf("exact")).forGetter(EndGatewayConfig::lambda$static$1)).apply(instance, EndGatewayConfig::new);
    }

    private static Boolean lambda$static$1(EndGatewayConfig endGatewayConfig) {
        return endGatewayConfig.exact;
    }

    private static Optional lambda$static$0(EndGatewayConfig endGatewayConfig) {
        return endGatewayConfig.exit;
    }
}

