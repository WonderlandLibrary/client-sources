/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public abstract class Biomes {
    public static final RegistryKey<Biome> OCEAN = Biomes.makeKey("ocean");
    public static final RegistryKey<Biome> PLAINS = Biomes.makeKey("plains");
    public static final RegistryKey<Biome> DESERT = Biomes.makeKey("desert");
    public static final RegistryKey<Biome> MOUNTAINS = Biomes.makeKey("mountains");
    public static final RegistryKey<Biome> FOREST = Biomes.makeKey("forest");
    public static final RegistryKey<Biome> TAIGA = Biomes.makeKey("taiga");
    public static final RegistryKey<Biome> SWAMP = Biomes.makeKey("swamp");
    public static final RegistryKey<Biome> RIVER = Biomes.makeKey("river");
    public static final RegistryKey<Biome> NETHER_WASTES = Biomes.makeKey("nether_wastes");
    public static final RegistryKey<Biome> THE_END = Biomes.makeKey("the_end");
    public static final RegistryKey<Biome> FROZEN_OCEAN = Biomes.makeKey("frozen_ocean");
    public static final RegistryKey<Biome> FROZEN_RIVER = Biomes.makeKey("frozen_river");
    public static final RegistryKey<Biome> SNOWY_TUNDRA = Biomes.makeKey("snowy_tundra");
    public static final RegistryKey<Biome> SNOWY_MOUNTAINS = Biomes.makeKey("snowy_mountains");
    public static final RegistryKey<Biome> MUSHROOM_FIELDS = Biomes.makeKey("mushroom_fields");
    public static final RegistryKey<Biome> MUSHROOM_FIELD_SHORE = Biomes.makeKey("mushroom_field_shore");
    public static final RegistryKey<Biome> BEACH = Biomes.makeKey("beach");
    public static final RegistryKey<Biome> DESERT_HILLS = Biomes.makeKey("desert_hills");
    public static final RegistryKey<Biome> WOODED_HILLS = Biomes.makeKey("wooded_hills");
    public static final RegistryKey<Biome> TAIGA_HILLS = Biomes.makeKey("taiga_hills");
    public static final RegistryKey<Biome> MOUNTAIN_EDGE = Biomes.makeKey("mountain_edge");
    public static final RegistryKey<Biome> JUNGLE = Biomes.makeKey("jungle");
    public static final RegistryKey<Biome> JUNGLE_HILLS = Biomes.makeKey("jungle_hills");
    public static final RegistryKey<Biome> JUNGLE_EDGE = Biomes.makeKey("jungle_edge");
    public static final RegistryKey<Biome> DEEP_OCEAN = Biomes.makeKey("deep_ocean");
    public static final RegistryKey<Biome> STONE_SHORE = Biomes.makeKey("stone_shore");
    public static final RegistryKey<Biome> SNOWY_BEACH = Biomes.makeKey("snowy_beach");
    public static final RegistryKey<Biome> BIRCH_FOREST = Biomes.makeKey("birch_forest");
    public static final RegistryKey<Biome> BIRCH_FOREST_HILLS = Biomes.makeKey("birch_forest_hills");
    public static final RegistryKey<Biome> DARK_FOREST = Biomes.makeKey("dark_forest");
    public static final RegistryKey<Biome> SNOWY_TAIGA = Biomes.makeKey("snowy_taiga");
    public static final RegistryKey<Biome> SNOWY_TAIGA_HILLS = Biomes.makeKey("snowy_taiga_hills");
    public static final RegistryKey<Biome> GIANT_TREE_TAIGA = Biomes.makeKey("giant_tree_taiga");
    public static final RegistryKey<Biome> GIANT_TREE_TAIGA_HILLS = Biomes.makeKey("giant_tree_taiga_hills");
    public static final RegistryKey<Biome> WOODED_MOUNTAINS = Biomes.makeKey("wooded_mountains");
    public static final RegistryKey<Biome> SAVANNA = Biomes.makeKey("savanna");
    public static final RegistryKey<Biome> SAVANNA_PLATEAU = Biomes.makeKey("savanna_plateau");
    public static final RegistryKey<Biome> BADLANDS = Biomes.makeKey("badlands");
    public static final RegistryKey<Biome> WOODED_BADLANDS_PLATEAU = Biomes.makeKey("wooded_badlands_plateau");
    public static final RegistryKey<Biome> BADLANDS_PLATEAU = Biomes.makeKey("badlands_plateau");
    public static final RegistryKey<Biome> SMALL_END_ISLANDS = Biomes.makeKey("small_end_islands");
    public static final RegistryKey<Biome> END_MIDLANDS = Biomes.makeKey("end_midlands");
    public static final RegistryKey<Biome> END_HIGHLANDS = Biomes.makeKey("end_highlands");
    public static final RegistryKey<Biome> END_BARRENS = Biomes.makeKey("end_barrens");
    public static final RegistryKey<Biome> WARM_OCEAN = Biomes.makeKey("warm_ocean");
    public static final RegistryKey<Biome> LUKEWARM_OCEAN = Biomes.makeKey("lukewarm_ocean");
    public static final RegistryKey<Biome> COLD_OCEAN = Biomes.makeKey("cold_ocean");
    public static final RegistryKey<Biome> DEEP_WARM_OCEAN = Biomes.makeKey("deep_warm_ocean");
    public static final RegistryKey<Biome> DEEP_LUKEWARM_OCEAN = Biomes.makeKey("deep_lukewarm_ocean");
    public static final RegistryKey<Biome> DEEP_COLD_OCEAN = Biomes.makeKey("deep_cold_ocean");
    public static final RegistryKey<Biome> DEEP_FROZEN_OCEAN = Biomes.makeKey("deep_frozen_ocean");
    public static final RegistryKey<Biome> THE_VOID = Biomes.makeKey("the_void");
    public static final RegistryKey<Biome> SUNFLOWER_PLAINS = Biomes.makeKey("sunflower_plains");
    public static final RegistryKey<Biome> DESERT_LAKES = Biomes.makeKey("desert_lakes");
    public static final RegistryKey<Biome> GRAVELLY_MOUNTAINS = Biomes.makeKey("gravelly_mountains");
    public static final RegistryKey<Biome> FLOWER_FOREST = Biomes.makeKey("flower_forest");
    public static final RegistryKey<Biome> TAIGA_MOUNTAINS = Biomes.makeKey("taiga_mountains");
    public static final RegistryKey<Biome> SWAMP_HILLS = Biomes.makeKey("swamp_hills");
    public static final RegistryKey<Biome> ICE_SPIKES = Biomes.makeKey("ice_spikes");
    public static final RegistryKey<Biome> MODIFIED_JUNGLE = Biomes.makeKey("modified_jungle");
    public static final RegistryKey<Biome> MODIFIED_JUNGLE_EDGE = Biomes.makeKey("modified_jungle_edge");
    public static final RegistryKey<Biome> TALL_BIRCH_FOREST = Biomes.makeKey("tall_birch_forest");
    public static final RegistryKey<Biome> TALL_BIRCH_HILLS = Biomes.makeKey("tall_birch_hills");
    public static final RegistryKey<Biome> DARK_FOREST_HILLS = Biomes.makeKey("dark_forest_hills");
    public static final RegistryKey<Biome> SNOWY_TAIGA_MOUNTAINS = Biomes.makeKey("snowy_taiga_mountains");
    public static final RegistryKey<Biome> GIANT_SPRUCE_TAIGA = Biomes.makeKey("giant_spruce_taiga");
    public static final RegistryKey<Biome> GIANT_SPRUCE_TAIGA_HILLS = Biomes.makeKey("giant_spruce_taiga_hills");
    public static final RegistryKey<Biome> MODIFIED_GRAVELLY_MOUNTAINS = Biomes.makeKey("modified_gravelly_mountains");
    public static final RegistryKey<Biome> SHATTERED_SAVANNA = Biomes.makeKey("shattered_savanna");
    public static final RegistryKey<Biome> SHATTERED_SAVANNA_PLATEAU = Biomes.makeKey("shattered_savanna_plateau");
    public static final RegistryKey<Biome> ERODED_BADLANDS = Biomes.makeKey("eroded_badlands");
    public static final RegistryKey<Biome> MODIFIED_WOODED_BADLANDS_PLATEAU = Biomes.makeKey("modified_wooded_badlands_plateau");
    public static final RegistryKey<Biome> MODIFIED_BADLANDS_PLATEAU = Biomes.makeKey("modified_badlands_plateau");
    public static final RegistryKey<Biome> BAMBOO_JUNGLE = Biomes.makeKey("bamboo_jungle");
    public static final RegistryKey<Biome> BAMBOO_JUNGLE_HILLS = Biomes.makeKey("bamboo_jungle_hills");
    public static final RegistryKey<Biome> SOUL_SAND_VALLEY = Biomes.makeKey("soul_sand_valley");
    public static final RegistryKey<Biome> CRIMSON_FOREST = Biomes.makeKey("crimson_forest");
    public static final RegistryKey<Biome> WARPED_FOREST = Biomes.makeKey("warped_forest");
    public static final RegistryKey<Biome> BASALT_DELTAS = Biomes.makeKey("basalt_deltas");

    private static RegistryKey<Biome> makeKey(String string) {
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(string));
    }
}

