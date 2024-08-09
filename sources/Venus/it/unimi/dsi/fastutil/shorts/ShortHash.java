/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

public interface ShortHash {

    public static interface Strategy {
        public int hashCode(short var1);

        public boolean equals(short var1, short var2);
    }
}

