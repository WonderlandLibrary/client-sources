/*
 * Decompiled with CFR 0_118.
 */
package optifine;

import optifine.Config;
import optifine.RangeInt;

public class RangeListInt {
    private RangeInt[] ranges = new RangeInt[0];

    public void addRange(RangeInt ri) {
        this.ranges = (RangeInt[])Config.addObjectToArray(this.ranges, ri);
    }

    public boolean isInRange(int val) {
        int i = 0;
        while (i < this.ranges.length) {
            RangeInt ri = this.ranges[i];
            if (ri.isInRange(val)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public int getCountRanges() {
        return this.ranges.length;
    }

    public RangeInt getRange(int i) {
        return this.ranges[i];
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        int i = 0;
        while (i < this.ranges.length) {
            RangeInt ri = this.ranges[i];
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(ri.toString());
            ++i;
        }
        sb.append("]");
        return sb.toString();
    }
}

