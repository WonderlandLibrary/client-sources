/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.chars.Char2ByteFunction;
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
public interface Char2ByteMap
extends Char2ByteFunction,
Map<Character, Byte> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(byte var1);

    @Override
    public byte defaultReturnValue();

    public ObjectSet<Entry> char2ByteEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Character, Byte>> entrySet() {
        return this.char2ByteEntrySet();
    }

    @Override
    @Deprecated
    default public Byte put(Character c, Byte by) {
        return Char2ByteFunction.super.put(c, by);
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        return Char2ByteFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        return Char2ByteFunction.super.remove(object);
    }

    public CharSet keySet();

    public ByteCollection values();

    @Override
    public boolean containsKey(char var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Char2ByteFunction.super.containsKey(object);
    }

    public boolean containsValue(byte var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Byte)object);
    }

    default public byte getOrDefault(char c, byte by) {
        byte by2 = this.get(c);
        return by2 != this.defaultReturnValue() || this.containsKey(c) ? by2 : by;
    }

    @Override
    default public byte putIfAbsent(char c, byte by) {
        byte by2;
        byte by3 = this.get(c);
        if (by3 != (by2 = this.defaultReturnValue()) || this.containsKey(c)) {
            return by3;
        }
        this.put(c, by);
        return by2;
    }

    default public boolean remove(char c, byte by) {
        byte by2 = this.get(c);
        if (by2 != by || by2 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.remove(c);
        return false;
    }

    @Override
    default public boolean replace(char c, byte by, byte by2) {
        byte by3 = this.get(c);
        if (by3 != by || by3 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.put(c, by2);
        return false;
    }

    @Override
    default public byte replace(char c, byte by) {
        return this.containsKey(c) ? this.put(c, by) : this.defaultReturnValue();
    }

    default public byte computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        byte by = this.get(c);
        if (by != this.defaultReturnValue() || this.containsKey(c)) {
            return by;
        }
        byte by2 = SafeMath.safeIntToByte(intUnaryOperator.applyAsInt(c));
        this.put(c, by2);
        return by2;
    }

    default public byte computeIfAbsentNullable(char c, IntFunction<? extends Byte> intFunction) {
        Objects.requireNonNull(intFunction);
        byte by = this.get(c);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(c)) {
            return by;
        }
        Byte by3 = intFunction.apply(c);
        if (by3 == null) {
            return by2;
        }
        byte by4 = by3;
        this.put(c, by4);
        return by4;
    }

    default public byte computeIfAbsentPartial(char c, Char2ByteFunction char2ByteFunction) {
        Objects.requireNonNull(char2ByteFunction);
        byte by = this.get(c);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(c)) {
            return by;
        }
        if (!char2ByteFunction.containsKey(c)) {
            return by2;
        }
        byte by3 = char2ByteFunction.get(c);
        this.put(c, by3);
        return by3;
    }

    @Override
    default public byte computeIfPresent(char c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(c);
        byte by2 = this.defaultReturnValue();
        if (by == by2 && !this.containsKey(c)) {
            return by2;
        }
        Byte by3 = biFunction.apply(Character.valueOf(c), (Byte)by);
        if (by3 == null) {
            this.remove(c);
            return by2;
        }
        byte by4 = by3;
        this.put(c, by4);
        return by4;
    }

    @Override
    default public byte compute(char c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.get(c);
        byte by2 = this.defaultReturnValue();
        boolean bl = by != by2 || this.containsKey(c);
        Byte by3 = biFunction.apply(Character.valueOf(c), bl ? Byte.valueOf(by) : null);
        if (by3 == null) {
            if (bl) {
                this.remove(c);
            }
            return by2;
        }
        byte by4 = by3;
        this.put(c, by4);
        return by4;
    }

    @Override
    default public byte merge(char c, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        byte by2;
        Objects.requireNonNull(biFunction);
        byte by3 = this.get(c);
        byte by4 = this.defaultReturnValue();
        if (by3 != by4 || this.containsKey(c)) {
            Byte by5 = biFunction.apply((Byte)by3, (Byte)by);
            if (by5 == null) {
                this.remove(c);
                return by4;
            }
            by2 = by5;
        } else {
            by2 = by;
        }
        this.put(c, by2);
        return by2;
    }

    @Override
    @Deprecated
    default public Byte getOrDefault(Object object, Byte by) {
        return Map.super.getOrDefault(object, by);
    }

    @Override
    @Deprecated
    default public Byte putIfAbsent(Character c, Byte by) {
        return Map.super.putIfAbsent(c, by);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Character c, Byte by, Byte by2) {
        return Map.super.replace(c, by, by2);
    }

    @Override
    @Deprecated
    default public Byte replace(Character c, Byte by) {
        return Map.super.replace(c, by);
    }

    @Override
    @Deprecated
    default public Byte computeIfAbsent(Character c, Function<? super Character, ? extends Byte> function) {
        return Map.super.computeIfAbsent(c, function);
    }

    @Override
    @Deprecated
    default public Byte computeIfPresent(Character c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.computeIfPresent(c, biFunction);
    }

    @Override
    @Deprecated
    default public Byte compute(Character c, BiFunction<? super Character, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.compute(c, biFunction);
    }

    @Override
    @Deprecated
    default public Byte merge(Character c, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.merge(c, by, biFunction);
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
        return this.put((Character)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Character)object, (Byte)object2, (BiFunction<? super Byte, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Character)object, (BiFunction<? super Character, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Byte, ? extends Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Byte>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Character)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Character)object, (Byte)object2, (Byte)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Character)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Byte)object2);
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
    extends Map.Entry<Character, Byte> {
        public char getCharKey();

        @Override
        @Deprecated
        default public Character getKey() {
            return Character.valueOf(this.getCharKey());
        }

        public byte getByteValue();

        @Override
        public byte setValue(byte var1);

        @Override
        @Deprecated
        default public Byte getValue() {
            return this.getByteValue();
        }

        @Override
        @Deprecated
        default public Byte setValue(Byte by) {
            return this.setValue((byte)by);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Byte)object);
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

