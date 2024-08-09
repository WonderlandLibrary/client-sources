/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.surfacebuilders;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ConfiguredSurfaceBuilders {
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244169_a = ConfiguredSurfaceBuilders.func_244192_a("badlands", SurfaceBuilder.BADLANDS.func_242929_a(SurfaceBuilder.RED_SAND_WHITE_TERRACOTTA_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244170_b = ConfiguredSurfaceBuilders.func_244192_a("basalt_deltas", SurfaceBuilder.field_237191_af_.func_242929_a(SurfaceBuilder.field_237187_R_));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244171_c = ConfiguredSurfaceBuilders.func_244192_a("crimson_forest", SurfaceBuilder.field_237189_ad_.func_242929_a(SurfaceBuilder.field_237185_P_));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244172_d = ConfiguredSurfaceBuilders.func_244192_a("desert", SurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilder.SAND_SAND_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244173_e = ConfiguredSurfaceBuilders.func_244192_a("end", SurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilder.END_STONE_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244174_f = ConfiguredSurfaceBuilders.func_244192_a("eroded_badlands", SurfaceBuilder.ERODED_BADLANDS.func_242929_a(SurfaceBuilder.RED_SAND_WHITE_TERRACOTTA_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244175_g = ConfiguredSurfaceBuilders.func_244192_a("frozen_ocean", SurfaceBuilder.FROZEN_OCEAN.func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244176_h = ConfiguredSurfaceBuilders.func_244192_a("full_sand", SurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilder.SAND_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244177_i = ConfiguredSurfaceBuilders.func_244192_a("giant_tree_taiga", SurfaceBuilder.GIANT_TREE_TAIGA.func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244178_j = ConfiguredSurfaceBuilders.func_244192_a("grass", SurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244179_k = ConfiguredSurfaceBuilders.func_244192_a("gravelly_mountain", SurfaceBuilder.GRAVELLY_MOUNTAIN.func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244180_l = ConfiguredSurfaceBuilders.func_244192_a("ice_spikes", SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState())));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244181_m = ConfiguredSurfaceBuilders.func_244192_a("mountain", SurfaceBuilder.MOUNTAIN.func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244182_n = ConfiguredSurfaceBuilders.func_244192_a("mycelium", SurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilder.MYCELIUM_DIRT_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244183_o = ConfiguredSurfaceBuilders.func_244192_a("nether", SurfaceBuilder.NETHER.func_242929_a(SurfaceBuilder.NETHERRACK_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244184_p = ConfiguredSurfaceBuilders.func_244192_a("nope", SurfaceBuilder.NOPE.func_242929_a(SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244185_q = ConfiguredSurfaceBuilders.func_244192_a("ocean_sand", SurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilder.GRASS_DIRT_SAND_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244186_r = ConfiguredSurfaceBuilders.func_244192_a("shattered_savanna", SurfaceBuilder.SHATTERED_SAVANNA.func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244187_s = ConfiguredSurfaceBuilders.func_244192_a("soul_sand_valley", SurfaceBuilder.field_237190_ae_.func_242929_a(SurfaceBuilder.field_237184_N_));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244188_t = ConfiguredSurfaceBuilders.func_244192_a("stone", SurfaceBuilder.DEFAULT.func_242929_a(SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244189_u = ConfiguredSurfaceBuilders.func_244192_a("swamp", SurfaceBuilder.SWAMP.func_242929_a(SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244190_v = ConfiguredSurfaceBuilders.func_244192_a("warped_forest", SurfaceBuilder.field_237189_ad_.func_242929_a(SurfaceBuilder.field_237186_Q_));
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> field_244191_w = ConfiguredSurfaceBuilders.func_244192_a("wooded_badlands", SurfaceBuilder.WOODED_BADLANDS.func_242929_a(SurfaceBuilder.RED_SAND_WHITE_TERRACOTTA_GRAVEL_CONFIG));

    private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> func_244192_a(String string, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, string, configuredSurfaceBuilder);
    }
}

