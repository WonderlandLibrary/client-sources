package optfine;

import net.minecraft.client.renderer.block.model.*;
import java.util.*;

public class NaturalProperties
{
    private Map[] quadMaps;
    private static final String[] I;
    public boolean flip;
    public int rotation;
    
    private int[] fixVertexData(final int[] array, final int n, final boolean b) {
        final int[] array2 = new int[array.length];
        int i = "".length();
        "".length();
        if (4 < 2) {
            throw null;
        }
        while (i < array.length) {
            array2[i] = array[i];
            ++i;
        }
        int n2 = (0xB4 ^ 0xB0) - n;
        if (b) {
            n2 += 3;
        }
        int n3 = n2 % (0x56 ^ 0x52);
        int j = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (j < (0xC4 ^ 0xC0)) {
            final int n4 = j * (0x61 ^ 0x66);
            final int n5 = n3 * (0x10 ^ 0x17);
            array2[n5 + (0x67 ^ 0x63)] = array[n4 + (0x69 ^ 0x6D)];
            array2[n5 + (0xA5 ^ 0xA1) + " ".length()] = array[n4 + (0x67 ^ 0x63) + " ".length()];
            if (b) {
                if (--n3 < 0) {
                    n3 = "   ".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
            }
            else if (++n3 > "   ".length()) {
                n3 = "".length();
            }
            ++j;
        }
        return array2;
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public boolean isValid() {
        int n;
        if (this.rotation != "  ".length() && this.rotation != (0x51 ^ 0x55)) {
            n = (this.flip ? 1 : 0);
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public synchronized BakedQuad getQuad(final BakedQuad bakedQuad, final int n, final boolean b) {
        int n2 = n;
        if (b) {
            n2 = (n | (0xE ^ 0xA));
        }
        if (n2 > 0 && n2 < this.quadMaps.length) {
            Map<BakedQuad, BakedQuad> map = (Map<BakedQuad, BakedQuad>)this.quadMaps[n2];
            if (map == null) {
                map = new IdentityHashMap<BakedQuad, BakedQuad>(" ".length());
                this.quadMaps[n2] = map;
            }
            BakedQuad quad = map.get(bakedQuad);
            if (quad == null) {
                quad = this.makeQuad(bakedQuad, n, b);
                map.put(bakedQuad, quad);
            }
            return quad;
        }
        return bakedQuad;
    }
    
    public NaturalProperties(final String s) {
        this.rotation = " ".length();
        this.flip = ("".length() != 0);
        this.quadMaps = new Map[0x4B ^ 0x43];
        if (s.equals(NaturalProperties.I["".length()])) {
            this.rotation = (0xC7 ^ 0xC3);
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else if (s.equals(NaturalProperties.I[" ".length()])) {
            this.rotation = "  ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (s.equals(NaturalProperties.I["  ".length()])) {
            this.flip = (" ".length() != 0);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else if (s.equals(NaturalProperties.I["   ".length()])) {
            this.rotation = (0x64 ^ 0x60);
            this.flip = (" ".length() != 0);
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else if (s.equals(NaturalProperties.I[0xB3 ^ 0xB7])) {
            this.rotation = "  ".length();
            this.flip = (" ".length() != 0);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            Config.warn(NaturalProperties.I[0x73 ^ 0x76] + s);
        }
    }
    
    private BakedQuad makeQuad(final BakedQuad bakedQuad, final int n, final boolean b) {
        return new BakedQuad(this.fixVertexData(bakedQuad.getVertexData(), n, b), bakedQuad.getTintIndex(), bakedQuad.getFace(), bakedQuad.getSprite());
    }
    
    private static void I() {
        (I = new String[0x8 ^ 0xE])["".length()] = I("S", "gNQOv");
        NaturalProperties.I[" ".length()] = I("B", "pStRf");
        NaturalProperties.I["  ".length()] = I("4", "rNgGj");
        NaturalProperties.I["   ".length()] = I("F\u0015", "rSTnE");
        NaturalProperties.I[0x7F ^ 0x7B] = I("t>", "FxyDw");
        NaturalProperties.I[0x6E ^ 0x6B] = I("\u000f/\u001b\u001e\u0015 \";\u000e\u001f5;\u001d\u000e\u0014{n:\u0005\f/!\u0018\u0005G57\u001f\u000e]a", "ANokg");
    }
}
