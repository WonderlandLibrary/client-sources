/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.Char2CharFunction;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharSet;
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
public interface Char2CharMap
extends Char2CharFunction,
Map<Character, Character> {
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

    public ObjectSet<Entry> char2CharEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Character, Character>> entrySet() {
        return this.char2CharEntrySet();
    }

    @Override
    @Deprecated
    default public Character put(Character c, Character c2) {
        return Char2CharFunction.super.put(c, c2);
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        return Char2CharFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        return Char2CharFunction.super.remove(object);
    }

    public CharSet keySet();

    public CharCollection values();

    @Override
    public boolean containsKey(char var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Char2CharFunction.super.containsKey(object);
    }

    public boolean containsValue(char var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Character)object).charValue());
    }

    default public char getOrDefault(char c, char c2) {
        char c3 = this.get(c);
        return c3 != this.defaultReturnValue() || this.containsKey(c) ? c3 : c2;
    }

    @Override
    default public char putIfAbsent(char c, char c2) {
        char c3;
        char c4 = this.get(c);
        if (c4 != (c3 = this.defaultReturnValue()) || this.containsKey(c)) {
            return c4;
        }
        this.put(c, c2);
        return c3;
    }

    default public boolean remove(char c, char c2) {
        char c3 = this.get(c);
        if (c3 != c2 || c3 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.remove(c);
        return false;
    }

    @Override
    default public boolean replace(char c, char c2, char c3) {
        char c4 = this.get(c);
        if (c4 != c2 || c4 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.put(c, c3);
        return false;
    }

    @Override
    default public char replace(char c, char c2) {
        return this.containsKey(c) ? this.put(c, c2) : this.defaultReturnValue();
    }

    default public char computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        char c2 = this.get(c);
        if (c2 != this.defaultReturnValue() || this.containsKey(c)) {
            return c2;
        }
        char c3 = SafeMath.safeIntToChar(intUnaryOperator.applyAsInt(c));
        this.put(c, c3);
        return c3;
    }

    default public char computeIfAbsentNullable(char c, IntFunction<? extends Character> intFunction) {
        Objects.requireNonNull(intFunction);
        char c2 = this.get(c);
        char c3 = this.defaultReturnValue();
        if (c2 != c3 || this.containsKey(c)) {
            return c2;
        }
        Character c4 = intFunction.apply(c);
        if (c4 == null) {
            return c3;
        }
        char c5 = c4.charValue();
        this.put(c, c5);
        return c5;
    }

    default public char computeIfAbsentPartial(char c, Char2CharFunction char2CharFunction) {
        Objects.requireNonNull(char2CharFunction);
        char c2 = this.get(c);
        char c3 = this.defaultReturnValue();
        if (c2 != c3 || this.containsKey(c)) {
            return c2;
        }
        if (!char2CharFunction.containsKey(c)) {
            return c3;
        }
        char c4 = char2CharFunction.get(c);
        this.put(c, c4);
        return c4;
    }

    @Override
    default public char computeIfPresent(char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c2 = this.get(c);
        char c3 = this.defaultReturnValue();
        if (c2 == c3 && !this.containsKey(c)) {
            return c3;
        }
        Character c4 = biFunction.apply(Character.valueOf(c), Character.valueOf(c2));
        if (c4 == null) {
            this.remove(c);
            return c3;
        }
        char c5 = c4.charValue();
        this.put(c, c5);
        return c5;
    }

    @Override
    default public char compute(char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c2 = this.get(c);
        char c3 = this.defaultReturnValue();
        boolean bl = c2 != c3 || this.containsKey(c);
        Character c4 = biFunction.apply(Character.valueOf(c), bl ? Character.valueOf(c2) : null);
        if (c4 == null) {
            if (bl) {
                this.remove(c);
            }
            return c3;
        }
        char c5 = c4.charValue();
        this.put(c, c5);
        return c5;
    }

    @Override
    default public char merge(char c, char c2, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        char c3;
        Objects.requireNonNull(biFunction);
        char c4 = this.get(c);
        char c5 = this.defaultReturnValue();
        if (c4 != c5 || this.containsKey(c)) {
            Character c6 = biFunction.apply(Character.valueOf(c4), Character.valueOf(c2));
            if (c6 == null) {
                this.remove(c);
                return c5;
            }
            c3 = c6.charValue();
        } else {
            c3 = c2;
        }
        this.put(c, c3);
        return c3;
    }

    @Override
    @Deprecated
    default public Character getOrDefault(Object object, Character c) {
        return Map.super.getOrDefault(object, c);
    }

    @Override
    @Deprecated
    default public Character putIfAbsent(Character c, Character c2) {
        return Map.super.putIfAbsent(c, c2);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Character c, Character c2, Character c3) {
        return Map.super.replace(c, c2, c3);
    }

    @Override
    @Deprecated
    default public Character replace(Character c, Character c2) {
        return Map.super.replace(c, c2);
    }

    @Override
    @Deprecated
    default public Character computeIfAbsent(Character c, Function<? super Character, ? extends Character> function) {
        return Map.super.computeIfAbsent(c, function);
    }

    @Override
    @Deprecated
    default public Character computeIfPresent(Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        return Map.super.computeIfPresent(c, biFunction);
    }

    @Override
    @Deprecated
    default public Character compute(Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        return Map.super.compute(c, biFunction);
    }

    @Override
    @Deprecated
    default public Character merge(Character c, Character c2, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        return Map.super.merge(c, c2, biFunction);
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
        return this.put((Character)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Character)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Character)object, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Character>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Character)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Character)object, (Character)object2, (Character)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Character)object, (Character)object2);
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
    extends Map.Entry<Character, Character> {
        public char getCharKey();

        @Override
        @Deprecated
        default public Character getKey() {
            return Character.valueOf(this.getCharKey());
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

