/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

public interface BooleanHash {

    public static interface Strategy {
        public int hashCode(boolean var1);

        public boolean equals(boolean var1, boolean var2);
    }
}

