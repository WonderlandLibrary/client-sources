/*
 * Decompiled with CFR 0.152.
 */
package optfine;

import optfine.Config;
import optfine.RangeInt;

public class RangeListInt {
    private RangeInt[] ranges = new RangeInt[0];

    public void addRange(RangeInt p_addRange_1_) {
        this.ranges = (RangeInt[])Config.addObjectToArray(this.ranges, p_addRange_1_);
    }

    public boolean isInRange(int p_isInRange_1_) {
        int i = 0;
        while (i < this.ranges.length) {
            RangeInt rangeint = this.ranges[i];
            if (rangeint.isInRange(p_isInRange_1_)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public String toString() {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("[");
        int i = 0;
        while (true) {
            if (i >= this.ranges.length) {
                stringbuffer.append("]");
                return stringbuffer.toString();
            }
            RangeInt rangeint = this.ranges[i];
            if (i > 0) {
                stringbuffer.append(", ");
            }
            stringbuffer.append(rangeint.toString());
            ++i;
        }
    }
}

