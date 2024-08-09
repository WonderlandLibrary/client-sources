/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2LongFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Float2LongMap
extends Float2LongFunction,
Map<Float, Long> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(long var1);

    @Override
    public long defaultReturnValue();

    public ObjectSet<Entry> float2LongEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Float, Long>> entrySet() {
        return this.float2LongEntrySet();
    }

    @Override
    @Deprecated
    default public Long put(Float f, Long l) {
        return Float2LongFunction.super.put(f, l);
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        return Float2LongFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        return Float2LongFunction.super.remove(object);
    }

    public FloatSet keySet();

    public LongCollection values();

    @Override
    public boolean containsKey(float var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Float2LongFunction.super.containsKey(object);
    }

    public boolean containsValue(long var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Long)object);
    }

    default public long getOrDefault(float f, long l) {
        long l2 = this.get(f);
        return l2 != this.defaultReturnValue() || this.containsKey(f) ? l2 : l;
    }

    @Override
    default public long putIfAbsent(float f, long l) {
        long l2;
        long l3 = this.get(f);
        if (l3 != (l2 = this.defaultReturnValue()) || this.containsKey(f)) {
            return l3;
        }
        this.put(f, l);
        return l2;
    }

    default public boolean remove(float f, long l) {
        long l2 = this.get(f);
        if (l2 != l || l2 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.remove(f);
        return false;
    }

    @Override
    default public boolean replace(float f, long l, long l2) {
        long l3 = this.get(f);
        if (l3 != l || l3 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.put(f, l2);
        return false;
    }

    @Override
    default public long replace(float f, long l) {
        return this.containsKey(f) ? this.put(f, l) : this.defaultReturnValue();
    }

    default public long computeIfAbsent(float f, DoubleToLongFunction doubleToLongFunction) {
        Objects.requireNonNull(doubleToLongFunction);
        long l = this.get(f);
        if (l != this.defaultReturnValue() || this.containsKey(f)) {
            return l;
        }
        long l2 = doubleToLongFunction.applyAsLong(f);
        this.put(f, l2);
        return l2;
    }

    default public long computeIfAbsentNullable(float f, DoubleFunction<? extends Long> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        long l = this.get(f);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(f)) {
            return l;
        }
        Long l3 = doubleFunction.apply(f);
        if (l3 == null) {
            return l2;
        }
        long l4 = l3;
        this.put(f, l4);
        return l4;
    }

    default public long computeIfAbsentPartial(float f, Float2LongFunction float2LongFunction) {
        Objects.requireNonNull(float2LongFunction);
        long l = this.get(f);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(f)) {
            return l;
        }
        if (!float2LongFunction.containsKey(f)) {
            return l2;
        }
        long l3 = float2LongFunction.get(f);
        this.put(f, l3);
        return l3;
    }

    @Override
    default public long computeIfPresent(float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(f);
        long l2 = this.defaultReturnValue();
        if (l == l2 && !this.containsKey(f)) {
            return l2;
        }
        Long l3 = biFunction.apply(Float.valueOf(f), (Long)l);
        if (l3 == null) {
            this.remove(f);
            return l2;
        }
        long l4 = l3;
        this.put(f, l4);
        return l4;
    }

    @Override
    default public long compute(float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(f);
        long l2 = this.defaultReturnValue();
        boolean bl = l != l2 || this.containsKey(f);
        Long l3 = biFunction.apply(Float.valueOf(f), bl ? Long.valueOf(l) : null);
        if (l3 == null) {
            if (bl) {
                this.remove(f);
            }
            return l2;
        }
        long l4 = l3;
        this.put(f, l4);
        return l4;
    }

    @Override
    default public long merge(float f, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        long l2;
        Objects.requireNonNull(biFunction);
        long l3 = this.get(f);
        long l4 = this.defaultReturnValue();
        if (l3 != l4 || this.containsKey(f)) {
            Long l5 = biFunction.apply((Long)l3, (Long)l);
            if (l5 == null) {
                this.remove(f);
                return l4;
            }
            l2 = l5;
        } else {
            l2 = l;
        }
        this.put(f, l2);
        return l2;
    }

    @Override
    @Deprecated
    default public Long getOrDefault(Object object, Long l) {
        return Map.super.getOrDefault(object, l);
    }

    @Override
    @Deprecated
    default public Long putIfAbsent(Float f, Long l) {
        return Map.super.putIfAbsent(f, l);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Float f, Long l, Long l2) {
        return Map.super.replace(f, l, l2);
    }

    @Override
    @Deprecated
    default public Long replace(Float f, Long l) {
        return Map.super.replace(f, l);
    }

    @Override
    @Deprecated
    default public Long computeIfAbsent(Float f, Function<? super Float, ? extends Long> function) {
        return Map.super.computeIfAbsent(f, function);
    }

    @Override
    @Deprecated
    default public Long computeIfPresent(Float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
        return Map.super.computeIfPresent(f, biFunction);
    }

    @Override
    @Deprecated
    default public Long compute(Float f, BiFunction<? super Float, ? super Long, ? extends Long> biFunction) {
        return Map.super.compute(f, biFunction);
    }

    @Override
    @Deprecated
    default public Long merge(Float f, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return Map.super.merge(f, l, biFunction);
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
        return this.put((Float)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Float)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Float)object, (BiFunction<? super Float, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Long>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Float)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Float)object, (Long)object2, (Long)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Float)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Long)object2);
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
    extends Map.Entry<Float, Long> {
        public float getFloatKey();

        @Override
        @Deprecated
        default public Float getKey() {
            return Float.valueOf(this.getFloatKey());
        }

        public long getLongValue();

        @Override
        public long setValue(long var1);

        @Override
        @Deprecated
        default public Long getValue() {
            return this.getLongValue();
        }

        @Override
        @Deprecated
        default public Long setValue(Long l) {
            return this.setValue((long)l);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Long)object);
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

