package net.minecraft.nbt;

import java.io.*;

public class NBTTagString extends NBTBase
{
    private static final String[] I;
    private String data;
    
    @Override
    public byte getId() {
        return (byte)(0x3A ^ 0x32);
    }
    
    public String getString() {
        return this.data;
    }
    
    @Override
    public boolean hasNoTags() {
        return this.data.isEmpty();
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    void write(final DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.data);
    }
    
    @Override
    void read(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        nbtSizeTracker.read(288L);
        this.data = dataInput.readUTF();
        nbtSizeTracker.read((0xF ^ 0x1F) * this.data.length());
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }
    
    @Override
    public NBTBase copy() {
        return new NBTTagString(this.data);
    }
    
    static {
        I();
    }
    
    public NBTTagString(final String data) {
        this.data = data;
        if (data == null) {
            throw new IllegalArgumentException(NBTTagString.I[" ".length()]);
        }
    }
    
    public NBTTagString() {
        this.data = NBTTagString.I["".length()];
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return "".length() != 0;
        }
        final NBTTagString nbtTagString = (NBTTagString)o;
        if ((this.data != null || nbtTagString.data != null) && (this.data == null || !this.data.equals(nbtTagString.data))) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public String toString() {
        return NBTTagString.I["  ".length()] + this.data.replace(NBTTagString.I["   ".length()], NBTTagString.I[0x3C ^ 0x38]) + NBTTagString.I[0xBB ^ 0xBE];
    }
    
    private static void I() {
        (I = new String[0x27 ^ 0x21])["".length()] = I("", "UIsNw");
        NBTTagString.I[" ".length()] = I("& \u00169\u001dC>\u0012?\r\r*F#\u000b\u0017m\u0007!\b\f:\u0003)", "cMfMd");
        NBTTagString.I["  ".length()] = I("S", "qmjuo");
        NBTTagString.I["   ".length()] = I("J", "hAqvm");
        NBTTagString.I[0x7E ^ 0x7A] = I("\u0018P", "Drxck");
        NBTTagString.I[0x68 ^ 0x6D] = I("T", "vRrUg");
    }
}
