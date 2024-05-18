package net.minecraft.nbt;

import net.minecraft.util.*;
import java.io.*;

public class NBTTagFloat extends NBTPrimitive
{
    private static final String[] I;
    private float data;
    
    @Override
    public short getShort() {
        return (short)(MathHelper.floor_float(this.data) & 17552 + 1817 + 13374 + 32792);
    }
    
    @Override
    public String toString() {
        return this.data + NBTTagFloat.I["".length()];
    }
    
    @Override
    public long getLong() {
        return (long)this.data;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return "".length() != 0;
        }
        if (this.data == ((NBTTagFloat)o).data) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public float getFloat() {
        return this.data;
    }
    
    public NBTTagFloat(final float data) {
        this.data = data;
    }
    
    @Override
    public byte getId() {
        return (byte)(0x23 ^ 0x26);
    }
    
    @Override
    public byte getByte() {
        return (byte)(MathHelper.floor_float(this.data) & 179 + 52 - 89 + 113);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeFloat(this.data);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("0", "VWEqL");
    }
    
    @Override
    public int getInt() {
        return MathHelper.floor_float(this.data);
    }
    
    NBTTagFloat() {
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagFloat(this.data);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(96L);
        this.data = dataInput.readFloat();
    }
    
    static {
        I();
    }
}
