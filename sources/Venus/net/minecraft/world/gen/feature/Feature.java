/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.feature.BambooFeature;
import net.minecraft.world.gen.feature.BasaltColumnFeature;
import net.minecraft.world.gen.feature.BasaltDeltasFeature;
import net.minecraft.world.gen.feature.BasaltPillarFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BigBrownMushroomFeature;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BigRedMushroomFeature;
import net.minecraft.world.gen.feature.BlobReplacementConfig;
import net.minecraft.world.gen.feature.BlockBlobFeature;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockPileFeature;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.BlockWithContextFeature;
import net.minecraft.world.gen.feature.BlueIceFeature;
import net.minecraft.world.gen.feature.BonusChestFeature;
import net.minecraft.world.gen.feature.ChorusPlantFeature;
import net.minecraft.world.gen.feature.ColumnConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.CoralClawFeature;
import net.minecraft.world.gen.feature.CoralMushroomFeature;
import net.minecraft.world.gen.feature.CoralTreeFeature;
import net.minecraft.world.gen.feature.DecoratedFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFlowersFeature;
import net.minecraft.world.gen.feature.DesertWellsFeature;
import net.minecraft.world.gen.feature.DungeonsFeature;
import net.minecraft.world.gen.feature.EndGatewayConfig;
import net.minecraft.world.gen.feature.EndGatewayFeature;
import net.minecraft.world.gen.feature.EndIslandFeature;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.FillLayerConfig;
import net.minecraft.world.gen.feature.FillLayerFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.FossilsFeature;
import net.minecraft.world.gen.feature.GlowstoneBlobFeature;
import net.minecraft.world.gen.feature.HugeFungusConfig;
import net.minecraft.world.gen.feature.HugeFungusFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.IceAndSnowFeature;
import net.minecraft.world.gen.feature.IcePathFeature;
import net.minecraft.world.gen.feature.IceSpikeFeature;
import net.minecraft.world.gen.feature.IcebergFeature;
import net.minecraft.world.gen.feature.KelpFeature;
import net.minecraft.world.gen.feature.LakesFeature;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeature;
import net.minecraft.world.gen.feature.NetherVegetationFeature;
import net.minecraft.world.gen.feature.NoExposedOreFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.NoOpFeature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.RandomPatchFeature;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.ReplaceBlockFeature;
import net.minecraft.world.gen.feature.SeaGrassFeature;
import net.minecraft.world.gen.feature.SeaPickleFeature;
import net.minecraft.world.gen.feature.SingleRandomFeature;
import net.minecraft.world.gen.feature.SingleRandomFeatureConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.SphereReplaceFeature;
import net.minecraft.world.gen.feature.SpringFeature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TwistingVineFeature;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceFeature;
import net.minecraft.world.gen.feature.VinesFeature;
import net.minecraft.world.gen.feature.VoidStartPlatformFeature;
import net.minecraft.world.gen.feature.WeepingVineFeature;
import net.minecraft.world.gen.feature.structure.BasaltDeltasStructure;
import net.minecraft.world.gen.feature.structure.NetherackBlobReplacementStructure;

public abstract class Feature<FC extends IFeatureConfig> {
    public static final Feature<NoFeatureConfig> NO_OP = Feature.register("no_op", new NoOpFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<BaseTreeFeatureConfig> TREE = Feature.register("tree", new TreeFeature(BaseTreeFeatureConfig.CODEC));
    public static final FlowersFeature<BlockClusterFeatureConfig> FLOWER = Feature.register("flower", new DefaultFlowersFeature(BlockClusterFeatureConfig.field_236587_a_));
    public static final FlowersFeature<BlockClusterFeatureConfig> NO_BONEMEAL_FLOWER = Feature.register("no_bonemeal_flower", new DefaultFlowersFeature(BlockClusterFeatureConfig.field_236587_a_));
    public static final Feature<BlockClusterFeatureConfig> RANDOM_PATCH = Feature.register("random_patch", new RandomPatchFeature(BlockClusterFeatureConfig.field_236587_a_));
    public static final Feature<BlockStateProvidingFeatureConfig> BLOCK_PILE = Feature.register("block_pile", new BlockPileFeature(BlockStateProvidingFeatureConfig.field_236453_a_));
    public static final Feature<LiquidsConfig> SPRING_FEATURE = Feature.register("spring_feature", new SpringFeature(LiquidsConfig.field_236649_a_));
    public static final Feature<NoFeatureConfig> CHORUS_PLANT = Feature.register("chorus_plant", new ChorusPlantFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<ReplaceBlockConfig> EMERALD_ORE = Feature.register("emerald_ore", new ReplaceBlockFeature(ReplaceBlockConfig.field_236604_a_));
    public static final Feature<NoFeatureConfig> VOID_START_PLATFORM = Feature.register("void_start_platform", new VoidStartPlatformFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> DESERT_WELL = Feature.register("desert_well", new DesertWellsFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> FOSSIL = Feature.register("fossil", new FossilsFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<BigMushroomFeatureConfig> HUGE_RED_MUSHROOM = Feature.register("huge_red_mushroom", new BigRedMushroomFeature(BigMushroomFeatureConfig.field_236528_a_));
    public static final Feature<BigMushroomFeatureConfig> HUGE_BROWN_MUSHROOM = Feature.register("huge_brown_mushroom", new BigBrownMushroomFeature(BigMushroomFeatureConfig.field_236528_a_));
    public static final Feature<NoFeatureConfig> ICE_SPIKE = Feature.register("ice_spike", new IceSpikeFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> GLOWSTONE_BLOB = Feature.register("glowstone_blob", new GlowstoneBlobFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> FREEZE_TOP_LAYER = Feature.register("freeze_top_layer", new IceAndSnowFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> VINES = Feature.register("vines", new VinesFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> MONSTER_ROOM = Feature.register("monster_room", new DungeonsFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> BLUE_ICE = Feature.register("blue_ice", new BlueIceFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<BlockStateFeatureConfig> ICEBERG = Feature.register("iceberg", new IcebergFeature(BlockStateFeatureConfig.field_236455_a_));
    public static final Feature<BlockStateFeatureConfig> FOREST_ROCK = Feature.register("forest_rock", new BlockBlobFeature(BlockStateFeatureConfig.field_236455_a_));
    public static final Feature<SphereReplaceConfig> DISK = Feature.register("disk", new SphereReplaceFeature(SphereReplaceConfig.field_236516_a_));
    public static final Feature<SphereReplaceConfig> ICE_PATCH = Feature.register("ice_patch", new IcePathFeature(SphereReplaceConfig.field_236516_a_));
    public static final Feature<BlockStateFeatureConfig> LAKE = Feature.register("lake", new LakesFeature(BlockStateFeatureConfig.field_236455_a_));
    public static final Feature<OreFeatureConfig> ORE = Feature.register("ore", new OreFeature(OreFeatureConfig.field_236566_a_));
    public static final Feature<EndSpikeFeatureConfig> END_SPIKE = Feature.register("end_spike", new EndSpikeFeature(EndSpikeFeatureConfig.field_236644_a_));
    public static final Feature<NoFeatureConfig> END_ISLAND = Feature.register("end_island", new EndIslandFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<EndGatewayConfig> END_GATEWAY = Feature.register("end_gateway", new EndGatewayFeature(EndGatewayConfig.field_236522_a_));
    public static final SeaGrassFeature SEAGRASS = Feature.register("seagrass", new SeaGrassFeature(ProbabilityConfig.field_236576_b_));
    public static final Feature<NoFeatureConfig> KELP = Feature.register("kelp", new KelpFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> CORAL_TREE = Feature.register("coral_tree", new CoralTreeFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> CORAL_MUSHROOM = Feature.register("coral_mushroom", new CoralMushroomFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> CORAL_CLAW = Feature.register("coral_claw", new CoralClawFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<FeatureSpreadConfig> SEA_PICKLE = Feature.register("sea_pickle", new SeaPickleFeature(FeatureSpreadConfig.field_242797_a));
    public static final Feature<BlockWithContextConfig> SIMPLE_BLOCK = Feature.register("simple_block", new BlockWithContextFeature(BlockWithContextConfig.field_236636_a_));
    public static final Feature<ProbabilityConfig> BAMBOO = Feature.register("bamboo", new BambooFeature(ProbabilityConfig.field_236576_b_));
    public static final Feature<HugeFungusConfig> HUGE_FUNGUS = Feature.register("huge_fungus", new HugeFungusFeature(HugeFungusConfig.field_236298_a_));
    public static final Feature<BlockStateProvidingFeatureConfig> NETHER_FOREST_VEGETATION = Feature.register("nether_forest_vegetation", new NetherVegetationFeature(BlockStateProvidingFeatureConfig.field_236453_a_));
    public static final Feature<NoFeatureConfig> WEEPING_VINES = Feature.register("weeping_vines", new WeepingVineFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> TWISTING_VINES = Feature.register("twisting_vines", new TwistingVineFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<ColumnConfig> BASALT_COLUMNS = Feature.register("basalt_columns", new BasaltColumnFeature(ColumnConfig.CODEC));
    public static final Feature<BasaltDeltasFeature> DELTA_FEATURE = Feature.register("delta_feature", new BasaltDeltasStructure(BasaltDeltasFeature.field_236495_a_));
    public static final Feature<BlobReplacementConfig> NETHERRACK_REPLACE_BLOBS = Feature.register("netherrack_replace_blobs", new NetherackBlobReplacementStructure(BlobReplacementConfig.field_242817_a));
    public static final Feature<FillLayerConfig> FILL_LAYER = Feature.register("fill_layer", new FillLayerFeature(FillLayerConfig.field_236537_a_));
    public static final BonusChestFeature BONUS_CHEST = Feature.register("bonus_chest", new BonusChestFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<NoFeatureConfig> BASALT_PILLAR = Feature.register("basalt_pillar", new BasaltPillarFeature(NoFeatureConfig.field_236558_a_));
    public static final Feature<OreFeatureConfig> NO_SURFACE_ORE = Feature.register("no_surface_ore", new NoExposedOreFeature(OreFeatureConfig.field_236566_a_));
    public static final Feature<MultipleRandomFeatureConfig> RANDOM_SELECTOR = Feature.register("random_selector", new MultipleWithChanceRandomFeature(MultipleRandomFeatureConfig.field_236583_a_));
    public static final Feature<SingleRandomFeature> SIMPLE_RANDOM_SELECTOR = Feature.register("simple_random_selector", new SingleRandomFeatureConfig(SingleRandomFeature.field_236642_a_));
    public static final Feature<TwoFeatureChoiceConfig> RANDOM_BOOLEAN_SELECTOR = Feature.register("random_boolean_selector", new TwoFeatureChoiceFeature(TwoFeatureChoiceConfig.field_236579_a_));
    public static final Feature<DecoratedFeatureConfig> DECORATED = Feature.register("decorated", new DecoratedFeature(DecoratedFeatureConfig.field_236491_a_));
    private final Codec<ConfiguredFeature<FC, Feature<FC>>> codec;

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String string, F f) {
        return (F)Registry.register(Registry.FEATURE, string, f);
    }

    public Feature(Codec<FC> codec) {
        this.codec = ((MapCodec)codec.fieldOf("config")).xmap(this::lambda$new$0, Feature::lambda$new$1).codec();
    }

    public Codec<ConfiguredFeature<FC, Feature<FC>>> getCodec() {
        return this.codec;
    }

    public ConfiguredFeature<FC, ?> withConfiguration(FC FC) {
        return new ConfiguredFeature<FC, Feature>(this, FC);
    }

    protected void setBlockState(IWorldWriter iWorldWriter, BlockPos blockPos, BlockState blockState) {
        iWorldWriter.setBlockState(blockPos, blockState, 3);
    }

    public abstract boolean func_241855_a(ISeedReader var1, ChunkGenerator var2, Random var3, BlockPos var4, FC var5);

    protected static boolean isStone(Block block) {
        return block == Blocks.STONE || block == Blocks.GRANITE || block == Blocks.DIORITE || block == Blocks.ANDESITE;
    }

    public static boolean isDirt(Block block) {
        return block == Blocks.DIRT || block == Blocks.GRASS_BLOCK || block == Blocks.PODZOL || block == Blocks.COARSE_DIRT || block == Blocks.MYCELIUM;
    }

    public static boolean isDirtAt(IWorldGenerationBaseReader iWorldGenerationBaseReader, BlockPos blockPos) {
        return iWorldGenerationBaseReader.hasBlockState(blockPos, Feature::lambda$isDirtAt$2);
    }

    public static boolean isAirAt(IWorldGenerationBaseReader iWorldGenerationBaseReader, BlockPos blockPos) {
        return iWorldGenerationBaseReader.hasBlockState(blockPos, AbstractBlock.AbstractBlockState::isAir);
    }

    private static boolean lambda$isDirtAt$2(BlockState blockState) {
        return Feature.isDirt(blockState.getBlock());
    }

    private static IFeatureConfig lambda$new$1(ConfiguredFeature configuredFeature) {
        return configuredFeature.config;
    }

    private ConfiguredFeature lambda$new$0(IFeatureConfig iFeatureConfig) {
        return new ConfiguredFeature<IFeatureConfig, Feature>(this, iFeatureConfig);
    }
}

