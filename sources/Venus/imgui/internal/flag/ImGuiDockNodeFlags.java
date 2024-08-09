/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.internal.flag;

public final class ImGuiDockNodeFlags
extends imgui.flag.ImGuiDockNodeFlags {
    public static final int DockSpace = 1024;
    public static final int CentralNode = 2048;
    public static final int NoTabBar = 4096;
    public static final int HiddenTabBar = 8192;
    public static final int NoWindowMenuButton = 16384;
    public static final int NoCloseButton = 32768;
    public static final int NoDocking = 65536;
    public static final int NoDockingSplitMe = 131072;
    public static final int NoDockingSplitOther = 262144;
    public static final int NoDockingOverMe = 524288;
    public static final int NoDockingOverOther = 0x100000;
    public static final int NoResizeX = 0x200000;
    public static final int NoResizeY = 0x400000;

    private ImGuiDockNodeFlags() {
    }
}

