/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractBiMap;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.Serialization;
import com.google.common.collect.WellBehavedMap;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class EnumBiMap<K extends Enum<K>, V extends Enum<V>>
extends AbstractBiMap<K, V> {
    private transient Class<K> keyType;
    private transient Class<V> valueType;
    @GwtIncompatible
    private static final long serialVersionUID = 0L;

    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Class<K> clazz, Class<V> clazz2) {
        return new EnumBiMap<K, V>(clazz, clazz2);
    }

    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Map<K, V> map) {
        EnumBiMap<K, V> enumBiMap = EnumBiMap.create(EnumBiMap.inferKeyType(map), EnumBiMap.inferValueType(map));
        enumBiMap.putAll((Map)map);
        return enumBiMap;
    }

    private EnumBiMap(Class<K> clazz, Class<V> clazz2) {
        super(WellBehavedMap.wrap(new EnumMap(clazz)), WellBehavedMap.wrap(new EnumMap(clazz2)));
        this.keyType = clazz;
        this.valueType = clazz2;
    }

    static <K extends Enum<K>> Class<K> inferKeyType(Map<K, ?> map) {
        if (map instanceof EnumBiMap) {
            return ((EnumBiMap)map).keyType();
        }
        if (map instanceof EnumHashBiMap) {
            return ((EnumHashBiMap)map).keyType();
        }
        Preconditions.checkArgument(!map.isEmpty());
        return ((Enum)map.keySet().iterator().next()).getDeclaringClass();
    }

    private static <V extends Enum<V>> Class<V> inferValueType(Map<?, V> map) {
        if (map instanceof EnumBiMap) {
            return ((EnumBiMap)map).valueType;
        }
        Preconditions.checkArgument(!map.isEmpty());
        return ((Enum)map.values().iterator().next()).getDeclaringClass();
    }

    public Class<K> keyType() {
        return this.keyType;
    }

    public Class<V> valueType() {
        return this.valueType;
    }

    @Override
    K checkKey(K k) {
        return (K)((Enum)Preconditions.checkNotNull(k));
    }

    @Override
    V checkValue(V v) {
        return (V)((Enum)Preconditions.checkNotNull(v));
    }

    @GwtIncompatible
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.keyType);
        objectOutputStream.writeObject(this.valueType);
        Serialization.writeMap(this, objectOutputStream);
    }

    @GwtIncompatible
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keyType = (Class)objectInputStream.readObject();
        this.valueType = (Class)objectInputStream.readObject();
        this.setDelegates(WellBehavedMap.wrap(new EnumMap(this.keyType)), WellBehavedMap.wrap(new EnumMap(this.valueType)));
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
    public boolean containsValue(@Nullable Object object) {
        return super.containsValue(object);
    }

    @Override
    Object checkValue(Object object) {
        return this.checkValue((V)((Enum)object));
    }

    @Override
    Object checkKey(Object object) {
        return this.checkKey((K)((Enum)object));
    }
}

