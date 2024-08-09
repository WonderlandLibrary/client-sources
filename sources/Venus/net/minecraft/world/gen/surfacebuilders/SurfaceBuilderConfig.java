/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;

public class SurfaceBuilderConfig
implements ISurfaceBuilderConfig {
    public static final Codec<SurfaceBuilderConfig> field_237203_a_ = RecordCodecBuilder.create(SurfaceBuilderConfig::lambda$static$3);
    private final BlockState topMaterial;
    private final BlockState underMaterial;
    private final BlockState underWaterMaterial;

    public SurfaceBuilderConfig(BlockState blockState, BlockState blockState2, BlockState blockState3) {
        this.topMaterial = blockState;
        this.underMaterial = blockState2;
        this.underWaterMaterial = blockState3;
    }

    @Override
    public BlockState getTop() {
        return this.topMaterial;
    }

    @Override
    public BlockState getUnder() {
        return this.underMaterial;
    }

    public BlockState getUnderWaterMaterial() {
        return this.underWaterMaterial;
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)BlockState.CODEC.fieldOf("top_material")).forGetter(SurfaceBuilderConfig::lambda$static$0), ((MapCodec)BlockState.CODEC.fieldOf("under_material")).forGetter(SurfaceBuilderConfig::lambda$static$1), ((MapCodec)BlockState.CODEC.fieldOf("underwater_material")).forGetter(SurfaceBuilderConfig::lambda$static$2)).apply(instance, SurfaceBuilderConfig::new);
    }

    private static BlockState lambda$static$2(SurfaceBuilderConfig surfaceBuilderConfig) {
        return surfaceBuilderConfig.underWaterMaterial;
    }

    private static BlockState lambda$static$1(SurfaceBuilderConfig surfaceBuilderConfig) {
        return surfaceBuilderConfig.underMaterial;
    }

    private static BlockState lambda$static$0(SurfaceBuilderConfig surfaceBuilderConfig) {
        return surfaceBuilderConfig.topMaterial;
    }
}

