/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.longs.Long2BooleanFunction;
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
import java.util.function.LongPredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Long2BooleanMap
extends Long2BooleanFunction,
Map<Long, Boolean> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(boolean var1);

    @Override
    public boolean defaultReturnValue();

    public ObjectSet<Entry> long2BooleanEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Long, Boolean>> entrySet() {
        return this.long2BooleanEntrySet();
    }

    @Override
    @Deprecated
    default public Boolean put(Long l, Boolean bl) {
        return Long2BooleanFunction.super.put(l, bl);
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        return Long2BooleanFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        return Long2BooleanFunction.super.remove(object);
    }

    public LongSet keySet();

    public BooleanCollection values();

    @Override
    public boolean containsKey(long var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Long2BooleanFunction.super.containsKey(object);
    }

    public boolean containsValue(boolean var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Boolean)object);
    }

    default public boolean getOrDefault(long l, boolean bl) {
        boolean bl2 = this.get(l);
        return bl2 != this.defaultReturnValue() || this.containsKey(l) ? bl2 : bl;
    }

    @Override
    default public boolean putIfAbsent(long l, boolean bl) {
        boolean bl2;
        boolean bl3 = this.get(l);
        if (bl3 != (bl2 = this.defaultReturnValue()) || this.containsKey(l)) {
            return bl3;
        }
        this.put(l, bl);
        return bl2;
    }

    default public boolean remove(long l, boolean bl) {
        boolean bl2 = this.get(l);
        if (bl2 != bl || bl2 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.remove(l);
        return false;
    }

    @Override
    default public boolean replace(long l, boolean bl, boolean bl2) {
        boolean bl3 = this.get(l);
        if (bl3 != bl || bl3 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.put(l, bl2);
        return false;
    }

    @Override
    default public boolean replace(long l, boolean bl) {
        return this.containsKey(l) ? this.put(l, bl) : this.defaultReturnValue();
    }

    default public boolean computeIfAbsent(long l, LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        boolean bl = this.get(l);
        if (bl != this.defaultReturnValue() || this.containsKey(l)) {
            return bl;
        }
        boolean bl2 = longPredicate.test(l);
        this.put(l, bl2);
        return bl2;
    }

    default public boolean computeIfAbsentNullable(long l, LongFunction<? extends Boolean> longFunction) {
        Objects.requireNonNull(longFunction);
        boolean bl = this.get(l);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(l)) {
            return bl;
        }
        Boolean bl3 = longFunction.apply(l);
        if (bl3 == null) {
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(l, bl4);
        return bl4;
    }

    default public boolean computeIfAbsentPartial(long l, Long2BooleanFunction long2BooleanFunction) {
        Objects.requireNonNull(long2BooleanFunction);
        boolean bl = this.get(l);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(l)) {
            return bl;
        }
        if (!long2BooleanFunction.containsKey(l)) {
            return bl2;
        }
        boolean bl3 = long2BooleanFunction.get(l);
        this.put(l, bl3);
        return bl3;
    }

    @Override
    default public boolean computeIfPresent(long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(l);
        boolean bl2 = this.defaultReturnValue();
        if (bl == bl2 && !this.containsKey(l)) {
            return bl2;
        }
        Boolean bl3 = biFunction.apply((Long)l, (Boolean)bl);
        if (bl3 == null) {
            this.remove(l);
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(l, bl4);
        return bl4;
    }

    @Override
    default public boolean compute(long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(l);
        boolean bl2 = this.defaultReturnValue();
        boolean bl3 = bl != bl2 || this.containsKey(l);
        Boolean bl4 = biFunction.apply((Long)l, bl3 ? Boolean.valueOf(bl) : null);
        if (bl4 == null) {
            if (bl3) {
                this.remove(l);
            }
            return bl2;
        }
        boolean bl5 = bl4;
        this.put(l, bl5);
        return bl5;
    }

    @Override
    default public boolean merge(long l, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        boolean bl2;
        Objects.requireNonNull(biFunction);
        boolean bl3 = this.get(l);
        boolean bl4 = this.defaultReturnValue();
        if (bl3 != bl4 || this.containsKey(l)) {
            Boolean bl5 = biFunction.apply((Boolean)bl3, (Boolean)bl);
            if (bl5 == null) {
                this.remove(l);
                return bl4;
            }
            bl2 = bl5;
        } else {
            bl2 = bl;
        }
        this.put(l, bl2);
        return bl2;
    }

    @Override
    @Deprecated
    default public Boolean getOrDefault(Object object, Boolean bl) {
        return Map.super.getOrDefault(object, bl);
    }

    @Override
    @Deprecated
    default public Boolean putIfAbsent(Long l, Boolean bl) {
        return Map.super.putIfAbsent(l, bl);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Long l, Boolean bl, Boolean bl2) {
        return Map.super.replace(l, bl, bl2);
    }

    @Override
    @Deprecated
    default public Boolean replace(Long l, Boolean bl) {
        return Map.super.replace(l, bl);
    }

    @Override
    @Deprecated
    default public Boolean computeIfAbsent(Long l, Function<? super Long, ? extends Boolean> function) {
        return Map.super.computeIfAbsent(l, function);
    }

    @Override
    @Deprecated
    default public Boolean computeIfPresent(Long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.computeIfPresent(l, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean compute(Long l, BiFunction<? super Long, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.compute(l, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean merge(Long l, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.merge(l, bl, biFunction);
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
        return this.put((Long)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Long)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Long)object, (BiFunction<? super Long, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Boolean>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Long)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Long)object, (Boolean)object2, (Boolean)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Long)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Boolean)object2);
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
    extends Map.Entry<Long, Boolean> {
        public long getLongKey();

        @Override
        @Deprecated
        default public Long getKey() {
            return this.getLongKey();
        }

        public boolean getBooleanValue();

        @Override
        public boolean setValue(boolean var1);

        @Override
        @Deprecated
        default public Boolean getValue() {
            return this.getBooleanValue();
        }

        @Override
        @Deprecated
        default public Boolean setValue(Boolean bl) {
            return this.setValue((boolean)bl);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Boolean)object);
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

