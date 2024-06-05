package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.io.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class EnumHashBiMap<K extends Enum<K>, V> extends AbstractBiMap<K, V>
{
    private transient Class<K> keyType;
    @GwtIncompatible("only needed in emulated source.")
    private static final long serialVersionUID = 0L;
    
    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(final Class<K> keyType) {
        return new EnumHashBiMap<K, V>(keyType);
    }
    
    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(final Map<K, ? extends V> map) {
        final EnumHashBiMap<K, V> bimap = create((Class<K>)EnumBiMap.inferKeyType((Map<K, ?>)map));
        bimap.putAll((Map)map);
        return bimap;
    }
    
    private EnumHashBiMap(final Class<K> keyType) {
        super((Map<Object, Object>)WellBehavedMap.wrap((Map<K, V>)new EnumMap<K, Object>(keyType)), Maps.newHashMapWithExpectedSize(keyType.getEnumConstants().length));
        this.keyType = keyType;
    }
    
    @Override
    K checkKey(final K key) {
        return Preconditions.checkNotNull(key);
    }
    
    @Override
    public V put(final K key, @Nullable final V value) {
        return super.put(key, value);
    }
    
    @Override
    public V forcePut(final K key, @Nullable final V value) {
        return super.forcePut(key, value);
    }
    
    public Class<K> keyType() {
        return this.keyType;
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(this.keyType);
        Serialization.writeMap((Map<Object, Object>)this, stream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.keyType = (Class<K>)stream.readObject();
        this.setDelegates(WellBehavedMap.wrap(new EnumMap<K, V>(this.keyType)), new HashMap<V, K>(this.keyType.getEnumConstants().length * 3 / 2));
        Serialization.populateMap((Map<Object, Object>)this, stream);
    }
}
