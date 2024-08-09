/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImFont;
import imgui.ImVec2;
import imgui.binding.ImGuiStruct;

public final class ImDrawList
extends ImGuiStruct {
    public ImDrawList(long l) {
        super(l);
    }

    public native int getImDrawListFlags();

    public native void setImDrawListFlags(int var1);

    public void addImDrawListFlags(int n) {
        this.setImDrawListFlags(this.getImDrawListFlags() | n);
    }

    public void removeImDrawListFlags(int n) {
        this.setImDrawListFlags(this.getImDrawListFlags() & ~n);
    }

    public boolean hasImDrawListFlags(int n) {
        return (this.getImDrawListFlags() & n) != 0;
    }

    public native void pushClipRect(float var1, float var2, float var3, float var4);

    public native void pushClipRect(float var1, float var2, float var3, float var4, boolean var5);

    public native void pushClipRectFullScreen();

    public native void popClipRect();

    public native void pushTextureId(int var1);

    public native void popTextureId();

    public ImVec2 getClipRectMin() {
        ImVec2 imVec2 = new ImVec2();
        this.getClipRectMin(imVec2);
        return imVec2;
    }

    public native void getClipRectMin(ImVec2 var1);

    public native float getClipRectMinX();

    public native float getClipRectMinY();

    public ImVec2 getClipRectMax() {
        ImVec2 imVec2 = new ImVec2();
        this.getClipRectMax(imVec2);
        return imVec2;
    }

    public native void getClipRectMax(ImVec2 var1);

    public native float getClipRectMaxX();

    public native float getClipRectMaxY();

    public native void addLine(float var1, float var2, float var3, float var4, int var5);

    public native void addLine(float var1, float var2, float var3, float var4, int var5, float var6);

    public native void addRect(float var1, float var2, float var3, float var4, int var5);

    public native void addRect(float var1, float var2, float var3, float var4, int var5, float var6);

    public native void addRect(float var1, float var2, float var3, float var4, int var5, float var6, int var7);

    public native void addRect(float var1, float var2, float var3, float var4, int var5, float var6, int var7, float var8);

    public native void addRectFilled(float var1, float var2, float var3, float var4, int var5);

    public native void addRectFilled(float var1, float var2, float var3, float var4, int var5, float var6);

    public native void addRectFilled(float var1, float var2, float var3, float var4, int var5, float var6, int var7);

    public native void addRectFilledMultiColor(float var1, float var2, float var3, float var4, long var5, long var7, long var9, long var11);

    public native void addQuad(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9);

    public native void addQuad(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, float var10);

    public native void addQuadFilled(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9);

    public native void addTriangle(float var1, float var2, float var3, float var4, float var5, float var6, int var7);

    public native void addTriangle(float var1, float var2, float var3, float var4, float var5, float var6, int var7, float var8);

    public native void addTriangleFilled(float var1, float var2, float var3, float var4, float var5, float var6, int var7);

    public native void addCircle(float var1, float var2, float var3, int var4);

    public native void addCircle(float var1, float var2, float var3, int var4, int var5);

    public native void addCircle(float var1, float var2, float var3, int var4, int var5, float var6);

    public native void addCircleFilled(float var1, float var2, float var3, int var4);

    public native void addCircleFilled(float var1, float var2, float var3, int var4, int var5);

    public native void addNgon(float var1, float var2, float var3, int var4, int var5);

    public native void addNgon(float var1, float var2, float var3, int var4, int var5, float var6);

    public native void addNgonFilled(float var1, float var2, float var3, int var4, int var5);

    public native void addText(float var1, float var2, int var3, String var4);

    public void addText(ImFont imFont, float f, float f2, float f3, int n, String string) {
        this.nAddText(imFont.ptr, f, f2, f3, n, string);
    }

    private native void nAddText(long var1, float var3, float var4, float var5, int var6, String var7);

    public void addText(ImFont imFont, float f, float f2, float f3, int n, String string, float f4) {
        this.nAddText(imFont.ptr, f, f2, f3, n, string, f4);
    }

    private native void nAddText(long var1, float var3, float var4, float var5, int var6, String var7, float var8);

    public void addText(ImFont imFont, float f, float f2, float f3, int n, String string, float f4, float f5, float f6, float f7, float f8) {
        this.nAddText(imFont.ptr, f, f2, f3, n, string, f4, f5, f6, f7, f8);
    }

    private native void nAddText(long var1, float var3, float var4, float var5, int var6, String var7, float var8, float var9, float var10, float var11, float var12);

    public native void addPolyline(ImVec2[] var1, int var2, int var3, int var4, float var5);

    public native void addConvexPolyFilled(ImVec2[] var1, int var2, int var3);

    public native void addBezierCubic(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, float var10);

    public native void addBezierCubic(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, float var10, int var11);

    public native void addBezierQuadratic(float var1, float var2, float var3, float var4, float var5, float var6, int var7, float var8);

    public native void addBezierQuadratic(float var1, float var2, float var3, float var4, float var5, float var6, int var7, float var8, int var9);

    public native void addImage(int var1, float var2, float var3, float var4, float var5);

    public native void addImage(int var1, float var2, float var3, float var4, float var5, float var6, float var7);

    public native void addImage(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9);

    public native void addImage(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10);

    public native void addImageQuad(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9);

    public native void addImageQuad(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11);

    public native void addImageQuad(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13);

    public native void addImageQuad(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15);

    public native void addImageQuad(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15, float var16, float var17);

    public native void addImageQuad(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15, float var16, float var17, int var18);

    public native void addImageRounded(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, float var11);

    public native void addImageRounded(int var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, float var11, int var12);

    public native void pathClear();

    public native void pathLineTo(float var1, float var2);

    public native void pathLineToMergeDuplicate(float var1, float var2);

    public native void pathFillConvex(int var1);

    public native void pathStroke(int var1, int var2);

    public native void pathStroke(int var1, int var2, float var3);

    public native void pathArcTo(float var1, float var2, float var3, float var4, float var5);

    public native void pathArcTo(float var1, float var2, float var3, float var4, float var5, int var6);

    public native void pathArcToFast(float var1, float var2, float var3, int var4, int var5);

    public native void pathBezierCubicCurveTo(float var1, float var2, float var3, float var4, float var5, float var6);

    public native void pathBezierCubicCurveTo(float var1, float var2, float var3, float var4, float var5, float var6, int var7);

    public native void pathBezierQuadraticCurveTo(float var1, float var2, float var3, float var4);

    public native void pathBezierQuadraticCurveTo(float var1, float var2, float var3, float var4, int var5);

    public native void pathRect(float var1, float var2, float var3, float var4);

    public native void pathRect(float var1, float var2, float var3, float var4, float var5);

    public native void pathRect(float var1, float var2, float var3, float var4, float var5, int var6);

    public native void channelsSplit(int var1);

    public native void channelsMerge();

    public native void channelsSetCurrent(int var1);

    public native void primReserve(int var1, int var2);

    public native void primUnreserve(int var1, int var2);

    public native void primRect(float var1, float var2, float var3, float var4, int var5);

    public native void primRectUV(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9);

    public native void primQuadUV(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15, float var16, int var17);

    public native void primWriteVtx(float var1, float var2, float var3, float var4, int var5);

    public native void primVtx(float var1, float var2, float var3, float var4, int var5);
}

