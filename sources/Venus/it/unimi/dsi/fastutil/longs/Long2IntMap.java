/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.longs.Long2IntFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Long2IntMap
extends Long2IntFunction,
Map<Long, Integer> {
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

    public ObjectSet<Entry> long2IntEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Long, Integer>> entrySet() {
        return this.long2IntEntrySet();
    }

    @Override
    @Deprecated
    default public Integer put(Long l, Integer n) {
        return Long2IntFunction.super.put(l, n);
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        return Long2IntFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        return Long2IntFunction.super.remove(object);
    }

    public LongSet keySet();

    public IntCollection values();

    @Override
    public boolean containsKey(long var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Long2IntFunction.super.containsKey(object);
    }

    public boolean containsValue(int var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Integer)object);
    }

    default public int getOrDefault(long l, int n) {
        int n2 = this.get(l);
        return n2 != this.defaultReturnValue() || this.containsKey(l) ? n2 : n;
    }

    @Override
    default public int putIfAbsent(long l, int n) {
        int n2;
        int n3 = this.get(l);
        if (n3 != (n2 = this.defaultReturnValue()) || this.containsKey(l)) {
            return n3;
        }
        this.put(l, n);
        return n2;
    }

    default public boolean remove(long l, int n) {
        int n2 = this.get(l);
        if (n2 != n || n2 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.remove(l);
        return false;
    }

    @Override
    default public boolean replace(long l, int n, int n2) {
        int n3 = this.get(l);
        if (n3 != n || n3 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.put(l, n2);
        return false;
    }

    @Override
    default public int replace(long l, int n) {
        return this.containsKey(l) ? this.put(l, n) : this.defaultReturnValue();
    }

    default public int computeIfAbsent(long l, LongToIntFunction longToIntFunction) {
        Objects.requireNonNull(longToIntFunction);
        int n = this.get(l);
        if (n != this.defaultReturnValue() || this.containsKey(l)) {
            return n;
        }
        int n2 = longToIntFunction.applyAsInt(l);
        this.put(l, n2);
        return n2;
    }

    default public int computeIfAbsentNullable(long l, LongFunction<? extends Integer> longFunction) {
        Objects.requireNonNull(longFunction);
        int n = this.get(l);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(l)) {
            return n;
        }
        Integer n3 = longFunction.apply(l);
        if (n3 == null) {
            return n2;
        }
        int n4 = n3;
        this.put(l, n4);
        return n4;
    }

    default public int computeIfAbsentPartial(long l, Long2IntFunction long2IntFunction) {
        Objects.requireNonNull(long2IntFunction);
        int n = this.get(l);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(l)) {
            return n;
        }
        if (!long2IntFunction.containsKey(l)) {
            return n2;
        }
        int n3 = long2IntFunction.get(l);
        this.put(l, n3);
        return n3;
    }

    @Override
    default public int computeIfPresent(long l, BiFunction<? super Long, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(l);
        int n2 = this.defaultReturnValue();
        if (n == n2 && !this.containsKey(l)) {
            return n2;
        }
        Integer n3 = biFunction.apply((Long)l, (Integer)n);
        if (n3 == null) {
            this.remove(l);
            return n2;
        }
        int n4 = n3;
        this.put(l, n4);
        return n4;
    }

    @Override
    default public int compute(long l, BiFunction<? super Long, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(l);
        int n2 = this.defaultReturnValue();
        boolean bl = n != n2 || this.containsKey(l);
        Integer n3 = biFunction.apply((Long)l, bl ? Integer.valueOf(n) : null);
        if (n3 == null) {
            if (bl) {
                this.remove(l);
            }
            return n2;
        }
        int n4 = n3;
        this.put(l, n4);
        return n4;
    }

    @Override
    default public int merge(long l, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        int n2;
        Objects.requireNonNull(biFunction);
        int n3 = this.get(l);
        int n4 = this.defaultReturnValue();
        if (n3 != n4 || this.containsKey(l)) {
            Integer n5 = biFunction.apply((Integer)n3, (Integer)n);
            if (n5 == null) {
                this.remove(l);
                return n4;
            }
            n2 = n5;
        } else {
            n2 = n;
        }
        this.put(l, n2);
        return n2;
    }

    @Override
    @Deprecated
    default public Integer getOrDefault(Object object, Integer n) {
        return Map.super.getOrDefault(object, n);
    }

    @Override
    @Deprecated
    default public Integer putIfAbsent(Long l, Integer n) {
        return Map.super.putIfAbsent(l, n);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Long l, Integer n, Integer n2) {
        return Map.super.replace(l, n, n2);
    }

    @Override
    @Deprecated
    default public Integer replace(Long l, Integer n) {
        return Map.super.replace(l, n);
    }

    @Override
    @Deprecated
    default public Integer computeIfAbsent(Long l, Function<? super Long, ? extends Integer> function) {
        return Map.super.computeIfAbsent(l, function);
    }

    @Override
    @Deprecated
    default public Integer computeIfPresent(Long l, BiFunction<? super Long, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.computeIfPresent(l, biFunction);
    }

    @Override
    @Deprecated
    default public Integer compute(Long l, BiFunction<? super Long, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.compute(l, biFunction);
    }

    @Override
    @Deprecated
    default public Integer merge(Long l, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.merge(l, n, biFunction);
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
        return this.put((Long)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Long)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Long)object, (BiFunction<? super Long, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Integer>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Long)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Long)object, (Integer)object2, (Integer)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Long)object, (Integer)object2);
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
    public static interface Entry
    extends Map.Entry<Long, Integer> {
        public long getLongKey();

        @Override
        @Deprecated
        default public Long getKey() {
            return this.getLongKey();
        }

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

        @Override
        @Deprecated
        default public Object getKey() {
            return this.getKey();
        }
    }

    public static interface FastEntrySet
    extends ObjectSet<Entry> {
        public ObjectIterator<Entry> fastIterator();

        default public void fastForEach(Consumer<? super Entry> consumer) {
            this.forEach(consumer);
        }
    }
}

