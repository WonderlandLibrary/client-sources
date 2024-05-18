package net.minecraft.nbt;

import java.io.*;

public class NBTTagLong extends NBTPrimitive
{
    private static final String[] I;
    private long data;
    
    @Override
    public short getShort() {
        return (short)(this.data & 0xFFFFL);
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public byte getId() {
        return (byte)(0xA2 ^ 0xA6);
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.data);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return "".length() != 0;
        }
        if (this.data == ((NBTTagLong)o).data) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public long getLong() {
        return this.data;
    }
    
    @Override
    public String toString() {
        return this.data + NBTTagLong.I["".length()];
    }
    
    NBTTagLong() {
    }
    
    @Override
    public float getFloat() {
        return this.data;
    }
    
    @Override
    public byte getByte() {
        return (byte)(this.data & 0xFFL);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001e", "Rrgsm");
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(128L);
        this.data = dataInput.readLong();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)(this.data ^ this.data >>> (0xA1 ^ 0x81));
    }
    
    @Override
    public int getInt() {
        return (int)(this.data & -1L);
    }
    
    static {
        I();
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagLong(this.data);
    }
    
    public NBTTagLong(final long data) {
        this.data = data;
    }
}
