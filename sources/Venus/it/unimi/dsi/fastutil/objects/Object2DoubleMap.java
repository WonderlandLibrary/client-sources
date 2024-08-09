/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Object2DoubleMap<K>
extends Object2DoubleFunction<K>,
Map<K, Double> {
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

    public ObjectSet<Entry<K>> object2DoubleEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<K, Double>> entrySet() {
        return this.object2DoubleEntrySet();
    }

    @Override
    @Deprecated
    default public Double put(K k, Double d) {
        return Object2DoubleFunction.super.put(k, d);
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        return Object2DoubleFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        return Object2DoubleFunction.super.remove(object);
    }

    @Override
    public ObjectSet<K> keySet();

    public DoubleCollection values();

    @Override
    public boolean containsKey(Object var1);

    public boolean containsValue(double var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Double)object);
    }

    @Override
    default public double getOrDefault(Object object, double d) {
        double d2 = this.getDouble(object);
        return d2 != this.defaultReturnValue() || this.containsKey(object) ? d2 : d;
    }

    @Override
    default public double putIfAbsent(K k, double d) {
        double d2;
        double d3 = this.getDouble(k);
        if (d3 != (d2 = this.defaultReturnValue()) || this.containsKey(k)) {
            return d3;
        }
        this.put(k, d);
        return d2;
    }

    default public boolean remove(Object object, double d) {
        double d2 = this.getDouble(object);
        if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d) || d2 == this.defaultReturnValue() && !this.containsKey(object)) {
            return true;
        }
        this.removeDouble(object);
        return false;
    }

    @Override
    default public boolean replace(K k, double d, double d2) {
        double d3 = this.getDouble(k);
        if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d) || d3 == this.defaultReturnValue() && !this.containsKey(k)) {
            return true;
        }
        this.put(k, d2);
        return false;
    }

    @Override
    default public double replace(K k, double d) {
        return this.containsKey(k) ? this.put(k, d) : this.defaultReturnValue();
    }

    default public double computeDoubleIfAbsent(K k, ToDoubleFunction<? super K> toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        double d = this.getDouble(k);
        if (d != this.defaultReturnValue() || this.containsKey(k)) {
            return d;
        }
        double d2 = toDoubleFunction.applyAsDouble(k);
        this.put(k, d2);
        return d2;
    }

    default public double computeDoubleIfAbsentPartial(K k, Object2DoubleFunction<? super K> object2DoubleFunction) {
        Objects.requireNonNull(object2DoubleFunction);
        double d = this.getDouble(k);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(k)) {
            return d;
        }
        if (!object2DoubleFunction.containsKey(k)) {
            return d2;
        }
        double d3 = object2DoubleFunction.getDouble(k);
        this.put(k, d3);
        return d3;
    }

    default public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.getDouble(k);
        double d2 = this.defaultReturnValue();
        if (d == d2 && !this.containsKey(k)) {
            return d2;
        }
        Double d3 = biFunction.apply(k, d);
        if (d3 == null) {
            this.removeDouble(k);
            return d2;
        }
        double d4 = d3;
        this.put(k, d4);
        return d4;
    }

    default public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.getDouble(k);
        double d2 = this.defaultReturnValue();
        boolean bl = d != d2 || this.containsKey(k);
        Double d3 = biFunction.apply(k, bl ? Double.valueOf(d) : null);
        if (d3 == null) {
            if (bl) {
                this.removeDouble(k);
            }
            return d2;
        }
        double d4 = d3;
        this.put(k, d4);
        return d4;
    }

    default public double mergeDouble(K k, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        double d2;
        Objects.requireNonNull(biFunction);
        double d3 = this.getDouble(k);
        double d4 = this.defaultReturnValue();
        if (d3 != d4 || this.containsKey(k)) {
            Double d5 = biFunction.apply((Double)d3, (Double)d);
            if (d5 == null) {
                this.removeDouble(k);
                return d4;
            }
            d2 = d5;
        } else {
            d2 = d;
        }
        this.put(k, d2);
        return d2;
    }

    @Override
    @Deprecated
    default public Double getOrDefault(Object object, Double d) {
        return Map.super.getOrDefault(object, d);
    }

    @Override
    @Deprecated
    default public Double putIfAbsent(K k, Double d) {
        return Map.super.putIfAbsent(k, d);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(K k, Double d, Double d2) {
        return Map.super.replace(k, d, d2);
    }

    @Override
    @Deprecated
    default public Double replace(K k, Double d) {
        return Map.super.replace(k, d);
    }

    @Override
    @Deprecated
    default public Double merge(K k, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return Map.super.merge(k, d, biFunction);
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
        return this.put((K)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge(object, (Double)object2, (BiFunction<Double, Double, Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((K)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((K)object, (Double)object2, (Double)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((K)object, (Double)object2);
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
    public static interface Entry<K>
    extends Map.Entry<K, Double> {
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
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<K>> consumer) {
            this.forEach(consumer);
        }
    }
}

