/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.longs.Long2ByteFunction;
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
public interface Long2ByteMap
extends Long2ByteFunction,
Map<Long, Byte> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(byte var1);

    @Override
    public byte defaultReturnValue();

    public ObjectSet<Entry> long2ByteEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Long, Byte>> entrySet() {
        return this.long2ByteEntrySet();
    }

    @Override
    @Deprecated
    default public Byte put(Long l, Byte by) {
        return Long2ByteFunction.super.put(l, by);
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        return Long2ByteFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        return Long2ByteFunction.super.remove(object);
    }

    public LongSet keySet();

    public ByteCollection values();

    @Override
    public boolean containsKey(long var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Long2ByteFunction.super.containsKey(object);
    }

    public boolean containsValue(byte var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Byte)object);
    }

    default public byte getOrDefault(long l, byte by) {
        byte by2 = this.get(l);
        return by2 != this.defaultReturnValue() || this.containsKey(l) ? by2 : by;
    }

    @Override
    default public byte putIfAbsent(long l, byte by) {
        byte by2;
        byte by3 = this.get(l);
        if (by3 != (by2 = this.defaultReturnValue()) || this.containsKey(l)) {
            return by3;
        }
        this.put(l, by);
        return by2;
    }

    default public boolean remove(long l, byte by) {
        byte by2 = this.get(l);
        if (by2 != by || by2 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.remove(l);
        return false;
    }

    @Override
    default public boolean replace(long l, byte by, byte by2) {
        byte by3 = this.get(l);
        if (by3 != by || by3 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.put(l, by2);
        return false;
    }

    @Override
    default public byte replace(long l, byte by) {
        return this.containsKey(l) ? this.put(l, by) : this.defaultReturnValue();
    }

    default public byte computeIfAbsent(long l, LongToIntFunction longToIntFunction) {
        Objects.requireNonNull(longToIntFunction);
        byte by = this.get(l);
        if (by != this.defaultReturnValue() || this.containsKey(l)) {
            return by;
        }
        byte by2 = SafeMath.safeIntToByte(longToIntFunction.applyAsInt(l));
        this.put(l, by2);
        return by2;
    }

    default public byte computeIfAbsentNullable(long l, LongFunction<? extends Byte> longFunction) {
        Objects.requireNonNull(longFunction);
        byte by = this.get(l);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(l)) {
            return by;
        }
        Byte by3 = longFunction.apply(l);
        if (by3 == null) {
            return by2;
        }
        byte by4 = by3;
        this.put(l, by4);
        return by4;
    }

    default public byte computeIfAbsentPartial(long l, Long2ByteFunction long2ByteFunction) {
        Objects.requireNonNull(long2ByteFunction);
        byte by = this.get(l);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(l)) {
            return by;
        }
        if (!long2ByteFunction.containsKey(l)) {
            return by2;
        }
        byte by3 = long2ByteFunction.get(l);
        this.put(l, by3);
        return by3;
    }

    @Override
    default public byte computeIfPresent(long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(l);
        byte by2 = this.defaultReturnValue();
        if (by == by2 && !this.containsKey(l)) {
            return by2;
        }
        Byte by3 = biFunction.apply((Long)l, (Byte)by);
        if (by3 == null) {
            this.remove(l);
            return by2;
        }
        byte by4 = by3;
        this.put(l, by4);
        return by4;
    }

    @Override
    default public byte compute(long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(l);
        byte by2 = this.defaultReturnValue();
        boolean bl = by != by2 || this.containsKey(l);
        Byte by3 = biFunction.apply((Long)l, bl ? Byte.valueOf(by) : null);
        if (by3 == null) {
            if (bl) {
                this.remove(l);
            }
            return by2;
        }
        byte by4 = by3;
        this.put(l, by4);
        return by4;
    }

    @Override
    default public byte merge(long l, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        byte by2;
        Objects.requireNonNull(biFunction);
        byte by3 = this.get(l);
        byte by4 = this.defaultReturnValue();
        if (by3 != by4 || this.containsKey(l)) {
            Byte by5 = biFunction.apply((Byte)by3, (Byte)by);
            if (by5 == null) {
                this.remove(l);
                return by4;
            }
            by2 = by5;
        } else {
            by2 = by;
        }
        this.put(l, by2);
        return by2;
    }

    @Override
    @Deprecated
    default public Byte getOrDefault(Object object, Byte by) {
        return Map.super.getOrDefault(object, by);
    }

    @Override
    @Deprecated
    default public Byte putIfAbsent(Long l, Byte by) {
        return Map.super.putIfAbsent(l, by);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Long l, Byte by, Byte by2) {
        return Map.super.replace(l, by, by2);
    }

    @Override
    @Deprecated
    default public Byte replace(Long l, Byte by) {
        return Map.super.replace(l, by);
    }

    @Override
    @Deprecated
    default public Byte computeIfAbsent(Long l, Function<? super Long, ? extends Byte> function) {
        return Map.super.computeIfAbsent(l, function);
    }

    @Override
    @Deprecated
    default public Byte computeIfPresent(Long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.computeIfPresent(l, biFunction);
    }

    @Override
    @Deprecated
    default public Byte compute(Long l, BiFunction<? super Long, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.compute(l, biFunction);
    }

    @Override
    @Deprecated
    default public Byte merge(Long l, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.merge(l, by, biFunction);
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
        return this.put((Long)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Long)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Long)object, (BiFunction<? super Long, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Byte>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Long)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Long)object, (Byte)object2, (Byte)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Long)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Byte)object2);
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
    extends Map.Entry<Long, Byte> {
        public long getLongKey();

        @Override
        @Deprecated
        default public Long getKey() {
            return this.getLongKey();
        }

        public byte getByteValue();

        @Override
        public byte setValue(byte var1);

        @Override
        @Deprecated
        default public Byte getValue() {
            return this.getByteValue();
        }

        @Override
        @Deprecated
        default public Byte setValue(Byte by) {
            return this.setValue((byte)by);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Byte)object);
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

