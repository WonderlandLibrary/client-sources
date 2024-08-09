/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.Char2ShortFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
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
public interface Char2ShortMap
extends Char2ShortFunction,
Map<Character, Short> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(short var1);

    @Override
    public short defaultReturnValue();

    public ObjectSet<Entry> char2ShortEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Character, Short>> entrySet() {
        return this.char2ShortEntrySet();
    }

    @Override
    @Deprecated
    default public Short put(Character c, Short s) {
        return Char2ShortFunction.super.put(c, s);
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        return Char2ShortFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        return Char2ShortFunction.super.remove(object);
    }

    public CharSet keySet();

    public ShortCollection values();

    @Override
    public boolean containsKey(char var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Char2ShortFunction.super.containsKey(object);
    }

    public boolean containsValue(short var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Short)object);
    }

    default public short getOrDefault(char c, short s) {
        short s2 = this.get(c);
        return s2 != this.defaultReturnValue() || this.containsKey(c) ? s2 : s;
    }

    @Override
    default public short putIfAbsent(char c, short s) {
        short s2;
        short s3 = this.get(c);
        if (s3 != (s2 = this.defaultReturnValue()) || this.containsKey(c)) {
            return s3;
        }
        this.put(c, s);
        return s2;
    }

    default public boolean remove(char c, short s) {
        short s2 = this.get(c);
        if (s2 != s || s2 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.remove(c);
        return false;
    }

    @Override
    default public boolean replace(char c, short s, short s2) {
        short s3 = this.get(c);
        if (s3 != s || s3 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.put(c, s2);
        return false;
    }

    @Override
    default public short replace(char c, short s) {
        return this.containsKey(c) ? this.put(c, s) : this.defaultReturnValue();
    }

    default public short computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        short s = this.get(c);
        if (s != this.defaultReturnValue() || this.containsKey(c)) {
            return s;
        }
        short s2 = SafeMath.safeIntToShort(intUnaryOperator.applyAsInt(c));
        this.put(c, s2);
        return s2;
    }

    default public short computeIfAbsentNullable(char c, IntFunction<? extends Short> intFunction) {
        Objects.requireNonNull(intFunction);
        short s = this.get(c);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(c)) {
            return s;
        }
        Short s3 = intFunction.apply(c);
        if (s3 == null) {
            return s2;
        }
        short s4 = s3;
        this.put(c, s4);
        return s4;
    }

    default public short computeIfAbsentPartial(char c, Char2ShortFunction char2ShortFunction) {
        Objects.requireNonNull(char2ShortFunction);
        short s = this.get(c);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(c)) {
            return s;
        }
        if (!char2ShortFunction.containsKey(c)) {
            return s2;
        }
        short s3 = char2ShortFunction.get(c);
        this.put(c, s3);
        return s3;
    }

    @Override
    default public short computeIfPresent(char c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(c);
        short s2 = this.defaultReturnValue();
        if (s == s2 && !this.containsKey(c)) {
            return s2;
        }
        Short s3 = biFunction.apply(Character.valueOf(c), (Short)s);
        if (s3 == null) {
            this.remove(c);
            return s2;
        }
        short s4 = s3;
        this.put(c, s4);
        return s4;
    }

    @Override
    default public short compute(char c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(c);
        short s2 = this.defaultReturnValue();
        boolean bl = s != s2 || this.containsKey(c);
        Short s3 = biFunction.apply(Character.valueOf(c), bl ? Short.valueOf(s) : null);
        if (s3 == null) {
            if (bl) {
                this.remove(c);
            }
            return s2;
        }
        short s4 = s3;
        this.put(c, s4);
        return s4;
    }

    @Override
    default public short merge(char c, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        short s2;
        Objects.requireNonNull(biFunction);
        short s3 = this.get(c);
        short s4 = this.defaultReturnValue();
        if (s3 != s4 || this.containsKey(c)) {
            Short s5 = biFunction.apply((Short)s3, (Short)s);
            if (s5 == null) {
                this.remove(c);
                return s4;
            }
            s2 = s5;
        } else {
            s2 = s;
        }
        this.put(c, s2);
        return s2;
    }

    @Override
    @Deprecated
    default public Short getOrDefault(Object object, Short s) {
        return Map.super.getOrDefault(object, s);
    }

    @Override
    @Deprecated
    default public Short putIfAbsent(Character c, Short s) {
        return Map.super.putIfAbsent(c, s);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Character c, Short s, Short s2) {
        return Map.super.replace(c, s, s2);
    }

    @Override
    @Deprecated
    default public Short replace(Character c, Short s) {
        return Map.super.replace(c, s);
    }

    @Override
    @Deprecated
    default public Short computeIfAbsent(Character c, Function<? super Character, ? extends Short> function) {
        return Map.super.computeIfAbsent(c, function);
    }

    @Override
    @Deprecated
    default public Short computeIfPresent(Character c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
        return Map.super.computeIfPresent(c, biFunction);
    }

    @Override
    @Deprecated
    default public Short compute(Character c, BiFunction<? super Character, ? super Short, ? extends Short> biFunction) {
        return Map.super.compute(c, biFunction);
    }

    @Override
    @Deprecated
    default public Short merge(Character c, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return Map.super.merge(c, s, biFunction);
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
        return this.put((Character)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Character)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Character)object, (BiFunction<? super Character, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Short>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Character)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Character)object, (Short)object2, (Short)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Character)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Short)object2);
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
    extends Map.Entry<Character, Short> {
        public char getCharKey();

        @Override
        @Deprecated
        default public Character getKey() {
            return Character.valueOf(this.getCharKey());
        }

        public short getShortValue();

        @Override
        public short setValue(short var1);

        @Override
        @Deprecated
        default public Short getValue() {
            return this.getShortValue();
        }

        @Override
        @Deprecated
        default public Short setValue(Short s) {
            return this.setValue((short)s);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Short)object);
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

