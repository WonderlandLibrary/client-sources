/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.Reference2ByteFunction;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Reference2ByteMap<K>
extends Reference2ByteFunction<K>,
Map<K, Byte> {
    @Override
    public int size();

    @Override
    default public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void defaultReturnValue(byte var1);

    @Override
    public byte defaultReturnValue();

    public ObjectSet<Entry<K>> reference2ByteEntrySet();

    @Override
    @Deprecated
    default public ObjectSet<Map.Entry<K, Byte>> entrySet() {
        return this.reference2ByteEntrySet();
    }

    @Override
    @Deprecated
    default public Byte put(K k, Byte by) {
        return Reference2ByteFunction.super.put(k, by);
    }

    @Override
    @Deprecated
    default public Byte get(Object object) {
        return Reference2ByteFunction.super.get(object);
    }

    @Override
    @Deprecated
    default public Byte remove(Object object) {
        return Reference2ByteFunction.super.remove(object);
    }

    @Override
    public ReferenceSet<K> keySet();

    public ByteCollection values();

    @Override
    public boolean containsKey(Object var1);

    public boolean containsValue(byte var1);

    @Override
    @Deprecated
    default public boolean containsValue(Object object) {
        return object == null ? false : this.containsValue((Byte)object);
    }

    @Override
    default public byte getOrDefault(Object object, byte by) {
        byte by2 = this.getByte(object);
        return by2 != this.defaultReturnValue() || this.containsKey(object) ? by2 : by;
    }

    @Override
    default public byte putIfAbsent(K k, byte by) {
        byte by2;
        byte by3 = this.getByte(k);
        if (by3 != (by2 = this.defaultReturnValue()) || this.containsKey(k)) {
            return by3;
        }
        this.put(k, by);
        return by2;
    }

    default public boolean remove(Object object, byte by) {
        byte by2 = this.getByte(object);
        if (by2 != by || by2 == this.defaultReturnValue() && !this.containsKey(object)) {
            return true;
        }
        this.removeByte(object);
        return false;
    }

    @Override
    default public boolean replace(K k, byte by, byte by2) {
        byte by3 = this.getByte(k);
        if (by3 != by || by3 == this.defaultReturnValue() && !this.containsKey(k)) {
            return true;
        }
        this.put(k, by2);
        return false;
    }

    @Override
    default public byte replace(K k, byte by) {
        return this.containsKey(k) ? this.put(k, by) : this.defaultReturnValue();
    }

    default public byte computeByteIfAbsent(K k, ToIntFunction<? super K> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        byte by = this.getByte(k);
        if (by != this.defaultReturnValue() || this.containsKey(k)) {
            return by;
        }
        byte by2 = SafeMath.safeIntToByte(toIntFunction.applyAsInt(k));
        this.put(k, by2);
        return by2;
    }

    default public byte computeByteIfAbsentPartial(K k, Reference2ByteFunction<? super K> reference2ByteFunction) {
        Objects.requireNonNull(reference2ByteFunction);
        byte by = this.getByte(k);
        byte by2 = this.defaultReturnValue();
        if (by != by2 || this.containsKey(k)) {
            return by;
        }
        if (!reference2ByteFunction.containsKey(k)) {
            return by2;
        }
        byte by3 = reference2ByteFunction.getByte(k);
        this.put(k, by3);
        return by3;
    }

    default public byte computeByteIfPresent(K k, BiFunction<? super K, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.getByte(k);
        byte by2 = this.defaultReturnValue();
        if (by == by2 && !this.containsKey(k)) {
            return by2;
        }
        Byte by3 = biFunction.apply(k, by);
        if (by3 == null) {
            this.removeByte(k);
            return by2;
        }
        byte by4 = by3;
        this.put(k, by4);
        return by4;
    }

    default public byte computeByte(K k, BiFunction<? super K, ? super Byte, ? extends Byte> biFunction) {
        Objects.requireNonNull(biFunction);
        byte by = this.getByte(k);
        byte by2 = this.defaultReturnValue();
        boolean bl = by != by2 || this.containsKey(k);
        Byte by3 = biFunction.apply(k, bl ? Byte.valueOf(by) : null);
        if (by3 == null) {
            if (bl) {
                this.removeByte(k);
            }
            return by2;
        }
        byte by4 = by3;
        this.put(k, by4);
        return by4;
    }

    default public byte mergeByte(K k, byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        byte by2;
        Objects.requireNonNull(biFunction);
        byte by3 = this.getByte(k);
        byte by4 = this.defaultReturnValue();
        if (by3 != by4 || this.containsKey(k)) {
            Byte by5 = biFunction.apply((Byte)by3, (Byte)by);
            if (by5 == null) {
                this.removeByte(k);
                return by4;
            }
            by2 = by5;
        } else {
            by2 = by;
        }
        this.put(k, by2);
        return by2;
    }

    @Override
    @Deprecated
    default public Byte getOrDefault(Object object, Byte by) {
        return Map.super.getOrDefault(object, by);
    }

    @Override
    @Deprecated
    default public Byte putIfAbsent(K k, Byte by) {
        return Map.super.putIfAbsent(k, by);
    }

    @Override
    @Deprecated
    default public boolean remove(Object object, Object object2) {
        return Map.super.remove(object, object2);
    }

    @Override
    @Deprecated
    default public boolean replace(K k, Byte by, Byte by2) {
        return Map.super.replace(k, by, by2);
    }

    @Override
    @Deprecated
    default public Byte replace(K k, Byte by) {
        return Map.super.replace(k, by);
    }

    @Override
    @Deprecated
    default public Byte merge(K k, Byte by, BiFunction<? super Byte, ? super Byte, ? extends Byte> biFunction) {
        return Map.super.merge(k, by, biFunction);
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
        return this.put((K)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object merge(Object object, Object object2, BiFunction biFunction) {
        return this.merge(object, (Byte)object2, (BiFunction<Byte, Byte, Byte>)biFunction);
    }

    @Override
    @Deprecated
    default public Object replace(Object object, Object object2) {
        return this.replace((K)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public boolean replace(Object object, Object object2, Object object3) {
        return this.replace((K)object, (Byte)object2, (Byte)object3);
    }

    @Override
    @Deprecated
    default public Object putIfAbsent(Object object, Object object2) {
        return this.putIfAbsent((K)object, (Byte)object2);
    }

    @Override
    @Deprecated
    default public Object getOrDefault(Object object, Object object2) {
        return this.getOrDefault(object, (Byte)object2);
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
    extends Map.Entry<K, Byte> {
        public byte getByteValue();

        @Override
        public byte setValue(byte var1);

        @Override
        @Deprecated
        default public Byte getValue() {
            return this.getByteValue();
        }

        @Override
        @Deprecated
        default public Byte setValue(Byte by) {
            return this.setValue((byte)by);
        }

        @Override
        @Deprecated
        default public Object setValue(Object object) {
            return this.setValue((Byte)object);
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

