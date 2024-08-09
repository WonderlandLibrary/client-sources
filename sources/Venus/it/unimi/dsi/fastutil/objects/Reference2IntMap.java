/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2IntFunction;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Reference2IntMap<K>
extends Reference2IntFunction<K>,
Map<K, Integer> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(int var1);

    @Override
    public int defaultReturnValue();

    public ObjectSet<Entry<K>> reference2IntEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<K, Integer>> entrySet() {
        return this.reference2IntEntrySet();
    }

    @Override
    @Deprecated
    default public Integer put(K k, Integer n) {
        return Reference2IntFunction.super.put(k, n);
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        return Reference2IntFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        return Reference2IntFunction.super.remove(object);
    }

    @Override
    public ReferenceSet<K> keySet();

    public IntCollection values();

    @Override
    public boolean containsKey(Object var1);

    public boolean containsValue(int var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Integer)object);
    }

    @Override
    default public int getOrDefault(Object object, int n) {
        int n2 = this.getInt(object);
        return n2 != this.defaultReturnValue() || this.containsKey(object) ? n2 : n;
    }

    @Override
    default public int putIfAbsent(K k, int n) {
        int n2;
        int n3 = this.getInt(k);
        if (n3 != (n2 = this.defaultReturnValue()) || this.containsKey(k)) {
            return n3;
        }
        this.put(k, n);
        return n2;
    }

    default public boolean remove(Object object, int n) {
        int n2 = this.getInt(object);
        if (n2 != n || n2 == this.defaultReturnValue() && !this.containsKey(object)) {
            return true;
        }
        this.removeInt(object);
        return false;
    }

    @Override
    default public boolean replace(K k, int n, int n2) {
        int n3 = this.getInt(k);
        if (n3 != n || n3 == this.defaultReturnValue() && !this.containsKey(k)) {
            return true;
        }
        this.put(k, n2);
        return false;
    }

    @Override
    default public int replace(K k, int n) {
        return this.containsKey(k) ? this.put(k, n) : this.defaultReturnValue();
    }

    default public int computeIntIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        int n = this.getInt(k);
        if (n != this.defaultReturnValue() || this.containsKey(k)) {
            return n;
        }
        int n2 = toIntFunction.applyAsInt(k);
        this.put(k, n2);
        return n2;
    }

    default public int computeIntIfAbsentPartial(K k, Reference2IntFunction<? super K> reference2IntFunction) {
        Objects.requireNonNull(reference2IntFunction);
        int n = this.getInt(k);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(k)) {
            return n;
        }
        if (!reference2IntFunction.containsKey(k)) {
            return n2;
        }
        int n3 = reference2IntFunction.getInt(k);
        this.put(k, n3);
        return n3;
    }

    default public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.getInt(k);
        int n2 = this.defaultReturnValue();
        if (n == n2 && !this.containsKey(k)) {
            return n2;
        }
        Integer n3 = biFunction.apply(k, n);
        if (n3 == null) {
            this.removeInt(k);
            return n2;
        }
        int n4 = n3;
        this.put(k, n4);
        return n4;
    }

    default public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.getInt(k);
        int n2 = this.defaultReturnValue();
        boolean bl = n != n2 || this.containsKey(k);
        Integer n3 = biFunction.apply(k, bl ? Integer.valueOf(n) : null);
        if (n3 == null) {
            if (bl) {
                this.removeInt(k);
            }
            return n2;
        }
        int n4 = n3;
        this.put(k, n4);
        return n4;
    }

    default public int mergeInt(K k, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        int n2;
        Objects.requireNonNull(biFunction);
        int n3 = this.getInt(k);
        int n4 = this.defaultReturnValue();
        if (n3 != n4 || this.containsKey(k)) {
            Integer n5 = biFunction.apply((Integer)n3, (Integer)n);
            if (n5 == null) {
                this.removeInt(k);
                return n4;
            }
            n2 = n5;
        } else {
            n2 = n;
        }
        this.put(k, n2);
        return n2;
    }

    @Override
    @Deprecated
    default public Integer getOrDefault(Object object, Integer n) {
        return Map.super.getOrDefault(object, n);
    }

    @Override
    @Deprecated
    default public Integer putIfAbsent(K k, Integer n) {
        return Map.super.putIfAbsent(k, n);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(K k, Integer n, Integer n2) {
        return Map.super.replace(k, n, n2);
    }

    @Override
    @Deprecated
    default public Integer replace(K k, Integer n) {
        return Map.super.replace(k, n);
    }

    @Override
    @Deprecated
    default public Integer merge(K k, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.merge(k, n, biFunction);
    }

    @Override
    @Deprecated
    default public Object remove(Object object) {
        return this.remove(object);
    }

    @Override
    @Deprecated
    default public Object get(Object object) {
        return this.get(object);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((K)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge(object, (Integer)object2, (BiFunction<Integer, Integer, Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((K)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((K)object, (Integer)object2, (Integer)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((K)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Set entrySet() {
        return this.entrySet();
    }

    @Override
    default public Collection values() {
        return this.values();
    }

    @Override
    default public Set keySet() {
        return this.keySet();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static interface Entry<K>
    extends Map.Entry<K, Integer> {
        public int getIntValue();

        @Override
        public int setValue(int var1);

        @Override
        @Deprecated
        default public Integer getValue() {
            return this.getIntValue();
        }

        @Override
        @Deprecated
        default public Integer setValue(Integer n) {
            return this.setValue((int)n);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Integer)object);
        }

        @Override
        @Deprecated
        default public Object getValue() {
            return this.getValue();
        }
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<K>> consumer) {
            this.forEach(consumer);
        }
    }
}

