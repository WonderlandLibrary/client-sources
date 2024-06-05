package xyz.vladymyr.commons;

import java.util.*;
import xyz.vladymyr.commons.functions.*;
import java.util.function.*;

public final class Maps
{
    public static <K, V> Optional<V> find(final Map<K, V> map, final K key) {
        return Optional.ofNullable(map.get(key));
    }
    
    public static <K, V> void putAll(final Map<K, V> map, final Function<V, K> converter, final V... values) {
        for (final V value : values) {
            map.put(converter.apply(value), value);
        }
    }
    
    public static <K, V> Optional<V> findByType(final Map<K, V> map, final Class<V> valueCls) {
        for (final V value : map.values()) {
            if (value.getClass() == valueCls) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
    
    public static <K, V> Map<K, V> removeIfKey(final Map<K, V> map, final Predicate<K> filter) {
        return removeIf(map, filter, value -> false);
    }
    
    public static <K, V> Map<K, V> removeIfValue(final Map<K, V> map, final Predicate<V> filter) {
        return removeIf(map, key -> false, filter);
    }
    
    public static <K, V> Map<K, V> removeIf(final Map<K, V> map, final Predicate<K> keyFilter, final Predicate<V> valueFilter) {
        map.entrySet().removeIf(entry -> keyFilter.test(entry.getKey()) || valueFilter.test((V)entry.getValue()));
        return map;
    }
    
    public static <K, V> Map<K, V> computeifPresent(final Map<K, V> map, final K key, final TriConsumer<Map<K, V>, K, V> function) {
        if (map.containsKey(key)) {
            function.accept(map, key, map.get(key));
        }
        return map;
    }
    
    public static <K, V> Map<K, V> computeifAbsent(final Map<K, V> map, final K key, final BiConsumer<Map<K, V>, K> function) {
        if (!map.containsKey(key)) {
            function.accept(map, key);
        }
        return map;
    }
}
