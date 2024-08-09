/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DelegatingDynamicOps;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.util.registry.WorldGenSettingsExport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldSettingsImport<T>
extends DelegatingDynamicOps<T> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final IResourceAccess resourceAccess;
    private final DynamicRegistries.Impl dynamicRegistries;
    private final Map<RegistryKey<? extends Registry<?>>, ResultMap<?>> registryToResultMap;
    private final WorldSettingsImport<JsonElement> jsonOps;

    public static <T> WorldSettingsImport<T> create(DynamicOps<T> dynamicOps, IResourceManager iResourceManager, DynamicRegistries.Impl impl) {
        return WorldSettingsImport.create(dynamicOps, IResourceAccess.create(iResourceManager), impl);
    }

    public static <T> WorldSettingsImport<T> create(DynamicOps<T> dynamicOps, IResourceAccess iResourceAccess, DynamicRegistries.Impl impl) {
        WorldSettingsImport<T> worldSettingsImport = new WorldSettingsImport<T>(dynamicOps, iResourceAccess, impl, Maps.newIdentityHashMap());
        DynamicRegistries.loadRegistryData(impl, worldSettingsImport);
        return worldSettingsImport;
    }

    private WorldSettingsImport(DynamicOps<T> dynamicOps, IResourceAccess iResourceAccess, DynamicRegistries.Impl impl, IdentityHashMap<RegistryKey<? extends Registry<?>>, ResultMap<?>> identityHashMap) {
        super(dynamicOps);
        this.resourceAccess = iResourceAccess;
        this.dynamicRegistries = impl;
        this.registryToResultMap = identityHashMap;
        this.jsonOps = dynamicOps == JsonOps.INSTANCE ? this : new WorldSettingsImport<JsonElement>(JsonOps.INSTANCE, iResourceAccess, impl, identityHashMap);
    }

    protected <E> DataResult<Pair<Supplier<E>, T>> decode(T t, RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec, boolean bl) {
        Optional optional = this.dynamicRegistries.func_230521_a_(registryKey);
        if (!optional.isPresent()) {
            return DataResult.error("Unknown registry: " + registryKey);
        }
        MutableRegistry mutableRegistry = optional.get();
        DataResult dataResult = ResourceLocation.CODEC.decode(this.ops, t);
        if (!dataResult.result().isPresent()) {
            return !bl ? DataResult.error("Inline definitions not allowed here") : codec.decode(this, t).map(WorldSettingsImport::lambda$decode$2);
        }
        Pair pair = dataResult.result().get();
        ResourceLocation resourceLocation = (ResourceLocation)pair.getFirst();
        return this.createRegistry(registryKey, mutableRegistry, codec, resourceLocation).map(arg_0 -> WorldSettingsImport.lambda$decode$3(pair, arg_0));
    }

    public <E> DataResult<SimpleRegistry<E>> decode(SimpleRegistry<E> simpleRegistry, RegistryKey<? extends Registry<E>> registryKey, Codec<E> codec) {
        Collection<ResourceLocation> collection = this.resourceAccess.getRegistryObjects(registryKey);
        DataResult<SimpleRegistry<Object>> dataResult = DataResult.success(simpleRegistry, Lifecycle.stable());
        String string = registryKey.getLocation().getPath() + "/";
        for (ResourceLocation resourceLocation : collection) {
            String string2 = resourceLocation.getPath();
            if (!string2.endsWith(".json")) {
                LOGGER.warn("Skipping resource {} since it is not a json file", (Object)resourceLocation);
                continue;
            }
            if (!string2.startsWith(string)) {
                LOGGER.warn("Skipping resource {} since it does not have a registry name prefix", (Object)resourceLocation);
                continue;
            }
            String string3 = string2.substring(string.length(), string2.length() - 5);
            ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), string3);
            dataResult = dataResult.flatMap(arg_0 -> this.lambda$decode$5(registryKey, codec, resourceLocation2, arg_0));
        }
        return dataResult.setPartial(simpleRegistry);
    }

    private <E> DataResult<Supplier<E>> createRegistry(RegistryKey<? extends Registry<E>> registryKey, MutableRegistry<E> mutableRegistry, Codec<E> codec, ResourceLocation resourceLocation) {
        App<Pair.Mu<Object>, OptionalInt> app;
        RegistryKey registryKey2 = RegistryKey.getOrCreateKey(registryKey, resourceLocation);
        ResultMap<E> resultMap = this.getResultMap(registryKey);
        DataResult dataResult = resultMap.resultMap.get(registryKey2);
        if (dataResult != null) {
            return dataResult;
        }
        com.google.common.base.Supplier<Object> supplier = Suppliers.memoize(() -> WorldSettingsImport.lambda$createRegistry$6(mutableRegistry, registryKey2));
        resultMap.resultMap.put(registryKey2, DataResult.success(supplier));
        DataResult<Pair<Supplier, OptionalInt>> dataResult2 = this.resourceAccess.decode(this.jsonOps, registryKey, registryKey2, codec);
        Optional optional = dataResult2.result();
        if (optional.isPresent()) {
            app = optional.get();
            mutableRegistry.validateAndRegister((OptionalInt)((Pair)app).getSecond(), registryKey2, ((Pair)app).getFirst(), dataResult2.lifecycle());
        }
        app = !optional.isPresent() && mutableRegistry.getValueForKey(registryKey2) != null ? DataResult.success(() -> WorldSettingsImport.lambda$createRegistry$7(mutableRegistry, registryKey2), Lifecycle.stable()) : dataResult2.map(arg_0 -> WorldSettingsImport.lambda$createRegistry$9(mutableRegistry, registryKey2, arg_0));
        resultMap.resultMap.put(registryKey2, app);
        return app;
    }

    private <E> ResultMap<E> getResultMap(RegistryKey<? extends Registry<E>> registryKey) {
        return this.registryToResultMap.computeIfAbsent(registryKey, WorldSettingsImport::lambda$getResultMap$10);
    }

    protected <E> DataResult<Registry<E>> getRegistryByKey(RegistryKey<? extends Registry<E>> registryKey) {
        return this.dynamicRegistries.func_230521_a_(registryKey).map(WorldSettingsImport::lambda$getRegistryByKey$11).orElseGet(() -> WorldSettingsImport.lambda$getRegistryByKey$12(registryKey));
    }

    private static DataResult lambda$getRegistryByKey$12(RegistryKey registryKey) {
        return DataResult.error("Unknown registry: " + registryKey);
    }

    private static DataResult lambda$getRegistryByKey$11(MutableRegistry mutableRegistry) {
        return DataResult.success(mutableRegistry, mutableRegistry.getLifecycle());
    }

    private static ResultMap lambda$getResultMap$10(RegistryKey registryKey) {
        return new ResultMap();
    }

    private static Supplier lambda$createRegistry$9(MutableRegistry mutableRegistry, RegistryKey registryKey, Pair pair) {
        return () -> WorldSettingsImport.lambda$createRegistry$8(mutableRegistry, registryKey);
    }

    private static Object lambda$createRegistry$8(MutableRegistry mutableRegistry, RegistryKey registryKey) {
        return mutableRegistry.getValueForKey(registryKey);
    }

    private static Object lambda$createRegistry$7(MutableRegistry mutableRegistry, RegistryKey registryKey) {
        return mutableRegistry.getValueForKey(registryKey);
    }

    private static Object lambda$createRegistry$6(MutableRegistry mutableRegistry, RegistryKey registryKey) {
        Object t = mutableRegistry.getValueForKey(registryKey);
        if (t == null) {
            throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + registryKey);
        }
        return t;
    }

    private DataResult lambda$decode$5(RegistryKey registryKey, Codec codec, ResourceLocation resourceLocation, SimpleRegistry simpleRegistry) {
        return this.createRegistry(registryKey, simpleRegistry, codec, resourceLocation).map(arg_0 -> WorldSettingsImport.lambda$decode$4(simpleRegistry, arg_0));
    }

    private static SimpleRegistry lambda$decode$4(SimpleRegistry simpleRegistry, Supplier supplier) {
        return simpleRegistry;
    }

    private static Pair lambda$decode$3(Pair pair, Supplier supplier) {
        return Pair.of(supplier, pair.getSecond());
    }

    private static Pair lambda$decode$2(Pair pair) {
        return pair.mapFirst(WorldSettingsImport::lambda$decode$1);
    }

    private static Supplier lambda$decode$1(Object object) {
        return () -> WorldSettingsImport.lambda$decode$0(object);
    }

    private static Object lambda$decode$0(Object object) {
        return object;
    }

    public static interface IResourceAccess {
        public Collection<ResourceLocation> getRegistryObjects(RegistryKey<? extends Registry<?>> var1);

        public <E> DataResult<Pair<E, OptionalInt>> decode(DynamicOps<JsonElement> var1, RegistryKey<? extends Registry<E>> var2, RegistryKey<E> var3, Decoder<E> var4);

        public static IResourceAccess create(IResourceManager iResourceManager) {
            return new IResourceAccess(iResourceManager){
                final IResourceManager val$manager;
                {
                    this.val$manager = iResourceManager;
                }

                @Override
                public Collection<ResourceLocation> getRegistryObjects(RegistryKey<? extends Registry<?>> registryKey) {
                    return this.val$manager.getAllResourceLocations(registryKey.getLocation().getPath(), 1::lambda$getRegistryObjects$0);
                }

                /*
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public <E> DataResult<Pair<E, OptionalInt>> decode(DynamicOps<JsonElement> dynamicOps, RegistryKey<? extends Registry<E>> registryKey, RegistryKey<E> registryKey2, Decoder<E> decoder) {
                    ResourceLocation resourceLocation = registryKey2.getLocation();
                    ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), registryKey.getLocation().getPath() + "/" + resourceLocation.getPath() + ".json");
                    try (IResource iResource = this.val$manager.getResource(resourceLocation2);){
                        DataResult<Pair<E, OptionalInt>> dataResult;
                        try (InputStreamReader inputStreamReader = new InputStreamReader(iResource.getInputStream(), StandardCharsets.UTF_8);){
                            JsonParser jsonParser = new JsonParser();
                            JsonElement jsonElement = jsonParser.parse(inputStreamReader);
                            dataResult = decoder.parse(dynamicOps, jsonElement).map(1::lambda$decode$1);
                        }
                        return dataResult;
                    } catch (JsonIOException | JsonSyntaxException | IOException exception) {
                        return DataResult.error("Failed to parse " + resourceLocation2 + " file: " + exception.getMessage());
                    }
                }

                public String toString() {
                    return "ResourceAccess[" + this.val$manager + "]";
                }

                private static Pair lambda$decode$1(Object object) {
                    return Pair.of(object, OptionalInt.empty());
                }

                private static boolean lambda$getRegistryObjects$0(String string) {
                    return string.endsWith(".json");
                }
            };
        }

        public static final class RegistryAccess
        implements IResourceAccess {
            private final Map<RegistryKey<?>, JsonElement> keyToElementMap = Maps.newIdentityHashMap();
            private final Object2IntMap<RegistryKey<?>> keyToIDMap = new Object2IntOpenCustomHashMap(Util.identityHashStrategy());
            private final Map<RegistryKey<?>, Lifecycle> keyToLifecycleMap = Maps.newIdentityHashMap();

            public <E> void encode(DynamicRegistries.Impl impl, RegistryKey<E> registryKey, Encoder<E> encoder, int n, E e, Lifecycle lifecycle) {
                DataResult<JsonElement> dataResult = encoder.encodeStart(WorldGenSettingsExport.create(JsonOps.INSTANCE, impl), e);
                Optional<DataResult.PartialResult<JsonElement>> optional = dataResult.error();
                if (optional.isPresent()) {
                    LOGGER.error("Error adding element: {}", (Object)optional.get().message());
                } else {
                    this.keyToElementMap.put(registryKey, dataResult.result().get());
                    this.keyToIDMap.put((RegistryKey<?>)registryKey, n);
                    this.keyToLifecycleMap.put(registryKey, lifecycle);
                }
            }

            @Override
            public Collection<ResourceLocation> getRegistryObjects(RegistryKey<? extends Registry<?>> registryKey) {
                return this.keyToElementMap.keySet().stream().filter(arg_0 -> RegistryAccess.lambda$getRegistryObjects$0(registryKey, arg_0)).map(arg_0 -> RegistryAccess.lambda$getRegistryObjects$1(registryKey, arg_0)).collect(Collectors.toList());
            }

            @Override
            public <E> DataResult<Pair<E, OptionalInt>> decode(DynamicOps<JsonElement> dynamicOps, RegistryKey<? extends Registry<E>> registryKey, RegistryKey<E> registryKey2, Decoder<E> decoder) {
                JsonElement jsonElement = this.keyToElementMap.get(registryKey2);
                return jsonElement == null ? DataResult.error("Unknown element: " + registryKey2) : decoder.parse(dynamicOps, jsonElement).setLifecycle(this.keyToLifecycleMap.get(registryKey2)).map(arg_0 -> this.lambda$decode$2(registryKey2, arg_0));
            }

            private Pair lambda$decode$2(RegistryKey registryKey, Object object) {
                return Pair.of(object, OptionalInt.of(this.keyToIDMap.getInt(registryKey)));
            }

            private static ResourceLocation lambda$getRegistryObjects$1(RegistryKey registryKey, RegistryKey registryKey2) {
                return new ResourceLocation(registryKey2.getLocation().getNamespace(), registryKey.getLocation().getPath() + "/" + registryKey2.getLocation().getPath() + ".json");
            }

            private static boolean lambda$getRegistryObjects$0(RegistryKey registryKey, RegistryKey registryKey2) {
                return registryKey2.isParent(registryKey);
            }
        }
    }

    static final class ResultMap<E> {
        private final Map<RegistryKey<E>, DataResult<Supplier<E>>> resultMap = Maps.newIdentityHashMap();

        private ResultMap() {
        }
    }
}

