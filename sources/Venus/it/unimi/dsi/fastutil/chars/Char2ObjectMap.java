/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.Char2ObjectFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Char2ObjectMap<V>
extends Char2ObjectFunction<V>,
Map<Character, V> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(V var1);

    @Override
    public V defaultReturnValue();

    public ObjectSet<Entry<V>> char2ObjectEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Character, V>> entrySet() {
        return this.char2ObjectEntrySet();
    }

    @Override
    @Deprecated
    default public V put(Character c, V v) {
        return Char2ObjectFunction.super.put(c, v);
    }

    @Override
    @Deprecated
    default public V get(Object object) {
        return Char2ObjectFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public V remove(Object object) {
        return Char2ObjectFunction.super.remove(object);
    }

    public CharSet keySet();

    @Override
    public ObjectCollection<V> values();

    @Override
    public boolean containsKey(char var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Char2ObjectFunction.super.containsKey(object);
    }

    default public V getOrDefault(char c, V v) {
        Object v2 = this.get(c);
        return v2 != this.defaultReturnValue() || this.containsKey(c) ? v2 : v;
    }

    @Override
    default public V putIfAbsent(char c, V v) {
        V v2;
        Object v3 = this.get(c);
        if (v3 != (v2 = this.defaultReturnValue()) || this.containsKey(c)) {
            return v3;
        }
        this.put(c, v);
        return v2;
    }

    default public boolean remove(char c, Object object) {
        Object v = this.get(c);
        if (!Objects.equals(v, object) || v == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.remove(c);
        return false;
    }

    @Override
    default public boolean replace(char c, V v, V v2) {
        Object v3 = this.get(c);
        if (!Objects.equals(v3, v) || v3 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.put(c, v2);
        return false;
    }

    @Override
    default public V replace(char c, V v) {
        return this.containsKey(c) ? this.put(c, v) : this.defaultReturnValue();
    }

    default public V computeIfAbsent(char c, IntFunction<? extends V> intFunction) {
        Objects.requireNonNull(intFunction);
        Object v = this.get(c);
        if (v != this.defaultReturnValue() || this.containsKey(c)) {
            return v;
        }
        V v2 = intFunction.apply(c);
        this.put(c, v2);
        return v2;
    }

    default public V computeIfAbsentPartial(char c, Char2ObjectFunction<? extends V> char2ObjectFunction) {
        Objects.requireNonNull(char2ObjectFunction);
        Object v = this.get(c);
        V v2 = this.defaultReturnValue();
        if (v != v2 || this.containsKey(c)) {
            return v;
        }
        if (!char2ObjectFunction.containsKey(c)) {
            return v2;
        }
        V v3 = char2ObjectFunction.get(c);
        this.put(c, v3);
        return v3;
    }

    @Override
    default public V computeIfPresent(char c, BiFunction<? super Character, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(c);
        V v2 = this.defaultReturnValue();
        if (v == v2 && !this.containsKey(c)) {
            return v2;
        }
        V v3 = biFunction.apply(Character.valueOf(c), v);
        if (v3 == null) {
            this.remove(c);
            return v2;
        }
        this.put(c, v3);
        return v3;
    }

    @Override
    default public V compute(char c, BiFunction<? super Character, ? super V, ? extends V> biFunction) {
        Objects.requireNonNull(biFunction);
        Object v = this.get(c);
        V v2 = this.defaultReturnValue();
        boolean bl = v != v2 || this.containsKey(c);
        V v3 = biFunction.apply(Character.valueOf(c), bl ? v : null);
        if (v3 == null) {
            if (bl) {
                this.remove(c);
            }
            return v2;
        }
        this.put(c, v3);
        return v3;
    }

    @Override
    default public V merge(char c, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        V v2;
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(v);
        Object v3 = this.get(c);
        V v4 = this.defaultReturnValue();
        if (v3 != v4 || this.containsKey(c)) {
            V v5 = biFunction.apply(v3, v);
            if (v5 == null) {
                this.remove(c);
                return v4;
            }
            v2 = v5;
        } else {
            v2 = v;
        }
        this.put(c, v2);
        return v2;
    }

    @Override
    @Deprecated
    default public V getOrDefault(Object object, V v) {
        return Map.super.getOrDefault(object, v);
    }

    @Override
    @Deprecated
    default public V putIfAbsent(Character c, V v) {
        return Map.super.putIfAbsent(c, v);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Character c, V v, V v2) {
        return Map.super.replace(c, v, v2);
    }

    @Override
    @Deprecated
    default public V replace(Character c, V v) {
        return Map.super.replace(c, v);
    }

    @Override
    @Deprecated
    default public V computeIfAbsent(Character c, Function<? super Character, ? extends V> function) {
        return Map.super.computeIfAbsent(c, function);
    }

    @Override
    @Deprecated
    default public V computeIfPresent(Character c, BiFunction<? super Character, ? super V, ? extends V> biFunction) {
        return Map.super.computeIfPresent(c, biFunction);
    }

    @Override
    @Deprecated
    default public V compute(Character c, BiFunction<? super Character, ? super V, ? extends V> biFunction) {
        return Map.super.compute(c, biFunction);
    }

    @Override
    @Deprecated
    default public V merge(Character c, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        return Map.super.merge(c, v, biFunction);
    }

    @Override
    @Deprecated
    default public Object put(Object object, Object object2) {
        return this.put((Character)object, (V)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Character)object, (V)object2, (BiFunction<? super V, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Character)object, (BiFunction<? super Character, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super V, ? extends V>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Character)object, function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Character)object, (V)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Character)object, (V)object2, (V)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Character)object, (V)object2);
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
    public static interface Entry<V>
    extends Map.Entry<Character, V> {
        public char getCharKey();

        @Override
        @Deprecated
        default public Character getKey() {
            return Character.valueOf(this.getCharKey());
        }

        @Override
        @Deprecated
        default public Object getKey() {
            return this.getKey();
        }
    }

    public static interface FastEntrySet<V>
    extends ObjectSet<Entry<V>> {
        public ObjectIterator<Entry<V>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<V>> consumer) {
            this.forEach(consumer);
        }
    }
}

