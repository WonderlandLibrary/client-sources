/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2BooleanFunction;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Reference2BooleanMap<K>
extends Reference2BooleanFunction<K>,
Map<K, Boolean> {
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

    public ObjectSet<Entry<K>> reference2BooleanEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<K, Boolean>> entrySet() {
        return this.reference2BooleanEntrySet();
    }

    @Override
    @Deprecated
    default public Boolean put(K k, Boolean bl) {
        return Reference2BooleanFunction.super.put(k, bl);
    }

    @Override
    @Deprecated
    default public Boolean get(Object object) {
        return Reference2BooleanFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Boolean remove(Object object) {
        return Reference2BooleanFunction.super.remove(object);
    }

    @Override
    public ReferenceSet<K> keySet();

    public BooleanCollection values();

    @Override
    public boolean containsKey(Object var1);

    public boolean containsValue(boolean var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Boolean)object);
    }

    @Override
    default public boolean getOrDefault(Object object, boolean bl) {
        boolean bl2 = this.getBoolean(object);
        return bl2 != this.defaultReturnValue() || this.containsKey(object) ? bl2 : bl;
    }

    @Override
    default public boolean putIfAbsent(K k, boolean bl) {
        boolean bl2;
        boolean bl3 = this.getBoolean(k);
        if (bl3 != (bl2 = this.defaultReturnValue()) || this.containsKey(k)) {
            return bl3;
        }
        this.put(k, bl);
        return bl2;
    }

    default public boolean remove(Object object, boolean bl) {
        boolean bl2 = this.getBoolean(object);
        if (bl2 != bl || bl2 == this.defaultReturnValue() && !this.containsKey(object)) {
            return true;
        }
        this.removeBoolean(object);
        return false;
    }

    @Override
    default public boolean replace(K k, boolean bl, boolean bl2) {
        boolean bl3 = this.getBoolean(k);
        if (bl3 != bl || bl3 == this.defaultReturnValue() && !this.containsKey(k)) {
            return true;
        }
        this.put(k, bl2);
        return false;
    }

    @Override
    default public boolean replace(K k, boolean bl) {
        return this.containsKey(k) ? this.put(k, bl) : this.defaultReturnValue();
    }

    default public boolean computeBooleanIfAbsent(K k, Predicate<? super K> predicate) {
        Objects.requireNonNull(predicate);
        boolean bl = this.getBoolean(k);
        if (bl != this.defaultReturnValue() || this.containsKey(k)) {
            return bl;
        }
        boolean bl2 = predicate.test(k);
        this.put(k, bl2);
        return bl2;
    }

    default public boolean computeBooleanIfAbsentPartial(K k, Reference2BooleanFunction<? super K> reference2BooleanFunction) {
        Objects.requireNonNull(reference2BooleanFunction);
        boolean bl = this.getBoolean(k);
        boolean bl2 = this.defaultReturnValue();
        if (bl != bl2 || this.containsKey(k)) {
            return bl;
        }
        if (!reference2BooleanFunction.containsKey(k)) {
            return bl2;
        }
        boolean bl3 = reference2BooleanFunction.getBoolean(k);
        this.put(k, bl3);
        return bl3;
    }

    default public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.getBoolean(k);
        boolean bl2 = this.defaultReturnValue();
        if (bl == bl2 && !this.containsKey(k)) {
            return bl2;
        }
        Boolean bl3 = biFunction.apply(k, bl);
        if (bl3 == null) {
            this.removeBoolean(k);
            return bl2;
        }
        boolean bl4 = bl3;
        this.put(k, bl4);
        return bl4;
    }

    default public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> biFunction) {
        Objects.requireNonNull(biFunction);
        boolean bl = this.getBoolean(k);
        boolean bl2 = this.defaultReturnValue();
        boolean bl3 = bl != bl2 || this.containsKey(k);
        Boolean bl4 = biFunction.apply(k, bl3 ? Boolean.valueOf(bl) : null);
        if (bl4 == null) {
            if (bl3) {
                this.removeBoolean(k);
            }
            return bl2;
        }
        boolean bl5 = bl4;
        this.put(k, bl5);
        return bl5;
    }

    default public boolean mergeBoolean(K k, boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        boolean bl2;
        Objects.requireNonNull(biFunction);
        boolean bl3 = this.getBoolean(k);
        boolean bl4 = this.defaultReturnValue();
        if (bl3 != bl4 || this.containsKey(k)) {
            Boolean bl5 = biFunction.apply((Boolean)bl3, (Boolean)bl);
            if (bl5 == null) {
                this.removeBoolean(k);
                return bl4;
            }
            bl2 = bl5;
        } else {
            bl2 = bl;
        }
        this.put(k, bl2);
        return bl2;
    }

    @Override
    @Deprecated
    default public Boolean getOrDefault(Object object, Boolean bl) {
        return Map.super.getOrDefault(object, bl);
    }

    @Override
    @Deprecated
    default public Boolean putIfAbsent(K k, Boolean bl) {
        return Map.super.putIfAbsent(k, bl);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(K k, Boolean bl, Boolean bl2) {
        return Map.super.replace(k, bl, bl2);
    }

    @Override
    @Deprecated
    default public Boolean replace(K k, Boolean bl) {
        return Map.super.replace(k, bl);
    }

    @Override
    @Deprecated
    default public Boolean merge(K k, Boolean bl, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> biFunction) {
        return Map.super.merge(k, bl, biFunction);
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
        return this.put((K)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge(object, (Boolean)object2, (BiFunction<Boolean, Boolean, Boolean>)biFunction);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((K)object, (Boolean)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((K)object, (Boolean)object2, (Boolean)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((K)object, (Boolean)object2);
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
    public static interface Entry<K>
    extends Map.Entry<K, Boolean> {
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
    }

    public static interface FastEntrySet<K>
    extends ObjectSet<Entry<K>> {
        public ObjectIterator<Entry<K>> fastIterator();

        default public void fastForEach(Consumer<? super Entry<K>> consumer) {
            this.forEach(consumer);
        }
    }
}

