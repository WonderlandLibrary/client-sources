/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

public interface ByteHash {

    public static interface Strategy {
        public int hashCode(byte var1);

        public boolean equals(byte var1, byte var2);
    }
}

