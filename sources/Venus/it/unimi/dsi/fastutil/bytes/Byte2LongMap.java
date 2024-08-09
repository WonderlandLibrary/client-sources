/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2LongFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
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
public interface Byte2LongMap
extends Byte2LongFunction,
Map<Byte, Long> {
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

    public ObjectSet<Entry> byte2LongEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Byte, Long>> entrySet() {
        return this.byte2LongEntrySet();
    }

    @Override
    @Deprecated
    default public Long put(Byte by, Long l) {
        return Byte2LongFunction.super.put(by, l);
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        return Byte2LongFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        return Byte2LongFunction.super.remove(object);
    }

    public ByteSet keySet();

    public LongCollection values();

    @Override
    public boolean containsKey(byte var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Byte2LongFunction.super.containsKey(object);
    }

    public boolean containsValue(long var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Long)object);
    }

    default public long getOrDefault(byte by, long l) {
        long l2 = this.get(by);
        return l2 != this.defaultReturnValue() || this.containsKey(by) ? l2 : l;
    }

    @Override
    default public long putIfAbsent(byte by, long l) {
        long l2;
        long l3 = this.get(by);
        if (l3 != (l2 = this.defaultReturnValue()) || this.containsKey(by)) {
            return l3;
        }
        this.put(by, l);
        return l2;
    }

    default public boolean remove(byte by, long l) {
        long l2 = this.get(by);
        if (l2 != l || l2 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.remove(by);
        return false;
    }

    @Override
    default public boolean replace(byte by, long l, long l2) {
        long l3 = this.get(by);
        if (l3 != l || l3 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.put(by, l2);
        return false;
    }

    @Override
    default public long replace(byte by, long l) {
        return this.containsKey(by) ? this.put(by, l) : this.defaultReturnValue();
    }

    default public long computeIfAbsent(byte by, IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        long l = this.get(by);
        if (l != this.defaultReturnValue() || this.containsKey(by)) {
            return l;
        }
        long l2 = intToLongFunction.applyAsLong(by);
        this.put(by, l2);
        return l2;
    }

    default public long computeIfAbsentNullable(byte by, IntFunction<? extends Long> intFunction) {
        Objects.requireNonNull(intFunction);
        long l = this.get(by);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(by)) {
            return l;
        }
        Long l3 = intFunction.apply(by);
        if (l3 == null) {
            return l2;
        }
        long l4 = l3;
        this.put(by, l4);
        return l4;
    }

    default public long computeIfAbsentPartial(byte by, Byte2LongFunction byte2LongFunction) {
        Objects.requireNonNull(byte2LongFunction);
        long l = this.get(by);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(by)) {
            return l;
        }
        if (!byte2LongFunction.containsKey(by)) {
            return l2;
        }
        long l3 = byte2LongFunction.get(by);
        this.put(by, l3);
        return l3;
    }

    @Override
    default public long computeIfPresent(byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(by);
        long l2 = this.defaultReturnValue();
        if (l == l2 && !this.containsKey(by)) {
            return l2;
        }
        Long l3 = biFunction.apply((Byte)by, (Long)l);
        if (l3 == null) {
            this.remove(by);
            return l2;
        }
        long l4 = l3;
        this.put(by, l4);
        return l4;
    }

    @Override
    default public long compute(byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(by);
        long l2 = this.defaultReturnValue();
        boolean bl = l != l2 || this.containsKey(by);
        Long l3 = biFunction.apply((Byte)by, bl ? Long.valueOf(l) : null);
        if (l3 == null) {
            if (bl) {
                this.remove(by);
            }
            return l2;
        }
        long l4 = l3;
        this.put(by, l4);
        return l4;
    }

    @Override
    default public long merge(byte by, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        long l2;
        Objects.requireNonNull(biFunction);
        long l3 = this.get(by);
        long l4 = this.defaultReturnValue();
        if (l3 != l4 || this.containsKey(by)) {
            Long l5 = biFunction.apply((Long)l3, (Long)l);
            if (l5 == null) {
                this.remove(by);
                return l4;
            }
            l2 = l5;
        } else {
            l2 = l;
        }
        this.put(by, l2);
        return l2;
    }

    @Override
    @Deprecated
    default public Long getOrDefault(Object object, Long l) {
        return Map.super.getOrDefault(object, l);
    }

    @Override
    @Deprecated
    default public Long putIfAbsent(Byte by, Long l) {
        return Map.super.putIfAbsent(by, l);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Byte by, Long l, Long l2) {
        return Map.super.replace(by, l, l2);
    }

    @Override
    @Deprecated
    default public Long replace(Byte by, Long l) {
        return Map.super.replace(by, l);
    }

    @Override
    @Deprecated
    default public Long computeIfAbsent(Byte by, Function<? super Byte, ? extends Long> function) {
        return Map.super.computeIfAbsent(by, function);
    }

    @Override
    @Deprecated
    default public Long computeIfPresent(Byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
        return Map.super.computeIfPresent(by, biFunction);
    }

    @Override
    @Deprecated
    default public Long compute(Byte by, BiFunction<? super Byte, ? super Long, ? extends Long> biFunction) {
        return Map.super.compute(by, biFunction);
    }

    @Override
    @Deprecated
    default public Long merge(Byte by, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return Map.super.merge(by, l, biFunction);
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
        return this.put((Byte)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Byte)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Byte)object, (BiFunction<? super Byte, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Long>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Byte)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Byte)object, (Long)object2, (Long)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Byte)object, (Long)object2);
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
    extends Map.Entry<Byte, Long> {
        public byte getByteKey();

        @Override
        @Deprecated
        default public Byte getKey() {
            return this.getByteKey();
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

