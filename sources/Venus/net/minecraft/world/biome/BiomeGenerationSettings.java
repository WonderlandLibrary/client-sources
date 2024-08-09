/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Util;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeGenerationSettings {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final BiomeGenerationSettings DEFAULT_SETTINGS = new BiomeGenerationSettings(BiomeGenerationSettings::lambda$static$0, ImmutableMap.of(), ImmutableList.of(), ImmutableList.of());
    public static final MapCodec<BiomeGenerationSettings> CODEC = RecordCodecBuilder.mapCodec(BiomeGenerationSettings::lambda$static$5);
    private final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder;
    private final Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> carvers;
    private final List<List<Supplier<ConfiguredFeature<?, ?>>>> features;
    private final List<Supplier<StructureFeature<?, ?>>> structures;
    private final List<ConfiguredFeature<?, ?>> flowerFeatures;

    private BiomeGenerationSettings(Supplier<ConfiguredSurfaceBuilder<?>> supplier, Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> map, List<List<Supplier<ConfiguredFeature<?, ?>>>> list, List<Supplier<StructureFeature<?, ?>>> list2) {
        this.surfaceBuilder = supplier;
        this.carvers = map;
        this.features = list;
        this.structures = list2;
        this.flowerFeatures = list.stream().flatMap(Collection::stream).map(Supplier::get).flatMap(ConfiguredFeature::func_242768_d).filter(BiomeGenerationSettings::lambda$new$6).collect(ImmutableList.toImmutableList());
    }

    public List<Supplier<ConfiguredCarver<?>>> getCarvers(GenerationStage.Carving carving) {
        return this.carvers.getOrDefault(carving, ImmutableList.of());
    }

    public boolean hasStructure(Structure<?> structure) {
        return this.structures.stream().anyMatch(arg_0 -> BiomeGenerationSettings.lambda$hasStructure$7(structure, arg_0));
    }

    public Collection<Supplier<StructureFeature<?, ?>>> getStructures() {
        return this.structures;
    }

    public StructureFeature<?, ?> getStructure(StructureFeature<?, ?> structureFeature) {
        return DataFixUtils.orElse(this.structures.stream().map(Supplier::get).filter(arg_0 -> BiomeGenerationSettings.lambda$getStructure$8(structureFeature, arg_0)).findAny(), structureFeature);
    }

    public List<ConfiguredFeature<?, ?>> getFlowerFeatures() {
        return this.flowerFeatures;
    }

    public List<List<Supplier<ConfiguredFeature<?, ?>>>> getFeatures() {
        return this.features;
    }

    public Supplier<ConfiguredSurfaceBuilder<?>> getSurfaceBuilder() {
        return this.surfaceBuilder;
    }

    public ISurfaceBuilderConfig getSurfaceBuilderConfig() {
        return this.surfaceBuilder.get().getConfig();
    }

    private static boolean lambda$getStructure$8(StructureFeature structureFeature, StructureFeature structureFeature2) {
        return structureFeature2.field_236268_b_ == structureFeature.field_236268_b_;
    }

    private static boolean lambda$hasStructure$7(Structure structure, Supplier supplier) {
        return ((StructureFeature)supplier.get()).field_236268_b_ == structure;
    }

    private static boolean lambda$new$6(ConfiguredFeature configuredFeature) {
        return configuredFeature.feature == Feature.FLOWER;
    }

    private static App lambda$static$5(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)ConfiguredSurfaceBuilder.field_244393_b_.fieldOf("surface_builder")).forGetter(BiomeGenerationSettings::lambda$static$1), Codec.simpleMap(GenerationStage.Carving.field_236074_c_, ConfiguredCarver.field_242759_c.promotePartial((Consumer)Util.func_240982_a_("Carver: ", LOGGER::error)), IStringSerializable.createKeyable(GenerationStage.Carving.values())).fieldOf("carvers").forGetter(BiomeGenerationSettings::lambda$static$2), ((MapCodec)ConfiguredFeature.field_242764_c.promotePartial((Consumer)Util.func_240982_a_("Feature: ", LOGGER::error)).listOf().fieldOf("features")).forGetter(BiomeGenerationSettings::lambda$static$3), ((MapCodec)StructureFeature.field_242770_c.promotePartial((Consumer)Util.func_240982_a_("Structure start: ", LOGGER::error)).fieldOf("starts")).forGetter(BiomeGenerationSettings::lambda$static$4)).apply(instance, BiomeGenerationSettings::new);
    }

    private static List lambda$static$4(BiomeGenerationSettings biomeGenerationSettings) {
        return biomeGenerationSettings.structures;
    }

    private static List lambda$static$3(BiomeGenerationSettings biomeGenerationSettings) {
        return biomeGenerationSettings.features;
    }

    private static Map lambda$static$2(BiomeGenerationSettings biomeGenerationSettings) {
        return biomeGenerationSettings.carvers;
    }

    private static Supplier lambda$static$1(BiomeGenerationSettings biomeGenerationSettings) {
        return biomeGenerationSettings.surfaceBuilder;
    }

    private static ConfiguredSurfaceBuilder lambda$static$0() {
        return ConfiguredSurfaceBuilders.field_244184_p;
    }

    public static class Builder {
        private Optional<Supplier<ConfiguredSurfaceBuilder<?>>> surfaceBuilder = Optional.empty();
        private final Map<GenerationStage.Carving, List<Supplier<ConfiguredCarver<?>>>> carvers = Maps.newLinkedHashMap();
        private final List<List<Supplier<ConfiguredFeature<?, ?>>>> features = Lists.newArrayList();
        private final List<Supplier<StructureFeature<?, ?>>> structures = Lists.newArrayList();

        public Builder withSurfaceBuilder(ConfiguredSurfaceBuilder<?> configuredSurfaceBuilder) {
            return this.withSurfaceBuilder(() -> Builder.lambda$withSurfaceBuilder$0(configuredSurfaceBuilder));
        }

        public Builder withSurfaceBuilder(Supplier<ConfiguredSurfaceBuilder<?>> supplier) {
            this.surfaceBuilder = Optional.of(supplier);
            return this;
        }

        public Builder withFeature(GenerationStage.Decoration decoration, ConfiguredFeature<?, ?> configuredFeature) {
            return this.withFeature(decoration.ordinal(), () -> Builder.lambda$withFeature$1(configuredFeature));
        }

        public Builder withFeature(int n, Supplier<ConfiguredFeature<?, ?>> supplier) {
            this.populateStageEntries(n);
            this.features.get(n).add(supplier);
            return this;
        }

        public <C extends ICarverConfig> Builder withCarver(GenerationStage.Carving carving, ConfiguredCarver<C> configuredCarver) {
            this.carvers.computeIfAbsent(carving, Builder::lambda$withCarver$2).add(() -> Builder.lambda$withCarver$3(configuredCarver));
            return this;
        }

        public Builder withStructure(StructureFeature<?, ?> structureFeature) {
            this.structures.add(() -> Builder.lambda$withStructure$4(structureFeature));
            return this;
        }

        private void populateStageEntries(int n) {
            while (this.features.size() <= n) {
                this.features.add(Lists.newArrayList());
            }
        }

        public BiomeGenerationSettings build() {
            return new BiomeGenerationSettings(this.surfaceBuilder.orElseThrow(Builder::lambda$build$5), (Map)this.carvers.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Builder::lambda$build$6)), this.features.stream().map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList()), ImmutableList.copyOf(this.structures));
        }

        private static List lambda$build$6(Map.Entry entry) {
            return ImmutableList.copyOf((Collection)entry.getValue());
        }

        private static IllegalStateException lambda$build$5() {
            return new IllegalStateException("Missing surface builder");
        }

        private static StructureFeature lambda$withStructure$4(StructureFeature structureFeature) {
            return structureFeature;
        }

        private static ConfiguredCarver lambda$withCarver$3(ConfiguredCarver configuredCarver) {
            return configuredCarver;
        }

        private static List lambda$withCarver$2(GenerationStage.Carving carving) {
            return Lists.newArrayList();
        }

        private static ConfiguredFeature lambda$withFeature$1(ConfiguredFeature configuredFeature) {
            return configuredFeature;
        }

        private static ConfiguredSurfaceBuilder lambda$withSurfaceBuilder$0(ConfiguredSurfaceBuilder configuredSurfaceBuilder) {
            return configuredSurfaceBuilder;
        }
    }
}

