package net.minecraft.client.resources.data;

import net.minecraft.util.*;

public class PackMetadataSection implements IMetadataSection
{
    private final IChatComponent packDescription;
    private final int packFormat;
    
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getPackFormat() {
        return this.packFormat;
    }
    
    public IChatComponent getPackDescription() {
        return this.packDescription;
    }
    
    public PackMetadataSection(final IChatComponent packDescription, final int packFormat) {
        this.packDescription = packDescription;
        this.packFormat = packFormat;
    }
}
