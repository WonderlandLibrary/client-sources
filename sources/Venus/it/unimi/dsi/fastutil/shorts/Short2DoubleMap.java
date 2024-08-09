/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2DoubleFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Short2DoubleMap
extends Short2DoubleFunction,
Map<Short, Double> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(double var1);

    @Override
    public double defaultReturnValue();

    public ObjectSet<Entry> short2DoubleEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Short, Double>> entrySet() {
        return this.short2DoubleEntrySet();
    }

    @Override
    @Deprecated
    default public Double put(Short s, Double d) {
        return Short2DoubleFunction.super.put(s, d);
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        return Short2DoubleFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        return Short2DoubleFunction.super.remove(object);
    }

    public ShortSet keySet();

    public DoubleCollection values();

    @Override
    public boolean containsKey(short var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Short2DoubleFunction.super.containsKey(object);
    }

    public boolean containsValue(double var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Double)object);
    }

    default public double getOrDefault(short s, double d) {
        double d2 = this.get(s);
        return d2 != this.defaultReturnValue() || this.containsKey(s) ? d2 : d;
    }

    @Override
    default public double putIfAbsent(short s, double d) {
        double d2;
        double d3 = this.get(s);
        if (d3 != (d2 = this.defaultReturnValue()) || this.containsKey(s)) {
            return d3;
        }
        this.put(s, d);
        return d2;
    }

    default public boolean remove(short s, double d) {
        double d2 = this.get(s);
        if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d) || d2 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.remove(s);
        return false;
    }

    @Override
    default public boolean replace(short s, double d, double d2) {
        double d3 = this.get(s);
        if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d) || d3 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.put(s, d2);
        return false;
    }

    @Override
    default public double replace(short s, double d) {
        return this.containsKey(s) ? this.put(s, d) : this.defaultReturnValue();
    }

    default public double computeIfAbsent(short s, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        double d = this.get(s);
        if (d != this.defaultReturnValue() || this.containsKey(s)) {
            return d;
        }
        double d2 = intToDoubleFunction.applyAsDouble(s);
        this.put(s, d2);
        return d2;
    }

    default public double computeIfAbsentNullable(short s, IntFunction<? extends Double> intFunction) {
        Objects.requireNonNull(intFunction);
        double d = this.get(s);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(s)) {
            return d;
        }
        Double d3 = intFunction.apply(s);
        if (d3 == null) {
            return d2;
        }
        double d4 = d3;
        this.put(s, d4);
        return d4;
    }

    default public double computeIfAbsentPartial(short s, Short2DoubleFunction short2DoubleFunction) {
        Objects.requireNonNull(short2DoubleFunction);
        double d = this.get(s);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(s)) {
            return d;
        }
        if (!short2DoubleFunction.containsKey(s)) {
            return d2;
        }
        double d3 = short2DoubleFunction.get(s);
        this.put(s, d3);
        return d3;
    }

    @Override
    default public double computeIfPresent(short s, BiFunction<? super Short, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(s);
        double d2 = this.defaultReturnValue();
        if (d == d2 && !this.containsKey(s)) {
            return d2;
        }
        Double d3 = biFunction.apply((Short)s, (Double)d);
        if (d3 == null) {
            this.remove(s);
            return d2;
        }
        double d4 = d3;
        this.put(s, d4);
        return d4;
    }

    @Override
    default public double compute(short s, BiFunction<? super Short, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(s);
        double d2 = this.defaultReturnValue();
        boolean bl = d != d2 || this.containsKey(s);
        Double d3 = biFunction.apply((Short)s, bl ? Double.valueOf(d) : null);
        if (d3 == null) {
            if (bl) {
                this.remove(s);
            }
            return d2;
        }
        double d4 = d3;
        this.put(s, d4);
        return d4;
    }

    @Override
    default public double merge(short s, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        double d2;
        Objects.requireNonNull(biFunction);
        double d3 = this.get(s);
        double d4 = this.defaultReturnValue();
        if (d3 != d4 || this.containsKey(s)) {
            Double d5 = biFunction.apply((Double)d3, (Double)d);
            if (d5 == null) {
                this.remove(s);
                return d4;
            }
            d2 = d5;
        } else {
            d2 = d;
        }
        this.put(s, d2);
        return d2;
    }

    @Override
    @Deprecated
    default public Double getOrDefault(Object object, Double d) {
        return Map.super.getOrDefault(object, d);
    }

    @Override
    @Deprecated
    default public Double putIfAbsent(Short s, Double d) {
        return Map.super.putIfAbsent(s, d);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Short s, Double d, Double d2) {
        return Map.super.replace(s, d, d2);
    }

    @Override
    @Deprecated
    default public Double replace(Short s, Double d) {
        return Map.super.replace(s, d);
    }

    @Override
    @Deprecated
    default public Double computeIfAbsent(Short s, Function<? super Short, ? extends Double> function) {
        return Map.super.computeIfAbsent(s, function);
    }

    @Override
    @Deprecated
    default public Double computeIfPresent(Short s, BiFunction<? super Short, ? super Double, ? extends Double> biFunction) {
        return Map.super.computeIfPresent(s, biFunction);
    }

    @Override
    @Deprecated
    default public Double compute(Short s, BiFunction<? super Short, ? super Double, ? extends Double> biFunction) {
        return Map.super.compute(s, biFunction);
    }

    @Override
    @Deprecated
    default public Double merge(Short s, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return Map.super.merge(s, d, biFunction);
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
        return this.put((Short)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Short)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Short)object, (BiFunction<? super Short, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Double>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Short)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Short)object, (Double)object2, (Double)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Short)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Double)object2);
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
    extends Map.Entry<Short, Double> {
        public short getShortKey();

        @Override
        @Deprecated
        default public Short getKey() {
            return this.getShortKey();
        }

        public double getDoubleValue();

        @Override
        public double setValue(double var1);

        @Override
        @Deprecated
        default public Double getValue() {
            return this.getDoubleValue();
        }

        @Override
        @Deprecated
        default public Double setValue(Double d) {
            return this.setValue((double)d);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Double)object);
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

