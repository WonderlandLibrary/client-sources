/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

public class CacheLocalByte {
    private int maxX = 18;
    private int maxY = 128;
    private int maxZ = 18;
    private int offsetX = 0;
    private int offsetY = 0;
    private int offsetZ = 0;
    private byte[][][] cache = null;
    private byte[] lastZs = null;
    private int lastDz = 0;

    public CacheLocalByte(int n, int n2, int n3) {
        this.maxX = n;
        this.maxY = n2;
        this.maxZ = n3;
        this.cache = new byte[n][n2][n3];
        this.resetCache();
    }

    public void resetCache() {
        for (int i = 0; i < this.maxX; ++i) {
            byte[][] byArray = this.cache[i];
            for (int j = 0; j < this.maxY; ++j) {
                byte[] byArray2 = byArray[j];
                for (int k = 0; k < this.maxZ; ++k) {
                    byArray2[k] = -1;
                }
            }
        }
    }

    public void setOffset(int n, int n2, int n3) {
        this.offsetX = n;
        this.offsetY = n2;
        this.offsetZ = n3;
        this.resetCache();
    }

    public byte get(int n, int n2, int n3) {
        try {
            this.lastZs = this.cache[n - this.offsetX][n2 - this.offsetY];
            this.lastDz = n3 - this.offsetZ;
            return this.lastZs[this.lastDz];
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            arrayIndexOutOfBoundsException.printStackTrace();
            return 1;
        }
    }

    public void setLast(byte by) {
        try {
            this.lastZs[this.lastDz] = by;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

