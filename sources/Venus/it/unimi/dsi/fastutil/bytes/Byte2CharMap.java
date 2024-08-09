/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.Byte2CharFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.chars.CharCollection;
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
public interface Byte2CharMap
extends Byte2CharFunction,
Map<Byte, Character> {
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

    public ObjectSet<Entry> byte2CharEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Byte, Character>> entrySet() {
        return this.byte2CharEntrySet();
    }

    @Override
    @Deprecated
    default public Character put(Byte by, Character c) {
        return Byte2CharFunction.super.put(by, c);
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        return Byte2CharFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        return Byte2CharFunction.super.remove(object);
    }

    public ByteSet keySet();

    public CharCollection values();

    @Override
    public boolean containsKey(byte var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Byte2CharFunction.super.containsKey(object);
    }

    public boolean containsValue(char var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Character)object).charValue());
    }

    default public char getOrDefault(byte by, char c) {
        char c2 = this.get(by);
        return c2 != this.defaultReturnValue() || this.containsKey(by) ? c2 : c;
    }

    @Override
    default public char putIfAbsent(byte by, char c) {
        char c2;
        char c3 = this.get(by);
        if (c3 != (c2 = this.defaultReturnValue()) || this.containsKey(by)) {
            return c3;
        }
        this.put(by, c);
        return c2;
    }

    default public boolean remove(byte by, char c) {
        char c2 = this.get(by);
        if (c2 != c || c2 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.remove(by);
        return false;
    }

    @Override
    default public boolean replace(byte by, char c, char c2) {
        char c3 = this.get(by);
        if (c3 != c || c3 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.put(by, c2);
        return false;
    }

    @Override
    default public char replace(byte by, char c) {
        return this.containsKey(by) ? this.put(by, c) : this.defaultReturnValue();
    }

    default public char computeIfAbsent(byte by, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        char c = this.get(by);
        if (c != this.defaultReturnValue() || this.containsKey(by)) {
            return c;
        }
        char c2 = SafeMath.safeIntToChar(intUnaryOperator.applyAsInt(by));
        this.put(by, c2);
        return c2;
    }

    default public char computeIfAbsentNullable(byte by, IntFunction<? extends Character> intFunction) {
        Objects.requireNonNull(intFunction);
        char c = this.get(by);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(by)) {
            return c;
        }
        Character c3 = intFunction.apply(by);
        if (c3 == null) {
            return c2;
        }
        char c4 = c3.charValue();
        this.put(by, c4);
        return c4;
    }

    default public char computeIfAbsentPartial(byte by, Byte2CharFunction byte2CharFunction) {
        Objects.requireNonNull(byte2CharFunction);
        char c = this.get(by);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(by)) {
            return c;
        }
        if (!byte2CharFunction.containsKey(by)) {
            return c2;
        }
        char c3 = byte2CharFunction.get(by);
        this.put(by, c3);
        return c3;
    }

    @Override
    default public char computeIfPresent(byte by, BiFunction<? super Byte, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(by);
        char c2 = this.defaultReturnValue();
        if (c == c2 && !this.containsKey(by)) {
            return c2;
        }
        Character c3 = biFunction.apply((Byte)by, Character.valueOf(c));
        if (c3 == null) {
            this.remove(by);
            return c2;
        }
        char c4 = c3.charValue();
        this.put(by, c4);
        return c4;
    }

    @Override
    default public char compute(byte by, BiFunction<? super Byte, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(by);
        char c2 = this.defaultReturnValue();
        boolean bl = c != c2 || this.containsKey(by);
        Character c3 = biFunction.apply((Byte)by, bl ? Character.valueOf(c) : null);
        if (c3 == null) {
            if (bl) {
                this.remove(by);
            }
            return c2;
        }
        char c4 = c3.charValue();
        this.put(by, c4);
        return c4;
    }

    @Override
    default public char merge(byte by, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        char c2;
        Objects.requireNonNull(biFunction);
        char c3 = this.get(by);
        char c4 = this.defaultReturnValue();
        if (c3 != c4 || this.containsKey(by)) {
            Character c5 = biFunction.apply(Character.valueOf(c3), Character.valueOf(c));
            if (c5 == null) {
                this.remove(by);
                return c4;
            }
            c2 = c5.charValue();
        } else {
            c2 = c;
        }
        this.put(by, c2);
        return c2;
    }

    @Override
    @Deprecated
    default public Character getOrDefault(Object object, Character c) {
        return Map.super.getOrDefault(object, c);
    }

    @Override
    @Deprecated
    default public Character putIfAbsent(Byte by, Character c) {
        return Map.super.putIfAbsent(by, c);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Byte by, Character c, Character c2) {
        return Map.super.replace(by, c, c2);
    }

    @Override
    @Deprecated
    default public Character replace(Byte by, Character c) {
        return Map.super.replace(by, c);
    }

    @Override
    @Deprecated
    default public Character computeIfAbsent(Byte by, Function<? super Byte, ? extends Character> function) {
        return Map.super.computeIfAbsent(by, function);
    }

    @Override
    @Deprecated
    default public Character computeIfPresent(Byte by, BiFunction<? super Byte, ? super Character, ? extends Character> biFunction) {
        return Map.super.computeIfPresent(by, biFunction);
    }

    @Override
    @Deprecated
    default public Character compute(Byte by, BiFunction<? super Byte, ? super Character, ? extends Character> biFunction) {
        return Map.super.compute(by, biFunction);
    }

    @Override
    @Deprecated
    default public Character merge(Byte by, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        return Map.super.merge(by, c, biFunction);
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
        return this.put((Byte)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Byte)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Byte)object, (BiFunction<? super Byte, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Character>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Byte)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Byte)object, (Character)object2, (Character)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Byte)object, (Character)object2);
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
    extends Map.Entry<Byte, Character> {
        public byte getByteKey();

        @Override
        @Deprecated
        default public Byte getKey() {
            return this.getByteKey();
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

