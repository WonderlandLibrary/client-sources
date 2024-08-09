/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImVec2;
import imgui.binding.ImGuiStruct;

public final class ImGuiPlatformMonitor
extends ImGuiStruct {
    public ImGuiPlatformMonitor(long l) {
        super(l);
    }

    public native void getMainPos(ImVec2 var1);

    public native float getMainPosX();

    public native float getMainPosY();

    public native void setMainPos(float var1, float var2);

    public native void getMainSize(ImVec2 var1);

    public native float getMainSizeX();

    public native float getMainSizeY();

    public native void setMainSize(float var1, float var2);

    public native void getWorkPos(ImVec2 var1);

    public native float getWorkPosX();

    public native float getWorkPosY();

    public native void setWorkPos(float var1, float var2);

    public native void getWorkSize(ImVec2 var1);

    public native float getWorkSizeX();

    public native float getWorkSizeY();

    public native void setWorkSize(float var1, float var2);

    public native float getDpiScale();

    public native void setDpiScale(float var1);
}

