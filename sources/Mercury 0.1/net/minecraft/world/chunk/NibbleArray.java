/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.world.chunk;

public class NibbleArray {
    private final byte[] data;
    private static final String __OBFID = "CL_00000371";

    public NibbleArray() {
        this.data = new byte[2048];
    }

    public NibbleArray(byte[] storageArray) {
        this.data = storageArray;
        if (storageArray.length != 2048) {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + storageArray.length);
        }
    }

    public int get(int x2, int y2, int z2) {
        return this.getFromIndex(this.getCoordinateIndex(x2, y2, z2));
    }

    public void set(int x2, int y2, int z2, int value) {
        this.setIndex(this.getCoordinateIndex(x2, y2, z2), value);
    }

    private int getCoordinateIndex(int x2, int y2, int z2) {
        return y2 << 8 | z2 << 4 | x2;
    }

    public int getFromIndex(int index) {
        int var2 = this.func_177478_c(index);
        return this.func_177479_b(index) ? this.data[var2] & 15 : this.data[var2] >> 4 & 15;
    }

    public void setIndex(int index, int value) {
        int var3 = this.func_177478_c(index);
        this.data[var3] = this.func_177479_b(index) ? (byte)(this.data[var3] & 240 | value & 15) : (byte)(this.data[var3] & 15 | (value & 15) << 4);
    }

    private boolean func_177479_b(int p_177479_1_) {
        return (p_177479_1_ & 1) == 0;
    }

    private int func_177478_c(int p_177478_1_) {
        return p_177478_1_ >> 1;
    }

    public byte[] getData() {
        return this.data;
    }
}

