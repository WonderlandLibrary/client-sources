package net.minecraft.client.resources.data;

import java.util.*;

public class TextureMetadataSection implements IMetadataSection
{
    private final List<Integer> listMipmaps;
    private final boolean textureClamp;
    private final boolean textureBlur;
    
    public boolean getTextureClamp() {
        return this.textureClamp;
    }
    
    public List<Integer> getListMipmaps() {
        return Collections.unmodifiableList((List<? extends Integer>)this.listMipmaps);
    }
    
    public boolean getTextureBlur() {
        return this.textureBlur;
    }
    
    public TextureMetadataSection(final boolean textureBlur, final boolean textureClamp, final List<Integer> listMipmaps) {
        this.textureBlur = textureBlur;
        this.textureClamp = textureClamp;
        this.listMipmaps = listMipmaps;
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
            if (4 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
