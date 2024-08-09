/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.Byte2IntFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.ints.IntCollection;
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
import java.util.function.IntUnaryOperator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Byte2IntMap
extends Byte2IntFunction,
Map<Byte, Integer> {
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

    public ObjectSet<Entry> byte2IntEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Byte, Integer>> entrySet() {
        return this.byte2IntEntrySet();
    }

    @Override
    @Deprecated
    default public Integer put(Byte by, Integer n) {
        return Byte2IntFunction.super.put(by, n);
    }

    @Override
    @Deprecated
    default public Integer get(Object object) {
        return Byte2IntFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Integer remove(Object object) {
        return Byte2IntFunction.super.remove(object);
    }

    public ByteSet keySet();

    public IntCollection values();

    @Override
    public boolean containsKey(byte var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Byte2IntFunction.super.containsKey(object);
    }

    public boolean containsValue(int var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Integer)object);
    }

    default public int getOrDefault(byte by, int n) {
        int n2 = this.get(by);
        return n2 != this.defaultReturnValue() || this.containsKey(by) ? n2 : n;
    }

    @Override
    default public int putIfAbsent(byte by, int n) {
        int n2;
        int n3 = this.get(by);
        if (n3 != (n2 = this.defaultReturnValue()) || this.containsKey(by)) {
            return n3;
        }
        this.put(by, n);
        return n2;
    }

    default public boolean remove(byte by, int n) {
        int n2 = this.get(by);
        if (n2 != n || n2 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.remove(by);
        return false;
    }

    @Override
    default public boolean replace(byte by, int n, int n2) {
        int n3 = this.get(by);
        if (n3 != n || n3 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.put(by, n2);
        return false;
    }

    @Override
    default public int replace(byte by, int n) {
        return this.containsKey(by) ? this.put(by, n) : this.defaultReturnValue();
    }

    default public int computeIfAbsent(byte by, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        int n = this.get(by);
        if (n != this.defaultReturnValue() || this.containsKey(by)) {
            return n;
        }
        int n2 = intUnaryOperator.applyAsInt(by);
        this.put(by, n2);
        return n2;
    }

    default public int computeIfAbsentNullable(byte by, IntFunction<? extends Integer> intFunction) {
        Objects.requireNonNull(intFunction);
        int n = this.get(by);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(by)) {
            return n;
        }
        Integer n3 = intFunction.apply(by);
        if (n3 == null) {
            return n2;
        }
        int n4 = n3;
        this.put(by, n4);
        return n4;
    }

    default public int computeIfAbsentPartial(byte by, Byte2IntFunction byte2IntFunction) {
        Objects.requireNonNull(byte2IntFunction);
        int n = this.get(by);
        int n2 = this.defaultReturnValue();
        if (n != n2 || this.containsKey(by)) {
            return n;
        }
        if (!byte2IntFunction.containsKey(by)) {
            return n2;
        }
        int n3 = byte2IntFunction.get(by);
        this.put(by, n3);
        return n3;
    }

    @Override
    default public int computeIfPresent(byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(by);
        int n2 = this.defaultReturnValue();
        if (n == n2 && !this.containsKey(by)) {
            return n2;
        }
        Integer n3 = biFunction.apply((Byte)by, (Integer)n);
        if (n3 == null) {
            this.remove(by);
            return n2;
        }
        int n4 = n3;
        this.put(by, n4);
        return n4;
    }

    @Override
    default public int compute(byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
        Objects.requireNonNull(biFunction);
        int n = this.get(by);
        int n2 = this.defaultReturnValue();
        boolean bl = n != n2 || this.containsKey(by);
        Integer n3 = biFunction.apply((Byte)by, bl ? Integer.valueOf(n) : null);
        if (n3 == null) {
            if (bl) {
                this.remove(by);
            }
            return n2;
        }
        int n4 = n3;
        this.put(by, n4);
        return n4;
    }

    @Override
    default public int merge(byte by, int n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        int n2;
        Objects.requireNonNull(biFunction);
        int n3 = this.get(by);
        int n4 = this.defaultReturnValue();
        if (n3 != n4 || this.containsKey(by)) {
            Integer n5 = biFunction.apply((Integer)n3, (Integer)n);
            if (n5 == null) {
                this.remove(by);
                return n4;
            }
            n2 = n5;
        } else {
            n2 = n;
        }
        this.put(by, n2);
        return n2;
    }

    @Override
    @Deprecated
    default public Integer getOrDefault(Object object, Integer n) {
        return Map.super.getOrDefault(object, n);
    }

    @Override
    @Deprecated
    default public Integer putIfAbsent(Byte by, Integer n) {
        return Map.super.putIfAbsent(by, n);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Byte by, Integer n, Integer n2) {
        return Map.super.replace(by, n, n2);
    }

    @Override
    @Deprecated
    default public Integer replace(Byte by, Integer n) {
        return Map.super.replace(by, n);
    }

    @Override
    @Deprecated
    default public Integer computeIfAbsent(Byte by, Function<? super Byte, ? extends Integer> function) {
        return Map.super.computeIfAbsent(by, function);
    }

    @Override
    @Deprecated
    default public Integer computeIfPresent(Byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.computeIfPresent(by, biFunction);
    }

    @Override
    @Deprecated
    default public Integer compute(Byte by, BiFunction<? super Byte, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.compute(by, biFunction);
    }

    @Override
    @Deprecated
    default public Integer merge(Byte by, Integer n, BiFunction<? super Integer, ? super Integer, ? extends Integer> biFunction) {
        return Map.super.merge(by, n, biFunction);
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
        return this.put((Byte)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Byte)object, (Integer)object2, (BiFunction<? super Integer, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Byte)object, (BiFunction<? super Byte, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Integer, ? extends Integer>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Integer>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Byte)object, (Integer)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Byte)object, (Integer)object2, (Integer)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Byte)object, (Integer)object2);
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
    extends Map.Entry<Byte, Integer> {
        public byte getByteKey();

        @Override
        @Deprecated
        default public Byte getKey() {
            return this.getByteKey();
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

