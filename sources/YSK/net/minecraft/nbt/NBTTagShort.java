package net.minecraft.nbt;

import java.io.*;

public class NBTTagShort extends NBTPrimitive
{
    private short data;
    private static final String[] I;
    
    static {
        I();
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    public NBTTagShort(final short data) {
        this.data = data;
    }
    
    public NBTTagShort() {
    }
    
    @Override
    public String toString() {
        return this.data + NBTTagShort.I["".length()];
    }
    
    @Override
    public float getFloat() {
        return this.data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeShort(this.data);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return "".length() != 0;
        }
        if (this.data == ((NBTTagShort)o).data) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (4 < 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagShort(this.data);
    }
    
    @Override
    public byte getByte() {
        return (byte)(this.data & 31 + 211 - 7 + 20);
    }
    
    @Override
    public long getLong() {
        return this.data;
    }
    
    @Override
    public byte getId() {
        return (byte)"  ".length();
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(80L);
        this.data = dataInput.readShort();
    }
    
    @Override
    public short getShort() {
        return this.data;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("#", "PbCPw");
    }
    
    @Override
    public int getInt() {
        return this.data;
    }
}
