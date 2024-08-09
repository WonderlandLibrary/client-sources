/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.extension.imguifiledialog;

import imgui.extension.imguifiledialog.callback.ImGuiFileDialogPaneFun;
import java.util.HashMap;

public final class ImGuiFileDialog {
    private ImGuiFileDialog() {
    }

    public static native void openDialog(String var0, String var1, String var2, String var3, String var4, int var5, long var6, int var8);

    public static native void openModal(String var0, String var1, String var2, String var3, String var4, int var5, long var6, int var8);

    public static native void openModal(String var0, String var1, String var2, String var3, int var4, long var5, int var7);

    public static native void openModal(String var0, String var1, String var2, String var3, String var4, ImGuiFileDialogPaneFun var5, float var6, int var7, long var8, int var10);

    public static native void openModal(String var0, String var1, String var2, String var3, ImGuiFileDialogPaneFun var4, float var5, int var6, long var7, int var9);

    public static native boolean display(String var0, int var1, float var2, float var3, float var4, float var5);

    public static native void close();

    public static native boolean wasOpenedThisFrame(String var0);

    public static native boolean wasOpenedThisFrame();

    public static native boolean isOpened(String var0);

    public static native boolean isOpened();

    public static native String getOpenedKey();

    public static native boolean isOk();

    public static native HashMap<String, String> getSelection();

    public static native String getFilePathName();

    public static native String getCurrentFileName();

    public static native String getCurrentPath();

    public static native String getCurrentFilter();

    public static native long getUserDatas();
}

