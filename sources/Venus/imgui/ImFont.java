/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImDrawList;
import imgui.ImFontGlyph;
import imgui.ImVec2;
import imgui.binding.ImGuiStructDestroyable;

public final class ImFont
extends ImGuiStructDestroyable {
    private final ImFontGlyph fallbackGlyph = new ImFontGlyph(0L);
    private final ImFontGlyph foundGlyph = new ImFontGlyph(0L);

    public ImFont() {
    }

    public ImFont(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public native float getFallbackAdvanceX();

    public native void setFallbackAdvanceX(float var1);

    public native float getFontSize();

    public ImFontGlyph getFallbackGlyph() {
        this.fallbackGlyph.ptr = this.nGetFallbackGlyphPtr();
        return this.fallbackGlyph;
    }

    private native long nGetFallbackGlyphPtr();

    public native short getConfigDataCount();

    public native short getFallbackChar();

    public native short getEllipsisChar();

    public native void setEllipsisChar(int var1);

    public native short getDotChar();

    public native void setDotChar(int var1);

    public native boolean getDirtyLookupTables();

    public native void setDirtyLookupTables(boolean var1);

    public native float getScale();

    public native void setScale(float var1);

    public native float getAscent();

    public native void setAscent(float var1);

    public native float getDescent();

    public native void setDescent(float var1);

    public native int getMetricsTotalSurface();

    public native void setMetricsTotalSurface(int var1);

    public ImFontGlyph findGlyph(int n) {
        this.foundGlyph.ptr = this.nFindGlyph(n);
        return this.foundGlyph;
    }

    private native long nFindGlyph(int var1);

    public ImFontGlyph findGlyphNoFallback(int n) {
        this.foundGlyph.ptr = this.nFindGlyphNoFallback(n);
        return this.foundGlyph;
    }

    private native long nFindGlyphNoFallback(int var1);

    public native float getCharAdvance(int var1);

    public native boolean isLoaded();

    public native String getDebugName();

    public ImVec2 calcTextSizeA(float f, float f2, float f3, String string) {
        ImVec2 imVec2 = new ImVec2();
        this.calcTextSizeA(imVec2, f, f2, f3, string);
        return imVec2;
    }

    public native void calcTextSizeA(ImVec2 var1, float var2, float var3, float var4, String var5);

    public native float calcTextSizeAX(float var1, float var2, float var3, String var4);

    public native float calcTextSizeAY(float var1, float var2, float var3, String var4);

    public native String calcWordWrapPositionA(float var1, String var2, String var3, float var4);

    public void renderChar(ImDrawList imDrawList, float f, float f2, float f3, int n, int n2) {
        this.nRenderChar(imDrawList.ptr, f, f2, f3, n, n2);
    }

    private native void nRenderChar(long var1, float var3, float var4, float var5, int var6, int var7);

    public void renderText(ImDrawList imDrawList, float f, float f2, float f3, int n, float f4, float f5, float f6, float f7, String string, String string2) {
        this.nRenderText(imDrawList.ptr, f, f2, f3, n, f4, f5, f6, f7, string, string2, 0.0f, false);
    }

    public void renderText(ImDrawList imDrawList, float f, float f2, float f3, int n, float f4, float f5, float f6, float f7, String string, String string2, float f8) {
        this.nRenderText(imDrawList.ptr, f, f2, f3, n, f4, f5, f6, f7, string, string2, f8, false);
    }

    public void renderText(ImDrawList imDrawList, float f, float f2, float f3, int n, float f4, float f5, float f6, float f7, String string, String string2, float f8, boolean bl) {
        this.nRenderText(imDrawList.ptr, f, f2, f3, n, f4, f5, f6, f7, string, string2, f8, bl);
    }

    private native void nRenderText(long var1, float var3, float var4, float var5, int var6, float var7, float var8, float var9, float var10, String var11, String var12, float var13, boolean var14);
}

