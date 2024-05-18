package net.minecraft.server.management;

import com.google.common.collect.*;
import java.util.*;

public class LowerStringMap<V> implements Map<String, V>
{
    private final Map<String, V> internalMap;
    
    @Override
    public boolean isEmpty() {
        return this.internalMap.isEmpty();
    }
    
    @Override
    public Set<String> keySet() {
        return this.internalMap.keySet();
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void clear() {
        this.internalMap.clear();
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        return this.put((String)o, o2);
    }
    
    @Override
    public V put(final String s, final V v) {
        return this.internalMap.put(s.toLowerCase(), v);
    }
    
    @Override
    public void putAll(final Map<? extends String, ? extends V> map) {
        final Iterator<Entry<? extends String, ? extends V>> iterator = map.entrySet().iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Entry<? extends String, ? extends V> entry = iterator.next();
            this.put((String)entry.getKey(), (V)entry.getValue());
        }
    }
    
    @Override
    public int size() {
        return this.internalMap.size();
    }
    
    @Override
    public V get(final Object o) {
        return this.internalMap.get(o.toString().toLowerCase());
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.internalMap.containsKey(o.toString().toLowerCase());
    }
    
    public LowerStringMap() {
        this.internalMap = (Map<String, V>)Maps.newLinkedHashMap();
    }
    
    @Override
    public Collection<V> values() {
        return this.internalMap.values();
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return this.internalMap.containsKey(o);
    }
    
    @Override
    public V remove(final Object o) {
        return this.internalMap.remove(o.toString().toLowerCase());
    }
    
    @Override
    public Set<Entry<String, V>> entrySet() {
        return this.internalMap.entrySet();
    }
}
