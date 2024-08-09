/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.util.Freezable;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Relation<K, V>
implements Freezable<Relation<K, V>> {
    private Map<K, Set<V>> data;
    Constructor<? extends Set<V>> setCreator;
    Object[] setComparatorParam;
    volatile boolean frozen = false;

    public static <K, V> Relation<K, V> of(Map<K, Set<V>> map, Class<?> clazz) {
        return new Relation<K, V>(map, clazz);
    }

    public static <K, V> Relation<K, V> of(Map<K, Set<V>> map, Class<?> clazz, Comparator<V> comparator) {
        return new Relation<K, V>(map, clazz, comparator);
    }

    public Relation(Map<K, Set<V>> map, Class<?> clazz) {
        this(map, clazz, null);
    }

    public Relation(Map<K, Set<V>> hashMap, Class<?> clazz, Comparator<V> comparator) {
        try {
            Object[] objectArray;
            if (comparator == null) {
                objectArray = null;
            } else {
                Object[] objectArray2 = new Object[1];
                objectArray = objectArray2;
                objectArray2[0] = comparator;
            }
            this.setComparatorParam = objectArray;
            if (comparator == null) {
                this.setCreator = clazz.getConstructor(new Class[0]);
                this.setCreator.newInstance(this.setComparatorParam);
            } else {
                this.setCreator = clazz.getConstructor(Comparator.class);
                this.setCreator.newInstance(this.setComparatorParam);
            }
            this.data = hashMap == null ? new HashMap() : hashMap;
        } catch (Exception exception) {
            throw (RuntimeException)new IllegalArgumentException("Can't create new set").initCause(exception);
        }
    }

    public void clear() {
        this.data.clear();
    }

    public boolean containsKey(Object object) {
        return this.data.containsKey(object);
    }

    public boolean containsValue(Object object) {
        for (Set<V> set : this.data.values()) {
            if (!set.contains(object)) continue;
            return false;
        }
        return true;
    }

    public final Set<Map.Entry<K, V>> entrySet() {
        return this.keyValueSet();
    }

    public Set<Map.Entry<K, Set<V>>> keyValuesSet() {
        return this.data.entrySet();
    }

    public Set<Map.Entry<K, V>> keyValueSet() {
        LinkedHashSet<Map.Entry<K, V>> linkedHashSet = new LinkedHashSet<Map.Entry<K, V>>();
        for (K k : this.data.keySet()) {
            for (V v : this.data.get(k)) {
                linkedHashSet.add(new SimpleEntry<K, V>(k, v));
            }
        }
        return linkedHashSet;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (object.getClass() != this.getClass()) {
            return true;
        }
        return this.data.equals(((Relation)object).data);
    }

    public Set<V> getAll(Object object) {
        return this.data.get(object);
    }

    public Set<V> get(Object object) {
        return this.data.get(object);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public Set<K> keySet() {
        return this.data.keySet();
    }

    public V put(K k, V v) {
        Set<V> set = this.data.get(k);
        if (set == null) {
            set = this.newSet();
            this.data.put(k, set);
        }
        set.add(v);
        return v;
    }

    public V putAll(K k, Collection<? extends V> collection) {
        Set<V> set = this.data.get(k);
        if (set == null) {
            set = this.newSet();
            this.data.put(k, set);
        }
        set.addAll(collection);
        return collection.size() == 0 ? null : (V)collection.iterator().next();
    }

    public V putAll(Collection<K> collection, V v) {
        V v2 = null;
        for (K k : collection) {
            v2 = this.put(k, v);
        }
        return v2;
    }

    private Set<V> newSet() {
        try {
            return this.setCreator.newInstance(this.setComparatorParam);
        } catch (Exception exception) {
            throw (RuntimeException)new IllegalArgumentException("Can't create new set").initCause(exception);
        }
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public void putAll(Relation<? extends K, ? extends V> relation) {
        for (K k : relation.keySet()) {
            for (V v : relation.getAll(k)) {
                this.put(k, v);
            }
        }
    }

    public Set<V> removeAll(K k) {
        try {
            return this.data.remove(k);
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    public boolean remove(K k, V v) {
        try {
            Set<V> set = this.data.get(k);
            if (set == null) {
                return false;
            }
            boolean bl = set.remove(v);
            if (set.size() == 0) {
                this.data.remove(k);
            }
            return bl;
        } catch (NullPointerException nullPointerException) {
            return true;
        }
    }

    public int size() {
        return this.data.size();
    }

    public Set<V> values() {
        return this.values(new LinkedHashSet());
    }

    public <C extends Collection<V>> C values(C c) {
        for (Map.Entry<K, Set<V>> entry : this.data.entrySet()) {
            c.addAll((Collection)entry.getValue());
        }
        return c;
    }

    public String toString() {
        return this.data.toString();
    }

    public Relation<K, V> addAllInverted(Relation<V, K> relation) {
        for (K k : relation.data.keySet()) {
            for (V v : relation.data.get(k)) {
                this.put(v, k);
            }
        }
        return this;
    }

    public Relation<K, V> addAllInverted(Map<V, K> map) {
        for (Map.Entry<V, K> entry : map.entrySet()) {
            this.put(entry.getValue(), entry.getKey());
        }
        return this;
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    @Override
    public Relation<K, V> freeze() {
        if (!this.frozen) {
            for (K k : this.data.keySet()) {
                this.data.put(k, Collections.unmodifiableSet(this.data.get(k)));
            }
            this.data = Collections.unmodifiableMap(this.data);
            this.frozen = true;
        }
        return this;
    }

    @Override
    public Relation<K, V> cloneAsThawed() {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Relation<K, V> relation) {
        boolean bl = false;
        for (K k : relation.keySet()) {
            try {
                Set<V> set = relation.getAll(k);
                if (set == null) continue;
                bl |= this.removeAll(k, (Iterable<V>)set);
            } catch (NullPointerException nullPointerException) {}
        }
        return bl;
    }

    @SafeVarargs
    public final Set<V> removeAll(K ... KArray) {
        return this.removeAll((Collection<K>)Arrays.asList(KArray));
    }

    public boolean removeAll(K k, Iterable<V> iterable) {
        boolean bl = false;
        for (V v : iterable) {
            bl |= this.remove(k, v);
        }
        return bl;
    }

    public Set<V> removeAll(Collection<K> collection) {
        LinkedHashSet<V> linkedHashSet = new LinkedHashSet<V>();
        for (K k : collection) {
            try {
                Set<V> set = this.data.remove(k);
                if (set == null) continue;
                linkedHashSet.addAll(set);
            } catch (NullPointerException nullPointerException) {}
        }
        return linkedHashSet;
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    static class SimpleEntry<K, V>
    implements Map.Entry<K, V> {
        K key;
        V value;

        public SimpleEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public SimpleEntry(Map.Entry<K, V> entry) {
            this.key = entry.getKey();
            this.value = entry.getValue();
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }
    }
}

