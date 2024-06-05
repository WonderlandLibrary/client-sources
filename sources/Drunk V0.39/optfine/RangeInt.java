/*
 * Decompiled with CFR 0.152.
 */
package optfine;

public class RangeInt {
    private int min;
    private int max;

    public RangeInt(int p_i54_1_, int p_i54_2_) {
        this.min = Math.min(p_i54_1_, p_i54_2_);
        this.max = Math.max(p_i54_1_, p_i54_2_);
    }

    public boolean isInRange(int p_isInRange_1_) {
        if (p_isInRange_1_ < this.min) {
            return false;
        }
        if (p_isInRange_1_ > this.max) return false;
        return true;
    }

    public String toString() {
        return "min: " + this.min + ", max: " + this.max;
    }
}

