/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.imnodes;

import imgui.ImVec2;
import imgui.binding.ImGuiStruct;

public final class ImNodesStyle
extends ImGuiStruct {
    public ImNodesStyle(long l) {
        super(l);
    }

    public native float getGridSpacing();

    public native void setGridSpacing(float var1);

    public native float getNodeCornerRounding();

    public native void setNodeCornerRounding(float var1);

    public native void getNodePadding(ImVec2 var1);

    public native void setNodePadding(float var1, float var2);

    public native float getNodeBorderThickness();

    public native void setNodeBorderThickness(float var1);

    public native float getLinkThickness();

    public native void setLinkThickness(float var1);

    public native float getLinkLineSegmentsPerLength();

    public native void setLinkLineSegmentsPerLength(float var1);

    public native float getLinkHoverDistance();

    public native void setLinkHoverDistance(float var1);

    public native float getPinCircleRadius();

    public native void setPinCircleRadius(float var1);

    public native float getPinQuadSideLength();

    public native void setPinQuadSideLength(float var1);

    public native float getPinTriangleSideLength();

    public native void setPinTriangleSideLength(float var1);

    public native float getPinLineThickness();

    public native void setPinLineThickness(float var1);

    public native float getPinHoverRadius();

    public native void setPinHoverRadius(float var1);

    public native float getPinOffset();

    public native void setPinOffset(float var1);

    public native void getMiniMapPadding(ImVec2 var1);

    public native void setMiniMapPadding(float var1, float var2);

    public native ImVec2 getMiniMapOffset(ImVec2 var1);

    public native void setMiniMapOffset(float var1, float var2);

    public native int getFlags();

    public native void setFlags(int var1);
}

