package net.minecraft.nbt;

import java.io.*;

public abstract class NBTBase
{
    private static final String[] I;
    public static final String[] NBT_TYPES;
    
    @Override
    public abstract String toString();
    
    abstract void write(final DataOutput p0) throws IOException;
    
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
    
    protected static NBTBase createNewByType(final byte b) {
        switch (b) {
            case 0: {
                return new NBTTagEnd();
            }
            case 1: {
                return new NBTTagByte();
            }
            case 2: {
                return new NBTTagShort();
            }
            case 3: {
                return new NBTTagInt();
            }
            case 4: {
                return new NBTTagLong();
            }
            case 5: {
                return new NBTTagFloat();
            }
            case 6: {
                return new NBTTagDouble();
            }
            case 7: {
                return new NBTTagByteArray();
            }
            case 8: {
                return new NBTTagString();
            }
            case 9: {
                return new NBTTagList();
            }
            case 10: {
                return new NBTTagCompound();
            }
            case 11: {
                return new NBTTagIntArray();
            }
            default: {
                return null;
            }
        }
    }
    
    private static void I() {
        (I = new String[0x3F ^ 0x33])["".length()] = I("\u0002-(", "GclqA");
        NBTBase.I[" ".length()] = I("7\u0011\u00181", "uHLtF");
        NBTBase.I["  ".length()] = I(";*,!\u0000", "hbcsT");
        NBTBase.I["   ".length()] = I("#\u000f\r", "jAYnD");
        NBTBase.I[0x60 ^ 0x64] = I(";\t?/", "wFqhP");
        NBTBase.I[0xC ^ 0x9] = I("\u0001*\u0002#\u0006", "GfMbR");
        NBTBase.I[0x17 ^ 0x11] = I("5\u00151(\r4", "qZdjA");
        NBTBase.I[0x2D ^ 0x2A] = I("4\u000f8(*+", "vVlmq");
        NBTBase.I[0x7B ^ 0x73] = I("\u0015\u0018\u0017>\u0004\u0001", "FLEwJ");
        NBTBase.I[0xAE ^ 0xA7] = I("\"/$$", "nfwpS");
        NBTBase.I[0x50 ^ 0x5A] = I("\f\n(\b\u001c\u001a\u000b!", "OEeXS");
        NBTBase.I[0xA3 ^ 0xA8] = I(",\u0005\u001e+\u0011", "eKJpL");
    }
    
    public abstract byte getId();
    
    public boolean hasNoTags() {
        return "".length() != 0;
    }
    
    protected String getString() {
        return this.toString();
    }
    
    public abstract NBTBase copy();
    
    abstract void read(final DataInput p0, final int p1, final NBTSizeTracker p2) throws IOException;
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof NBTBase)) {
            return "".length() != 0;
        }
        if (this.getId() == ((NBTBase)o).getId()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int hashCode() {
        return this.getId();
    }
    
    static {
        I();
        final String[] nbt_TYPES = new String[0x86 ^ 0x8A];
        nbt_TYPES["".length()] = NBTBase.I["".length()];
        nbt_TYPES[" ".length()] = NBTBase.I[" ".length()];
        nbt_TYPES["  ".length()] = NBTBase.I["  ".length()];
        nbt_TYPES["   ".length()] = NBTBase.I["   ".length()];
        nbt_TYPES[0x7E ^ 0x7A] = NBTBase.I[0x41 ^ 0x45];
        nbt_TYPES[0x45 ^ 0x40] = NBTBase.I[0x8C ^ 0x89];
        nbt_TYPES[0x19 ^ 0x1F] = NBTBase.I[0x1F ^ 0x19];
        nbt_TYPES[0x5B ^ 0x5C] = NBTBase.I[0x58 ^ 0x5F];
        nbt_TYPES[0x6 ^ 0xE] = NBTBase.I[0xA1 ^ 0xA9];
        nbt_TYPES[0x8C ^ 0x85] = NBTBase.I[0x60 ^ 0x69];
        nbt_TYPES[0xF ^ 0x5] = NBTBase.I[0x66 ^ 0x6C];
        nbt_TYPES[0x1E ^ 0x15] = NBTBase.I[0x2F ^ 0x24];
        NBT_TYPES = nbt_TYPES;
    }
    
    public abstract static class NBTPrimitive extends NBTBase
    {
        public abstract float getFloat();
        
        public abstract long getLong();
        
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
                if (3 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public abstract short getShort();
        
        public abstract int getInt();
        
        public abstract double getDouble();
        
        public abstract byte getByte();
    }
}
