/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.flag;

public final class ImGuiTableColumnFlags {
    public static final int None = 0;
    public static final int Disabled = 1;
    public static final int DefaultHide = 2;
    public static final int DefaultSort = 4;
    public static final int WidthStretch = 8;
    public static final int WidthFixed = 16;
    public static final int NoResize = 32;
    public static final int NoReorder = 64;
    public static final int NoHide = 128;
    public static final int NoClip = 256;
    public static final int NoSort = 512;
    public static final int NoSortAscending = 1024;
    public static final int NoSortDescending = 2048;
    public static final int NoHeaderLabel = 4096;
    public static final int NoHeaderWidth = 8192;
    public static final int PreferSortAscending = 16384;
    public static final int PreferSortDescending = 32768;
    public static final int IndentEnable = 65536;
    public static final int IndentDisable = 131072;
    public static final int IsEnabled = 0x1000000;
    public static final int IsVisible = 0x2000000;
    public static final int IsSorted = 0x4000000;
    public static final int IsHovered = 0x8000000;

    private ImGuiTableColumnFlags() {
    }
}

