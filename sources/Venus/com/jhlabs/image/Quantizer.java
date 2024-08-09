/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

public interface Quantizer {
    public void setup(int var1);

    public void addPixels(int[] var1, int var2, int var3);

    public int[] buildColorTable();

    public int getIndexForColor(int var1);
}

