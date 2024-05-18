package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Iterators;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class NextTickHashSet extends TreeSet
{
    private LongHashMap HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private static final int Ó = Integer.MIN_VALUE;
    
    public NextTickHashSet(final Set oldSet) {
        this.HorizonCode_Horizon_È = new LongHashMap();
        this.Â = Integer.MIN_VALUE;
        this.Ý = Integer.MIN_VALUE;
        this.Ø­áŒŠá = Integer.MIN_VALUE;
        this.Âµá€ = Integer.MIN_VALUE;
        for (final Object obj : oldSet) {
            this.add(obj);
        }
    }
    
    @Override
    public boolean contains(final Object obj) {
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry entry = (NextTickListEntry)obj;
        final Set set = this.HorizonCode_Horizon_È(entry, false);
        return set != null && set.contains(entry);
    }
    
    @Override
    public boolean add(final Object obj) {
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry entry = (NextTickListEntry)obj;
        if (entry == null) {
            return false;
        }
        final Set set = this.HorizonCode_Horizon_È(entry, true);
        final boolean added = set.add(entry);
        final boolean addedParent = super.add(obj);
        if (added != addedParent) {
            throw new IllegalStateException("Added: " + added + ", addedParent: " + addedParent);
        }
        return addedParent;
    }
    
    @Override
    public boolean remove(final Object obj) {
        if (!(obj instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry entry = (NextTickListEntry)obj;
        final Set set = this.HorizonCode_Horizon_È(entry, false);
        if (set == null) {
            return false;
        }
        final boolean removed = set.remove(entry);
        final boolean removedParent = super.remove(entry);
        if (removed != removedParent) {
            throw new IllegalStateException("Added: " + removed + ", addedParent: " + removedParent);
        }
        return removedParent;
    }
    
    private Set HorizonCode_Horizon_È(final NextTickListEntry entry, final boolean autoCreate) {
        if (entry == null) {
            return null;
        }
        final BlockPos pos = entry.HorizonCode_Horizon_È;
        final int cx = pos.HorizonCode_Horizon_È() >> 4;
        final int cz = pos.Ý() >> 4;
        return this.HorizonCode_Horizon_È(cx, cz, autoCreate);
    }
    
    private Set HorizonCode_Horizon_È(final int cx, final int cz, final boolean autoCreate) {
        final long key = ChunkCoordIntPair.HorizonCode_Horizon_È(cx, cz);
        HashSet set = (HashSet)this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(key);
        if (set == null && autoCreate) {
            set = new HashSet();
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(key, set);
        }
        return set;
    }
    
    @Override
    public Iterator iterator() {
        if (this.Â == Integer.MIN_VALUE) {
            return super.iterator();
        }
        if (this.size() <= 0) {
            return (Iterator)Iterators.emptyIterator();
        }
        final int cMinX = this.Â >> 4;
        final int cMinZ = this.Ý >> 4;
        final int cMaxX = this.Ø­áŒŠá >> 4;
        final int cMaxZ = this.Âµá€ >> 4;
        final ArrayList listIterators = new ArrayList();
        for (int x = cMinX; x <= cMaxX; ++x) {
            for (int z = cMinZ; z <= cMaxZ; ++z) {
                final Set set = this.HorizonCode_Horizon_È(x, z, false);
                if (set != null) {
                    listIterators.add(set.iterator());
                }
            }
        }
        if (listIterators.size() <= 0) {
            return (Iterator)Iterators.emptyIterator();
        }
        if (listIterators.size() == 1) {
            return listIterators.get(0);
        }
        return Iterators.concat((Iterator)listIterators.iterator());
    }
    
    public void HorizonCode_Horizon_È(final int minX, final int minZ, final int maxX, final int maxZ) {
        this.Â = Math.min(minX, maxX);
        this.Ý = Math.min(minZ, maxZ);
        this.Ø­áŒŠá = Math.max(minX, maxX);
        this.Âµá€ = Math.max(minZ, maxZ);
    }
    
    public void HorizonCode_Horizon_È() {
        this.Â = Integer.MIN_VALUE;
        this.Ý = Integer.MIN_VALUE;
        this.Ø­áŒŠá = Integer.MIN_VALUE;
        this.Âµá€ = Integer.MIN_VALUE;
    }
}
