package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;

public class RegistryNamespaced<K, V> extends RegistrySimple<K, V> implements IObjectIntIterable<V>
{
    protected final Map<V, K> inverseObjectRegistry;
    protected final ObjectIntIdentityMap underlyingIntegerMap;
    
    public void register(final int n, final K k, final V v) {
        this.underlyingIntegerMap.put(v, n);
        this.putObject(k, v);
    }
    
    public V getObjectById(final int n) {
        return (V)this.underlyingIntegerMap.getByValue(n);
    }
    
    public int getIDForObject(final V v) {
        return this.underlyingIntegerMap.get(v);
    }
    
    public RegistryNamespaced() {
        this.underlyingIntegerMap = new ObjectIntIdentityMap();
        this.inverseObjectRegistry = (Map<V, K>)((BiMap)this.registryObjects).inverse();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public K getNameForObject(final V v) {
        return this.inverseObjectRegistry.get(v);
    }
    
    @Override
    public V getObject(final K k) {
        return super.getObject(k);
    }
    
    @Override
    public Iterator<V> iterator() {
        return (Iterator<V>)this.underlyingIntegerMap.iterator();
    }
    
    @Override
    public boolean containsKey(final K k) {
        return super.containsKey(k);
    }
    
    @Override
    protected Map<K, V> createUnderlyingMap() {
        return (Map<K, V>)HashBiMap.create();
    }
}
