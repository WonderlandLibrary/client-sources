/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.doubles.Double2CharFunction;
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
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Double2CharMap
extends Double2CharFunction,
Map<Double, Character> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(char var1);

    @Override
    public char defaultReturnValue();

    public ObjectSet<Entry> double2CharEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Double, Character>> entrySet() {
        return this.double2CharEntrySet();
    }

    @Override
    @Deprecated
    default public Character put(Double d, Character c) {
        return Double2CharFunction.super.put(d, c);
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        return Double2CharFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        return Double2CharFunction.super.remove(object);
    }

    public DoubleSet keySet();

    public CharCollection values();

    @Override
    public boolean containsKey(double var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Double2CharFunction.super.containsKey(object);
    }

    public boolean containsValue(char var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Character)object).charValue());
    }

    default public char getOrDefault(double d, char c) {
        char c2 = this.get(d);
        return c2 != this.defaultReturnValue() || this.containsKey(d) ? c2 : c;
    }

    @Override
    default public char putIfAbsent(double d, char c) {
        char c2;
        char c3 = this.get(d);
        if (c3 != (c2 = this.defaultReturnValue()) || this.containsKey(d)) {
            return c3;
        }
        this.put(d, c);
        return c2;
    }

    default public boolean remove(double d, char c) {
        char c2 = this.get(d);
        if (c2 != c || c2 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.remove(d);
        return false;
    }

    @Override
    default public boolean replace(double d, char c, char c2) {
        char c3 = this.get(d);
        if (c3 != c || c3 == this.defaultReturnValue() && !this.containsKey(d)) {
            return true;
        }
        this.put(d, c2);
        return false;
    }

    @Override
    default public char replace(double d, char c) {
        return this.containsKey(d) ? this.put(d, c) : this.defaultReturnValue();
    }

    default public char computeIfAbsent(double d, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        char c = this.get(d);
        if (c != this.defaultReturnValue() || this.containsKey(d)) {
            return c;
        }
        char c2 = SafeMath.safeIntToChar(doubleToIntFunction.applyAsInt(d));
        this.put(d, c2);
        return c2;
    }

    default public char computeIfAbsentNullable(double d, DoubleFunction<? extends Character> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        char c = this.get(d);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(d)) {
            return c;
        }
        Character c3 = doubleFunction.apply(d);
        if (c3 == null) {
            return c2;
        }
        char c4 = c3.charValue();
        this.put(d, c4);
        return c4;
    }

    default public char computeIfAbsentPartial(double d, Double2CharFunction double2CharFunction) {
        Objects.requireNonNull(double2CharFunction);
        char c = this.get(d);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(d)) {
            return c;
        }
        if (!double2CharFunction.containsKey(d)) {
            return c2;
        }
        char c3 = double2CharFunction.get(d);
        this.put(d, c3);
        return c3;
    }

    @Override
    default public char computeIfPresent(double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(d);
        char c2 = this.defaultReturnValue();
        if (c == c2 && !this.containsKey(d)) {
            return c2;
        }
        Character c3 = biFunction.apply((Double)d, Character.valueOf(c));
        if (c3 == null) {
            this.remove(d);
            return c2;
        }
        char c4 = c3.charValue();
        this.put(d, c4);
        return c4;
    }

    @Override
    default public char compute(double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(d);
        char c2 = this.defaultReturnValue();
        boolean bl = c != c2 || this.containsKey(d);
        Character c3 = biFunction.apply((Double)d, bl ? Character.valueOf(c) : null);
        if (c3 == null) {
            if (bl) {
                this.remove(d);
            }
            return c2;
        }
        char c4 = c3.charValue();
        this.put(d, c4);
        return c4;
    }

    @Override
    default public char merge(double d, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        char c2;
        Objects.requireNonNull(biFunction);
        char c3 = this.get(d);
        char c4 = this.defaultReturnValue();
        if (c3 != c4 || this.containsKey(d)) {
            Character c5 = biFunction.apply(Character.valueOf(c3), Character.valueOf(c));
            if (c5 == null) {
                this.remove(d);
                return c4;
            }
            c2 = c5.charValue();
        } else {
            c2 = c;
        }
        this.put(d, c2);
        return c2;
    }

    @Override
    @Deprecated
    default public Character getOrDefault(Object object, Character c) {
        return Map.super.getOrDefault(object, c);
    }

    @Override
    @Deprecated
    default public Character putIfAbsent(Double d, Character c) {
        return Map.super.putIfAbsent(d, c);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Double d, Character c, Character c2) {
        return Map.super.replace(d, c, c2);
    }

    @Override
    @Deprecated
    default public Character replace(Double d, Character c) {
        return Map.super.replace(d, c);
    }

    @Override
    @Deprecated
    default public Character computeIfAbsent(Double d, Function<? super Double, ? extends Character> function) {
        return Map.super.computeIfAbsent(d, function);
    }

    @Override
    @Deprecated
    default public Character computeIfPresent(Double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
        return Map.super.computeIfPresent(d, biFunction);
    }

    @Override
    @Deprecated
    default public Character compute(Double d, BiFunction<? super Double, ? super Character, ? extends Character> biFunction) {
        return Map.super.compute(d, biFunction);
    }

    @Override
    @Deprecated
    default public Character merge(Double d, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        return Map.super.merge(d, c, biFunction);
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
        return this.put((Double)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Double)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Double)object, (BiFunction<? super Double, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Double)object, (BiFunction<? super Double, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Double)object, (Function<? super Double, ? extends Character>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Double)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Double)object, (Character)object2, (Character)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Double)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Character)object2);
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
    extends Map.Entry<Double, Character> {
        public double getDoubleKey();

        @Override
        @Deprecated
        default public Double getKey() {
            return this.getDoubleKey();
        }

        public char getCharValue();

        @Override
        public char setValue(char var1);

        @Override
        @Deprecated
        default public Character getValue() {
            return Character.valueOf(this.getCharValue());
        }

        @Override
        @Deprecated
        default public Character setValue(Character c) {
            return Character.valueOf(this.setValue(c.charValue()));
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Character)object);
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

