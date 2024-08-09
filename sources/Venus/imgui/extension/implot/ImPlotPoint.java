/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.implot;

import imgui.ImVec2;
import imgui.binding.ImGuiStructDestroyable;

public final class ImPlotPoint
extends ImGuiStructDestroyable {
    public ImPlotPoint(long l) {
        super(l);
    }

    public ImPlotPoint() {
        this(0.0, 0.0);
    }

    public ImPlotPoint(ImVec2 imVec2) {
        this(imVec2.x, imVec2.y);
    }

    public ImPlotPoint(double d, double d2) {
        this(0L);
        this.ptr = this.create(d, d2);
    }

    @Override
    protected native long create();

    protected native long create(double var1, double var3);

    public native double getX();

    public native double getY();

    public native void setX(double var1);

    public native void setY(double var1);
}

