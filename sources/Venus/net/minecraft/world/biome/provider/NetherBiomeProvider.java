/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.MaxMinNoiseMixer;

public class NetherBiomeProvider
extends BiomeProvider {
    private static final Noise DEFAULT_NOISE = new Noise(-7, ImmutableList.of(1.0, 1.0));
    public static final MapCodec<NetherBiomeProvider> PACKET_CODEC = RecordCodecBuilder.mapCodec(NetherBiomeProvider::lambda$static$7);
    public static final Codec<NetherBiomeProvider> CODEC = Codec.mapEither(DefaultBuilder.CODEC, PACKET_CODEC).xmap(NetherBiomeProvider::lambda$static$8, NetherBiomeProvider::lambda$static$10).codec();
    private final Noise temperatureNoise;
    private final Noise humidityNoise;
    private final Noise altitudeNoise;
    private final Noise weirdnessNoise;
    private final MaxMinNoiseMixer temperatureNoiseMixer;
    private final MaxMinNoiseMixer humidityNoiseMixer;
    private final MaxMinNoiseMixer altitudeNoiseMixer;
    private final MaxMinNoiseMixer weirdnessNoiseMixer;
    private final List<Pair<Biome.Attributes, Supplier<Biome>>> biomeAttributes;
    private final boolean useHeightForNoise;
    private final long seed;
    private final Optional<Pair<Registry<Biome>, Preset>> netherProviderPreset;

    private NetherBiomeProvider(long l, List<Pair<Biome.Attributes, Supplier<Biome>>> list, Optional<Pair<Registry<Biome>, Preset>> optional) {
        this(l, list, DEFAULT_NOISE, DEFAULT_NOISE, DEFAULT_NOISE, DEFAULT_NOISE, optional);
    }

    private NetherBiomeProvider(long l, List<Pair<Biome.Attributes, Supplier<Biome>>> list, Noise noise, Noise noise2, Noise noise3, Noise noise4) {
        this(l, list, noise, noise2, noise3, noise4, Optional.empty());
    }

    private NetherBiomeProvider(long l, List<Pair<Biome.Attributes, Supplier<Biome>>> list, Noise noise, Noise noise2, Noise noise3, Noise noise4, Optional<Pair<Registry<Biome>, Preset>> optional) {
        super(list.stream().map(Pair::getSecond));
        this.seed = l;
        this.netherProviderPreset = optional;
        this.temperatureNoise = noise;
        this.humidityNoise = noise2;
        this.altitudeNoise = noise3;
        this.weirdnessNoise = noise4;
        this.temperatureNoiseMixer = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(l), noise.getNumberOfOctaves(), noise.getAmplitudes());
        this.humidityNoiseMixer = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(l + 1L), noise2.getNumberOfOctaves(), noise2.getAmplitudes());
        this.altitudeNoiseMixer = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(l + 2L), noise3.getNumberOfOctaves(), noise3.getAmplitudes());
        this.weirdnessNoiseMixer = MaxMinNoiseMixer.func_242930_a(new SharedSeedRandom(l + 3L), noise4.getNumberOfOctaves(), noise4.getAmplitudes());
        this.biomeAttributes = list;
        this.useHeightForNoise = false;
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @Override
    public BiomeProvider getBiomeProvider(long l) {
        return new NetherBiomeProvider(l, this.biomeAttributes, this.temperatureNoise, this.humidityNoise, this.altitudeNoise, this.weirdnessNoise, this.netherProviderPreset);
    }

    private Optional<DefaultBuilder> getDefaultBuilder() {
        return this.netherProviderPreset.map(this::lambda$getDefaultBuilder$11);
    }

    @Override
    public Biome getNoiseBiome(int n, int n2, int n3) {
        int n4 = this.useHeightForNoise ? n2 : 0;
        Biome.Attributes attributes = new Biome.Attributes((float)this.temperatureNoiseMixer.func_237211_a_(n, n4, n3), (float)this.humidityNoiseMixer.func_237211_a_(n, n4, n3), (float)this.altitudeNoiseMixer.func_237211_a_(n, n4, n3), (float)this.weirdnessNoiseMixer.func_237211_a_(n, n4, n3), 0.0f);
        return this.biomeAttributes.stream().min(Comparator.comparing(arg_0 -> NetherBiomeProvider.lambda$getNoiseBiome$12(attributes, arg_0))).map(Pair::getSecond).map(Supplier::get).orElse(BiomeRegistry.THE_VOID);
    }

    public boolean isDefaultPreset(long l) {
        return this.seed == l && this.netherProviderPreset.isPresent() && Objects.equals(this.netherProviderPreset.get().getSecond(), Preset.DEFAULT_NETHER_PROVIDER_PRESET);
    }

    private static Float lambda$getNoiseBiome$12(Biome.Attributes attributes, Pair pair) {
        return Float.valueOf(((Biome.Attributes)pair.getFirst()).getAttributeDifference(attributes));
    }

    private DefaultBuilder lambda$getDefaultBuilder$11(Pair pair) {
        return new DefaultBuilder((Preset)pair.getSecond(), (Registry)pair.getFirst(), this.seed);
    }

    private static Either lambda$static$10(NetherBiomeProvider netherBiomeProvider) {
        return netherBiomeProvider.getDefaultBuilder().map(Either::left).orElseGet(() -> NetherBiomeProvider.lambda$static$9(netherBiomeProvider));
    }

    private static Either lambda$static$9(NetherBiomeProvider netherBiomeProvider) {
        return Either.right(netherBiomeProvider);
    }

    private static NetherBiomeProvider lambda$static$8(Either either) {
        return either.map(DefaultBuilder::build, Function.identity());
    }

    private static App lambda$static$7(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.LONG.fieldOf("seed")).forGetter(NetherBiomeProvider::lambda$static$0), ((MapCodec)RecordCodecBuilder.create(NetherBiomeProvider::lambda$static$1).listOf().fieldOf("biomes")).forGetter(NetherBiomeProvider::lambda$static$2), ((MapCodec)Noise.CODEC.fieldOf("temperature_noise")).forGetter(NetherBiomeProvider::lambda$static$3), ((MapCodec)Noise.CODEC.fieldOf("humidity_noise")).forGetter(NetherBiomeProvider::lambda$static$4), ((MapCodec)Noise.CODEC.fieldOf("altitude_noise")).forGetter(NetherBiomeProvider::lambda$static$5), ((MapCodec)Noise.CODEC.fieldOf("weirdness_noise")).forGetter(NetherBiomeProvider::lambda$static$6)).apply(instance, NetherBiomeProvider::new);
    }

    private static Noise lambda$static$6(NetherBiomeProvider netherBiomeProvider) {
        return netherBiomeProvider.weirdnessNoise;
    }

    private static Noise lambda$static$5(NetherBiomeProvider netherBiomeProvider) {
        return netherBiomeProvider.altitudeNoise;
    }

    private static Noise lambda$static$4(NetherBiomeProvider netherBiomeProvider) {
        return netherBiomeProvider.humidityNoise;
    }

    private static Noise lambda$static$3(NetherBiomeProvider netherBiomeProvider) {
        return netherBiomeProvider.temperatureNoise;
    }

    private static List lambda$static$2(NetherBiomeProvider netherBiomeProvider) {
        return netherBiomeProvider.biomeAttributes;
    }

    private static App lambda$static$1(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Biome.Attributes.CODEC.fieldOf("parameters")).forGetter(Pair::getFirst), ((MapCodec)Biome.BIOME_CODEC.fieldOf("biome")).forGetter(Pair::getSecond)).apply(instance, Pair::of);
    }

    private static Long lambda$static$0(NetherBiomeProvider netherBiomeProvider) {
        return netherBiomeProvider.seed;
    }

    static class Noise {
        private final int numOctaves;
        private final DoubleList amplitudes;
        public static final Codec<Noise> CODEC = RecordCodecBuilder.create(Noise::lambda$static$0);

        public Noise(int n, List<Double> list) {
            this.numOctaves = n;
            this.amplitudes = new DoubleArrayList(list);
        }

        public int getNumberOfOctaves() {
            return this.numOctaves;
        }

        public DoubleList getAmplitudes() {
            return this.amplitudes;
        }

        private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
            return instance.group(((MapCodec)Codec.INT.fieldOf("firstOctave")).forGetter(Noise::getNumberOfOctaves), ((MapCodec)Codec.DOUBLE.listOf().fieldOf("amplitudes")).forGetter(Noise::getAmplitudes)).apply(instance, Noise::new);
        }
    }

    public static class Preset {
        private static final Map<ResourceLocation, Preset> PRESETS = Maps.newHashMap();
        public static final Preset DEFAULT_NETHER_PROVIDER_PRESET = new Preset(new ResourceLocation("nether"), Preset::lambda$static$5);
        private final ResourceLocation id;
        private final Function3<Preset, Registry<Biome>, Long, NetherBiomeProvider> netherProviderFunction;

        public Preset(ResourceLocation resourceLocation, Function3<Preset, Registry<Biome>, Long, NetherBiomeProvider> function3) {
            this.id = resourceLocation;
            this.netherProviderFunction = function3;
            PRESETS.put(resourceLocation, this);
        }

        public NetherBiomeProvider build(Registry<Biome> registry, long l) {
            return this.netherProviderFunction.apply(this, registry, l);
        }

        private static NetherBiomeProvider lambda$static$5(Preset preset, Registry registry, Long l) {
            return new NetherBiomeProvider(l, ImmutableList.of(Pair.of(new Biome.Attributes(0.0f, 0.0f, 0.0f, 0.0f, 0.0f), () -> Preset.lambda$static$0(registry)), Pair.of(new Biome.Attributes(0.0f, -0.5f, 0.0f, 0.0f, 0.0f), () -> Preset.lambda$static$1(registry)), Pair.of(new Biome.Attributes(0.4f, 0.0f, 0.0f, 0.0f, 0.0f), () -> Preset.lambda$static$2(registry)), Pair.of(new Biome.Attributes(0.0f, 0.5f, 0.0f, 0.0f, 0.375f), () -> Preset.lambda$static$3(registry)), Pair.of(new Biome.Attributes(-0.5f, 0.0f, 0.0f, 0.0f, 0.175f), () -> Preset.lambda$static$4(registry))), Optional.of(Pair.of(registry, preset)));
        }

        private static Biome lambda$static$4(Registry registry) {
            return registry.getOrThrow(Biomes.BASALT_DELTAS);
        }

        private static Biome lambda$static$3(Registry registry) {
            return registry.getOrThrow(Biomes.WARPED_FOREST);
        }

        private static Biome lambda$static$2(Registry registry) {
            return registry.getOrThrow(Biomes.CRIMSON_FOREST);
        }

        private static Biome lambda$static$1(Registry registry) {
            return registry.getOrThrow(Biomes.SOUL_SAND_VALLEY);
        }

        private static Biome lambda$static$0(Registry registry) {
            return registry.getOrThrow(Biomes.NETHER_WASTES);
        }
    }

    static final class DefaultBuilder {
        public static final MapCodec<DefaultBuilder> CODEC = RecordCodecBuilder.mapCodec(DefaultBuilder::lambda$static$3);
        private final Preset preset;
        private final Registry<Biome> lookupRegistry;
        private final long seed;

        private DefaultBuilder(Preset preset, Registry<Biome> registry, long l) {
            this.preset = preset;
            this.lookupRegistry = registry;
            this.seed = l;
        }

        public Preset getPreset() {
            return this.preset;
        }

        public Registry<Biome> getLookupRegistry() {
            return this.lookupRegistry;
        }

        public long getSeed() {
            return this.seed;
        }

        public NetherBiomeProvider build() {
            return this.preset.build(this.lookupRegistry, this.seed);
        }

        private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
            return instance.group(((MapCodec)ResourceLocation.CODEC.flatXmap(DefaultBuilder::lambda$static$1, DefaultBuilder::lambda$static$2).fieldOf("preset")).stable().forGetter(DefaultBuilder::getPreset), RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter(DefaultBuilder::getLookupRegistry), ((MapCodec)Codec.LONG.fieldOf("seed")).stable().forGetter(DefaultBuilder::getSeed)).apply(instance, instance.stable(DefaultBuilder::new));
        }

        private static DataResult lambda$static$2(Preset preset) {
            return DataResult.success(preset.id);
        }

        private static DataResult lambda$static$1(ResourceLocation resourceLocation) {
            return Optional.ofNullable(Preset.PRESETS.get(resourceLocation)).map(DataResult::success).orElseGet(() -> DefaultBuilder.lambda$static$0(resourceLocation));
        }

        private static DataResult lambda$static$0(ResourceLocation resourceLocation) {
            return DataResult.error("Unknown preset: " + resourceLocation);
        }
    }
}

