/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.util.Util;

public class NibbleArray {
    @Nullable
    protected byte[] data;

    public NibbleArray() {
    }

    public NibbleArray(byte[] byArray) {
        this.data = byArray;
        if (byArray.length != 2048) {
            throw Util.pauseDevMode(new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + byArray.length));
        }
    }

    protected NibbleArray(int n) {
        this.data = new byte[n];
    }

    public int get(int n, int n2, int n3) {
        return this.getFromIndex(this.getCoordinateIndex(n, n2, n3));
    }

    public void set(int n, int n2, int n3, int n4) {
        this.setIndex(this.getCoordinateIndex(n, n2, n3), n4);
    }

    protected int getCoordinateIndex(int n, int n2, int n3) {
        return n2 << 8 | n3 << 4 | n;
    }

    private int getFromIndex(int n) {
        if (this.data == null) {
            return 1;
        }
        int n2 = this.getNibbleIndex(n);
        return this.isLowerNibble(n) ? this.data[n2] & 0xF : this.data[n2] >> 4 & 0xF;
    }

    private void setIndex(int n, int n2) {
        if (this.data == null) {
            this.data = new byte[2048];
        }
        int n3 = this.getNibbleIndex(n);
        this.data[n3] = this.isLowerNibble(n) ? (byte)(this.data[n3] & 0xF0 | n2 & 0xF) : (byte)(this.data[n3] & 0xF | (n2 & 0xF) << 4);
    }

    private boolean isLowerNibble(int n) {
        return (n & 1) == 0;
    }

    private int getNibbleIndex(int n) {
        return n >> 1;
    }

    public byte[] getData() {
        if (this.data == null) {
            this.data = new byte[2048];
        }
        return this.data;
    }

    public NibbleArray copy() {
        return this.data == null ? new NibbleArray() : new NibbleArray((byte[])this.data.clone());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4096; ++i) {
            stringBuilder.append(Integer.toHexString(this.getFromIndex(i)));
            if ((i & 0xF) == 15) {
                stringBuilder.append("\n");
            }
            if ((i & 0xFF) != 255) continue;
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public boolean isEmpty() {
        return this.data == null;
    }
}

