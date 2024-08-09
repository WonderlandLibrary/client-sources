/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImFont;
import imgui.ImFontConfig;
import imgui.binding.ImGuiStructDestroyable;
import imgui.type.ImInt;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class ImFontAtlas
extends ImGuiStructDestroyable {
    private ByteBuffer alpha8pixels = null;
    private ByteBuffer rgba32pixels = null;

    public ImFontAtlas() {
    }

    public ImFontAtlas(long l) {
        super(l);
    }

    static native void nInit();

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public ImFont addFont(ImFontConfig imFontConfig) {
        return new ImFont(this.nAddFont(imFontConfig.ptr));
    }

    private native long nAddFont(long var1);

    public ImFont addFontDefault() {
        return new ImFont(this.nAddFontDefault());
    }

    private native long nAddFontDefault();

    public ImFont addFontDefault(ImFontConfig imFontConfig) {
        return new ImFont(this.nAddFontDefault(imFontConfig.ptr));
    }

    private native long nAddFontDefault(long var1);

    public ImFont addFontFromFileTTF(String string, float f) {
        return new ImFont(this.nAddFontFromFileTTF(string, f));
    }

    private native long nAddFontFromFileTTF(String var1, float var2);

    public ImFont addFontFromFileTTF(String string, float f, ImFontConfig imFontConfig) {
        return new ImFont(this.nAddFontFromFileTTF(string, f, imFontConfig.ptr));
    }

    private native long nAddFontFromFileTTF(String var1, float var2, long var3);

    public ImFont addFontFromFileTTF(String string, float f, short[] sArray) {
        return new ImFont(this.nAddFontFromFileTTF(string, f, sArray));
    }

    private native long nAddFontFromFileTTF(String var1, float var2, short[] var3);

    public ImFont addFontFromFileTTF(String string, float f, ImFontConfig imFontConfig, short[] sArray) {
        return new ImFont(this.nAddFontFromFileTTF(string, f, imFontConfig.ptr, sArray));
    }

    private native long nAddFontFromFileTTF(String var1, float var2, long var3, short[] var5);

    public ImFont addFontFromMemoryTTF(byte[] byArray, float f) {
        return new ImFont(this.nAddFontFromMemoryTTF(byArray, byArray.length, f));
    }

    private native long nAddFontFromMemoryTTF(byte[] var1, int var2, float var3);

    public ImFont addFontFromMemoryTTF(byte[] byArray, float f, ImFontConfig imFontConfig) {
        return new ImFont(this.nAddFontFromMemoryTTF(byArray, byArray.length, f, imFontConfig.ptr));
    }

    private native long nAddFontFromMemoryTTF(byte[] var1, int var2, float var3, long var4);

    public ImFont addFontFromMemoryTTF(byte[] byArray, float f, short[] sArray) {
        return new ImFont(this.nAddFontFromMemoryTTF(byArray, byArray.length, f, sArray));
    }

    private native long nAddFontFromMemoryTTF(byte[] var1, int var2, float var3, short[] var4);

    public ImFont addFontFromMemoryTTF(byte[] byArray, float f, ImFontConfig imFontConfig, short[] sArray) {
        return new ImFont(this.nAddFontFromMemoryTTF(byArray, byArray.length, f, imFontConfig.ptr, sArray));
    }

    private native long nAddFontFromMemoryTTF(byte[] var1, int var2, float var3, long var4, short[] var6);

    public ImFont addFontFromMemoryCompressedTTF(byte[] byArray, float f) {
        return new ImFont(this.nAddFontFromMemoryCompressedTTF(byArray, byArray.length, f));
    }

    private native long nAddFontFromMemoryCompressedTTF(byte[] var1, int var2, float var3);

    public ImFont addFontFromMemoryCompressedTTF(byte[] byArray, float f, ImFontConfig imFontConfig) {
        return new ImFont(this.nAddFontFromMemoryCompressedTTF(byArray, byArray.length, f, imFontConfig.ptr));
    }

    private native long nAddFontFromMemoryCompressedTTF(byte[] var1, int var2, float var3, long var4);

    public ImFont addFontFromMemoryCompressedTTF(byte[] byArray, float f, short[] sArray) {
        return new ImFont(this.nAddFontFromMemoryCompressedTTF(byArray, byArray.length, f, sArray));
    }

    private native long nAddFontFromMemoryCompressedTTF(byte[] var1, int var2, float var3, short[] var4);

    public ImFont addFontFromMemoryCompressedTTF(byte[] byArray, float f, ImFontConfig imFontConfig, short[] sArray) {
        return new ImFont(this.nAddFontFromMemoryCompressedTTF(byArray, byArray.length, f, imFontConfig.ptr, sArray));
    }

    private native long nAddFontFromMemoryCompressedTTF(byte[] var1, int var2, float var3, long var4, short[] var6);

    public ImFont addFontFromMemoryCompressedBase85TTF(String string, float f) {
        return new ImFont(this.nAddFontFromMemoryCompressedBase85TTF(string, f));
    }

    private native long nAddFontFromMemoryCompressedBase85TTF(String var1, float var2);

    public ImFont addFontFromMemoryCompressedBase85TTF(String string, float f, ImFontConfig imFontConfig) {
        return new ImFont(this.nAddFontFromMemoryCompressedBase85TTF(string, f, imFontConfig.ptr));
    }

    private native long nAddFontFromMemoryCompressedBase85TTF(String var1, float var2, long var3);

    public ImFont addFontFromMemoryCompressedBase85TTF(String string, float f, short[] sArray) {
        return new ImFont(this.nAddFontFromMemoryCompressedBase85TTF(string, f, sArray));
    }

    private native long nAddFontFromMemoryCompressedBase85TTF(String var1, float var2, short[] var3);

    public ImFont addFontFromMemoryCompressedBase85TTF(String string, float f, ImFontConfig imFontConfig, short[] sArray) {
        return new ImFont(this.nAddFontFromMemoryCompressedBase85TTF(string, f, imFontConfig.ptr, sArray));
    }

    private native long nAddFontFromMemoryCompressedBase85TTF(String var1, float var2, long var3, short[] var5);

    public native void clearInputData();

    public native void clearTexData();

    public native void clearFonts();

    public native void clear();

    public native boolean build();

    public ByteBuffer getTexDataAsAlpha8(ImInt imInt, ImInt imInt2) {
        return this.getTexDataAsAlpha8(imInt, imInt2, new ImInt());
    }

    public ByteBuffer getTexDataAsAlpha8(ImInt imInt, ImInt imInt2, ImInt imInt3) {
        this.getTexDataAsAlpha8(imInt.getData(), imInt2.getData(), imInt3.getData());
        return this.alpha8pixels;
    }

    private ByteBuffer createAlpha8Pixels(int n) {
        if (this.alpha8pixels == null || this.alpha8pixels.limit() != n) {
            this.alpha8pixels = ByteBuffer.allocateDirect(n).order(ByteOrder.nativeOrder());
        } else {
            this.alpha8pixels.clear();
        }
        return this.alpha8pixels;
    }

    private native void getTexDataAsAlpha8(int[] var1, int[] var2, int[] var3);

    public ByteBuffer getTexDataAsRGBA32(ImInt imInt, ImInt imInt2) {
        return this.getTexDataAsRGBA32(imInt, imInt2, new ImInt());
    }

    public ByteBuffer getTexDataAsRGBA32(ImInt imInt, ImInt imInt2, ImInt imInt3) {
        this.nGetTexDataAsRGBA32(imInt.getData(), imInt2.getData(), imInt3.getData());
        return this.rgba32pixels;
    }

    private ByteBuffer createRgba32Pixels(int n) {
        if (this.rgba32pixels == null || this.rgba32pixels.limit() != n) {
            this.rgba32pixels = ByteBuffer.allocateDirect(n).order(ByteOrder.nativeOrder());
        } else {
            this.rgba32pixels.clear();
        }
        return this.rgba32pixels;
    }

    private native void nGetTexDataAsRGBA32(int[] var1, int[] var2, int[] var3);

    public native boolean isBuilt();

    public native void setTexID(int var1);

    public native short[] getGlyphRangesDefault();

    public native short[] getGlyphRangesKorean();

    public native short[] getGlyphRangesJapanese();

    public native short[] getGlyphRangesChineseFull();

    public native short[] getGlyphRangesChineseSimplifiedCommon();

    public native short[] getGlyphRangesCyrillic();

    public native short[] getGlyphRangesThai();

    public native short[] getGlyphRangesVietnamese();

    public native int addCustomRectRegular(int var1, int var2);

    public int addCustomRectFontGlyph(ImFont imFont, short s, int n, int n2, float f) {
        return this.nAddCustomRectFontGlyph(imFont.ptr, s, n, n2, f);
    }

    private native int nAddCustomRectFontGlyph(long var1, short var3, int var4, int var5, float var6);

    public int addCustomRectFontGlyph(ImFont imFont, short s, int n, int n2, float f, float f2, float f3) {
        return this.nAddCustomRectFontGlyph(imFont.ptr, s, n, n2, f, f2, f3);
    }

    private native int nAddCustomRectFontGlyph(long var1, short var3, int var4, int var5, float var6, float var7, float var8);

    public native boolean getLocked();

    public native void setLocked(boolean var1);

    public native int getFlags();

    public native void setFlags(int var1);

    public void addFlags(int n) {
        this.setFlags(this.getFlags() | n);
    }

    public void removeFlags(int n) {
        this.setFlags(this.getFlags() & ~n);
    }

    public boolean hasFlags(int n) {
        return (this.getFlags() & n) != 0;
    }

    public native int getTexID();

    public native int getTexDesiredWidth();

    public native void setTexDesiredWidth(int var1);

    public native int getTexGlyphPadding();

    public native void setTexGlyphPadding(int var1);
}

