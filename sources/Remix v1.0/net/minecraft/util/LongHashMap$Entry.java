package net.minecraft.util;

static class Entry
{
    final long key;
    final int hash;
    Object value;
    Entry nextEntry;
    
    Entry(final int p_i1553_1_, final long p_i1553_2_, final Object p_i1553_4_, final Entry p_i1553_5_) {
        this.value = p_i1553_4_;
        this.nextEntry = p_i1553_5_;
        this.key = p_i1553_2_;
        this.hash = p_i1553_1_;
    }
    
    public final long getKey() {
        return this.key;
    }
    
    public final Object getValue() {
        return this.value;
    }
    
    @Override
    public final boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof Entry)) {
            return false;
        }
        final Entry var2 = (Entry)p_equals_1_;
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
        return LongHashMap.access$000(this.key);
    }
    
    @Override
    public final String toString() {
        return this.getKey() + "=" + this.getValue();
    }
}
