/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.Float2IntFunction;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
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
public interface Float2IntMap
extends Float2IntFunction,
Map<Float, Integer> {
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

    public ObjectSet<Entry> float2IntEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Float, Integer>> entrySet() {
        return this.float2IntEntrySet();
    }

    @Override
    @Deprecated
    default public Integer put(Float f, Integer n) {
        return Float2IntFunction.super.put(f, n);
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        return Float2IntFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        return Float2IntFunction.super.remove(object);
    }

    public FloatSet keySet();

    public IntCollection values();

    @Override
    public boolean containsKey(float var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Float2IntFunction.super.containsKey(object);
    }

    public boolean containsValue(int var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Integer)object);
    }

    default public int getOrDefault(float f, int n) {
        int n2 = this.get(f);
        return n2 != this.defaultReturnValue() || this.containsKey(f) ? n2 : n;
    }

    @Override
    default public int putIfAbsent(float f, int n) {
        int n2;
        int n3 = this.get(f);
        if (n3 != (n2 = this.defaultReturnValue()) || this.containsKey(f)) {
            return n3;
        }
        this.put(f, n);
        return n2;
    }

    default public boolean remove(float f, int n) {
        int n2 = this.get(f);
        if (n2 != n || n2 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.remove(f);
        return false;
    }

    @Override
    default public boolean replace(float f, int n, int n2) {
        int n3 = this.get(f);
        if (n3 != n || n3 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.put(f, n2);
        return false;
    }

    @Override
    default public int replace(float f, int n) {
        return this.containsKey(f) ? this.put(f, n) : this.defaultReturnValue();
    }

    default public int computeIfAbsent(float f, DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        int n = this.get(f);
        if (n != this.defaultReturnValue() || this.containsKey(f)) {
            return n;
        }
        int n2 = doubleToIntFunction.applyAsInt(f);
        this.put(f, n2);
        return n2;
    }

    default public int computeIfAbsentNullable(float f, DoubleFunction<? extends Integer> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        int n = this.get(f);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(f)) {
            return n;
        }
        Integer n3 = doubleFunction.apply(f);
        if (n3 == null) {
            return n2;
        }
        int n4 = n3;
        this.put(f, n4);
        return n4;
    }

    default public int computeIfAbsentPartial(float f, Float2IntFunction float2IntFunction) {
        Objects.requireNonNull(float2IntFunction);
        int n = this.get(f);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(f)) {
            return n;
        }
        if (!float2IntFunction.containsKey(f)) {
            return n2;
        }
        int n3 = float2IntFunction.get(f);
        this.put(f, n3);
        return n3;
    }

    @Override
    default public int computeIfPresent(float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(f);
        int n2 = this.defaultReturnValue();
        if (n == n2 && !this.containsKey(f)) {
            return n2;
        }
        Integer n3 = biFunction.apply(Float.valueOf(f), (Integer)n);
        if (n3 == null) {
            this.remove(f);
            return n2;
        }
        int n4 = n3;
        this.put(f, n4);
        return n4;
    }

    @Override
    default public int compute(float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(f);
        int n2 = this.defaultReturnValue();
        boolean bl = n != n2 || this.containsKey(f);
        Integer n3 = biFunction.apply(Float.valueOf(f), bl ? Integer.valueOf(n) : null);
        if (n3 == null) {
            if (bl) {
                this.remove(f);
            }
            return n2;
        }
        int n4 = n3;
        this.put(f, n4);
        return n4;
    }

    @Override
    default public int merge(float f, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        int n2;
        Objects.requireNonNull(biFunction);
        int n3 = this.get(f);
        int n4 = this.defaultReturnValue();
        if (n3 != n4 || this.containsKey(f)) {
            Integer n5 = biFunction.apply((Integer)n3, (Integer)n);
            if (n5 == null) {
                this.remove(f);
                return n4;
            }
            n2 = n5;
        } else {
            n2 = n;
        }
        this.put(f, n2);
        return n2;
    }

    @Override
    @Deprecated
    default public Integer getOrDefault(Object object, Integer n) {
        return Map.super.getOrDefault(object, n);
    }

    @Override
    @Deprecated
    default public Integer putIfAbsent(Float f, Integer n) {
        return Map.super.putIfAbsent(f, n);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Float f, Integer n, Integer n2) {
        return Map.super.replace(f, n, n2);
    }

    @Override
    @Deprecated
    default public Integer replace(Float f, Integer n) {
        return Map.super.replace(f, n);
    }

    @Override
    @Deprecated
    default public Integer computeIfAbsent(Float f, Function<? super Float, ? extends Integer> function) {
        return Map.super.computeIfAbsent(f, function);
    }

    @Override
    @Deprecated
    default public Integer computeIfPresent(Float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.computeIfPresent(f, biFunction);
    }

    @Override
    @Deprecated
    default public Integer compute(Float f, BiFunction<? super Float, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.compute(f, biFunction);
    }

    @Override
    @Deprecated
    default public Integer merge(Float f, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.merge(f, n, biFunction);
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
        return this.put((Float)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Float)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Float)object, (BiFunction<? super Float, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Integer>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Float)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Float)object, (Integer)object2, (Integer)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Float)object, (Integer)object2);
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
    extends Map.Entry<Float, Integer> {
        public float getFloatKey();

        @Override
        @Deprecated
        default public Float getKey() {
            return Float.valueOf(this.getFloatKey());
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

