/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanFunction;
import it.unimi.dsi.fastutil.bytes.ByteSet;
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
import java.util.function.IntPredicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Byte2BooleanMap
extends Byte2BooleanFunction,
Map<Byte, Boolean> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(boolean var1);

    @Override
    public boolean defaultReturnValue();

    public ObjectSet<Entry> byte2BooleanEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Byte, Boolean>> entrySet() {
        return this.byte2BooleanEntrySet();
    }

    @Override
    @Deprecated
    default public Boolean put(Byte by, Boolean bl) {
        return Byte2BooleanFunction.super.put(by, bl);
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        return Byte2BooleanFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        return Byte2BooleanFunction.super.remove(object);
    }

    public ByteSet keySet();

    public BooleanCollection values();

    @Override
    public boolean containsKey(byte var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Byte2BooleanFunction.super.containsKey(object);
    }

    public boolean containsValue(boolean var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Boolean)object);
    }

    default public boolean getOrDefault(byte by, boolean bl) {
        boolean bl2 = this.get(by);
        return bl2 != this.defaultReturnValue() || this.containsKey(by) ? bl2 : bl;
    }

    @Override
    default public boolean putIfAbsent(byte by, boolean bl) {
        boolean bl2;
        boolean bl3 = this.get(by);
        if (bl3 != (bl2 = this.defaultReturnValue()) || this.containsKey(by)) {
            return bl3;
        }
        this.put(by, bl);
        return bl2;
    }

    default public boolean remove(byte by, boolean bl) {
        boolean bl2 = this.get(by);
        if (bl2 != bl || bl2 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.remove(by);
        return false;
    }

    @Override
    default public boolean replace(byte by, boolean bl, boolean bl2) {
        boolean bl3 = this.get(by);
        if (bl3 != bl || bl3 == this.defaultReturnValue() && !this.containsKey(by)) {
            return true;
        }
        this.put(by, bl2);
        return false;
    }

    @Override
    default public boolean replace(byte by, boolean bl) {
        return this.containsKey(by) ? this.put(by, bl) : this.defaultReturnValue();
    }

    default public boolean computeIfAbsent(byte by, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        boolean bl = this.get(by);
        if (bl != this.defaultReturnValue() || this.containsKey(by)) {
            return bl;
        }
        boolean bl2 = intPredicate.test(by);
        this.put(by, bl2);
        return bl2;
    }

    default public boolean computeIfAbsentNullable(byte by, IntFunction<? extends Boolean> intFunction) {
        Objects.requireNonNull(intFunction);
        boolean bl = this.get(by);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(by)) {
            return bl;
        }
        Boolean bl3 = intFunction.apply(by);
        if (bl3 == null) {
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(by, bl4);
        return bl4;
    }

    default public boolean computeIfAbsentPartial(byte by, Byte2BooleanFunction byte2BooleanFunction) {
        Objects.requireNonNull(byte2BooleanFunction);
        boolean bl = this.get(by);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(by)) {
            return bl;
        }
        if (!byte2BooleanFunction.containsKey(by)) {
            return bl2;
        }
        boolean bl3 = byte2BooleanFunction.get(by);
        this.put(by, bl3);
        return bl3;
    }

    @Override
    default public boolean computeIfPresent(byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(by);
        boolean bl2 = this.defaultReturnValue();
        if (bl == bl2 && !this.containsKey(by)) {
            return bl2;
        }
        Boolean bl3 = biFunction.apply((Byte)by, (Boolean)bl);
        if (bl3 == null) {
            this.remove(by);
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(by, bl4);
        return bl4;
    }

    @Override
    default public boolean compute(byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(by);
        boolean bl2 = this.defaultReturnValue();
        boolean bl3 = bl != bl2 || this.containsKey(by);
        Boolean bl4 = biFunction.apply((Byte)by, bl3 ? Boolean.valueOf(bl) : null);
        if (bl4 == null) {
            if (bl3) {
                this.remove(by);
            }
            return bl2;
        }
        boolean bl5 = bl4;
        this.put(by, bl5);
        return bl5;
    }

    @Override
    default public boolean merge(byte by, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        boolean bl2;
        Objects.requireNonNull(biFunction);
        boolean bl3 = this.get(by);
        boolean bl4 = this.defaultReturnValue();
        if (bl3 != bl4 || this.containsKey(by)) {
            Boolean bl5 = biFunction.apply((Boolean)bl3, (Boolean)bl);
            if (bl5 == null) {
                this.remove(by);
                return bl4;
            }
            bl2 = bl5;
        } else {
            bl2 = bl;
        }
        this.put(by, bl2);
        return bl2;
    }

    @Override
    @Deprecated
    default public Boolean getOrDefault(Object object, Boolean bl) {
        return Map.super.getOrDefault(object, bl);
    }

    @Override
    @Deprecated
    default public Boolean putIfAbsent(Byte by, Boolean bl) {
        return Map.super.putIfAbsent(by, bl);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Byte by, Boolean bl, Boolean bl2) {
        return Map.super.replace(by, bl, bl2);
    }

    @Override
    @Deprecated
    default public Boolean replace(Byte by, Boolean bl) {
        return Map.super.replace(by, bl);
    }

    @Override
    @Deprecated
    default public Boolean computeIfAbsent(Byte by, Function<? super Byte, ? extends Boolean> function) {
        return Map.super.computeIfAbsent(by, function);
    }

    @Override
    @Deprecated
    default public Boolean computeIfPresent(Byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.computeIfPresent(by, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean compute(Byte by, BiFunction<? super Byte, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.compute(by, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean merge(Byte by, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.merge(by, bl, biFunction);
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
        return this.put((Byte)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Byte)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Byte)object, (BiFunction<? super Byte, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Byte)object, (BiFunction<? super Byte, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Byte)object, (Function<? super Byte, ? extends Boolean>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Byte)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Byte)object, (Boolean)object2, (Boolean)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Byte)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Boolean)object2);
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
    extends Map.Entry<Byte, Boolean> {
        public byte getByteKey();

        @Override
        @Deprecated
        default public Byte getKey() {
            return this.getByteKey();
        }

        public boolean getBooleanValue();

        @Override
        public boolean setValue(boolean var1);

        @Override
        @Deprecated
        default public Boolean getValue() {
            return this.getBooleanValue();
        }

        @Override
        @Deprecated
        default public Boolean setValue(Boolean bl) {
            return this.setValue((boolean)bl);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Boolean)object);
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

