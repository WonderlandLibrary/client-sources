/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.OptionalInt;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.AxisRotatingBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.ForestFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.PlainFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BasaltDeltasFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BlobReplacementConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.ColumnConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.EndGatewayConfig;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.SingleRandomFeature;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.ThreeLayerFeature;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.AcaciaFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.DarkOakFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.JungleFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.MegaPineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.PineFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.CocoaTreeDecorator;
import net.minecraft.world.gen.treedecorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunkplacer.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.MegaJungleTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraft.world.server.ServerWorld;

public class Features {
    public static final ConfiguredFeature<?, ?> END_SPIKE = Features.register("end_spike", Feature.END_SPIKE.withConfiguration(new EndSpikeFeatureConfig(false, ImmutableList.of(), (BlockPos)null)));
    public static final ConfiguredFeature<?, ?> END_GATEWAY = Features.register("end_gateway", Feature.END_GATEWAY.withConfiguration(EndGatewayConfig.func_214702_a(ServerWorld.field_241108_a_, true)).withPlacement((ConfiguredPlacement)Placement.END_GATEWAY.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    public static final ConfiguredFeature<?, ?> END_GATEWAY_DELAYED = Features.register("end_gateway_delayed", Feature.END_GATEWAY.withConfiguration(EndGatewayConfig.func_214698_a()));
    public static final ConfiguredFeature<?, ?> CHORUS_PLANT = Features.register("chorus_plant", (ConfiguredFeature)Feature.CHORUS_PLANT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242732_c(4));
    public static final ConfiguredFeature<?, ?> END_ISLAND = Features.register("end_island", Feature.END_ISLAND.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    public static final ConfiguredFeature<?, ?> END_ISLAND_DECORATED = Features.register("end_island_decorated", END_ISLAND.withPlacement((ConfiguredPlacement)Placement.END_ISLAND.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    public static final ConfiguredFeature<?, ?> DELTA = Features.register("delta", Feature.DELTA_FEATURE.withConfiguration(new BasaltDeltasFeature(States.LAVA_BLOCK, States.MAGMA_BLOCK, FeatureSpread.func_242253_a(3, 4), FeatureSpread.func_242253_a(0, 2))).withPlacement((ConfiguredPlacement)Placement.field_242897_C.configure(new FeatureSpreadConfig(40))));
    public static final ConfiguredFeature<?, ?> SMALL_BASALT_COLUMNS = Features.register("small_basalt_columns", Feature.BASALT_COLUMNS.withConfiguration(new ColumnConfig(FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(1, 3))).withPlacement((ConfiguredPlacement)Placement.field_242897_C.configure(new FeatureSpreadConfig(4))));
    public static final ConfiguredFeature<?, ?> LARGE_BASALT_COLUMNS = Features.register("large_basalt_columns", Feature.BASALT_COLUMNS.withConfiguration(new ColumnConfig(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(5, 5))).withPlacement((ConfiguredPlacement)Placement.field_242897_C.configure(new FeatureSpreadConfig(2))));
    public static final ConfiguredFeature<?, ?> BASALT_BLOBS = Features.register("basalt_blobs", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(States.NETHERRACK, States.BASALT, FeatureSpread.func_242253_a(3, 4))).func_242733_d(128)).func_242728_a()).func_242731_b(75));
    public static final ConfiguredFeature<?, ?> BLACKSTONE_BLOBS = Features.register("blackstone_blobs", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(States.NETHERRACK, States.BLACKSTONE, FeatureSpread.func_242253_a(3, 4))).func_242733_d(128)).func_242728_a()).func_242731_b(25));
    public static final ConfiguredFeature<?, ?> GLOWSTONE_EXTRA = Features.register("glowstone_extra", Feature.GLOWSTONE_BLOB.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement((ConfiguredPlacement)Placement.field_242912_w.configure(new FeatureSpreadConfig(10))));
    public static final ConfiguredFeature<?, ?> GLOWSTONE = Features.register("glowstone", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.GLOWSTONE_BLOB.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242733_d(128)).func_242728_a()).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> CRIMSON_FOREST_VEGETATION = Features.register("crimson_forest_vegetation", Feature.NETHER_FOREST_VEGETATION.withConfiguration(Configs.CRIMSON_FOREST_VEGETATION_CONFIG).withPlacement((ConfiguredPlacement)Placement.field_242897_C.configure(new FeatureSpreadConfig(6))));
    public static final ConfiguredFeature<?, ?> WARPED_FOREST_VEGETATION = Features.register("warped_forest_vegetation", Feature.NETHER_FOREST_VEGETATION.withConfiguration(Configs.WARPED_FOREST_VEGETATION_CONFIG).withPlacement((ConfiguredPlacement)Placement.field_242897_C.configure(new FeatureSpreadConfig(5))));
    public static final ConfiguredFeature<?, ?> NETHER_SPROUTS = Features.register("nether_sprouts", Feature.NETHER_FOREST_VEGETATION.withConfiguration(Configs.NETHER_SPROUTS_CONFIG).withPlacement((ConfiguredPlacement)Placement.field_242897_C.configure(new FeatureSpreadConfig(4))));
    public static final ConfiguredFeature<?, ?> TWISTING_VINES = Features.register("twisting_vines", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.TWISTING_VINES.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242733_d(128)).func_242728_a()).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> WEEPING_VINES = Features.register("weeping_vines", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.WEEPING_VINES.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242733_d(128)).func_242728_a()).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> BASALT_PILLAR = Features.register("basalt_pillar", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.BASALT_PILLAR.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242733_d(128)).func_242728_a()).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> SEAGRASS_COLD = Features.register("seagrass_cold", ((ConfiguredFeature)Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.3f)).func_242731_b(32)).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature<?, ?> SEAGRASS_DEEP_COLD = Features.register("seagrass_deep_cold", ((ConfiguredFeature)Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.8f)).func_242731_b(40)).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature<?, ?> SEAGRASS_NORMAL = Features.register("seagrass_normal", ((ConfiguredFeature)Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.3f)).func_242731_b(48)).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature<?, ?> SEAGRASS_RIVER = Features.register("seagrass_river", ((ConfiguredFeature)Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.4f)).func_242731_b(48)).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature<?, ?> SEAGRASS_DEEP = Features.register("seagrass_deep", ((ConfiguredFeature)Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.8f)).func_242731_b(48)).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature<?, ?> SEAGRASS_SWAMP = Features.register("seagrass_swamp", ((ConfiguredFeature)Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.6f)).func_242731_b(64)).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature<?, ?> SEAGRASS_WARM = Features.register("seagrass_warm", ((ConfiguredFeature)Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.3f)).func_242731_b(80)).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature<?, ?> SEAGRASS_DEEP_WARM = Features.register("seagrass_deep_warm", ((ConfiguredFeature)Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.8f)).func_242731_b(80)).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature<?, ?> SEA_PICKLE = Features.register("sea_pickle", (ConfiguredFeature)Feature.SEA_PICKLE.withConfiguration(new FeatureSpreadConfig(20)).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT).func_242729_a(16));
    public static final ConfiguredFeature<?, ?> ICE_SPIKE = Features.register("ice_spike", (ConfiguredFeature)Feature.ICE_SPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242731_b(3));
    public static final ConfiguredFeature<?, ?> ICE_PATCH = Features.register("ice_patch", (ConfiguredFeature)Feature.ICE_PATCH.withConfiguration(new SphereReplaceConfig(States.PACKED_ICE, FeatureSpread.func_242253_a(2, 1), 1, ImmutableList.of(States.DIRT, States.GRASS_BLOCK, States.PODZOL, States.COARSE_DIRT, States.MYCELIUM, States.SNOW_BLOCK, States.ICE))).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));
    public static final ConfiguredFeature<?, ?> FOREST_ROCK = Features.register("forest_rock", (ConfiguredFeature)Feature.FOREST_ROCK.withConfiguration(new BlockStateFeatureConfig(States.MOSSY_COBBLESTONE)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242732_c(2));
    public static final ConfiguredFeature<?, ?> SEAGRASS_SIMPLE = Features.register("seagrass_simple", Feature.SIMPLE_BLOCK.withConfiguration(new BlockWithContextConfig(States.SEAGRASS, ImmutableList.of(States.STONE), ImmutableList.of(States.WATER_BLOCK), ImmutableList.of(States.WATER_BLOCK))).withPlacement((ConfiguredPlacement)Placement.CARVING_MASK.configure(new CaveEdgeConfig(GenerationStage.Carving.LIQUID, 0.1f))));
    public static final ConfiguredFeature<?, ?> ICEBERG_PACKED = Features.register("iceberg_packed", (ConfiguredFeature)Feature.ICEBERG.withConfiguration(new BlockStateFeatureConfig(States.PACKED_ICE)).withPlacement((ConfiguredPlacement)Placement.ICEBERG.configure(NoPlacementConfig.field_236556_b_)).func_242729_a(16));
    public static final ConfiguredFeature<?, ?> ICEBERG_BLUE = Features.register("iceberg_blue", (ConfiguredFeature)Feature.ICEBERG.withConfiguration(new BlockStateFeatureConfig(States.BLUE_ICE)).withPlacement((ConfiguredPlacement)Placement.ICEBERG.configure(NoPlacementConfig.field_236556_b_)).func_242729_a(200));
    public static final ConfiguredFeature<?, ?> KELP_COLD = Features.register("kelp_cold", ((ConfiguredFeature)Feature.KELP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement((ConfiguredPlacement)Placements.KELP_PLACEMENT).func_242728_a()).withPlacement((ConfiguredPlacement)Placement.field_242901_e.configure(new TopSolidWithNoiseConfig(120, 80.0, 0.0))));
    public static final ConfiguredFeature<?, ?> KELP_WARM = Features.register("kelp_warm", ((ConfiguredFeature)Feature.KELP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement((ConfiguredPlacement)Placements.KELP_PLACEMENT).func_242728_a()).withPlacement((ConfiguredPlacement)Placement.field_242901_e.configure(new TopSolidWithNoiseConfig(80, 80.0, 0.0))));
    public static final ConfiguredFeature<?, ?> BLUE_ICE = Features.register("blue_ice", (ConfiguredFeature)((ConfiguredFeature)Feature.BLUE_ICE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement((ConfiguredPlacement)Placement.field_242907_l.configure(new TopSolidRangeConfig(30, 32, 64))).func_242728_a()).func_242732_c(19));
    public static final ConfiguredFeature<?, ?> BAMBOO_LIGHT = Features.register("bamboo_light", (ConfiguredFeature)Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0f)).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(16));
    public static final ConfiguredFeature<?, ?> BAMBOO = Features.register("bamboo", ((ConfiguredFeature)Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.2f)).withPlacement((ConfiguredPlacement)Placements.BAMBOO_PLACEMENT).func_242728_a()).withPlacement((ConfiguredPlacement)Placement.field_242901_e.configure(new TopSolidWithNoiseConfig(160, 80.0, 0.3))));
    public static final ConfiguredFeature<?, ?> VINES = Features.register("vines", (ConfiguredFeature)((ConfiguredFeature)Feature.VINES.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242728_a()).func_242731_b(50));
    public static final ConfiguredFeature<?, ?> LAKE_WATER = Features.register("lake_water", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(States.WATER_BLOCK)).withPlacement((ConfiguredPlacement)Placement.WATER_LAKE.configure(new ChanceConfig(4))));
    public static final ConfiguredFeature<?, ?> LAKE_LAVA = Features.register("lake_lava", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(States.LAVA_BLOCK)).withPlacement((ConfiguredPlacement)Placement.LAVA_LAKE.configure(new ChanceConfig(80))));
    public static final ConfiguredFeature<?, ?> DISK_CLAY = Features.register("disk_clay", Feature.DISK.withConfiguration(new SphereReplaceConfig(States.CLAY, FeatureSpread.func_242253_a(2, 1), 1, ImmutableList.of(States.DIRT, States.CLAY))).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature<?, ?> DISK_GRAVEL = Features.register("disk_gravel", Feature.DISK.withConfiguration(new SphereReplaceConfig(States.GRAVEL, FeatureSpread.func_242253_a(2, 3), 2, ImmutableList.of(States.DIRT, States.GRASS_BLOCK))).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature<?, ?> DISK_SAND = Features.register("disk_sand", (ConfiguredFeature)Feature.DISK.withConfiguration(new SphereReplaceConfig(States.SAND, FeatureSpread.func_242253_a(2, 4), 2, ImmutableList.of(States.DIRT, States.GRASS_BLOCK))).withPlacement((ConfiguredPlacement)Placements.SEAGRASS_DISK_PLACEMENT).func_242731_b(3));
    public static final ConfiguredFeature<?, ?> FREEZE_TOP_LAYER = Features.register("freeze_top_layer", Feature.FREEZE_TOP_LAYER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    public static final ConfiguredFeature<?, ?> BONUS_CHEST = Features.register("bonus_chest", Feature.BONUS_CHEST.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    public static final ConfiguredFeature<?, ?> VOID_START_PLATFORM = Features.register("void_start_platform", Feature.VOID_START_PLATFORM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    public static final ConfiguredFeature<?, ?> MONSTER_ROOM = Features.register("monster_room", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.MONSTER_ROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242733_d(256)).func_242728_a()).func_242731_b(8));
    public static final ConfiguredFeature<?, ?> DESERT_WELL = Features.register("desert_well", (ConfiguredFeature)Feature.DESERT_WELL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242729_a(1000));
    public static final ConfiguredFeature<?, ?> FOSSIL = Features.register("fossil", (ConfiguredFeature)Feature.FOSSIL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242729_a(64));
    public static final ConfiguredFeature<?, ?> SPRING_LAVA_DOUBLE = Features.register("spring_lava_double", (ConfiguredFeature)((ConfiguredFeature)Feature.SPRING_FEATURE.withConfiguration(Configs.LAVA_SPRING_CONFIG).withPlacement((ConfiguredPlacement)Placement.field_242909_n.configure(new TopSolidRangeConfig(8, 16, 256))).func_242728_a()).func_242731_b(40));
    public static final ConfiguredFeature<?, ?> SPRING_LAVA = Features.register("spring_lava", (ConfiguredFeature)((ConfiguredFeature)Feature.SPRING_FEATURE.withConfiguration(Configs.LAVA_SPRING_CONFIG).withPlacement((ConfiguredPlacement)Placement.field_242909_n.configure(new TopSolidRangeConfig(8, 16, 256))).func_242728_a()).func_242731_b(20));
    public static final ConfiguredFeature<?, ?> SPRING_DELTA = Features.register("spring_delta", (ConfiguredFeature)((ConfiguredFeature)Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(States.LAVA, true, 4, 1, ImmutableSet.of(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.GRAVEL, Blocks.MAGMA_BLOCK, Blocks.BLACKSTONE))).withPlacement((ConfiguredPlacement)Placements.SPRING_PLACEMENT).func_242728_a()).func_242731_b(16));
    public static final ConfiguredFeature<?, ?> SPRING_CLOSED = Features.register("spring_closed", (ConfiguredFeature)((ConfiguredFeature)Feature.SPRING_FEATURE.withConfiguration(Configs.CLOSED_SPRING_CONFIG).withPlacement((ConfiguredPlacement)Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a()).func_242731_b(16));
    public static final ConfiguredFeature<?, ?> SPRING_CLOSED_DOUBLE = Features.register("spring_closed_double", (ConfiguredFeature)((ConfiguredFeature)Feature.SPRING_FEATURE.withConfiguration(Configs.CLOSED_SPRING_CONFIG).withPlacement((ConfiguredPlacement)Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a()).func_242731_b(32));
    public static final ConfiguredFeature<?, ?> SPRING_OPEN = Features.register("spring_open", (ConfiguredFeature)((ConfiguredFeature)Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(States.LAVA, false, 4, 1, ImmutableSet.of(Blocks.NETHERRACK))).withPlacement((ConfiguredPlacement)Placements.SPRING_PLACEMENT).func_242728_a()).func_242731_b(8));
    public static final ConfiguredFeature<?, ?> SPRING_WATER = Features.register("spring_water", (ConfiguredFeature)((ConfiguredFeature)Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(States.WATER, true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).withPlacement((ConfiguredPlacement)Placement.field_242908_m.configure(new TopSolidRangeConfig(8, 8, 256))).func_242728_a()).func_242731_b(50));
    public static final ConfiguredFeature<?, ?> PILE_HAY = Features.register("pile_hay", Feature.BLOCK_PILE.withConfiguration(new BlockStateProvidingFeatureConfig(new AxisRotatingBlockStateProvider(Blocks.HAY_BLOCK))));
    public static final ConfiguredFeature<?, ?> PILE_MELON = Features.register("pile_melon", Feature.BLOCK_PILE.withConfiguration(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(States.MELON))));
    public static final ConfiguredFeature<?, ?> PILE_SNOW = Features.register("pile_snow", Feature.BLOCK_PILE.withConfiguration(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(States.SNOW))));
    public static final ConfiguredFeature<?, ?> PILE_ICE = Features.register("pile_ice", Feature.BLOCK_PILE.withConfiguration(new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.BLUE_ICE, 1).addWeightedBlockstate(States.PACKED_ICE, 5))));
    public static final ConfiguredFeature<?, ?> PILE_PUMPKIN = Features.register("pile_pumpkin", Feature.BLOCK_PILE.withConfiguration(new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.PUMPKIN, 19).addWeightedBlockstate(States.JACK_O_LANTERN, 1))));
    public static final ConfiguredFeature<?, ?> PATCH_FIRE = Features.register("patch_fire", Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.FIRE), SimpleBlockPlacer.PLACER).tries(64).whitelist(ImmutableSet.of(States.NETHERRACK.getBlock())).func_227317_b_().build()).withPlacement((ConfiguredPlacement)Placements.FIRE_PLACEMENT));
    public static final ConfiguredFeature<?, ?> PATCH_SOUL_FIRE = Features.register("patch_soul_fire", Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.SOUL_FIRE), new SimpleBlockPlacer()).tries(64).whitelist(ImmutableSet.of(States.SOUL_SOIL.getBlock())).func_227317_b_().build()).withPlacement((ConfiguredPlacement)Placements.FIRE_PLACEMENT));
    public static final ConfiguredFeature<?, ?> PATCH_BROWN_MUSHROOM = Features.register("patch_brown_mushroom", Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.BROWN_MUSHROOM), SimpleBlockPlacer.PLACER).tries(64).func_227317_b_().build()));
    public static final ConfiguredFeature<?, ?> PATCH_RED_MUSHROOM = Features.register("patch_red_mushroom", Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.RED_MUSHROOM), SimpleBlockPlacer.PLACER).tries(64).func_227317_b_().build()));
    public static final ConfiguredFeature<?, ?> PATCH_CRIMSON_ROOTS = Features.register("patch_crimson_roots", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.CRIMSON_ROOTS), new SimpleBlockPlacer()).tries(64).func_227317_b_().build()).func_242733_d(128));
    public static final ConfiguredFeature<?, ?> PATCH_SUNFLOWER = Features.register("patch_sunflower", (ConfiguredFeature)((ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.SUNFLOWER), new DoublePlantBlockPlacer()).tries(64).func_227317_b_().build()).withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> PATCH_PUMPKIN = Features.register("patch_pumpkin", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.PUMPKIN), SimpleBlockPlacer.PLACER).tries(64).whitelist(ImmutableSet.of(States.GRASS_BLOCK.getBlock())).func_227317_b_().build()).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242729_a(32));
    public static final ConfiguredFeature<?, ?> PATCH_TAIGA_GRASS = Features.register("patch_taiga_grass", Feature.RANDOM_PATCH.withConfiguration(Configs.TAIGA_GRASS_CONFIG));
    public static final ConfiguredFeature<?, ?> PATCH_BERRY_BUSH = Features.register("patch_berry_bush", Feature.RANDOM_PATCH.withConfiguration(Configs.BERRY_BUSH_PATCH_CONFIG));
    public static final ConfiguredFeature<?, ?> PATCH_GRASS_PLAIN = Features.register("patch_grass_plain", ((ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.GRASS_PATCH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242900_d.configure(new NoiseDependant(-0.8, 5, 10))));
    public static final ConfiguredFeature<?, ?> PATCH_GRASS_FOREST = Features.register("patch_grass_forest", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.GRASS_PATCH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(2));
    public static final ConfiguredFeature<?, ?> PATCH_GRASS_BADLANDS = Features.register("patch_grass_badlands", Feature.RANDOM_PATCH.withConfiguration(Configs.GRASS_PATCH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature<?, ?> PATCH_GRASS_SAVANNA = Features.register("patch_grass_savanna", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.GRASS_PATCH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(20));
    public static final ConfiguredFeature<?, ?> PATCH_GRASS_NORMAL = Features.register("patch_grass_normal", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.GRASS_PATCH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(5));
    public static final ConfiguredFeature<?, ?> PATCH_GRASS_TAIGA_2 = Features.register("patch_grass_taiga_2", Feature.RANDOM_PATCH.withConfiguration(Configs.TAIGA_GRASS_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature<?, ?> PATCH_GRASS_TAIGA = Features.register("patch_grass_taiga", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.TAIGA_GRASS_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(7));
    public static final ConfiguredFeature<?, ?> PATCH_GRASS_JUNGLE = Features.register("patch_grass_jungle", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.JUNGLE_VEGETATION_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(25));
    public static final ConfiguredFeature<?, ?> PATCH_DEAD_BUSH_2 = Features.register("patch_dead_bush_2", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.DEAD_BUSH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(2));
    public static final ConfiguredFeature<?, ?> PATCH_DEAD_BUSH = Features.register("patch_dead_bush", Feature.RANDOM_PATCH.withConfiguration(Configs.DEAD_BUSH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature<?, ?> PATCH_DEAD_BUSH_BADLANDS = Features.register("patch_dead_bush_badlands", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.DEAD_BUSH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(20));
    public static final ConfiguredFeature<?, ?> PATCH_MELON = Features.register("patch_melon", Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.MELON), SimpleBlockPlacer.PLACER).tries(64).whitelist(ImmutableSet.of(States.GRASS_BLOCK.getBlock())).replaceable().func_227317_b_().build()).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature<?, ?> PATCH_BERRY_SPARSE = Features.register("patch_berry_sparse", PATCH_BERRY_BUSH.withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature<?, ?> PATCH_BERRY_DECORATED = Features.register("patch_berry_decorated", (ConfiguredFeature)PATCH_BERRY_BUSH.withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242729_a(12));
    public static final ConfiguredFeature<?, ?> PATCH_WATERLILLY = Features.register("patch_waterlilly", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.LILY_PAD), SimpleBlockPlacer.PLACER).tries(10).build()).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(4));
    public static final ConfiguredFeature<?, ?> PATCH_TALL_GRASS_2 = Features.register("patch_tall_grass_2", ((ConfiguredFeature)((ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.TALL_GRASS_CONFIG).withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.FLOWER_TALL_GRASS_PLACEMENT).func_242728_a()).withPlacement((ConfiguredPlacement)Placement.field_242900_d.configure(new NoiseDependant(-0.8, 0, 7))));
    public static final ConfiguredFeature<?, ?> PATCH_TALL_GRASS = Features.register("patch_tall_grass", (ConfiguredFeature)((ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.TALL_GRASS_CONFIG).withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242731_b(7));
    public static final ConfiguredFeature<?, ?> PATCH_LARGE_FERN = Features.register("patch_large_fern", (ConfiguredFeature)((ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.LARGE_FERN), new DoublePlantBlockPlacer()).tries(64).func_227317_b_().build()).withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242731_b(7));
    public static final ConfiguredFeature<?, ?> PATCH_CACTUS = Features.register("patch_cactus", Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.CACTUS), new ColumnBlockPlacer(1, 2)).tries(10).func_227317_b_().build()));
    public static final ConfiguredFeature<?, ?> PATCH_CACTUS_DESERT = Features.register("patch_cactus_desert", (ConfiguredFeature)PATCH_CACTUS.withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> PATCH_CACTUS_DECORATED = Features.register("patch_cactus_decorated", (ConfiguredFeature)PATCH_CACTUS.withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(5));
    public static final ConfiguredFeature<?, ?> PATCH_SUGAR_CANE_SWAMP = Features.register("patch_sugar_cane_swamp", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.SUGAR_CANE_PATCH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(20));
    public static final ConfiguredFeature<?, ?> PATCH_SUGAR_CANE_DESERT = Features.register("patch_sugar_cane_desert", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.SUGAR_CANE_PATCH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(60));
    public static final ConfiguredFeature<?, ?> PATCH_SUGAR_CANE_BADLANDS = Features.register("patch_sugar_cane_badlands", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.SUGAR_CANE_PATCH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(13));
    public static final ConfiguredFeature<?, ?> PATCH_SUGAR_CANE = Features.register("patch_sugar_cane", (ConfiguredFeature)Feature.RANDOM_PATCH.withConfiguration(Configs.SUGAR_CANE_PATCH_CONFIG).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> BROWN_MUSHROOM_NETHER = Features.register("brown_mushroom_nether", (ConfiguredFeature)((ConfiguredFeature)PATCH_BROWN_MUSHROOM.func_242733_d(128)).func_242729_a(2));
    public static final ConfiguredFeature<?, ?> RED_MUSHROOM_NETHER = Features.register("red_mushroom_nether", (ConfiguredFeature)((ConfiguredFeature)PATCH_RED_MUSHROOM.func_242733_d(128)).func_242729_a(2));
    public static final ConfiguredFeature<?, ?> BROWN_MUSHROOM_NORMAL = Features.register("brown_mushroom_normal", (ConfiguredFeature)PATCH_BROWN_MUSHROOM.withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242729_a(4));
    public static final ConfiguredFeature<?, ?> RED_MUSHROOM_NORMAL = Features.register("red_mushroom_normal", (ConfiguredFeature)PATCH_RED_MUSHROOM.withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT).func_242729_a(8));
    public static final ConfiguredFeature<?, ?> BROWN_MUSHROOM_TAIGA = Features.register("brown_mushroom_taiga", ((ConfiguredFeature)PATCH_BROWN_MUSHROOM.func_242729_a(4)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT));
    public static final ConfiguredFeature<?, ?> RED_MUSHROOM_TAIGA = Features.register("red_mushroom_taiga", ((ConfiguredFeature)PATCH_RED_MUSHROOM.func_242729_a(8)).withPlacement((ConfiguredPlacement)Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature<?, ?> BROWN_MUSHROOM_GIANT = Features.register("brown_mushroom_giant", (ConfiguredFeature)BROWN_MUSHROOM_TAIGA.func_242731_b(3));
    public static final ConfiguredFeature<?, ?> RED_MUSHROOM_GIANT = Features.register("red_mushroom_giant", (ConfiguredFeature)RED_MUSHROOM_TAIGA.func_242731_b(3));
    public static final ConfiguredFeature<?, ?> BROWN_MUSHROOM_SWAMP = Features.register("brown_mushroom_swamp", (ConfiguredFeature)BROWN_MUSHROOM_TAIGA.func_242731_b(8));
    public static final ConfiguredFeature<?, ?> RED_MUSHROOM_SWAMP = Features.register("red_mushroom_swamp", (ConfiguredFeature)RED_MUSHROOM_TAIGA.func_242731_b(8));
    public static final ConfiguredFeature<?, ?> ORE_MAGMA = Features.register("ore_magma", (ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, States.MAGMA_BLOCK, 33)).withPlacement((ConfiguredPlacement)Placement.MAGMA.configure(NoPlacementConfig.field_236556_b_)).func_242728_a()).func_242731_b(4));
    public static final ConfiguredFeature<?, ?> ORE_SOUL_SAND = Features.register("ore_soul_sand", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, States.SOUL_SAND, 12)).func_242733_d(32)).func_242728_a()).func_242731_b(12));
    public static final ConfiguredFeature<?, ?> ORE_GOLD_DELTAS = Features.register("ore_gold_deltas", (ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, States.NETHER_GOLD_ORE, 10)).withPlacement((ConfiguredPlacement)Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a()).func_242731_b(20));
    public static final ConfiguredFeature<?, ?> ORE_QUARTZ_DELTAS = Features.register("ore_quartz_deltas", (ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, States.NETHER_QUARTZ_ORE, 14)).withPlacement((ConfiguredPlacement)Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a()).func_242731_b(32));
    public static final ConfiguredFeature<?, ?> ORE_GOLD_NETHER = Features.register("ore_gold_nether", (ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, States.NETHER_GOLD_ORE, 10)).withPlacement((ConfiguredPlacement)Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a()).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> ORE_QUARTZ_NETHER = Features.register("ore_quartz_nether", (ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, States.NETHER_QUARTZ_ORE, 14)).withPlacement((ConfiguredPlacement)Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a()).func_242731_b(16));
    public static final ConfiguredFeature<?, ?> ORE_GRAVEL_NETHER = Features.register("ore_gravel_nether", (ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, States.GRAVEL, 33)).withPlacement((ConfiguredPlacement)Placement.field_242907_l.configure(new TopSolidRangeConfig(5, 0, 37))).func_242728_a()).func_242731_b(2));
    public static final ConfiguredFeature<?, ?> ORE_BLACKSTONE = Features.register("ore_blackstone", (ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, States.BLACKSTONE, 33)).withPlacement((ConfiguredPlacement)Placement.field_242907_l.configure(new TopSolidRangeConfig(5, 10, 37))).func_242728_a()).func_242731_b(2));
    public static final ConfiguredFeature<?, ?> ORE_DIRT = Features.register("ore_dirt", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.DIRT, 33)).func_242733_d(256)).func_242728_a()).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> ORE_GRAVEL = Features.register("ore_gravel", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.GRAVEL, 33)).func_242733_d(256)).func_242728_a()).func_242731_b(8));
    public static final ConfiguredFeature<?, ?> ORE_GRANITE = Features.register("ore_granite", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.GRANITE, 33)).func_242733_d(80)).func_242728_a()).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> ORE_DIORITE = Features.register("ore_diorite", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.DIORITE, 33)).func_242733_d(80)).func_242728_a()).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> ORE_ANDESITE = Features.register("ore_andesite", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.ANDESITE, 33)).func_242733_d(80)).func_242728_a()).func_242731_b(10));
    public static final ConfiguredFeature<?, ?> ORE_COAL = Features.register("ore_coal", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.COAL_ORE, 17)).func_242733_d(128)).func_242728_a()).func_242731_b(20));
    public static final ConfiguredFeature<?, ?> ORE_IRON = Features.register("ore_iron", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.IRON_ORE, 9)).func_242733_d(64)).func_242728_a()).func_242731_b(20));
    public static final ConfiguredFeature<?, ?> ORE_GOLD_EXTRA = Features.register("ore_gold_extra", (ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.GOLD_ORE, 9)).withPlacement((ConfiguredPlacement)Placement.field_242907_l.configure(new TopSolidRangeConfig(32, 32, 80))).func_242728_a()).func_242731_b(20));
    public static final ConfiguredFeature<?, ?> ORE_GOLD = Features.register("ore_gold", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.GOLD_ORE, 9)).func_242733_d(32)).func_242728_a()).func_242731_b(2));
    public static final ConfiguredFeature<?, ?> ORE_REDSTONE = Features.register("ore_redstone", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.REDSTONE_ORE, 8)).func_242733_d(16)).func_242728_a()).func_242731_b(8));
    public static final ConfiguredFeature<?, ?> ORE_DIAMOND = Features.register("ore_diamond", (ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.DIAMOND_ORE, 8)).func_242733_d(16)).func_242728_a());
    public static final ConfiguredFeature<?, ?> ORE_LAPIS = Features.register("ore_lapis", (ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.LAPIS_ORE, 7)).withPlacement((ConfiguredPlacement)Placement.field_242910_o.configure(new DepthAverageConfig(16, 16))).func_242728_a());
    public static final ConfiguredFeature<?, ?> ORE_INFESTED = Features.register("ore_infested", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, States.INFESTED_STONE, 9)).func_242733_d(64)).func_242728_a()).func_242731_b(7));
    public static final ConfiguredFeature<?, ?> ORE_EMERALD = Features.register("ore_emerald", Feature.EMERALD_ORE.withConfiguration(new ReplaceBlockConfig(States.STONE, States.EMERALD_ORE)).withPlacement((ConfiguredPlacement)Placement.EMERALD_ORE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    public static final ConfiguredFeature<?, ?> ORE_DEBRIS_LARGE = Features.register("ore_debris_large", (ConfiguredFeature)Feature.NO_SURFACE_ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241884_c, States.ANCIENT_DEBRIS, 3)).withPlacement((ConfiguredPlacement)Placement.field_242910_o.configure(new DepthAverageConfig(16, 8))).func_242728_a());
    public static final ConfiguredFeature<?, ?> ORE_DEBRIS_SMALL = Features.register("ore_debris_small", (ConfiguredFeature)Feature.NO_SURFACE_ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241884_c, States.ANCIENT_DEBRIS, 2)).withPlacement((ConfiguredPlacement)Placement.field_242907_l.configure(new TopSolidRangeConfig(8, 16, 128))).func_242728_a());
    public static final ConfiguredFeature<?, ?> CRIMSON_FUNGI = Features.register("crimson_fungi", Feature.HUGE_FUNGUS.withConfiguration(HugeFungusConfig.field_236300_c_).withPlacement((ConfiguredPlacement)Placement.field_242897_C.configure(new FeatureSpreadConfig(8))));
    public static final ConfiguredFeature<HugeFungusConfig, ?> CRIMSON_FUNGI_PLANTED = Features.register("crimson_fungi_planted", Feature.HUGE_FUNGUS.withConfiguration(HugeFungusConfig.field_236299_b_));
    public static final ConfiguredFeature<?, ?> WARPED_FUNGI = Features.register("warped_fungi", Feature.HUGE_FUNGUS.withConfiguration(HugeFungusConfig.field_236302_e_).withPlacement((ConfiguredPlacement)Placement.field_242897_C.configure(new FeatureSpreadConfig(8))));
    public static final ConfiguredFeature<HugeFungusConfig, ?> WARPED_FUNGI_PLANTED = Features.register("warped_fungi_planted", Feature.HUGE_FUNGUS.withConfiguration(HugeFungusConfig.field_236301_d_));
    public static final ConfiguredFeature<?, ?> HUGE_BROWN_MUSHROOM = Features.register("huge_brown_mushroom", Feature.HUGE_BROWN_MUSHROOM.withConfiguration(new BigMushroomFeatureConfig(new SimpleBlockStateProvider(States.BROWN_MUSHROOM_BLOCK_UP), new SimpleBlockStateProvider(States.MUSHROOM_STEM), 3)));
    public static final ConfiguredFeature<?, ?> HUGE_RED_MUSHROOM = Features.register("huge_red_mushroom", Feature.HUGE_RED_MUSHROOM.withConfiguration(new BigMushroomFeatureConfig(new SimpleBlockStateProvider(States.RED_MUSHROOM_BLOCK_DOWN), new SimpleBlockStateProvider(States.MUSHROOM_STEM), 2)));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> OAK = Features.register("oak", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.OAK_LOG), new SimpleBlockStateProvider(States.OAK_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1)).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> DARK_OAK = Features.register("dark_oak", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.DARK_OAK_LOG), new SimpleBlockStateProvider(States.DARK_OAK_LEAVES), new DarkOakFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0)), new DarkOakTrunkPlacer(6, 2, 1), new ThreeLayerFeature(1, 1, 0, 1, 2, OptionalInt.empty())).func_236701_a_(Integer.MAX_VALUE).func_236702_a_(Heightmap.Type.MOTION_BLOCKING).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> BIRCH = Features.register("birch", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.BIRCH_LOG), new SimpleBlockStateProvider(States.BIRCH_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(1, 0, 1)).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> ACACIA = Features.register("acacia", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.ACACIA_LOG), new SimpleBlockStateProvider(States.ACACIA_LEAVES), new AcaciaFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0)), new ForkyTrunkPlacer(5, 2, 2), new TwoLayerFeature(1, 0, 2)).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SPRUCE = Features.register("spruce", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.SPRUCE_LOG), new SimpleBlockStateProvider(States.SPRUCE_LEAVES), new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)), new StraightTrunkPlacer(5, 2, 1), new TwoLayerFeature(2, 0, 2)).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PINE = Features.register("pine", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.SPRUCE_LOG), new SimpleBlockStateProvider(States.SPRUCE_LEAVES), new PineFoliagePlacer(FeatureSpread.func_242252_a(1), FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(3, 1)), new StraightTrunkPlacer(6, 4, 0), new TwoLayerFeature(2, 0, 2)).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> JUNGLE_TREE = Features.register("jungle_tree", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.JUNGLE_LOG), new SimpleBlockStateProvider(States.JUNGLE_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 8, 0), new TwoLayerFeature(1, 0, 1)).func_236703_a_(ImmutableList.of(new CocoaTreeDecorator(0.2f), TrunkVineTreeDecorator.field_236879_b_, LeaveVineTreeDecorator.field_236871_b_)).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_OAK = Features.register("fancy_oak", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.OAK_LOG), new SimpleBlockStateProvider(States.OAK_LEAVES), new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> JUNGLE_TREE_NO_VINE = Features.register("jungle_tree_no_vine", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.JUNGLE_LOG), new SimpleBlockStateProvider(States.JUNGLE_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 8, 0), new TwoLayerFeature(1, 0, 1)).setIgnoreVines().build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MEGA_JUNGLE_TREE = Features.register("mega_jungle_tree", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.JUNGLE_LOG), new SimpleBlockStateProvider(States.JUNGLE_LEAVES), new JungleFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 2), new MegaJungleTrunkPlacer(10, 2, 19), new TwoLayerFeature(1, 1, 2)).func_236703_a_(ImmutableList.of(TrunkVineTreeDecorator.field_236879_b_, LeaveVineTreeDecorator.field_236871_b_)).build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MEGA_SPRUCE = Features.register("mega_spruce", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.SPRUCE_LOG), new SimpleBlockStateProvider(States.SPRUCE_LEAVES), new MegaPineFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), FeatureSpread.func_242253_a(13, 4)), new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2)).func_236703_a_(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(States.PODZOL)))).build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MEGA_PINE = Features.register("mega_pine", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.SPRUCE_LOG), new SimpleBlockStateProvider(States.SPRUCE_LEAVES), new MegaPineFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), FeatureSpread.func_242253_a(3, 4)), new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2)).func_236703_a_(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(States.PODZOL)))).build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SUPER_BIRCH_BEES_0002 = Features.register("super_birch_bees_0002", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.BIRCH_LOG), new SimpleBlockStateProvider(States.BIRCH_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 2, 6), new TwoLayerFeature(1, 0, 1)).setIgnoreVines().func_236703_a_(ImmutableList.of(Placements.BEES_0002_PLACEMENT)).build()));
    public static final ConfiguredFeature<?, ?> SWAMP_TREE = Features.register("swamp_tree", ((ConfiguredFeature)Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.OAK_LOG), new SimpleBlockStateProvider(States.OAK_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(3), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 3, 0), new TwoLayerFeature(1, 0, 1)).func_236701_a_(1).func_236703_a_(ImmutableList.of(LeaveVineTreeDecorator.field_236871_b_)).build()).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(2, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> JUNGLE_BUSH = Features.register("jungle_bush", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.JUNGLE_LOG), new SimpleBlockStateProvider(States.OAK_LEAVES), new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0)).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> OAK_BEES_0002 = Features.register("oak_bees_0002", Feature.TREE.withConfiguration(OAK.func_242767_c().func_236685_a_(ImmutableList.of(Placements.BEES_0002_PLACEMENT))));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> OAK_BEES_002 = Features.register("oak_bees_002", Feature.TREE.withConfiguration(OAK.func_242767_c().func_236685_a_(ImmutableList.of(Placements.BEES_002_PLACEMENT))));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> OAK_BEES_005 = Features.register("oak_bees_005", Feature.TREE.withConfiguration(OAK.func_242767_c().func_236685_a_(ImmutableList.of(Placements.BEES_005_PLACEMENT))));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> BIRCH_BEES_0002 = Features.register("birch_bees_0002", Feature.TREE.withConfiguration(BIRCH.func_242767_c().func_236685_a_(ImmutableList.of(Placements.BEES_0002_PLACEMENT))));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> BIRCH_BEES_002 = Features.register("birch_bees_002", Feature.TREE.withConfiguration(BIRCH.func_242767_c().func_236685_a_(ImmutableList.of(Placements.BEES_002_PLACEMENT))));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> BIRCH_BEES_005 = Features.register("birch_bees_005", Feature.TREE.withConfiguration(BIRCH.func_242767_c().func_236685_a_(ImmutableList.of(Placements.BEES_005_PLACEMENT))));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_OAK_BEES_0002 = Features.register("fancy_oak_bees_0002", Feature.TREE.withConfiguration(FANCY_OAK.func_242767_c().func_236685_a_(ImmutableList.of(Placements.BEES_0002_PLACEMENT))));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_OAK_BEES_002 = Features.register("fancy_oak_bees_002", Feature.TREE.withConfiguration(FANCY_OAK.func_242767_c().func_236685_a_(ImmutableList.of(Placements.BEES_002_PLACEMENT))));
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> FANCY_OAK_BEES_005 = Features.register("fancy_oak_bees_005", Feature.TREE.withConfiguration(FANCY_OAK.func_242767_c().func_236685_a_(ImmutableList.of(Placements.BEES_005_PLACEMENT))));
    public static final ConfiguredFeature<?, ?> OAK_BADLANDS = Features.register("oak_badlands", ((ConfiguredFeature)OAK.withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(5, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> SPRUCE_SNOWY = Features.register("spruce_snowy", ((ConfiguredFeature)SPRUCE.withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> FLOWER_WARM = Features.register("flower_warm", (ConfiguredFeature)((ConfiguredFeature)Feature.FLOWER.withConfiguration(Configs.NORMAL_FLOWER_CONFIG).withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4));
    public static final ConfiguredFeature<?, ?> FLOWER_DEFAULT = Features.register("flower_default", (ConfiguredFeature)((ConfiguredFeature)Feature.FLOWER.withConfiguration(Configs.NORMAL_FLOWER_CONFIG).withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));
    public static final ConfiguredFeature<?, ?> FLOWER_FOREST = Features.register("flower_forest", (ConfiguredFeature)((ConfiguredFeature)Feature.FLOWER.withConfiguration(new BlockClusterFeatureConfig.Builder(ForestFlowerBlockStateProvider.PROVIDER, SimpleBlockPlacer.PLACER).tries(64).build()).withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242731_b(100));
    public static final ConfiguredFeature<?, ?> FLOWER_SWAMP = Features.register("flower_swamp", ((ConfiguredFeature)Feature.FLOWER.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.BLUE_ORCHID), SimpleBlockPlacer.PLACER).tries(64).build()).withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT));
    public static final ConfiguredFeature<?, ?> FLOWER_PLAIN = Features.register("flower_plain", Feature.FLOWER.withConfiguration(new BlockClusterFeatureConfig.Builder(PlainFlowerBlockStateProvider.PROVIDER, SimpleBlockPlacer.PLACER).tries(64).build()));
    public static final ConfiguredFeature<?, ?> FLOWER_PLAIN_DECORATED = Features.register("flower_plain_decorated", ((ConfiguredFeature)((ConfiguredFeature)FLOWER_PLAIN.withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.FLOWER_TALL_GRASS_PLACEMENT).func_242728_a()).withPlacement((ConfiguredPlacement)Placement.field_242900_d.configure(new NoiseDependant(-0.8, 15, 4))));
    private static final ImmutableList<Supplier<ConfiguredFeature<?, ?>>> FOREST_FLOWER_VEGETATION_LIST = ImmutableList.of(Features::lambda$static$0, Features::lambda$static$1, Features::lambda$static$2, Features::lambda$static$3);
    public static final ConfiguredFeature<?, ?> FOREST_FLOWER_VEGETATION_COMMON = Features.register("forest_flower_vegetation_common", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(FOREST_FLOWER_VEGETATION_LIST)).func_242730_a(FeatureSpread.func_242253_a(-1, 4))).withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5));
    public static final ConfiguredFeature<?, ?> FOREST_FLOWER_VEGETATION = Features.register("forest_flower_vegetation", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(FOREST_FLOWER_VEGETATION_LIST)).func_242730_a(FeatureSpread.func_242253_a(-3, 4))).withPlacement((ConfiguredPlacement)Placements.VEGETATION_PLACEMENT)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5));
    public static final ConfiguredFeature<?, ?> DARK_FOREST_VEGETATION_BROWN = Features.register("dark_forest_vegetation_brown", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(HUGE_BROWN_MUSHROOM.withChance(0.025f), HUGE_RED_MUSHROOM.withChance(0.05f), DARK_OAK.withChance(0.6666667f), BIRCH.withChance(0.2f), FANCY_OAK.withChance(0.1f)), OAK)).withPlacement((ConfiguredPlacement)Placement.DARK_OAK_TREE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    public static final ConfiguredFeature<?, ?> DARK_FOREST_VEGETATION_RED = Features.register("dark_forest_vegetation_red", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(HUGE_RED_MUSHROOM.withChance(0.025f), HUGE_BROWN_MUSHROOM.withChance(0.05f), DARK_OAK.withChance(0.6666667f), BIRCH.withChance(0.2f), FANCY_OAK.withChance(0.1f)), OAK)).withPlacement((ConfiguredPlacement)Placement.DARK_OAK_TREE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    public static final ConfiguredFeature<?, ?> WARM_OCEAN_VEGETATION = Features.register("warm_ocean_vegetation", ((ConfiguredFeature)Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(ImmutableList.of(Features::lambda$static$4, Features::lambda$static$5, Features::lambda$static$6))).withPlacement((ConfiguredPlacement)Placements.KELP_PLACEMENT).func_242728_a()).withPlacement((ConfiguredPlacement)Placement.field_242901_e.configure(new TopSolidWithNoiseConfig(20, 400.0, 0.0))));
    public static final ConfiguredFeature<?, ?> FOREST_FLOWER_TREES = Features.register("forest_flower_trees", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(BIRCH_BEES_002.withChance(0.2f), FANCY_OAK_BEES_002.withChance(0.1f)), OAK_BEES_002)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(6, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> TAIGA_VEGETATION = Features.register("taiga_vegetation", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(PINE.withChance(0.33333334f)), SPRUCE)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> TREES_SHATTERED_SAVANNA = Features.register("trees_shattered_savanna", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(ACACIA.withChance(0.8f)), OAK)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(2, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> TREES_SAVANNA = Features.register("trees_savanna", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(ACACIA.withChance(0.8f)), OAK)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(1, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> BIRCH_TALL = Features.register("birch_tall", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(SUPER_BIRCH_BEES_0002.withChance(0.5f)), BIRCH_BEES_0002)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> TREES_BIRCH = Features.register("trees_birch", ((ConfiguredFeature)BIRCH_BEES_0002.withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> TREES_MOUNTAIN_EDGE = Features.register("trees_mountain_edge", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(SPRUCE.withChance(0.666f), FANCY_OAK.withChance(0.1f)), OAK)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(3, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> TREES_MOUNTAIN = Features.register("trees_mountain", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(SPRUCE.withChance(0.666f), FANCY_OAK.withChance(0.1f)), OAK)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> TREES_WATER = Features.register("trees_water", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FANCY_OAK.withChance(0.1f)), OAK)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> BIRCH_OTHER = Features.register("birch_other", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(BIRCH_BEES_0002.withChance(0.2f), FANCY_OAK_BEES_0002.withChance(0.1f)), OAK_BEES_0002)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> PLAIN_VEGETATION = Features.register("plain_vegetation", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FANCY_OAK_BEES_005.withChance(0.33333334f)), OAK_BEES_005)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.05f, 1))));
    public static final ConfiguredFeature<?, ?> TREES_JUNGLE_EDGE = Features.register("trees_jungle_edge", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FANCY_OAK.withChance(0.1f), JUNGLE_BUSH.withChance(0.5f)), JUNGLE_TREE)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(2, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> TREES_GIANT_SPRUCE = Features.register("trees_giant_spruce", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(MEGA_SPRUCE.withChance(0.33333334f), PINE.withChance(0.33333334f)), SPRUCE)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> TREES_GIANT = Features.register("trees_giant", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(MEGA_SPRUCE.withChance(0.025641026f), MEGA_PINE.withChance(0.30769232f), PINE.withChance(0.33333334f)), SPRUCE)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> TREES_JUNGLE = Features.register("trees_jungle", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FANCY_OAK.withChance(0.1f), JUNGLE_BUSH.withChance(0.5f), MEGA_JUNGLE_TREE.withChance(0.33333334f)), JUNGLE_TREE)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(50, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> BAMBOO_VEGETATION = Features.register("bamboo_vegetation", ((ConfiguredFeature)Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FANCY_OAK.withChance(0.05f), JUNGLE_BUSH.withChance(0.15f), MEGA_JUNGLE_TREE.withChance(0.7f)), Feature.RANDOM_PATCH.withConfiguration(Configs.JUNGLE_VEGETATION_CONFIG))).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT)).withPlacement((ConfiguredPlacement)Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(30, 0.1f, 1))));
    public static final ConfiguredFeature<?, ?> MUSHROOM_FIELD_VEGETATION = Features.register("mushroom_field_vegetation", Feature.RANDOM_BOOLEAN_SELECTOR.withConfiguration(new TwoFeatureChoiceConfig(Features::lambda$static$7, Features::lambda$static$8)).withPlacement((ConfiguredPlacement)Placements.HEIGHTMAP_PLACEMENT));

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String string, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, string, configuredFeature);
    }

    private static ConfiguredFeature lambda$static$8() {
        return HUGE_BROWN_MUSHROOM;
    }

    private static ConfiguredFeature lambda$static$7() {
        return HUGE_RED_MUSHROOM;
    }

    private static ConfiguredFeature lambda$static$6() {
        return Feature.CORAL_MUSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    }

    private static ConfiguredFeature lambda$static$5() {
        return Feature.CORAL_CLAW.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    }

    private static ConfiguredFeature lambda$static$4() {
        return Feature.CORAL_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    }

    private static ConfiguredFeature lambda$static$3() {
        return Feature.NO_BONEMEAL_FLOWER.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.LILY_OF_THE_VALLEY), SimpleBlockPlacer.PLACER).tries(64).build());
    }

    private static ConfiguredFeature lambda$static$2() {
        return Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.PEONY), new DoublePlantBlockPlacer()).tries(64).func_227317_b_().build());
    }

    private static ConfiguredFeature lambda$static$1() {
        return Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.ROSE_BUSH), new DoublePlantBlockPlacer()).tries(64).func_227317_b_().build());
    }

    private static ConfiguredFeature lambda$static$0() {
        return Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.LILAC), new DoublePlantBlockPlacer()).tries(64).func_227317_b_().build());
    }

    public static final class States {
        protected static final BlockState GRASS = Blocks.GRASS.getDefaultState();
        protected static final BlockState FERN = Blocks.FERN.getDefaultState();
        protected static final BlockState PODZOL = Blocks.PODZOL.getDefaultState();
        protected static final BlockState COARSE_DIRT = Blocks.COARSE_DIRT.getDefaultState();
        protected static final BlockState MYCELIUM = Blocks.MYCELIUM.getDefaultState();
        protected static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
        protected static final BlockState ICE = Blocks.ICE.getDefaultState();
        protected static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
        protected static final BlockState OAK_LEAVES = Blocks.OAK_LEAVES.getDefaultState();
        protected static final BlockState JUNGLE_LOG = Blocks.JUNGLE_LOG.getDefaultState();
        protected static final BlockState JUNGLE_LEAVES = Blocks.JUNGLE_LEAVES.getDefaultState();
        protected static final BlockState SPRUCE_LOG = Blocks.SPRUCE_LOG.getDefaultState();
        protected static final BlockState SPRUCE_LEAVES = Blocks.SPRUCE_LEAVES.getDefaultState();
        protected static final BlockState ACACIA_LOG = Blocks.ACACIA_LOG.getDefaultState();
        protected static final BlockState ACACIA_LEAVES = Blocks.ACACIA_LEAVES.getDefaultState();
        protected static final BlockState BIRCH_LOG = Blocks.BIRCH_LOG.getDefaultState();
        protected static final BlockState BIRCH_LEAVES = Blocks.BIRCH_LEAVES.getDefaultState();
        protected static final BlockState DARK_OAK_LOG = Blocks.DARK_OAK_LOG.getDefaultState();
        protected static final BlockState DARK_OAK_LEAVES = Blocks.DARK_OAK_LEAVES.getDefaultState();
        protected static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.getDefaultState();
        protected static final BlockState LARGE_FERN = Blocks.LARGE_FERN.getDefaultState();
        protected static final BlockState TALL_GRASS = Blocks.TALL_GRASS.getDefaultState();
        protected static final BlockState LILAC = Blocks.LILAC.getDefaultState();
        protected static final BlockState ROSE_BUSH = Blocks.ROSE_BUSH.getDefaultState();
        protected static final BlockState PEONY = Blocks.PEONY.getDefaultState();
        protected static final BlockState BROWN_MUSHROOM = Blocks.BROWN_MUSHROOM.getDefaultState();
        protected static final BlockState RED_MUSHROOM = Blocks.RED_MUSHROOM.getDefaultState();
        protected static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
        protected static final BlockState BLUE_ICE = Blocks.BLUE_ICE.getDefaultState();
        protected static final BlockState LILY_OF_THE_VALLEY = Blocks.LILY_OF_THE_VALLEY.getDefaultState();
        protected static final BlockState BLUE_ORCHID = Blocks.BLUE_ORCHID.getDefaultState();
        protected static final BlockState POPPY = Blocks.POPPY.getDefaultState();
        protected static final BlockState DANDELION = Blocks.DANDELION.getDefaultState();
        protected static final BlockState DEAD_BUSH = Blocks.DEAD_BUSH.getDefaultState();
        protected static final BlockState MELON = Blocks.MELON.getDefaultState();
        protected static final BlockState PUMPKIN = Blocks.PUMPKIN.getDefaultState();
        protected static final BlockState SWEET_BERRY_BUSH = (BlockState)Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, 3);
        protected static final BlockState FIRE = Blocks.FIRE.getDefaultState();
        protected static final BlockState SOUL_FIRE = Blocks.SOUL_FIRE.getDefaultState();
        protected static final BlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
        protected static final BlockState SOUL_SOIL = Blocks.SOUL_SOIL.getDefaultState();
        protected static final BlockState CRIMSON_ROOTS = Blocks.CRIMSON_ROOTS.getDefaultState();
        protected static final BlockState LILY_PAD = Blocks.LILY_PAD.getDefaultState();
        protected static final BlockState SNOW = Blocks.SNOW.getDefaultState();
        protected static final BlockState JACK_O_LANTERN = Blocks.JACK_O_LANTERN.getDefaultState();
        protected static final BlockState SUNFLOWER = Blocks.SUNFLOWER.getDefaultState();
        protected static final BlockState CACTUS = Blocks.CACTUS.getDefaultState();
        protected static final BlockState SUGAR_CANE = Blocks.SUGAR_CANE.getDefaultState();
        protected static final BlockState RED_MUSHROOM_BLOCK_DOWN = (BlockState)Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.DOWN, false);
        protected static final BlockState BROWN_MUSHROOM_BLOCK_UP = (BlockState)((BlockState)Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.UP, true)).with(HugeMushroomBlock.DOWN, false);
        protected static final BlockState MUSHROOM_STEM = (BlockState)((BlockState)Blocks.MUSHROOM_STEM.getDefaultState().with(HugeMushroomBlock.UP, false)).with(HugeMushroomBlock.DOWN, false);
        protected static final FluidState WATER = Fluids.WATER.getDefaultState();
        protected static final FluidState LAVA = Fluids.LAVA.getDefaultState();
        protected static final BlockState WATER_BLOCK = Blocks.WATER.getDefaultState();
        protected static final BlockState LAVA_BLOCK = Blocks.LAVA.getDefaultState();
        protected static final BlockState DIRT = Blocks.DIRT.getDefaultState();
        protected static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
        protected static final BlockState GRANITE = Blocks.GRANITE.getDefaultState();
        protected static final BlockState DIORITE = Blocks.DIORITE.getDefaultState();
        protected static final BlockState ANDESITE = Blocks.ANDESITE.getDefaultState();
        protected static final BlockState COAL_ORE = Blocks.COAL_ORE.getDefaultState();
        protected static final BlockState IRON_ORE = Blocks.IRON_ORE.getDefaultState();
        protected static final BlockState GOLD_ORE = Blocks.GOLD_ORE.getDefaultState();
        protected static final BlockState REDSTONE_ORE = Blocks.REDSTONE_ORE.getDefaultState();
        protected static final BlockState DIAMOND_ORE = Blocks.DIAMOND_ORE.getDefaultState();
        protected static final BlockState LAPIS_ORE = Blocks.LAPIS_ORE.getDefaultState();
        protected static final BlockState STONE = Blocks.STONE.getDefaultState();
        protected static final BlockState EMERALD_ORE = Blocks.EMERALD_ORE.getDefaultState();
        protected static final BlockState INFESTED_STONE = Blocks.INFESTED_STONE.getDefaultState();
        protected static final BlockState SAND = Blocks.SAND.getDefaultState();
        protected static final BlockState CLAY = Blocks.CLAY.getDefaultState();
        protected static final BlockState MOSSY_COBBLESTONE = Blocks.MOSSY_COBBLESTONE.getDefaultState();
        protected static final BlockState SEAGRASS = Blocks.SEAGRASS.getDefaultState();
        protected static final BlockState MAGMA_BLOCK = Blocks.MAGMA_BLOCK.getDefaultState();
        protected static final BlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
        protected static final BlockState NETHER_GOLD_ORE = Blocks.NETHER_GOLD_ORE.getDefaultState();
        protected static final BlockState NETHER_QUARTZ_ORE = Blocks.NETHER_QUARTZ_ORE.getDefaultState();
        protected static final BlockState BLACKSTONE = Blocks.BLACKSTONE.getDefaultState();
        protected static final BlockState ANCIENT_DEBRIS = Blocks.ANCIENT_DEBRIS.getDefaultState();
        protected static final BlockState BASALT = Blocks.BASALT.getDefaultState();
        protected static final BlockState CRIMSON_FUNGUS = Blocks.CRIMSON_FUNGUS.getDefaultState();
        protected static final BlockState WARPED_FUNGUS = Blocks.WARPED_FUNGUS.getDefaultState();
        protected static final BlockState WARPED_ROOTS = Blocks.WARPED_ROOTS.getDefaultState();
        protected static final BlockState NETHER_SPROUTS = Blocks.NETHER_SPROUTS.getDefaultState();
    }

    public static final class Placements {
        public static final BeehiveTreeDecorator BEES_0002_PLACEMENT = new BeehiveTreeDecorator(0.002f);
        public static final BeehiveTreeDecorator BEES_002_PLACEMENT = new BeehiveTreeDecorator(0.02f);
        public static final BeehiveTreeDecorator BEES_005_PLACEMENT = new BeehiveTreeDecorator(0.05f);
        public static final ConfiguredPlacement<FeatureSpreadConfig> FIRE_PLACEMENT = Placement.FIRE.configure(new FeatureSpreadConfig(10));
        public static final ConfiguredPlacement<NoPlacementConfig> FLOWER_TALL_GRASS_PLACEMENT = Placement.field_242904_h.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<NoPlacementConfig> KELP_PLACEMENT = Placement.TOP_SOLID_HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<NoPlacementConfig> BAMBOO_PLACEMENT = Placement.field_242906_k.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<NoPlacementConfig> HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT = Placement.field_242905_i.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<TopSolidRangeConfig> NETHER_SPRING_ORE_PLACEMENT = Placement.field_242907_l.configure(new TopSolidRangeConfig(10, 20, 128));
        public static final ConfiguredPlacement<TopSolidRangeConfig> SPRING_PLACEMENT = Placement.field_242907_l.configure(new TopSolidRangeConfig(4, 8, 128));
        public static final ConfiguredPlacement<?> VEGETATION_PLACEMENT = Placement.field_242911_p.configure(NoPlacementConfig.field_236556_b_);
        public static final ConfiguredPlacement<?> HEIGHTMAP_PLACEMENT = (ConfiguredPlacement)FLOWER_TALL_GRASS_PLACEMENT.func_242728_a();
        public static final ConfiguredPlacement<?> PATCH_PLACEMENT = (ConfiguredPlacement)HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT.func_242728_a();
        public static final ConfiguredPlacement<?> SEAGRASS_DISK_PLACEMENT = (ConfiguredPlacement)KELP_PLACEMENT.func_242728_a();
    }

    public static final class Configs {
        public static final BlockClusterFeatureConfig GRASS_PATCH_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.GRASS), SimpleBlockPlacer.PLACER).tries(32).build();
        public static final BlockClusterFeatureConfig TAIGA_GRASS_CONFIG = new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider().addWeightedBlockstate(States.GRASS, 1).addWeightedBlockstate(States.FERN, 4), SimpleBlockPlacer.PLACER).tries(32).build();
        public static final BlockClusterFeatureConfig JUNGLE_VEGETATION_CONFIG = new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider().addWeightedBlockstate(States.GRASS, 3).addWeightedBlockstate(States.FERN, 1), SimpleBlockPlacer.PLACER).blacklist(ImmutableSet.of(States.PODZOL)).tries(32).build();
        public static final BlockClusterFeatureConfig NORMAL_FLOWER_CONFIG = new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider().addWeightedBlockstate(States.POPPY, 2).addWeightedBlockstate(States.DANDELION, 1), SimpleBlockPlacer.PLACER).tries(64).build();
        public static final BlockClusterFeatureConfig DEAD_BUSH_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.DEAD_BUSH), SimpleBlockPlacer.PLACER).tries(4).build();
        public static final BlockClusterFeatureConfig BERRY_BUSH_PATCH_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.SWEET_BERRY_BUSH), SimpleBlockPlacer.PLACER).tries(64).whitelist(ImmutableSet.of(States.GRASS_BLOCK.getBlock())).func_227317_b_().build();
        public static final BlockClusterFeatureConfig TALL_GRASS_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.TALL_GRASS), new DoublePlantBlockPlacer()).tries(64).func_227317_b_().build();
        public static final BlockClusterFeatureConfig SUGAR_CANE_PATCH_CONFIG = new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(States.SUGAR_CANE), new ColumnBlockPlacer(2, 2)).tries(20).xSpread(4).ySpread(0).zSpread(4).func_227317_b_().requiresWater().build();
        public static final LiquidsConfig LAVA_SPRING_CONFIG = new LiquidsConfig(States.LAVA, true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE));
        public static final LiquidsConfig CLOSED_SPRING_CONFIG = new LiquidsConfig(States.LAVA, false, 5, 0, ImmutableSet.of(Blocks.NETHERRACK));
        public static final BlockStateProvidingFeatureConfig CRIMSON_FOREST_VEGETATION_CONFIG = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.CRIMSON_ROOTS, 87).addWeightedBlockstate(States.CRIMSON_FUNGUS, 11).addWeightedBlockstate(States.WARPED_FUNGUS, 1));
        public static final BlockStateProvidingFeatureConfig WARPED_FOREST_VEGETATION_CONFIG = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.WARPED_ROOTS, 85).addWeightedBlockstate(States.CRIMSON_ROOTS, 1).addWeightedBlockstate(States.WARPED_FUNGUS, 13).addWeightedBlockstate(States.CRIMSON_FUNGUS, 1));
        public static final BlockStateProvidingFeatureConfig NETHER_SPROUTS_CONFIG = new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(States.NETHER_SPROUTS));
    }
}

