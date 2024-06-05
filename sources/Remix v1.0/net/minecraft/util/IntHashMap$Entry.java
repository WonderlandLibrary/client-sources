package net.minecraft.util;

static class Entry
{
    final int hashEntry;
    final int slotHash;
    Object valueEntry;
    Entry nextEntry;
    
    Entry(final int p_i1552_1_, final int p_i1552_2_, final Object p_i1552_3_, final Entry p_i1552_4_) {
        this.valueEntry = p_i1552_3_;
        this.nextEntry = p_i1552_4_;
        this.hashEntry = p_i1552_2_;
        this.slotHash = p_i1552_1_;
    }
    
    public final int getHash() {
        return this.hashEntry;
    }
    
    public final Object getValue() {
        return this.valueEntry;
    }
    
    @Override
    public final boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof Entry)) {
            return false;
        }
        final Entry var2 = (Entry)p_equals_1_;
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
        return IntHashMap.access$000(this.hashEntry);
    }
    
    @Override
    public final String toString() {
        return this.getHash() + "=" + this.getValue();
    }
}
