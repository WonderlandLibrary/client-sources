package net.minecraft.nbt;

import java.util.*;
import java.io.*;

public class NBTTagIntArray extends NBTBase
{
    private int[] intArray;
    private static final String[] I;
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.intArray.length);
        int i = "".length();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (i < this.intArray.length) {
            dataOutput.writeInt(this.intArray[i]);
            ++i;
        }
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }
    
    @Override
    public NBTBase copy() {
        final int[] array = new int[this.intArray.length];
        System.arraycopy(this.intArray, "".length(), array, "".length(), this.intArray.length);
        return new NBTTagIntArray(array);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\n", "QzSxD");
        NBTTagIntArray.I[" ".length()] = I("J", "fWCgF");
        NBTTagIntArray.I["  ".length()] = I("\t", "TcMJF");
    }
    
    static {
        I();
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
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(192L);
        final int int1 = dataInput.readInt();
        nbtSizeTracker.read((0x25 ^ 0x5) * int1);
        this.intArray = new int[int1];
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < int1) {
            this.intArray[i] = dataInput.readInt();
            ++i;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        int n;
        if (super.equals(o)) {
            n = (Arrays.equals(this.intArray, ((NBTTagIntArray)o).intArray) ? 1 : 0);
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    NBTTagIntArray() {
    }
    
    @Override
    public String toString() {
        String string = NBTTagIntArray.I["".length()];
        final int[] intArray;
        final int length = (intArray = this.intArray).length;
        int i = "".length();
        "".length();
        if (1 < -1) {
            throw null;
        }
        while (i < length) {
            string = String.valueOf(string) + intArray[i] + NBTTagIntArray.I[" ".length()];
            ++i;
        }
        return String.valueOf(string) + NBTTagIntArray.I["  ".length()];
    }
    
    @Override
    public byte getId() {
        return (byte)(0xA8 ^ 0xA3);
    }
    
    public NBTTagIntArray(final int[] intArray) {
        this.intArray = intArray;
    }
    
    public int[] getIntArray() {
        return this.intArray;
    }
}
