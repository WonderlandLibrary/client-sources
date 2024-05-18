package net.minecraft.nbt;

import java.io.*;

public class NBTTagInt extends NBTPrimitive
{
    private int data;
    
    @Override
    public long getLong() {
        return this.data;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return "".length() != 0;
        }
        if (this.data == ((NBTTagInt)o).data) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagInt(this.data);
    }
    
    public NBTTagInt(final int data) {
        this.data = data;
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(96L);
        this.data = dataInput.readInt();
    }
    
    @Override
    public byte getId() {
        return (byte)"   ".length();
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append(this.data).toString();
    }
    
    NBTTagInt() {
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public float getFloat() {
        return this.data;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
    
    @Override
    public short getShort() {
        return (short)(this.data & 5876 + 43411 - 16481 + 32729);
    }
    
    @Override
    public int getInt() {
        return this.data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data);
    }
    
    @Override
    public byte getByte() {
        return (byte)(this.data & 84 + 129 - 171 + 213);
    }
}
