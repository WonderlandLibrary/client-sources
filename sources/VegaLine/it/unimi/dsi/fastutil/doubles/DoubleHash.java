/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

public interface DoubleHash {

    public static interface Strategy {
        public int hashCode(double var1);

        public boolean equals(double var1, double var3);
    }
}

