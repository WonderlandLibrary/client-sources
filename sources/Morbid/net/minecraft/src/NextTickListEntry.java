package net.minecraft.src;

public class NextTickListEntry implements Comparable
{
    private static long nextTickEntryID;
    public int xCoord;
    public int yCoord;
    public int zCoord;
    public int blockID;
    public long scheduledTime;
    public int field_82754_f;
    private long tickEntryID;
    
    static {
        NextTickListEntry.nextTickEntryID = 0L;
    }
    
    public NextTickListEntry(final int par1, final int par2, final int par3, final int par4) {
        this.tickEntryID = NextTickListEntry.nextTickEntryID++;
        this.xCoord = par1;
        this.yCoord = par2;
        this.zCoord = par3;
        this.blockID = par4;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof NextTickListEntry)) {
            return false;
        }
        final NextTickListEntry var2 = (NextTickListEntry)par1Obj;
        return this.xCoord == var2.xCoord && this.yCoord == var2.yCoord && this.zCoord == var2.zCoord && Block.isAssociatedBlockID(this.blockID, var2.blockID);
    }
    
    @Override
    public int hashCode() {
        return (this.xCoord * 1024 * 1024 + this.zCoord * 1024 + this.yCoord) * 256;
    }
    
    public NextTickListEntry setScheduledTime(final long par1) {
        this.scheduledTime = par1;
        return this;
    }
    
    public void func_82753_a(final int par1) {
        this.field_82754_f = par1;
    }
    
    public int comparer(final NextTickListEntry par1NextTickListEntry) {
        return (this.scheduledTime < par1NextTickListEntry.scheduledTime) ? -1 : ((this.scheduledTime > par1NextTickListEntry.scheduledTime) ? 1 : ((this.field_82754_f != par1NextTickListEntry.field_82754_f) ? (this.field_82754_f - par1NextTickListEntry.field_82754_f) : ((this.tickEntryID < par1NextTickListEntry.tickEntryID) ? -1 : ((this.tickEntryID > par1NextTickListEntry.tickEntryID) ? 1 : 0))));
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.blockID) + ": (" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + "), " + this.scheduledTime + ", " + this.field_82754_f + ", " + this.tickEntryID;
    }
    
    @Override
    public int compareTo(final Object par1Obj) {
        return this.comparer((NextTickListEntry)par1Obj);
    }
}
