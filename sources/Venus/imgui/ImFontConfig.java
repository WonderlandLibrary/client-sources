/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImVec2;
import imgui.binding.ImGuiStructDestroyable;

public final class ImFontConfig
extends ImGuiStructDestroyable {
    private short[] glyphRanges;

    public ImFontConfig() {
        this.setFontDataOwnedByAtlas(true);
    }

    ImFontConfig(long l) {
        super(l);
        this.setFontDataOwnedByAtlas(true);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public native byte[] getFontData();

    public native void setFontData(byte[] var1);

    public native int getFontDataSize();

    public native void setFontDataSize(int var1);

    public native boolean getFontDataOwnedByAtlas();

    public native void setFontDataOwnedByAtlas(boolean var1);

    public native int getFontNo();

    public native void setFontNo(int var1);

    public native float getSizePixels();

    public native void setSizePixels(float var1);

    public native int getOversampleH();

    public native void setOversampleH(int var1);

    public native int getOversampleV();

    public native void setOversampleV(int var1);

    public native boolean getPixelSnapH();

    public native void setPixelSnapH(boolean var1);

    public ImVec2 getGlyphExtraSpacing() {
        ImVec2 imVec2 = new ImVec2();
        this.getGlyphExtraSpacing(imVec2);
        return imVec2;
    }

    public native void getGlyphExtraSpacing(ImVec2 var1);

    public native float getGlyphExtraSpacingX();

    public native float getGlyphExtraSpacingY();

    public native void setGlyphExtraSpacing(float var1, float var2);

    public ImVec2 getGlyphOffset() {
        ImVec2 imVec2 = new ImVec2();
        this.getGlyphOffset(imVec2);
        return imVec2;
    }

    public native void getGlyphOffset(ImVec2 var1);

    public native float getGlyphOffsetX();

    public native float getGlyphOffsetY();

    public native void setGlyphOffset(float var1, float var2);

    public short[] getGlyphRanges() {
        return this.glyphRanges;
    }

    public void setGlyphRanges(short[] sArray) {
        this.glyphRanges = sArray;
        this.nSetGlyphRanges(sArray);
    }

    private native void nSetGlyphRanges(short[] var1);

    public native float getGlyphMinAdvanceX();

    public native void setGlyphMinAdvanceX(float var1);

    public native float getGlyphMaxAdvanceX();

    public native void setGlyphMaxAdvanceX(float var1);

    public native boolean getMergeMode();

    public native void setMergeMode(boolean var1);

    public native int getFontBuilderFlags();

    public native void setFontBuilderFlags(int var1);

    public native float getRasterizerMultiply();

    public native void setRasterizerMultiply(float var1);

    public native short getEllipsisChar();

    public native void setEllipsisChar(int var1);

    public native void setName(String var1);
}

