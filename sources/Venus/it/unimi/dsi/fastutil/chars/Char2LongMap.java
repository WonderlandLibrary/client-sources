/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2LongFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.longs.LongCollection;
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
import java.util.function.IntToLongFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Char2LongMap
extends Char2LongFunction,
Map<Character, Long> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(long var1);

    @Override
    public long defaultReturnValue();

    public ObjectSet<Entry> char2LongEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Character, Long>> entrySet() {
        return this.char2LongEntrySet();
    }

    @Override
    @Deprecated
    default public Long put(Character c, Long l) {
        return Char2LongFunction.super.put(c, l);
    }

    @Override
    @Deprecated
    default public Long get(Object object) {
        return Char2LongFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Long remove(Object object) {
        return Char2LongFunction.super.remove(object);
    }

    public CharSet keySet();

    public LongCollection values();

    @Override
    public boolean containsKey(char var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Char2LongFunction.super.containsKey(object);
    }

    public boolean containsValue(long var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Long)object);
    }

    default public long getOrDefault(char c, long l) {
        long l2 = this.get(c);
        return l2 != this.defaultReturnValue() || this.containsKey(c) ? l2 : l;
    }

    @Override
    default public long putIfAbsent(char c, long l) {
        long l2;
        long l3 = this.get(c);
        if (l3 != (l2 = this.defaultReturnValue()) || this.containsKey(c)) {
            return l3;
        }
        this.put(c, l);
        return l2;
    }

    default public boolean remove(char c, long l) {
        long l2 = this.get(c);
        if (l2 != l || l2 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.remove(c);
        return false;
    }

    @Override
    default public boolean replace(char c, long l, long l2) {
        long l3 = this.get(c);
        if (l3 != l || l3 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.put(c, l2);
        return false;
    }

    @Override
    default public long replace(char c, long l) {
        return this.containsKey(c) ? this.put(c, l) : this.defaultReturnValue();
    }

    default public long computeIfAbsent(char c, IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        long l = this.get(c);
        if (l != this.defaultReturnValue() || this.containsKey(c)) {
            return l;
        }
        long l2 = intToLongFunction.applyAsLong(c);
        this.put(c, l2);
        return l2;
    }

    default public long computeIfAbsentNullable(char c, IntFunction<? extends Long> intFunction) {
        Objects.requireNonNull(intFunction);
        long l = this.get(c);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(c)) {
            return l;
        }
        Long l3 = intFunction.apply(c);
        if (l3 == null) {
            return l2;
        }
        long l4 = l3;
        this.put(c, l4);
        return l4;
    }

    default public long computeIfAbsentPartial(char c, Char2LongFunction char2LongFunction) {
        Objects.requireNonNull(char2LongFunction);
        long l = this.get(c);
        long l2 = this.defaultReturnValue();
        if (l != l2 || this.containsKey(c)) {
            return l;
        }
        if (!char2LongFunction.containsKey(c)) {
            return l2;
        }
        long l3 = char2LongFunction.get(c);
        this.put(c, l3);
        return l3;
    }

    @Override
    default public long computeIfPresent(char c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(c);
        long l2 = this.defaultReturnValue();
        if (l == l2 && !this.containsKey(c)) {
            return l2;
        }
        Long l3 = biFunction.apply(Character.valueOf(c), (Long)l);
        if (l3 == null) {
            this.remove(c);
            return l2;
        }
        long l4 = l3;
        this.put(c, l4);
        return l4;
    }

    @Override
    default public long compute(char c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
        Objects.requireNonNull(biFunction);
        long l = this.get(c);
        long l2 = this.defaultReturnValue();
        boolean bl = l != l2 || this.containsKey(c);
        Long l3 = biFunction.apply(Character.valueOf(c), bl ? Long.valueOf(l) : null);
        if (l3 == null) {
            if (bl) {
                this.remove(c);
            }
            return l2;
        }
        long l4 = l3;
        this.put(c, l4);
        return l4;
    }

    @Override
    default public long merge(char c, long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        long l2;
        Objects.requireNonNull(biFunction);
        long l3 = this.get(c);
        long l4 = this.defaultReturnValue();
        if (l3 != l4 || this.containsKey(c)) {
            Long l5 = biFunction.apply((Long)l3, (Long)l);
            if (l5 == null) {
                this.remove(c);
                return l4;
            }
            l2 = l5;
        } else {
            l2 = l;
        }
        this.put(c, l2);
        return l2;
    }

    @Override
    @Deprecated
    default public Long getOrDefault(Object object, Long l) {
        return Map.super.getOrDefault(object, l);
    }

    @Override
    @Deprecated
    default public Long putIfAbsent(Character c, Long l) {
        return Map.super.putIfAbsent(c, l);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Character c, Long l, Long l2) {
        return Map.super.replace(c, l, l2);
    }

    @Override
    @Deprecated
    default public Long replace(Character c, Long l) {
        return Map.super.replace(c, l);
    }

    @Override
    @Deprecated
    default public Long computeIfAbsent(Character c, Function<? super Character, ? extends Long> function) {
        return Map.super.computeIfAbsent(c, function);
    }

    @Override
    @Deprecated
    default public Long computeIfPresent(Character c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
        return Map.super.computeIfPresent(c, biFunction);
    }

    @Override
    @Deprecated
    default public Long compute(Character c, BiFunction<? super Character, ? super Long, ? extends Long> biFunction) {
        return Map.super.compute(c, biFunction);
    }

    @Override
    @Deprecated
    default public Long merge(Character c, Long l, BiFunction<? super Long, ? super Long, ? extends Long> biFunction) {
        return Map.super.merge(c, l, biFunction);
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
        return this.put((Character)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Character)object, (Long)object2, (BiFunction<? super Long, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Character)object, (BiFunction<? super Character, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Long, ? extends Long>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Long>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Character)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Character)object, (Long)object2, (Long)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Character)object, (Long)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Long)object2);
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
    extends Map.Entry<Character, Long> {
        public char getCharKey();

        @Override
        @Deprecated
        default public Character getKey() {
            return Character.valueOf(this.getCharKey());
        }

        public long getLongValue();

        @Override
        public long setValue(long var1);

        @Override
        @Deprecated
        default public Long getValue() {
            return this.getLongValue();
        }

        @Override
        @Deprecated
        default public Long setValue(Long l) {
            return this.setValue((long)l);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Long)object);
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

