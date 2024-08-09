/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2DoubleFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
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
public interface Char2DoubleMap
extends Char2DoubleFunction,
Map<Character, Double> {
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

    public ObjectSet<Entry> char2DoubleEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Character, Double>> entrySet() {
        return this.char2DoubleEntrySet();
    }

    @Override
    @Deprecated
    default public Double put(Character c, Double d) {
        return Char2DoubleFunction.super.put(c, d);
    }

    @Override
    @Deprecated
    default public Double get(Object object) {
        return Char2DoubleFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Double remove(Object object) {
        return Char2DoubleFunction.super.remove(object);
    }

    public CharSet keySet();

    public DoubleCollection values();

    @Override
    public boolean containsKey(char var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Char2DoubleFunction.super.containsKey(object);
    }

    public boolean containsValue(double var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Double)object);
    }

    default public double getOrDefault(char c, double d) {
        double d2 = this.get(c);
        return d2 != this.defaultReturnValue() || this.containsKey(c) ? d2 : d;
    }

    @Override
    default public double putIfAbsent(char c, double d) {
        double d2;
        double d3 = this.get(c);
        if (d3 != (d2 = this.defaultReturnValue()) || this.containsKey(c)) {
            return d3;
        }
        this.put(c, d);
        return d2;
    }

    default public boolean remove(char c, double d) {
        double d2 = this.get(c);
        if (Double.doubleToLongBits(d2) != Double.doubleToLongBits(d) || d2 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.remove(c);
        return false;
    }

    @Override
    default public boolean replace(char c, double d, double d2) {
        double d3 = this.get(c);
        if (Double.doubleToLongBits(d3) != Double.doubleToLongBits(d) || d3 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.put(c, d2);
        return false;
    }

    @Override
    default public double replace(char c, double d) {
        return this.containsKey(c) ? this.put(c, d) : this.defaultReturnValue();
    }

    default public double computeIfAbsent(char c, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        double d = this.get(c);
        if (d != this.defaultReturnValue() || this.containsKey(c)) {
            return d;
        }
        double d2 = intToDoubleFunction.applyAsDouble(c);
        this.put(c, d2);
        return d2;
    }

    default public double computeIfAbsentNullable(char c, IntFunction<? extends Double> intFunction) {
        Objects.requireNonNull(intFunction);
        double d = this.get(c);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(c)) {
            return d;
        }
        Double d3 = intFunction.apply(c);
        if (d3 == null) {
            return d2;
        }
        double d4 = d3;
        this.put(c, d4);
        return d4;
    }

    default public double computeIfAbsentPartial(char c, Char2DoubleFunction char2DoubleFunction) {
        Objects.requireNonNull(char2DoubleFunction);
        double d = this.get(c);
        double d2 = this.defaultReturnValue();
        if (d != d2 || this.containsKey(c)) {
            return d;
        }
        if (!char2DoubleFunction.containsKey(c)) {
            return d2;
        }
        double d3 = char2DoubleFunction.get(c);
        this.put(c, d3);
        return d3;
    }

    @Override
    default public double computeIfPresent(char c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(c);
        double d2 = this.defaultReturnValue();
        if (d == d2 && !this.containsKey(c)) {
            return d2;
        }
        Double d3 = biFunction.apply(Character.valueOf(c), (Double)d);
        if (d3 == null) {
            this.remove(c);
            return d2;
        }
        double d4 = d3;
        this.put(c, d4);
        return d4;
    }

    @Override
    default public double compute(char c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
        Objects.requireNonNull(biFunction);
        double d = this.get(c);
        double d2 = this.defaultReturnValue();
        boolean bl = d != d2 || this.containsKey(c);
        Double d3 = biFunction.apply(Character.valueOf(c), bl ? Double.valueOf(d) : null);
        if (d3 == null) {
            if (bl) {
                this.remove(c);
            }
            return d2;
        }
        double d4 = d3;
        this.put(c, d4);
        return d4;
    }

    @Override
    default public double merge(char c, double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        double d2;
        Objects.requireNonNull(biFunction);
        double d3 = this.get(c);
        double d4 = this.defaultReturnValue();
        if (d3 != d4 || this.containsKey(c)) {
            Double d5 = biFunction.apply((Double)d3, (Double)d);
            if (d5 == null) {
                this.remove(c);
                return d4;
            }
            d2 = d5;
        } else {
            d2 = d;
        }
        this.put(c, d2);
        return d2;
    }

    @Override
    @Deprecated
    default public Double getOrDefault(Object object, Double d) {
        return Map.super.getOrDefault(object, d);
    }

    @Override
    @Deprecated
    default public Double putIfAbsent(Character c, Double d) {
        return Map.super.putIfAbsent(c, d);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Character c, Double d, Double d2) {
        return Map.super.replace(c, d, d2);
    }

    @Override
    @Deprecated
    default public Double replace(Character c, Double d) {
        return Map.super.replace(c, d);
    }

    @Override
    @Deprecated
    default public Double computeIfAbsent(Character c, Function<? super Character, ? extends Double> function) {
        return Map.super.computeIfAbsent(c, function);
    }

    @Override
    @Deprecated
    default public Double computeIfPresent(Character c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
        return Map.super.computeIfPresent(c, biFunction);
    }

    @Override
    @Deprecated
    default public Double compute(Character c, BiFunction<? super Character, ? super Double, ? extends Double> biFunction) {
        return Map.super.compute(c, biFunction);
    }

    @Override
    @Deprecated
    default public Double merge(Character c, Double d, BiFunction<? super Double, ? super Double, ? extends Double> biFunction) {
        return Map.super.merge(c, d, biFunction);
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
        return this.put((Character)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Character)object, (Double)object2, (BiFunction<? super Double, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Character)object, (BiFunction<? super Character, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Double, ? extends Double>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Double>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Character)object, (Double)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Character)object, (Double)object2, (Double)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Character)object, (Double)object2);
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
    extends Map.Entry<Character, Double> {
        public char getCharKey();

        @Override
        @Deprecated
        default public Character getKey() {
            return Character.valueOf(this.getCharKey());
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

