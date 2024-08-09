/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.ImGuiPlatformMonitor;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.binding.ImGuiStruct;
import imgui.callback.ImPlatformFuncViewport;
import imgui.callback.ImPlatformFuncViewportFloat;
import imgui.callback.ImPlatformFuncViewportImVec2;
import imgui.callback.ImPlatformFuncViewportString;
import imgui.callback.ImPlatformFuncViewportSuppBoolean;
import imgui.callback.ImPlatformFuncViewportSuppFloat;
import imgui.callback.ImPlatformFuncViewportSuppImVec2;

public final class ImGuiPlatformIO
extends ImGuiStruct {
    private static final ImGuiViewport TMP_VIEWPORT = new ImGuiViewport(0L);
    private static final ImGuiPlatformMonitor TMP_MONITOR = new ImGuiPlatformMonitor(0L);
    private static final ImVec2 TMP_IM_VEC2 = new ImVec2();

    public ImGuiPlatformIO(long l) {
        super(l);
    }

    static void init() {
        ImGuiPlatformIO.nInit(TMP_VIEWPORT, TMP_IM_VEC2);
    }

    private static native void nInit(ImGuiViewport var0, ImVec2 var1);

    public native void setPlatformCreateWindow(ImPlatformFuncViewport var1);

    public native void setPlatformDestroyWindow(ImPlatformFuncViewport var1);

    public native void setPlatformShowWindow(ImPlatformFuncViewport var1);

    public native void setPlatformSetWindowPos(ImPlatformFuncViewportImVec2 var1);

    public native void setPlatformGetWindowPos(ImPlatformFuncViewportSuppImVec2 var1);

    public native void setPlatformSetWindowSize(ImPlatformFuncViewportImVec2 var1);

    public native void setPlatformGetWindowSize(ImPlatformFuncViewportSuppImVec2 var1);

    public native void setPlatformSetWindowFocus(ImPlatformFuncViewport var1);

    public native void setPlatformGetWindowFocus(ImPlatformFuncViewportSuppBoolean var1);

    public native void setPlatformGetWindowMinimized(ImPlatformFuncViewportSuppBoolean var1);

    public native void setPlatformSetWindowTitle(ImPlatformFuncViewportString var1);

    public native void setPlatformSetWindowAlpha(ImPlatformFuncViewportFloat var1);

    public native void setPlatformUpdateWindow(ImPlatformFuncViewport var1);

    public native void setPlatformRenderWindow(ImPlatformFuncViewport var1);

    public native void setPlatformSwapBuffers(ImPlatformFuncViewport var1);

    public native void setPlatformGetWindowDpiScale(ImPlatformFuncViewportSuppFloat var1);

    public native void setPlatformOnChangedViewport(ImPlatformFuncViewport var1);

    public native void setRendererCreateWindow(ImPlatformFuncViewport var1);

    public native void setRendererDestroyWindow(ImPlatformFuncViewport var1);

    public native void setRendererSetWindowPos(ImPlatformFuncViewportImVec2 var1);

    public native void setRendererRenderWindow(ImPlatformFuncViewport var1);

    public native void setRendererSwapBuffers(ImPlatformFuncViewport var1);

    public native void resizeMonitors(int var1);

    public native int getMonitorsSize();

    public native void pushMonitors(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9);

    public ImGuiPlatformMonitor getMonitors(int n) {
        ImGuiPlatformIO.TMP_MONITOR.ptr = this.nGetMonitors(n);
        return TMP_MONITOR;
    }

    private native long nGetMonitors(int var1);

    public native int getViewportsSize();

    public ImGuiViewport getViewports(int n) {
        ImGuiPlatformIO.TMP_VIEWPORT.ptr = this.nGetViewports(n);
        return TMP_VIEWPORT;
    }

    private native long nGetViewports(int var1);
}

