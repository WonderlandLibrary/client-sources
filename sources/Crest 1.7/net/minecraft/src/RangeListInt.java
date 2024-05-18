// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.src;

public class RangeListInt
{
    private RangeInt[] ranges;
    
    public RangeListInt() {
        this.ranges = new RangeInt[0];
    }
    
    public void addRange(final RangeInt ri) {
        this.ranges = (RangeInt[])Config.addObjectToArray(this.ranges, ri);
    }
    
    public boolean isInRange(final int val) {
        for (int i = 0; i < this.ranges.length; ++i) {
            final RangeInt ri = this.ranges[i];
            if (ri.isInRange(val)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < this.ranges.length; ++i) {
            final RangeInt ri = this.ranges[i];
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(ri.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
