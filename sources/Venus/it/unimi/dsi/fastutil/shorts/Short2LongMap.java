/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2LongFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Short2LongMap
extends Short2LongFunction,
Map<Short, Long> {
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

    public ObjectSet<Entry> short2LongEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Short, Long>> entrySet() {
        return this.short2LongEntrySet();
    }

    @Override
    @Deprecated
    default public Long put(Short s, Long l) {
        return Short2LongFunction.super.put(s, l);
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        return Short2LongFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        return Short2LongFunction.super.remove(object);
    }

    public ShortSet keySet();

    public LongCollection values();

    @Override
    public boolean containsKey(short var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Short2LongFunction.super.containsKey(object);
    }

    public boolean containsValue(long var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Long)object);
    }

    default public long getOrDefault(short s, long l) {
        long l2 = this.get(s);
        return l2 != this.defaultReturnValue() || this.containsKey(s) ? l2 : l;
    }

    @Override
    default public long putIfAbsent(short s, long l) {
        long l2;
        long l3 = this.get(s);
        if (l3 != (l2 = this.defaultReturnValue()) || this.containsKey(s)) {
            return l3;
        }
        this.put(s, l);
        return l2;
    }

    default public boolean remove(short s, long l) {
        long l2 = this.get(s);
        if (l2 != l || l2 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.remove(s);
        return false;
    }

    @Override
    default public boolean replace(short s, long l, long l2) {
        long l3 = this.get(s);
        if (l3 != l || l3 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.put(s, l2);
        return false;
    }

    @Override
    default public long replace(short s, long l) {
        return this.containsKey(s) ? this.put(s, l) : this.defaultReturnValue();
    }

    default public long computeIfAbsent(short s, IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        long l = this.get(s);
        if (l != this.defaultReturnValue() || this.containsKey(s)) {
            return l;
        }
        long l2 = intToLongFunction.applyAsLong(s);
        this.put(s, l2);
        return l2;
    }

    default public long computeIfAbsentNullable(short s, IntFunction<? extends Long> intFunction) {
        Objects.requireNonNull(intFunction);
        long l = this.get(s);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(s)) {
            return l;
        }
        Long l3 = intFunction.apply(s);
        if (l3 == null) {
            return l2;
        }
        long l4 = l3;
        this.put(s, l4);
        return l4;
    }

    default public long computeIfAbsentPartial(short s, Short2LongFunction short2LongFunction) {
        Objects.requireNonNull(short2LongFunction);
        long l = this.get(s);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(s)) {
            return l;
        }
        if (!short2LongFunction.containsKey(s)) {
            return l2;
        }
        long l3 = short2LongFunction.get(s);
        this.put(s, l3);
        return l3;
    }

    @Override
    default public long computeIfPresent(short s, BiFunction<? super Short, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(s);
        long l2 = this.defaultReturnValue();
        if (l == l2 && !this.containsKey(s)) {
            return l2;
        }
        Long l3 = biFunction.apply((Short)s, (Long)l);
        if (l3 == null) {
            this.remove(s);
            return l2;
        }
        long l4 = l3;
        this.put(s, l4);
        return l4;
    }

    @Override
    default public long compute(short s, BiFunction<? super Short, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(s);
        long l2 = this.defaultReturnValue();
        boolean bl = l != l2 || this.containsKey(s);
        Long l3 = biFunction.apply((Short)s, bl ? Long.valueOf(l) : null);
        if (l3 == null) {
            if (bl) {
                this.remove(s);
            }
            return l2;
        }
        long l4 = l3;
        this.put(s, l4);
        return l4;
    }

    @Override
    default public long merge(short s, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        long l2;
        Objects.requireNonNull(biFunction);
        long l3 = this.get(s);
        long l4 = this.defaultReturnValue();
        if (l3 != l4 || this.containsKey(s)) {
            Long l5 = biFunction.apply((Long)l3, (Long)l);
            if (l5 == null) {
                this.remove(s);
                return l4;
            }
            l2 = l5;
        } else {
            l2 = l;
        }
        this.put(s, l2);
        return l2;
    }

    @Override
    @Deprecated
    default public Long getOrDefault(Object object, Long l) {
        return Map.super.getOrDefault(object, l);
    }

    @Override
    @Deprecated
    default public Long putIfAbsent(Short s, Long l) {
        return Map.super.putIfAbsent(s, l);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Short s, Long l, Long l2) {
        return Map.super.replace(s, l, l2);
    }

    @Override
    @Deprecated
    default public Long replace(Short s, Long l) {
        return Map.super.replace(s, l);
    }

    @Override
    @Deprecated
    default public Long computeIfAbsent(Short s, Function<? super Short, ? extends Long> function) {
        return Map.super.computeIfAbsent(s, function);
    }

    @Override
    @Deprecated
    default public Long computeIfPresent(Short s, BiFunction<? super Short, ? super Long, ? extends Long> biFunction) {
        return Map.super.computeIfPresent(s, biFunction);
    }

    @Override
    @Deprecated
    default public Long compute(Short s, BiFunction<? super Short, ? super Long, ? extends Long> biFunction) {
        return Map.super.compute(s, biFunction);
    }

    @Override
    @Deprecated
    default public Long merge(Short s, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return Map.super.merge(s, l, biFunction);
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
        return this.put((Short)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Short)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Short)object, (BiFunction<? super Short, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Long>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Short)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Short)object, (Long)object2, (Long)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Short)object, (Long)object2);
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
    extends Map.Entry<Short, Long> {
        public short getShortKey();

        @Override
        @Deprecated
        default public Short getKey() {
            return this.getShortKey();
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

