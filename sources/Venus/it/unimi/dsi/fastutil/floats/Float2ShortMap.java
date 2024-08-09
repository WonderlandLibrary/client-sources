/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.floats.Float2ShortFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
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
public interface Float2ShortMap
extends Float2ShortFunction,
Map<Float, Short> {
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

    public ObjectSet<Entry> float2ShortEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Float, Short>> entrySet() {
        return this.float2ShortEntrySet();
    }

    @Override
    @Deprecated
    default public Short put(Float f, Short s) {
        return Float2ShortFunction.super.put(f, s);
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        return Float2ShortFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        return Float2ShortFunction.super.remove(object);
    }

    public FloatSet keySet();

    public ShortCollection values();

    @Override
    public boolean containsKey(float var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Float2ShortFunction.super.containsKey(object);
    }

    public boolean containsValue(short var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Short)object);
    }

    default public short getOrDefault(float f, short s) {
        short s2 = this.get(f);
        return s2 != this.defaultReturnValue() || this.containsKey(f) ? s2 : s;
    }

    @Override
    default public short putIfAbsent(float f, short s) {
        short s2;
        short s3 = this.get(f);
        if (s3 != (s2 = this.defaultReturnValue()) || this.containsKey(f)) {
            return s3;
        }
        this.put(f, s);
        return s2;
    }

    default public boolean remove(float f, short s) {
        short s2 = this.get(f);
        if (s2 != s || s2 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.remove(f);
        return false;
    }

    @Override
    default public boolean replace(float f, short s, short s2) {
        short s3 = this.get(f);
        if (s3 != s || s3 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.put(f, s2);
        return false;
    }

    @Override
    default public short replace(float f, short s) {
        return this.containsKey(f) ? this.put(f, s) : this.defaultReturnValue();
    }

    default public short computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        short s = this.get(f);
        if (s != this.defaultReturnValue() || this.containsKey(f)) {
            return s;
        }
        short s2 = SafeMath.safeIntToShort(doubleToIntFunction.applyAsInt(f));
        this.put(f, s2);
        return s2;
    }

    default public short computeIfAbsentNullable(float f, DoubleFunction<? extends Short> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        short s = this.get(f);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(f)) {
            return s;
        }
        Short s3 = doubleFunction.apply(f);
        if (s3 == null) {
            return s2;
        }
        short s4 = s3;
        this.put(f, s4);
        return s4;
    }

    default public short computeIfAbsentPartial(float f, Float2ShortFunction float2ShortFunction) {
        Objects.requireNonNull(float2ShortFunction);
        short s = this.get(f);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(f)) {
            return s;
        }
        if (!float2ShortFunction.containsKey(f)) {
            return s2;
        }
        short s3 = float2ShortFunction.get(f);
        this.put(f, s3);
        return s3;
    }

    @Override
    default public short computeIfPresent(float f, BiFunction<? super Float, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(f);
        short s2 = this.defaultReturnValue();
        if (s == s2 && !this.containsKey(f)) {
            return s2;
        }
        Short s3 = biFunction.apply(Float.valueOf(f), (Short)s);
        if (s3 == null) {
            this.remove(f);
            return s2;
        }
        short s4 = s3;
        this.put(f, s4);
        return s4;
    }

    @Override
    default public short compute(float f, BiFunction<? super Float, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(f);
        short s2 = this.defaultReturnValue();
        boolean bl = s != s2 || this.containsKey(f);
        Short s3 = biFunction.apply(Float.valueOf(f), bl ? Short.valueOf(s) : null);
        if (s3 == null) {
            if (bl) {
                this.remove(f);
            }
            return s2;
        }
        short s4 = s3;
        this.put(f, s4);
        return s4;
    }

    @Override
    default public short merge(float f, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        short s2;
        Objects.requireNonNull(biFunction);
        short s3 = this.get(f);
        short s4 = this.defaultReturnValue();
        if (s3 != s4 || this.containsKey(f)) {
            Short s5 = biFunction.apply((Short)s3, (Short)s);
            if (s5 == null) {
                this.remove(f);
                return s4;
            }
            s2 = s5;
        } else {
            s2 = s;
        }
        this.put(f, s2);
        return s2;
    }

    @Override
    @Deprecated
    default public Short getOrDefault(Object object, Short s) {
        return Map.super.getOrDefault(object, s);
    }

    @Override
    @Deprecated
    default public Short putIfAbsent(Float f, Short s) {
        return Map.super.putIfAbsent(f, s);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Float f, Short s, Short s2) {
        return Map.super.replace(f, s, s2);
    }

    @Override
    @Deprecated
    default public Short replace(Float f, Short s) {
        return Map.super.replace(f, s);
    }

    @Override
    @Deprecated
    default public Short computeIfAbsent(Float f, Function<? super Float, ? extends Short> function) {
        return Map.super.computeIfAbsent(f, function);
    }

    @Override
    @Deprecated
    default public Short computeIfPresent(Float f, BiFunction<? super Float, ? super Short, ? extends Short> biFunction) {
        return Map.super.computeIfPresent(f, biFunction);
    }

    @Override
    @Deprecated
    default public Short compute(Float f, BiFunction<? super Float, ? super Short, ? extends Short> biFunction) {
        return Map.super.compute(f, biFunction);
    }

    @Override
    @Deprecated
    default public Short merge(Float f, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return Map.super.merge(f, s, biFunction);
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
        return this.put((Float)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Float)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Float)object, (BiFunction<? super Float, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Short>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Float)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Float)object, (Short)object2, (Short)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Float)object, (Short)object2);
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
    extends Map.Entry<Float, Short> {
        public float getFloatKey();

        @Override
        @Deprecated
        default public Float getKey() {
            return Float.valueOf(this.getFloatKey());
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

