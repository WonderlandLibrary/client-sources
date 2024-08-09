/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.SimplexNoiseGenerator;

public class EndBiomeProvider
extends BiomeProvider {
    public static final Codec<EndBiomeProvider> CODEC = RecordCodecBuilder.create(EndBiomeProvider::lambda$static$2);
    private final SimplexNoiseGenerator generator;
    private final Registry<Biome> lookupRegistry;
    private final long seed;
    private final Biome theEndBiome;
    private final Biome endHighlandsBiome;
    private final Biome endMidlandsBiome;
    private final Biome smallEndIslandsBiome;
    private final Biome endBarrensBiome;

    public EndBiomeProvider(Registry<Biome> registry, long l) {
        this(registry, l, registry.getOrThrow(Biomes.THE_END), registry.getOrThrow(Biomes.END_HIGHLANDS), registry.getOrThrow(Biomes.END_MIDLANDS), registry.getOrThrow(Biomes.SMALL_END_ISLANDS), registry.getOrThrow(Biomes.END_BARRENS));
    }

    private EndBiomeProvider(Registry<Biome> registry, long l, Biome biome, Biome biome2, Biome biome3, Biome biome4, Biome biome5) {
        super(ImmutableList.of(biome, biome2, biome3, biome4, biome5));
        this.lookupRegistry = registry;
        this.seed = l;
        this.theEndBiome = biome;
        this.endHighlandsBiome = biome2;
        this.endMidlandsBiome = biome3;
        this.smallEndIslandsBiome = biome4;
        this.endBarrensBiome = biome5;
        SharedSeedRandom sharedSeedRandom = new SharedSeedRandom(l);
        sharedSeedRandom.skip(17292);
        this.generator = new SimplexNoiseGenerator(sharedSeedRandom);
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @Override
    public BiomeProvider getBiomeProvider(long l) {
        return new EndBiomeProvider(this.lookupRegistry, l, this.theEndBiome, this.endHighlandsBiome, this.endMidlandsBiome, this.smallEndIslandsBiome, this.endBarrensBiome);
    }

    @Override
    public Biome getNoiseBiome(int n, int n2, int n3) {
        int n4 = n >> 2;
        int n5 = n3 >> 2;
        if ((long)n4 * (long)n4 + (long)n5 * (long)n5 <= 4096L) {
            return this.theEndBiome;
        }
        float f = EndBiomeProvider.getRandomNoise(this.generator, n4 * 2 + 1, n5 * 2 + 1);
        if (f > 40.0f) {
            return this.endHighlandsBiome;
        }
        if (f >= 0.0f) {
            return this.endMidlandsBiome;
        }
        return f < -20.0f ? this.smallEndIslandsBiome : this.endBarrensBiome;
    }

    public boolean areProvidersEqual(long l) {
        return this.seed == l;
    }

    public static float getRandomNoise(SimplexNoiseGenerator simplexNoiseGenerator, int n, int n2) {
        int n3 = n / 2;
        int n4 = n2 / 2;
        int n5 = n % 2;
        int n6 = n2 % 2;
        float f = 100.0f - MathHelper.sqrt(n * n + n2 * n2) * 8.0f;
        f = MathHelper.clamp(f, -100.0f, 80.0f);
        for (int i = -12; i <= 12; ++i) {
            for (int j = -12; j <= 12; ++j) {
                long l = n3 + i;
                long l2 = n4 + j;
                if (l * l + l2 * l2 <= 4096L || !(simplexNoiseGenerator.getValue(l, l2) < (double)-0.9f)) continue;
                float f2 = (MathHelper.abs(l) * 3439.0f + MathHelper.abs(l2) * 147.0f) % 13.0f + 9.0f;
                float f3 = n5 - i * 2;
                float f4 = n6 - j * 2;
                float f5 = 100.0f - MathHelper.sqrt(f3 * f3 + f4 * f4) * f2;
                f5 = MathHelper.clamp(f5, -100.0f, 80.0f);
                f = Math.max(f, f5);
            }
        }
        return f;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter(EndBiomeProvider::lambda$static$0), ((MapCodec)Codec.LONG.fieldOf("seed")).stable().forGetter(EndBiomeProvider::lambda$static$1)).apply(instance, instance.stable(EndBiomeProvider::new));
    }

    private static Long lambda$static$1(EndBiomeProvider endBiomeProvider) {
        return endBiomeProvider.seed;
    }

    private static Registry lambda$static$0(EndBiomeProvider endBiomeProvider) {
        return endBiomeProvider.lookupRegistry;
    }
}

