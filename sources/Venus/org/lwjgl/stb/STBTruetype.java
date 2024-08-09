/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.LibSTB;
import org.lwjgl.stb.STBRPRect;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTBitmap;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackRange;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.stb.STBTTVertex;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class STBTruetype {
    public static final byte STBTT_vmove = 1;
    public static final byte STBTT_vline = 2;
    public static final byte STBTT_vcurve = 3;
    public static final byte STBTT_vcubic = 4;
    public static final int STBTT_MACSTYLE_DONTCARE = 0;
    public static final int STBTT_MACSTYLE_BOLD = 1;
    public static final int STBTT_MACSTYLE_ITALIC = 2;
    public static final int STBTT_MACSTYLE_UNDERSCORE = 4;
    public static final int STBTT_MACSTYLE_NONE = 8;
    public static final int STBTT_PLATFORM_ID_UNICODE = 0;
    public static final int STBTT_PLATFORM_ID_MAC = 1;
    public static final int STBTT_PLATFORM_ID_ISO = 2;
    public static final int STBTT_PLATFORM_ID_MICROSOFT = 3;
    public static final int STBTT_UNICODE_EID_UNICODE_1_0 = 0;
    public static final int STBTT_UNICODE_EID_UNICODE_1_1 = 1;
    public static final int STBTT_UNICODE_EID_ISO_10646 = 2;
    public static final int STBTT_UNICODE_EID_UNICODE_2_0_BMP = 3;
    public static final int STBTT_UNICODE_EID_UNICODE_2_0_FULL = 4;
    public static final int STBTT_MS_EID_SYMBOL = 0;
    public static final int STBTT_MS_EID_UNICODE_BMP = 1;
    public static final int STBTT_MS_EID_SHIFTJIS = 2;
    public static final int STBTT_MS_EID_UNICODE_FULL = 10;
    public static final int STBTT_MAC_EID_ROMAN = 0;
    public static final int STBTT_MAC_EID_JAPANESE = 1;
    public static final int STBTT_MAC_EID_CHINESE_TRAD = 2;
    public static final int STBTT_MAC_EID_KOREAN = 3;
    public static final int STBTT_MAC_EID_ARABIC = 4;
    public static final int STBTT_MAC_EID_HEBREW = 5;
    public static final int STBTT_MAC_EID_GREEK = 6;
    public static final int STBTT_MAC_EID_RUSSIAN = 7;
    public static final int STBTT_MS_LANG_ENGLISH = 1033;
    public static final int STBTT_MS_LANG_CHINESE = 2052;
    public static final int STBTT_MS_LANG_DUTCH = 1043;
    public static final int STBTT_MS_LANG_FRENCH = 1036;
    public static final int STBTT_MS_LANG_GERMAN = 1031;
    public static final int STBTT_MS_LANG_HEBREW = 1037;
    public static final int STBTT_MS_LANG_ITALIAN = 1040;
    public static final int STBTT_MS_LANG_JAPANESE = 1041;
    public static final int STBTT_MS_LANG_KOREAN = 1042;
    public static final int STBTT_MS_LANG_RUSSIAN = 1049;
    public static final int STBTT_MS_LANG_SPANISH = 1033;
    public static final int STBTT_MS_LANG_SWEDISH = 1053;
    public static final int STBTT_MAC_LANG_ENGLISH = 0;
    public static final int STBTT_MAC_LANG_ARABIC = 12;
    public static final int STBTT_MAC_LANG_DUTCH = 4;
    public static final int STBTT_MAC_LANG_FRENCH = 1;
    public static final int STBTT_MAC_LANG_GERMAN = 2;
    public static final int STBTT_MAC_LANG_HEBREW = 10;
    public static final int STBTT_MAC_LANG_ITALIAN = 3;
    public static final int STBTT_MAC_LANG_JAPANESE = 11;
    public static final int STBTT_MAC_LANG_KOREAN = 23;
    public static final int STBTT_MAC_LANG_RUSSIAN = 32;
    public static final int STBTT_MAC_LANG_SPANISH = 6;
    public static final int STBTT_MAC_LANG_SWEDISH = 5;
    public static final int STBTT_MAC_LANG_CHINESE_SIMPLIFIED = 33;
    public static final int STBTT_MAC_LANG_CHINESE_TRAD = 19;

    protected STBTruetype() {
        throw new UnsupportedOperationException();
    }

    public static native int nstbtt_BakeFontBitmap(long var0, int var2, float var3, long var4, int var6, int var7, int var8, int var9, long var10);

    public static int stbtt_BakeFontBitmap(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, float f, @NativeType(value="unsigned char *") ByteBuffer byteBuffer2, int n, int n2, int n3, @NativeType(value="stbtt_bakedchar *") STBTTBakedChar.Buffer buffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer2, n * n2);
        }
        return STBTruetype.nstbtt_BakeFontBitmap(MemoryUtil.memAddress(byteBuffer), 0, f, MemoryUtil.memAddress(byteBuffer2), n, n2, n3, buffer.remaining(), buffer.address());
    }

    public static native void nstbtt_GetBakedQuad(long var0, int var2, int var3, int var4, long var5, long var7, long var9, int var11);

    public static void stbtt_GetBakedQuad(@NativeType(value="stbtt_bakedchar const *") STBTTBakedChar.Buffer buffer, int n, int n2, int n3, @NativeType(value="float *") FloatBuffer floatBuffer, @NativeType(value="float *") FloatBuffer floatBuffer2, @NativeType(value="stbtt_aligned_quad *") STBTTAlignedQuad sTBTTAlignedQuad, @NativeType(value="int") boolean bl) {
        if (Checks.CHECKS) {
            Checks.check(buffer, n3 + 1);
            Checks.check((Buffer)floatBuffer, 1);
            Checks.check((Buffer)floatBuffer2, 1);
        }
        STBTruetype.nstbtt_GetBakedQuad(buffer.address(), n, n2, n3, MemoryUtil.memAddress(floatBuffer), MemoryUtil.memAddress(floatBuffer2), sTBTTAlignedQuad.address(), bl ? 1 : 0);
    }

    public static native void nstbtt_GetScaledFontVMetrics(long var0, int var2, float var3, long var4, long var6, long var8);

    public static void stbtt_GetScaledFontVMetrics(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, int n, float f, @NativeType(value="float *") FloatBuffer floatBuffer, @NativeType(value="float *") FloatBuffer floatBuffer2, @NativeType(value="float *") FloatBuffer floatBuffer3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
            Checks.check((Buffer)floatBuffer2, 1);
            Checks.check((Buffer)floatBuffer3, 1);
        }
        STBTruetype.nstbtt_GetScaledFontVMetrics(MemoryUtil.memAddress(byteBuffer), n, f, MemoryUtil.memAddress(floatBuffer), MemoryUtil.memAddress(floatBuffer2), MemoryUtil.memAddress(floatBuffer3));
    }

    public static native int nstbtt_PackBegin(long var0, long var2, int var4, int var5, int var6, int var7, long var8);

    @NativeType(value="int")
    public static boolean stbtt_PackBegin(@NativeType(value="stbtt_pack_context *") STBTTPackContext sTBTTPackContext, @Nullable @NativeType(value="unsigned char *") ByteBuffer byteBuffer, int n, int n2, int n3, int n4, @NativeType(value="void *") long l) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)byteBuffer, (n3 != 0 ? n3 : n) * n2);
        }
        return STBTruetype.nstbtt_PackBegin(sTBTTPackContext.address(), MemoryUtil.memAddressSafe(byteBuffer), n, n2, n3, n4, l) != 0;
    }

    @NativeType(value="int")
    public static boolean stbtt_PackBegin(@NativeType(value="stbtt_pack_context *") STBTTPackContext sTBTTPackContext, @Nullable @NativeType(value="unsigned char *") ByteBuffer byteBuffer, int n, int n2, int n3, int n4) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)byteBuffer, (n3 != 0 ? n3 : n) * n2);
        }
        return STBTruetype.nstbtt_PackBegin(sTBTTPackContext.address(), MemoryUtil.memAddressSafe(byteBuffer), n, n2, n3, n4, 0L) != 0;
    }

    public static native void nstbtt_PackEnd(long var0);

    public static void stbtt_PackEnd(@NativeType(value="stbtt_pack_context *") STBTTPackContext sTBTTPackContext) {
        STBTruetype.nstbtt_PackEnd(sTBTTPackContext.address());
    }

    public static int STBTT_POINT_SIZE(int n) {
        return -n;
    }

    public static native int nstbtt_PackFontRange(long var0, long var2, int var4, float var5, int var6, int var7, long var8);

    @NativeType(value="int")
    public static boolean stbtt_PackFontRange(@NativeType(value="stbtt_pack_context *") STBTTPackContext sTBTTPackContext, @NativeType(value="unsigned char const *") ByteBuffer byteBuffer, int n, float f, int n2, @NativeType(value="stbtt_packedchar *") STBTTPackedchar.Buffer buffer) {
        return STBTruetype.nstbtt_PackFontRange(sTBTTPackContext.address(), MemoryUtil.memAddress(byteBuffer), n, f, n2, buffer.remaining(), buffer.address()) != 0;
    }

    public static native int nstbtt_PackFontRanges(long var0, long var2, int var4, long var5, int var7);

    @NativeType(value="int")
    public static boolean stbtt_PackFontRanges(@NativeType(value="stbtt_pack_context *") STBTTPackContext sTBTTPackContext, @NativeType(value="unsigned char const *") ByteBuffer byteBuffer, int n, @NativeType(value="stbtt_pack_range *") STBTTPackRange.Buffer buffer) {
        if (Checks.CHECKS) {
            STBTTPackRange.validate(buffer.address(), buffer.remaining());
        }
        return STBTruetype.nstbtt_PackFontRanges(sTBTTPackContext.address(), MemoryUtil.memAddress(byteBuffer), n, buffer.address(), buffer.remaining()) != 0;
    }

    public static native void nstbtt_PackSetOversampling(long var0, int var2, int var3);

    public static void stbtt_PackSetOversampling(@NativeType(value="stbtt_pack_context *") STBTTPackContext sTBTTPackContext, @NativeType(value="unsigned int") int n, @NativeType(value="unsigned int") int n2) {
        STBTruetype.nstbtt_PackSetOversampling(sTBTTPackContext.address(), n, n2);
    }

    public static native void nstbtt_PackSetSkipMissingCodepoints(long var0, int var2);

    public static void stbtt_PackSetSkipMissingCodepoints(@NativeType(value="stbtt_pack_context *") STBTTPackContext sTBTTPackContext, @NativeType(value="int") boolean bl) {
        STBTruetype.nstbtt_PackSetSkipMissingCodepoints(sTBTTPackContext.address(), bl ? 1 : 0);
    }

    public static native void nstbtt_GetPackedQuad(long var0, int var2, int var3, int var4, long var5, long var7, long var9, int var11);

    public static void stbtt_GetPackedQuad(@NativeType(value="stbtt_packedchar const *") STBTTPackedchar.Buffer buffer, int n, int n2, int n3, @NativeType(value="float *") FloatBuffer floatBuffer, @NativeType(value="float *") FloatBuffer floatBuffer2, @NativeType(value="stbtt_aligned_quad *") STBTTAlignedQuad sTBTTAlignedQuad, @NativeType(value="int") boolean bl) {
        if (Checks.CHECKS) {
            Checks.check(buffer, n3 + 1);
            Checks.check((Buffer)floatBuffer, 1);
            Checks.check((Buffer)floatBuffer2, 1);
        }
        STBTruetype.nstbtt_GetPackedQuad(buffer.address(), n, n2, n3, MemoryUtil.memAddress(floatBuffer), MemoryUtil.memAddress(floatBuffer2), sTBTTAlignedQuad.address(), bl ? 1 : 0);
    }

    public static native int nstbtt_PackFontRangesGatherRects(long var0, long var2, long var4, int var6, long var7);

    public static int stbtt_PackFontRangesGatherRects(@NativeType(value="stbtt_pack_context *") STBTTPackContext sTBTTPackContext, @NativeType(value="stbtt_fontinfo *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="stbtt_pack_range *") STBTTPackRange.Buffer buffer, @NativeType(value="stbrp_rect *") STBRPRect.Buffer buffer2) {
        if (Checks.CHECKS) {
            STBTTPackRange.validate(buffer.address(), buffer.remaining());
        }
        return STBTruetype.nstbtt_PackFontRangesGatherRects(sTBTTPackContext.address(), sTBTTFontinfo.address(), buffer.address(), buffer.remaining(), buffer2.address());
    }

    public static native void nstbtt_PackFontRangesPackRects(long var0, long var2, int var4);

    public static void stbtt_PackFontRangesPackRects(@NativeType(value="stbtt_pack_context *") STBTTPackContext sTBTTPackContext, @NativeType(value="stbrp_rect *") STBRPRect.Buffer buffer) {
        STBTruetype.nstbtt_PackFontRangesPackRects(sTBTTPackContext.address(), buffer.address(), buffer.remaining());
    }

    public static native int nstbtt_PackFontRangesRenderIntoRects(long var0, long var2, long var4, int var6, long var7);

    @NativeType(value="int")
    public static boolean stbtt_PackFontRangesRenderIntoRects(@NativeType(value="stbtt_pack_context *") STBTTPackContext sTBTTPackContext, @NativeType(value="stbtt_fontinfo *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="stbtt_pack_range *") STBTTPackRange.Buffer buffer, @NativeType(value="stbrp_rect *") STBRPRect.Buffer buffer2) {
        if (Checks.CHECKS) {
            STBTTPackRange.validate(buffer.address(), buffer.remaining());
        }
        return STBTruetype.nstbtt_PackFontRangesRenderIntoRects(sTBTTPackContext.address(), sTBTTFontinfo.address(), buffer.address(), buffer.remaining(), buffer2.address()) != 0;
    }

    public static native int nstbtt_GetNumberOfFonts(long var0);

    public static int stbtt_GetNumberOfFonts(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer) {
        return STBTruetype.nstbtt_GetNumberOfFonts(MemoryUtil.memAddress(byteBuffer));
    }

    public static native int nstbtt_GetFontOffsetForIndex(long var0, int var2);

    public static int stbtt_GetFontOffsetForIndex(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, int n) {
        return STBTruetype.nstbtt_GetFontOffsetForIndex(MemoryUtil.memAddress(byteBuffer), n);
    }

    public static native int nstbtt_InitFont(long var0, long var2, int var4);

    @NativeType(value="int")
    public static boolean stbtt_InitFont(@NativeType(value="stbtt_fontinfo *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="unsigned char const *") ByteBuffer byteBuffer, int n) {
        return STBTruetype.nstbtt_InitFont(sTBTTFontinfo.address(), MemoryUtil.memAddress(byteBuffer), n) != 0;
    }

    @NativeType(value="int")
    public static boolean stbtt_InitFont(@NativeType(value="stbtt_fontinfo *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="unsigned char const *") ByteBuffer byteBuffer) {
        return STBTruetype.nstbtt_InitFont(sTBTTFontinfo.address(), MemoryUtil.memAddress(byteBuffer), 0) != 0;
    }

    public static native int nstbtt_FindGlyphIndex(long var0, int var2);

    public static int stbtt_FindGlyphIndex(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n) {
        return STBTruetype.nstbtt_FindGlyphIndex(sTBTTFontinfo.address(), n);
    }

    public static native float nstbtt_ScaleForPixelHeight(long var0, float var2);

    public static float stbtt_ScaleForPixelHeight(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f) {
        return STBTruetype.nstbtt_ScaleForPixelHeight(sTBTTFontinfo.address(), f);
    }

    public static native float nstbtt_ScaleForMappingEmToPixels(long var0, float var2);

    public static float stbtt_ScaleForMappingEmToPixels(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f) {
        return STBTruetype.nstbtt_ScaleForMappingEmToPixels(sTBTTFontinfo.address(), f);
    }

    public static native void nstbtt_GetFontVMetrics(long var0, long var2, long var4, long var6);

    public static void stbtt_GetFontVMetrics(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.checkSafe((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
        }
        STBTruetype.nstbtt_GetFontVMetrics(sTBTTFontinfo.address(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3));
    }

    public static native int nstbtt_GetFontVMetricsOS2(long var0, long var2, long var4, long var6);

    @NativeType(value="int")
    public static boolean stbtt_GetFontVMetricsOS2(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.checkSafe((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
        }
        return STBTruetype.nstbtt_GetFontVMetricsOS2(sTBTTFontinfo.address(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3)) != 0;
    }

    public static native void nstbtt_GetFontBoundingBox(long var0, long var2, long var4, long var6, long var8);

    public static void stbtt_GetFontBoundingBox(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
            Checks.check((Buffer)intBuffer4, 1);
        }
        STBTruetype.nstbtt_GetFontBoundingBox(sTBTTFontinfo.address(), MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer4));
    }

    public static native void nstbtt_GetCodepointHMetrics(long var0, int var2, long var3, long var5);

    public static void stbtt_GetCodepointHMetrics(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.checkSafe((Buffer)intBuffer2, 1);
        }
        STBTruetype.nstbtt_GetCodepointHMetrics(sTBTTFontinfo.address(), n, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2));
    }

    public static native int nstbtt_GetCodepointKernAdvance(long var0, int var2, int var3);

    public static int stbtt_GetCodepointKernAdvance(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, int n2) {
        return STBTruetype.nstbtt_GetCodepointKernAdvance(sTBTTFontinfo.address(), n, n2);
    }

    public static native int nstbtt_GetCodepointBox(long var0, int var2, long var3, long var5, long var7, long var9);

    @NativeType(value="int")
    public static boolean stbtt_GetCodepointBox(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3, @Nullable @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.checkSafe((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
            Checks.checkSafe((Buffer)intBuffer4, 1);
        }
        return STBTruetype.nstbtt_GetCodepointBox(sTBTTFontinfo.address(), n, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4)) != 0;
    }

    public static native void nstbtt_GetGlyphHMetrics(long var0, int var2, long var3, long var5);

    public static void stbtt_GetGlyphHMetrics(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.checkSafe((Buffer)intBuffer2, 1);
        }
        STBTruetype.nstbtt_GetGlyphHMetrics(sTBTTFontinfo.address(), n, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2));
    }

    public static native int nstbtt_GetGlyphKernAdvance(long var0, int var2, int var3);

    public static int stbtt_GetGlyphKernAdvance(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, int n2) {
        return STBTruetype.nstbtt_GetGlyphKernAdvance(sTBTTFontinfo.address(), n, n2);
    }

    public static native int nstbtt_GetGlyphBox(long var0, int var2, long var3, long var5, long var7, long var9);

    @NativeType(value="int")
    public static boolean stbtt_GetGlyphBox(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3, @Nullable @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.checkSafe((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
            Checks.checkSafe((Buffer)intBuffer4, 1);
        }
        return STBTruetype.nstbtt_GetGlyphBox(sTBTTFontinfo.address(), n, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4)) != 0;
    }

    public static native int nstbtt_IsGlyphEmpty(long var0, int var2);

    @NativeType(value="int")
    public static boolean stbtt_IsGlyphEmpty(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n) {
        return STBTruetype.nstbtt_IsGlyphEmpty(sTBTTFontinfo.address(), n) != 0;
    }

    public static native int nstbtt_GetCodepointShape(long var0, int var2, long var3);

    public static int stbtt_GetCodepointShape(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, @NativeType(value="stbtt_vertex **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        return STBTruetype.nstbtt_GetCodepointShape(sTBTTFontinfo.address(), n, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="int")
    public static STBTTVertex.Buffer stbtt_GetCodepointShape(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.pointers(0L);
            int n3 = STBTruetype.nstbtt_GetCodepointShape(sTBTTFontinfo.address(), n, MemoryUtil.memAddress(pointerBuffer));
            STBTTVertex.Buffer buffer = STBTTVertex.createSafe(pointerBuffer.get(0), n3);
            return buffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native int nstbtt_GetGlyphShape(long var0, int var2, long var3);

    public static int stbtt_GetGlyphShape(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, @NativeType(value="stbtt_vertex **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        return STBTruetype.nstbtt_GetGlyphShape(sTBTTFontinfo.address(), n, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="int")
    public static STBTTVertex.Buffer stbtt_GetGlyphShape(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.pointers(0L);
            int n3 = STBTruetype.nstbtt_GetGlyphShape(sTBTTFontinfo.address(), n, MemoryUtil.memAddress(pointerBuffer));
            STBTTVertex.Buffer buffer = STBTTVertex.createSafe(pointerBuffer.get(0), n3);
            return buffer;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nstbtt_FreeShape(long var0, long var2);

    public static void stbtt_FreeShape(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="stbtt_vertex *") STBTTVertex.Buffer buffer) {
        if (Checks.CHECKS) {
            Checks.check(buffer, 1);
        }
        STBTruetype.nstbtt_FreeShape(sTBTTFontinfo.address(), buffer.address());
    }

    public static native void nstbtt_FreeBitmap(long var0, long var2);

    public static void stbtt_FreeBitmap(@NativeType(value="unsigned char *") ByteBuffer byteBuffer, @NativeType(value="void *") long l) {
        STBTruetype.nstbtt_FreeBitmap(MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void stbtt_FreeBitmap(@NativeType(value="unsigned char *") ByteBuffer byteBuffer) {
        STBTruetype.nstbtt_FreeBitmap(MemoryUtil.memAddress(byteBuffer), 0L);
    }

    public static native long nstbtt_GetCodepointBitmap(long var0, float var2, float var3, int var4, long var5, long var7, long var9, long var11);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetCodepointBitmap(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, float f2, int n, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3, @Nullable @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
            Checks.checkSafe((Buffer)intBuffer4, 1);
        }
        long l = STBTruetype.nstbtt_GetCodepointBitmap(sTBTTFontinfo.address(), f, f2, n, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4));
        return MemoryUtil.memByteBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()));
    }

    public static native long nstbtt_GetCodepointBitmapSubpixel(long var0, float var2, float var3, float var4, float var5, int var6, long var7, long var9, long var11, long var13);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetCodepointBitmapSubpixel(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, float f2, float f3, float f4, int n, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3, @Nullable @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
            Checks.checkSafe((Buffer)intBuffer4, 1);
        }
        long l = STBTruetype.nstbtt_GetCodepointBitmapSubpixel(sTBTTFontinfo.address(), f, f2, f3, f4, n, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4));
        return MemoryUtil.memByteBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()));
    }

    public static native void nstbtt_MakeCodepointBitmap(long var0, long var2, int var4, int var5, int var6, float var7, float var8, int var9);

    public static void stbtt_MakeCodepointBitmap(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="unsigned char *") ByteBuffer byteBuffer, int n, int n2, int n3, float f, float f2, int n4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, (n3 != 0 ? n3 : n) * n2);
        }
        STBTruetype.nstbtt_MakeCodepointBitmap(sTBTTFontinfo.address(), MemoryUtil.memAddress(byteBuffer), n, n2, n3, f, f2, n4);
    }

    public static native void nstbtt_MakeCodepointBitmapSubpixel(long var0, long var2, int var4, int var5, int var6, float var7, float var8, float var9, float var10, int var11);

    public static void stbtt_MakeCodepointBitmapSubpixel(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="unsigned char *") ByteBuffer byteBuffer, int n, int n2, int n3, float f, float f2, float f3, float f4, int n4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, (n3 != 0 ? n3 : n) * n2);
        }
        STBTruetype.nstbtt_MakeCodepointBitmapSubpixel(sTBTTFontinfo.address(), MemoryUtil.memAddress(byteBuffer), n, n2, n3, f, f2, f3, f4, n4);
    }

    public static native void nstbtt_MakeCodepointBitmapSubpixelPrefilter(long var0, long var2, int var4, int var5, int var6, float var7, float var8, float var9, float var10, int var11, int var12, long var13, long var15, int var17);

    public static void stbtt_MakeCodepointBitmapSubpixelPrefilter(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="unsigned char *") ByteBuffer byteBuffer, int n, int n2, int n3, float f, float f2, float f3, float f4, int n4, int n5, @NativeType(value="float *") FloatBuffer floatBuffer, @NativeType(value="float *") FloatBuffer floatBuffer2, int n6) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, (n3 != 0 ? n3 : n) * n2);
            Checks.check((Buffer)floatBuffer, 1);
            Checks.check((Buffer)floatBuffer2, 1);
        }
        STBTruetype.nstbtt_MakeCodepointBitmapSubpixelPrefilter(sTBTTFontinfo.address(), MemoryUtil.memAddress(byteBuffer), n, n2, n3, f, f2, f3, f4, n4, n5, MemoryUtil.memAddress(floatBuffer), MemoryUtil.memAddress(floatBuffer2), n6);
    }

    public static native void nstbtt_GetCodepointBitmapBox(long var0, int var2, float var3, float var4, long var5, long var7, long var9, long var11);

    public static void stbtt_GetCodepointBitmapBox(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, float f, float f2, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3, @Nullable @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.checkSafe((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
            Checks.checkSafe((Buffer)intBuffer4, 1);
        }
        STBTruetype.nstbtt_GetCodepointBitmapBox(sTBTTFontinfo.address(), n, f, f2, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4));
    }

    public static native void nstbtt_GetCodepointBitmapBoxSubpixel(long var0, int var2, float var3, float var4, float var5, float var6, long var7, long var9, long var11, long var13);

    public static void stbtt_GetCodepointBitmapBoxSubpixel(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, float f, float f2, float f3, float f4, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3, @Nullable @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.checkSafe((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
            Checks.checkSafe((Buffer)intBuffer4, 1);
        }
        STBTruetype.nstbtt_GetCodepointBitmapBoxSubpixel(sTBTTFontinfo.address(), n, f, f2, f3, f4, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4));
    }

    public static native long nstbtt_GetGlyphBitmap(long var0, float var2, float var3, int var4, long var5, long var7, long var9, long var11);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetGlyphBitmap(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, float f2, int n, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3, @Nullable @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
            Checks.checkSafe((Buffer)intBuffer4, 1);
        }
        long l = STBTruetype.nstbtt_GetGlyphBitmap(sTBTTFontinfo.address(), f, f2, n, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4));
        return MemoryUtil.memByteBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()));
    }

    public static native long nstbtt_GetGlyphBitmapSubpixel(long var0, float var2, float var3, float var4, float var5, int var6, long var7, long var9, long var11, long var13);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetGlyphBitmapSubpixel(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, float f2, float f3, float f4, int n, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3, @Nullable @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
            Checks.checkSafe((Buffer)intBuffer4, 1);
        }
        long l = STBTruetype.nstbtt_GetGlyphBitmapSubpixel(sTBTTFontinfo.address(), f, f2, f3, f4, n, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4));
        return MemoryUtil.memByteBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()));
    }

    public static native void nstbtt_MakeGlyphBitmap(long var0, long var2, int var4, int var5, int var6, float var7, float var8, int var9);

    public static void stbtt_MakeGlyphBitmap(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="unsigned char *") ByteBuffer byteBuffer, int n, int n2, int n3, float f, float f2, int n4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, (n3 != 0 ? n3 : n) * n2);
        }
        STBTruetype.nstbtt_MakeGlyphBitmap(sTBTTFontinfo.address(), MemoryUtil.memAddress(byteBuffer), n, n2, n3, f, f2, n4);
    }

    public static native void nstbtt_MakeGlyphBitmapSubpixel(long var0, long var2, int var4, int var5, int var6, float var7, float var8, float var9, float var10, int var11);

    public static void stbtt_MakeGlyphBitmapSubpixel(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="unsigned char *") ByteBuffer byteBuffer, int n, int n2, int n3, float f, float f2, float f3, float f4, int n4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, (n3 != 0 ? n3 : n) * n2);
        }
        STBTruetype.nstbtt_MakeGlyphBitmapSubpixel(sTBTTFontinfo.address(), MemoryUtil.memAddress(byteBuffer), n, n2, n3, f, f2, f3, f4, n4);
    }

    public static native void nstbtt_MakeGlyphBitmapSubpixelPrefilter(long var0, long var2, int var4, int var5, int var6, float var7, float var8, float var9, float var10, int var11, int var12, long var13, long var15, int var17);

    public static void stbtt_MakeGlyphBitmapSubpixelPrefilter(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="unsigned char *") ByteBuffer byteBuffer, int n, int n2, int n3, float f, float f2, float f3, float f4, int n4, int n5, @NativeType(value="float *") FloatBuffer floatBuffer, @NativeType(value="float *") FloatBuffer floatBuffer2, int n6) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, (n3 != 0 ? n3 : n) * n2);
            Checks.check((Buffer)floatBuffer, 1);
            Checks.check((Buffer)floatBuffer2, 1);
        }
        STBTruetype.nstbtt_MakeGlyphBitmapSubpixelPrefilter(sTBTTFontinfo.address(), MemoryUtil.memAddress(byteBuffer), n, n2, n3, f, f2, f3, f4, n4, n5, MemoryUtil.memAddress(floatBuffer), MemoryUtil.memAddress(floatBuffer2), n6);
    }

    public static native void nstbtt_GetGlyphBitmapBox(long var0, int var2, float var3, float var4, long var5, long var7, long var9, long var11);

    public static void stbtt_GetGlyphBitmapBox(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, float f, float f2, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3, @Nullable @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.checkSafe((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
            Checks.checkSafe((Buffer)intBuffer4, 1);
        }
        STBTruetype.nstbtt_GetGlyphBitmapBox(sTBTTFontinfo.address(), n, f, f2, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4));
    }

    public static native void nstbtt_GetGlyphBitmapBoxSubpixel(long var0, int var2, float var3, float var4, float var5, float var6, long var7, long var9, long var11, long var13);

    public static void stbtt_GetGlyphBitmapBoxSubpixel(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, float f, float f2, float f3, float f4, @Nullable @NativeType(value="int *") IntBuffer intBuffer, @Nullable @NativeType(value="int *") IntBuffer intBuffer2, @Nullable @NativeType(value="int *") IntBuffer intBuffer3, @Nullable @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.checkSafe((Buffer)intBuffer, 1);
            Checks.checkSafe((Buffer)intBuffer2, 1);
            Checks.checkSafe((Buffer)intBuffer3, 1);
            Checks.checkSafe((Buffer)intBuffer4, 1);
        }
        STBTruetype.nstbtt_GetGlyphBitmapBoxSubpixel(sTBTTFontinfo.address(), n, f, f2, f3, f4, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddressSafe(intBuffer2), MemoryUtil.memAddressSafe(intBuffer3), MemoryUtil.memAddressSafe(intBuffer4));
    }

    public static native void nstbtt_Rasterize(long var0, float var2, long var3, int var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12, long var13);

    public static void stbtt_Rasterize(@NativeType(value="stbtt__bitmap *") STBTTBitmap sTBTTBitmap, float f, @NativeType(value="stbtt_vertex *") STBTTVertex.Buffer buffer, float f2, float f3, float f4, float f5, int n, int n2, @NativeType(value="int") boolean bl) {
        STBTruetype.nstbtt_Rasterize(sTBTTBitmap.address(), f, buffer.address(), buffer.remaining(), f2, f3, f4, f5, n, n2, bl ? 1 : 0, 0L);
    }

    public static native void nstbtt_FreeSDF(long var0, long var2);

    public static void stbtt_FreeSDF(@NativeType(value="unsigned char *") ByteBuffer byteBuffer, @NativeType(value="void *") long l) {
        STBTruetype.nstbtt_FreeSDF(MemoryUtil.memAddress(byteBuffer), l);
    }

    public static void stbtt_FreeSDF(@NativeType(value="unsigned char *") ByteBuffer byteBuffer) {
        STBTruetype.nstbtt_FreeSDF(MemoryUtil.memAddress(byteBuffer), 0L);
    }

    public static native long nstbtt_GetGlyphSDF(long var0, float var2, int var3, int var4, byte var5, float var6, long var7, long var9, long var11, long var13);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetGlyphSDF(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, int n, int n2, @NativeType(value="unsigned char") byte by, float f2, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
            Checks.check((Buffer)intBuffer4, 1);
        }
        long l = STBTruetype.nstbtt_GetGlyphSDF(sTBTTFontinfo.address(), f, n, n2, by, f2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer4));
        return MemoryUtil.memByteBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()));
    }

    public static native long nstbtt_GetCodepointSDF(long var0, float var2, int var3, int var4, byte var5, float var6, long var7, long var9, long var11, long var13);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetCodepointSDF(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, int n, int n2, @NativeType(value="unsigned char") byte by, float f2, @NativeType(value="int *") IntBuffer intBuffer, @NativeType(value="int *") IntBuffer intBuffer2, @NativeType(value="int *") IntBuffer intBuffer3, @NativeType(value="int *") IntBuffer intBuffer4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
            Checks.check((Buffer)intBuffer2, 1);
            Checks.check((Buffer)intBuffer3, 1);
            Checks.check((Buffer)intBuffer4, 1);
        }
        long l = STBTruetype.nstbtt_GetCodepointSDF(sTBTTFontinfo.address(), f, n, n2, by, f2, MemoryUtil.memAddress(intBuffer), MemoryUtil.memAddress(intBuffer2), MemoryUtil.memAddress(intBuffer3), MemoryUtil.memAddress(intBuffer4));
        return MemoryUtil.memByteBufferSafe(l, intBuffer.get(intBuffer.position()) * intBuffer2.get(intBuffer2.position()));
    }

    public static native int nstbtt_FindMatchingFont(long var0, long var2, int var4);

    public static int stbtt_FindMatchingFont(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @NativeType(value="char const *") ByteBuffer byteBuffer2, int n) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer2);
        }
        return STBTruetype.nstbtt_FindMatchingFont(MemoryUtil.memAddress(byteBuffer), MemoryUtil.memAddress(byteBuffer2), n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int stbtt_FindMatchingFont(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, @NativeType(value="char const *") CharSequence charSequence, int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            memoryStack.nUTF8(charSequence, false);
            long l = memoryStack.getPointerAddress();
            int n3 = STBTruetype.nstbtt_FindMatchingFont(MemoryUtil.memAddress(byteBuffer), l, n);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native int nstbtt_CompareUTF8toUTF16_bigendian(long var0, int var2, long var3, int var5);

    @NativeType(value="int")
    public static boolean stbtt_CompareUTF8toUTF16_bigendian(@NativeType(value="char const *") ByteBuffer byteBuffer, @NativeType(value="char const *") ByteBuffer byteBuffer2) {
        return STBTruetype.nstbtt_CompareUTF8toUTF16_bigendian(MemoryUtil.memAddress(byteBuffer), byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer2), byteBuffer2.remaining()) != 0;
    }

    public static native long nstbtt_GetFontNameString(long var0, long var2, int var4, int var5, int var6, int var7);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    @NativeType(value="char const *")
    public static ByteBuffer stbtt_GetFontNameString(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, int n2, int n3, int n4) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n5 = memoryStack.getPointer();
        IntBuffer intBuffer = memoryStack.callocInt(1);
        try {
            long l = STBTruetype.nstbtt_GetFontNameString(sTBTTFontinfo.address(), MemoryUtil.memAddress(intBuffer), n, n2, n3, n4);
            ByteBuffer byteBuffer = MemoryUtil.memByteBufferSafe(l, intBuffer.get(0));
            return byteBuffer;
        } finally {
            memoryStack.setPointer(n5);
        }
    }

    public static native void nstbtt_GetBakedQuad(long var0, int var2, int var3, int var4, float[] var5, float[] var6, long var7, int var9);

    public static void stbtt_GetBakedQuad(@NativeType(value="stbtt_bakedchar const *") STBTTBakedChar.Buffer buffer, int n, int n2, int n3, @NativeType(value="float *") float[] fArray, @NativeType(value="float *") float[] fArray2, @NativeType(value="stbtt_aligned_quad *") STBTTAlignedQuad sTBTTAlignedQuad, @NativeType(value="int") boolean bl) {
        if (Checks.CHECKS) {
            Checks.check(buffer, n3 + 1);
            Checks.check(fArray, 1);
            Checks.check(fArray2, 1);
        }
        STBTruetype.nstbtt_GetBakedQuad(buffer.address(), n, n2, n3, fArray, fArray2, sTBTTAlignedQuad.address(), bl ? 1 : 0);
    }

    public static native void nstbtt_GetScaledFontVMetrics(long var0, int var2, float var3, float[] var4, float[] var5, float[] var6);

    public static void stbtt_GetScaledFontVMetrics(@NativeType(value="unsigned char const *") ByteBuffer byteBuffer, int n, float f, @NativeType(value="float *") float[] fArray, @NativeType(value="float *") float[] fArray2, @NativeType(value="float *") float[] fArray3) {
        if (Checks.CHECKS) {
            Checks.check(fArray, 1);
            Checks.check(fArray2, 1);
            Checks.check(fArray3, 1);
        }
        STBTruetype.nstbtt_GetScaledFontVMetrics(MemoryUtil.memAddress(byteBuffer), n, f, fArray, fArray2, fArray3);
    }

    public static native void nstbtt_GetPackedQuad(long var0, int var2, int var3, int var4, float[] var5, float[] var6, long var7, int var9);

    public static void stbtt_GetPackedQuad(@NativeType(value="stbtt_packedchar const *") STBTTPackedchar.Buffer buffer, int n, int n2, int n3, @NativeType(value="float *") float[] fArray, @NativeType(value="float *") float[] fArray2, @NativeType(value="stbtt_aligned_quad *") STBTTAlignedQuad sTBTTAlignedQuad, @NativeType(value="int") boolean bl) {
        if (Checks.CHECKS) {
            Checks.check(buffer, n3 + 1);
            Checks.check(fArray, 1);
            Checks.check(fArray2, 1);
        }
        STBTruetype.nstbtt_GetPackedQuad(buffer.address(), n, n2, n3, fArray, fArray2, sTBTTAlignedQuad.address(), bl ? 1 : 0);
    }

    public static native void nstbtt_GetFontVMetrics(long var0, int[] var2, int[] var3, int[] var4);

    public static void stbtt_GetFontVMetrics(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @Nullable @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3) {
        if (Checks.CHECKS) {
            Checks.checkSafe(nArray, 1);
            Checks.checkSafe(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
        }
        STBTruetype.nstbtt_GetFontVMetrics(sTBTTFontinfo.address(), nArray, nArray2, nArray3);
    }

    public static native int nstbtt_GetFontVMetricsOS2(long var0, int[] var2, int[] var3, int[] var4);

    @NativeType(value="int")
    public static boolean stbtt_GetFontVMetricsOS2(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @Nullable @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3) {
        if (Checks.CHECKS) {
            Checks.checkSafe(nArray, 1);
            Checks.checkSafe(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
        }
        return STBTruetype.nstbtt_GetFontVMetricsOS2(sTBTTFontinfo.address(), nArray, nArray2, nArray3) != 0;
    }

    public static native void nstbtt_GetFontBoundingBox(long var0, int[] var2, int[] var3, int[] var4, int[] var5);

    public static void stbtt_GetFontBoundingBox(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
            Checks.check(nArray4, 1);
        }
        STBTruetype.nstbtt_GetFontBoundingBox(sTBTTFontinfo.address(), nArray, nArray2, nArray3, nArray4);
    }

    public static native void nstbtt_GetCodepointHMetrics(long var0, int var2, int[] var3, int[] var4);

    public static void stbtt_GetCodepointHMetrics(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, @Nullable @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="int *") int[] nArray2) {
        if (Checks.CHECKS) {
            Checks.checkSafe(nArray, 1);
            Checks.checkSafe(nArray2, 1);
        }
        STBTruetype.nstbtt_GetCodepointHMetrics(sTBTTFontinfo.address(), n, nArray, nArray2);
    }

    public static native int nstbtt_GetCodepointBox(long var0, int var2, int[] var3, int[] var4, int[] var5, int[] var6);

    @NativeType(value="int")
    public static boolean stbtt_GetCodepointBox(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, @Nullable @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3, @Nullable @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.checkSafe(nArray, 1);
            Checks.checkSafe(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
            Checks.checkSafe(nArray4, 1);
        }
        return STBTruetype.nstbtt_GetCodepointBox(sTBTTFontinfo.address(), n, nArray, nArray2, nArray3, nArray4) != 0;
    }

    public static native void nstbtt_GetGlyphHMetrics(long var0, int var2, int[] var3, int[] var4);

    public static void stbtt_GetGlyphHMetrics(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, @Nullable @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="int *") int[] nArray2) {
        if (Checks.CHECKS) {
            Checks.checkSafe(nArray, 1);
            Checks.checkSafe(nArray2, 1);
        }
        STBTruetype.nstbtt_GetGlyphHMetrics(sTBTTFontinfo.address(), n, nArray, nArray2);
    }

    public static native int nstbtt_GetGlyphBox(long var0, int var2, int[] var3, int[] var4, int[] var5, int[] var6);

    @NativeType(value="int")
    public static boolean stbtt_GetGlyphBox(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, @Nullable @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3, @Nullable @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.checkSafe(nArray, 1);
            Checks.checkSafe(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
            Checks.checkSafe(nArray4, 1);
        }
        return STBTruetype.nstbtt_GetGlyphBox(sTBTTFontinfo.address(), n, nArray, nArray2, nArray3, nArray4) != 0;
    }

    public static native long nstbtt_GetCodepointBitmap(long var0, float var2, float var3, int var4, int[] var5, int[] var6, int[] var7, int[] var8);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetCodepointBitmap(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, float f2, int n, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3, @Nullable @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
            Checks.checkSafe(nArray4, 1);
        }
        long l = STBTruetype.nstbtt_GetCodepointBitmap(sTBTTFontinfo.address(), f, f2, n, nArray, nArray2, nArray3, nArray4);
        return MemoryUtil.memByteBufferSafe(l, nArray[0] * nArray2[0]);
    }

    public static native long nstbtt_GetCodepointBitmapSubpixel(long var0, float var2, float var3, float var4, float var5, int var6, int[] var7, int[] var8, int[] var9, int[] var10);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetCodepointBitmapSubpixel(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, float f2, float f3, float f4, int n, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3, @Nullable @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
            Checks.checkSafe(nArray4, 1);
        }
        long l = STBTruetype.nstbtt_GetCodepointBitmapSubpixel(sTBTTFontinfo.address(), f, f2, f3, f4, n, nArray, nArray2, nArray3, nArray4);
        return MemoryUtil.memByteBufferSafe(l, nArray[0] * nArray2[0]);
    }

    public static native void nstbtt_MakeCodepointBitmapSubpixelPrefilter(long var0, long var2, int var4, int var5, int var6, float var7, float var8, float var9, float var10, int var11, int var12, float[] var13, float[] var14, int var15);

    public static void stbtt_MakeCodepointBitmapSubpixelPrefilter(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="unsigned char *") ByteBuffer byteBuffer, int n, int n2, int n3, float f, float f2, float f3, float f4, int n4, int n5, @NativeType(value="float *") float[] fArray, @NativeType(value="float *") float[] fArray2, int n6) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, (n3 != 0 ? n3 : n) * n2);
            Checks.check(fArray, 1);
            Checks.check(fArray2, 1);
        }
        STBTruetype.nstbtt_MakeCodepointBitmapSubpixelPrefilter(sTBTTFontinfo.address(), MemoryUtil.memAddress(byteBuffer), n, n2, n3, f, f2, f3, f4, n4, n5, fArray, fArray2, n6);
    }

    public static native void nstbtt_GetCodepointBitmapBox(long var0, int var2, float var3, float var4, int[] var5, int[] var6, int[] var7, int[] var8);

    public static void stbtt_GetCodepointBitmapBox(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, float f, float f2, @Nullable @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3, @Nullable @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.checkSafe(nArray, 1);
            Checks.checkSafe(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
            Checks.checkSafe(nArray4, 1);
        }
        STBTruetype.nstbtt_GetCodepointBitmapBox(sTBTTFontinfo.address(), n, f, f2, nArray, nArray2, nArray3, nArray4);
    }

    public static native void nstbtt_GetCodepointBitmapBoxSubpixel(long var0, int var2, float var3, float var4, float var5, float var6, int[] var7, int[] var8, int[] var9, int[] var10);

    public static void stbtt_GetCodepointBitmapBoxSubpixel(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, float f, float f2, float f3, float f4, @Nullable @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3, @Nullable @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.checkSafe(nArray, 1);
            Checks.checkSafe(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
            Checks.checkSafe(nArray4, 1);
        }
        STBTruetype.nstbtt_GetCodepointBitmapBoxSubpixel(sTBTTFontinfo.address(), n, f, f2, f3, f4, nArray, nArray2, nArray3, nArray4);
    }

    public static native long nstbtt_GetGlyphBitmap(long var0, float var2, float var3, int var4, int[] var5, int[] var6, int[] var7, int[] var8);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetGlyphBitmap(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, float f2, int n, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3, @Nullable @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
            Checks.checkSafe(nArray4, 1);
        }
        long l = STBTruetype.nstbtt_GetGlyphBitmap(sTBTTFontinfo.address(), f, f2, n, nArray, nArray2, nArray3, nArray4);
        return MemoryUtil.memByteBufferSafe(l, nArray[0] * nArray2[0]);
    }

    public static native long nstbtt_GetGlyphBitmapSubpixel(long var0, float var2, float var3, float var4, float var5, int var6, int[] var7, int[] var8, int[] var9, int[] var10);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetGlyphBitmapSubpixel(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, float f2, float f3, float f4, int n, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3, @Nullable @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
            Checks.checkSafe(nArray4, 1);
        }
        long l = STBTruetype.nstbtt_GetGlyphBitmapSubpixel(sTBTTFontinfo.address(), f, f2, f3, f4, n, nArray, nArray2, nArray3, nArray4);
        return MemoryUtil.memByteBufferSafe(l, nArray[0] * nArray2[0]);
    }

    public static native void nstbtt_MakeGlyphBitmapSubpixelPrefilter(long var0, long var2, int var4, int var5, int var6, float var7, float var8, float var9, float var10, int var11, int var12, float[] var13, float[] var14, int var15);

    public static void stbtt_MakeGlyphBitmapSubpixelPrefilter(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, @NativeType(value="unsigned char *") ByteBuffer byteBuffer, int n, int n2, int n3, float f, float f2, float f3, float f4, int n4, int n5, @NativeType(value="float *") float[] fArray, @NativeType(value="float *") float[] fArray2, int n6) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, (n3 != 0 ? n3 : n) * n2);
            Checks.check(fArray, 1);
            Checks.check(fArray2, 1);
        }
        STBTruetype.nstbtt_MakeGlyphBitmapSubpixelPrefilter(sTBTTFontinfo.address(), MemoryUtil.memAddress(byteBuffer), n, n2, n3, f, f2, f3, f4, n4, n5, fArray, fArray2, n6);
    }

    public static native void nstbtt_GetGlyphBitmapBox(long var0, int var2, float var3, float var4, int[] var5, int[] var6, int[] var7, int[] var8);

    public static void stbtt_GetGlyphBitmapBox(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, float f, float f2, @Nullable @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3, @Nullable @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.checkSafe(nArray, 1);
            Checks.checkSafe(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
            Checks.checkSafe(nArray4, 1);
        }
        STBTruetype.nstbtt_GetGlyphBitmapBox(sTBTTFontinfo.address(), n, f, f2, nArray, nArray2, nArray3, nArray4);
    }

    public static native void nstbtt_GetGlyphBitmapBoxSubpixel(long var0, int var2, float var3, float var4, float var5, float var6, int[] var7, int[] var8, int[] var9, int[] var10);

    public static void stbtt_GetGlyphBitmapBoxSubpixel(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, int n, float f, float f2, float f3, float f4, @Nullable @NativeType(value="int *") int[] nArray, @Nullable @NativeType(value="int *") int[] nArray2, @Nullable @NativeType(value="int *") int[] nArray3, @Nullable @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.checkSafe(nArray, 1);
            Checks.checkSafe(nArray2, 1);
            Checks.checkSafe(nArray3, 1);
            Checks.checkSafe(nArray4, 1);
        }
        STBTruetype.nstbtt_GetGlyphBitmapBoxSubpixel(sTBTTFontinfo.address(), n, f, f2, f3, f4, nArray, nArray2, nArray3, nArray4);
    }

    public static native long nstbtt_GetGlyphSDF(long var0, float var2, int var3, int var4, byte var5, float var6, int[] var7, int[] var8, int[] var9, int[] var10);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetGlyphSDF(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, int n, int n2, @NativeType(value="unsigned char") byte by, float f2, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
            Checks.check(nArray4, 1);
        }
        long l = STBTruetype.nstbtt_GetGlyphSDF(sTBTTFontinfo.address(), f, n, n2, by, f2, nArray, nArray2, nArray3, nArray4);
        return MemoryUtil.memByteBufferSafe(l, nArray[0] * nArray2[0]);
    }

    public static native long nstbtt_GetCodepointSDF(long var0, float var2, int var3, int var4, byte var5, float var6, int[] var7, int[] var8, int[] var9, int[] var10);

    @Nullable
    @NativeType(value="unsigned char *")
    public static ByteBuffer stbtt_GetCodepointSDF(@NativeType(value="stbtt_fontinfo const *") STBTTFontinfo sTBTTFontinfo, float f, int n, int n2, @NativeType(value="unsigned char") byte by, float f2, @NativeType(value="int *") int[] nArray, @NativeType(value="int *") int[] nArray2, @NativeType(value="int *") int[] nArray3, @NativeType(value="int *") int[] nArray4) {
        if (Checks.CHECKS) {
            Checks.check(nArray, 1);
            Checks.check(nArray2, 1);
            Checks.check(nArray3, 1);
            Checks.check(nArray4, 1);
        }
        long l = STBTruetype.nstbtt_GetCodepointSDF(sTBTTFontinfo.address(), f, n, n2, by, f2, nArray, nArray2, nArray3, nArray4);
        return MemoryUtil.memByteBufferSafe(l, nArray[0] * nArray2[0]);
    }

    static {
        LibSTB.initialize();
    }
}

