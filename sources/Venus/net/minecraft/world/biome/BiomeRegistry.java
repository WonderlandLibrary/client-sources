/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

public abstract class BiomeRegistry {
    private static final Int2ObjectMap<RegistryKey<Biome>> idToKeyMap = new Int2ObjectArrayMap<RegistryKey<Biome>>();
    public static final Biome PLAINS = BiomeRegistry.register(1, Biomes.PLAINS, BiomeMaker.makePlainsBiome(false));
    public static final Biome THE_VOID = BiomeRegistry.register(127, Biomes.THE_VOID, BiomeMaker.makeVoidBiome());

    private static Biome register(int n, RegistryKey<Biome> registryKey, Biome biome) {
        idToKeyMap.put(n, registryKey);
        return WorldGenRegistries.register(WorldGenRegistries.BIOME, n, registryKey, biome);
    }

    public static RegistryKey<Biome> getKeyFromID(int n) {
        return (RegistryKey)idToKeyMap.get(n);
    }

    static {
        BiomeRegistry.register(0, Biomes.OCEAN, BiomeMaker.makeOceanBiome(false));
        BiomeRegistry.register(2, Biomes.DESERT, BiomeMaker.makeDesertBiome(0.125f, 0.05f, true, true, true));
        BiomeRegistry.register(3, Biomes.MOUNTAINS, BiomeMaker.makeMountainBiome(1.0f, 0.5f, ConfiguredSurfaceBuilders.field_244181_m, false));
        BiomeRegistry.register(4, Biomes.FOREST, BiomeMaker.makeForestBiome(0.1f, 0.2f));
        BiomeRegistry.register(5, Biomes.TAIGA, BiomeMaker.makeTaigaBiome(0.2f, 0.2f, false, false, true, false));
        BiomeRegistry.register(6, Biomes.SWAMP, BiomeMaker.makeGenericSwampBiome(-0.2f, 0.1f, false));
        BiomeRegistry.register(7, Biomes.RIVER, BiomeMaker.makeRiverBiome(-0.5f, 0.0f, 0.5f, 4159204, false));
        BiomeRegistry.register(8, Biomes.NETHER_WASTES, BiomeMaker.makeNetherWastesBiome());
        BiomeRegistry.register(9, Biomes.THE_END, BiomeMaker.makeTheEndBiome());
        BiomeRegistry.register(10, Biomes.FROZEN_OCEAN, BiomeMaker.makeFrozenOceanBiome(false));
        BiomeRegistry.register(11, Biomes.FROZEN_RIVER, BiomeMaker.makeRiverBiome(-0.5f, 0.0f, 0.0f, 3750089, true));
        BiomeRegistry.register(12, Biomes.SNOWY_TUNDRA, BiomeMaker.makeSnowyBiome(0.125f, 0.05f, false, false));
        BiomeRegistry.register(13, Biomes.SNOWY_MOUNTAINS, BiomeMaker.makeSnowyBiome(0.45f, 0.3f, false, true));
        BiomeRegistry.register(14, Biomes.MUSHROOM_FIELDS, BiomeMaker.makeMushroomBiome(0.2f, 0.3f));
        BiomeRegistry.register(15, Biomes.MUSHROOM_FIELD_SHORE, BiomeMaker.makeMushroomBiome(0.0f, 0.025f));
        BiomeRegistry.register(16, Biomes.BEACH, BiomeMaker.makeGenericBeachBiome(0.0f, 0.025f, 0.8f, 0.4f, 4159204, false, false));
        BiomeRegistry.register(17, Biomes.DESERT_HILLS, BiomeMaker.makeDesertBiome(0.45f, 0.3f, false, true, false));
        BiomeRegistry.register(18, Biomes.WOODED_HILLS, BiomeMaker.makeForestBiome(0.45f, 0.3f));
        BiomeRegistry.register(19, Biomes.TAIGA_HILLS, BiomeMaker.makeTaigaBiome(0.45f, 0.3f, false, false, false, false));
        BiomeRegistry.register(20, Biomes.MOUNTAIN_EDGE, BiomeMaker.makeMountainBiome(0.8f, 0.3f, ConfiguredSurfaceBuilders.field_244178_j, true));
        BiomeRegistry.register(21, Biomes.JUNGLE, BiomeMaker.makeJungleBiome());
        BiomeRegistry.register(22, Biomes.JUNGLE_HILLS, BiomeMaker.makeJungleHillsBiome());
        BiomeRegistry.register(23, Biomes.JUNGLE_EDGE, BiomeMaker.makeJungleEdgeBiome());
        BiomeRegistry.register(24, Biomes.DEEP_OCEAN, BiomeMaker.makeOceanBiome(true));
        BiomeRegistry.register(25, Biomes.STONE_SHORE, BiomeMaker.makeGenericBeachBiome(0.1f, 0.8f, 0.2f, 0.3f, 4159204, false, true));
        BiomeRegistry.register(26, Biomes.SNOWY_BEACH, BiomeMaker.makeGenericBeachBiome(0.0f, 0.025f, 0.05f, 0.3f, 4020182, true, false));
        BiomeRegistry.register(27, Biomes.BIRCH_FOREST, BiomeMaker.makeBirchForestBiome(0.1f, 0.2f, false));
        BiomeRegistry.register(28, Biomes.BIRCH_FOREST_HILLS, BiomeMaker.makeBirchForestBiome(0.45f, 0.3f, false));
        BiomeRegistry.register(29, Biomes.DARK_FOREST, BiomeMaker.makeDarkForestBiome(0.1f, 0.2f, false));
        BiomeRegistry.register(30, Biomes.SNOWY_TAIGA, BiomeMaker.makeTaigaBiome(0.2f, 0.2f, true, false, false, true));
        BiomeRegistry.register(31, Biomes.SNOWY_TAIGA_HILLS, BiomeMaker.makeTaigaBiome(0.45f, 0.3f, true, false, false, false));
        BiomeRegistry.register(32, Biomes.GIANT_TREE_TAIGA, BiomeMaker.makeGiantTaigaBiome(0.2f, 0.2f, 0.3f, false));
        BiomeRegistry.register(33, Biomes.GIANT_TREE_TAIGA_HILLS, BiomeMaker.makeGiantTaigaBiome(0.45f, 0.3f, 0.3f, false));
        BiomeRegistry.register(34, Biomes.WOODED_MOUNTAINS, BiomeMaker.makeMountainBiome(1.0f, 0.5f, ConfiguredSurfaceBuilders.field_244178_j, true));
        BiomeRegistry.register(35, Biomes.SAVANNA, BiomeMaker.makeGenericSavannaBiome(0.125f, 0.05f, 1.2f, false, false));
        BiomeRegistry.register(36, Biomes.SAVANNA_PLATEAU, BiomeMaker.makeSavannaPlateauBiome());
        BiomeRegistry.register(37, Biomes.BADLANDS, BiomeMaker.makeBadlandsBiome(0.1f, 0.2f, false));
        BiomeRegistry.register(38, Biomes.WOODED_BADLANDS_PLATEAU, BiomeMaker.makeWoodedBadlandsPlateauBiome(1.5f, 0.025f));
        BiomeRegistry.register(39, Biomes.BADLANDS_PLATEAU, BiomeMaker.makeBadlandsBiome(1.5f, 0.025f, true));
        BiomeRegistry.register(40, Biomes.SMALL_END_ISLANDS, BiomeMaker.makeSmallEndIslandsBiome());
        BiomeRegistry.register(41, Biomes.END_MIDLANDS, BiomeMaker.makeEndMidlandsBiome());
        BiomeRegistry.register(42, Biomes.END_HIGHLANDS, BiomeMaker.makeEndHighlandsBiome());
        BiomeRegistry.register(43, Biomes.END_BARRENS, BiomeMaker.makeEndBarrensBiome());
        BiomeRegistry.register(44, Biomes.WARM_OCEAN, BiomeMaker.makeWarmOceanBiome());
        BiomeRegistry.register(45, Biomes.LUKEWARM_OCEAN, BiomeMaker.makeLukewarmOceanBiome(false));
        BiomeRegistry.register(46, Biomes.COLD_OCEAN, BiomeMaker.makeColdOceanBiome(false));
        BiomeRegistry.register(47, Biomes.DEEP_WARM_OCEAN, BiomeMaker.makeDeepWarmOceanBiome());
        BiomeRegistry.register(48, Biomes.DEEP_LUKEWARM_OCEAN, BiomeMaker.makeLukewarmOceanBiome(true));
        BiomeRegistry.register(49, Biomes.DEEP_COLD_OCEAN, BiomeMaker.makeColdOceanBiome(true));
        BiomeRegistry.register(50, Biomes.DEEP_FROZEN_OCEAN, BiomeMaker.makeFrozenOceanBiome(true));
        BiomeRegistry.register(129, Biomes.SUNFLOWER_PLAINS, BiomeMaker.makePlainsBiome(true));
        BiomeRegistry.register(130, Biomes.DESERT_LAKES, BiomeMaker.makeDesertBiome(0.225f, 0.25f, false, false, false));
        BiomeRegistry.register(131, Biomes.GRAVELLY_MOUNTAINS, BiomeMaker.makeMountainBiome(1.0f, 0.5f, ConfiguredSurfaceBuilders.field_244179_k, false));
        BiomeRegistry.register(132, Biomes.FLOWER_FOREST, BiomeMaker.makeFlowerForestBiome());
        BiomeRegistry.register(133, Biomes.TAIGA_MOUNTAINS, BiomeMaker.makeTaigaBiome(0.3f, 0.4f, false, true, false, false));
        BiomeRegistry.register(134, Biomes.SWAMP_HILLS, BiomeMaker.makeGenericSwampBiome(-0.1f, 0.3f, true));
        BiomeRegistry.register(140, Biomes.ICE_SPIKES, BiomeMaker.makeSnowyBiome(0.425f, 0.45000002f, true, false));
        BiomeRegistry.register(149, Biomes.MODIFIED_JUNGLE, BiomeMaker.makeModifiedJungleBiome());
        BiomeRegistry.register(151, Biomes.MODIFIED_JUNGLE_EDGE, BiomeMaker.makeModifiedJungleEdgeBiome());
        BiomeRegistry.register(155, Biomes.TALL_BIRCH_FOREST, BiomeMaker.makeBirchForestBiome(0.2f, 0.4f, true));
        BiomeRegistry.register(156, Biomes.TALL_BIRCH_HILLS, BiomeMaker.makeBirchForestBiome(0.55f, 0.5f, true));
        BiomeRegistry.register(157, Biomes.DARK_FOREST_HILLS, BiomeMaker.makeDarkForestBiome(0.2f, 0.4f, true));
        BiomeRegistry.register(158, Biomes.SNOWY_TAIGA_MOUNTAINS, BiomeMaker.makeTaigaBiome(0.3f, 0.4f, true, true, false, false));
        BiomeRegistry.register(160, Biomes.GIANT_SPRUCE_TAIGA, BiomeMaker.makeGiantTaigaBiome(0.2f, 0.2f, 0.25f, true));
        BiomeRegistry.register(161, Biomes.GIANT_SPRUCE_TAIGA_HILLS, BiomeMaker.makeGiantTaigaBiome(0.2f, 0.2f, 0.25f, true));
        BiomeRegistry.register(162, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, BiomeMaker.makeMountainBiome(1.0f, 0.5f, ConfiguredSurfaceBuilders.field_244179_k, false));
        BiomeRegistry.register(163, Biomes.SHATTERED_SAVANNA, BiomeMaker.makeGenericSavannaBiome(0.3625f, 1.225f, 1.1f, true, true));
        BiomeRegistry.register(164, Biomes.SHATTERED_SAVANNA_PLATEAU, BiomeMaker.makeGenericSavannaBiome(1.05f, 1.2125001f, 1.0f, true, true));
        BiomeRegistry.register(165, Biomes.ERODED_BADLANDS, BiomeMaker.makeErodedBadlandsBiome());
        BiomeRegistry.register(166, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, BiomeMaker.makeWoodedBadlandsPlateauBiome(0.45f, 0.3f));
        BiomeRegistry.register(167, Biomes.MODIFIED_BADLANDS_PLATEAU, BiomeMaker.makeBadlandsBiome(0.45f, 0.3f, true));
        BiomeRegistry.register(168, Biomes.BAMBOO_JUNGLE, BiomeMaker.makeBambooJungleBiome());
        BiomeRegistry.register(169, Biomes.BAMBOO_JUNGLE_HILLS, BiomeMaker.makeBambooJungleHillsBiome());
        BiomeRegistry.register(170, Biomes.SOUL_SAND_VALLEY, BiomeMaker.makeSoulSandValleyBiome());
        BiomeRegistry.register(171, Biomes.CRIMSON_FOREST, BiomeMaker.makeCrimsonForestBiome());
        BiomeRegistry.register(172, Biomes.WARPED_FOREST, BiomeMaker.makeWarpedForestBiome());
        BiomeRegistry.register(173, Biomes.BASALT_DELTAS, BiomeMaker.makeBasaltDeltasBiome());
    }
}

