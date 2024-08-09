/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.implot;

import imgui.ImVec2;
import imgui.ImVec4;
import imgui.binding.ImGuiStruct;

public final class ImPlotStyle
extends ImGuiStruct {
    public ImPlotStyle(long l) {
        super(l);
    }

    public native float getLineWeight();

    public native void setLineWeight(float var1);

    public native int getMarker();

    public native void setMarker(float var1);

    public native float getMarkerSize();

    public native void setMarkerSize(float var1);

    public native float getMarkerWeight();

    public native void setMarkerWeight(float var1);

    public native float getFillAlpha();

    public native void setFillAlpha(float var1);

    public native float getErrorBarSize();

    public native void setErrorBarSize(float var1);

    public native float getErrorBarWeight();

    public native void setErrorBarWeight(float var1);

    public native float getDigitalBitHeight();

    public native void setDigitalBitHeight(float var1);

    public native float getDigitalBitGap();

    public native void setDigitalBitGap(float var1);

    public native float getPlotBorderSize();

    public native void setPlotBorderSize(float var1);

    public native float getMinorAlpha();

    public native void setMinorAlpha(float var1);

    public ImVec2 getMajorTickLen() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetMajorTickLen(imVec2);
        return imVec2;
    }

    private native void nGetMajorTickLen(ImVec2 var1);

    public void setMajorTickLen(ImVec2 imVec2) {
        this.nSetMajorTickLen(imVec2.x, imVec2.y);
    }

    private native void nSetMajorTickLen(float var1, float var2);

    public ImVec2 getMinorTickLen() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetMinorTickLen(imVec2);
        return imVec2;
    }

    private native void nGetMinorTickLen(ImVec2 var1);

    public void setMinorTickLen(ImVec2 imVec2) {
        this.nSetMinorTickLen(imVec2.x, imVec2.y);
    }

    private native void nSetMinorTickLen(float var1, float var2);

    public ImVec2 getMajorTickSize() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetMajorTickSize(imVec2);
        return imVec2;
    }

    private native void nGetMajorTickSize(ImVec2 var1);

    public void setMajorTickSize(ImVec2 imVec2) {
        this.nSetMajorTickSize(imVec2.x, imVec2.y);
    }

    private native void nSetMajorTickSize(float var1, float var2);

    public ImVec2 getMinorTickSize() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetMinorTickSize(imVec2);
        return imVec2;
    }

    private native void nGetMinorTickSize(ImVec2 var1);

    public void setMinorTickSize(ImVec2 imVec2) {
        this.nSetMinorTickSize(imVec2.x, imVec2.y);
    }

    private native void nSetMinorTickSize(float var1, float var2);

    public ImVec2 getMajorGridSize() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetMajorGridSize(imVec2);
        return imVec2;
    }

    private native void nGetMajorGridSize(ImVec2 var1);

    public void setMajorGridSize(ImVec2 imVec2) {
        this.nSetMajorGridSize(imVec2.x, imVec2.y);
    }

    private native void nSetMajorGridSize(float var1, float var2);

    public ImVec2 getMinorGridSize() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetMinorGridSize(imVec2);
        return imVec2;
    }

    private native void nGetMinorGridSize(ImVec2 var1);

    public void setMinorGridSize(ImVec2 imVec2) {
        this.nSetMinorGridSize(imVec2.x, imVec2.y);
    }

    private native void nSetMinorGridSize(float var1, float var2);

    public ImVec2 getPlotPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetPlotPadding(imVec2);
        return imVec2;
    }

    private native void nGetPlotPadding(ImVec2 var1);

    public void setPlotPadding(ImVec2 imVec2) {
        this.nSetPlotPadding(imVec2.x, imVec2.y);
    }

    private native void nSetPlotPadding(float var1, float var2);

    public ImVec2 getLabelPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetLabelPadding(imVec2);
        return imVec2;
    }

    private native void nGetLabelPadding(ImVec2 var1);

    public void setLabelPadding(ImVec2 imVec2) {
        this.nSetLabelPadding(imVec2.x, imVec2.y);
    }

    private native void nSetLabelPadding(float var1, float var2);

    public ImVec2 getLegendPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetLegendPadding(imVec2);
        return imVec2;
    }

    private native void nGetLegendPadding(ImVec2 var1);

    public void setLegendPadding(ImVec2 imVec2) {
        this.nSetLegendPadding(imVec2.x, imVec2.y);
    }

    private native void nSetLegendPadding(float var1, float var2);

    public ImVec2 getLegendInnerPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetLegendInnerPadding(imVec2);
        return imVec2;
    }

    private native void nGetLegendInnerPadding(ImVec2 var1);

    public void setLegendInnerPadding(ImVec2 imVec2) {
        this.nSetLegendInnerPadding(imVec2.x, imVec2.y);
    }

    private native void nSetLegendInnerPadding(float var1, float var2);

    public ImVec2 getLegendSpacing() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetLegendSpacing(imVec2);
        return imVec2;
    }

    private native void nGetLegendSpacing(ImVec2 var1);

    public void setLegendSpacing(ImVec2 imVec2) {
        this.nSetLegendSpacing(imVec2.x, imVec2.y);
    }

    private native void nSetLegendSpacing(float var1, float var2);

    public ImVec2 getMousePosPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetMousePosPadding(imVec2);
        return imVec2;
    }

    private native void nGetMousePosPadding(ImVec2 var1);

    public void setMousePosPadding(ImVec2 imVec2) {
        this.nSetMousePosPadding(imVec2.x, imVec2.y);
    }

    private native void nSetMousePosPadding(float var1, float var2);

    public ImVec2 getAnnotationPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetAnnotationPadding(imVec2);
        return imVec2;
    }

    private native void nGetAnnotationPadding(ImVec2 var1);

    public void setAnnotationPadding(ImVec2 imVec2) {
        this.nSetAnnotationPadding(imVec2.x, imVec2.y);
    }

    private native void nSetAnnotationPadding(float var1, float var2);

    public ImVec2 getFitPadding() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetFitPadding(imVec2);
        return imVec2;
    }

    private native void nGetFitPadding(ImVec2 var1);

    public void setFitPadding(ImVec2 imVec2) {
        this.nSetFitPadding(imVec2.x, imVec2.y);
    }

    private native void nSetFitPadding(float var1, float var2);

    public ImVec2 getPlotDefaultSize() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetPlotDefaultSize(imVec2);
        return imVec2;
    }

    private native void nGetPlotDefaultSize(ImVec2 var1);

    public void setPlotDefaultSize(ImVec2 imVec2) {
        this.nSetPlotDefaultSize(imVec2.x, imVec2.y);
    }

    private native void nSetPlotDefaultSize(float var1, float var2);

    public ImVec2 getPlotMinSize() {
        ImVec2 imVec2 = new ImVec2();
        this.nGetPlotMinSize(imVec2);
        return imVec2;
    }

    private native void nGetPlotMinSize(ImVec2 var1);

    public void setPlotMinSize(ImVec2 imVec2) {
        this.nSetPlotMinSize(imVec2.x, imVec2.y);
    }

    private native void nSetPlotMinSize(float var1, float var2);

    public ImVec4[] getColors() {
        float[] fArray = new float[25];
        float[] fArray2 = new float[25];
        float[] fArray3 = new float[25];
        float[] fArray4 = new float[25];
        this.nGetColors(fArray, fArray2, fArray3, fArray4, 25);
        ImVec4[] imVec4Array = new ImVec4[25];
        for (int i = 0; i < 25; ++i) {
            imVec4Array[i] = new ImVec4(fArray[i], fArray2[i], fArray3[i], fArray4[i]);
        }
        return imVec4Array;
    }

    private native void nGetColors(float[] var1, float[] var2, float[] var3, float[] var4, int var5);

    public void setColors(ImVec4[] imVec4Array) {
        float[] fArray = new float[25];
        float[] fArray2 = new float[25];
        float[] fArray3 = new float[25];
        float[] fArray4 = new float[25];
        for (int i = 0; i < 25; ++i) {
            fArray[i] = imVec4Array[i].w;
            fArray2[i] = imVec4Array[i].x;
            fArray3[i] = imVec4Array[i].y;
            fArray4[i] = imVec4Array[i].z;
        }
        this.nSetColors(fArray, fArray2, fArray3, fArray4, 25);
    }

    private native void nSetColors(float[] var1, float[] var2, float[] var3, float[] var4, int var5);

    public native int getColormap();

    public native void setColormap(int var1);

    public native boolean isAntiAliasedLines();

    public native void setAntiAliasedLines(boolean var1);

    public native boolean isUseLocalTime();

    public native void setUseLocalTime(boolean var1);

    public native boolean isUseISO8601();

    public native void setUseISO8601(boolean var1);

    public native boolean isUse24HourClock();

    public native void setUse24HourClock(boolean var1);
}

