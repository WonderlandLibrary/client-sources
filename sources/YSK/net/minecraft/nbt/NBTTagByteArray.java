package net.minecraft.nbt;

import java.util.*;
import java.io.*;

public class NBTTagByteArray extends NBTBase
{
    private static final String[] I;
    private byte[] data;
    
    @Override
    public NBTBase copy() {
        final byte[] array = new byte[this.data.length];
        System.arraycopy(this.data, "".length(), array, "".length(), this.data.length);
        return new NBTTagByteArray(array);
    }
    
    @Override
    public boolean equals(final Object o) {
        int n;
        if (super.equals(o)) {
            n = (Arrays.equals(this.data, ((NBTTagByteArray)o).data) ? 1 : 0);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(")", "rOnnU");
        NBTTagByteArray.I[" ".length()] = I("w+*\u001c2$\u0014", "WIShW");
    }
    
    public NBTTagByteArray(final byte[] data) {
        this.data = data;
    }
    
    NBTTagByteArray() {
    }
    
    @Override
    public String toString() {
        return NBTTagByteArray.I["".length()] + this.data.length + NBTTagByteArray.I[" ".length()];
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(192L);
        final int int1 = dataInput.readInt();
        nbtSizeTracker.read((0x57 ^ 0x5F) * int1);
        dataInput.readFully(this.data = new byte[int1]);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }
    
    public byte[] getByteArray() {
        return this.data;
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.data.length);
        dataOutput.write(this.data);
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public byte getId() {
        return (byte)(0x3B ^ 0x3C);
    }
}
