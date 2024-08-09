/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.flag;

public final class ImGuiConfigFlags {
    public static final int None = 0;
    public static final int NavEnableKeyboard = 1;
    public static final int NavEnableGamepad = 2;
    public static final int NavEnableSetMousePos = 4;
    public static final int NavNoCaptureKeyboard = 8;
    public static final int NoMouse = 16;
    public static final int NoMouseCursorChange = 32;
    public static final int DockingEnable = 64;
    public static final int ViewportsEnable = 1024;
    public static final int DpiEnableScaleViewports = 16384;
    public static final int DpiEnableScaleFonts = 32768;
    public static final int IsSRGB = 0x100000;
    public static final int IsTouchScreen = 0x200000;

    private ImGuiConfigFlags() {
    }
}

