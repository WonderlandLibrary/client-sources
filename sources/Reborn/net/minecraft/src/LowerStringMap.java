package net.minecraft.src;

import java.util.*;

public class LowerStringMap implements Map
{
    private final Map internalMap;
    
    public LowerStringMap() {
        this.internalMap = new LinkedHashMap();
    }
    
    @Override
    public int size() {
        return this.internalMap.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.internalMap.isEmpty();
    }
    
    @Override
    public boolean containsKey(final Object par1Obj) {
        return this.internalMap.containsKey(par1Obj.toString().toLowerCase());
    }
    
    @Override
    public boolean containsValue(final Object par1Obj) {
        return this.internalMap.containsKey(par1Obj);
    }
    
    @Override
    public Object get(final Object par1Obj) {
        return this.internalMap.get(par1Obj.toString().toLowerCase());
    }
    
    public Object putLower(final String par1Str, final Object par2Obj) {
        return this.internalMap.put(par1Str.toLowerCase(), par2Obj);
    }
    
    @Override
    public Object remove(final Object par1Obj) {
        return this.internalMap.remove(par1Obj.toString().toLowerCase());
    }
    
    @Override
    public void putAll(final Map par1Map) {
        for (final Entry var3 : par1Map.entrySet()) {
            this.putLower(var3.getKey(), var3.getValue());
        }
    }
    
    @Override
    public void clear() {
        this.internalMap.clear();
    }
    
    @Override
    public Set keySet() {
        return this.internalMap.keySet();
    }
    
    @Override
    public Collection values() {
        return this.internalMap.values();
    }
    
    @Override
    public Set entrySet() {
        return this.internalMap.entrySet();
    }
    
    @Override
    public Object put(final Object par1Obj, final Object par2Obj) {
        return this.putLower((String)par1Obj, par2Obj);
    }
}
