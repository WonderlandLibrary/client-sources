/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.floats.Float2CharFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Float2CharMap
extends Float2CharFunction,
Map<Float, Character> {
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

    public ObjectSet<Entry> float2CharEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Float, Character>> entrySet() {
        return this.float2CharEntrySet();
    }

    @Override
    @Deprecated
    default public Character put(Float f, Character c) {
        return Float2CharFunction.super.put(f, c);
    }

    @Override
    @Deprecated
    default public Character get(Object object) {
        return Float2CharFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Character remove(Object object) {
        return Float2CharFunction.super.remove(object);
    }

    public FloatSet keySet();

    public CharCollection values();

    @Override
    public boolean containsKey(float var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Float2CharFunction.super.containsKey(object);
    }

    public boolean containsValue(char var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Character)object).charValue());
    }

    default public char getOrDefault(float f, char c) {
        char c2 = this.get(f);
        return c2 != this.defaultReturnValue() || this.containsKey(f) ? c2 : c;
    }

    @Override
    default public char putIfAbsent(float f, char c) {
        char c2;
        char c3 = this.get(f);
        if (c3 != (c2 = this.defaultReturnValue()) || this.containsKey(f)) {
            return c3;
        }
        this.put(f, c);
        return c2;
    }

    default public boolean remove(float f, char c) {
        char c2 = this.get(f);
        if (c2 != c || c2 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.remove(f);
        return false;
    }

    @Override
    default public boolean replace(float f, char c, char c2) {
        char c3 = this.get(f);
        if (c3 != c || c3 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.put(f, c2);
        return false;
    }

    @Override
    default public char replace(float f, char c) {
        return this.containsKey(f) ? this.put(f, c) : this.defaultReturnValue();
    }

    default public char computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        char c = this.get(f);
        if (c != this.defaultReturnValue() || this.containsKey(f)) {
            return c;
        }
        char c2 = SafeMath.safeIntToChar(doubleToIntFunction.applyAsInt(f));
        this.put(f, c2);
        return c2;
    }

    default public char computeIfAbsentNullable(float f, DoubleFunction<? extends Character> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        char c = this.get(f);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(f)) {
            return c;
        }
        Character c3 = doubleFunction.apply(f);
        if (c3 == null) {
            return c2;
        }
        char c4 = c3.charValue();
        this.put(f, c4);
        return c4;
    }

    default public char computeIfAbsentPartial(float f, Float2CharFunction float2CharFunction) {
        Objects.requireNonNull(float2CharFunction);
        char c = this.get(f);
        char c2 = this.defaultReturnValue();
        if (c != c2 || this.containsKey(f)) {
            return c;
        }
        if (!float2CharFunction.containsKey(f)) {
            return c2;
        }
        char c3 = float2CharFunction.get(f);
        this.put(f, c3);
        return c3;
    }

    @Override
    default public char computeIfPresent(float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(f);
        char c2 = this.defaultReturnValue();
        if (c == c2 && !this.containsKey(f)) {
            return c2;
        }
        Character c3 = biFunction.apply(Float.valueOf(f), Character.valueOf(c));
        if (c3 == null) {
            this.remove(f);
            return c2;
        }
        char c4 = c3.charValue();
        this.put(f, c4);
        return c4;
    }

    @Override
    default public char compute(float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
        Objects.requireNonNull(biFunction);
        char c = this.get(f);
        char c2 = this.defaultReturnValue();
        boolean bl = c != c2 || this.containsKey(f);
        Character c3 = biFunction.apply(Float.valueOf(f), bl ? Character.valueOf(c) : null);
        if (c3 == null) {
            if (bl) {
                this.remove(f);
            }
            return c2;
        }
        char c4 = c3.charValue();
        this.put(f, c4);
        return c4;
    }

    @Override
    default public char merge(float f, char c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        char c2;
        Objects.requireNonNull(biFunction);
        char c3 = this.get(f);
        char c4 = this.defaultReturnValue();
        if (c3 != c4 || this.containsKey(f)) {
            Character c5 = biFunction.apply(Character.valueOf(c3), Character.valueOf(c));
            if (c5 == null) {
                this.remove(f);
                return c4;
            }
            c2 = c5.charValue();
        } else {
            c2 = c;
        }
        this.put(f, c2);
        return c2;
    }

    @Override
    @Deprecated
    default public Character getOrDefault(Object object, Character c) {
        return Map.super.getOrDefault(object, c);
    }

    @Override
    @Deprecated
    default public Character putIfAbsent(Float f, Character c) {
        return Map.super.putIfAbsent(f, c);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Float f, Character c, Character c2) {
        return Map.super.replace(f, c, c2);
    }

    @Override
    @Deprecated
    default public Character replace(Float f, Character c) {
        return Map.super.replace(f, c);
    }

    @Override
    @Deprecated
    default public Character computeIfAbsent(Float f, Function<? super Float, ? extends Character> function) {
        return Map.super.computeIfAbsent(f, function);
    }

    @Override
    @Deprecated
    default public Character computeIfPresent(Float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
        return Map.super.computeIfPresent(f, biFunction);
    }

    @Override
    @Deprecated
    default public Character compute(Float f, BiFunction<? super Float, ? super Character, ? extends Character> biFunction) {
        return Map.super.compute(f, biFunction);
    }

    @Override
    @Deprecated
    default public Character merge(Float f, Character c, BiFunction<? super Character, ? super Character, ? extends Character> biFunction) {
        return Map.super.merge(f, c, biFunction);
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
        return this.put((Float)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Float)object, (Character)object2, (BiFunction<? super Character, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Float)object, (BiFunction<? super Float, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Character, ? extends Character>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Character>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Float)object, (Character)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Float)object, (Character)object2, (Character)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Float)object, (Character)object2);
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
    extends Map.Entry<Float, Character> {
        public float getFloatKey();

        @Override
        @Deprecated
        default public Float getKey() {
            return Float.valueOf(this.getFloatKey());
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

