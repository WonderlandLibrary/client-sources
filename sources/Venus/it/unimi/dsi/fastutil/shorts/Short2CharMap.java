/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2CharFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
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
public interface Short2CharMap
extends Short2CharFunction,
Map<Short, Character> {
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

    public ObjectSet<Entry> short2CharEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Short, Character>> entrySet() {
        return this.short2CharEntrySet();
    }

    @Override
    @Deprecated
    default public Character put(Short s, Character c) {
        return Short2CharFunction.super.put(s, c);
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        return Short2CharFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        return Short2CharFunction.super.remove(object);
    }

    public ShortSet keySet();

    public CharCollection values();

    @Override
    public boolean containsKey(short var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Short2CharFunction.super.containsKey(object);
    }

    public boolean containsValue(char var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Character)object).charValue());
    }

    default public char getOrDefault(short s, char c) {
        char c2 = this.get(s);
        return c2 != this.defaultReturnValue() || this.containsKey(s) ? c2 : c;
    }

    @Override
    default public char putIfAbsent(short s, char c) {
        char c2;
        char c3 = this.get(s);
        if (c3 != (c2 = this.defaultReturnValue()) || this.containsKey(s)) {
            return c3;
        }
        this.put(s, c);
        return c2;
    }

    default public boolean remove(short s, char c) {
        char c2 = this.get(s);
        if (c2 != c || c2 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.remove(s);
        return false;
    }

    @Override
    default public boolean replace(short s, char c, char c2) {
        char c3 = this.get(s);
        if (c3 != c || c3 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.put(s, c2);
        return false;
    }

    @Override
    default public char replace(short s, char c) {
        return this.containsKey(s) ? this.put(s, c) : this.defaultReturnValue();
    }

    default public char computeIfAbsent(short s, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        char c = this.get(s);
        if (c != this.defaultReturnValue() || this.containsKey(s)) {
            return c;
        }
        char c2 = SafeMath.safeIntToChar(intUnaryOperator.applyAsInt(s));
        this.put(s, c2);
        return c2;
    }

    default public char computeIfAbsentNullable(short s, IntFunction<? extends Character> intFunction) {
        Objects.requireNonNull(intFunction);
        char c = this.get(s);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(s)) {
            return c;
        }
        Character c3 = intFunction.apply(s);
        if (c3 == null) {
            return c2;
        }
        char c4 = c3.charValue();
        this.put(s, c4);
        return c4;
    }

    default public char computeIfAbsentPartial(short s, Short2CharFunction short2CharFunction) {
        Objects.requireNonNull(short2CharFunction);
        char c = this.get(s);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(s)) {
            return c;
        }
        if (!short2CharFunction.containsKey(s)) {
            return c2;
        }
        char c3 = short2CharFunction.get(s);
        this.put(s, c3);
        return c3;
    }

    @Override
    default public char computeIfPresent(short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(s);
        char c2 = this.defaultReturnValue();
        if (c == c2 && !this.containsKey(s)) {
            return c2;
        }
        Character c3 = biFunction.apply((Short)s, Character.valueOf(c));
        if (c3 == null) {
            this.remove(s);
            return c2;
        }
        char c4 = c3.charValue();
        this.put(s, c4);
        return c4;
    }

    @Override
    default public char compute(short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(s);
        char c2 = this.defaultReturnValue();
        boolean bl = c != c2 || this.containsKey(s);
        Character c3 = biFunction.apply((Short)s, bl ? Character.valueOf(c) : null);
        if (c3 == null) {
            if (bl) {
                this.remove(s);
            }
            return c2;
        }
        char c4 = c3.charValue();
        this.put(s, c4);
        return c4;
    }

    @Override
    default public char merge(short s, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        char c2;
        Objects.requireNonNull(biFunction);
        char c3 = this.get(s);
        char c4 = this.defaultReturnValue();
        if (c3 != c4 || this.containsKey(s)) {
            Character c5 = biFunction.apply(Character.valueOf(c3), Character.valueOf(c));
            if (c5 == null) {
                this.remove(s);
                return c4;
            }
            c2 = c5.charValue();
        } else {
            c2 = c;
        }
        this.put(s, c2);
        return c2;
    }

    @Override
    @Deprecated
    default public Character getOrDefault(Object object, Character c) {
        return Map.super.getOrDefault(object, c);
    }

    @Override
    @Deprecated
    default public Character putIfAbsent(Short s, Character c) {
        return Map.super.putIfAbsent(s, c);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Short s, Character c, Character c2) {
        return Map.super.replace(s, c, c2);
    }

    @Override
    @Deprecated
    default public Character replace(Short s, Character c) {
        return Map.super.replace(s, c);
    }

    @Override
    @Deprecated
    default public Character computeIfAbsent(Short s, Function<? super Short, ? extends Character> function) {
        return Map.super.computeIfAbsent(s, function);
    }

    @Override
    @Deprecated
    default public Character computeIfPresent(Short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
        return Map.super.computeIfPresent(s, biFunction);
    }

    @Override
    @Deprecated
    default public Character compute(Short s, BiFunction<? super Short, ? super Character, ? extends Character> biFunction) {
        return Map.super.compute(s, biFunction);
    }

    @Override
    @Deprecated
    default public Character merge(Short s, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        return Map.super.merge(s, c, biFunction);
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
        return this.put((Short)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Short)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Short)object, (BiFunction<? super Short, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Character>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Short)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Short)object, (Character)object2, (Character)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Short)object, (Character)object2);
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
    extends Map.Entry<Short, Character> {
        public short getShortKey();

        @Override
        @Deprecated
        default public Short getKey() {
            return this.getShortKey();
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

