/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2IntFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
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
public interface Char2IntMap
extends Char2IntFunction,
Map<Character, Integer> {
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

    public ObjectSet<Entry> char2IntEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Character, Integer>> entrySet() {
        return this.char2IntEntrySet();
    }

    @Override
    @Deprecated
    default public Integer put(Character c, Integer n) {
        return Char2IntFunction.super.put(c, n);
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        return Char2IntFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        return Char2IntFunction.super.remove(object);
    }

    public CharSet keySet();

    public IntCollection values();

    @Override
    public boolean containsKey(char var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Char2IntFunction.super.containsKey(object);
    }

    public boolean containsValue(int var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Integer)object);
    }

    default public int getOrDefault(char c, int n) {
        int n2 = this.get(c);
        return n2 != this.defaultReturnValue() || this.containsKey(c) ? n2 : n;
    }

    @Override
    default public int putIfAbsent(char c, int n) {
        int n2;
        int n3 = this.get(c);
        if (n3 != (n2 = this.defaultReturnValue()) || this.containsKey(c)) {
            return n3;
        }
        this.put(c, n);
        return n2;
    }

    default public boolean remove(char c, int n) {
        int n2 = this.get(c);
        if (n2 != n || n2 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.remove(c);
        return false;
    }

    @Override
    default public boolean replace(char c, int n, int n2) {
        int n3 = this.get(c);
        if (n3 != n || n3 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.put(c, n2);
        return false;
    }

    @Override
    default public int replace(char c, int n) {
        return this.containsKey(c) ? this.put(c, n) : this.defaultReturnValue();
    }

    default public int computeIfAbsent(char c, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        int n = this.get(c);
        if (n != this.defaultReturnValue() || this.containsKey(c)) {
            return n;
        }
        int n2 = intUnaryOperator.applyAsInt(c);
        this.put(c, n2);
        return n2;
    }

    default public int computeIfAbsentNullable(char c, IntFunction<? extends Integer> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.get(c);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(c)) {
            return n;
        }
        Integer n3 = intFunction.apply(c);
        if (n3 == null) {
            return n2;
        }
        int n4 = n3;
        this.put(c, n4);
        return n4;
    }

    default public int computeIfAbsentPartial(char c, Char2IntFunction char2IntFunction) {
        Objects.requireNonNull(char2IntFunction);
        int n = this.get(c);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(c)) {
            return n;
        }
        if (!char2IntFunction.containsKey(c)) {
            return n2;
        }
        int n3 = char2IntFunction.get(c);
        this.put(c, n3);
        return n3;
    }

    @Override
    default public int computeIfPresent(char c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(c);
        int n2 = this.defaultReturnValue();
        if (n == n2 && !this.containsKey(c)) {
            return n2;
        }
        Integer n3 = biFunction.apply(Character.valueOf(c), (Integer)n);
        if (n3 == null) {
            this.remove(c);
            return n2;
        }
        int n4 = n3;
        this.put(c, n4);
        return n4;
    }

    @Override
    default public int compute(char c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(c);
        int n2 = this.defaultReturnValue();
        boolean bl = n != n2 || this.containsKey(c);
        Integer n3 = biFunction.apply(Character.valueOf(c), bl ? Integer.valueOf(n) : null);
        if (n3 == null) {
            if (bl) {
                this.remove(c);
            }
            return n2;
        }
        int n4 = n3;
        this.put(c, n4);
        return n4;
    }

    @Override
    default public int merge(char c, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        int n2;
        Objects.requireNonNull(biFunction);
        int n3 = this.get(c);
        int n4 = this.defaultReturnValue();
        if (n3 != n4 || this.containsKey(c)) {
            Integer n5 = biFunction.apply((Integer)n3, (Integer)n);
            if (n5 == null) {
                this.remove(c);
                return n4;
            }
            n2 = n5;
        } else {
            n2 = n;
        }
        this.put(c, n2);
        return n2;
    }

    @Override
    @Deprecated
    default public Integer getOrDefault(Object object, Integer n) {
        return Map.super.getOrDefault(object, n);
    }

    @Override
    @Deprecated
    default public Integer putIfAbsent(Character c, Integer n) {
        return Map.super.putIfAbsent(c, n);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Character c, Integer n, Integer n2) {
        return Map.super.replace(c, n, n2);
    }

    @Override
    @Deprecated
    default public Integer replace(Character c, Integer n) {
        return Map.super.replace(c, n);
    }

    @Override
    @Deprecated
    default public Integer computeIfAbsent(Character c, Function<? super Character, ? extends Integer> function) {
        return Map.super.computeIfAbsent(c, function);
    }

    @Override
    @Deprecated
    default public Integer computeIfPresent(Character c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.computeIfPresent(c, biFunction);
    }

    @Override
    @Deprecated
    default public Integer compute(Character c, BiFunction<? super Character, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.compute(c, biFunction);
    }

    @Override
    @Deprecated
    default public Integer merge(Character c, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.merge(c, n, biFunction);
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
        return this.put((Character)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Character)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Character)object, (BiFunction<? super Character, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Integer>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Character)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Character)object, (Integer)object2, (Integer)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Character)object, (Integer)object2);
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
    extends Map.Entry<Character, Integer> {
        public char getCharKey();

        @Override
        @Deprecated
        default public Character getKey() {
            return Character.valueOf(this.getCharKey());
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

