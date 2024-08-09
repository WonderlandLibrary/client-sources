/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKeyCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;

public class ConfiguredCarver<WC extends ICarverConfig> {
    public static final Codec<ConfiguredCarver<?>> field_236235_a_ = Registry.CARVER.dispatch(ConfiguredCarver::lambda$static$0, WorldCarver::func_236244_c_);
    public static final Codec<Supplier<ConfiguredCarver<?>>> field_244390_b_ = RegistryKeyCodec.create(Registry.CONFIGURED_CARVER_KEY, field_236235_a_);
    public static final Codec<List<Supplier<ConfiguredCarver<?>>>> field_242759_c = RegistryKeyCodec.getValueCodecs(Registry.CONFIGURED_CARVER_KEY, field_236235_a_);
    private final WorldCarver<WC> carver;
    private final WC config;

    public ConfiguredCarver(WorldCarver<WC> worldCarver, WC WC) {
        this.carver = worldCarver;
        this.config = WC;
    }

    public WC func_242760_a() {
        return this.config;
    }

    public boolean shouldCarve(Random random2, int n, int n2) {
        return this.carver.shouldCarve(random2, n, n2, this.config);
    }

    public boolean carveRegion(IChunk iChunk, Function<BlockPos, Biome> function, Random random2, int n, int n2, int n3, int n4, int n5, BitSet bitSet) {
        return this.carver.carveRegion(iChunk, function, random2, n, n2, n3, n4, n5, bitSet, this.config);
    }

    private static WorldCarver lambda$static$0(ConfiguredCarver configuredCarver) {
        return configuredCarver.carver;
    }
}

