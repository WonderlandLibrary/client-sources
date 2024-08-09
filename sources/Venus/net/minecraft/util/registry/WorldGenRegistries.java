/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.google.common.collect.Maps;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenRegistries {
    protected static final Logger LOGGER = LogManager.getLogger();
    private static final Map<ResourceLocation, Supplier<?>> REGISTRY_NAME_TO_DEFAULT = Maps.newLinkedHashMap();
    private static final MutableRegistry<MutableRegistry<?>> INTERNAL_ROOT_REGISTRIES = new SimpleRegistry(RegistryKey.getOrCreateRootKey(new ResourceLocation("root")), Lifecycle.experimental());
    public static final Registry<? extends Registry<?>> ROOT_REGISTRIES = INTERNAL_ROOT_REGISTRIES;
    public static final Registry<ConfiguredSurfaceBuilder<?>> CONFIGURED_SURFACE_BUILDER = WorldGenRegistries.createRegistry(Registry.CONFIGURED_SURFACE_BUILDER_KEY, WorldGenRegistries::lambda$static$0);
    public static final Registry<ConfiguredCarver<?>> CONFIGURED_CARVER = WorldGenRegistries.createRegistry(Registry.CONFIGURED_CARVER_KEY, WorldGenRegistries::lambda$static$1);
    public static final Registry<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE = WorldGenRegistries.createRegistry(Registry.CONFIGURED_FEATURE_KEY, WorldGenRegistries::lambda$static$2);
    public static final Registry<StructureFeature<?, ?>> CONFIGURED_STRUCTURE_FEATURE = WorldGenRegistries.createRegistry(Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, WorldGenRegistries::lambda$static$3);
    public static final Registry<StructureProcessorList> STRUCTURE_PROCESSOR_LIST = WorldGenRegistries.createRegistry(Registry.STRUCTURE_PROCESSOR_LIST_KEY, WorldGenRegistries::lambda$static$4);
    public static final Registry<JigsawPattern> JIGSAW_POOL = WorldGenRegistries.createRegistry(Registry.JIGSAW_POOL_KEY, JigsawPatternRegistry::func_244093_a);
    public static final Registry<Biome> BIOME = WorldGenRegistries.createRegistry(Registry.BIOME_KEY, WorldGenRegistries::lambda$static$5);
    public static final Registry<DimensionSettings> NOISE_SETTINGS = WorldGenRegistries.createRegistry(Registry.NOISE_SETTINGS_KEY, DimensionSettings::func_242746_i);

    private static <T> Registry<T> createRegistry(RegistryKey<? extends Registry<T>> registryKey, Supplier<T> supplier) {
        return WorldGenRegistries.createRegistry(registryKey, Lifecycle.stable(), supplier);
    }

    private static <T> Registry<T> createRegistry(RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle, Supplier<T> supplier) {
        return WorldGenRegistries.createRegistry(registryKey, new SimpleRegistry(registryKey, lifecycle), supplier, lifecycle);
    }

    private static <T, R extends MutableRegistry<T>> R createRegistry(RegistryKey<? extends Registry<T>> registryKey, R r, Supplier<T> supplier, Lifecycle lifecycle) {
        ResourceLocation resourceLocation = registryKey.getLocation();
        REGISTRY_NAME_TO_DEFAULT.put(resourceLocation, supplier);
        MutableRegistry<MutableRegistry<?>> mutableRegistry = INTERNAL_ROOT_REGISTRIES;
        return mutableRegistry.register(registryKey, r, lifecycle);
    }

    public static <T> T register(Registry<? super T> registry, String string, T t) {
        return WorldGenRegistries.register(registry, new ResourceLocation(string), t);
    }

    public static <V, T extends V> T register(Registry<V> registry, ResourceLocation resourceLocation, T t) {
        return ((MutableRegistry)registry).register(RegistryKey.getOrCreateKey(registry.getRegistryKey(), resourceLocation), t, Lifecycle.stable());
    }

    public static <V, T extends V> T register(Registry<V> registry, int n, RegistryKey<V> registryKey, T t) {
        return ((MutableRegistry)registry).register(n, registryKey, t, Lifecycle.stable());
    }

    public static void init() {
    }

    private static void lambda$static$6(ResourceLocation resourceLocation, Supplier supplier) {
        if (supplier.get() == null) {
            LOGGER.error("Unable to bootstrap registry '{}'", (Object)resourceLocation);
        }
    }

    private static Biome lambda$static$5() {
        return BiomeRegistry.PLAINS;
    }

    private static StructureProcessorList lambda$static$4() {
        return ProcessorLists.field_244102_b;
    }

    private static StructureFeature lambda$static$3() {
        return StructureFeatures.field_244136_b;
    }

    private static ConfiguredFeature lambda$static$2() {
        return Features.OAK;
    }

    private static ConfiguredCarver lambda$static$1() {
        return ConfiguredCarvers.field_243767_a;
    }

    private static ConfiguredSurfaceBuilder lambda$static$0() {
        return ConfiguredSurfaceBuilders.field_244184_p;
    }

    static {
        REGISTRY_NAME_TO_DEFAULT.forEach(WorldGenRegistries::lambda$static$6);
        Registry.validateMutableRegistry(INTERNAL_ROOT_REGISTRIES);
    }
}

