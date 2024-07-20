/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

public interface FloatHash {

    public static interface Strategy {
        public int hashCode(float var1);

        public boolean equals(float var1, float var2);
    }
}

