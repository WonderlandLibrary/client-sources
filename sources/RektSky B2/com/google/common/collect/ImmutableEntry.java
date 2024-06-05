package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible(serializable = true)
class ImmutableEntry<K, V> extends AbstractMapEntry<K, V> implements Serializable
{
    final K key;
    final V value;
    private static final long serialVersionUID = 0L;
    
    ImmutableEntry(@Nullable final K key, @Nullable final V value) {
        this.key = key;
        this.value = value;
    }
    
    @Nullable
    @Override
    public final K getKey() {
        return this.key;
    }
    
    @Nullable
    @Override
    public final V getValue() {
        return this.value;
    }
    
    @Override
    public final V setValue(final V value) {
        throw new UnsupportedOperationException();
    }
}
