package net.minecraft.world;

import net.minecraft.util.*;
import net.minecraft.block.*;

public class NextTickListEntry implements Comparable<NextTickListEntry>
{
    private long tickEntryID;
    public long scheduledTime;
    public int priority;
    public final BlockPos position;
    private static final String[] I;
    private static long nextTickEntryID;
    private final Block block;
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((NextTickListEntry)o);
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public NextTickListEntry setScheduledTime(final long scheduledTime) {
        this.scheduledTime = scheduledTime;
        return this;
    }
    
    private static void I() {
        (I = new String[0x4C ^ 0x48])["".length()] = I("Nd", "tDrDr");
        NextTickListEntry.I[" ".length()] = I("tq", "XQdPE");
        NextTickListEntry.I["  ".length()] = I("xW", "TwsFo");
        NextTickListEntry.I["   ".length()] = I("Ht", "dTQSy");
    }
    
    @Override
    public int hashCode() {
        return this.position.hashCode();
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setPriority(final int priority) {
        this.priority = priority;
    }
    
    @Override
    public int compareTo(final NextTickListEntry nextTickListEntry) {
        int n;
        if (this.scheduledTime < nextTickListEntry.scheduledTime) {
            n = -" ".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else if (this.scheduledTime > nextTickListEntry.scheduledTime) {
            n = " ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (this.priority != nextTickListEntry.priority) {
            n = this.priority - nextTickListEntry.priority;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (this.tickEntryID < nextTickListEntry.tickEntryID) {
            n = -" ".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else if (this.tickEntryID > nextTickListEntry.tickEntryID) {
            n = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public String toString() {
        return String.valueOf(Block.getIdFromBlock(this.block)) + NextTickListEntry.I["".length()] + this.position + NextTickListEntry.I[" ".length()] + this.scheduledTime + NextTickListEntry.I["  ".length()] + this.priority + NextTickListEntry.I["   ".length()] + this.tickEntryID;
    }
    
    public NextTickListEntry(final BlockPos position, final Block block) {
        this.tickEntryID = NextTickListEntry.nextTickEntryID++;
        this.position = position;
        this.block = block;
    }
    
    static {
        I();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NextTickListEntry)) {
            return "".length() != 0;
        }
        final NextTickListEntry nextTickListEntry = (NextTickListEntry)o;
        if (this.position.equals(nextTickListEntry.position) && Block.isEqualTo(this.block, nextTickListEntry.block)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
