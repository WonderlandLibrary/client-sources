package net.minecraft.src;

import java.util.*;

public class NextTickHashSet extends AbstractSet
{
    private LongHashMap longHashMap;
    private int size;
    private HashSet emptySet;
    
    public NextTickHashSet(final Set var1) {
        this.longHashMap = new LongHashMap();
        this.size = 0;
        this.emptySet = new HashSet();
        this.addAll(var1);
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean contains(final Object var1) {
        if (!(var1 instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry var2 = (NextTickListEntry)var1;
        final long var3 = ChunkCoordIntPair.chunkXZ2Int(var2.xCoord >> 4, var2.zCoord >> 4);
        final HashSet var4 = (HashSet)this.longHashMap.getValueByKey(var3);
        return var4 != null && var4.contains(var2);
    }
    
    @Override
    public boolean add(final Object var1) {
        if (!(var1 instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry var2 = (NextTickListEntry)var1;
        final long var3 = ChunkCoordIntPair.chunkXZ2Int(var2.xCoord >> 4, var2.zCoord >> 4);
        HashSet var4 = (HashSet)this.longHashMap.getValueByKey(var3);
        if (var4 == null) {
            var4 = new HashSet();
            this.longHashMap.add(var3, var4);
        }
        final boolean var5 = var4.add(var2);
        if (var5) {
            ++this.size;
        }
        return var5;
    }
    
    @Override
    public boolean remove(final Object var1) {
        if (!(var1 instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry var2 = (NextTickListEntry)var1;
        final long var3 = ChunkCoordIntPair.chunkXZ2Int(var2.xCoord >> 4, var2.zCoord >> 4);
        final HashSet var4 = (HashSet)this.longHashMap.getValueByKey(var3);
        if (var4 == null) {
            return false;
        }
        final boolean var5 = var4.remove(var2);
        if (var5) {
            --this.size;
        }
        return var5;
    }
    
    public Iterator getNextTickEntries(final int var1, final int var2) {
        final long var3 = ChunkCoordIntPair.chunkXZ2Int(var1, var2);
        HashSet var4 = (HashSet)this.longHashMap.getValueByKey(var3);
        if (var4 == null) {
            var4 = this.emptySet;
        }
        return var4.iterator();
    }
    
    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
