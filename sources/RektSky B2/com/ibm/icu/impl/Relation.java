package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import java.lang.reflect.*;
import java.util.*;

public class Relation<K, V> implements Freezable<Relation<K, V>>
{
    private Map<K, Set<V>> data;
    Constructor<? extends Set<V>> setCreator;
    Object[] setComparatorParam;
    volatile boolean frozen;
    
    public static <K, V> Relation<K, V> of(final Map<K, Set<V>> map, final Class<?> setCreator) {
        return new Relation<K, V>(map, setCreator);
    }
    
    public static <K, V> Relation<K, V> of(final Map<K, Set<V>> map, final Class<?> setCreator, final Comparator<V> setComparator) {
        return new Relation<K, V>(map, setCreator, setComparator);
    }
    
    public Relation(final Map<K, Set<V>> map, final Class<?> setCreator) {
        this(map, setCreator, null);
    }
    
    public Relation(final Map<K, Set<V>> map, final Class<?> setCreator, final Comparator<V> setComparator) {
        this.frozen = false;
        try {
            this.setComparatorParam = (Object[])((setComparator == null) ? null : new Object[] { setComparator });
            if (setComparator == null) {
                (this.setCreator = (Constructor<? extends Set<V>>)setCreator.getConstructor((Class<?>[])new Class[0])).newInstance(this.setComparatorParam);
            }
            else {
                (this.setCreator = (Constructor<? extends Set<V>>)setCreator.getConstructor(Comparator.class)).newInstance(this.setComparatorParam);
            }
            this.data = ((map == null) ? new HashMap<K, Set<V>>() : map);
        }
        catch (Exception e) {
            throw (RuntimeException)new IllegalArgumentException("Can't create new set").initCause(e);
        }
    }
    
    public void clear() {
        this.data.clear();
    }
    
    public boolean containsKey(final Object key) {
        return this.data.containsKey(key);
    }
    
    public boolean containsValue(final Object value) {
        for (final Set<V> values : this.data.values()) {
            if (values.contains(value)) {
                return true;
            }
        }
        return false;
    }
    
    public final Set<Map.Entry<K, V>> entrySet() {
        return this.keyValueSet();
    }
    
    public Set<Map.Entry<K, Set<V>>> keyValuesSet() {
        return this.data.entrySet();
    }
    
    public Set<Map.Entry<K, V>> keyValueSet() {
        final Set<Map.Entry<K, V>> result = new LinkedHashSet<Map.Entry<K, V>>();
        for (final K key : this.data.keySet()) {
            for (final V value : this.data.get(key)) {
                result.add(new SimpleEntry<K, V>(key, value));
            }
        }
        return result;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o.getClass() == this.getClass() && this.data.equals(((Relation)o).data);
    }
    
    public Set<V> getAll(final Object key) {
        return this.data.get(key);
    }
    
    public Set<V> get(final Object key) {
        return this.data.get(key);
    }
    
    @Override
    public int hashCode() {
        return this.data.hashCode();
    }
    
    public boolean isEmpty() {
        return this.data.isEmpty();
    }
    
    public Set<K> keySet() {
        return this.data.keySet();
    }
    
    public V put(final K key, final V value) {
        Set<V> set = this.data.get(key);
        if (set == null) {
            this.data.put(key, set = this.newSet());
        }
        set.add(value);
        return value;
    }
    
    public V putAll(final K key, final Collection<? extends V> values) {
        Set<V> set = this.data.get(key);
        if (set == null) {
            this.data.put(key, set = this.newSet());
        }
        set.addAll(values);
        return (values.size() == 0) ? null : values.iterator().next();
    }
    
    public V putAll(final Collection<K> keys, final V value) {
        V result = null;
        for (final K key : keys) {
            result = this.put(key, value);
        }
        return result;
    }
    
    private Set<V> newSet() {
        try {
            return (Set<V>)this.setCreator.newInstance(this.setComparatorParam);
        }
        catch (Exception e) {
            throw (RuntimeException)new IllegalArgumentException("Can't create new set").initCause(e);
        }
    }
    
    public void putAll(final Map<? extends K, ? extends V> t) {
        for (final Map.Entry<? extends K, ? extends V> entry : t.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
    
    public void putAll(final Relation<? extends K, ? extends V> t) {
        for (final K key : t.keySet()) {
            for (final V value : t.getAll(key)) {
                this.put(key, value);
            }
        }
    }
    
    public Set<V> removeAll(final K key) {
        try {
            return this.data.remove(key);
        }
        catch (NullPointerException e) {
            return null;
        }
    }
    
    public boolean remove(final K key, final V value) {
        try {
            final Set<V> set = this.data.get(key);
            if (set == null) {
                return false;
            }
            final boolean result = set.remove(value);
            if (set.size() == 0) {
                this.data.remove(key);
            }
            return result;
        }
        catch (NullPointerException e) {
            return false;
        }
    }
    
    public int size() {
        return this.data.size();
    }
    
    public Set<V> values() {
        return this.values(new LinkedHashSet<V>());
    }
    
    public <C extends Collection<V>> C values(final C result) {
        for (final Map.Entry<K, Set<V>> keyValue : this.data.entrySet()) {
            result.addAll((Collection<? extends V>)keyValue.getValue());
        }
        return result;
    }
    
    @Override
    public String toString() {
        return this.data.toString();
    }
    
    public Relation<K, V> addAllInverted(final Relation<V, K> source) {
        for (final V value : source.data.keySet()) {
            for (final K key : source.data.get(value)) {
                this.put(key, value);
            }
        }
        return this;
    }
    
    public Relation<K, V> addAllInverted(final Map<V, K> source) {
        for (final Map.Entry<V, K> entry : source.entrySet()) {
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
            for (final K key : this.data.keySet()) {
                this.data.put(key, Collections.unmodifiableSet((Set<? extends V>)this.data.get(key)));
            }
            this.data = Collections.unmodifiableMap((Map<? extends K, ? extends Set<V>>)this.data);
            this.frozen = true;
        }
        return this;
    }
    
    @Override
    public Relation<K, V> cloneAsThawed() {
        throw new UnsupportedOperationException();
    }
    
    public boolean removeAll(final Relation<K, V> toBeRemoved) {
        boolean result = false;
        for (final K key : toBeRemoved.keySet()) {
            try {
                final Set<V> values = toBeRemoved.getAll(key);
                if (values == null) {
                    continue;
                }
                result |= this.removeAll(key, values);
            }
            catch (NullPointerException ex) {}
        }
        return result;
    }
    
    public Set<V> removeAll(final K... keys) {
        return this.removeAll(Arrays.asList(keys));
    }
    
    public boolean removeAll(final K key, final Iterable<V> toBeRemoved) {
        boolean result = false;
        for (final V value : toBeRemoved) {
            result |= this.remove(key, value);
        }
        return result;
    }
    
    public Set<V> removeAll(final Collection<K> toBeRemoved) {
        final Set<V> result = new LinkedHashSet<V>();
        for (final K key : toBeRemoved) {
            try {
                final Set<V> removals = this.data.remove(key);
                if (removals == null) {
                    continue;
                }
                result.addAll((Collection<? extends V>)removals);
            }
            catch (NullPointerException ex) {}
        }
        return result;
    }
    
    static class SimpleEntry<K, V> implements Map.Entry<K, V>
    {
        K key;
        V value;
        
        public SimpleEntry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
        
        public SimpleEntry(final Map.Entry<K, V> e) {
            this.key = e.getKey();
            this.value = e.getValue();
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
        public V setValue(final V value) {
            final V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }
}
