/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.flag;

public final class ImGuiFocusedFlags {
    public static final int None = 0;
    public static final int ChildWindows = 1;
    public static final int RootWindow = 2;
    public static final int AnyWindow = 4;
    public static final int NoPopupHierarchy = 8;
    public static final int DockHierarchy = 16;
    public static final int RootAndChildWindows = 3;

    private ImGuiFocusedFlags() {
    }
}

