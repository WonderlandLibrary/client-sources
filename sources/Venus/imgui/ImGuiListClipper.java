/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui;

import imgui.callback.ImListClipperCallback;

public final class ImGuiListClipper {
    private ImGuiListClipper() {
    }

    public static void forEach(int n, ImListClipperCallback imListClipperCallback) {
        ImGuiListClipper.forEach(n, -1, imListClipperCallback);
    }

    public static native void forEach(int var0, int var1, ImListClipperCallback var2);
}

