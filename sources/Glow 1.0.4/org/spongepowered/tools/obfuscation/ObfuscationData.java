package org.spongepowered.tools.obfuscation;

import java.util.*;

public class ObfuscationData<T> implements Iterable<ObfuscationType>
{
    private final Map<ObfuscationType, T> data;
    private final T defaultValue;
    
    public ObfuscationData() {
        this(null);
    }
    
    public ObfuscationData(final T defaultValue) {
        super();
        this.data = new HashMap<ObfuscationType, T>();
        this.defaultValue = defaultValue;
    }
    
    @Deprecated
    public void add(final ObfuscationType obfuscationType, final T t) {
        this.put(obfuscationType, t);
    }
    
    public void put(final ObfuscationType obfuscationType, final T t) {
        this.data.put(obfuscationType, t);
    }
    
    public boolean isEmpty() {
        return this.data.isEmpty();
    }
    
    public T get(final ObfuscationType obfuscationType) {
        final T value = this.data.get(obfuscationType);
        return (value != null) ? value : this.defaultValue;
    }
    
    @Override
    public Iterator<ObfuscationType> iterator() {
        return this.data.keySet().iterator();
    }
    
    @Override
    public String toString() {
        return String.format("ObfuscationData[%s,DEFAULT=%s]", this.listValues(), this.defaultValue);
    }
    
    public String values() {
        return "[" + this.listValues() + "]";
    }
    
    private String listValues() {
        final StringBuilder sb = new StringBuilder();
        int n = 0;
        for (final ObfuscationType obfuscationType : this.data.keySet()) {
            if (n != 0) {
                sb.append(',');
            }
            sb.append(obfuscationType.getKey()).append('=').append(this.data.get(obfuscationType));
            n = 1;
        }
        return sb.toString();
    }
}
