/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome.provider;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;

public class OverworldBiomeProvider
extends BiomeProvider {
    public static final Codec<OverworldBiomeProvider> CODEC = RecordCodecBuilder.create(OverworldBiomeProvider::lambda$static$4);
    private final Layer genBiomes;
    private static final List<RegistryKey<Biome>> biomes = ImmutableList.of(Biomes.OCEAN, Biomes.PLAINS, Biomes.DESERT, Biomes.MOUNTAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.SWAMP, Biomes.RIVER, Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_MOUNTAINS, Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE, Biomes.BEACH, Biomes.DESERT_HILLS, Biomes.WOODED_HILLS, Biomes.TAIGA_HILLS, Biomes.MOUNTAIN_EDGE, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.DEEP_OCEAN, Biomes.STONE_SHORE, Biomes.SNOWY_BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.WOODED_MOUNTAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.BADLANDS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.BADLANDS_PLATEAU, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.SUNFLOWER_PLAINS, Biomes.DESERT_LAKES, Biomes.GRAVELLY_MOUNTAINS, Biomes.FLOWER_FOREST, Biomes.TAIGA_MOUNTAINS, Biomes.SWAMP_HILLS, Biomes.ICE_SPIKES, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.DARK_FOREST_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.ERODED_BADLANDS, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU);
    private final long seed;
    private final boolean legacyBiomes;
    private final boolean largeBiomes;
    private final Registry<Biome> lookupRegistry;

    public OverworldBiomeProvider(long l, boolean bl, boolean bl2, Registry<Biome> registry) {
        super(biomes.stream().map(arg_0 -> OverworldBiomeProvider.lambda$new$6(registry, arg_0)));
        this.seed = l;
        this.legacyBiomes = bl;
        this.largeBiomes = bl2;
        this.lookupRegistry = registry;
        this.genBiomes = LayerUtil.func_237215_a_(l, bl, bl2 ? 6 : 4, 4);
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @Override
    public BiomeProvider getBiomeProvider(long l) {
        return new OverworldBiomeProvider(l, this.legacyBiomes, this.largeBiomes, this.lookupRegistry);
    }

    @Override
    public Biome getNoiseBiome(int n, int n2, int n3) {
        return this.genBiomes.func_242936_a(this.lookupRegistry, n, n3);
    }

    private static Supplier lambda$new$6(Registry registry, RegistryKey registryKey) {
        return () -> OverworldBiomeProvider.lambda$new$5(registry, registryKey);
    }

    private static Biome lambda$new$5(Registry registry, RegistryKey registryKey) {
        return (Biome)registry.getOrThrow(registryKey);
    }

    private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.LONG.fieldOf("seed")).stable().forGetter(OverworldBiomeProvider::lambda$static$0), Codec.BOOL.optionalFieldOf("legacy_biome_init_layer", false, Lifecycle.stable()).forGetter(OverworldBiomeProvider::lambda$static$1), ((MapCodec)Codec.BOOL.fieldOf("large_biomes")).orElse(false).stable().forGetter(OverworldBiomeProvider::lambda$static$2), RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter(OverworldBiomeProvider::lambda$static$3)).apply(instance, instance.stable(OverworldBiomeProvider::new));
    }

    private static Registry lambda$static$3(OverworldBiomeProvider overworldBiomeProvider) {
        return overworldBiomeProvider.lookupRegistry;
    }

    private static Boolean lambda$static$2(OverworldBiomeProvider overworldBiomeProvider) {
        return overworldBiomeProvider.largeBiomes;
    }

    private static Boolean lambda$static$1(OverworldBiomeProvider overworldBiomeProvider) {
        return overworldBiomeProvider.legacyBiomes;
    }

    private static Long lambda$static$0(OverworldBiomeProvider overworldBiomeProvider) {
        return overworldBiomeProvider.seed;
    }
}

