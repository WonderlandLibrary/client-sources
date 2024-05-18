package optfine;

import net.minecraft.util.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import java.util.*;

public class NextTickHashSet extends TreeSet
{
    private int minX;
    private int maxX;
    private int maxZ;
    private static final String[] I;
    private int minZ;
    private static final int UNDEFINED;
    private LongHashMap longHashMap;
    
    public NextTickHashSet(final Set set) {
        this.longHashMap = new LongHashMap();
        this.minX = -"".length();
        this.minZ = -"".length();
        this.maxX = -"".length();
        this.maxZ = -"".length();
        final Iterator<Object> iterator = set.iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }
    
    public void clearIteratorLimits() {
        this.minX = -"".length();
        this.minZ = -"".length();
        this.maxX = -"".length();
        this.maxZ = -"".length();
    }
    
    private Set getSubSet(final NextTickListEntry nextTickListEntry, final boolean b) {
        if (nextTickListEntry == null) {
            return null;
        }
        final BlockPos position = nextTickListEntry.position;
        return this.getSubSet(position.getX() >> (0x83 ^ 0x87), position.getZ() >> (0x74 ^ 0x70), b);
    }
    
    static {
        I();
        UNDEFINED = -"".length();
    }
    
    @Override
    public boolean contains(final Object o) {
        if (!(o instanceof NextTickListEntry)) {
            return "".length() != 0;
        }
        final NextTickListEntry nextTickListEntry = (NextTickListEntry)o;
        final Set subSet = this.getSubSet(nextTickListEntry, "".length() != 0);
        int n;
        if (subSet == null) {
            n = "".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            n = (subSet.contains(nextTickListEntry) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public boolean add(final Object o) {
        if (!(o instanceof NextTickListEntry)) {
            return "".length() != 0;
        }
        final NextTickListEntry nextTickListEntry = (NextTickListEntry)o;
        if (nextTickListEntry == null) {
            return "".length() != 0;
        }
        final boolean add = this.getSubSet(nextTickListEntry, " ".length() != 0).add(nextTickListEntry);
        final boolean add2 = super.add(o);
        if (add != add2) {
            throw new IllegalStateException(NextTickHashSet.I["".length()] + add + NextTickHashSet.I[" ".length()] + add2);
        }
        return add2;
    }
    
    private Set getSubSet(final int n, final int n2, final boolean b) {
        final long chunkXZ2Int = ChunkCoordIntPair.chunkXZ2Int(n, n2);
        HashSet set = (HashSet)this.longHashMap.getValueByKey(chunkXZ2Int);
        if (set == null && b) {
            set = new HashSet();
            this.longHashMap.add(chunkXZ2Int, set);
        }
        return set;
    }
    
    private static void I() {
        (I = new String[0x52 ^ 0x56])["".length()] = I("\b-%\u0013\"si", "IIAvF");
        NextTickHashSet.I[" ".length()] = I("bs)\u00161+7\u0018\u0013'+=<Hu", "NSHrU");
        NextTickHashSet.I["  ".length()] = I("\b\u0001!\b\u0012sE", "IeEmv");
        NextTickHashSet.I["   ".length()] = I("{R\r5-2\u0016<0;2\u001c\u0018ki", "WrlQI");
    }
    
    @Override
    public Iterator iterator() {
        if (this.minX == -"".length()) {
            return super.iterator();
        }
        if (this.size() <= 0) {
            return (Iterator)Iterators.emptyIterator();
        }
        final int n = this.minX >> (0xBB ^ 0xBF);
        final int n2 = this.minZ >> (0xBE ^ 0xBA);
        final int n3 = this.maxX >> (0x8F ^ 0x8B);
        final int n4 = this.maxZ >> (0x1A ^ 0x1E);
        final ArrayList<Object> list = new ArrayList<Object>();
        int i = n;
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i <= n3) {
            int j = n2;
            "".length();
            if (-1 == 1) {
                throw null;
            }
            while (j <= n4) {
                final Set subSet = this.getSubSet(i, j, "".length() != 0);
                if (subSet != null) {
                    list.add(subSet.iterator());
                }
                ++j;
            }
            ++i;
        }
        if (list.size() <= 0) {
            return (Iterator)Iterators.emptyIterator();
        }
        if (list.size() == " ".length()) {
            return (Iterator)list.get("".length());
        }
        return Iterators.concat((Iterator)list.iterator());
    }
    
    @Override
    public boolean remove(final Object o) {
        if (!(o instanceof NextTickListEntry)) {
            return "".length() != 0;
        }
        final NextTickListEntry nextTickListEntry = (NextTickListEntry)o;
        final Set subSet = this.getSubSet(nextTickListEntry, "".length() != 0);
        if (subSet == null) {
            return "".length() != 0;
        }
        final boolean remove = subSet.remove(nextTickListEntry);
        final boolean remove2 = super.remove(nextTickListEntry);
        if (remove != remove2) {
            throw new IllegalStateException(NextTickHashSet.I["  ".length()] + remove + NextTickHashSet.I["   ".length()] + remove2);
        }
        return remove2;
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
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setIteratorLimits(final int n, final int n2, final int n3, final int n4) {
        this.minX = Math.min(n, n3);
        this.minZ = Math.min(n2, n4);
        this.maxX = Math.max(n, n3);
        this.maxZ = Math.max(n2, n4);
    }
}
