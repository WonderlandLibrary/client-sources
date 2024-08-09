/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistryCodec;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleRegistry<T>
extends MutableRegistry<T> {
    protected static final Logger LOGGER0 = LogManager.getLogger();
    private final ObjectList<T> entryList = new ObjectArrayList<T>(256);
    private final Object2IntMap<T> entryIndexMap = new Object2IntOpenCustomHashMap<T>(Util.identityHashStrategy());
    private final BiMap<ResourceLocation, T> registryObjects;
    private final BiMap<RegistryKey<T>, T> keyToObjectMap;
    private final Map<T, Lifecycle> objectToLifecycleMap;
    private Lifecycle lifecycle;
    protected Object[] values;
    private int nextFreeId;

    public SimpleRegistry(RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle) {
        super(registryKey, lifecycle);
        this.entryIndexMap.defaultReturnValue(-1);
        this.registryObjects = HashBiMap.create();
        this.keyToObjectMap = HashBiMap.create();
        this.objectToLifecycleMap = Maps.newIdentityHashMap();
        this.lifecycle = lifecycle;
    }

    public static <T> MapCodec<Entry<T>> getRegistryEntryCodec(RegistryKey<? extends Registry<T>> registryKey, MapCodec<T> mapCodec) {
        return RecordCodecBuilder.mapCodec(arg_0 -> SimpleRegistry.lambda$getRegistryEntryCodec$3(registryKey, mapCodec, arg_0));
    }

    @Override
    public <V extends T> V register(int n, RegistryKey<T> registryKey, V v, Lifecycle lifecycle) {
        return this.register(n, registryKey, v, lifecycle, false);
    }

    private <V extends T> V register(int n, RegistryKey<T> registryKey, V v, Lifecycle lifecycle, boolean bl) {
        Validate.notNull(registryKey);
        Validate.notNull(v);
        this.entryList.size(Math.max(this.entryList.size(), n + 1));
        this.entryList.set(n, v);
        this.entryIndexMap.put((T)v, n);
        this.values = null;
        if (bl && this.keyToObjectMap.containsKey(registryKey)) {
            LOGGER0.debug("Adding duplicate key '{}' to registry", (Object)registryKey);
        }
        if (this.registryObjects.containsValue(v)) {
            LOGGER0.error("Adding duplicate value '{}' to registry", (Object)v);
        }
        this.registryObjects.put(registryKey.getLocation(), v);
        this.keyToObjectMap.put(registryKey, v);
        this.objectToLifecycleMap.put(v, lifecycle);
        this.lifecycle = this.lifecycle.add(lifecycle);
        if (this.nextFreeId <= n) {
            this.nextFreeId = n + 1;
        }
        return v;
    }

    @Override
    public <V extends T> V register(RegistryKey<T> registryKey, V v, Lifecycle lifecycle) {
        return this.register(this.nextFreeId, registryKey, v, lifecycle);
    }

    @Override
    public <V extends T> V validateAndRegister(OptionalInt optionalInt, RegistryKey<T> registryKey, V v, Lifecycle lifecycle) {
        int n;
        Validate.notNull(registryKey);
        Validate.notNull(v);
        Object v2 = this.keyToObjectMap.get(registryKey);
        if (v2 == null) {
            n = optionalInt.isPresent() ? optionalInt.getAsInt() : this.nextFreeId;
        } else {
            n = this.entryIndexMap.getInt(v2);
            if (optionalInt.isPresent() && optionalInt.getAsInt() != n) {
                throw new IllegalStateException("ID mismatch");
            }
            this.entryIndexMap.removeInt(v2);
            this.objectToLifecycleMap.remove(v2);
        }
        return this.register(n, registryKey, v, lifecycle, true);
    }

    @Override
    @Nullable
    public ResourceLocation getKey(T t) {
        return (ResourceLocation)this.registryObjects.inverse().get(t);
    }

    @Override
    public Optional<RegistryKey<T>> getOptionalKey(T t) {
        return Optional.ofNullable((RegistryKey)this.keyToObjectMap.inverse().get(t));
    }

    @Override
    public int getId(@Nullable T t) {
        return this.entryIndexMap.getInt(t);
    }

    @Override
    @Nullable
    public T getValueForKey(@Nullable RegistryKey<T> registryKey) {
        return (T)this.keyToObjectMap.get(registryKey);
    }

    @Override
    @Nullable
    public T getByValue(int n) {
        return n >= 0 && n < this.entryList.size() ? (T)this.entryList.get(n) : null;
    }

    @Override
    public Lifecycle getLifecycleByRegistry(T t) {
        return this.objectToLifecycleMap.get(t);
    }

    @Override
    public Lifecycle getLifecycle() {
        return this.lifecycle;
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.filter(this.entryList.iterator(), Objects::nonNull);
    }

    @Override
    @Nullable
    public T getOrDefault(@Nullable ResourceLocation resourceLocation) {
        return (T)this.registryObjects.get(resourceLocation);
    }

    @Override
    public Set<ResourceLocation> keySet() {
        return Collections.unmodifiableSet(this.registryObjects.keySet());
    }

    @Override
    public Set<Map.Entry<RegistryKey<T>, T>> getEntries() {
        return Collections.unmodifiableMap(this.keyToObjectMap).entrySet();
    }

    @Nullable
    public T getRandom(Random random2) {
        if (this.values == null) {
            Collection collection = this.registryObjects.values();
            if (collection.isEmpty()) {
                return null;
            }
            this.values = collection.toArray(new Object[collection.size()]);
        }
        return (T)Util.getRandomObject(this.values, random2);
    }

    @Override
    public boolean containsKey(ResourceLocation resourceLocation) {
        return this.registryObjects.containsKey(resourceLocation);
    }

    public static <T> Codec<SimpleRegistry<T>> createSimpleRegistryCodec(RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle, Codec<T> codec) {
        return SimpleRegistry.getRegistryEntryCodec(registryKey, codec.fieldOf("element")).codec().listOf().xmap(arg_0 -> SimpleRegistry.lambda$createSimpleRegistryCodec$4(registryKey, lifecycle, arg_0), SimpleRegistry::lambda$createSimpleRegistryCodec$5);
    }

    public static <T> Codec<SimpleRegistry<T>> getSimpleRegistryCodec(RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle, Codec<T> codec) {
        return SimpleRegistryCodec.create(registryKey, lifecycle, codec);
    }

    public static <T> Codec<SimpleRegistry<T>> getUnboundedRegistryCodec(RegistryKey<? extends Registry<T>> registryKey, Lifecycle lifecycle, Codec<T> codec) {
        return Codec.unboundedMap(ResourceLocation.CODEC.xmap(RegistryKey.getKeyCreator(registryKey), RegistryKey::getLocation), codec).xmap(arg_0 -> SimpleRegistry.lambda$getUnboundedRegistryCodec$7(registryKey, lifecycle, arg_0), SimpleRegistry::lambda$getUnboundedRegistryCodec$8);
    }

    private static Map lambda$getUnboundedRegistryCodec$8(SimpleRegistry simpleRegistry) {
        return ImmutableMap.copyOf(simpleRegistry.keyToObjectMap);
    }

    private static SimpleRegistry lambda$getUnboundedRegistryCodec$7(RegistryKey registryKey, Lifecycle lifecycle, Map map) {
        SimpleRegistry simpleRegistry = new SimpleRegistry(registryKey, lifecycle);
        map.forEach((arg_0, arg_1) -> SimpleRegistry.lambda$getUnboundedRegistryCodec$6(simpleRegistry, lifecycle, arg_0, arg_1));
        return simpleRegistry;
    }

    private static void lambda$getUnboundedRegistryCodec$6(SimpleRegistry simpleRegistry, Lifecycle lifecycle, RegistryKey registryKey, Object object) {
        simpleRegistry.register(registryKey, object, lifecycle);
    }

    private static List lambda$createSimpleRegistryCodec$5(SimpleRegistry simpleRegistry) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Object t : simpleRegistry) {
            builder.add(new Entry(simpleRegistry.getOptionalKey(t).get(), simpleRegistry.getId(t), t));
        }
        return builder.build();
    }

    private static SimpleRegistry lambda$createSimpleRegistryCodec$4(RegistryKey registryKey, Lifecycle lifecycle, List list) {
        SimpleRegistry simpleRegistry = new SimpleRegistry(registryKey, lifecycle);
        for (Entry entry : list) {
            simpleRegistry.register(entry.index, entry.name, entry.value, lifecycle);
        }
        return simpleRegistry;
    }

    private static App lambda$getRegistryEntryCodec$3(RegistryKey registryKey, MapCodec mapCodec, RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)ResourceLocation.CODEC.xmap(RegistryKey.getKeyCreator(registryKey), RegistryKey::getLocation).fieldOf("name")).forGetter(SimpleRegistry::lambda$getRegistryEntryCodec$0), ((MapCodec)Codec.INT.fieldOf("id")).forGetter(SimpleRegistry::lambda$getRegistryEntryCodec$1), mapCodec.forGetter(SimpleRegistry::lambda$getRegistryEntryCodec$2)).apply(instance, Entry::new);
    }

    private static Object lambda$getRegistryEntryCodec$2(Entry entry) {
        return entry.value;
    }

    private static Integer lambda$getRegistryEntryCodec$1(Entry entry) {
        return entry.index;
    }

    private static RegistryKey lambda$getRegistryEntryCodec$0(Entry entry) {
        return entry.name;
    }

    public static class Entry<T> {
        public final RegistryKey<T> name;
        public final int index;
        public final T value;

        public Entry(RegistryKey<T> registryKey, int n, T t) {
            this.name = registryKey;
            this.index = n;
            this.value = t;
        }
    }
}

