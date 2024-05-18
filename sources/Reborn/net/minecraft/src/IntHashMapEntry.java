package net.minecraft.src;

class IntHashMapEntry
{
    final int hashEntry;
    Object valueEntry;
    IntHashMapEntry nextEntry;
    final int slotHash;
    
    IntHashMapEntry(final int par1, final int par2, final Object par3Obj, final IntHashMapEntry par4IntHashMapEntry) {
        this.valueEntry = par3Obj;
        this.nextEntry = par4IntHashMapEntry;
        this.hashEntry = par2;
        this.slotHash = par1;
    }
    
    public final int getHash() {
        return this.hashEntry;
    }
    
    public final Object getValue() {
        return this.valueEntry;
    }
    
    @Override
    public final boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof IntHashMapEntry)) {
            return false;
        }
        final IntHashMapEntry var2 = (IntHashMapEntry)par1Obj;
        final Integer var3 = this.getHash();
        final Integer var4 = var2.getHash();
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
        return IntHashMap.getHash(this.hashEntry);
    }
    
    @Override
    public final String toString() {
        return String.valueOf(this.getHash()) + "=" + this.getValue();
    }
}
