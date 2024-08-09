/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.util.registry.WorldSettingsImport;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DynamicRegistries {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<RegistryKey<? extends Registry<?>>, CodecHolder<?>> registryCodecMap = Util.make(DynamicRegistries::lambda$static$0);
    private static final Impl registries = Util.make(DynamicRegistries::lambda$static$3);

    public abstract <E> Optional<MutableRegistry<E>> func_230521_a_(RegistryKey<? extends Registry<E>> var1);

    public <E> MutableRegistry<E> getRegistry(RegistryKey<? extends Registry<E>> registryKey) {
        return this.func_230521_a_(registryKey).orElseThrow(() -> DynamicRegistries.lambda$getRegistry$4(registryKey));
    }

    public Registry<DimensionType> func_230520_a_() {
        return this.getRegistry(Registry.DIMENSION_TYPE_KEY);
    }

    private static <E> void put(ImmutableMap.Builder<RegistryKey<? extends Registry<?>>, CodecHolder<?>> builder, RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec) {
        builder.put(registryKey, new CodecHolder<E>(registryKey, codec, null));
    }

    private static <E> void put(ImmutableMap.Builder<RegistryKey<? extends Registry<?>>, CodecHolder<?>> builder, RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec, Codec<E> codec2) {
        builder.put(registryKey, new CodecHolder<E>(registryKey, codec, codec2));
    }

    public static Impl func_239770_b_() {
        Impl impl = new Impl();
        WorldSettingsImport.IResourceAccess.RegistryAccess registryAccess = new WorldSettingsImport.IResourceAccess.RegistryAccess();
        for (CodecHolder<?> codecHolder : registryCodecMap.values()) {
            DynamicRegistries.registerRegistry(impl, registryAccess, codecHolder);
        }
        WorldSettingsImport.create(JsonOps.INSTANCE, registryAccess, impl);
        return impl;
    }

    private static <E> void registerRegistry(Impl impl, WorldSettingsImport.IResourceAccess.RegistryAccess registryAccess, CodecHolder<E> codecHolder) {
        RegistryKey<Registry<E>> registryKey = codecHolder.getRegistryKey();
        boolean bl = !registryKey.equals(Registry.NOISE_SETTINGS_KEY) && !registryKey.equals(Registry.DIMENSION_TYPE_KEY);
        MutableRegistry<E> mutableRegistry = registries.getRegistry(registryKey);
        MutableRegistry<E> mutableRegistry2 = impl.getRegistry(registryKey);
        for (Map.Entry entry : mutableRegistry.getEntries()) {
            Object t = entry.getValue();
            if (bl) {
                registryAccess.encode(registries, entry.getKey(), codecHolder.getRegistryCodec(), mutableRegistry.getId(t), t, mutableRegistry.getLifecycleByRegistry(t));
                continue;
            }
            mutableRegistry2.register(mutableRegistry.getId(t), entry.getKey(), t, mutableRegistry.getLifecycleByRegistry(t));
        }
    }

    private static <R extends Registry<?>> void getWorldGenRegistry(Impl impl, RegistryKey<R> registryKey) {
        Registry<Registry<?>> registry = WorldGenRegistries.ROOT_REGISTRIES;
        Registry<?> registry2 = registry.getValueForKey(registryKey);
        if (registry2 == null) {
            throw new IllegalStateException("Missing builtin registry: " + registryKey);
        }
        DynamicRegistries.registerRegistry(impl, registry2);
    }

    private static <E> void registerRegistry(Impl impl, Registry<E> registry) {
        MutableRegistry<E> mutableRegistry = impl.func_230521_a_(registry.getRegistryKey()).orElseThrow(() -> DynamicRegistries.lambda$registerRegistry$5(registry));
        for (Map.Entry<RegistryKey<E>, E> entry : registry.getEntries()) {
            E e = entry.getValue();
            mutableRegistry.register(registry.getId(e), entry.getKey(), e, registry.getLifecycleByRegistry(e));
        }
    }

    public static void loadRegistryData(Impl impl, WorldSettingsImport<?> worldSettingsImport) {
        for (CodecHolder<?> codecHolder : registryCodecMap.values()) {
            DynamicRegistries.loadRegistryData(worldSettingsImport, impl, codecHolder);
        }
    }

    private static <E> void loadRegistryData(WorldSettingsImport<?> worldSettingsImport, Impl impl, CodecHolder<E> codecHolder) {
        RegistryKey<Registry<E>> registryKey = codecHolder.getRegistryKey();
        SimpleRegistry simpleRegistry = Optional.ofNullable(impl.keyToSimpleRegistryMap.get(registryKey)).map(DynamicRegistries::lambda$loadRegistryData$6).orElseThrow(() -> DynamicRegistries.lambda$loadRegistryData$7(registryKey));
        DataResult<SimpleRegistry<E>> dataResult = worldSettingsImport.decode(simpleRegistry, codecHolder.getRegistryKey(), codecHolder.getRegistryCodec());
        dataResult.error().ifPresent(DynamicRegistries::lambda$loadRegistryData$8);
    }

    private static void lambda$loadRegistryData$8(DataResult.PartialResult partialResult) {
        LOGGER.error("Error loading registry data: {}", (Object)partialResult.message());
    }

    private static IllegalStateException lambda$loadRegistryData$7(RegistryKey registryKey) {
        return new IllegalStateException("Missing registry: " + registryKey);
    }

    private static SimpleRegistry lambda$loadRegistryData$6(SimpleRegistry simpleRegistry) {
        return simpleRegistry;
    }

    private static IllegalStateException lambda$registerRegistry$5(Registry registry) {
        return new IllegalStateException("Missing registry: " + registry.getRegistryKey());
    }

    private static IllegalStateException lambda$getRegistry$4(RegistryKey registryKey) {
        return new IllegalStateException("Missing registry: " + registryKey);
    }

    private static Impl lambda$static$3() {
        Impl impl = new Impl();
        DimensionType.registerTypes(impl);
        registryCodecMap.keySet().stream().filter(DynamicRegistries::lambda$static$1).forEach(arg_0 -> DynamicRegistries.lambda$static$2(impl, arg_0));
        return impl;
    }

    private static void lambda$static$2(Impl impl, RegistryKey registryKey) {
        DynamicRegistries.getWorldGenRegistry(impl, registryKey);
    }

    private static boolean lambda$static$1(RegistryKey registryKey) {
        return !registryKey.equals(Registry.DIMENSION_TYPE_KEY);
    }

    private static ImmutableMap lambda$static$0() {
        ImmutableMap.Builder<RegistryKey<Registry<?>>, CodecHolder<?>> builder = ImmutableMap.builder();
        DynamicRegistries.put(builder, Registry.DIMENSION_TYPE_KEY, DimensionType.CODEC, DimensionType.CODEC);
        DynamicRegistries.put(builder, Registry.BIOME_KEY, Biome.CODEC, Biome.PACKET_CODEC);
        DynamicRegistries.put(builder, Registry.CONFIGURED_SURFACE_BUILDER_KEY, ConfiguredSurfaceBuilder.field_237168_a_);
        DynamicRegistries.put(builder, Registry.CONFIGURED_CARVER_KEY, ConfiguredCarver.field_236235_a_);
        DynamicRegistries.put(builder, Registry.CONFIGURED_FEATURE_KEY, ConfiguredFeature.field_242763_a);
        DynamicRegistries.put(builder, Registry.CONFIGURED_STRUCTURE_FEATURE_KEY, StructureFeature.field_236267_a_);
        DynamicRegistries.put(builder, Registry.STRUCTURE_PROCESSOR_LIST_KEY, IStructureProcessorType.field_242921_l);
        DynamicRegistries.put(builder, Registry.JIGSAW_POOL_KEY, JigsawPattern.field_236852_a_);
        DynamicRegistries.put(builder, Registry.NOISE_SETTINGS_KEY, DimensionSettings.field_236097_a_);
        return builder.build();
    }

    static final class CodecHolder<E> {
        private final RegistryKey<? extends Registry<E>> registryKey;
        private final Codec<E> registryCodec;
        @Nullable
        private final Codec<E> packetCodec;

        public CodecHolder(RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec, @Nullable Codec<E> codec2) {
            this.registryKey = registryKey;
            this.registryCodec = codec;
            this.packetCodec = codec2;
        }

        public RegistryKey<? extends Registry<E>> getRegistryKey() {
            return this.registryKey;
        }

        public Codec<E> getRegistryCodec() {
            return this.registryCodec;
        }

        @Nullable
        public Codec<E> getPacketCodec() {
            return this.packetCodec;
        }

        public boolean hasPacketCodec() {
            return this.packetCodec != null;
        }
    }

    public static final class Impl
    extends DynamicRegistries {
        public static final Codec<Impl> registryCodec = Impl.getCodec();
        private final Map<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> keyToSimpleRegistryMap;

        private static <E> Codec<Impl> getCodec() {
            Codec<RegistryKey> codec = ResourceLocation.CODEC.xmap(RegistryKey::getOrCreateRootKey, RegistryKey::getLocation);
            Codec<SimpleRegistry> codec2 = codec.partialDispatch("type", Impl::lambda$getCodec$0, Impl::lambda$getCodec$2);
            UnboundedMapCodec<RegistryKey, SimpleRegistry> unboundedMapCodec = Codec.unboundedMap(codec, codec2);
            return Impl.getSerializableCodec(unboundedMapCodec);
        }

        private static <K extends RegistryKey<? extends Registry<?>>, V extends SimpleRegistry<?>> Codec<Impl> getSerializableCodec(UnboundedMapCodec<K, V> unboundedMapCodec) {
            return unboundedMapCodec.xmap(Impl::new, Impl::lambda$getSerializableCodec$4);
        }

        private static <E> DataResult<? extends Codec<E>> serializeRegistry(RegistryKey<? extends Registry<E>> registryKey) {
            return Optional.ofNullable(registryCodecMap.get(registryKey)).map(Impl::lambda$serializeRegistry$5).map(DataResult::success).orElseGet(() -> Impl.lambda$serializeRegistry$6(registryKey));
        }

        public Impl() {
            this(registryCodecMap.keySet().stream().collect(Collectors.toMap(Function.identity(), Impl::createStableRegistry)));
        }

        private Impl(Map<? extends RegistryKey<? extends Registry<?>>, ? extends SimpleRegistry<?>> map) {
            this.keyToSimpleRegistryMap = map;
        }

        private static <E> SimpleRegistry<?> createStableRegistry(RegistryKey<? extends Registry<?>> registryKey) {
            return new SimpleRegistry(registryKey, Lifecycle.stable());
        }

        @Override
        public <E> Optional<MutableRegistry<E>> func_230521_a_(RegistryKey<? extends Registry<E>> registryKey) {
            return Optional.ofNullable((MutableRegistry)this.keyToSimpleRegistryMap.get(registryKey)).map(Impl::lambda$func_230521_a_$7);
        }

        private static MutableRegistry lambda$func_230521_a_$7(MutableRegistry mutableRegistry) {
            return mutableRegistry;
        }

        private static DataResult lambda$serializeRegistry$6(RegistryKey registryKey) {
            return DataResult.error("Unknown or not serializable registry: " + registryKey);
        }

        private static Codec lambda$serializeRegistry$5(CodecHolder codecHolder) {
            return codecHolder.getPacketCodec();
        }

        private static Map lambda$getSerializableCodec$4(Impl impl) {
            return impl.keyToSimpleRegistryMap.entrySet().stream().filter(Impl::lambda$getSerializableCodec$3).collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        private static boolean lambda$getSerializableCodec$3(Map.Entry entry) {
            return registryCodecMap.get(entry.getKey()).hasPacketCodec();
        }

        private static DataResult lambda$getCodec$2(RegistryKey registryKey) {
            return Impl.serializeRegistry(registryKey).map(arg_0 -> Impl.lambda$getCodec$1(registryKey, arg_0));
        }

        private static Codec lambda$getCodec$1(RegistryKey registryKey, Codec codec) {
            return SimpleRegistry.createSimpleRegistryCodec(registryKey, Lifecycle.experimental(), codec);
        }

        private static DataResult lambda$getCodec$0(SimpleRegistry simpleRegistry) {
            return DataResult.success(simpleRegistry.getRegistryKey());
        }
    }
}

