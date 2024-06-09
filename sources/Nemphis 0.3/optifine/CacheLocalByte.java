/*
 * Decompiled with CFR 0_118.
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
        int x = 0;
        while (x < this.maxX) {
            byte[][] ys = this.cache[x];
            int y = 0;
            while (y < this.maxY) {
                byte[] zs = ys[y];
                int z = 0;
                while (z < this.maxZ) {
                    zs[z] = -1;
                    ++z;
                }
                ++y;
            }
            ++x;
        }
    }

    public void setOffset(int x, int y, int z) {
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
        this.resetCache();
    }

    public byte get(int x, int y, int z) {
        try {
            this.lastZs = this.cache[x - this.offsetX][y - this.offsetY];
            this.lastDz = z - this.offsetZ;
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

