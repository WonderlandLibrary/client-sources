/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.implot;

import imgui.ImVec2;
import imgui.binding.ImGuiStructDestroyable;
import imgui.extension.implot.ImPlotPoint;
import imgui.extension.implot.ImPlotRange;

public final class ImPlotLimits
extends ImGuiStructDestroyable {
    public ImPlotLimits(long l) {
        super(l);
    }

    public ImPlotLimits() {
        this(0.0, 0.0, 0.0, 0.0);
    }

    public ImPlotLimits(double d, double d2, double d3, double d4) {
        this(0L);
        this.ptr = this.create(d, d2, d3, d4);
    }

    @Override
    protected native long create();

    protected native long create(double var1, double var3, double var5, double var7);

    public boolean contains(ImPlotPoint imPlotPoint) {
        return this.contains(imPlotPoint.getX(), imPlotPoint.getY());
    }

    public native boolean contains(double var1, double var3);

    public ImVec2 minVec() {
        ImPlotPoint imPlotPoint = this.min();
        ImVec2 imVec2 = new ImVec2((float)imPlotPoint.getX(), (float)imPlotPoint.getY());
        imPlotPoint.destroy();
        return imVec2;
    }

    public ImPlotPoint min() {
        return new ImPlotPoint(this.nMin());
    }

    private native long nMin();

    public ImPlotPoint max() {
        return new ImPlotPoint(this.nMax());
    }

    public ImVec2 maxVec() {
        ImPlotPoint imPlotPoint = this.max();
        ImVec2 imVec2 = new ImVec2((float)imPlotPoint.getX(), (float)imPlotPoint.getY());
        imPlotPoint.destroy();
        return imVec2;
    }

    private native long nMax();

    public ImPlotRange getX() {
        return new ImPlotRange(this.nGetX(this.ptr));
    }

    private native long nGetX(long var1);

    public ImPlotRange getY() {
        return new ImPlotRange(this.nGetY(this.ptr));
    }

    private native long nGetY(long var1);

    public void setX(ImPlotRange imPlotRange) {
        this.nSetX(imPlotRange.ptr);
    }

    private native void nSetX(long var1);

    public void setY(ImPlotRange imPlotRange) {
        this.nSetY(imPlotRange.ptr);
    }

    private native void nSetY(long var1);
}

