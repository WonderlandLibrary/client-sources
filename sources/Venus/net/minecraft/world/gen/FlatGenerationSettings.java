/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.FillLayerConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlatGenerationSettings {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Codec<FlatGenerationSettings> field_236932_a_ = RecordCodecBuilder.create(FlatGenerationSettings::lambda$static$4).stable();
    private static final Map<Structure<?>, StructureFeature<?, ?>> STRUCTURES = Util.make(Maps.newHashMap(), FlatGenerationSettings::lambda$static$5);
    private final Registry<Biome> field_242867_d;
    private final DimensionStructuresSettings field_236933_f_;
    private final List<FlatLayerInfo> flatLayers = Lists.newArrayList();
    private Supplier<Biome> biomeToUse;
    private final BlockState[] states = new BlockState[256];
    private boolean allAir;
    private boolean field_236934_k_ = false;
    private boolean field_236935_l_ = false;

    public FlatGenerationSettings(Registry<Biome> registry, DimensionStructuresSettings dimensionStructuresSettings, List<FlatLayerInfo> list, boolean bl, boolean bl2, Optional<Supplier<Biome>> optional) {
        this(dimensionStructuresSettings, registry);
        if (bl) {
            this.func_236941_b_();
        }
        if (bl2) {
            this.func_236936_a_();
        }
        this.flatLayers.addAll(list);
        this.updateLayers();
        if (!optional.isPresent()) {
            LOGGER.error("Unknown biome, defaulting to plains");
            this.biomeToUse = () -> FlatGenerationSettings.lambda$new$6(registry);
        } else {
            this.biomeToUse = optional.get();
        }
    }

    public FlatGenerationSettings(DimensionStructuresSettings dimensionStructuresSettings, Registry<Biome> registry) {
        this.field_242867_d = registry;
        this.field_236933_f_ = dimensionStructuresSettings;
        this.biomeToUse = () -> FlatGenerationSettings.lambda$new$7(registry);
    }

    public FlatGenerationSettings func_236937_a_(DimensionStructuresSettings dimensionStructuresSettings) {
        return this.func_241527_a_(this.flatLayers, dimensionStructuresSettings);
    }

    public FlatGenerationSettings func_241527_a_(List<FlatLayerInfo> list, DimensionStructuresSettings dimensionStructuresSettings) {
        FlatGenerationSettings flatGenerationSettings = new FlatGenerationSettings(dimensionStructuresSettings, this.field_242867_d);
        for (FlatLayerInfo flatLayerInfo : list) {
            flatGenerationSettings.flatLayers.add(new FlatLayerInfo(flatLayerInfo.getLayerCount(), flatLayerInfo.getLayerMaterial().getBlock()));
            flatGenerationSettings.updateLayers();
        }
        flatGenerationSettings.func_242870_a(this.biomeToUse);
        if (this.field_236934_k_) {
            flatGenerationSettings.func_236936_a_();
        }
        if (this.field_236935_l_) {
            flatGenerationSettings.func_236941_b_();
        }
        return flatGenerationSettings;
    }

    public void func_236936_a_() {
        this.field_236934_k_ = true;
    }

    public void func_236941_b_() {
        this.field_236935_l_ = true;
    }

    public Biome func_236942_c_() {
        int n;
        boolean bl;
        Biome biome = this.getBiome();
        BiomeGenerationSettings biomeGenerationSettings = biome.getGenerationSettings();
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder().withSurfaceBuilder(biomeGenerationSettings.getSurfaceBuilder());
        if (this.field_236935_l_) {
            builder.withFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);
            builder.withFeature(GenerationStage.Decoration.LAKES, Features.LAKE_LAVA);
        }
        for (Map.Entry<Structure<?>, StructureSeparationSettings> blockStateArray2 : this.field_236933_f_.func_236195_a_().entrySet()) {
            builder.withStructure(biomeGenerationSettings.getStructure(STRUCTURES.get(blockStateArray2.getKey())));
        }
        boolean bl2 = bl = (!this.allAir || this.field_242867_d.getOptionalKey(biome).equals(Optional.of(Biomes.THE_VOID))) && this.field_236934_k_;
        if (bl) {
            List<List<Supplier<ConfiguredFeature<?, ?>>>> list = biomeGenerationSettings.getFeatures();
            for (n = 0; n < list.size(); ++n) {
                if (n == GenerationStage.Decoration.UNDERGROUND_STRUCTURES.ordinal() || n == GenerationStage.Decoration.SURFACE_STRUCTURES.ordinal()) continue;
                for (Supplier supplier : list.get(n)) {
                    builder.withFeature(n, supplier);
                }
            }
        }
        BlockState[] blockStateArray = this.getStates();
        for (n = 0; n < blockStateArray.length; ++n) {
            BlockState blockState = blockStateArray[n];
            if (blockState == null || Heightmap.Type.MOTION_BLOCKING.getHeightLimitPredicate().test(blockState)) continue;
            this.states[n] = null;
            builder.withFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Feature.FILL_LAYER.withConfiguration(new FillLayerConfig(n, blockState)));
        }
        return new Biome.Builder().precipitation(biome.getPrecipitation()).category(biome.getCategory()).depth(biome.getDepth()).scale(biome.getScale()).temperature(biome.getTemperature()).downfall(biome.getDownfall()).setEffects(biome.getAmbience()).withGenerationSettings(builder.build()).withMobSpawnSettings(biome.getMobSpawnInfo()).build();
    }

    public DimensionStructuresSettings func_236943_d_() {
        return this.field_236933_f_;
    }

    public Biome getBiome() {
        return this.biomeToUse.get();
    }

    public void func_242870_a(Supplier<Biome> supplier) {
        this.biomeToUse = supplier;
    }

    public List<FlatLayerInfo> getFlatLayers() {
        return this.flatLayers;
    }

    public BlockState[] getStates() {
        return this.states;
    }

    public void updateLayers() {
        Arrays.fill(this.states, 0, this.states.length, null);
        int n = 0;
        for (FlatLayerInfo flatLayerInfo : this.flatLayers) {
            flatLayerInfo.setMinY(n);
            n += flatLayerInfo.getLayerCount();
        }
        this.allAir = true;
        for (FlatLayerInfo flatLayerInfo : this.flatLayers) {
            for (int i = flatLayerInfo.getMinY(); i < flatLayerInfo.getMinY() + flatLayerInfo.getLayerCount(); ++i) {
                BlockState blockState = flatLayerInfo.getLayerMaterial();
                if (blockState.isIn(Blocks.AIR)) continue;
                this.allAir = false;
                this.states[i] = blockState;
            }
        }
    }

    public static FlatGenerationSettings func_242869_a(Registry<Biome> registry) {
        DimensionStructuresSettings dimensionStructuresSettings = new DimensionStructuresSettings(Optional.of(DimensionStructuresSettings.field_236192_c_), Maps.newHashMap(ImmutableMap.of(Structure.field_236381_q_, DimensionStructuresSettings.field_236191_b_.get(Structure.field_236381_q_))));
        FlatGenerationSettings flatGenerationSettings = new FlatGenerationSettings(dimensionStructuresSettings, registry);
        flatGenerationSettings.biomeToUse = () -> FlatGenerationSettings.lambda$func_242869_a$8(registry);
        flatGenerationSettings.getFlatLayers().add(new FlatLayerInfo(1, Blocks.BEDROCK));
        flatGenerationSettings.getFlatLayers().add(new FlatLayerInfo(2, Blocks.DIRT));
        flatGenerationSettings.getFlatLayers().add(new FlatLayerInfo(1, Blocks.GRASS_BLOCK));
        flatGenerationSettings.updateLayers();
        return flatGenerationSettings;
    }

    private static Biome lambda$func_242869_a$8(Registry registry) {
        return registry.getOrThrow(Biomes.PLAINS);
    }

    private static Biome lambda$new$7(Registry registry) {
        return registry.getOrThrow(Biomes.PLAINS);
    }

    private static Biome lambda$new$6(Registry registry) {
        return registry.getOrThrow(Biomes.PLAINS);
    }

    private static void lambda$static$5(HashMap hashMap) {
        hashMap.put(Structure.field_236367_c_, StructureFeatures.field_244136_b);
        hashMap.put(Structure.field_236381_q_, StructureFeatures.field_244154_t);
        hashMap.put(Structure.field_236375_k_, StructureFeatures.field_244145_k);
        hashMap.put(Structure.field_236374_j_, StructureFeatures.field_244144_j);
        hashMap.put(Structure.field_236370_f_, StructureFeatures.field_244140_f);
        hashMap.put(Structure.field_236369_e_, StructureFeatures.field_244139_e);
        hashMap.put(Structure.field_236371_g_, StructureFeatures.field_244141_g);
        hashMap.put(Structure.field_236377_m_, StructureFeatures.field_244147_m);
        hashMap.put(Structure.field_236373_i_, StructureFeatures.field_244142_h);
        hashMap.put(Structure.field_236376_l_, StructureFeatures.field_244146_l);
        hashMap.put(Structure.field_236379_o_, StructureFeatures.field_244151_q);
        hashMap.put(Structure.field_236368_d_, StructureFeatures.field_244138_d);
        hashMap.put(Structure.field_236378_n_, StructureFeatures.field_244149_o);
        hashMap.put(Structure.field_236366_b_, StructureFeatures.field_244135_a);
        hashMap.put(Structure.field_236372_h_, StructureFeatures.field_244159_y);
        hashMap.put(Structure.field_236383_s_, StructureFeatures.field_244153_s);
    }

    private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
        return instance.group(RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter(FlatGenerationSettings::lambda$static$0), ((MapCodec)DimensionStructuresSettings.field_236190_a_.fieldOf("structures")).forGetter(FlatGenerationSettings::func_236943_d_), ((MapCodec)FlatLayerInfo.field_236929_a_.listOf().fieldOf("layers")).forGetter(FlatGenerationSettings::getFlatLayers), ((MapCodec)Codec.BOOL.fieldOf("lakes")).orElse(false).forGetter(FlatGenerationSettings::lambda$static$1), ((MapCodec)Codec.BOOL.fieldOf("features")).orElse(false).forGetter(FlatGenerationSettings::lambda$static$2), Biome.BIOME_CODEC.optionalFieldOf("biome").orElseGet(Optional::empty).forGetter(FlatGenerationSettings::lambda$static$3)).apply(instance, FlatGenerationSettings::new);
    }

    private static Optional lambda$static$3(FlatGenerationSettings flatGenerationSettings) {
        return Optional.of(flatGenerationSettings.biomeToUse);
    }

    private static Boolean lambda$static$2(FlatGenerationSettings flatGenerationSettings) {
        return flatGenerationSettings.field_236934_k_;
    }

    private static Boolean lambda$static$1(FlatGenerationSettings flatGenerationSettings) {
        return flatGenerationSettings.field_236935_l_;
    }

    private static Registry lambda$static$0(FlatGenerationSettings flatGenerationSettings) {
        return flatGenerationSettings.field_242867_d;
    }
}

