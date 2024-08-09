/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import net.minecraft.world.chunk.NibbleArray;

public class NibbleArrayRepeater
extends NibbleArray {
    public NibbleArrayRepeater() {
        super(128);
    }

    public NibbleArrayRepeater(NibbleArray nibbleArray, int n) {
        super(128);
        System.arraycopy(nibbleArray.getData(), n * 128, this.data, 0, 128);
    }

    @Override
    protected int getCoordinateIndex(int n, int n2, int n3) {
        return n3 << 4 | n;
    }

    @Override
    public byte[] getData() {
        byte[] byArray = new byte[2048];
        for (int i = 0; i < 16; ++i) {
            System.arraycopy(this.data, 0, byArray, i * 128, 128);
        }
        return byArray;
    }
}

