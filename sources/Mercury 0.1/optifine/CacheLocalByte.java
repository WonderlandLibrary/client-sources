/*
 * Decompiled with CFR 0.145.
 */
package optifine;

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

    public CacheLocalByte(int maxX, int maxY, int maxZ) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.cache = new byte[maxX][maxY][maxZ];
        this.resetCache();
    }

    public void resetCache() {
        for (int x2 = 0; x2 < this.maxX; ++x2) {
            byte[][] ys2 = this.cache[x2];
            for (int y2 = 0; y2 < this.maxY; ++y2) {
                byte[] zs2 = ys2[y2];
                for (int z2 = 0; z2 < this.maxZ; ++z2) {
                    zs2[z2] = -1;
                }
            }
        }
    }

    public void setOffset(int x2, int y2, int z2) {
        this.offsetX = x2;
        this.offsetY = y2;
        this.offsetZ = z2;
        this.resetCache();
    }

    public byte get(int x2, int y2, int z2) {
        try {
            this.lastZs = this.cache[x2 - this.offsetX][y2 - this.offsetY];
            this.lastDz = z2 - this.offsetZ;
            return this.lastZs[this.lastDz];
        }
        catch (ArrayIndexOutOfBoundsException var5) {
            var5.printStackTrace();
            return -1;
        }
    }

    public void setLast(byte val) {
        try {
            this.lastZs[this.lastDz] = val;
        }
        catch (Exception var3) {
            var3.printStackTrace();
        }
    }
}

