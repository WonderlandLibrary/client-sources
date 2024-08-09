/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.CaveEdge;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.ChancePlacement;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountExtraPlacement;
import net.minecraft.world.gen.placement.CountMultilayerPlacement;
import net.minecraft.world.gen.placement.CountNoiseBiasedPlacement;
import net.minecraft.world.gen.placement.CountNoisePlacement;
import net.minecraft.world.gen.placement.CountPlacement;
import net.minecraft.world.gen.placement.DarkOakTreePlacement;
import net.minecraft.world.gen.placement.DecoratedPlacement;
import net.minecraft.world.gen.placement.DecoratedPlacementConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.DepthAveragePlacement;
import net.minecraft.world.gen.placement.EndGateway;
import net.minecraft.world.gen.placement.EndIsland;
import net.minecraft.world.gen.placement.FirePlacement;
import net.minecraft.world.gen.placement.GlowstonePlacement;
import net.minecraft.world.gen.placement.Height4To32;
import net.minecraft.world.gen.placement.HeightmapPlacement;
import net.minecraft.world.gen.placement.HeightmapSpreadDoublePlacement;
import net.minecraft.world.gen.placement.HeightmapWorldSurfacePlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.IcebergPlacement;
import net.minecraft.world.gen.placement.LakeLava;
import net.minecraft.world.gen.placement.LakeWater;
import net.minecraft.world.gen.placement.NetherMagma;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Passthrough;
import net.minecraft.world.gen.placement.RangeBiasedPlacement;
import net.minecraft.world.gen.placement.RangePlacement;
import net.minecraft.world.gen.placement.RangeVeryBiasedPlacement;
import net.minecraft.world.gen.placement.Spread32AbovePlacement;
import net.minecraft.world.gen.placement.SquarePlacement;
import net.minecraft.world.gen.placement.TopSolidOnce;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;

public abstract class Placement<DC extends IPlacementConfig> {
    public static final Placement<NoPlacementConfig> NOPE = Placement.register("nope", new Passthrough(NoPlacementConfig.field_236555_a_));
    public static final Placement<ChanceConfig> field_242898_b = Placement.register("chance", new ChancePlacement(ChanceConfig.field_236950_a_));
    public static final Placement<FeatureSpreadConfig> field_242899_c = Placement.register("count", new CountPlacement(FeatureSpreadConfig.field_242797_a));
    public static final Placement<NoiseDependant> field_242900_d = Placement.register("count_noise", new CountNoisePlacement(NoiseDependant.field_236550_a_));
    public static final Placement<TopSolidWithNoiseConfig> field_242901_e = Placement.register("count_noise_biased", new CountNoiseBiasedPlacement(TopSolidWithNoiseConfig.field_236978_a_));
    public static final Placement<AtSurfaceWithExtraConfig> field_242902_f = Placement.register("count_extra", new CountExtraPlacement(AtSurfaceWithExtraConfig.field_236973_a_));
    public static final Placement<NoPlacementConfig> field_242903_g = Placement.register("square", new SquarePlacement(NoPlacementConfig.field_236555_a_));
    public static final Placement<NoPlacementConfig> field_242904_h = Placement.register("heightmap", new HeightmapPlacement<NoPlacementConfig>(NoPlacementConfig.field_236555_a_));
    public static final Placement<NoPlacementConfig> field_242905_i = Placement.register("heightmap_spread_double", new HeightmapSpreadDoublePlacement<NoPlacementConfig>(NoPlacementConfig.field_236555_a_));
    public static final Placement<NoPlacementConfig> TOP_SOLID_HEIGHTMAP = Placement.register("top_solid_heightmap", new TopSolidOnce(NoPlacementConfig.field_236555_a_));
    public static final Placement<NoPlacementConfig> field_242906_k = Placement.register("heightmap_world_surface", new HeightmapWorldSurfacePlacement(NoPlacementConfig.field_236555_a_));
    public static final Placement<TopSolidRangeConfig> field_242907_l = Placement.register("range", new RangePlacement(TopSolidRangeConfig.field_236985_a_));
    public static final Placement<TopSolidRangeConfig> field_242908_m = Placement.register("range_biased", new RangeBiasedPlacement(TopSolidRangeConfig.field_236985_a_));
    public static final Placement<TopSolidRangeConfig> field_242909_n = Placement.register("range_very_biased", new RangeVeryBiasedPlacement(TopSolidRangeConfig.field_236985_a_));
    public static final Placement<DepthAverageConfig> field_242910_o = Placement.register("depth_average", new DepthAveragePlacement(DepthAverageConfig.field_236955_a_));
    public static final Placement<NoPlacementConfig> field_242911_p = Placement.register("spread_32_above", new Spread32AbovePlacement(NoPlacementConfig.field_236555_a_));
    public static final Placement<CaveEdgeConfig> CARVING_MASK = Placement.register("carving_mask", new CaveEdge(CaveEdgeConfig.field_236946_a_));
    public static final Placement<FeatureSpreadConfig> FIRE = Placement.register("fire", new FirePlacement(FeatureSpreadConfig.field_242797_a));
    public static final Placement<NoPlacementConfig> MAGMA = Placement.register("magma", new NetherMagma(NoPlacementConfig.field_236555_a_));
    public static final Placement<NoPlacementConfig> EMERALD_ORE = Placement.register("emerald_ore", new Height4To32(NoPlacementConfig.field_236555_a_));
    public static final Placement<ChanceConfig> LAVA_LAKE = Placement.register("lava_lake", new LakeLava(ChanceConfig.field_236950_a_));
    public static final Placement<ChanceConfig> WATER_LAKE = Placement.register("water_lake", new LakeWater(ChanceConfig.field_236950_a_));
    public static final Placement<FeatureSpreadConfig> field_242912_w = Placement.register("glowstone", new GlowstonePlacement(FeatureSpreadConfig.field_242797_a));
    public static final Placement<NoPlacementConfig> END_GATEWAY = Placement.register("end_gateway", new EndGateway(NoPlacementConfig.field_236555_a_));
    public static final Placement<NoPlacementConfig> DARK_OAK_TREE = Placement.register("dark_oak_tree", new DarkOakTreePlacement(NoPlacementConfig.field_236555_a_));
    public static final Placement<NoPlacementConfig> ICEBERG = Placement.register("iceberg", new IcebergPlacement(NoPlacementConfig.field_236555_a_));
    public static final Placement<NoPlacementConfig> END_ISLAND = Placement.register("end_island", new EndIsland(NoPlacementConfig.field_236555_a_));
    public static final Placement<DecoratedPlacementConfig> field_242896_B = Placement.register("decorated", new DecoratedPlacement(DecoratedPlacementConfig.field_242883_a));
    public static final Placement<FeatureSpreadConfig> field_242897_C = Placement.register("count_multilayer", new CountMultilayerPlacement(FeatureSpreadConfig.field_242797_a));
    private final Codec<ConfiguredPlacement<DC>> codec;

    private static <T extends IPlacementConfig, G extends Placement<T>> G register(String string, G g) {
        return (G)Registry.register(Registry.DECORATOR, string, g);
    }

    public Placement(Codec<DC> codec) {
        this.codec = ((MapCodec)codec.fieldOf("config")).xmap(this::lambda$new$0, ConfiguredPlacement::func_242877_b).codec();
    }

    public ConfiguredPlacement<DC> configure(DC DC) {
        return new ConfiguredPlacement<DC>(this, DC);
    }

    public Codec<ConfiguredPlacement<DC>> getCodec() {
        return this.codec;
    }

    public abstract Stream<BlockPos> func_241857_a(WorldDecoratingHelper var1, Random var2, DC var3, BlockPos var4);

    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
    }

    private ConfiguredPlacement lambda$new$0(IPlacementConfig iPlacementConfig) {
        return new ConfiguredPlacement<IPlacementConfig>(this, iPlacementConfig);
    }
}

