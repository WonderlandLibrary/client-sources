/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.imguizmo;

import imgui.ImDrawList;
import imgui.ImGui;

public final class ImGuizmo {
    private static final float[] MATRICES = new float[64];

    private ImGuizmo() {
    }

    private static native void nEnabled(boolean var0);

    public static void setEnabled(boolean bl) {
        ImGuizmo.nEnabled(bl);
    }

    private static native boolean nIsUsing();

    public static boolean isUsing() {
        return ImGuizmo.nIsUsing();
    }

    private static native boolean nIsOver();

    public static boolean isOver() {
        return ImGuizmo.nIsOver();
    }

    private static native void nSetDrawList(long var0);

    public static void setDrawList(ImDrawList imDrawList) {
        ImGuizmo.nSetDrawList(imDrawList.ptr);
    }

    public static void setDrawList() {
        ImGuizmo.setDrawList(ImGui.getWindowDrawList());
    }

    public static native void beginFrame();

    private static native void nDecomposeMatrixToComponents(float[] var0, float[] var1, float[] var2, float[] var3);

    public static void decomposeMatrixToComponents(float[] fArray, float[] fArray2, float[] fArray3, float[] fArray4) {
        ImGuizmo.nDecomposeMatrixToComponents(fArray, fArray2, fArray3, fArray4);
    }

    private static native void nRecomposeMatrixFromComponents(float[] var0, float[] var1, float[] var2, float[] var3);

    public static void recomposeMatrixFromComponents(float[] fArray, float[] fArray2, float[] fArray3, float[] fArray4) {
        ImGuizmo.nRecomposeMatrixFromComponents(fArray, fArray2, fArray3, fArray4);
    }

    public static native void nSetRect(float var0, float var1, float var2, float var3);

    public static void setRect(float f, float f2, float f3, float f4) {
        ImGuizmo.nSetRect(f, f2, f3, f4);
    }

    private static native void nSetOrthographic(boolean var0);

    public static void setOrthographic(boolean bl) {
        ImGuizmo.nSetOrthographic(bl);
    }

    private static native void nDrawCubes(float[] var0, float[] var1, float[] var2, int var3);

    public static void drawCubes(float[] fArray, float[] fArray2, float[] ... fArray3) {
        if (fArray3.length > 4) {
            throw new IllegalArgumentException("Drawing cubes with ImGuizmo only supports up to 4 cubes because it should only be used for debugging purposes");
        }
        int n = fArray3.length;
        for (int i = 0; i < n; ++i) {
            float[] fArray4 = fArray3[i];
            System.arraycopy(fArray4, 0, MATRICES, i * fArray4.length, fArray4.length);
        }
        ImGuizmo.nDrawCubes(fArray, fArray2, MATRICES, fArray3.length);
    }

    private static native void nDrawGrid(float[] var0, float[] var1, float[] var2, int var3);

    public static void drawGrid(float[] fArray, float[] fArray2, float[] fArray3, int n) {
        ImGuizmo.nDrawGrid(fArray, fArray2, fArray3, n);
    }

    private static native void nManipulate(float[] var0, float[] var1, int var2, int var3, float[] var4);

    private static native void nManipulate(float[] var0, float[] var1, int var2, int var3, float[] var4, float[] var5);

    private static native void nManipulate(float[] var0, float[] var1, int var2, int var3, float[] var4, float[] var5, float[] var6);

    private static native void nManipulate(float[] var0, float[] var1, int var2, int var3, float[] var4, float[] var5, float[] var6, float[] var7);

    private static native void nManipulate(float[] var0, float[] var1, int var2, int var3, float[] var4, float[] var5, float[] var6, float[] var7, float[] var8);

    public static void manipulate(float[] fArray, float[] fArray2, float[] fArray3, int n, int n2) {
        ImGuizmo.nManipulate(fArray, fArray2, n, n2, fArray3);
    }

    public static void manipulate(float[] fArray, float[] fArray2, float[] fArray3, int n, int n2, float[] fArray4) {
        ImGuizmo.nManipulate(fArray, fArray2, n, n2, fArray3, fArray4);
    }

    public static void manipulate(float[] fArray, float[] fArray2, float[] fArray3, int n, int n2, float[] fArray4, float[] fArray5) {
        ImGuizmo.nManipulate(fArray, fArray2, n, n2, fArray3, fArray4, fArray5);
    }

    public static void manipulate(float[] fArray, float[] fArray2, float[] fArray3, int n, int n2, float[] fArray4, float[] fArray5, float[] fArray6) {
        ImGuizmo.nManipulate(fArray, fArray2, n, n2, fArray3, fArray4, fArray5, fArray6);
    }

    public static void manipulate(float[] fArray, float[] fArray2, float[] fArray3, float[] fArray4, int n, int n2, float[] fArray5, float[] fArray6, float[] fArray7) {
        ImGuizmo.nManipulate(fArray, fArray2, n, n2, fArray3, fArray4, fArray5, fArray6, fArray7);
    }

    private static native void nViewManipulate(float[] var0, float var1, float[] var2, float[] var3, int var4);

    public static void viewManipulate(float[] fArray, float f, float[] fArray2, float[] fArray3, int n) {
        ImGuizmo.nViewManipulate(fArray, f, fArray2, fArray3, n);
    }

    private static native void nSetId(int var0);

    public static void setId(int n) {
        ImGuizmo.nSetId(n);
    }

    private static native boolean nIsOver(int var0);

    public static boolean isOver(int n) {
        return ImGuizmo.nIsOver(n);
    }

    private static native void nSetGizmoSizeClipSpace(float var0);

    private static native void nAllowAxisFlip(boolean var0);

    public static void setAllowAxisFlip(boolean bl) {
        ImGuizmo.nAllowAxisFlip(bl);
    }
}

