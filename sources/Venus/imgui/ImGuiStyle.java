/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.ImGuiStructDestroyable;

public final class ImGuiStyle
extends ImGuiStructDestroyable {
    public ImGuiStyle() {
    }

    public ImGuiStyle(long l) {
        super(l);
    }

    @Override
    protected long create() {
        return this.nCreate();
    }

    private native long nCreate();

    public native float getAlpha();

    public native void setAlpha(float var1);

    public native float getDisabledAlpha();

    public native void setDisabledAlpha(float var1);

    public ImVec2 getWindowPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.getWindowPadding(imVec2);
        return imVec2;
    }

    public native void getWindowPadding(ImVec2 var1);

    public native float getWindowPaddingX();

    public native float getWindowPaddingY();

    public native void setWindowPadding(float var1, float var2);

    public native float getWindowRounding();

    public native void setWindowRounding(float var1);

    public native float getWindowBorderSize();

    public native void setWindowBorderSize(float var1);

    public ImVec2 getWindowMinSize() {
        ImVec2 imVec2 = new ImVec2();
        this.getWindowMinSize(imVec2);
        return imVec2;
    }

    public native void getWindowMinSize(ImVec2 var1);

    public native float getWindowMinSizeX();

    public native float getWindowMinSizeY();

    public native void setWindowMinSize(float var1, float var2);

    public ImVec2 getWindowTitleAlign() {
        ImVec2 imVec2 = new ImVec2();
        this.getWindowTitleAlign(imVec2);
        return imVec2;
    }

    public native void getWindowTitleAlign(ImVec2 var1);

    public native float getWindowTitleAlignX();

    public native float getWindowTitleAlignY();

    public native void setWindowTitleAlign(float var1, float var2);

    public native int getWindowMenuButtonPosition();

    public native void setWindowMenuButtonPosition(int var1);

    public native float getChildRounding();

    public native void setChildRounding(float var1);

    public native float getChildBorderSize();

    public native void setChildBorderSize(float var1);

    public native float getPopupRounding();

    public native void setPopupRounding(float var1);

    public native float getPopupBorderSize();

    public native void setPopupBorderSize(float var1);

    public ImVec2 getFramePadding() {
        ImVec2 imVec2 = new ImVec2();
        this.getFramePadding(imVec2);
        return imVec2;
    }

    public native void getFramePadding(ImVec2 var1);

    public native float getFramePaddingX();

    public native float getFramePaddingY();

    public native void setFramePadding(float var1, float var2);

    public native float getFrameRounding();

    public native void setFrameRounding(float var1);

    public native float getFrameBorderSize();

    public native void setFrameBorderSize(float var1);

    public ImVec2 getItemSpacing() {
        ImVec2 imVec2 = new ImVec2();
        this.getItemSpacing(imVec2);
        return imVec2;
    }

    public native void getItemSpacing(ImVec2 var1);

    public native float getItemSpacingX();

    public native float getItemSpacingY();

    public native void setItemSpacing(float var1, float var2);

    public ImVec2 getItemInnerSpacing() {
        ImVec2 imVec2 = new ImVec2();
        this.getItemInnerSpacing(imVec2);
        return imVec2;
    }

    public native void getItemInnerSpacing(ImVec2 var1);

    public native float getItemInnerSpacingX();

    public native float getItemInnerSpacingY();

    public native void setItemInnerSpacing(float var1, float var2);

    public ImVec2 getCellPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.getCellPadding(imVec2);
        return imVec2;
    }

    public native void getCellPadding(ImVec2 var1);

    public native float getCellPaddingX();

    public native float getCellPaddingY();

    public native void setCellPadding(float var1, float var2);

    public ImVec2 getTouchExtraPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.getTouchExtraPadding(imVec2);
        return imVec2;
    }

    public native void getTouchExtraPadding(ImVec2 var1);

    public native float getTouchExtraPaddingX();

    public native float getTouchExtraPaddingY();

    public native void setTouchExtraPadding(float var1, float var2);

    public native float getIndentSpacing();

    public native void setIndentSpacing(float var1);

    public native float getColumnsMinSpacing();

    public native void setColumnsMinSpacing(float var1);

    public native float getScrollbarSize();

    public native void setScrollbarSize(float var1);

    public native float getScrollbarRounding();

    public native void setScrollbarRounding(float var1);

    public native float getGrabMinSize();

    public native void setGrabMinSize(float var1);

    public native float getGrabRounding();

    public native void setGrabRounding(float var1);

    public native float getLogSliderDeadzone();

    public native void setLogSliderDeadzone(float var1);

    public native float getTabRounding();

    public native void setTabRounding(float var1);

    public native float getTabBorderSize();

    public native void setTabBorderSize(float var1);

    public native float getTabMinWidthForCloseButton();

    public native void setTabMinWidthForCloseButton(float var1);

    public native int getColorButtonPosition();

    public native void setColorButtonPosition(int var1);

    public ImVec2 getButtonTextAlign() {
        ImVec2 imVec2 = new ImVec2();
        this.getButtonTextAlign(imVec2);
        return imVec2;
    }

    public native void getButtonTextAlign(ImVec2 var1);

    public native float getButtonTextAlignX();

    public native float getButtonTextAlignY();

    public native void setButtonTextAlign(float var1, float var2);

    public ImVec2 getSelectableTextAlign() {
        ImVec2 imVec2 = new ImVec2();
        this.getSelectableTextAlign(imVec2);
        return imVec2;
    }

    public native void getSelectableTextAlign(ImVec2 var1);

    public native float getSelectableTextAlignX();

    public native float getSelectableTextAlignY();

    public native void setSelectableTextAlign(float var1, float var2);

    public ImVec2 getDisplayWindowPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.getDisplayWindowPadding(imVec2);
        return imVec2;
    }

    public native void getDisplayWindowPadding(ImVec2 var1);

    public native float getDisplayWindowPaddingX();

    public native float getDisplayWindowPaddingY();

    public native void setDisplayWindowPadding(float var1, float var2);

    public ImVec2 getDisplaySafeAreaPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.getDisplaySafeAreaPadding(imVec2);
        return imVec2;
    }

    public native void getDisplaySafeAreaPadding(ImVec2 var1);

    public native float getDisplaySafeAreaPaddingX();

    public native float getDisplaySafeAreaPaddingY();

    public native void setDisplaySafeAreaPadding(float var1, float var2);

    public native float getMouseCursorScale();

    public native void setMouseCursorScale(float var1);

    public native boolean getAntiAliasedLines();

    public native void setAntiAliasedLines(boolean var1);

    public native boolean getAntiAliasedLinesUseTex();

    public native void setAntiAliasedLinesUseTex(boolean var1);

    public native boolean getAntiAliasedFill();

    public native void setAntiAliasedFill(boolean var1);

    public native float getCurveTessellationTol();

    public native void setCurveTessellationTol(float var1);

    public native float getCircleTessellationMaxError();

    public native void setCircleTessellationMaxError(float var1);

    public float[][] getColors() {
        float[][] fArray = new float[55][4];
        this.getColors(fArray);
        return fArray;
    }

    public native void getColors(float[][] var1);

    public native void setColors(float[][] var1);

    public ImVec4 getColor(int n) {
        ImVec4 imVec4 = new ImVec4();
        this.getColor(n, imVec4);
        return imVec4;
    }

    public native void getColor(int var1, ImVec4 var2);

    public native void setColor(int var1, float var2, float var3, float var4, float var5);

    public native void setColor(int var1, int var2, int var3, int var4, int var5);

    public native void setColor(int var1, int var2);

    public native void scaleAllSizes(float var1);
}

