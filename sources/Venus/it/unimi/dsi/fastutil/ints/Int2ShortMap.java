/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.ints.Int2ShortFunction;
import it.unimi.dsi.fastutil.ints.IntSet;
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
public interface Int2ShortMap
extends Int2ShortFunction,
Map<Integer, Short> {
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

    public ObjectSet<Entry> int2ShortEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Integer, Short>> entrySet() {
        return this.int2ShortEntrySet();
    }

    @Override
    @Deprecated
    default public Short put(Integer n, Short s) {
        return Int2ShortFunction.super.put(n, s);
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        return Int2ShortFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        return Int2ShortFunction.super.remove(object);
    }

    public IntSet keySet();

    public ShortCollection values();

    @Override
    public boolean containsKey(int var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Int2ShortFunction.super.containsKey(object);
    }

    public boolean containsValue(short var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Short)object);
    }

    default public short getOrDefault(int n, short s) {
        short s2 = this.get(n);
        return s2 != this.defaultReturnValue() || this.containsKey(n) ? s2 : s;
    }

    @Override
    default public short putIfAbsent(int n, short s) {
        short s2;
        short s3 = this.get(n);
        if (s3 != (s2 = this.defaultReturnValue()) || this.containsKey(n)) {
            return s3;
        }
        this.put(n, s);
        return s2;
    }

    default public boolean remove(int n, short s) {
        short s2 = this.get(n);
        if (s2 != s || s2 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.remove(n);
        return false;
    }

    @Override
    default public boolean replace(int n, short s, short s2) {
        short s3 = this.get(n);
        if (s3 != s || s3 == this.defaultReturnValue() && !this.containsKey(n)) {
            return true;
        }
        this.put(n, s2);
        return false;
    }

    @Override
    default public short replace(int n, short s) {
        return this.containsKey(n) ? this.put(n, s) : this.defaultReturnValue();
    }

    default public short computeIfAbsent(int n, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        short s = this.get(n);
        if (s != this.defaultReturnValue() || this.containsKey(n)) {
            return s;
        }
        short s2 = SafeMath.safeIntToShort(intUnaryOperator.applyAsInt(n));
        this.put(n, s2);
        return s2;
    }

    default public short computeIfAbsentNullable(int n, IntFunction<? extends Short> intFunction) {
        Objects.requireNonNull(intFunction);
        short s = this.get(n);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(n)) {
            return s;
        }
        Short s3 = intFunction.apply(n);
        if (s3 == null) {
            return s2;
        }
        short s4 = s3;
        this.put(n, s4);
        return s4;
    }

    default public short computeIfAbsentPartial(int n, Int2ShortFunction int2ShortFunction) {
        Objects.requireNonNull(int2ShortFunction);
        short s = this.get(n);
        short s2 = this.defaultReturnValue();
        if (s != s2 || this.containsKey(n)) {
            return s;
        }
        if (!int2ShortFunction.containsKey(n)) {
            return s2;
        }
        short s3 = int2ShortFunction.get(n);
        this.put(n, s3);
        return s3;
    }

    @Override
    default public short computeIfPresent(int n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(n);
        short s2 = this.defaultReturnValue();
        if (s == s2 && !this.containsKey(n)) {
            return s2;
        }
        Short s3 = biFunction.apply((Integer)n, (Short)s);
        if (s3 == null) {
            this.remove(n);
            return s2;
        }
        short s4 = s3;
        this.put(n, s4);
        return s4;
    }

    @Override
    default public short compute(int n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s = this.get(n);
        short s2 = this.defaultReturnValue();
        boolean bl = s != s2 || this.containsKey(n);
        Short s3 = biFunction.apply((Integer)n, bl ? Short.valueOf(s) : null);
        if (s3 == null) {
            if (bl) {
                this.remove(n);
            }
            return s2;
        }
        short s4 = s3;
        this.put(n, s4);
        return s4;
    }

    @Override
    default public short merge(int n, short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        short s2;
        Objects.requireNonNull(biFunction);
        short s3 = this.get(n);
        short s4 = this.defaultReturnValue();
        if (s3 != s4 || this.containsKey(n)) {
            Short s5 = biFunction.apply((Short)s3, (Short)s);
            if (s5 == null) {
                this.remove(n);
                return s4;
            }
            s2 = s5;
        } else {
            s2 = s;
        }
        this.put(n, s2);
        return s2;
    }

    @Override
    @Deprecated
    default public Short getOrDefault(Object object, Short s) {
        return Map.super.getOrDefault(object, s);
    }

    @Override
    @Deprecated
    default public Short putIfAbsent(Integer n, Short s) {
        return Map.super.putIfAbsent(n, s);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Integer n, Short s, Short s2) {
        return Map.super.replace(n, s, s2);
    }

    @Override
    @Deprecated
    default public Short replace(Integer n, Short s) {
        return Map.super.replace(n, s);
    }

    @Override
    @Deprecated
    default public Short computeIfAbsent(Integer n, Function<? super Integer, ? extends Short> function) {
        return Map.super.computeIfAbsent(n, function);
    }

    @Override
    @Deprecated
    default public Short computeIfPresent(Integer n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
        return Map.super.computeIfPresent(n, biFunction);
    }

    @Override
    @Deprecated
    default public Short compute(Integer n, BiFunction<? super Integer, ? super Short, ? extends Short> biFunction) {
        return Map.super.compute(n, biFunction);
    }

    @Override
    @Deprecated
    default public Short merge(Integer n, Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return Map.super.merge(n, s, biFunction);
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
        return this.put((Integer)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Integer)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Integer)object, (BiFunction<? super Integer, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Integer)object, (BiFunction<? super Integer, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Integer)object, (Function<? super Integer, ? extends Short>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Integer)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Integer)object, (Short)object2, (Short)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Integer)object, (Short)object2);
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
    extends Map.Entry<Integer, Short> {
        public int getIntKey();

        @Override
        @Deprecated
        default public Integer getKey() {
            return this.getIntKey();
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

