/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.ints.Int2CharFunction;
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
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Int2CharMap
extends Int2CharFunction,
Map<Integer, Character> {
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

    public ObjectSet<Entry> int2CharEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Integer, Character>> entrySet() {
        return this.int2CharEntrySet();
    }

    @Override
    @Deprecated
    default public Character put(Integer n, Character c) {
        return Int2CharFunction.super.put(n, c);
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        return Int2CharFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        return Int2CharFunction.super.remove(object);
    }

    public IntSet keySet();

    public CharCollection values();

    @Override
    public boolean containsKey(int var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Int2CharFunction.super.containsKey(object);
    }

    public boolean containsValue(char var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Character)object).charValue());
    }

    default public char getOrDefault(int n, char c) {
        char c2 = this.get(n);
        return c2 != this.defaultReturnValue() || this.containsKey(n) ? c2 : c;
    }

    @Override
    default public char putIfAbsent(int n, char c) {
        char c2;
        char c3 = this.get(n);
        if (c3 != (c2 = this.defaultReturnValue()) || this.containsKey(n)) {
            return c3;
        }
        this.put(n, c);
        return c2;
    }

    default public boolean remove(int n, char c) {
        char c2 = this.get(n);
        if (c2 != c || c2 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.remove(n);
        return false;
    }

    @Override
    default public boolean replace(int n, char c, char c2) {
        char c3 = this.get(n);
        if (c3 != c || c3 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.put(n, c2);
        return false;
    }

    @Override
    default public char replace(int n, char c) {
        return this.containsKey(n) ? this.put(n, c) : this.defaultReturnValue();
    }

    default public char computeIfAbsent(int n, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        char c = this.get(n);
        if (c != this.defaultReturnValue() || this.containsKey(n)) {
            return c;
        }
        char c2 = SafeMath.safeIntToChar(intUnaryOperator.applyAsInt(n));
        this.put(n, c2);
        return c2;
    }

    default public char computeIfAbsentNullable(int n, IntFunction<? extends Character> intFunction) {
        Objects.requireNonNull(intFunction);
        char c = this.get(n);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(n)) {
            return c;
        }
        Character c3 = intFunction.apply(n);
        if (c3 == null) {
            return c2;
        }
        char c4 = c3.charValue();
        this.put(n, c4);
        return c4;
    }

    default public char computeIfAbsentPartial(int n, Int2CharFunction int2CharFunction) {
        Objects.requireNonNull(int2CharFunction);
        char c = this.get(n);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(n)) {
            return c;
        }
        if (!int2CharFunction.containsKey(n)) {
            return c2;
        }
        char c3 = int2CharFunction.get(n);
        this.put(n, c3);
        return c3;
    }

    @Override
    default public char computeIfPresent(int n, BiFunction<? super Integer, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(n);
        char c2 = this.defaultReturnValue();
        if (c == c2 && !this.containsKey(n)) {
            return c2;
        }
        Character c3 = biFunction.apply((Integer)n, Character.valueOf(c));
        if (c3 == null) {
            this.remove(n);
            return c2;
        }
        char c4 = c3.charValue();
        this.put(n, c4);
        return c4;
    }

    @Override
    default public char compute(int n, BiFunction<? super Integer, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(n);
        char c2 = this.defaultReturnValue();
        boolean bl = c != c2 || this.containsKey(n);
        Character c3 = biFunction.apply((Integer)n, bl ? Character.valueOf(c) : null);
        if (c3 == null) {
            if (bl) {
                this.remove(n);
            }
            return c2;
        }
        char c4 = c3.charValue();
        this.put(n, c4);
        return c4;
    }

    @Override
    default public char merge(int n, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        char c2;
        Objects.requireNonNull(biFunction);
        char c3 = this.get(n);
        char c4 = this.defaultReturnValue();
        if (c3 != c4 || this.containsKey(n)) {
            Character c5 = biFunction.apply(Character.valueOf(c3), Character.valueOf(c));
            if (c5 == null) {
                this.remove(n);
                return c4;
            }
            c2 = c5.charValue();
        } else {
            c2 = c;
        }
        this.put(n, c2);
        return c2;
    }

    @Override
    @Deprecated
    default public Character getOrDefault(Object object, Character c) {
        return Map.super.getOrDefault(object, c);
    }

    @Override
    @Deprecated
    default public Character putIfAbsent(Integer n, Character c) {
        return Map.super.putIfAbsent(n, c);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Integer n, Character c, Character c2) {
        return Map.super.replace(n, c, c2);
    }

    @Override
    @Deprecated
    default public Character replace(Integer n, Character c) {
        return Map.super.replace(n, c);
    }

    @Override
    @Deprecated
    default public Character computeIfAbsent(Integer n, Function<? super Integer, ? extends Character> function) {
        return Map.super.computeIfAbsent(n, function);
    }

    @Override
    @Deprecated
    default public Character computeIfPresent(Integer n, BiFunction<? super Integer, ? super Character, ? extends Character> biFunction) {
        return Map.super.computeIfPresent(n, biFunction);
    }

    @Override
    @Deprecated
    default public Character compute(Integer n, BiFunction<? super Integer, ? super Character, ? extends Character> biFunction) {
        return Map.super.compute(n, biFunction);
    }

    @Override
    @Deprecated
    default public Character merge(Integer n, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        return Map.super.merge(n, c, biFunction);
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
        return this.put((Integer)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Integer)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Integer)object, (BiFunction<? super Integer, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Character>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Integer)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Integer)object, (Character)object2, (Character)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Integer)object, (Character)object2);
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
    extends Map.Entry<Integer, Character> {
        public int getIntKey();

        @Override
        @Deprecated
        default public Integer getKey() {
            return this.getIntKey();
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

