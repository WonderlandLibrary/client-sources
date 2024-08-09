/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.doubles.Double2BooleanFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Double2BooleanMap
extends Double2BooleanFunction,
Map<Double, Boolean> {
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

    public ObjectSet<Entry> double2BooleanEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Double, Boolean>> entrySet() {
        return this.double2BooleanEntrySet();
    }

    @Override
    @Deprecated
    default public Boolean put(Double d, Boolean bl) {
        return Double2BooleanFunction.super.put(d, bl);
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        return Double2BooleanFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        return Double2BooleanFunction.super.remove(object);
    }

    public DoubleSet keySet();

    public BooleanCollection values();

    @Override
    public boolean containsKey(double var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Double2BooleanFunction.super.containsKey(object);
    }

    public boolean containsValue(boolean var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Boolean)object);
    }

    default public boolean getOrDefault(double d, boolean bl) {
        boolean bl2 = this.get(d);
        return bl2 != this.defaultReturnValue() || this.containsKey(d) ? bl2 : bl;
    }

    @Override
    default public boolean putIfAbsent(double d, boolean bl) {
        boolean bl2;
        boolean bl3 = this.get(d);
        if (bl3 != (bl2 = this.defaultReturnValue()) || this.containsKey(d)) {
            return bl3;
        }
        this.put(d, bl);
        return bl2;
    }

    default public boolean remove(double d, boolean bl) {
        boolean bl2 = this.get(d);
        if (bl2 != bl || bl2 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.remove(d);
        return false;
    }

    @Override
    default public boolean replace(double d, boolean bl, boolean bl2) {
        boolean bl3 = this.get(d);
        if (bl3 != bl || bl3 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.put(d, bl2);
        return false;
    }

    @Override
    default public boolean replace(double d, boolean bl) {
        return this.containsKey(d) ? this.put(d, bl) : this.defaultReturnValue();
    }

    default public boolean computeIfAbsent(double d, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        boolean bl = this.get(d);
        if (bl != this.defaultReturnValue() || this.containsKey(d)) {
            return bl;
        }
        boolean bl2 = doublePredicate.test(d);
        this.put(d, bl2);
        return bl2;
    }

    default public boolean computeIfAbsentNullable(double d, DoubleFunction<? extends Boolean> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        boolean bl = this.get(d);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(d)) {
            return bl;
        }
        Boolean bl3 = doubleFunction.apply(d);
        if (bl3 == null) {
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(d, bl4);
        return bl4;
    }

    default public boolean computeIfAbsentPartial(double d, Double2BooleanFunction double2BooleanFunction) {
        Objects.requireNonNull(double2BooleanFunction);
        boolean bl = this.get(d);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(d)) {
            return bl;
        }
        if (!double2BooleanFunction.containsKey(d)) {
            return bl2;
        }
        boolean bl3 = double2BooleanFunction.get(d);
        this.put(d, bl3);
        return bl3;
    }

    @Override
    default public boolean computeIfPresent(double d, BiFunction<? super Double, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(d);
        boolean bl2 = this.defaultReturnValue();
        if (bl == bl2 && !this.containsKey(d)) {
            return bl2;
        }
        Boolean bl3 = biFunction.apply((Double)d, (Boolean)bl);
        if (bl3 == null) {
            this.remove(d);
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(d, bl4);
        return bl4;
    }

    @Override
    default public boolean compute(double d, BiFunction<? super Double, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(d);
        boolean bl2 = this.defaultReturnValue();
        boolean bl3 = bl != bl2 || this.containsKey(d);
        Boolean bl4 = biFunction.apply((Double)d, bl3 ? Boolean.valueOf(bl) : null);
        if (bl4 == null) {
            if (bl3) {
                this.remove(d);
            }
            return bl2;
        }
        boolean bl5 = bl4;
        this.put(d, bl5);
        return bl5;
    }

    @Override
    default public boolean merge(double d, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        boolean bl2;
        Objects.requireNonNull(biFunction);
        boolean bl3 = this.get(d);
        boolean bl4 = this.defaultReturnValue();
        if (bl3 != bl4 || this.containsKey(d)) {
            Boolean bl5 = biFunction.apply((Boolean)bl3, (Boolean)bl);
            if (bl5 == null) {
                this.remove(d);
                return bl4;
            }
            bl2 = bl5;
        } else {
            bl2 = bl;
        }
        this.put(d, bl2);
        return bl2;
    }

    @Override
    @Deprecated
    default public Boolean getOrDefault(Object object, Boolean bl) {
        return Map.super.getOrDefault(object, bl);
    }

    @Override
    @Deprecated
    default public Boolean putIfAbsent(Double d, Boolean bl) {
        return Map.super.putIfAbsent(d, bl);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Double d, Boolean bl, Boolean bl2) {
        return Map.super.replace(d, bl, bl2);
    }

    @Override
    @Deprecated
    default public Boolean replace(Double d, Boolean bl) {
        return Map.super.replace(d, bl);
    }

    @Override
    @Deprecated
    default public Boolean computeIfAbsent(Double d, Function<? super Double, ? extends Boolean> function) {
        return Map.super.computeIfAbsent(d, function);
    }

    @Override
    @Deprecated
    default public Boolean computeIfPresent(Double d, BiFunction<? super Double, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.computeIfPresent(d, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean compute(Double d, BiFunction<? super Double, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.compute(d, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean merge(Double d, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.merge(d, bl, biFunction);
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
        return this.put((Double)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Double)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Double)object, (BiFunction<? super Double, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Boolean>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Double)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Double)object, (Boolean)object2, (Boolean)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Double)object, (Boolean)object2);
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
    extends Map.Entry<Double, Boolean> {
        public double getDoubleKey();

        @Override
        @Deprecated
        default public Double getKey() {
            return this.getDoubleKey();
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

