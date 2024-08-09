/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
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
import java.util.function.IntPredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Int2BooleanMap
extends Int2BooleanFunction,
Map<Integer, Boolean> {
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

    public ObjectSet<Entry> int2BooleanEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Integer, Boolean>> entrySet() {
        return this.int2BooleanEntrySet();
    }

    @Override
    @Deprecated
    default public Boolean put(Integer n, Boolean bl) {
        return Int2BooleanFunction.super.put(n, bl);
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        return Int2BooleanFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        return Int2BooleanFunction.super.remove(object);
    }

    public IntSet keySet();

    public BooleanCollection values();

    @Override
    public boolean containsKey(int var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Int2BooleanFunction.super.containsKey(object);
    }

    public boolean containsValue(boolean var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Boolean)object);
    }

    default public boolean getOrDefault(int n, boolean bl) {
        boolean bl2 = this.get(n);
        return bl2 != this.defaultReturnValue() || this.containsKey(n) ? bl2 : bl;
    }

    @Override
    default public boolean putIfAbsent(int n, boolean bl) {
        boolean bl2;
        boolean bl3 = this.get(n);
        if (bl3 != (bl2 = this.defaultReturnValue()) || this.containsKey(n)) {
            return bl3;
        }
        this.put(n, bl);
        return bl2;
    }

    default public boolean remove(int n, boolean bl) {
        boolean bl2 = this.get(n);
        if (bl2 != bl || bl2 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.remove(n);
        return false;
    }

    @Override
    default public boolean replace(int n, boolean bl, boolean bl2) {
        boolean bl3 = this.get(n);
        if (bl3 != bl || bl3 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.put(n, bl2);
        return false;
    }

    @Override
    default public boolean replace(int n, boolean bl) {
        return this.containsKey(n) ? this.put(n, bl) : this.defaultReturnValue();
    }

    default public boolean computeIfAbsent(int n, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        boolean bl = this.get(n);
        if (bl != this.defaultReturnValue() || this.containsKey(n)) {
            return bl;
        }
        boolean bl2 = intPredicate.test(n);
        this.put(n, bl2);
        return bl2;
    }

    default public boolean computeIfAbsentNullable(int n, IntFunction<? extends Boolean> intFunction) {
        Objects.requireNonNull(intFunction);
        boolean bl = this.get(n);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(n)) {
            return bl;
        }
        Boolean bl3 = intFunction.apply(n);
        if (bl3 == null) {
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(n, bl4);
        return bl4;
    }

    default public boolean computeIfAbsentPartial(int n, Int2BooleanFunction int2BooleanFunction) {
        Objects.requireNonNull(int2BooleanFunction);
        boolean bl = this.get(n);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(n)) {
            return bl;
        }
        if (!int2BooleanFunction.containsKey(n)) {
            return bl2;
        }
        boolean bl3 = int2BooleanFunction.get(n);
        this.put(n, bl3);
        return bl3;
    }

    @Override
    default public boolean computeIfPresent(int n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(n);
        boolean bl2 = this.defaultReturnValue();
        if (bl == bl2 && !this.containsKey(n)) {
            return bl2;
        }
        Boolean bl3 = biFunction.apply((Integer)n, (Boolean)bl);
        if (bl3 == null) {
            this.remove(n);
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(n, bl4);
        return bl4;
    }

    @Override
    default public boolean compute(int n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(n);
        boolean bl2 = this.defaultReturnValue();
        boolean bl3 = bl != bl2 || this.containsKey(n);
        Boolean bl4 = biFunction.apply((Integer)n, bl3 ? Boolean.valueOf(bl) : null);
        if (bl4 == null) {
            if (bl3) {
                this.remove(n);
            }
            return bl2;
        }
        boolean bl5 = bl4;
        this.put(n, bl5);
        return bl5;
    }

    @Override
    default public boolean merge(int n, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        boolean bl2;
        Objects.requireNonNull(biFunction);
        boolean bl3 = this.get(n);
        boolean bl4 = this.defaultReturnValue();
        if (bl3 != bl4 || this.containsKey(n)) {
            Boolean bl5 = biFunction.apply((Boolean)bl3, (Boolean)bl);
            if (bl5 == null) {
                this.remove(n);
                return bl4;
            }
            bl2 = bl5;
        } else {
            bl2 = bl;
        }
        this.put(n, bl2);
        return bl2;
    }

    @Override
    @Deprecated
    default public Boolean getOrDefault(Object object, Boolean bl) {
        return Map.super.getOrDefault(object, bl);
    }

    @Override
    @Deprecated
    default public Boolean putIfAbsent(Integer n, Boolean bl) {
        return Map.super.putIfAbsent(n, bl);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Integer n, Boolean bl, Boolean bl2) {
        return Map.super.replace(n, bl, bl2);
    }

    @Override
    @Deprecated
    default public Boolean replace(Integer n, Boolean bl) {
        return Map.super.replace(n, bl);
    }

    @Override
    @Deprecated
    default public Boolean computeIfAbsent(Integer n, Function<? super Integer, ? extends Boolean> function) {
        return Map.super.computeIfAbsent(n, function);
    }

    @Override
    @Deprecated
    default public Boolean computeIfPresent(Integer n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.computeIfPresent(n, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean compute(Integer n, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.compute(n, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean merge(Integer n, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.merge(n, bl, biFunction);
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
        return this.put((Integer)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Integer)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Integer)object, (BiFunction<? super Integer, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Boolean>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Integer)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Integer)object, (Boolean)object2, (Boolean)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Integer)object, (Boolean)object2);
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
    extends Map.Entry<Integer, Boolean> {
        public int getIntKey();

        @Override
        @Deprecated
        default public Integer getKey() {
            return this.getIntKey();
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

