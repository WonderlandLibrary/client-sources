/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2IntFunction;
import it.unimi.dsi.fastutil.shorts.ShortSet;
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
public interface Short2IntMap
extends Short2IntFunction,
Map<Short, Integer> {
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

    public ObjectSet<Entry> short2IntEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Short, Integer>> entrySet() {
        return this.short2IntEntrySet();
    }

    @Override
    @Deprecated
    default public Integer put(Short s, Integer n) {
        return Short2IntFunction.super.put(s, n);
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        return Short2IntFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        return Short2IntFunction.super.remove(object);
    }

    public ShortSet keySet();

    public IntCollection values();

    @Override
    public boolean containsKey(short var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Short2IntFunction.super.containsKey(object);
    }

    public boolean containsValue(int var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Integer)object);
    }

    default public int getOrDefault(short s, int n) {
        int n2 = this.get(s);
        return n2 != this.defaultReturnValue() || this.containsKey(s) ? n2 : n;
    }

    @Override
    default public int putIfAbsent(short s, int n) {
        int n2;
        int n3 = this.get(s);
        if (n3 != (n2 = this.defaultReturnValue()) || this.containsKey(s)) {
            return n3;
        }
        this.put(s, n);
        return n2;
    }

    default public boolean remove(short s, int n) {
        int n2 = this.get(s);
        if (n2 != n || n2 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.remove(s);
        return false;
    }

    @Override
    default public boolean replace(short s, int n, int n2) {
        int n3 = this.get(s);
        if (n3 != n || n3 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.put(s, n2);
        return false;
    }

    @Override
    default public int replace(short s, int n) {
        return this.containsKey(s) ? this.put(s, n) : this.defaultReturnValue();
    }

    default public int computeIfAbsent(short s, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        int n = this.get(s);
        if (n != this.defaultReturnValue() || this.containsKey(s)) {
            return n;
        }
        int n2 = intUnaryOperator.applyAsInt(s);
        this.put(s, n2);
        return n2;
    }

    default public int computeIfAbsentNullable(short s, IntFunction<? extends Integer> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.get(s);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(s)) {
            return n;
        }
        Integer n3 = intFunction.apply(s);
        if (n3 == null) {
            return n2;
        }
        int n4 = n3;
        this.put(s, n4);
        return n4;
    }

    default public int computeIfAbsentPartial(short s, Short2IntFunction short2IntFunction) {
        Objects.requireNonNull(short2IntFunction);
        int n = this.get(s);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(s)) {
            return n;
        }
        if (!short2IntFunction.containsKey(s)) {
            return n2;
        }
        int n3 = short2IntFunction.get(s);
        this.put(s, n3);
        return n3;
    }

    @Override
    default public int computeIfPresent(short s, BiFunction<? super Short, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(s);
        int n2 = this.defaultReturnValue();
        if (n == n2 && !this.containsKey(s)) {
            return n2;
        }
        Integer n3 = biFunction.apply((Short)s, (Integer)n);
        if (n3 == null) {
            this.remove(s);
            return n2;
        }
        int n4 = n3;
        this.put(s, n4);
        return n4;
    }

    @Override
    default public int compute(short s, BiFunction<? super Short, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(s);
        int n2 = this.defaultReturnValue();
        boolean bl = n != n2 || this.containsKey(s);
        Integer n3 = biFunction.apply((Short)s, bl ? Integer.valueOf(n) : null);
        if (n3 == null) {
            if (bl) {
                this.remove(s);
            }
            return n2;
        }
        int n4 = n3;
        this.put(s, n4);
        return n4;
    }

    @Override
    default public int merge(short s, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        int n2;
        Objects.requireNonNull(biFunction);
        int n3 = this.get(s);
        int n4 = this.defaultReturnValue();
        if (n3 != n4 || this.containsKey(s)) {
            Integer n5 = biFunction.apply((Integer)n3, (Integer)n);
            if (n5 == null) {
                this.remove(s);
                return n4;
            }
            n2 = n5;
        } else {
            n2 = n;
        }
        this.put(s, n2);
        return n2;
    }

    @Override
    @Deprecated
    default public Integer getOrDefault(Object object, Integer n) {
        return Map.super.getOrDefault(object, n);
    }

    @Override
    @Deprecated
    default public Integer putIfAbsent(Short s, Integer n) {
        return Map.super.putIfAbsent(s, n);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Short s, Integer n, Integer n2) {
        return Map.super.replace(s, n, n2);
    }

    @Override
    @Deprecated
    default public Integer replace(Short s, Integer n) {
        return Map.super.replace(s, n);
    }

    @Override
    @Deprecated
    default public Integer computeIfAbsent(Short s, Function<? super Short, ? extends Integer> function) {
        return Map.super.computeIfAbsent(s, function);
    }

    @Override
    @Deprecated
    default public Integer computeIfPresent(Short s, BiFunction<? super Short, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.computeIfPresent(s, biFunction);
    }

    @Override
    @Deprecated
    default public Integer compute(Short s, BiFunction<? super Short, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.compute(s, biFunction);
    }

    @Override
    @Deprecated
    default public Integer merge(Short s, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.merge(s, n, biFunction);
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
        return this.put((Short)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Short)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Short)object, (BiFunction<? super Short, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Integer>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Short)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Short)object, (Integer)object2, (Integer)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Short)object, (Integer)object2);
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
    extends Map.Entry<Short, Integer> {
        public short getShortKey();

        @Override
        @Deprecated
        default public Short getKey() {
            return this.getShortKey();
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

