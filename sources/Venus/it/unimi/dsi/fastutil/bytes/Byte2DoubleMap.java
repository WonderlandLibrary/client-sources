/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2DoubleFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
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
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Byte2DoubleMap
extends Byte2DoubleFunction,
Map<Byte, Double> {
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

    public ObjectSet<Entry> byte2DoubleEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Byte, Double>> entrySet() {
        return this.byte2DoubleEntrySet();
    }

    @Override
    @Deprecated
    default public Double put(Byte by, Double d) {
        return Byte2DoubleFunction.super.put(by, d);
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        return Byte2DoubleFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        return Byte2DoubleFunction.super.remove(object);
    }

    public ByteSet keySet();

    public DoubleCollection values();

    @Override
    public boolean containsKey(byte var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Byte2DoubleFunction.super.containsKey(object);
    }

    public boolean containsValue(double var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Double)object);
    }

    default public double getOrDefault(byte by, double d) {
        double d2 = this.get(by);
        return d2 != this.defaultReturnValue() || this.containsKey(by) ? d2 : d;
    }

    @Override
    default public double putIfAbsent(byte by, double d) {
        double d2;
        double d3 = this.get(by);
        if (d3 != (d2 = this.defaultReturnValue()) || this.containsKey(by)) {
            return d3;
        }
        this.put(by, d);
        return d2;
    }

    default public boolean remove(byte by, double d) {
        double d2 = this.get(by);
        if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d) || d2 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.remove(by);
        return false;
    }

    @Override
    default public boolean replace(byte by, double d, double d2) {
        double d3 = this.get(by);
        if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d) || d3 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.put(by, d2);
        return false;
    }

    @Override
    default public double replace(byte by, double d) {
        return this.containsKey(by) ? this.put(by, d) : this.defaultReturnValue();
    }

    default public double computeIfAbsent(byte by, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        double d = this.get(by);
        if (d != this.defaultReturnValue() || this.containsKey(by)) {
            return d;
        }
        double d2 = intToDoubleFunction.applyAsDouble(by);
        this.put(by, d2);
        return d2;
    }

    default public double computeIfAbsentNullable(byte by, IntFunction<? extends Double> intFunction) {
        Objects.requireNonNull(intFunction);
        double d = this.get(by);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(by)) {
            return d;
        }
        Double d3 = intFunction.apply(by);
        if (d3 == null) {
            return d2;
        }
        double d4 = d3;
        this.put(by, d4);
        return d4;
    }

    default public double computeIfAbsentPartial(byte by, Byte2DoubleFunction byte2DoubleFunction) {
        Objects.requireNonNull(byte2DoubleFunction);
        double d = this.get(by);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(by)) {
            return d;
        }
        if (!byte2DoubleFunction.containsKey(by)) {
            return d2;
        }
        double d3 = byte2DoubleFunction.get(by);
        this.put(by, d3);
        return d3;
    }

    @Override
    default public double computeIfPresent(byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(by);
        double d2 = this.defaultReturnValue();
        if (d == d2 && !this.containsKey(by)) {
            return d2;
        }
        Double d3 = biFunction.apply((Byte)by, (Double)d);
        if (d3 == null) {
            this.remove(by);
            return d2;
        }
        double d4 = d3;
        this.put(by, d4);
        return d4;
    }

    @Override
    default public double compute(byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(by);
        double d2 = this.defaultReturnValue();
        boolean bl = d != d2 || this.containsKey(by);
        Double d3 = biFunction.apply((Byte)by, bl ? Double.valueOf(d) : null);
        if (d3 == null) {
            if (bl) {
                this.remove(by);
            }
            return d2;
        }
        double d4 = d3;
        this.put(by, d4);
        return d4;
    }

    @Override
    default public double merge(byte by, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        double d2;
        Objects.requireNonNull(biFunction);
        double d3 = this.get(by);
        double d4 = this.defaultReturnValue();
        if (d3 != d4 || this.containsKey(by)) {
            Double d5 = biFunction.apply((Double)d3, (Double)d);
            if (d5 == null) {
                this.remove(by);
                return d4;
            }
            d2 = d5;
        } else {
            d2 = d;
        }
        this.put(by, d2);
        return d2;
    }

    @Override
    @Deprecated
    default public Double getOrDefault(Object object, Double d) {
        return Map.super.getOrDefault(object, d);
    }

    @Override
    @Deprecated
    default public Double putIfAbsent(Byte by, Double d) {
        return Map.super.putIfAbsent(by, d);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Byte by, Double d, Double d2) {
        return Map.super.replace(by, d, d2);
    }

    @Override
    @Deprecated
    default public Double replace(Byte by, Double d) {
        return Map.super.replace(by, d);
    }

    @Override
    @Deprecated
    default public Double computeIfAbsent(Byte by, Function<? super Byte, ? extends Double> function) {
        return Map.super.computeIfAbsent(by, function);
    }

    @Override
    @Deprecated
    default public Double computeIfPresent(Byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
        return Map.super.computeIfPresent(by, biFunction);
    }

    @Override
    @Deprecated
    default public Double compute(Byte by, BiFunction<? super Byte, ? super Double, ? extends Double> biFunction) {
        return Map.super.compute(by, biFunction);
    }

    @Override
    @Deprecated
    default public Double merge(Byte by, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return Map.super.merge(by, d, biFunction);
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
        return this.put((Byte)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Byte)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Byte)object, (BiFunction<? super Byte, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Double>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Byte)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Byte)object, (Double)object2, (Double)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Byte)object, (Double)object2);
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
    extends Map.Entry<Byte, Double> {
        public byte getByteKey();

        @Override
        @Deprecated
        default public Byte getKey() {
            return this.getByteKey();
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

