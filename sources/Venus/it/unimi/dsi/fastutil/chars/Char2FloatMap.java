/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.Char2FloatFunction;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.floats.FloatCollection;
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
import java.util.function.IntToDoubleFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Char2FloatMap
extends Char2FloatFunction,
Map<Character, Float> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(float var1);

    @Override
    public float defaultReturnValue();

    public ObjectSet<Entry> char2FloatEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Character, Float>> entrySet() {
        return this.char2FloatEntrySet();
    }

    @Override
    @Deprecated
    default public Float put(Character c, Float f) {
        return Char2FloatFunction.super.put(c, f);
    }

    @Override
    @Deprecated
    default public Float get(Object object) {
        return Char2FloatFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Float remove(Object object) {
        return Char2FloatFunction.super.remove(object);
    }

    public CharSet keySet();

    public FloatCollection values();

    @Override
    public boolean containsKey(char var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Char2FloatFunction.super.containsKey(object);
    }

    public boolean containsValue(float var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue(((Float)object).floatValue());
    }

    default public float getOrDefault(char c, float f) {
        float f2 = this.get(c);
        return f2 != this.defaultReturnValue() || this.containsKey(c) ? f2 : f;
    }

    @Override
    default public float putIfAbsent(char c, float f) {
        float f2;
        float f3 = this.get(c);
        if (f3 != (f2 = this.defaultReturnValue()) || this.containsKey(c)) {
            return f3;
        }
        this.put(c, f);
        return f2;
    }

    default public boolean remove(char c, float f) {
        float f2 = this.get(c);
        if (Float.floatToIntBits(f2) != Float.floatToIntBits(f) || f2 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.remove(c);
        return false;
    }

    @Override
    default public boolean replace(char c, float f, float f2) {
        float f3 = this.get(c);
        if (Float.floatToIntBits(f3) != Float.floatToIntBits(f) || f3 == this.defaultReturnValue() && !this.containsKey(c)) {
            return true;
        }
        this.put(c, f2);
        return false;
    }

    @Override
    default public float replace(char c, float f) {
        return this.containsKey(c) ? this.put(c, f) : this.defaultReturnValue();
    }

    default public float computeIfAbsent(char c, IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        float f = this.get(c);
        if (f != this.defaultReturnValue() || this.containsKey(c)) {
            return f;
        }
        float f2 = SafeMath.safeDoubleToFloat(intToDoubleFunction.applyAsDouble(c));
        this.put(c, f2);
        return f2;
    }

    default public float computeIfAbsentNullable(char c, IntFunction<? extends Float> intFunction) {
        Objects.requireNonNull(intFunction);
        float f = this.get(c);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(c)) {
            return f;
        }
        Float f3 = intFunction.apply(c);
        if (f3 == null) {
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(c, f4);
        return f4;
    }

    default public float computeIfAbsentPartial(char c, Char2FloatFunction char2FloatFunction) {
        Objects.requireNonNull(char2FloatFunction);
        float f = this.get(c);
        float f2 = this.defaultReturnValue();
        if (f != f2 || this.containsKey(c)) {
            return f;
        }
        if (!char2FloatFunction.containsKey(c)) {
            return f2;
        }
        float f3 = char2FloatFunction.get(c);
        this.put(c, f3);
        return f3;
    }

    @Override
    default public float computeIfPresent(char c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(c);
        float f2 = this.defaultReturnValue();
        if (f == f2 && !this.containsKey(c)) {
            return f2;
        }
        Float f3 = biFunction.apply(Character.valueOf(c), Float.valueOf(f));
        if (f3 == null) {
            this.remove(c);
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(c, f4);
        return f4;
    }

    @Override
    default public float compute(char c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
        Objects.requireNonNull(biFunction);
        float f = this.get(c);
        float f2 = this.defaultReturnValue();
        boolean bl = f != f2 || this.containsKey(c);
        Float f3 = biFunction.apply(Character.valueOf(c), bl ? Float.valueOf(f) : null);
        if (f3 == null) {
            if (bl) {
                this.remove(c);
            }
            return f2;
        }
        float f4 = f3.floatValue();
        this.put(c, f4);
        return f4;
    }

    @Override
    default public float merge(char c, float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        float f2;
        Objects.requireNonNull(biFunction);
        float f3 = this.get(c);
        float f4 = this.defaultReturnValue();
        if (f3 != f4 || this.containsKey(c)) {
            Float f5 = biFunction.apply(Float.valueOf(f3), Float.valueOf(f));
            if (f5 == null) {
                this.remove(c);
                return f4;
            }
            f2 = f5.floatValue();
        } else {
            f2 = f;
        }
        this.put(c, f2);
        return f2;
    }

    @Override
    @Deprecated
    default public Float getOrDefault(Object object, Float f) {
        return Map.super.getOrDefault(object, f);
    }

    @Override
    @Deprecated
    default public Float putIfAbsent(Character c, Float f) {
        return Map.super.putIfAbsent(c, f);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Character c, Float f, Float f2) {
        return Map.super.replace(c, f, f2);
    }

    @Override
    @Deprecated
    default public Float replace(Character c, Float f) {
        return Map.super.replace(c, f);
    }

    @Override
    @Deprecated
    default public Float computeIfAbsent(Character c, Function<? super Character, ? extends Float> function) {
        return Map.super.computeIfAbsent(c, function);
    }

    @Override
    @Deprecated
    default public Float computeIfPresent(Character c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
        return Map.super.computeIfPresent(c, biFunction);
    }

    @Override
    @Deprecated
    default public Float compute(Character c, BiFunction<? super Character, ? super Float, ? extends Float> biFunction) {
        return Map.super.compute(c, biFunction);
    }

    @Override
    @Deprecated
    default public Float merge(Character c, Float f, BiFunction<? super Float, ? super Float, ? extends Float> biFunction) {
        return Map.super.merge(c, f, biFunction);
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
        return this.put((Character)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Character)object, (Float)object2, (BiFunction<? super Float, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Character)object, (BiFunction<? super Character, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Character)object, (BiFunction<? super Character, ? super Float, ? extends Float>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Character)object, (Function<? super Character, ? extends Float>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Character)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Character)object, (Float)object2, (Float)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Character)object, (Float)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Float)object2);
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
    extends Map.Entry<Character, Float> {
        public char getCharKey();

        @Override
        @Deprecated
        default public Character getKey() {
            return Character.valueOf(this.getCharKey());
        }

        public float getFloatValue();

        @Override
        public float setValue(float var1);

        @Override
        @Deprecated
        default public Float getValue() {
            return Float.valueOf(this.getFloatValue());
        }

        @Override
        @Deprecated
        default public Float setValue(Float f) {
            return Float.valueOf(this.setValue(f.floatValue()));
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Float)object);
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

