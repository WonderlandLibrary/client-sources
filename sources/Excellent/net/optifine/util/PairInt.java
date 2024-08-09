package net.optifine.util;

import lombok.Getter;

public class PairInt {
    @Getter
    private final int left;
    @Getter
    private final int right;
    private final int hashCode;

    public PairInt(int left, int right) {
        this.left = left;
        this.right = right;
        this.hashCode = left + 37 * right;
    }

    public static PairInt of(int left, int right) {
        return new PairInt(left, right);
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof PairInt pairint)) {
            return false;
        } else {
            return this.left == pairint.left && this.right == pairint.right;
        }
    }

    public String toString() {
        return "(" + this.left + ", " + this.right + ")";
    }
}
