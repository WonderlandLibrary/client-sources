/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Index<K, V> {
    private final Map<K, V> keyToValue;
    private final Map<V, K> valueToKey;

    private Index(Map<K, V> map, Map<V, K> map2) {
        this.keyToValue = map;
        this.valueToKey = map2;
    }

    @NotNull
    public static <K, V extends Enum<V>> Index<K, V> create(Class<V> clazz, @NotNull Function<? super V, ? extends K> function) {
        return Index.create(clazz, function, (Enum[])((Enum[])clazz.getEnumConstants()));
    }

    @SafeVarargs
    @NotNull
    public static <K, V extends Enum<V>> Index<K, V> create(Class<V> clazz, @NotNull Function<? super V, ? extends K> function, @NotNull @NotNull V @NotNull ... VArray) {
        return Index.create(VArray, arg_0 -> Index.lambda$create$0(clazz, arg_0), function);
    }

    @SafeVarargs
    @NotNull
    public static <K, V> Index<K, V> create(@NotNull Function<? super V, ? extends K> function, @NotNull @NotNull V @NotNull ... VArray) {
        return Index.create(VArray, HashMap::new, function);
    }

    @NotNull
    public static <K, V> Index<K, V> create(@NotNull Function<? super V, ? extends K> function, @NotNull List<V> list) {
        return Index.create(list, HashMap::new, function);
    }

    @NotNull
    private static <K, V> Index<K, V> create(V[] VArray, IntFunction<Map<V, K>> intFunction, @NotNull Function<? super V, ? extends K> function) {
        return Index.create(Arrays.asList(VArray), intFunction, function);
    }

    @NotNull
    private static <K, V> Index<K, V> create(List<V> list, IntFunction<Map<V, K>> intFunction, @NotNull Function<? super V, ? extends K> function) {
        int n = list.size();
        HashMap<K, V> hashMap = new HashMap<K, V>(n);
        Map<K, V> map = intFunction.apply(n);
        for (int i = 0; i < n; ++i) {
            V v = list.get(i);
            K k = function.apply(v);
            if (hashMap.putIfAbsent(k, v) != null) {
                throw new IllegalStateException(String.format("Key %s already mapped to value %s", k, hashMap.get(k)));
            }
            if (map.putIfAbsent(v, k) == null) continue;
            throw new IllegalStateException(String.format("Value %s already mapped to key %s", v, map.get(v)));
        }
        return new Index(Collections.unmodifiableMap(hashMap), Collections.unmodifiableMap(map));
    }

    @NotNull
    public Set<K> keys() {
        return Collections.unmodifiableSet(this.keyToValue.keySet());
    }

    @Nullable
    public K key(@NotNull V v) {
        return this.valueToKey.get(v);
    }

    @NotNull
    public K keyOrThrow(@NotNull V v) {
        K k = this.key(v);
        if (k == null) {
            throw new NoSuchElementException("There is no key for value " + v);
        }
        return k;
    }

    @Contract(value="_, null -> null; _, !null -> !null")
    public K keyOr(@NotNull V v, @Nullable K k) {
        K k2 = this.key(v);
        return k2 == null ? k : k2;
    }

    @NotNull
    public Set<V> values() {
        return Collections.unmodifiableSet(this.valueToKey.keySet());
    }

    @Nullable
    public V value(@NotNull K k) {
        return this.keyToValue.get(k);
    }

    @NotNull
    public V valueOrThrow(@NotNull K k) {
        V v = this.value(k);
        if (v == null) {
            throw new NoSuchElementException("There is no value for key " + k);
        }
        return v;
    }

    @Contract(value="_, null -> null; _, !null -> !null")
    public V valueOr(@NotNull K k, @Nullable V v) {
        V v2 = this.value(k);
        return v2 == null ? v : v2;
    }

    @NotNull
    public Map<K, V> keyToValue() {
        return Collections.unmodifiableMap(this.keyToValue);
    }

    @NotNull
    public Map<V, K> valueToKey() {
        return Collections.unmodifiableMap(this.valueToKey);
    }

    private static Map lambda$create$0(Class clazz, int n) {
        return new EnumMap(clazz);
    }
}

