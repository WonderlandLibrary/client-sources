/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.doubles.Double2ShortFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Double2ShortMap
extends Double2ShortFunction,
Map<Double, Short> {
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

    public ObjectSet<Entry> double2ShortEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Double, Short>> entrySet() {
        return this.double2ShortEntrySet();
    }

    @Override
    @Deprecated
    default public Short put(Double d, Short s) {
        return Double2ShortFunction.super.put(d, s);
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        return Double2ShortFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        return Double2ShortFunction.super.remove(object);
    }

    public DoubleSet keySet();

    public ShortCollection values();

    @Override
    public boolean containsKey(double var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Double2ShortFunction.super.containsKey(object);
    }

    public boolean containsValue(short var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Short)object);
    }

    default public short getOrDefault(double d, short s) {
        short s2 = this.get(d);
        return s2 != this.defaultReturnValue() || this.containsKey(d) ? s2 : s;
    }

    @Override
    default public short putIfAbsent(double d, short s) {
        short s2;
        short s3 = this.get(d);
        if (s3 != (s2 = this.defaultReturnValue()) || this.containsKey(d)) {
            return s3;
        }
        this.put(d, s);
        return s2;
    }

    default public boolean remove(double d, short s) {
        short s2 = this.get(d);
        if (s2 != s || s2 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.remove(d);
        return false;
    }

    @Override
    default public boolean replace(double d, short s, short s2) {
        short s3 = this.get(d);
        if (s3 != s || s3 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.put(d, s2);
        return false;
    }

    @Override
    default public short replace(double d, short s) {
        return this.containsKey(d) ? this.put(d, s) : this.defaultReturnValue();
    }

    default public short computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        short s = this.get(d);
        if (s != this.defaultReturnValue() || this.containsKey(d)) {
            return s;
        }
        short s2 = SafeMath.safeIntToShort(doubleToIntFunction.applyAsInt(d));
        this.put(d, s2);
        return s2;
    }

    default public short computeIfAbsentNullable(double d, DoubleFunction<? extends Short> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        short s = this.get(d);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(d)) {
            return s;
        }
        Short s3 = doubleFunction.apply(d);
        if (s3 == null) {
            return s2;
        }
        short s4 = s3;
        this.put(d, s4);
        return s4;
    }

    default public short computeIfAbsentPartial(double d, Double2ShortFunction double2ShortFunction) {
        Objects.requireNonNull(double2ShortFunction);
        short s = this.get(d);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(d)) {
            return s;
        }
        if (!double2ShortFunction.containsKey(d)) {
            return s2;
        }
        short s3 = double2ShortFunction.get(d);
        this.put(d, s3);
        return s3;
    }

    @Override
    default public short computeIfPresent(double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(d);
        short s2 = this.defaultReturnValue();
        if (s == s2 && !this.containsKey(d)) {
            return s2;
        }
        Short s3 = biFunction.apply((Double)d, (Short)s);
        if (s3 == null) {
            this.remove(d);
            return s2;
        }
        short s4 = s3;
        this.put(d, s4);
        return s4;
    }

    @Override
    default public short compute(double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(d);
        short s2 = this.defaultReturnValue();
        boolean bl = s != s2 || this.containsKey(d);
        Short s3 = biFunction.apply((Double)d, bl ? Short.valueOf(s) : null);
        if (s3 == null) {
            if (bl) {
                this.remove(d);
            }
            return s2;
        }
        short s4 = s3;
        this.put(d, s4);
        return s4;
    }

    @Override
    default public short merge(double d, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        short s2;
        Objects.requireNonNull(biFunction);
        short s3 = this.get(d);
        short s4 = this.defaultReturnValue();
        if (s3 != s4 || this.containsKey(d)) {
            Short s5 = biFunction.apply((Short)s3, (Short)s);
            if (s5 == null) {
                this.remove(d);
                return s4;
            }
            s2 = s5;
        } else {
            s2 = s;
        }
        this.put(d, s2);
        return s2;
    }

    @Override
    @Deprecated
    default public Short getOrDefault(Object object, Short s) {
        return Map.super.getOrDefault(object, s);
    }

    @Override
    @Deprecated
    default public Short putIfAbsent(Double d, Short s) {
        return Map.super.putIfAbsent(d, s);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Double d, Short s, Short s2) {
        return Map.super.replace(d, s, s2);
    }

    @Override
    @Deprecated
    default public Short replace(Double d, Short s) {
        return Map.super.replace(d, s);
    }

    @Override
    @Deprecated
    default public Short computeIfAbsent(Double d, Function<? super Double, ? extends Short> function) {
        return Map.super.computeIfAbsent(d, function);
    }

    @Override
    @Deprecated
    default public Short computeIfPresent(Double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
        return Map.super.computeIfPresent(d, biFunction);
    }

    @Override
    @Deprecated
    default public Short compute(Double d, BiFunction<? super Double, ? super Short, ? extends Short> biFunction) {
        return Map.super.compute(d, biFunction);
    }

    @Override
    @Deprecated
    default public Short merge(Double d, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return Map.super.merge(d, s, biFunction);
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
        return this.put((Double)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Double)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Double)object, (BiFunction<? super Double, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Short>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Double)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Double)object, (Short)object2, (Short)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Double)object, (Short)object2);
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
    extends Map.Entry<Double, Short> {
        public double getDoubleKey();

        @Override
        @Deprecated
        default public Double getKey() {
            return this.getDoubleKey();
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

