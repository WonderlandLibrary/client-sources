package net.minecraft.nbt;

import java.io.*;

public class NBTTagByte extends NBTPrimitive
{
    private byte data;
    private static final String[] I;
    
    @Override
    public short getShort() {
        return this.data;
    }
    
    static {
        I();
    }
    
    @Override
    public int getInt() {
        return this.data;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0000", "bhToB");
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(72L);
        this.data = dataInput.readByte();
    }
    
    NBTTagByte() {
    }
    
    @Override
    public byte getByte() {
        return this.data;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
    
    @Override
    public String toString() {
        return this.data + NBTTagByte.I["".length()];
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(this.data);
    }
    
    @Override
    public float getFloat() {
        return this.data;
    }
    
    @Override
    public double getDouble() {
        return this.data;
    }
    
    @Override
    public long getLong() {
        return this.data;
    }
    
    @Override
    public byte getId() {
        return (byte)" ".length();
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagByte(this.data);
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public NBTTagByte(final byte data) {
        this.data = data;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return "".length() != 0;
        }
        if (this.data == ((NBTTagByte)o).data) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
