/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2BooleanFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Short2BooleanMap
extends Short2BooleanFunction,
Map<Short, Boolean> {
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

    public ObjectSet<Entry> short2BooleanEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Short, Boolean>> entrySet() {
        return this.short2BooleanEntrySet();
    }

    @Override
    @Deprecated
    default public Boolean put(Short s, Boolean bl) {
        return Short2BooleanFunction.super.put(s, bl);
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        return Short2BooleanFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        return Short2BooleanFunction.super.remove(object);
    }

    public ShortSet keySet();

    public BooleanCollection values();

    @Override
    public boolean containsKey(short var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Short2BooleanFunction.super.containsKey(object);
    }

    public boolean containsValue(boolean var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Boolean)object);
    }

    default public boolean getOrDefault(short s, boolean bl) {
        boolean bl2 = this.get(s);
        return bl2 != this.defaultReturnValue() || this.containsKey(s) ? bl2 : bl;
    }

    @Override
    default public boolean putIfAbsent(short s, boolean bl) {
        boolean bl2;
        boolean bl3 = this.get(s);
        if (bl3 != (bl2 = this.defaultReturnValue()) || this.containsKey(s)) {
            return bl3;
        }
        this.put(s, bl);
        return bl2;
    }

    default public boolean remove(short s, boolean bl) {
        boolean bl2 = this.get(s);
        if (bl2 != bl || bl2 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.remove(s);
        return false;
    }

    @Override
    default public boolean replace(short s, boolean bl, boolean bl2) {
        boolean bl3 = this.get(s);
        if (bl3 != bl || bl3 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.put(s, bl2);
        return false;
    }

    @Override
    default public boolean replace(short s, boolean bl) {
        return this.containsKey(s) ? this.put(s, bl) : this.defaultReturnValue();
    }

    default public boolean computeIfAbsent(short s, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        boolean bl = this.get(s);
        if (bl != this.defaultReturnValue() || this.containsKey(s)) {
            return bl;
        }
        boolean bl2 = intPredicate.test(s);
        this.put(s, bl2);
        return bl2;
    }

    default public boolean computeIfAbsentNullable(short s, IntFunction<? extends Boolean> intFunction) {
        Objects.requireNonNull(intFunction);
        boolean bl = this.get(s);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(s)) {
            return bl;
        }
        Boolean bl3 = intFunction.apply(s);
        if (bl3 == null) {
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(s, bl4);
        return bl4;
    }

    default public boolean computeIfAbsentPartial(short s, Short2BooleanFunction short2BooleanFunction) {
        Objects.requireNonNull(short2BooleanFunction);
        boolean bl = this.get(s);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(s)) {
            return bl;
        }
        if (!short2BooleanFunction.containsKey(s)) {
            return bl2;
        }
        boolean bl3 = short2BooleanFunction.get(s);
        this.put(s, bl3);
        return bl3;
    }

    @Override
    default public boolean computeIfPresent(short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(s);
        boolean bl2 = this.defaultReturnValue();
        if (bl == bl2 && !this.containsKey(s)) {
            return bl2;
        }
        Boolean bl3 = biFunction.apply((Short)s, (Boolean)bl);
        if (bl3 == null) {
            this.remove(s);
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(s, bl4);
        return bl4;
    }

    @Override
    default public boolean compute(short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(s);
        boolean bl2 = this.defaultReturnValue();
        boolean bl3 = bl != bl2 || this.containsKey(s);
        Boolean bl4 = biFunction.apply((Short)s, bl3 ? Boolean.valueOf(bl) : null);
        if (bl4 == null) {
            if (bl3) {
                this.remove(s);
            }
            return bl2;
        }
        boolean bl5 = bl4;
        this.put(s, bl5);
        return bl5;
    }

    @Override
    default public boolean merge(short s, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        boolean bl2;
        Objects.requireNonNull(biFunction);
        boolean bl3 = this.get(s);
        boolean bl4 = this.defaultReturnValue();
        if (bl3 != bl4 || this.containsKey(s)) {
            Boolean bl5 = biFunction.apply((Boolean)bl3, (Boolean)bl);
            if (bl5 == null) {
                this.remove(s);
                return bl4;
            }
            bl2 = bl5;
        } else {
            bl2 = bl;
        }
        this.put(s, bl2);
        return bl2;
    }

    @Override
    @Deprecated
    default public Boolean getOrDefault(Object object, Boolean bl) {
        return Map.super.getOrDefault(object, bl);
    }

    @Override
    @Deprecated
    default public Boolean putIfAbsent(Short s, Boolean bl) {
        return Map.super.putIfAbsent(s, bl);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Short s, Boolean bl, Boolean bl2) {
        return Map.super.replace(s, bl, bl2);
    }

    @Override
    @Deprecated
    default public Boolean replace(Short s, Boolean bl) {
        return Map.super.replace(s, bl);
    }

    @Override
    @Deprecated
    default public Boolean computeIfAbsent(Short s, Function<? super Short, ? extends Boolean> function) {
        return Map.super.computeIfAbsent(s, function);
    }

    @Override
    @Deprecated
    default public Boolean computeIfPresent(Short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.computeIfPresent(s, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean compute(Short s, BiFunction<? super Short, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.compute(s, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean merge(Short s, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.merge(s, bl, biFunction);
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
        return this.put((Short)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Short)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Short)object, (BiFunction<? super Short, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Boolean>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Short)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Short)object, (Boolean)object2, (Boolean)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Short)object, (Boolean)object2);
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
    extends Map.Entry<Short, Boolean> {
        public short getShortKey();

        @Override
        @Deprecated
        default public Short getKey() {
            return this.getShortKey();
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

