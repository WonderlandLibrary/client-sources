/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.flag;

public final class ImGuiInputTextFlags {
    public static final int None = 0;
    public static final int CharsDecimal = 1;
    public static final int CharsHexadecimal = 2;
    public static final int CharsUppercase = 4;
    public static final int CharsNoBlank = 8;
    public static final int AutoSelectAll = 16;
    public static final int EnterReturnsTrue = 32;
    public static final int CallbackCompletion = 64;
    public static final int CallbackHistory = 128;
    public static final int CallbackAlways = 256;
    public static final int CallbackCharFilter = 512;
    public static final int AllowTabInput = 1024;
    public static final int CtrlEnterForNewLine = 2048;
    public static final int NoHorizontalScroll = 4096;
    public static final int AlwaysOverwrite = 8192;
    public static final int ReadOnly = 16384;
    public static final int Password = 32768;
    public static final int NoUndoRedo = 65536;
    public static final int CharsScientific = 131072;
    public static final int CallbackResize = 262144;
    public static final int CallbackEdit = 524288;

    private ImGuiInputTextFlags() {
    }
}

