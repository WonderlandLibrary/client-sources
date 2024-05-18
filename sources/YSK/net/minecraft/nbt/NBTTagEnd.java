package net.minecraft.nbt;

import java.io.*;

public class NBTTagEnd extends NBTBase
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\f8\b", "IvLsS");
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
            if (3 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
    }
    
    static {
        I();
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagEnd();
    }
    
    @Override
    public byte getId() {
        return (byte)"".length();
    }
    
    @Override
    public String toString() {
        return NBTTagEnd.I["".length()];
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(64L);
    }
}
