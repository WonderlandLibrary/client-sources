package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import java.util.*;

public class BreakingFour extends BakedQuad
{
    private final TextureAtlasSprite texture;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BreakingFour.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x19 ^ 0x1F);
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0xB4 ^ 0xB0);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x7 ^ 0x2);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BreakingFour.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    private void func_178216_a(final int n) {
        final int n2 = (0x5E ^ 0x59) * n;
        final float intBitsToFloat = Float.intBitsToFloat(this.vertexData[n2]);
        final float intBitsToFloat2 = Float.intBitsToFloat(this.vertexData[n2 + " ".length()]);
        final float intBitsToFloat3 = Float.intBitsToFloat(this.vertexData[n2 + "  ".length()]);
        float n3 = 0.0f;
        float n4 = 0.0f;
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.face.ordinal()]) {
            case 1: {
                n3 = intBitsToFloat * 16.0f;
                n4 = (1.0f - intBitsToFloat3) * 16.0f;
                "".length();
                if (3 == -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                n3 = intBitsToFloat * 16.0f;
                n4 = intBitsToFloat3 * 16.0f;
                "".length();
                if (3 == 4) {
                    throw null;
                }
                break;
            }
            case 3: {
                n3 = (1.0f - intBitsToFloat) * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                "".length();
                if (3 < -1) {
                    throw null;
                }
                break;
            }
            case 4: {
                n3 = intBitsToFloat * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                "".length();
                if (true != true) {
                    throw null;
                }
                break;
            }
            case 5: {
                n3 = intBitsToFloat3 * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                "".length();
                if (3 < 3) {
                    throw null;
                }
                break;
            }
            case 6: {
                n3 = (1.0f - intBitsToFloat3) * 16.0f;
                n4 = (1.0f - intBitsToFloat2) * 16.0f;
                break;
            }
        }
        this.vertexData[n2 + (0x1F ^ 0x1B)] = Float.floatToRawIntBits(this.texture.getInterpolatedU(n3));
        this.vertexData[n2 + (0x9E ^ 0x9A) + " ".length()] = Float.floatToRawIntBits(this.texture.getInterpolatedV(n4));
    }
    
    private void func_178217_e() {
        int i = "".length();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (i < (0x93 ^ 0x97)) {
            this.func_178216_a(i);
            ++i;
        }
    }
    
    public BreakingFour(final BakedQuad bakedQuad, final TextureAtlasSprite texture) {
        super(Arrays.copyOf(bakedQuad.getVertexData(), bakedQuad.getVertexData().length), bakedQuad.tintIndex, FaceBakery.getFacingFromVertexData(bakedQuad.getVertexData()));
        this.texture = texture;
        this.func_178217_e();
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
            if (0 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
