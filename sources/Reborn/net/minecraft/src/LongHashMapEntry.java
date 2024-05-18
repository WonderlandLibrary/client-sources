package net.minecraft.src;

class LongHashMapEntry
{
    final long key;
    Object value;
    LongHashMapEntry nextEntry;
    final int hash;
    
    LongHashMapEntry(final int par1, final long par2, final Object par4Obj, final LongHashMapEntry par5LongHashMapEntry) {
        this.value = par4Obj;
        this.nextEntry = par5LongHashMapEntry;
        this.key = par2;
        this.hash = par1;
    }
    
    public final long getKey() {
        return this.key;
    }
    
    public final Object getValue() {
        return this.value;
    }
    
    @Override
    public final boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof LongHashMapEntry)) {
            return false;
        }
        final LongHashMapEntry var2 = (LongHashMapEntry)par1Obj;
        final Long var3 = this.getKey();
        final Long var4 = var2.getKey();
        if (var3 == var4 || (var3 != null && var3.equals(var4))) {
            final Object var5 = this.getValue();
            final Object var6 = var2.getValue();
            if (var5 == var6 || (var5 != null && var5.equals(var6))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public final int hashCode() {
        return LongHashMap.getHashCode(this.key);
    }
    
    @Override
    public final String toString() {
        return String.valueOf(this.getKey()) + "=" + this.getValue();
    }
}
