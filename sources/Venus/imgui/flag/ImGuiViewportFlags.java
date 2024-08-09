/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.flag;

public final class ImGuiViewportFlags {
    public static final int None = 0;
    public static final int IsPlatformWindow = 1;
    public static final int IsPlatformMonitor = 2;
    public static final int OwnedByApp = 4;
    public static final int NoDecoration = 8;
    public static final int NoTaskBarIcon = 16;
    public static final int NoFocusOnAppearing = 32;
    public static final int NoFocusOnClick = 64;
    public static final int NoInputs = 128;
    public static final int NoRendererClear = 256;
    public static final int TopMost = 512;
    public static final int Minimized = 1024;
    public static final int NoAutoMerge = 2048;
    public static final int CanHostOtherWindows = 4096;

    private ImGuiViewportFlags() {
    }
}

