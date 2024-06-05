package com.google.common.collect;

import java.util.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import java.io.*;

@GwtCompatible(emulated = true)
abstract class ImmutableMapEntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>>
{
    abstract ImmutableMap<K, V> map();
    
    @Override
    public int size() {
        return this.map().size();
    }
    
    @Override
    public boolean contains(@Nullable final Object object) {
        if (object instanceof Map.Entry) {
            final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)object;
            final V value = this.map().get(entry.getKey());
            return value != null && value.equals(entry.getValue());
        }
        return false;
    }
    
    @Override
    boolean isPartialView() {
        return this.map().isPartialView();
    }
    
    @GwtIncompatible("serialization")
    @Override
    Object writeReplace() {
        return new EntrySetSerializedForm(this.map());
    }
    
    @GwtIncompatible("serialization")
    private static class EntrySetSerializedForm<K, V> implements Serializable
    {
        final ImmutableMap<K, V> map;
        private static final long serialVersionUID = 0L;
        
        EntrySetSerializedForm(final ImmutableMap<K, V> map) {
            this.map = map;
        }
        
        Object readResolve() {
            return this.map.entrySet();
        }
    }
}
