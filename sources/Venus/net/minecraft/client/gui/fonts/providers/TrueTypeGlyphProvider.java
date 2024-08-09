/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts.providers;

import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.renderer.texture.NativeImage;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TrueTypeGlyphProvider
implements IGlyphProvider {
    private final ByteBuffer field_230146_a_;
    private final STBTTFontinfo fontInfo;
    private final float oversample;
    private final IntSet chars = new IntArraySet();
    private final float shiftX;
    private final float shiftY;
    private final float scale;
    private final float ascent;

    public TrueTypeGlyphProvider(ByteBuffer byteBuffer, STBTTFontinfo sTBTTFontinfo, float f, float f2, float f3, float f4, String string) {
        this.field_230146_a_ = byteBuffer;
        this.fontInfo = sTBTTFontinfo;
        this.oversample = f2;
        string.codePoints().forEach(this.chars::add);
        this.shiftX = f3 * f2;
        this.shiftY = f4 * f2;
        this.scale = STBTruetype.stbtt_ScaleForPixelHeight(sTBTTFontinfo, f * f2);
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            IntBuffer intBuffer2 = memoryStack.mallocInt(1);
            IntBuffer intBuffer3 = memoryStack.mallocInt(1);
            STBTruetype.stbtt_GetFontVMetrics(sTBTTFontinfo, intBuffer, intBuffer2, intBuffer3);
            this.ascent = (float)intBuffer.get(0) * this.scale;
        }
    }

    @Override
    @Nullable
    public GlpyhInfo getGlyphInfo(int n) {
        Object var2_16;
        if (this.chars.contains(n)) {
            return null;
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            IntBuffer intBuffer2 = memoryStack.mallocInt(1);
            IntBuffer intBuffer3 = memoryStack.mallocInt(1);
            IntBuffer intBuffer4 = memoryStack.mallocInt(1);
            int n2 = STBTruetype.stbtt_FindGlyphIndex(this.fontInfo, n);
            if (n2 != 0) {
                STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel(this.fontInfo, n2, this.scale, this.scale, this.shiftX, this.shiftY, intBuffer, intBuffer2, intBuffer3, intBuffer4);
                int n3 = intBuffer3.get(0) - intBuffer.get(0);
                int n4 = intBuffer4.get(0) - intBuffer2.get(0);
                if (n3 != 0 && n4 != 0) {
                    IntBuffer intBuffer5 = memoryStack.mallocInt(1);
                    IntBuffer intBuffer6 = memoryStack.mallocInt(1);
                    STBTruetype.stbtt_GetGlyphHMetrics(this.fontInfo, n2, intBuffer5, intBuffer6);
                    GlpyhInfo glpyhInfo = new GlpyhInfo(this, intBuffer.get(0), intBuffer3.get(0), -intBuffer2.get(0), -intBuffer4.get(0), (float)intBuffer5.get(0) * this.scale, (float)intBuffer6.get(0) * this.scale, n2);
                    return glpyhInfo;
                }
                GlpyhInfo glpyhInfo = null;
                return glpyhInfo;
            }
            var2_16 = null;
        }
        return var2_16;
    }

    @Override
    public void close() {
        this.fontInfo.free();
        MemoryUtil.memFree(this.field_230146_a_);
    }

    @Override
    public IntSet func_230428_a_() {
        return IntStream.range(0, 65535).filter(this::lambda$func_230428_a_$0).collect(IntOpenHashSet::new, IntCollection::add, IntCollection::addAll);
    }

    @Override
    @Nullable
    public IGlyphInfo getGlyphInfo(int n) {
        return this.getGlyphInfo(n);
    }

    private boolean lambda$func_230428_a_$0(int n) {
        return !this.chars.contains(n);
    }

    class GlpyhInfo
    implements IGlyphInfo {
        private final int width;
        private final int height;
        private final float field_212464_d;
        private final float field_212465_e;
        private final float advanceWidth;
        private final int glyphIndex;
        final TrueTypeGlyphProvider this$0;

        private GlpyhInfo(TrueTypeGlyphProvider trueTypeGlyphProvider, int n, int n2, int n3, int n4, float f, float f2, int n5) {
            this.this$0 = trueTypeGlyphProvider;
            this.width = n2 - n;
            this.height = n3 - n4;
            this.advanceWidth = f / trueTypeGlyphProvider.oversample;
            this.field_212464_d = (f2 + (float)n + trueTypeGlyphProvider.shiftX) / trueTypeGlyphProvider.oversample;
            this.field_212465_e = (trueTypeGlyphProvider.ascent - (float)n3 + trueTypeGlyphProvider.shiftY) / trueTypeGlyphProvider.oversample;
            this.glyphIndex = n5;
        }

        @Override
        public int getWidth() {
            return this.width;
        }

        @Override
        public int getHeight() {
            return this.height;
        }

        @Override
        public float getOversample() {
            return this.this$0.oversample;
        }

        @Override
        public float getAdvance() {
            return this.advanceWidth;
        }

        @Override
        public float getBearingX() {
            return this.field_212464_d;
        }

        @Override
        public float getBearingY() {
            return this.field_212465_e;
        }

        @Override
        public void uploadGlyph(int n, int n2) {
            NativeImage nativeImage = new NativeImage(NativeImage.PixelFormat.LUMINANCE, this.width, this.height, false);
            nativeImage.renderGlyph(this.this$0.fontInfo, this.glyphIndex, this.width, this.height, this.this$0.scale, this.this$0.scale, this.this$0.shiftX, this.this$0.shiftY, 0, 0);
            nativeImage.uploadTextureSub(0, n, n2, 0, 0, this.width, this.height, false, false);
        }

        @Override
        public boolean isColored() {
            return true;
        }
    }
}

