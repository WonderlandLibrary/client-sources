/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.objects.Object2CharFunction;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Object2CharMap<K>
extends Object2CharFunction<K>,
Map<K, Character> {
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

    public ObjectSet<Entry<K>> object2CharEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<K, Character>> entrySet() {
        return this.object2CharEntrySet();
    }

    @Override
    @Deprecated
    default public Character put(K k, Character c) {
        return Object2CharFunction.super.put(k, c);
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        return Object2CharFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        return Object2CharFunction.super.remove(object);
    }

    @Override
    public ObjectSet<K> keySet();

    public CharCollection values();

    @Override
    public boolean containsKey(Object var1);

    public boolean containsValue(char var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Character)object).charValue());
    }

    @Override
    default public char getOrDefault(Object object, char c) {
        char c2 = this.getChar(object);
        return c2 != this.defaultReturnValue() || this.containsKey(object) ? c2 : c;
    }

    @Override
    default public char putIfAbsent(K k, char c) {
        char c2;
        char c3 = this.getChar(k);
        if (c3 != (c2 = this.defaultReturnValue()) || this.containsKey(k)) {
            return c3;
        }
        this.put(k, c);
        return c2;
    }

    default public boolean remove(Object object, char c) {
        char c2 = this.getChar(object);
        if (c2 != c || c2 == this.defaultReturnValue() && !this.containsKey(object)) {
            return true;
        }
        this.removeChar(object);
        return false;
    }

    @Override
    default public boolean replace(K k, char c, char c2) {
        char c3 = this.getChar(k);
        if (c3 != c || c3 == this.defaultReturnValue() && !this.containsKey(k)) {
            return true;
        }
        this.put(k, c2);
        return false;
    }

    @Override
    default public char replace(K k, char c) {
        return this.containsKey(k) ? this.put(k, c) : this.defaultReturnValue();
    }

    default public char computeCharIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        char c = this.getChar(k);
        if (c != this.defaultReturnValue() || this.containsKey(k)) {
            return c;
        }
        char c2 = SafeMath.safeIntToChar(toIntFunction.applyAsInt(k));
        this.put(k, c2);
        return c2;
    }

    default public char computeCharIfAbsentPartial(K k, Object2CharFunction<? super K> object2CharFunction) {
        Objects.requireNonNull(object2CharFunction);
        char c = this.getChar(k);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(k)) {
            return c;
        }
        if (!object2CharFunction.containsKey(k)) {
            return c2;
        }
        char c3 = object2CharFunction.getChar(k);
        this.put(k, c3);
        return c3;
    }

    default public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.getChar(k);
        char c2 = this.defaultReturnValue();
        if (c == c2 && !this.containsKey(k)) {
            return c2;
        }
        Character c3 = biFunction.apply(k, Character.valueOf(c));
        if (c3 == null) {
            this.removeChar(k);
            return c2;
        }
        char c4 = c3.charValue();
        this.put(k, c4);
        return c4;
    }

    default public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.getChar(k);
        char c2 = this.defaultReturnValue();
        boolean bl = c != c2 || this.containsKey(k);
        Character c3 = biFunction.apply(k, bl ? Character.valueOf(c) : null);
        if (c3 == null) {
            if (bl) {
                this.removeChar(k);
            }
            return c2;
        }
        char c4 = c3.charValue();
        this.put(k, c4);
        return c4;
    }

    default public char mergeChar(K k, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        char c2;
        Objects.requireNonNull(biFunction);
        char c3 = this.getChar(k);
        char c4 = this.defaultReturnValue();
        if (c3 != c4 || this.containsKey(k)) {
            Character c5 = biFunction.apply(Character.valueOf(c3), Character.valueOf(c));
            if (c5 == null) {
                this.removeChar(k);
                return c4;
            }
            c2 = c5.charValue();
        } else {
            c2 = c;
        }
        this.put(k, c2);
        return c2;
    }

    @Override
    @Deprecated
    default public Character getOrDefault(Object object, Character c) {
        return Map.super.getOrDefault(object, c);
    }

    @Override
    @Deprecated
    default public Character putIfAbsent(K k, Character c) {
        return Map.super.putIfAbsent(k, c);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(K k, Character c, Character c2) {
        return Map.super.replace(k, c, c2);
    }

    @Override
    @Deprecated
    default public Character replace(K k, Character c) {
        return Map.super.replace(k, c);
    }

    @Override
    @Deprecated
    default public Character merge(K k, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        return Map.super.merge(k, c, biFunction);
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
        return this.put((K)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge(object, (Character)object2, (BiFunction<Character, Character, Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((K)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((K)object, (Character)object2, (Character)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((K)object, (Character)object2);
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
    public static interface Entry<K>
    extends Map.Entry<K, Character> {
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
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<K>> consumer) {
            this.forEach(consumer);
        }
    }
}

