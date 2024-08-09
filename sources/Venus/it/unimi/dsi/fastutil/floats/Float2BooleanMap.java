/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.floats.Float2BooleanFunction;
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
import java.util.function.DoublePredicate;
import java.util.function.Function;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Float2BooleanMap
extends Float2BooleanFunction,
Map<Float, Boolean> {
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

    public ObjectSet<Entry> float2BooleanEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<Float, Boolean>> entrySet() {
        return this.float2BooleanEntrySet();
    }

    @Override
    @Deprecated
    default public Boolean put(Float f, Boolean bl) {
        return Float2BooleanFunction.super.put(f, bl);
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        return Float2BooleanFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        return Float2BooleanFunction.super.remove(object);
    }

    public FloatSet keySet();

    public BooleanCollection values();

    @Override
    public boolean containsKey(float var1);

    @Override
    @Deprecated
    default public boolean containsKey(Object object) {
        return Float2BooleanFunction.super.containsKey(object);
    }

    public boolean containsValue(boolean var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Boolean)object);
    }

    default public boolean getOrDefault(float f, boolean bl) {
        boolean bl2 = this.get(f);
        return bl2 != this.defaultReturnValue() || this.containsKey(f) ? bl2 : bl;
    }

    @Override
    default public boolean putIfAbsent(float f, boolean bl) {
        boolean bl2;
        boolean bl3 = this.get(f);
        if (bl3 != (bl2 = this.defaultReturnValue()) || this.containsKey(f)) {
            return bl3;
        }
        this.put(f, bl);
        return bl2;
    }

    default public boolean remove(float f, boolean bl) {
        boolean bl2 = this.get(f);
        if (bl2 != bl || bl2 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.remove(f);
        return false;
    }

    @Override
    default public boolean replace(float f, boolean bl, boolean bl2) {
        boolean bl3 = this.get(f);
        if (bl3 != bl || bl3 == this.defaultReturnValue() && !this.containsKey(f)) {
            return true;
        }
        this.put(f, bl2);
        return false;
    }

    @Override
    default public boolean replace(float f, boolean bl) {
        return this.containsKey(f) ? this.put(f, bl) : this.defaultReturnValue();
    }

    default public boolean computeIfAbsent(float f, DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        boolean bl = this.get(f);
        if (bl != this.defaultReturnValue() || this.containsKey(f)) {
            return bl;
        }
        boolean bl2 = doublePredicate.test(f);
        this.put(f, bl2);
        return bl2;
    }

    default public boolean computeIfAbsentNullable(float f, DoubleFunction<? extends Boolean> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        boolean bl = this.get(f);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(f)) {
            return bl;
        }
        Boolean bl3 = doubleFunction.apply(f);
        if (bl3 == null) {
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(f, bl4);
        return bl4;
    }

    default public boolean computeIfAbsentPartial(float f, Float2BooleanFunction float2BooleanFunction) {
        Objects.requireNonNull(float2BooleanFunction);
        boolean bl = this.get(f);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(f)) {
            return bl;
        }
        if (!float2BooleanFunction.containsKey(f)) {
            return bl2;
        }
        boolean bl3 = float2BooleanFunction.get(f);
        this.put(f, bl3);
        return bl3;
    }

    @Override
    default public boolean computeIfPresent(float f, BiFunction<? super Float, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(f);
        boolean bl2 = this.defaultReturnValue();
        if (bl == bl2 && !this.containsKey(f)) {
            return bl2;
        }
        Boolean bl3 = biFunction.apply(Float.valueOf(f), (Boolean)bl);
        if (bl3 == null) {
            this.remove(f);
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(f, bl4);
        return bl4;
    }

    @Override
    default public boolean compute(float f, BiFunction<? super Float, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.get(f);
        boolean bl2 = this.defaultReturnValue();
        boolean bl3 = bl != bl2 || this.containsKey(f);
        Boolean bl4 = biFunction.apply(Float.valueOf(f), bl3 ? Boolean.valueOf(bl) : null);
        if (bl4 == null) {
            if (bl3) {
                this.remove(f);
            }
            return bl2;
        }
        boolean bl5 = bl4;
        this.put(f, bl5);
        return bl5;
    }

    @Override
    default public boolean merge(float f, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        boolean bl2;
        Objects.requireNonNull(biFunction);
        boolean bl3 = this.get(f);
        boolean bl4 = this.defaultReturnValue();
        if (bl3 != bl4 || this.containsKey(f)) {
            Boolean bl5 = biFunction.apply((Boolean)bl3, (Boolean)bl);
            if (bl5 == null) {
                this.remove(f);
                return bl4;
            }
            bl2 = bl5;
        } else {
            bl2 = bl;
        }
        this.put(f, bl2);
        return bl2;
    }

    @Override
    @Deprecated
    default public Boolean getOrDefault(Object object, Boolean bl) {
        return Map.super.getOrDefault(object, bl);
    }

    @Override
    @Deprecated
    default public Boolean putIfAbsent(Float f, Boolean bl) {
        return Map.super.putIfAbsent(f, bl);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Float f, Boolean bl, Boolean bl2) {
        return Map.super.replace(f, bl, bl2);
    }

    @Override
    @Deprecated
    default public Boolean replace(Float f, Boolean bl) {
        return Map.super.replace(f, bl);
    }

    @Override
    @Deprecated
    default public Boolean computeIfAbsent(Float f, Function<? super Float, ? extends Boolean> function) {
        return Map.super.computeIfAbsent(f, function);
    }

    @Override
    @Deprecated
    default public Boolean computeIfPresent(Float f, BiFunction<? super Float, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.computeIfPresent(f, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean compute(Float f, BiFunction<? super Float, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.compute(f, biFunction);
    }

    @Override
    @Deprecated
    default public Boolean merge(Float f, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.merge(f, bl, biFunction);
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
        return this.put((Float)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge((Float)object, (Boolean)object2, (BiFunction<? super Boolean, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object compute(Object object, BiFunction biFunction) {
        return this.compute((Float)object, (BiFunction<? super Float, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfPresent(Object object, BiFunction biFunction) {
        return this.computeIfPresent((Float)object, (BiFunction<? super Float, ? super Boolean, ? extends Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object computeIfAbsent(Object object, Function function) {
        return this.computeIfAbsent((Float)object, (Function<? super Float, ? extends Boolean>)function);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((Float)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((Float)object, (Boolean)object2, (Boolean)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((Float)object, (Boolean)object2);
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
    extends Map.Entry<Float, Boolean> {
        public float getFloatKey();

        @Override
        @Deprecated
        default public Float getKey() {
            return Float.valueOf(this.getFloatKey());
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

