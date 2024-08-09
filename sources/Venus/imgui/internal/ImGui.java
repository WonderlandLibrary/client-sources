/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.internal;

import imgui.ImVec2;
import imgui.internal.ImGuiDockNode;
import imgui.internal.ImGuiWindow;
import imgui.internal.ImRect;
import imgui.type.ImFloat;
import imgui.type.ImInt;

public final class ImGui
extends imgui.ImGui {
    private static final ImGuiDockNode DOCK_NODE = new ImGuiDockNode(0L);
    private static final ImGuiWindow IMGUI_CURRENT_WINDOW = new ImGuiWindow(0L);

    public static ImVec2 calcItemSize(float f, float f2, float f3, float f4) {
        ImVec2 imVec2 = new ImVec2();
        ImGui.calcItemSize(f, f2, f3, f4, imVec2);
        return imVec2;
    }

    public static native void calcItemSize(float var0, float var1, float var2, float var3, ImVec2 var4);

    public static native float calcItemSizeX(float var0, float var1, float var2, float var3);

    public static native float calcItemSizeY(float var0, float var1, float var2, float var3);

    public static native void pushItemFlag(int var0, boolean var1);

    public static native void popItemFlag();

    public static native void dockBuilderDockWindow(String var0, int var1);

    public static ImGuiDockNode dockBuilderGetNode(int n) {
        ImGui.DOCK_NODE.ptr = ImGui.nDockBuilderGetNode(n);
        return DOCK_NODE;
    }

    private static native long nDockBuilderGetNode(int var0);

    public static ImGuiDockNode dockBuilderGetCentralNode(int n) {
        ImGui.DOCK_NODE.ptr = ImGui.nDockBuilderGetCentralNode(n);
        return DOCK_NODE;
    }

    private static native long nDockBuilderGetCentralNode(int var0);

    public static native int dockBuilderAddNode();

    public static native int dockBuilderAddNode(int var0);

    public static native int dockBuilderAddNode(int var0, int var1);

    public static native void dockBuilderRemoveNode(int var0);

    public static native void dockBuilderRemoveNodeDockedWindows(int var0);

    public static native void dockBuilderRemoveNodeDockedWindows(int var0, boolean var1);

    public static native void dockBuilderRemoveNodeChildNodes(int var0);

    public static native void dockBuilderSetNodePos(int var0, float var1, float var2);

    public static native void dockBuilderSetNodeSize(int var0, float var1, float var2);

    public static int dockBuilderSplitNode(int n, int n2, float f, ImInt imInt, ImInt imInt2) {
        if (imInt == null && imInt2 != null) {
            return ImGui.nDockBuilderSplitNode(n, n2, f, 0, imInt2.getData());
        }
        if (imInt != null && imInt2 == null) {
            return ImGui.nDockBuilderSplitNode(n, n2, f, imInt.getData());
        }
        if (imInt != null) {
            return ImGui.nDockBuilderSplitNode(n, n2, f, imInt.getData(), imInt2.getData());
        }
        return ImGui.nDockBuilderSplitNode(n, n2, f);
    }

    private static native int nDockBuilderSplitNode(int var0, int var1, float var2, int[] var3, int[] var4);

    private static native int nDockBuilderSplitNode(int var0, int var1, float var2);

    private static native int nDockBuilderSplitNode(int var0, int var1, float var2, int[] var3);

    private static native int nDockBuilderSplitNode(int var0, int var1, float var2, int var3, int[] var4);

    public static native void dockBuilderCopyWindowSettings(String var0, String var1);

    public static native void dockBuilderFinish(int var0);

    public static boolean splitterBehavior(float f, float f2, float f3, float f4, int n, int n2, ImFloat imFloat, ImFloat imFloat2, float f5, float f6) {
        return ImGui.splitterBehavior(f, f2, f3, f4, n, n2, imFloat, imFloat2, f5, f6, 0.0f, 0.0f, 0);
    }

    public static boolean splitterBehavior(float f, float f2, float f3, float f4, int n, int n2, ImFloat imFloat, ImFloat imFloat2, float f5, float f6, float f7) {
        return ImGui.splitterBehavior(f, f2, f3, f4, n, n2, imFloat, imFloat2, f5, f6, f7, 0.0f, 0);
    }

    public static boolean splitterBehavior(float f, float f2, float f3, float f4, int n, int n2, ImFloat imFloat, ImFloat imFloat2, float f5, float f6, float f7, float f8, int n3) {
        return ImGui.nSplitterBehaviour(f, f2, f3, f4, n, n2, imFloat.getData(), imFloat2.getData(), f5, f6, f7, f8, n3);
    }

    private static native boolean nSplitterBehaviour(float var0, float var1, float var2, float var3, int var4, int var5, float[] var6, float[] var7, float var8, float var9, float var10, float var11, int var12);

    public static ImGuiWindow getCurrentWindow() {
        ImGui.IMGUI_CURRENT_WINDOW.ptr = ImGui.nGetCurrentWindow();
        return IMGUI_CURRENT_WINDOW;
    }

    private static native long nGetCurrentWindow();

    public static ImRect getWindowScrollbarRect(ImGuiWindow imGuiWindow, int n) {
        ImRect imRect = new ImRect();
        ImGui.nGetWindowScrollbarRect(imGuiWindow.ptr, n, imRect.min, imRect.max);
        return imRect;
    }

    private static native void nGetWindowScrollbarRect(long var0, int var2, ImVec2 var3, ImVec2 var4);
}

