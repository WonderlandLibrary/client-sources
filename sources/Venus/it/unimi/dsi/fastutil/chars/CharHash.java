/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

public interface CharHash {

    public static interface Strategy {
        public int hashCode(char var1);

        public boolean equals(char var1, char var2);
    }
}

