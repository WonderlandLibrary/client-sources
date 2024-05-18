package net.minecraft.nbt;

import net.minecraft.util.*;
import java.io.*;

public class NBTTagDouble extends NBTPrimitive
{
    private double data;
    private static final String[] I;
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("%", "AaWmv");
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return "".length() != 0;
        }
        if (this.data == ((NBTTagDouble)o).data) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getInt() {
        return MathHelper.floor_double(this.data);
    }
    
    @Override
    public short getShort() {
        return (short)(MathHelper.floor_double(this.data) & 16135 + 31978 - 10945 + 28367);
    }
    
    @Override
    public byte getId() {
        return (byte)(0x8F ^ 0x89);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(128L);
        this.data = dataInput.readDouble();
    }
    
    public NBTTagDouble(final double data) {
        this.data = data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(this.data);
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagDouble(this.data);
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    @Override
    public int hashCode() {
        final long doubleToLongBits = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(doubleToLongBits ^ doubleToLongBits >>> (0xA4 ^ 0x84));
    }
    
    @Override
    public byte getByte() {
        return (byte)(MathHelper.floor_double(this.data) & 44 + 77 - 79 + 213);
    }
    
    @Override
    public float getFloat() {
        return (float)this.data;
    }
    
    @Override
    public String toString() {
        return this.data + NBTTagDouble.I["".length()];
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    NBTTagDouble() {
    }
    
    @Override
    public long getLong() {
        return (long)Math.floor(this.data);
    }
}
