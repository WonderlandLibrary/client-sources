package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

public class BakedQuad
{
    protected final int[] vertexData;
    protected final int tintIndex;
    private static final String __OBFID;
    private TextureAtlasSprite sprite;
    private int[] vertexDataSingle;
    private static final String[] I;
    protected final EnumFacing face;
    
    @Override
    public String toString() {
        return BakedQuad.I["".length()] + this.vertexData.length / (0xB8 ^ 0xBF) + BakedQuad.I[" ".length()] + this.tintIndex + BakedQuad.I["  ".length()] + this.face + BakedQuad.I["   ".length()] + this.sprite;
    }
    
    public int[] getVertexData() {
        return this.vertexData;
    }
    
    public int getTintIndex() {
        return this.tintIndex;
    }
    
    public boolean hasTintIndex() {
        if (this.tintIndex != -" ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public BakedQuad(final int[] vertexData, final int tintIndex, final EnumFacing face) {
        this.sprite = null;
        this.vertexDataSingle = null;
        this.vertexData = vertexData;
        this.tintIndex = tintIndex;
        this.face = face;
    }
    
    static {
        I();
        __OBFID = BakedQuad.I[0x38 ^ 0x3C];
    }
    
    public EnumFacing getFace() {
        return this.face;
    }
    
    public TextureAtlasSprite getSprite() {
        return this.sprite;
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int[] getVertexDataSingle() {
        if (this.vertexDataSingle == null) {
            this.vertexDataSingle = makeVertexDataSingle(this.vertexData, this.sprite);
        }
        return this.vertexDataSingle;
    }
    
    private static int[] makeVertexDataSingle(final int[] array, final TextureAtlasSprite textureAtlasSprite) {
        final int[] array2 = new int[array.length];
        int i = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (i < array2.length) {
            array2[i] = array[i];
            ++i;
        }
        final int n = textureAtlasSprite.sheetWidth / textureAtlasSprite.getIconWidth();
        final int n2 = textureAtlasSprite.sheetHeight / textureAtlasSprite.getIconHeight();
        int j = "".length();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (j < (0x52 ^ 0x56)) {
            final int n3 = j * (0x21 ^ 0x26);
            final float intBitsToFloat = Float.intBitsToFloat(array2[n3 + (0x45 ^ 0x41)]);
            final float intBitsToFloat2 = Float.intBitsToFloat(array2[n3 + (0x0 ^ 0x4) + " ".length()]);
            final float singleU = textureAtlasSprite.toSingleU(intBitsToFloat);
            final float singleV = textureAtlasSprite.toSingleV(intBitsToFloat2);
            array2[n3 + (0xB4 ^ 0xB0)] = Float.floatToRawIntBits(singleU);
            array2[n3 + (0x8 ^ 0xC) + " ".length()] = Float.floatToRawIntBits(singleV);
            ++j;
        }
        return array2;
    }
    
    private static void I() {
        (I = new String[0x48 ^ 0x4D])["".length()] = I("!!'\u001c0/~u", "WDUhU");
        BakedQuad.I[" ".length()] = I("gG\u0017,(?]C", "KgcEF");
        BakedQuad.I["  ".length()] = I("Om('\u000e\n#)|M", "cMNFm");
        BakedQuad.I["   ".length()] = I("iv\"40,\"4~b", "EVQDB");
        BakedQuad.I[0x61 ^ 0x65] = I(")\u0003\u0005\\WZ\u007fhYVX", "jOZlg");
    }
    
    public BakedQuad(final int[] vertexData, final int tintIndex, final EnumFacing face, final TextureAtlasSprite sprite) {
        this.sprite = null;
        this.vertexDataSingle = null;
        this.vertexData = vertexData;
        this.tintIndex = tintIndex;
        this.face = face;
        this.sprite = sprite;
    }
}
