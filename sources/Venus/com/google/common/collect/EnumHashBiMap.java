/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractBiMap;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Serialization;
import com.google.common.collect.WellBehavedMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class EnumHashBiMap<K extends Enum<K>, V>
extends AbstractBiMap<K, V> {
    private transient Class<K> keyType;
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Class<K> clazz) {
        return new EnumHashBiMap<K, V>(clazz);
    }

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Map<K, ? extends V> map) {
        EnumHashBiMap<K, V> enumHashBiMap = EnumHashBiMap.create(EnumBiMap.inferKeyType(map));
        enumHashBiMap.putAll((Map)map);
        return enumHashBiMap;
    }

    private EnumHashBiMap(Class<K> clazz) {
        super(WellBehavedMap.wrap(new EnumMap(clazz)), Maps.newHashMapWithExpectedSize(((Enum[])clazz.getEnumConstants()).length));
        this.keyType = clazz;
    }

    @Override
    K checkKey(K k) {
        return (K)((Enum)Preconditions.checkNotNull(k));
    }

    @Override
    @CanIgnoreReturnValue
    public V put(K k, @Nullable V v) {
        return super.put(k, v);
    }

    @Override
    @CanIgnoreReturnValue
    public V forcePut(K k, @Nullable V v) {
        return super.forcePut(k, v);
    }

    public Class<K> keyType() {
        return this.keyType;
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.keyType);
        Serialization.writeMap(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keyType = (Class)objectInputStream.readObject();
        this.setDelegates(WellBehavedMap.wrap(new EnumMap(this.keyType)), new HashMap(((Enum[])this.keyType.getEnumConstants()).length * 3 / 2));
        Serialization.populateMap(this, objectInputStream);
    }

    @Override
    public Set entrySet() {
        return super.entrySet();
    }

    @Override
    public Set values() {
        return super.values();
    }

    @Override
    public Set keySet() {
        return super.keySet();
    }

    @Override
    public BiMap inverse() {
        return super.inverse();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void replaceAll(BiFunction biFunction) {
        super.replaceAll(biFunction);
    }

    @Override
    public void putAll(Map map) {
        super.putAll(map);
    }

    @Override
    @CanIgnoreReturnValue
    public Object remove(@Nullable Object object) {
        return super.remove(object);
    }

    @Override
    @CanIgnoreReturnValue
    public Object forcePut(Object object, @Nullable Object object2) {
        return this.forcePut((K)((Enum)object), (V)object2);
    }

    @Override
    @CanIgnoreReturnValue
    public Object put(Object object, @Nullable Object object2) {
        return this.put((K)((Enum)object), (V)object2);
    }

    @Override
    public boolean containsValue(@Nullable Object object) {
        return super.containsValue(object);
    }

    @Override
    Object checkKey(Object object) {
        return this.checkKey((K)((Enum)object));
    }
}

