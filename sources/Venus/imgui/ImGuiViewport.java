/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImDrawData;
import imgui.ImVec2;
import imgui.binding.ImGuiStruct;

public final class ImGuiViewport
extends ImGuiStruct {
    private static final ImDrawData DRAW_DATA = new ImDrawData(0L);

    public ImGuiViewport(long l) {
        super(l);
    }

    public native int getID();

    public native void setID(int var1);

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

    public ImVec2 getPos() {
        ImVec2 imVec2 = new ImVec2();
        this.getPos(imVec2);
        return imVec2;
    }

    public native void getPos(ImVec2 var1);

    public native float getPosX();

    public native float getPosY();

    public native void setPos(float var1, float var2);

    public ImVec2 getSize() {
        ImVec2 imVec2 = new ImVec2();
        this.getSize(imVec2);
        return imVec2;
    }

    public native void getSize(ImVec2 var1);

    public native float getSizeX();

    public native float getSizeY();

    public native void seSize(float var1, float var2);

    public ImVec2 getWorkPos() {
        ImVec2 imVec2 = new ImVec2();
        this.getWorkPos(imVec2);
        return imVec2;
    }

    public native void getWorkPos(ImVec2 var1);

    public native float getWorkPosX();

    public native float getWorkPosY();

    public native void setWorkPos(float var1, float var2);

    public ImVec2 getWorkSize() {
        ImVec2 imVec2 = new ImVec2();
        this.getWorkSize(imVec2);
        return imVec2;
    }

    public native void getWorkSize(ImVec2 var1);

    public native float getWorkSizeX();

    public native float getWorkSizeY();

    public native void setWorkSize(float var1, float var2);

    public native float getDpiScale();

    public native void setDpiScale(float var1);

    public native int getParentViewportId();

    public native void setParentViewportId(int var1);

    public ImDrawData getDrawData() {
        ImGuiViewport.DRAW_DATA.ptr = this.nGetDrawData();
        return DRAW_DATA;
    }

    private native long nGetDrawData();

    public native void setRendererUserData(Object var1);

    public native Object getRendererUserData();

    public native void setPlatformUserData(Object var1);

    public native Object getPlatformUserData();

    public native void setPlatformHandle(long var1);

    public native long getPlatformHandle();

    public native void setPlatformHandleRaw(long var1);

    public native long getPlatformHandleRaw();

    public native boolean getPlatformRequestMove();

    public native void setPlatformRequestMove(boolean var1);

    public native boolean getPlatformRequestResize();

    public native void setPlatformRequestResize(boolean var1);

    public native boolean getPlatformRequestClose();

    public native void setPlatformRequestClose(boolean var1);

    public ImVec2 getCenter() {
        ImVec2 imVec2 = new ImVec2();
        this.getCenter(imVec2);
        return imVec2;
    }

    public native void getCenter(ImVec2 var1);

    public native float getCenterX();

    public native float getCenterY();

    public ImVec2 getWorkCenter() {
        ImVec2 imVec2 = new ImVec2();
        this.getWorkCenter(imVec2);
        return imVec2;
    }

    public native void getWorkCenter(ImVec2 var1);

    public native float getWorkCenterX();

    public native float getWorkCenterY();
}

