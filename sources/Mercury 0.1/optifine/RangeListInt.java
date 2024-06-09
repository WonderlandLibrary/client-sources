/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import optifine.Config;
import optifine.RangeInt;

public class RangeListInt {
    private RangeInt[] ranges = new RangeInt[0];

    public void addRange(RangeInt ri2) {
        this.ranges = (RangeInt[])Config.addObjectToArray(this.ranges, ri2);
    }

    public boolean isInRange(int val) {
        for (int i2 = 0; i2 < this.ranges.length; ++i2) {
            RangeInt ri2 = this.ranges[i2];
            if (!ri2.isInRange(val)) continue;
            return true;
        }
        return false;
    }

    public int getCountRanges() {
        return this.ranges.length;
    }

    public RangeInt getRange(int i2) {
        return this.ranges[i2];
    }

    public String toString() {
        StringBuffer sb2 = new StringBuffer();
        sb2.append("[");
        for (int i2 = 0; i2 < this.ranges.length; ++i2) {
            RangeInt ri2 = this.ranges[i2];
            if (i2 > 0) {
                sb2.append(", ");
            }
            sb2.append(ri2.toString());
        }
        sb2.append("]");
        return sb2.toString();
    }
}

