/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class ConfiguredSurfaceBuilder<SC extends ISurfaceBuilderConfig> {
    public static final Codec<ConfiguredSurfaceBuilder<?>> field_237168_a_ = Registry.SURFACE_BUILDER.dispatch(ConfiguredSurfaceBuilder::lambda$static$0, SurfaceBuilder::func_237202_d_);
    public static final Codec<Supplier<ConfiguredSurfaceBuilder<?>>> field_244393_b_ = RegistryKeyCodec.create(Registry.CONFIGURED_SURFACE_BUILDER_KEY, field_237168_a_);
    public final SurfaceBuilder<SC> builder;
    public final SC config;

    public ConfiguredSurfaceBuilder(SurfaceBuilder<SC> surfaceBuilder, SC SC) {
        this.builder = surfaceBuilder;
        this.config = SC;
    }

    public void buildSurface(Random random2, IChunk iChunk, Biome biome, int n, int n2, int n3, double d, BlockState blockState, BlockState blockState2, int n4, long l) {
        this.builder.buildSurface(random2, iChunk, biome, n, n2, n3, d, blockState, blockState2, n4, l, this.config);
    }

    public void setSeed(long l) {
        this.builder.setSeed(l);
    }

    public SC getConfig() {
        return this.config;
    }

    private static SurfaceBuilder lambda$static$0(ConfiguredSurfaceBuilder configuredSurfaceBuilder) {
        return configuredSurfaceBuilder.builder;
    }
}

