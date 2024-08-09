/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.shorts.Short2ShortFunction;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
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
public interface Short2ShortMap
extends Short2ShortFunction,
Map<Short, Short> {
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

    public ObjectSet<Entry> short2ShortEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Short, Short>> entrySet() {
        return this.short2ShortEntrySet();
    }

    @Override
    @Deprecated
    default public Short put(Short s, Short s2) {
        return Short2ShortFunction.super.put(s, s2);
    }

    @Override
    @Deprecated
    default public Short get(Object object) {
        return Short2ShortFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Short remove(Object object) {
        return Short2ShortFunction.super.remove(object);
    }

    public ShortSet keySet();

    public ShortCollection values();

    @Override
    public boolean containsKey(short var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Short2ShortFunction.super.containsKey(object);
    }

    public boolean containsValue(short var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Short)object);
    }

    default public short getOrDefault(short s, short s2) {
        short s3 = this.get(s);
        return s3 != this.defaultReturnValue() || this.containsKey(s) ? s3 : s2;
    }

    @Override
    default public short putIfAbsent(short s, short s2) {
        short s3;
        short s4 = this.get(s);
        if (s4 != (s3 = this.defaultReturnValue()) || this.containsKey(s)) {
            return s4;
        }
        this.put(s, s2);
        return s3;
    }

    default public boolean remove(short s, short s2) {
        short s3 = this.get(s);
        if (s3 != s2 || s3 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.remove(s);
        return false;
    }

    @Override
    default public boolean replace(short s, short s2, short s3) {
        short s4 = this.get(s);
        if (s4 != s2 || s4 == this.defaultReturnValue() && !this.containsKey(s)) {
            return true;
        }
        this.put(s, s3);
        return false;
    }

    @Override
    default public short replace(short s, short s2) {
        return this.containsKey(s) ? this.put(s, s2) : this.defaultReturnValue();
    }

    default public short computeIfAbsent(short s, IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        short s2 = this.get(s);
        if (s2 != this.defaultReturnValue() || this.containsKey(s)) {
            return s2;
        }
        short s3 = SafeMath.safeIntToShort(intUnaryOperator.applyAsInt(s));
        this.put(s, s3);
        return s3;
    }

    default public short computeIfAbsentNullable(short s, IntFunction<? extends Short> intFunction) {
        Objects.requireNonNull(intFunction);
        short s2 = this.get(s);
        short s3 = this.defaultReturnValue();
        if (s2 != s3 || this.containsKey(s)) {
            return s2;
        }
        Short s4 = intFunction.apply(s);
        if (s4 == null) {
            return s3;
        }
        short s5 = s4;
        this.put(s, s5);
        return s5;
    }

    default public short computeIfAbsentPartial(short s, Short2ShortFunction short2ShortFunction) {
        Objects.requireNonNull(short2ShortFunction);
        short s2 = this.get(s);
        short s3 = this.defaultReturnValue();
        if (s2 != s3 || this.containsKey(s)) {
            return s2;
        }
        if (!short2ShortFunction.containsKey(s)) {
            return s3;
        }
        short s4 = short2ShortFunction.get(s);
        this.put(s, s4);
        return s4;
    }

    @Override
    default public short computeIfPresent(short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s2 = this.get(s);
        short s3 = this.defaultReturnValue();
        if (s2 == s3 && !this.containsKey(s)) {
            return s3;
        }
        Short s4 = biFunction.apply((Short)s, (Short)s2);
        if (s4 == null) {
            this.remove(s);
            return s3;
        }
        short s5 = s4;
        this.put(s, s5);
        return s5;
    }

    @Override
    default public short compute(short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        Objects.requireNonNull(biFunction);
        short s2 = this.get(s);
        short s3 = this.defaultReturnValue();
        boolean bl = s2 != s3 || this.containsKey(s);
        Short s4 = biFunction.apply((Short)s, bl ? Short.valueOf(s2) : null);
        if (s4 == null) {
            if (bl) {
                this.remove(s);
            }
            return s3;
        }
        short s5 = s4;
        this.put(s, s5);
        return s5;
    }

    @Override
    default public short merge(short s, short s2, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        short s3;
        Objects.requireNonNull(biFunction);
        short s4 = this.get(s);
        short s5 = this.defaultReturnValue();
        if (s4 != s5 || this.containsKey(s)) {
            Short s6 = biFunction.apply((Short)s4, (Short)s2);
            if (s6 == null) {
                this.remove(s);
                return s5;
            }
            s3 = s6;
        } else {
            s3 = s2;
        }
        this.put(s, s3);
        return s3;
    }

    @Override
    @Deprecated
    default public Short getOrDefault(Object object, Short s) {
        return Map.super.getOrDefault(object, s);
    }

    @Override
    @Deprecated
    default public Short putIfAbsent(Short s, Short s2) {
        return Map.super.putIfAbsent(s, s2);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Short s, Short s2, Short s3) {
        return Map.super.replace(s, s2, s3);
    }

    @Override
    @Deprecated
    default public Short replace(Short s, Short s2) {
        return Map.super.replace(s, s2);
    }

    @Override
    @Deprecated
    default public Short computeIfAbsent(Short s, Function<? super Short, ? extends Short> function) {
        return Map.super.computeIfAbsent(s, function);
    }

    @Override
    @Deprecated
    default public Short computeIfPresent(Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return Map.super.computeIfPresent(s, biFunction);
    }

    @Override
    @Deprecated
    default public Short compute(Short s, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return Map.super.compute(s, biFunction);
    }

    @Override
    @Deprecated
    default public Short merge(Short s, Short s2, BiFunction<? super Short, ? super Short, ? extends Short> biFunction) {
        return Map.super.merge(s, s2, biFunction);
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
        return this.put((Short)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Short)object, (Short)object2, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Short)object, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Short)object, (BiFunction<? super Short, ? super Short, ? extends Short>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Short)object, (Function<? super Short, ? extends Short>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Short)object, (Short)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Short)object, (Short)object2, (Short)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Short)object, (Short)object2);
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
    extends Map.Entry<Short, Short> {
        public short getShortKey();

        @Override
        @Deprecated
        default public Short getKey() {
            return this.getShortKey();
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

