/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.longs.Long2ShortFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
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
public interface Long2ShortMap
extends Long2ShortFunction,
Map<Long, Short> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(short var1);

    @Override
    public short defaultReturnValue();

    public ObjectSet<Entry> long2ShortEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Long, Short>> entrySet() {
        return this.long2ShortEntrySet();
    }

    @Override
    @Deprecated
    default public Short put(Long l, Short s) {
        return Long2ShortFunction.super.put(l, s);
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        return Long2ShortFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        return Long2ShortFunction.super.remove(object);
    }

    public LongSet keySet();

    public ShortCollection values();

    @Override
    public boolean containsKey(long var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Long2ShortFunction.super.containsKey(object);
    }

    public boolean containsValue(short var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Short)object);
    }

    default public short getOrDefault(long l, short s) {
        short s2 = this.get(l);
        return s2 != this.defaultReturnValue() || this.containsKey(l) ? s2 : s;
    }

    @Override
    default public short putIfAbsent(long l, short s) {
        short s2;
        short s3 = this.get(l);
        if (s3 != (s2 = this.defaultReturnValue()) || this.containsKey(l)) {
            return s3;
        }
        this.put(l, s);
        return s2;
    }

    default public boolean remove(long l, short s) {
        short s2 = this.get(l);
        if (s2 != s || s2 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.remove(l);
        return false;
    }

    @Override
    default public boolean replace(long l, short s, short s2) {
        short s3 = this.get(l);
        if (s3 != s || s3 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.put(l, s2);
        return false;
    }

    @Override
    default public short replace(long l, short s) {
        return this.containsKey(l) ? this.put(l, s) : this.defaultReturnValue();
    }

    default public short computeIfAbsent(long l, LongToIntFunction longToIntFunction) {
        Objects.requireNonNull(longToIntFunction);
        short s = this.get(l);
        if (s != this.defaultReturnValue() || this.containsKey(l)) {
            return s;
        }
        short s2 = SafeMath.safeIntToShort(longToIntFunction.applyAsInt(l));
        this.put(l, s2);
        return s2;
    }

    default public short computeIfAbsentNullable(long l, LongFunction<? extends Short> longFunction) {
        Objects.requireNonNull(longFunction);
        short s = this.get(l);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(l)) {
            return s;
        }
        Short s3 = longFunction.apply(l);
        if (s3 == null) {
            return s2;
        }
        short s4 = s3;
        this.put(l, s4);
        return s4;
    }

    default public short computeIfAbsentPartial(long l, Long2ShortFunction long2ShortFunction) {
        Objects.requireNonNull(long2ShortFunction);
        short s = this.get(l);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(l)) {
            return s;
        }
        if (!long2ShortFunction.containsKey(l)) {
            return s2;
        }
        short s3 = long2ShortFunction.get(l);
        this.put(l, s3);
        return s3;
    }

    @Override
    default public short computeIfPresent(long l, BiFunction<? super Long, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(l);
        short s2 = this.defaultReturnValue();
        if (s == s2 && !this.containsKey(l)) {
            return s2;
        }
        Short s3 = biFunction.apply((Long)l, (Short)s);
        if (s3 == null) {
            this.remove(l);
            return s2;
        }
        short s4 = s3;
        this.put(l, s4);
        return s4;
    }

    @Override
    default public short compute(long l, BiFunction<? super Long, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(l);
        short s2 = this.defaultReturnValue();
        boolean bl = s != s2 || this.containsKey(l);
        Short s3 = biFunction.apply((Long)l, bl ? Short.valueOf(s) : null);
        if (s3 == null) {
            if (bl) {
                this.remove(l);
            }
            return s2;
        }
        short s4 = s3;
        this.put(l, s4);
        return s4;
    }

    @Override
    default public short merge(long l, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        short s2;
        Objects.requireNonNull(biFunction);
        short s3 = this.get(l);
        short s4 = this.defaultReturnValue();
        if (s3 != s4 || this.containsKey(l)) {
            Short s5 = biFunction.apply((Short)s3, (Short)s);
            if (s5 == null) {
                this.remove(l);
                return s4;
            }
            s2 = s5;
        } else {
            s2 = s;
        }
        this.put(l, s2);
        return s2;
    }

    @Override
    @Deprecated
    default public Short getOrDefault(Object object, Short s) {
        return Map.super.getOrDefault(object, s);
    }

    @Override
    @Deprecated
    default public Short putIfAbsent(Long l, Short s) {
        return Map.super.putIfAbsent(l, s);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Long l, Short s, Short s2) {
        return Map.super.replace(l, s, s2);
    }

    @Override
    @Deprecated
    default public Short replace(Long l, Short s) {
        return Map.super.replace(l, s);
    }

    @Override
    @Deprecated
    default public Short computeIfAbsent(Long l, Function<? super Long, ? extends Short> function) {
        return Map.super.computeIfAbsent(l, function);
    }

    @Override
    @Deprecated
    default public Short computeIfPresent(Long l, BiFunction<? super Long, ? super Short, ? extends Short> biFunction) {
        return Map.super.computeIfPresent(l, biFunction);
    }

    @Override
    @Deprecated
    default public Short compute(Long l, BiFunction<? super Long, ? super Short, ? extends Short> biFunction) {
        return Map.super.compute(l, biFunction);
    }

    @Override
    @Deprecated
    default public Short merge(Long l, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return Map.super.merge(l, s, biFunction);
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
        return this.put((Long)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Long)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Long)object, (BiFunction<? super Long, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Short>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Long)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Long)object, (Short)object2, (Short)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Long)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Short)object2);
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
    extends Map.Entry<Long, Short> {
        public long getLongKey();

        @Override
        @Deprecated
        default public Long getKey() {
            return this.getLongKey();
        }

        public short getShortValue();

        @Override
        public short setValue(short var1);

        @Override
        @Deprecated
        default public Short getValue() {
            return this.getShortValue();
        }

        @Override
        @Deprecated
        default public Short setValue(Short s) {
            return this.setValue((short)s);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Short)object);
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

