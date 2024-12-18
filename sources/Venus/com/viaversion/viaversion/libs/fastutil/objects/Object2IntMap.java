/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Object2IntMap<K>
extends Object2IntFunction<K>,
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

    public ObjectSet<Entry<K>> object2IntEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<K, Integer>> entrySet() {
        return this.object2IntEntrySet();
    }

    @Override
    @Deprecated
    default public Integer put(K k, Integer n) {
        return Object2IntFunction.super.put(k, n);
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        return Object2IntFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        return Object2IntFunction.super.remove(object);
    }

    @Override
    public ObjectSet<K> keySet();

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
    default public void forEach(BiConsumer<? super K, ? super Integer> biConsumer) {
        ObjectSet<Entry<K>> objectSet = this.object2IntEntrySet();
        Consumer<Entry> consumer = arg_0 -> Object2IntMap.lambda$forEach$0(biConsumer, arg_0);
        if (objectSet instanceof FastEntrySet) {
            ((FastEntrySet)objectSet).fastForEach(consumer);
        } else {
            objectSet.forEach(consumer);
        }
    }

    @Override
    default public int getOrDefault(Object object, int n) {
        int n2 = this.getInt(object);
        return n2 != this.defaultReturnValue() || this.containsKey(object) ? n2 : n;
    }

    @Override
    @Deprecated
    default public Integer getOrDefault(Object object, Integer n) {
        return Map.super.getOrDefault(object, n);
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

    default public int computeIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        int n = this.getInt(k);
        if (n != this.defaultReturnValue() || this.containsKey(k)) {
            return n;
        }
        int n2 = toIntFunction.applyAsInt(k);
        this.put(k, n2);
        return n2;
    }

    @Deprecated
    default public int computeIntIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
        return this.computeIfAbsent(k, toIntFunction);
    }

    default public int computeIfAbsent(K k, Object2IntFunction<? super K> object2IntFunction) {
        Objects.requireNonNull(object2IntFunction);
        int n = this.getInt(k);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(k)) {
            return n;
        }
        if (!object2IntFunction.containsKey(k)) {
            return n2;
        }
        int n3 = object2IntFunction.getInt(k);
        this.put(k, n3);
        return n3;
    }

    @Deprecated
    default public int computeIntIfAbsentPartial(K k, Object2IntFunction<? super K> object2IntFunction) {
        return this.computeIfAbsent(k, object2IntFunction);
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

    @Override
    default public int merge(K k, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
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

    default public int mergeInt(K k, int n, IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        int n2 = this.getInt(k);
        int n3 = this.defaultReturnValue();
        int n4 = n2 != n3 || this.containsKey(k) ? intBinaryOperator.applyAsInt(n2, n) : n;
        this.put(k, n4);
        return n4;
    }

    default public int mergeInt(K k, int n, com.viaversion.viaversion.libs.fastutil.ints.IntBinaryOperator intBinaryOperator) {
        return this.mergeInt(k, n, (IntBinaryOperator)intBinaryOperator);
    }

    @Deprecated
    default public int mergeInt(K k, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        return this.merge(k, n, biFunction);
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
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Integer)object2);
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
        return this.merge((K)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
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

    private static void lambda$forEach$0(BiConsumer biConsumer, Entry entry) {
        biConsumer.accept(entry.getKey(), entry.getIntValue());
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<K>> consumer) {
            this.forEach(consumer);
        }
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
}

