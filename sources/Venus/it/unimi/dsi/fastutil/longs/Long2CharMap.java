/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.longs.Long2CharFunction;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.LongToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Long2CharMap
extends Long2CharFunction,
Map<Long, Character> {
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

    public ObjectSet<Entry> long2CharEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Long, Character>> entrySet() {
        return this.long2CharEntrySet();
    }

    @Override
    @Deprecated
    default public Character put(Long l, Character c) {
        return Long2CharFunction.super.put(l, c);
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        return Long2CharFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        return Long2CharFunction.super.remove(object);
    }

    public LongSet keySet();

    public CharCollection values();

    @Override
    public boolean containsKey(long var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Long2CharFunction.super.containsKey(object);
    }

    public boolean containsValue(char var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Character)object).charValue());
    }

    default public char getOrDefault(long l, char c) {
        char c2 = this.get(l);
        return c2 != this.defaultReturnValue() || this.containsKey(l) ? c2 : c;
    }

    @Override
    default public char putIfAbsent(long l, char c) {
        char c2;
        char c3 = this.get(l);
        if (c3 != (c2 = this.defaultReturnValue()) || this.containsKey(l)) {
            return c3;
        }
        this.put(l, c);
        return c2;
    }

    default public boolean remove(long l, char c) {
        char c2 = this.get(l);
        if (c2 != c || c2 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.remove(l);
        return false;
    }

    @Override
    default public boolean replace(long l, char c, char c2) {
        char c3 = this.get(l);
        if (c3 != c || c3 == this.defaultReturnValue() && !this.containsKey(l)) {
            return true;
        }
        this.put(l, c2);
        return false;
    }

    @Override
    default public char replace(long l, char c) {
        return this.containsKey(l) ? this.put(l, c) : this.defaultReturnValue();
    }

    default public char computeIfAbsent(long l, LongToIntFunction longToIntFunction) {
        Objects.requireNonNull(longToIntFunction);
        char c = this.get(l);
        if (c != this.defaultReturnValue() || this.containsKey(l)) {
            return c;
        }
        char c2 = SafeMath.safeIntToChar(longToIntFunction.applyAsInt(l));
        this.put(l, c2);
        return c2;
    }

    default public char computeIfAbsentNullable(long l, LongFunction<? extends Character> longFunction) {
        Objects.requireNonNull(longFunction);
        char c = this.get(l);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(l)) {
            return c;
        }
        Character c3 = longFunction.apply(l);
        if (c3 == null) {
            return c2;
        }
        char c4 = c3.charValue();
        this.put(l, c4);
        return c4;
    }

    default public char computeIfAbsentPartial(long l, Long2CharFunction long2CharFunction) {
        Objects.requireNonNull(long2CharFunction);
        char c = this.get(l);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(l)) {
            return c;
        }
        if (!long2CharFunction.containsKey(l)) {
            return c2;
        }
        char c3 = long2CharFunction.get(l);
        this.put(l, c3);
        return c3;
    }

    @Override
    default public char computeIfPresent(long l, BiFunction<? super Long, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(l);
        char c2 = this.defaultReturnValue();
        if (c == c2 && !this.containsKey(l)) {
            return c2;
        }
        Character c3 = biFunction.apply((Long)l, Character.valueOf(c));
        if (c3 == null) {
            this.remove(l);
            return c2;
        }
        char c4 = c3.charValue();
        this.put(l, c4);
        return c4;
    }

    @Override
    default public char compute(long l, BiFunction<? super Long, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(l);
        char c2 = this.defaultReturnValue();
        boolean bl = c != c2 || this.containsKey(l);
        Character c3 = biFunction.apply((Long)l, bl ? Character.valueOf(c) : null);
        if (c3 == null) {
            if (bl) {
                this.remove(l);
            }
            return c2;
        }
        char c4 = c3.charValue();
        this.put(l, c4);
        return c4;
    }

    @Override
    default public char merge(long l, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        char c2;
        Objects.requireNonNull(biFunction);
        char c3 = this.get(l);
        char c4 = this.defaultReturnValue();
        if (c3 != c4 || this.containsKey(l)) {
            Character c5 = biFunction.apply(Character.valueOf(c3), Character.valueOf(c));
            if (c5 == null) {
                this.remove(l);
                return c4;
            }
            c2 = c5.charValue();
        } else {
            c2 = c;
        }
        this.put(l, c2);
        return c2;
    }

    @Override
    @Deprecated
    default public Character getOrDefault(Object object, Character c) {
        return Map.super.getOrDefault(object, c);
    }

    @Override
    @Deprecated
    default public Character putIfAbsent(Long l, Character c) {
        return Map.super.putIfAbsent(l, c);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Long l, Character c, Character c2) {
        return Map.super.replace(l, c, c2);
    }

    @Override
    @Deprecated
    default public Character replace(Long l, Character c) {
        return Map.super.replace(l, c);
    }

    @Override
    @Deprecated
    default public Character computeIfAbsent(Long l, Function<? super Long, ? extends Character> function) {
        return Map.super.computeIfAbsent(l, function);
    }

    @Override
    @Deprecated
    default public Character computeIfPresent(Long l, BiFunction<? super Long, ? super Character, ? extends Character> biFunction) {
        return Map.super.computeIfPresent(l, biFunction);
    }

    @Override
    @Deprecated
    default public Character compute(Long l, BiFunction<? super Long, ? super Character, ? extends Character> biFunction) {
        return Map.super.compute(l, biFunction);
    }

    @Override
    @Deprecated
    default public Character merge(Long l, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        return Map.super.merge(l, c, biFunction);
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
        return this.put((Long)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Long)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Long)object, (BiFunction<? super Long, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Long)object, (BiFunction<? super Long, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Long)object, (Function<? super Long, ? extends Character>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Long)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Long)object, (Character)object2, (Character)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Long)object, (Character)object2);
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
    extends Map.Entry<Long, Character> {
        public long getLongKey();

        @Override
        @Deprecated
        default public Long getKey() {
            return this.getLongKey();
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

