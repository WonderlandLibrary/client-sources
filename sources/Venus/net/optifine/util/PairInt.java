/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

public class PairInt {
    private int left;
    private int right;
    private final int hashCode;

    public PairInt(int n, int n2) {
        this.left = n;
        this.right = n2;
        this.hashCode = n + 37 * n2;
    }

    public static PairInt of(int n, int n2) {
        return new PairInt(n, n2);
    }

    public int getLeft() {
        return this.left;
    }

    public int getRight() {
        return this.right;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof PairInt)) {
            return true;
        }
        PairInt pairInt = (PairInt)object;
        return this.left == pairInt.left && this.right == pairInt.right;
    }

    public String toString() {
        return "(" + this.left + ", " + this.right + ")";
    }
}

