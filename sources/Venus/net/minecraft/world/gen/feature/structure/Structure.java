/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.RuinedPortalFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.BastionRemantsStructure;
import net.minecraft.world.gen.feature.structure.BuriedTreasureStructure;
import net.minecraft.world.gen.feature.structure.DesertPyramidStructure;
import net.minecraft.world.gen.feature.structure.EndCityStructure;
import net.minecraft.world.gen.feature.structure.FortressStructure;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.IglooStructure;
import net.minecraft.world.gen.feature.structure.JunglePyramidStructure;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.NetherFossilStructure;
import net.minecraft.world.gen.feature.structure.OceanMonumentStructure;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.PillagerOutpostStructure;
import net.minecraft.world.gen.feature.structure.RuinedPortalStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckStructure;
import net.minecraft.world.gen.feature.structure.StrongholdStructure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.SwampHutStructure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.structure.VillageStructure;
import net.minecraft.world.gen.feature.structure.WoodlandMansionStructure;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Structure<C extends IFeatureConfig> {
    public static final BiMap<String, Structure<?>> field_236365_a_ = HashBiMap.create();
    private static final Map<Structure<?>, GenerationStage.Decoration> field_236385_u_ = Maps.newHashMap();
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Structure<VillageConfig> field_236366_b_ = Structure.func_236394_a_("Pillager_Outpost", new PillagerOutpostStructure(VillageConfig.field_236533_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<MineshaftConfig> field_236367_c_ = Structure.func_236394_a_("Mineshaft", new MineshaftStructure(MineshaftConfig.field_236541_a_), GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
    public static final Structure<NoFeatureConfig> field_236368_d_ = Structure.func_236394_a_("Mansion", new WoodlandMansionStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<NoFeatureConfig> field_236369_e_ = Structure.func_236394_a_("Jungle_Pyramid", new JunglePyramidStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<NoFeatureConfig> field_236370_f_ = Structure.func_236394_a_("Desert_Pyramid", new DesertPyramidStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<NoFeatureConfig> field_236371_g_ = Structure.func_236394_a_("Igloo", new IglooStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<RuinedPortalFeature> field_236372_h_ = Structure.func_236394_a_("Ruined_Portal", new RuinedPortalStructure(RuinedPortalFeature.field_236627_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<ShipwreckConfig> field_236373_i_ = Structure.func_236394_a_("Shipwreck", new ShipwreckStructure(ShipwreckConfig.field_236634_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final SwampHutStructure field_236374_j_ = Structure.func_236394_a_("Swamp_Hut", new SwampHutStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<NoFeatureConfig> field_236375_k_ = Structure.func_236394_a_("Stronghold", new StrongholdStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.STRONGHOLDS);
    public static final Structure<NoFeatureConfig> field_236376_l_ = Structure.func_236394_a_("Monument", new OceanMonumentStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<OceanRuinConfig> field_236377_m_ = Structure.func_236394_a_("Ocean_Ruin", new OceanRuinStructure(OceanRuinConfig.field_236561_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<NoFeatureConfig> field_236378_n_ = Structure.func_236394_a_("Fortress", new FortressStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.UNDERGROUND_DECORATION);
    public static final Structure<NoFeatureConfig> field_236379_o_ = Structure.func_236394_a_("EndCity", new EndCityStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<ProbabilityConfig> field_236380_p_ = Structure.func_236394_a_("Buried_Treasure", new BuriedTreasureStructure(ProbabilityConfig.field_236576_b_), GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
    public static final Structure<VillageConfig> field_236381_q_ = Structure.func_236394_a_("Village", new VillageStructure(VillageConfig.field_236533_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final Structure<NoFeatureConfig> field_236382_r_ = Structure.func_236394_a_("Nether_Fossil", new NetherFossilStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.UNDERGROUND_DECORATION);
    public static final Structure<VillageConfig> field_236383_s_ = Structure.func_236394_a_("Bastion_Remnant", new BastionRemantsStructure(VillageConfig.field_236533_a_), GenerationStage.Decoration.SURFACE_STRUCTURES);
    public static final List<Structure<?>> field_236384_t_ = ImmutableList.of(field_236366_b_, field_236381_q_, field_236382_r_);
    private static final ResourceLocation field_242783_w = new ResourceLocation("jigsaw");
    private static final Map<ResourceLocation, ResourceLocation> field_242784_x = ImmutableMap.builder().put(new ResourceLocation("nvi"), field_242783_w).put(new ResourceLocation("pcp"), field_242783_w).put(new ResourceLocation("bastionremnant"), field_242783_w).put(new ResourceLocation("runtime"), field_242783_w).build();
    private final Codec<StructureFeature<C, Structure<C>>> field_236386_w_;

    private static <F extends Structure<?>> F func_236394_a_(String string, F f, GenerationStage.Decoration decoration) {
        field_236365_a_.put(string.toLowerCase(Locale.ROOT), f);
        field_236385_u_.put(f, decoration);
        return (F)Registry.register(Registry.STRUCTURE_FEATURE, string.toLowerCase(Locale.ROOT), f);
    }

    public Structure(Codec<C> codec) {
        this.field_236386_w_ = ((MapCodec)codec.fieldOf("config")).xmap(this::lambda$new$0, Structure::lambda$new$1).codec();
    }

    public GenerationStage.Decoration func_236396_f_() {
        return field_236385_u_.get(this);
    }

    public static void func_236397_g_() {
    }

    @Nullable
    public static StructureStart<?> func_236393_a_(TemplateManager templateManager, CompoundNBT compoundNBT, long l) {
        String string = compoundNBT.getString("id");
        if ("INVALID".equals(string)) {
            return StructureStart.DUMMY;
        }
        Structure<?> structure = Registry.STRUCTURE_FEATURE.getOrDefault(new ResourceLocation(string.toLowerCase(Locale.ROOT)));
        if (structure == null) {
            LOGGER.error("Unknown feature id: {}", (Object)string);
            return null;
        }
        int n = compoundNBT.getInt("ChunkX");
        int n2 = compoundNBT.getInt("ChunkZ");
        int n3 = compoundNBT.getInt("references");
        MutableBoundingBox mutableBoundingBox = compoundNBT.contains("BB") ? new MutableBoundingBox(compoundNBT.getIntArray("BB")) : MutableBoundingBox.getNewBoundingBox();
        ListNBT listNBT = compoundNBT.getList("Children", 10);
        try {
            StructureStart<?> structureStart = structure.func_236387_a_(n, n2, mutableBoundingBox, n3, l);
            for (int i = 0; i < listNBT.size(); ++i) {
                CompoundNBT compoundNBT2 = listNBT.getCompound(i);
                String string2 = compoundNBT2.getString("id").toLowerCase(Locale.ROOT);
                ResourceLocation resourceLocation = new ResourceLocation(string2);
                ResourceLocation resourceLocation2 = field_242784_x.getOrDefault(resourceLocation, resourceLocation);
                IStructurePieceType iStructurePieceType = Registry.STRUCTURE_PIECE.getOrDefault(resourceLocation2);
                if (iStructurePieceType == null) {
                    LOGGER.error("Unknown structure piece id: {}", (Object)resourceLocation2);
                    continue;
                }
                try {
                    StructurePiece structurePiece = iStructurePieceType.load(templateManager, compoundNBT2);
                    structureStart.getComponents().add(structurePiece);
                    continue;
                } catch (Exception exception) {
                    LOGGER.error("Exception loading structure piece with id {}", (Object)resourceLocation2, (Object)exception);
                }
            }
            return structureStart;
        } catch (Exception exception) {
            LOGGER.error("Failed Start with id {}", (Object)string, (Object)exception);
            return null;
        }
    }

    public Codec<StructureFeature<C, Structure<C>>> func_236398_h_() {
        return this.field_236386_w_;
    }

    public StructureFeature<C, ? extends Structure<C>> func_236391_a_(C c) {
        return new StructureFeature<C, Structure>(this, c);
    }

    @Nullable
    public BlockPos func_236388_a_(IWorldReader iWorldReader, StructureManager structureManager, BlockPos blockPos, int n, boolean bl, long l, StructureSeparationSettings structureSeparationSettings) {
        int n2 = structureSeparationSettings.func_236668_a_();
        int n3 = blockPos.getX() >> 4;
        int n4 = blockPos.getZ() >> 4;
        SharedSeedRandom sharedSeedRandom = new SharedSeedRandom();
        block0: for (int i = 0; i <= n; ++i) {
            for (int j = -i; j <= i; ++j) {
                boolean bl2 = j == -i || j == i;
                for (int k = -i; k <= i; ++k) {
                    boolean bl3;
                    boolean bl4 = bl3 = k == -i || k == i;
                    if (!bl2 && !bl3) continue;
                    int n5 = n3 + n2 * j;
                    int n6 = n4 + n2 * k;
                    ChunkPos chunkPos = this.func_236392_a_(structureSeparationSettings, l, sharedSeedRandom, n5, n6);
                    IChunk iChunk = iWorldReader.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.STRUCTURE_STARTS);
                    StructureStart<?> structureStart = structureManager.func_235013_a_(SectionPos.from(iChunk.getPos(), 0), this, iChunk);
                    if (structureStart != null && structureStart.isValid()) {
                        if (bl && structureStart.isRefCountBelowMax()) {
                            structureStart.incrementRefCount();
                            return structureStart.getPos();
                        }
                        if (!bl) {
                            return structureStart.getPos();
                        }
                    }
                    if (i == 0) break;
                }
                if (i == 0) continue block0;
            }
        }
        return null;
    }

    protected boolean func_230365_b_() {
        return false;
    }

    public final ChunkPos func_236392_a_(StructureSeparationSettings structureSeparationSettings, long l, SharedSeedRandom sharedSeedRandom, int n, int n2) {
        int n3;
        int n4;
        int n5 = structureSeparationSettings.func_236668_a_();
        int n6 = structureSeparationSettings.func_236671_b_();
        int n7 = Math.floorDiv(n, n5);
        int n8 = Math.floorDiv(n2, n5);
        sharedSeedRandom.setLargeFeatureSeedWithSalt(l, n7, n8, structureSeparationSettings.func_236673_c_());
        if (this.func_230365_b_()) {
            n4 = sharedSeedRandom.nextInt(n5 - n6);
            n3 = sharedSeedRandom.nextInt(n5 - n6);
        } else {
            n4 = (sharedSeedRandom.nextInt(n5 - n6) + sharedSeedRandom.nextInt(n5 - n6)) / 2;
            n3 = (sharedSeedRandom.nextInt(n5 - n6) + sharedSeedRandom.nextInt(n5 - n6)) / 2;
        }
        return new ChunkPos(n7 * n5 + n4, n8 * n5 + n3);
    }

    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long l, SharedSeedRandom sharedSeedRandom, int n, int n2, Biome biome, ChunkPos chunkPos, C c) {
        return false;
    }

    private StructureStart<C> func_236387_a_(int n, int n2, MutableBoundingBox mutableBoundingBox, int n3, long l) {
        return this.getStartFactory().create(this, n, n2, mutableBoundingBox, n3, l);
    }

    public StructureStart<?> func_242785_a(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, TemplateManager templateManager, long l, ChunkPos chunkPos, Biome biome, int n, SharedSeedRandom sharedSeedRandom, StructureSeparationSettings structureSeparationSettings, C c) {
        ChunkPos chunkPos2 = this.func_236392_a_(structureSeparationSettings, l, sharedSeedRandom, chunkPos.x, chunkPos.z);
        if (chunkPos.x == chunkPos2.x && chunkPos.z == chunkPos2.z && this.func_230363_a_(chunkGenerator, biomeProvider, l, sharedSeedRandom, chunkPos.x, chunkPos.z, biome, chunkPos2, c)) {
            StructureStart<C> structureStart = this.func_236387_a_(chunkPos.x, chunkPos.z, MutableBoundingBox.getNewBoundingBox(), n, l);
            structureStart.func_230364_a_(dynamicRegistries, chunkGenerator, templateManager, chunkPos.x, chunkPos.z, biome, c);
            if (structureStart.isValid()) {
                return structureStart;
            }
        }
        return StructureStart.DUMMY;
    }

    public abstract IStartFactory<C> getStartFactory();

    public String getStructureName() {
        return (String)field_236365_a_.inverse().get(this);
    }

    public List<MobSpawnInfo.Spawners> getSpawnList() {
        return ImmutableList.of();
    }

    public List<MobSpawnInfo.Spawners> getCreatureSpawnList() {
        return ImmutableList.of();
    }

    private static IFeatureConfig lambda$new$1(StructureFeature structureFeature) {
        return structureFeature.field_236269_c_;
    }

    private StructureFeature lambda$new$0(IFeatureConfig iFeatureConfig) {
        return new StructureFeature<IFeatureConfig, Structure>(this, iFeatureConfig);
    }

    public static interface IStartFactory<C extends IFeatureConfig> {
        public StructureStart<C> create(Structure<C> var1, int var2, int var3, MutableBoundingBox var4, int var5, long var6);
    }
}

