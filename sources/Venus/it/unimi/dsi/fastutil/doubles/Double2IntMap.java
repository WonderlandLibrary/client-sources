/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.Double2IntFunction;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
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
public interface Double2IntMap
extends Double2IntFunction,
Map<Double, Integer> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(int var1);

    @Override
    public int defaultReturnValue();

    public ObjectSet<Entry> double2IntEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Double, Integer>> entrySet() {
        return this.double2IntEntrySet();
    }

    @Override
    @Deprecated
    default public Integer put(Double d, Integer n) {
        return Double2IntFunction.super.put(d, n);
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        return Double2IntFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        return Double2IntFunction.super.remove(object);
    }

    public DoubleSet keySet();

    public IntCollection values();

    @Override
    public boolean containsKey(double var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Double2IntFunction.super.containsKey(object);
    }

    public boolean containsValue(int var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Integer)object);
    }

    default public int getOrDefault(double d, int n) {
        int n2 = this.get(d);
        return n2 != this.defaultReturnValue() || this.containsKey(d) ? n2 : n;
    }

    @Override
    default public int putIfAbsent(double d, int n) {
        int n2;
        int n3 = this.get(d);
        if (n3 != (n2 = this.defaultReturnValue()) || this.containsKey(d)) {
            return n3;
        }
        this.put(d, n);
        return n2;
    }

    default public boolean remove(double d, int n) {
        int n2 = this.get(d);
        if (n2 != n || n2 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.remove(d);
        return false;
    }

    @Override
    default public boolean replace(double d, int n, int n2) {
        int n3 = this.get(d);
        if (n3 != n || n3 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.put(d, n2);
        return false;
    }

    @Override
    default public int replace(double d, int n) {
        return this.containsKey(d) ? this.put(d, n) : this.defaultReturnValue();
    }

    default public int computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        int n = this.get(d);
        if (n != this.defaultReturnValue() || this.containsKey(d)) {
            return n;
        }
        int n2 = doubleToIntFunction.applyAsInt(d);
        this.put(d, n2);
        return n2;
    }

    default public int computeIfAbsentNullable(double d, DoubleFunction<? extends Integer> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.get(d);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(d)) {
            return n;
        }
        Integer n3 = doubleFunction.apply(d);
        if (n3 == null) {
            return n2;
        }
        int n4 = n3;
        this.put(d, n4);
        return n4;
    }

    default public int computeIfAbsentPartial(double d, Double2IntFunction double2IntFunction) {
        Objects.requireNonNull(double2IntFunction);
        int n = this.get(d);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(d)) {
            return n;
        }
        if (!double2IntFunction.containsKey(d)) {
            return n2;
        }
        int n3 = double2IntFunction.get(d);
        this.put(d, n3);
        return n3;
    }

    @Override
    default public int computeIfPresent(double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(d);
        int n2 = this.defaultReturnValue();
        if (n == n2 && !this.containsKey(d)) {
            return n2;
        }
        Integer n3 = biFunction.apply((Double)d, (Integer)n);
        if (n3 == null) {
            this.remove(d);
            return n2;
        }
        int n4 = n3;
        this.put(d, n4);
        return n4;
    }

    @Override
    default public int compute(double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(d);
        int n2 = this.defaultReturnValue();
        boolean bl = n != n2 || this.containsKey(d);
        Integer n3 = biFunction.apply((Double)d, bl ? Integer.valueOf(n) : null);
        if (n3 == null) {
            if (bl) {
                this.remove(d);
            }
            return n2;
        }
        int n4 = n3;
        this.put(d, n4);
        return n4;
    }

    @Override
    default public int merge(double d, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        int n2;
        Objects.requireNonNull(biFunction);
        int n3 = this.get(d);
        int n4 = this.defaultReturnValue();
        if (n3 != n4 || this.containsKey(d)) {
            Integer n5 = biFunction.apply((Integer)n3, (Integer)n);
            if (n5 == null) {
                this.remove(d);
                return n4;
            }
            n2 = n5;
        } else {
            n2 = n;
        }
        this.put(d, n2);
        return n2;
    }

    @Override
    @Deprecated
    default public Integer getOrDefault(Object object, Integer n) {
        return Map.super.getOrDefault(object, n);
    }

    @Override
    @Deprecated
    default public Integer putIfAbsent(Double d, Integer n) {
        return Map.super.putIfAbsent(d, n);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Double d, Integer n, Integer n2) {
        return Map.super.replace(d, n, n2);
    }

    @Override
    @Deprecated
    default public Integer replace(Double d, Integer n) {
        return Map.super.replace(d, n);
    }

    @Override
    @Deprecated
    default public Integer computeIfAbsent(Double d, Function<? super Double, ? extends Integer> function) {
        return Map.super.computeIfAbsent(d, function);
    }

    @Override
    @Deprecated
    default public Integer computeIfPresent(Double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.computeIfPresent(d, biFunction);
    }

    @Override
    @Deprecated
    default public Integer compute(Double d, BiFunction<? super Double, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.compute(d, biFunction);
    }

    @Override
    @Deprecated
    default public Integer merge(Double d, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.merge(d, n, biFunction);
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
        return this.put((Double)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Double)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Double)object, (BiFunction<? super Double, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Integer>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Double)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Double)object, (Integer)object2, (Integer)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Double)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Integer)object2);
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
    extends Map.Entry<Double, Integer> {
        public double getDoubleKey();

        @Override
        @Deprecated
        default public Double getKey() {
            return this.getDoubleKey();
        }

        public int getIntValue();

        @Override
        public int setValue(int var1);

        @Override
        @Deprecated
        default public Integer getValue() {
            return this.getIntValue();
        }

        @Override
        @Deprecated
        default public Integer setValue(Integer n) {
            return this.setValue((int)n);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Integer)object);
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

